package com.gdi.bean.vo;

import java.util.List;

import com.gdi.bean.pojo.Investor;

public class InvestorVo {
	
	private int investorCount;//投资人数
	List<Investor> investorList;//投资信息列表
	
	public int getInvestorCount() {
		return investorCount;
	}
	public void setInvestorCount(int investorCount) {
		this.investorCount = investorCount;
	}
	public List<Investor> getInvestorList() {
		return investorList;
	}
	public void setInvestorList(List<Investor> investorList) {
		this.investorList = investorList;
	}
	public InvestorVo(int investorCount, List<Investor> investorList) {
		this.investorCount = investorCount;
		this.investorList = investorList;
	}
	public InvestorVo() {
	}
}
