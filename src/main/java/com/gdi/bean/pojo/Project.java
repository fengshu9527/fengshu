package com.gdi.bean.pojo;

import java.io.Serializable;
import java.util.Date;

import com.gdi.util.Consts;

public class Project implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7247406675321216775L;
	
	private int id;//项目ID
	private String name;//项目名称
	private int category;//项目范畴 1.国内 2.国外
	private String introduce;//项目介绍
	private int status;//ICO状态  0.审核中 1.ico失败 2.未开启 3.已开启 4.已结束
	private String startTime;//ICO开始时间
	private String endTime;//ICO结束时间
	private int tokenTotal;//代币总量
	private String officialWebsiteUrl;//官网地址
	private String whitePaperlink;//白皮书链接
	private String acceptCurrency;//接受币种 ETH  BTC  EOS
	private String otherCurrency;//其它币种
	private int targetAmount;//目标额度
	private int investmentThreshold;//投资门槛
	private String exchangeRate;//兑换比率
	private String allocationPlan;//分配方案
	private String progress;//项目进度
	private String tokenName;//代币名称
	private String imageUrl;//项目图片地址
	private int realAmount;//实际已筹额度
	private String projectAddress;//筹币地址
	private String userName;//发布人账户
	private String publishTime;//发布时间
	private int investorCount;//投资人数
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getTokenTotal() {
		return tokenTotal;
	}
	public void setTokenTotal(int tokenTotal) {
		this.tokenTotal = tokenTotal;
	}
	public String getOfficialWebsiteUrl() {
		return officialWebsiteUrl;
	}
	public void setOfficialWebsiteUrl(String officialWebsiteUrl) {
		this.officialWebsiteUrl = officialWebsiteUrl;
	}
	public String getWhitePaperlink() {
		return whitePaperlink;
	}
	public void setWhitePaperlink(String whitePaperlink) {
		this.whitePaperlink = whitePaperlink;
	}
	public String getAcceptCurrency() {
		return acceptCurrency;
	}
	public void setAcceptCurrency(String acceptCurrency) {
		this.acceptCurrency = acceptCurrency;
	}
	public String getOtherCurrency() {
		return otherCurrency;
	}
	public void setOtherCurrency(String otherCurrency) {
		this.otherCurrency = otherCurrency;
	}
	public int getInvestmentThreshold() {
		return investmentThreshold;
	}
	public void setInvestmentThreshold(int investmentThreshold) {
		this.investmentThreshold = investmentThreshold;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getAllocationPlan() {
		return allocationPlan;
	}
	public void setAllocationPlan(String allocationPlan) {
		this.allocationPlan = allocationPlan;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public int getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(int targetAmount) {
		this.targetAmount = targetAmount;
	}
	public int getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(int realAmount) {
		this.realAmount = realAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProjectAddress() {
		return projectAddress;
	}
	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public int getInvestorCount() {
		return investorCount;
	}
	public void setInvestorCount(int investorCount) {
		this.investorCount = investorCount;
	}
	public Project() {
	}
	public Project(String userName, String name, int category, String introduce, int status, String startTime, String endTime,
			int tokenTotal, String officialWebsiteUrl, String whitePaperlink, String acceptCurrency,
			String otherCurrency, int targetAmount, int investmentThreshold, String exchangeRate,
			String allocationPlan, String tokenName) {
		this.publishTime = Consts.DATETIME_FORMAT.format(new Date());
		this.userName = userName;
		this.name = name;
		this.category = category;
		this.introduce = introduce;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tokenTotal = tokenTotal;
		this.officialWebsiteUrl = officialWebsiteUrl;
		this.whitePaperlink = whitePaperlink;
		this.acceptCurrency = acceptCurrency;
		this.otherCurrency = otherCurrency;
		this.targetAmount = targetAmount;
		this.investmentThreshold = investmentThreshold;
		this.exchangeRate = exchangeRate;
		this.allocationPlan = allocationPlan;
		this.tokenName = tokenName;
	}
}
