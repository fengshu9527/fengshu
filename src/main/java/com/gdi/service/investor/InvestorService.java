package com.gdi.service.investor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.gdi.bean.pojo.Investor;
import com.gdi.bean.returns.Return;

public interface InvestorService {
	/**
	 * 添加投资记录
	 * @param investor
	 * @return
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException 
	 */
	Map<String,Object> addInvestor(Investor investor) throws ParseException, InterruptedException, ExecutionException, IOException;
	/**
	 * 根据代币名称和页码获取项目的投资人列表
	 * @param icoName
	 * @param pageIndex
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	Return getInvestorList(String icoName, int pageIndex) throws InterruptedException, ExecutionException;
	/**
	 * 根据代币名称获取项目的投资人数量
	 * @param icoName
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	int getInvestorCount(String icoName) throws InterruptedException, ExecutionException;
}
