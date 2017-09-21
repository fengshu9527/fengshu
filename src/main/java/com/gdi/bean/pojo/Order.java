package com.gdi.bean.pojo;

import java.util.Date;

import com.gdi.util.Consts;

public class Order {
	private String userAddress;//投资人地址(唯一标志)
    private int amount;//投资金额
	private String tokenName;//ico代币名称
	private String tradeAddr;//交易hash值
	private String date;//创建时间
	
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
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
	public String getTradeAddr() {
		return tradeAddr;
	}
	public void setTradeAddr(String tradeAddr) {
		this.tradeAddr = tradeAddr;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Order(String userAddress, int amount, String tokenName, String tradeAddr) {
		this.userAddress = userAddress;
		this.amount = amount;
		this.tokenName = tokenName;
		this.tradeAddr = tradeAddr;
		this.date = Consts.DATETIME_FORMAT.format(new Date());
	}
	public Order(String userAddress, int amount, String tokenName, String tradeAddr, String date) {
		this.userAddress = userAddress;
		this.amount = amount;
		this.tokenName = tokenName;
		this.tradeAddr = tradeAddr;
		this.date = date;
	}
	public Order() {
	}
}
