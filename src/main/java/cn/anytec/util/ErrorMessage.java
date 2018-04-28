package cn.anytec.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorMessage {

    public String generate(String code,String reason){
        Map<String,String> message = new HashMap<>();
        message.put("code",code);
        message.put("reason",reason);
        return new JSONObject().fluentPutAll(message).toJSONString();
    }
}
