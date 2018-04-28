package cn.anytec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*@SpringBootApplication
@ServletComponentScan
public class NTechApplication {
	public static void main(String[] args) {

		SpringApplication.run(NTechApplication.class, args);
	}
}*/


@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class NTechApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NTechApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(NTechApplication.class, args);
	}
}
