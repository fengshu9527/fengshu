package com.gdi.web.assessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gdi.bean.pojo.User;
import com.gdi.bean.pojo.assessor.Assessor;
import com.gdi.bean.pojo.assessor.Certificate;
import com.gdi.bean.pojo.assessor.Language;
import com.gdi.bean.pojo.assessor.ProjectExp;
import com.gdi.bean.returns.Return;
import com.gdi.service.assessor.AssessorService;
import com.gdi.service.user.UserService;
import com.gdi.util.CacheUtil;
import com.gdi.util.Consts;
import com.gdi.util.EmailUtils;
import com.gdi.util.RandomCodeUtil;

/**
 * 评审员控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/assessor")
public class AssessorController {

	@Resource
	private AssessorService assessorService;

	private static Logger logger = LoggerFactory.getLogger(AssessorController.class);

	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Return login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String phone  = request.getParameter("phone");
			String password  = request.getParameter("password");
			logger.info( "Assessor login: " + phone);
			return assessorService.login(phone, password);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new Assessor(),Return.ASSESSOR_USER);
		}
	}

	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonString  = request.getParameter("jsonString");
			if(jsonString==null || "".equals(jsonString) || "null".equals(jsonString)) {
				jsonString = "{\"attentionTime\":\"3年\",\"certificateList\":[{\"getTime\":\"2017-8-11\",\"name\":\"雅思\",\"score\":\"600\"},{\"getTime\":\"2017-8-18\",\"name\":\"托福\",\"score\":\"700\"}],\"email\":\"276273547@qq.com\",\"job\":\"IT男\",\"languageList\":[{\"name\":\"英语\",\"proficiency\":2},{\"name\":\"法语\",\"proficiency\":2}],\"password\":\"123456\",\"phone\":\"13246657033\",\"projectExpList\":[{\"beginTime\":\"2017-7-11\",\"endTime\":\"2017-9-27\",\"name\":\"分享币\",\"rateOfReturn\":\"30%\"},{\"beginTime\":\"2017-1-11\",\"endTime\":\"2017-5-27\",\"name\":\"狗狗比\",\"rateOfReturn\":\"300%\"}],\"userName\":\"fengshu\",\"validDateInfo\":\"2017-9-27,2017-9-28,2017-9-29\",\"workExperience\":\"3年\"}";
			}
			String password  = request.getParameter("password");
			String phone  = request.getParameter("phone");
			String validDateInfo  = request.getParameter("validDateInfo");
			logger.info( "Assessor register: " + phone);
			return assessorService.register(phone, password, jsonString, validDateInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
	
	/**
	 * 设置工时日期
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/setValidDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> setValidDate(HttpServletRequest request, HttpServletResponse response,HttpSession httpsession) {
		try {
			String phone  = request.getParameter("phone");
			String validDateInfo  = request.getParameter("validDateInfo");
			return assessorService.updateValidDate(phone, validDateInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
	
	/**
	 * 检验手机号是否已注册
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hasPhoneRegistered", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> hasPhoneRegistered(HttpServletRequest request, HttpServletResponse response) {
		try {
			String phone  = request.getParameter("phone");
			return assessorService.hasPhoneRegistered(phone);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
	/**
	 * 发短信
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendMessage(HttpServletRequest request, HttpServletResponse response,HttpSession httpsession) {
		try {
			String phone  = request.getParameter("phone");
			return assessorService.sendMessage(phone);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}

	/**
	 * 检验短信验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkMessageCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMessageCode(HttpServletRequest request, HttpServletResponse response,HttpSession httpsession) {
		try {
			String code  = request.getParameter("code");
			String phone  = request.getParameter("phone");
			return assessorService.checkMessageCode(phone, code);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
	
	/**
	 * 发邮件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendEmails(HttpServletRequest request, HttpServletResponse response,HttpSession httpsession) {
		try {
			String email  = request.getParameter("email");
			return assessorService.sendEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}

	/**
	 * 检验邮箱验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkEmailCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkEmailsCode(HttpServletRequest request, HttpServletResponse response,HttpSession httpsession) {
		try {
			String code  = request.getParameter("code");
			String email  = request.getParameter("email");
			return assessorService.checkEmailCode(email, code);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.errorReturn();
		}
	}
}
