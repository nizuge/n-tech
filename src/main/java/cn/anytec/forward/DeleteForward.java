package cn.anytec.forward;

import cn.anytec.config.GeneralConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class DeleteForward {
    @Autowired
    GeneralConfig config;

    private static final Logger logger = LoggerFactory.getLogger(DeleteForward.class);
    public String requestForward (String api) {

        URI uri;
        HttpResponse response;
        try {
            uri = new URI("http://" + config.getHostIp() + ":" + config.getSdk_port() + api);
            logger.info(uri.toString());
            response = Request.Delete(uri)
                    .connectTimeout(10000)
                    .socketTimeout(30000)
                    .addHeader("Authorization","Token "+config.getToken())
                    .execute().returnResponse();
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 204){
                return "204";
            }
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
