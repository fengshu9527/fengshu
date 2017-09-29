package com.gdi.service.assessor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.NewAccountIdentifier;

import com.alibaba.fastjson.JSONObject;
import com.gdi.bean.pojo.Project;
import com.gdi.bean.pojo.User;
import com.gdi.bean.pojo.assessor.Assessor;
import com.gdi.bean.returns.Return;
import com.gdi.contract.ContractImpl;
import com.gdi.service.assessor.AssessorService;
import com.gdi.util.CacheUtil;
import com.gdi.util.Consts;
import com.gdi.util.EmailUtils;
import com.gdi.util.PhoneFormatCheckUtils;
import com.gdi.util.RandomCodeUtil;
import com.gdi.util.Util;

/**
 * @author 
 */
@Service
public class AssessorServiceImpl implements AssessorService {

	private ContractImpl contract;

	public AssessorServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}

	@Override
	public Map<String,Object> register(String phone, String password, String jsonString, String validDateInfo) throws InterruptedException, ExecutionException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		//检查手机号是否已注册
		boolean res = contract.hasPhoneRegistered(phone).get().getValue();
		if (!res) {//不存在
			//调用Parity创建用户
			Parity parity = Util.GetParity();
			//密码加密并创建用户 待补充
			NewAccountIdentifier newAccountIdentifier = parity.personalNewAccount(password).send();
			//调用智能合约将数据存入链上
			contract.addAssessor(newAccountIdentifier.getAccountId(), password, phone, validDateInfo, jsonString);
			map.put("code", Consts.OK);
			map.put("msg", "注册成功");
		}else {
			map.put("code", Consts.NOT_FOUND);
			map.put("msg", "手机号已存在");
		}
		return map;
	}

	@Override
	public Return login(String phone, String inputPassword) throws InterruptedException, ExecutionException {
		//检查手机号是否已注册
		boolean res = contract.hasPhoneRegistered(phone).get().getValue();
		if (res) {//存在
			//根据账号获取密码
			List<Type> obj = contract.assessorPassByPhone(phone).get();
			String password = Util.byte32ToString(new Bytes32((byte[]) obj.get(0).getValue()));
			//对比密码（密码加密再对比，待补充）
			if(password.equals(inputPassword)) {
				String personAddr = new Address((BigInteger) obj.get(1).getValue()).toString();
				String validDateInfo = obj.get(3).getValue().toString();
				String jsonvalue = obj.get(4).getValue().toString();
				JSONObject jsonArray = JSONObject.parseObject(jsonvalue);
				Assessor assessor = (Assessor)JSONObject.toJavaObject(jsonArray, Assessor.class);
				if(validDateInfo!=null && !"".equals(validDateInfo) && validDateInfo.length()>0) {
					assessor.setHasSetvalidDateInfo(true);
				}
				assessor.setValidDateInfo(validDateInfo);
				assessor.setPersonAddr(personAddr);
				return Return.getInstance(Consts.OK,"登录成功",assessor,Return.ASSESSOR_USER);
			}
		}
		return Return.getInstance(Consts.NOT_FOUND,"手机号或密码错误",new Assessor(),Return.ASSESSOR_USER);
	}

	@Override
	public Map<String, Object> hasPhoneRegistered(String phone) throws InterruptedException, ExecutionException  {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isBlank(phone)){
			//检查手机号是否已注册
			boolean res = contract.hasPhoneRegistered(phone).get().getValue();
			if (!res) {
				map.put("code", Consts.OK);
				map.put("success", true);
				map.put("msg", "该手机未注册");
			}else {
				map.put("code", Consts.OK);
				map.put("success", false);
				map.put("msg", "手机已注册");
			}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		}
		return map;
	}

	@Override
	public Map<String, Object> sendMessage(String phone) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isBlank(phone)){
			if(PhoneFormatCheckUtils.isPhoneLegal(phone)){
				//生成随机码
				String code = RandomCodeUtil.getRandomCode(6);
				//发短信
				//EmailUtils.sendEmail(phone, code);
				System.out.println("------------------------------发短信----------phone"+phone+"-------code:"+code);
				//保存在cache
				CacheUtil.setKey(CacheUtil.MESSAGECODE_PREFIX+phone, "123456");
				map.put("code", Consts.OK);
				map.put("success", true);
				map.put("msg", "发送成功");
			}else {
				map.put("code", Consts.OK);
				map.put("success", false);
				map.put("msg", "请输入正确的手机号");
			}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		}
		return map;
	}

	@Override
	public Map<String, Object> sendEmail(String email) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isBlank(email)){
			if(EmailUtils.isEmial(email)){
				//生成随机码
				String code = RandomCodeUtil.getRandomCode(6);
				//发短信
				//EmailUtils.sendEmail(email, code);
				//保存在cache
				CacheUtil.setKey(CacheUtil.EMAILCODE_PREFIX+email, "123456");
				map.put("code", Consts.OK);
				map.put("success", true);
				map.put("msg", "发送成功");
			}else {
				map.put("code", Consts.OK);
				map.put("success", false);
				map.put("msg", "请输入正确的邮箱号");
			}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		}
		return map;
	}

	@Override
	public Map<String, Object> checkEmailCode(String email, String code) {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isBlank(email) && !StringUtils.isBlank(code)){
			//在cache获取code
			String emailCode = CacheUtil.getKey(CacheUtil.EMAILCODE_PREFIX+email);
			if(emailCode!=null && !"".equals(emailCode)) {
				if(emailCode.equals(code)) {
					map.put("code", Consts.OK);
					map.put("success", true);
					map.put("msg", "验证成功");
				}else {
					map.put("code", Consts.OK);
					map.put("success", false);
					map.put("msg", "验证码有误，请重新输入");
				}
			}else {
				map.put("code", Consts.OK);
				map.put("success", false);
				map.put("msg", "验证码已超时，请重新获取验证码");
			}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		}
		return map;
	}

	@Override
	public Map<String, Object> checkMessageCode(String phone, String code) {
		Map<String,Object> map = new HashMap<String, Object>();
        if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(code)){
        	//在cache获取code
    		String messageCode = CacheUtil.getKey(CacheUtil.MESSAGECODE_PREFIX+phone);
    		if(messageCode!=null && !"".equals(messageCode)) {
    			if(messageCode.equals(code)) {
    				map.put("code", Consts.OK);
    				map.put("success", true);
    				map.put("msg", "验证码成功");
    			}else {
    				map.put("code", Consts.OK);
    				map.put("success", false);
    				map.put("msg", "验证码有误，请重新输入");
    			}
    		}else {
    			map.put("code", Consts.OK);
    			map.put("success", false);
    			map.put("msg", "验证码已超时，请重新获取验证码");
    		}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		} 
		return map;
	}

	@Override
	public Map<String, Object> updateValidDate(String phone, String validDateInfo) throws InterruptedException, ExecutionException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(validDateInfo)){
			//检查手机号是否已注册
			boolean res = contract.hasPhoneRegistered(phone).get().getValue();
			if(res) {
				contract.modifyValidDate(phone, validDateInfo);
				map.put("code", Consts.OK);
				map.put("success", true);
				map.put("msg", "设置成功");
			}else {
				map.put("code", Consts.OK);
				map.put("success", false);
				map.put("msg", "该手机未注册");
			}
		}else {
			map.put("code", Consts.OK);
			map.put("success", false);
			map.put("msg", "非法的请求参数");
		}
		return map;
	}
}
