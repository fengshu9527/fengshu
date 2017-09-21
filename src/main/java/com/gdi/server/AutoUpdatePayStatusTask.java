/*package com.gdi.server;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.gdi.bean.pojo.PayRecordBean;
import com.gdi.bean.returns.PayReturn;
import com.gdi.constants.Constants;
import com.gdi.mapper.PayRecordMapper;
import com.gdi.service.alipay.AlipayService;
import com.gdi.service.weixin.WxPayService;
import com.gdi.utils.alipay.ToolUtil;
import com.gdi.web.payment.PaymentController;

*//**
 * 
 * @author Toe
 * 定时任务实现类，查询已经超过设置好的超时时间的待支付状态订单，并且修改其状态。
 *//*
public class AutoUpdatePayStatusTask {
	private final Logger logger = Logger.getLogger(AutoUpdatePayStatusTask.class);
	@Resource
	private PayRecordMapper payRecordMapper;
	@Resource
	private AlipayService alipayService;
	@Resource
	private WxPayService wxPayService;
	@Resource
	private  Map<String, String> systemConfig;


	//定时任务实现方法
	public void autoUpdatePayStatusTask(){
		this.logger.info("--------------------定时任务已启动--------------------");
		try {
			Long beforeTimeout = ToolUtil.getUTCTimeBeforeTimeout(systemConfig.get("timeoutExpress"));//当前时间减超时时间后的时间
			List<PayRecordBean>  payRecordList  = payRecordMapper.selectByPendingPay(beforeTimeout);
			for (PayRecordBean payRecordBean : payRecordList) {
				String channel = payRecordBean.getChanne();
				//判断支付类型
				if(Constants.ALIPAY_QR.equals(channel)){//支付宝扫码支付
					alipayService.orderQueryAndUpdatePayRecord(payRecordBean);
				}else if(Constants.WX_PUB_QR.equals(channel)){//微信公众号扫码支付
					wxPayService.orderQueryAndUpdatePayRecord(payRecordBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.toString());
		}
		this.logger.info("--------------------定时任务已结束--------------------");
	} 
}
*/