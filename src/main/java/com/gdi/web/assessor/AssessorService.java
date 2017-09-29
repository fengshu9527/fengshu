package com.gdi.service.assessor;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.gdi.bean.returns.Return;
public interface AssessorService {
	
	/**
	 * 评审人注册
	 * @param userName
	 * @param password
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Map<String, Object> register(String userName,String password,String jsonString,String validDateInfo) throws InterruptedException, ExecutionException, IOException;
	
	/**
	 * 评审人登录
	 * @param phone
	 * @param password
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Return login(String phone,String password) throws InterruptedException, ExecutionException;
	
	/**
	 * 验证手机号是否已存在
	 * @param userName
	 * @param password
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Map<String, Object> hasPhoneRegistered(String phone) throws InterruptedException, ExecutionException;
	
	
	/**
	 * 发短信
	 * @param phone
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> sendMessage(String phone) throws Exception;
	
	/**
	 * 发邮件
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> sendEmail(String email) throws Exception;
	
	
	/**
	 * 检验邮箱验证码
	 * @param email
	 * @return
	 */
	public Map<String, Object> checkEmailCode(String email, String code);
	
	/**
	 * 检验邮箱验证码
	 * @param email
	 * @return
	 */
	public Map<String, Object> checkMessageCode(String phone, String code);
	
	/**
	 * 修改工时日期
	 * @param phone
	 * @param code
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Map<String, Object> updateValidDate(String phone, String validDateInfo) throws InterruptedException, ExecutionException;
	
}
