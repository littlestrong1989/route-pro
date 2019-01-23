package com.sf.fvp.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sf.fvp.api.kafka.service.SaveFvpRouteMsgService;
import com.sf.fvp.constant.FvpConstants;
import com.sf.kafka.api.consume.ConsumeConfig;
import com.sf.kafka.api.consume.ConsumeOptionalConfig;
import com.sf.kafka.api.consume.IByteArrayMessageConsumeListener;
import com.sf.kafka.api.consume.KafkaConsumeRetryException;
import com.sf.kafka.api.consume.KafkaConsumerRegister;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class SystemInit {

	@Value("${monitor_url}")
	private String monitorUrl;
	
	@Value("${cluster_name}")
	private String clusterName;
	
	@Value("${fvp_route_token}")
	private String fvpRouteToken;
	
	@Value("${fvp_push_topic}")
	private String topic;
	
	@Autowired
	private SaveFvpRouteMsgService saveFvpRouteMsgService;
	
	@PostConstruct
	public void initData() {
		consumeFvpMessage();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void consumeFvpMessage() {
		log.info("系统初始化，开始监听kafka...");
		
		IByteArrayMessageConsumeListener<String> messageConsumeListener = new IByteArrayMessageConsumeListener<String>() {
			
			@Override
			public String decode(byte[] arg0) {
				return null;
			}
 
			@Override
			public void onMessage(List<String> messages) throws KafkaConsumeRetryException{
				try {
					//使用hessian RPC调用远程服务，将消息发送出去
					saveFvpRouteMsgService.saveRouteMsg(messages);
				} catch (Exception e) {
					log.error("调用远程服务失败："+e.getMessage());
					e.printStackTrace();
				}
			}
	    };
	    
	    ConsumeConfig consumeConfig = new ConsumeConfig(FvpConstants.SYSTEM_ID + ":" + fvpRouteToken , monitorUrl, clusterName, topic, 1);
	    
		try {
			//注册kafka，注册成功会监听kafka，获取消息消费
			KafkaConsumerRegister.registerByteArrayConsumer(consumeConfig, messageConsumeListener, new ConsumeOptionalConfig());
		} catch (Exception e) {
			log.error("注册kafka失败..."+e.getMessage());
			e.printStackTrace();
		}
	}
}
