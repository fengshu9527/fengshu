package com.gdi.service.user.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.NewAccountIdentifier;
import com.gdi.bean.pojo.User;
import com.gdi.bean.returns.Return;
import com.gdi.contract.ContractImpl;
import com.gdi.service.user.UserService;
import com.gdi.util.Consts;
import com.gdi.util.Util;

/**
 * @author 
 */
@Service
public class UserServiceImpl implements UserService {

	private ContractImpl contract;

	public UserServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}

	@Override
	public Map<String,Object> register(String userName, String password) throws InterruptedException, ExecutionException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		//检查用户名是否已注册
		boolean res = contract.hasAlreadyRegister(userName).get().getValue();
		if (!res) {//不存在
			//调用Parity创建用户
			Parity parity = Util.GetParity();
			//密码加密并创建用户 待补充
			NewAccountIdentifier newAccountIdentifier = parity.personalNewAccount(password).send();
			//调用智能合约将数据存入链上
			contract.addUser(newAccountIdentifier.getAccountId(), password, userName);
			map.put("code", Consts.OK);
			map.put("msg", "注册成功");
		}else {
			map.put("code", Consts.NOT_FOUND);
			map.put("msg", "用户名已存在");
		}
		return map;
	}

	@Override
	public Return login(String userName, String inputPassword) throws InterruptedException, ExecutionException {
		//检查用户名是否已注册
		boolean res = contract.hasAlreadyRegister(userName).get().getValue();
		if (res) {//存在
			//根据账号获取密码
			List<Type> obj = contract.getPasswordByUserName(userName).get();
			String password = Util.byte32ToString(new Bytes32((byte[]) obj.get(0).getValue()));
			String userAddress = new Address((BigInteger) obj.get(1).getValue()).toString();
			//缺失地址信息待补充
			//对比密码（密码加密再对比，待补充）
			if(password.equals(inputPassword)) {
				return Return.getInstance(Consts.OK,"登录成功",new User(password, userName, userAddress),Return.RETURN_USER);
			}
		}
		return Return.getInstance(Consts.NOT_FOUND,"账户或密码错误",new User(),Return.RETURN_USER);
	}
}
