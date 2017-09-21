package com.gdi.service.order.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import com.gdi.bean.pojo.Order;
import com.gdi.bean.returns.Return;
import com.gdi.contract.ContractImpl;
import com.gdi.service.order.OrderService;
import com.gdi.util.Consts;
import com.gdi.util.Util;

@Service
public class OrderServiceImpl implements OrderService{
	private final static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	private ContractImpl contract;

	public OrderServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}

	@Override
	public Return getOrderListByUserAddress(String userAddress, int pageIndex) throws InterruptedException, ExecutionException {
		List<Order> orderList = new ArrayList<Order>();
		int count = contract.userInvestCount(userAddress).get().getValue().intValue();
		int from = Consts.PAGE * pageIndex;   
		int to = Math.min(Consts.PAGE * (pageIndex + 1), count);
		for (int i = from; i < to; i++) {
			List<Type> obj = contract.getUserInvest(userAddress, i).get();
			String address = new Address((BigInteger) obj.get(0).getValue()).toString();
			String tokenName = Util.byte32ToString(new Bytes32((byte[]) obj.get(1).getValue()));
			String tradeAddr = obj.get(2).getValue().toString();
			int amount = Integer.parseInt(obj.get(3).getValue().toString());
			String date = obj.get(4).getValue().toString();
			Order order = new Order(address, amount, tokenName, tradeAddr, date);
			orderList.add(order);
		}
		return Return.getInstance(Consts.OK,"获取成功",orderList,Return.RETURN_ORDERLIST);
	}
}