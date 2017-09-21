package com.gdi.service.investor.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import com.alibaba.fastjson.JSONObject;
import com.gdi.bean.pojo.Investor;
import com.gdi.bean.pojo.Order;
import com.gdi.bean.pojo.Project;
import com.gdi.bean.returns.Return;
import com.gdi.contract.ContractImpl;
import com.gdi.service.investor.InvestorService;
import com.gdi.util.Consts;
import com.gdi.util.Util;

@Service
public class InvestorServiceImpl implements InvestorService{
	private final static Logger logger = Logger.getLogger(InvestorServiceImpl.class);
	private ContractImpl contract;

	public InvestorServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}

	public InvestorServiceImpl(String password, String content) throws IOException, CipherException {
		// 获取凭证
		File tmp = Util.StoreFile(content);
		Credentials credentials = WalletUtils.loadCredentials(password, tmp);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}



	@Override
	public Return getInvestorList(String icoName,int pageIndex) throws InterruptedException, ExecutionException {
		List<Investor> investorList = new ArrayList<Investor>();
		int count = contract.fetchInvestorCount(icoName).get().getValue().intValue();
		int from = Consts.PAGE * pageIndex;   
		int to = Math.min(Consts.PAGE * (pageIndex + 1), count);
		for (int i = from; i < to; i++) {
			List<Type> obj = contract.fetchInvetstor(icoName, i).get();
			String userName = Util.byte32ToString(new Bytes32((byte[]) obj.get(0).getValue()));
			int amount = Integer.parseInt(obj.get(1).getValue().toString());
			investorList.add(new Investor(amount, userName));
		}
		return Return.getInstanceByInvestor(Consts.OK,"获取成功",Return.RETURN_INVESTORCOUNT,count,Return.RETURN_INVESTORLIST,investorList);
	}

	@Override
	public Map<String, Object> addInvestor(Investor investor) throws ParseException, InterruptedException, ExecutionException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Type> obj = contract.searchByIcoName(investor.getTokenName()).get();
		String jsonvalue = obj.get(0).getValue().toString();
		if(jsonvalue!=null && !"".equals(jsonvalue)) {
			JSONObject jsonArray = JSONObject.parseObject(jsonvalue);
			Project poroject = (Project)JSONObject.toJavaObject(jsonArray, Project.class); 
			if(poroject.getInvestmentThreshold() != 0) {
				//判断投资金额是否大于剩余目标金额
				int realAmount = Integer.parseInt(obj.get(2).getValue().toString());
				int residualAmount = poroject.getTargetAmount() - realAmount;
				if(investor.getAmount() <= residualAmount) {
					if(poroject.getInvestmentThreshold() < investor.getAmount()) {
						//检查投资人余额是否足够投资
						int userBalance = Util.getBalance(investor.getFrom());
						if(userBalance >= investor.getAmount()) {
							//添加投资人数据
							contract.invest(investor.getTokenName(), investor.getFrom(), investor.getTo(), investor.getUserName(), investor.getAmount());
							//转账
							Map<String,Object> result = trasfer(investor);
							if((boolean) result.get("success")) {
								//添加交易数据
								Order order = new Order(investor.getFrom(), investor.getAmount(), investor.getTokenName(), (String)result.get("tradeHash"));
								contract.storeUserInvest(order);
								map.put("code", Consts.OK);
								map.put("msg", "投资成功");
							}else {
								map.put("code", Consts.SERVER_ERRORS);
								map.put("msg", "投资失败");
							}
						}else {
							map.put("code", Consts.BAD_REQUEST);
							map.put("msg", "账户余额必须大于等于投资金额");
						}
					}else {
						map.put("code", Consts.BAD_REQUEST);
						map.put("msg", "投资金额必须大于投资门槛");
					}
				}else {
					map.put("code", Consts.BAD_REQUEST);
					map.put("msg", "投资金额不能大于剩余目标金额");
				}
			}else {
				map.put("code", Consts.BAD_REQUEST);
				map.put("msg", "投资金额不能为0");
			}
		}else {
			map.put("code", Consts.NOT_FOUND);
			map.put("msg", "投资项目不存在");
		}
		return map;
	}

	@Override
	public int getInvestorCount(String icoName) throws InterruptedException, ExecutionException {
		return contract.fetchInvestorCount(icoName).get().getValue().intValue();
	}

	public Map<String,Object> trasfer(Investor investor)  {
		Map<String,Object> map = new HashMap<String, Object>();
		String accountId = investor.getFrom();//转账账户
		String toAccountId = investor.getTo();//收款账户
		BigDecimal amount = new BigDecimal(BigInteger.valueOf(investor.getAmount()).multiply(Consts.ETHER));//转为wei单位
		try{
			Parity parity = Parity.build(new HttpService());
			Transaction transaction = Transaction.createEtherTransaction(accountId,null,null,null,toAccountId,amount.toBigInteger());
			EthSendTransaction ethSendTransaction =parity.personalSignAndSendTransaction(transaction,investor.getPassword()).send();
			if(ethSendTransaction!=null){
				String tradeHash = ethSendTransaction.getTransactionHash();
				logger.debug("invest userAddress:"+accountId+"projectAddress:"+toAccountId+"交易hash:"+tradeHash);
				map.put("success", true);
				map.put("tradeHash", tradeHash);
			}else {
				//加入消息队列，将失败的转账重新执行，直到成功。
				logger.debug("账户:"+accountId+"交易失败!");
				map.put("success", false);
			}
		}catch (Exception e){
			logger.debug("账户:"+accountId+"交易失败!");
			map.put("success", false);
			//加入消息队列，将失败的转账重新执行，直到成功。
			e.printStackTrace();
		}
		return map;
	}
}