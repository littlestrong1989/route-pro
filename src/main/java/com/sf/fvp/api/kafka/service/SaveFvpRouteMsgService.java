package com.sf.fvp.api.kafka.service;

import java.util.List;

/**
 * 
 * @author 01383898
 *	Hessian接口
 */
public interface SaveFvpRouteMsgService {

	/**
	 * 消费kafka消息，并通过RPC将数据传输给远程服务器
	 * @param messages kafka消息
	 */
	public void saveRouteMsg(List<String> messages);
}
