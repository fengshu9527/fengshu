package com.gdi.web.user;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gdi.bean.pojo.User;
import com.gdi.bean.returns.Return;
import com.gdi.service.user.UserService;
import com.gdi.util.Consts;

/**
 * @author 
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/login")
	@ResponseBody
	public Return login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userName  = request.getParameter("userName");
			String password  = request.getParameter("password");
			logger.info( " login: " + userName);
			return userService.login(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new User(),Return.RETURN_USER);
		}
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userName  = request.getParameter("userName");
			String password  = request.getParameter("password");
			logger.info( " register: " + userName);
			return userService.register(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
}
