package com.gdi.service.order;

import java.util.concurrent.ExecutionException;
import com.gdi.bean.returns.Return;

public interface OrderService {
	/**
	 * 根据代币名称和页码获取项目的投资人列表
	 * @param icoName
	 * @param pageIndex
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	Return getOrderListByUserAddress(String userAddress, int pageIndex) throws InterruptedException, ExecutionException;
	
}
