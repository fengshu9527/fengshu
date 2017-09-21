package com.gdi.web.attention;
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
import com.gdi.bean.returns.Return;
import com.gdi.bean.vo.ProjectVo;
import com.gdi.service.attention.AttentionService;
import com.gdi.util.Consts;

@Controller
@RequestMapping("/attention")
public class AttentionController {
	private final Logger logger = Logger.getLogger(AttentionController.class);

	@Resource
	private AttentionService attentionService;


	/**
	 * 关注项目
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/attention", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object>  publish(HttpServletRequest request, HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String tokenName = request.getParameter("tokenName");
			logger.debug("attention userName:"+userName+"tokenName"+tokenName);
			return attentionService.attentionICOProject(tokenName, userName);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", Consts.SERVER_ERRORS);
			map.put("msg", "服务器出错");
			return map;
		}
	}
	
	/**
	 * 取消关注项目
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/cancelAttention", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object>  cancelAttention(HttpServletRequest request, HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String tokenName = request.getParameter("tokenName");
			logger.debug("cancelAttention userName:"+userName+"tokenName"+tokenName);
			return attentionService.removeAttentionICOProject(tokenName, userName);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", Consts.SERVER_ERRORS);
			map.put("msg", "服务器出错");
			return map;
		}
	}
	

	/**
	 * 获得项目关注列表列表信息
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getAttentionList", method = RequestMethod.POST) 
	@ResponseBody
	public Return getProjectList(HttpServletRequest request, HttpServletResponse resp){
		try {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			String userName = request.getParameter("userName");
			return attentionService.getAttentionList(pageIndex, userName);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new ArrayList<ProjectVo>(),Return.RETURN_PROJECTLIST);
		}
	}
}
