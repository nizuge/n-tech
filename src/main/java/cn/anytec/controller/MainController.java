package cn.anytec.controller;

import cn.anytec.config.GeneralConfig;
import cn.anytec.forward.DeleteForward;
import cn.anytec.forward.GetForward;
import cn.anytec.forward.PostForward;
import cn.anytec.forward.PutForward;
import cn.anytec.util.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ErrorMessage errorMessage;
    @Autowired
    GetForward getForward;
    @Autowired
    PostForward postForward;
    @Autowired
    PutForward putForward;
    @Autowired
    DeleteForward deleteForward;
    @Autowired
    GeneralConfig config;

    @RequestMapping(value = "/**",method = RequestMethod.GET)
    @ResponseBody
    public String getRequest(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        String api;
        if(uri.contains(config.getPro_name())){
            api = request.getRequestURI().split(config.getPro_name(),2)[1];
        }else {
            api = uri;
        }
        String reply = getForward.requestForward(api,request.getParameterMap());
        response.setHeader("Access-Control-Allow-Origin", "*");
        return reply;
    }

    @RequestMapping(value = "/**",method = RequestMethod.POST)
    @ResponseBody
    public String postRequest(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "photo",required = false)MultipartFile photo,
                              @RequestParam(value = "photo1",required = false)MultipartFile photo1,
                              @RequestParam(value = "photo2",required = false)MultipartFile photo2){
        String uri = request.getRequestURI();
        String api;
        if(uri.contains(config.getPro_name())){
            api = request.getRequestURI().split(config.getPro_name(),2)[1];
        }else {
            api = uri;
        }
        String reply;
        if(photo != null){
            reply = postForward.requestForward(api,request.getParameterMap(),photo);
        }else if(photo1 != null){
            if(photo2 == null) {
                response.setStatus(400);
                return errorMessage.generate("BAD_PARAM", "URL or file \'photo2\' not found in request");
            }
            reply = postForward.requestForward(api,request.getParameterMap(),photo1,photo2);
        }else {
            reply = postForward.requestForward(api,request.getParameterMap());
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return reply;
    }

    @RequestMapping(value = "/**",method = RequestMethod.PUT)
    @ResponseBody
    public String putRequest(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        String api;
        if(uri.contains(config.getPro_name())){
            api = request.getRequestURI().split(config.getPro_name(),2)[1];
        }else {
            api = uri;
        }
        Map<String,String> param = new HashMap<String,String>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            String key = "";
            while((s = bufferedReader.readLine())!=null){
                if(s.contains("Content-Disposition: form-data")) {
                    key = s.substring(38, s.length() - 1);
                    param.put(key, null);
                    continue;
                }
                if(s.equals("")||s.startsWith("----"))
                    continue;
                param.put(key,s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String reply = putForward.requestForward(api,param);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return reply;
    }

    @RequestMapping(value = "/**",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRequest(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        String api;
        if(uri.contains(config.getPro_name())){
            api = request.getRequestURI().split(config.getPro_name(),2)[1];
        }else {
            api = uri;
        }
        String reply = deleteForward.requestForward(api);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(reply.equals("204")){
            response.setStatus(204);
            reply = "";
        }
        return reply;
    }

}
