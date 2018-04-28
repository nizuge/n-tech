package cn.anytec.forward;

import cn.anytec.config.GeneralConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

@Component
public class PostForward {
	private static Logger logger = LoggerFactory.getLogger(PostForward.class);

	@Autowired
	GeneralConfig config;

    public String requestForward(String api, Map<String,String[]> params, MultipartFile... files) {
		URI uri;
		HttpEntity entity;
		HttpResponse response;
		try {
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			ContentType contentType= ContentType.create("text/plain", Charset.forName("UTF-8"));
			if(files != null && files.length != 0){
				for(MultipartFile file : files){
					multipartEntityBuilder.addBinaryBody(file.getName(),file.getBytes(), ContentType.DEFAULT_BINARY, file.getOriginalFilename());
				}
			}
			if(params != null && params.size() != 0){
				params.keySet().forEach((n-> {
					logger.info(n + ":" + params.get(n));
					multipartEntityBuilder.addTextBody(n, params.get(n)[0], contentType);
				}));
			}
			entity = multipartEntityBuilder.build();
			uri = new URI("http://" + config.getHostIp() + ":" + config.getSdk_port() + api);
			logger.info(uri.toString());
			response = Request.Post(uri)
					.connectTimeout(10000)
					.socketTimeout(30000)
					.addHeader("Authorization","Token "+config.getToken())
					.body(entity)
					.execute().returnResponse();
			return EntityUtils.toString(response.getEntity());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}