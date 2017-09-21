package com.gdi.service.attention;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.gdi.bean.returns.Return;

public interface AttentionService {
	/**
	 * 关注ICO项目
	 * @return
	 */
	Map<String,Object> attentionICOProject(String tokenName, String userName);
	/**
	 * 取消关注ICO项目
	 * @return
	 */
	Map<String,Object> removeAttentionICOProject(String tokenName, String userName);
	/**
	 * 根据用户名获取ico项目关注分页列表
	 * @param pageIndex
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	Return getAttentionList(int pageIndex, String userName) throws InterruptedException, ExecutionException;
	
	/**
	 * 用户是否关注了该项目(项目详情)
	 * @param tokenName
	 * @param userName
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	boolean hasAttention(String tokenName, String userName) throws InterruptedException, ExecutionException;
}
