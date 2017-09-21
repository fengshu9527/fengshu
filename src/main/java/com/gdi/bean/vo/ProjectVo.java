package com.gdi.bean.vo;

import java.io.Serializable;

import com.gdi.bean.pojo.Project;

public class ProjectVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2002250597848540580L;
	
	private int id;//项目ID
	private String name;//项目名称
	private int category;//项目范畴 1.国内 2.国外
	private String introduce;//项目介绍
	private int status;//ICO状态  1.未开启 2.已开启 3.已结束
	private String progress;//项目进度
	private String tokenName;//代币名称
	
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
	public ProjectVo() {
	}
			
	public ProjectVo(Project project) {
		this.id = project.getId();
		this.name = project.getName();
		this.category = project.getCategory();
		this.introduce = project.getIntroduce();
		this.status = project.getStatus();
		this.progress = project.getProgress();
		this.tokenName = project.getTokenName();
	}
}
