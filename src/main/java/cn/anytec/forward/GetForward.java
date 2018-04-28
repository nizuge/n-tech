package cn.anytec.forward;

import cn.anytec.config.GeneralConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

@Component
public class GetForward {

	@Autowired
	GeneralConfig config;
	
	private static final Logger logger = LoggerFactory.getLogger(GetForward.class);
	public String requestForward (String api, Map<String,String[]> params) {

		URI uri;
		HttpResponse response;
		try {
			StringBuilder url = new StringBuilder("http://" + config.getHostIp() + ":" + config.getSdk_port() + api);
			if(params != null && params.size() != 0){
				if(params.containsKey("max_id")){
					url.append("?").append("max_id").append("=").append(params.get("max_id")[0]);
				}else if(params.containsKey("min_id")){
					url.append("?").append("min_id").append("=").append(params.get("min_id")[0]);
				}
			}
			logger.info(url.toString());
			uri = new URI(url.toString());
			response = Request.Get(uri)
					.connectTimeout(10000)
					.socketTimeout(30000)
					.addHeader("Authorization","Token "+config.getToken())
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
