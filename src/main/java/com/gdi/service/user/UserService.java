package com.gdi.service.user;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.gdi.bean.pojo.User;
import com.gdi.bean.returns.Return;

public interface UserService {
	
	/**
	 * app用户注册
	 * @param userName
	 * @param password
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Map<String, Object> register(String userName,String password) throws InterruptedException, ExecutionException, IOException;
	
	/**
	 * app用户登录
	 * @param userName
	 * @param password
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Return login(String userName,String password) throws InterruptedException, ExecutionException;
	
}
