package com.sf.fvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppConfiguration {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AppConfiguration.class);
		//禁止命令行设置参数
        springApplication.setAddCommandLineProperties(false);
		springApplication.run(args); 
	}
}
