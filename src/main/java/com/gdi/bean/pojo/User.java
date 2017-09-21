package com.gdi.bean.pojo;

public class User {
	private String userAddress;//用户地址
	private String password;//密码
	private String userName;//用户名/账户
	
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public User(String password, String userName, String userAddress) {
		this.userAddress = userAddress;
		this.password = password;
		this.userName = userName;
	}
	public User() {
	}
}
