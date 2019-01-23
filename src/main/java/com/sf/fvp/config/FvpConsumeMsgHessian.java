package com.sf.fvp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.sf.fvp.api.kafka.service.SaveFvpRouteMsgService;

/**
 * 
 * @author 01383898 
 * 配置hessian 客户端
 *
 */
@Configuration
public class FvpConsumeMsgHessian {

	@Value("${fvp_route_hessian_url}")
	private String fvp_route_hessian_url;
	
	@Bean
    public HessianProxyFactoryBean fvpRouteClient() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl(fvp_route_hessian_url);
        factory.setServiceInterface(SaveFvpRouteMsgService.class);
        return factory;
    }
}
