package com.gdi.service.project.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.NewAccountIdentifier;
import com.alibaba.fastjson.JSONObject;
import com.gdi.bean.pojo.Project;
import com.gdi.bean.returns.Return;
import com.gdi.bean.vo.ProjectVo;
import com.gdi.contract.ContractImpl;
import com.gdi.service.project.ProjectService;
import com.gdi.util.Consts;
import com.gdi.util.Util;

@Service
public class ProjectServiceImpl implements ProjectService{
	private static String PASSWORD = "123456";
	private ContractImpl contract;
	
	public ProjectServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}

	public ProjectServiceImpl(String password, String content) throws IOException, CipherException {
		// 获取凭证
		File tmp = Util.StoreFile(content);
		Credentials credentials = WalletUtils.loadCredentials(password, tmp);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}
	
	@Override
	public Return getProjectList(int pageIndex) throws InterruptedException, ExecutionException {
		List<Project> projectVoList = new ArrayList<Project>();
		int count = contract.icoProjectsCount().get().getValue().intValue();
		int from = Consts.PAGE * pageIndex;   
		int to = Math.min(Consts.PAGE * (pageIndex + 1), count);
		for (int i = from; i < to; i++) {
			List<Type> obj = contract.fetchIcoProject(i).get();
			projectVoList.add(ObjectParseToProject(obj));
		}
		return Return.getInstance(Consts.OK,"获取成功",projectVoList,Return.RETURN_PROJECTLIST);
	}
	
	@Override
	public Return getProjectByTokenName(String tokenName) throws InterruptedException, ExecutionException {
		List<Type> obj = contract.searchByIcoName(tokenName).get();
		String jsonvalue = obj.get(0).getValue().toString();
        if(jsonvalue!=null && !"".equals(jsonvalue)) {
			return Return.getInstance(Consts.OK,"获取成功",ObjectParseToProject(obj),Return.RETURN_PROJECT);
        }else {
        	return Return.getInstance(Consts.NOT_FOUND,"未找到数据",new Project(),Return.RETURN_PROJECT);
        }
	}
	
	@Override
	public Map<String,Object> addProject(Project project) throws ParseException, InterruptedException, ExecutionException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		boolean res = contract.existIcoProject(project.getTokenName()).get().getValue();
		if(!res) {
			//调用Parity创建用户
			Parity parity = Util.GetParity();
			//密码加密并创建用户 待补充
			NewAccountIdentifier newAccountIdentifier = parity.personalNewAccount(PASSWORD).send();
			project.setProjectAddress(newAccountIdentifier.getAccountId());
			String projectJsonString = JSONObject.toJSONString(project);
			contract.addIcoProject(project.getTokenName(), projectJsonString, project.getTargetAmount(), Util.stringToUnixStamp(project.getStartTime()), Util.stringToUnixStamp(project.getEndTime()));
			map.put("code", Consts.OK);
			map.put("msg", "发布成功");
		}else {
			map.put("code", Consts.NOT_FOUND);
			map.put("msg", "项目代币名已存在");
		}
		return map;
	}
	//web3j返回的obj解析成project对象
	public Project ObjectParseToProject(List<Type> obj) throws InterruptedException, ExecutionException {
		Double Progress = 0.00;
		String jsonvalue = obj.get(0).getValue().toString();
		int realAmount = Integer.parseInt(obj.get(1).getValue().toString());
		int icoStatus = Integer.parseInt(obj.get(2).getValue().toString());
		JSONObject jsonArray = JSONObject.parseObject(jsonvalue);
		Project poroject = (Project)JSONObject.toJavaObject(jsonArray, Project.class);
		int investorCount = contract.fetchInvestorCount(poroject.getTokenName()).get().getValue().intValue();
		poroject.setRealAmount(realAmount);
		poroject.setStatus(icoStatus);
		poroject.setInvestorCount(investorCount);
		if(realAmount!=0) {
			Progress = new BigDecimal(realAmount).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(poroject.getTargetAmount()).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
			poroject.setProgress(Util.priceFormat(Progress*100));
		}
		return poroject;
	}
}
