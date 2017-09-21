package com.gdi.bean.pojo;

public class Investor {
	private String from;//投资人地址(唯一标志)
	private String to;//ico项目收款地址(唯一标志)
	private int amount;//投资金额
	private String tokenName;//ico代币名称(唯一标志)
	private String userName;//投资人名称(唯一标志)
	//以下字段不存入区块链
	private String password;//用户密码
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Investor(int amount, String userName) {
		this.amount = amount;
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Investor(String from, String to, int amount, String tokenName, String userName, String password) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.tokenName = tokenName;
		this.userName = userName;
		this.password = password;
	}
	public Investor() {
	}
}
