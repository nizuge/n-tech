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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

@Component
public class PutForward {

	@Autowired
	GeneralConfig config;

	private static final Logger logger = LoggerFactory.getLogger(PutForward.class);
	public String requestForward (String api, Map<String,String> params) {

		URI uri;
		HttpResponse response;
		HttpEntity entity;
		try {
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			ContentType contentType= ContentType.create("text/plain", Charset.forName("UTF-8"));
			if(params != null && params.size() != 0){
				params.keySet().forEach((n->{
					logger.info(n+":"+params.get(n));
					multipartEntityBuilder.addTextBody(n,params.get(n),contentType);
				}));
			}
			entity = multipartEntityBuilder.build();
			uri = new URI("http://" + config.getHostIp() + ":" + config.getSdk_port() + api);
			logger.info(uri.toString());
			response = Request.Put(uri)
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
