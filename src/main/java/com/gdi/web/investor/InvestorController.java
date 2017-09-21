package com.gdi.web.investor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gdi.bean.pojo.Investor;
import com.gdi.bean.returns.Return;
import com.gdi.service.investor.InvestorService;
import com.gdi.util.Consts;

@Controller
@RequestMapping("/investment")
public class InvestorController {
	private final Logger logger = Logger.getLogger(InvestorController.class);

	@Resource
	private InvestorService InvestorService;

	/**
	 * 投资
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/invest", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object>  publish(HttpServletRequest request, HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String from = request.getParameter("userAddress");//用户地址
			String to  = request.getParameter("projectAddress");//项目地址
			String amount = request.getParameter("amount");
			String tokenName = request.getParameter("tokenName");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			logger.debug("invest userName:"+userName+"tokenName:"+tokenName+"amount"+amount);
			Investor investor =new Investor(from, to, Integer.parseInt(amount), tokenName, userName, password);
			return InvestorService.addInvestor(investor);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", Consts.SERVER_ERRORS);
			map.put("msg", "服务器出错");
			return map;
		}
	}
	
	/**
	 * 提供给app获得项目列表信息
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getInvestList", method = RequestMethod.POST) 
	@ResponseBody
	public Return getProjectList(HttpServletRequest request, HttpServletResponse resp){
		try {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			String tokenName = request.getParameter("tokenName");
			return InvestorService.getInvestorList(tokenName, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstanceByInvestor(Consts.SERVER_ERRORS,"服务器出错",Return.RETURN_INVESTORCOUNT,0,Return.RETURN_INVESTORLIST,new ArrayList<Investor>());
		}
	}
}
