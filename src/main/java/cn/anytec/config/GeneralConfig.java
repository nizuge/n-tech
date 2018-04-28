package cn.anytec.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeneralConfig {

    @Value("${sdk.host_ip}")
    private String hostIp;
    @Value("${sdk.token}")
    private String token;
    @Value("${sdk.port}")
    private String sdk_port;
    @Value("${config.name}")
    private String pro_name;

    public String getHostIp() {
        return hostIp;
    }

    public String getToken() {
        return token;
    }

    public String getSdk_port() {
        return sdk_port;
    }

    public String getPro_name() {
        return pro_name;
    }
}

