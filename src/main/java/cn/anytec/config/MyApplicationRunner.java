package cn.anytec.config;
/**
 * 服务器启动时自动执行
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    GeneralConfig config;

    /*@PostConstruct
    public void initCameraHandler(){
        logger.info("=====初始化CameraDataHandler=====");
    }*/
    @Override
    public void run(ApplicationArguments arg) throws Exception {
        logger.info("====== 应用启动时执行 =======");
    }
}
