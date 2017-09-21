package com.gdi.web.order;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gdi.bean.pojo.Order;
import com.gdi.bean.returns.Return;
import com.gdi.service.order.OrderService;
import com.gdi.util.Consts;

@Controller
@RequestMapping("/order")
public class OrderController {
	private final Logger logger = Logger.getLogger(OrderController.class);

	@Resource
	private OrderService orderService;

	/**
	 * 提供给app获得项目列表信息
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST) 
	@ResponseBody
	public Return getProjectList(HttpServletRequest request, HttpServletResponse resp){
		try {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			String userAddress = request.getParameter("userAddress");
			return orderService.getOrderListByUserAddress(userAddress, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return Return.getInstance(Consts.SERVER_ERRORS,"服务器出错",new ArrayList<Order>(),Return.RETURN_ORDERLIST);
		}
	}
}
