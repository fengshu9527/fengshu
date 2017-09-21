package com.gdi.web.project;

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
import com.gdi.bean.pojo.Project;
import com.gdi.bean.returns.Return;
import com.gdi.bean.vo.ProjectVo;
import com.gdi.service.project.ProjectService;
import com.gdi.util.Consts;

@Controller
@RequestMapping("/project")
public class ProjectController {
	private final Logger logger = Logger.getLogger(ProjectController.class);

	@Resource
	private ProjectService projectService;


	/**
	 * 提供给app发布项目的接口
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object>  publish(HttpServletRequest request, HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");//发布人
			String name  = request.getParameter("name");//项目名称
			String category = request.getParameter("category");
			String introduce = request.getParameter("introduce");
			String status = request.getParameter("status");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String tokenTotal = request.getParameter("tokenTotal");
			String officialWebsiteUrl = request.getParameter("officialWebsiteUrl");
			String whitePaperlink = request.getParameter("whitePaperlink");
			String acceptCurrency = request.getParameter("acceptCurrency");
			String otherCurrency = request.getParameter("otherCurrency");
			String targetAmount = request.getParameter("targetAmount");
			String investmentThreshold = request.getParameter("investmentThreshold");
			String exchangeRate = request.getParameter("exchangeRate");
			String allocationPlan = request.getParameter("allocationPlan");
			//String image = request.getParameter("image");
			String tokenName = request.getParameter("tokenName");

			Project project = new Project(userName, name, Integer.parseInt(category), introduce, Integer.parseInt(status), startTime, 
					endTime,Integer.parseInt(tokenTotal), officialWebsiteUrl, whitePaperlink, acceptCurrency,otherCurrency, 
					Integer.parseInt(targetAmount), Integer.parseInt(investmentThreshold), 
					exchangeRate,allocationPlan, tokenName);
			logger.debug("publish projectName:"+name);
			return projectService.addProject(project);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", Consts.SERVER_ERRORS);
			map.put("msg", "服务器出错");
			return map;
		}
	}

	/**
	 * 根据项目代币名获得项目项目详情
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getProjectByTokenName") 
	@ResponseBody
	public Return getProjectByTokenName(HttpServletRequest request, HttpServletResponse resp){
		try {
			String tokenName = request.getParameter("tokenName");
			return projectService.getProjectByTokenName(tokenName);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new Project(),Return.RETURN_PROJECT);
		}
	}

	/**
	 * 提供给app获得项目列表信息
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST) 
	@ResponseBody
	public Return getProjectList(HttpServletRequest request, HttpServletResponse resp){
		try {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			return projectService.getProjectList(pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new ArrayList<ProjectVo>(),Return.RETURN_PROJECTLIST);
		}
	}
}
