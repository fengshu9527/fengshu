package com.gdi.service.attention.impl;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.alibaba.fastjson.JSONObject;
import com.gdi.bean.pojo.Project;
import com.gdi.bean.returns.Return;
import com.gdi.bean.vo.ProjectVo;
import com.gdi.contract.ContractImpl;
import com.gdi.service.attention.AttentionService;
import com.gdi.util.Consts;
import com.gdi.util.Util;

@Service
public class AttentionServiceImpl implements AttentionService{
	
	//private ProjectMapper projectMapper;
	
	private ContractImpl contract;
	
	public AttentionServiceImpl() throws IOException, CipherException { 
		// 获取凭证
		Credentials credentials = WalletUtils.loadCredentials(Consts.PASSWORD, Consts.PATH);
		contract = Util.GetCrowdFundingContract(credentials, Consts.CROWDFUNDING_ADDR);
	}
	
	@Override
	public Map<String, Object> attentionICOProject(String tokenName, String userName) {
		Map<String,Object> map = new HashMap<String, Object>();
		contract.attentionICOProject(tokenName, userName);
		map.put("code", Consts.OK);
		map.put("msg", "关注成功");
		return map;
	}

	@Override
	public Map<String, Object> removeAttentionICOProject(String tokenName, String userName) {
		Map<String,Object> map = new HashMap<String, Object>();
		contract.removeAttentionICOProject(tokenName, userName);
		map.put("code", Consts.OK);
		map.put("msg", "取消关注成功");
		return map;
	}

	@Override
	public Return getAttentionList(int pageIndex, String userName) throws InterruptedException, ExecutionException {
		List<ProjectVo> projectVoList = new ArrayList<ProjectVo>();
		int myCount = contract.myAttentionNumber(userName).get().getValue().intValue();//获取我关注的项目的数量（不包括已取关的）
		if(myCount>0) {
			Double Progress = 0.00;
			int myAllCount = contract.allAttentionNumber(userName).get().getValue().intValue();//获取我关注的项目的数量（包括已取关的）
			int from = Consts.PAGE * pageIndex;   
			int to = Math.min(Consts.PAGE * (pageIndex + 1), myAllCount);
			for (int i = from; i < to; i++) {
				List<Type> obj = contract.myAttentionIcoInfo(userName, i).get();
				boolean  hasAttention = Boolean.parseBoolean(obj.get(0).getValue().toString());
				if(hasAttention) {
					String jsonvalue = obj.get(1).toString();
					int realAmount = Integer.parseInt(obj.get(2).getValue().toString());
					int icoStatus = Integer.parseInt(obj.get(3).getValue().toString());
					JSONObject jsonArray = JSONObject.parseObject(jsonvalue);
					Project poroject = (Project)JSONObject.toJavaObject(jsonArray, Project.class);
					poroject.setRealAmount(realAmount);
					poroject.setStatus(icoStatus);
					if(realAmount!=0) {
						Progress = new BigDecimal(realAmount).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(poroject.getTargetAmount()).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
						poroject.setProgress(Util.priceFormat(Progress*100));
					}
					ProjectVo projectVo = new ProjectVo(poroject);
					projectVoList.add(projectVo);
				}
			}
		}
		return Return.getInstance(Consts.OK,"获取成功",projectVoList,Return.RETURN_PROJECTLIST);
	}

	@Override
	public boolean hasAttention(String tokenName, String userName) throws InterruptedException, ExecutionException {
		return contract.hasAttention(tokenName, userName).get().getValue();
	}
}
