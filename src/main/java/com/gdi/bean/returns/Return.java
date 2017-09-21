package com.gdi.bean.returns;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdi.bean.pojo.Investor;
import com.gdi.util.Consts;
public class Return implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8125640580197756762L;
	
	public static final String RETURN_CODE = "code";
	public static final String RETURN_MESSAGE = "msg";
	public static final String RETURN_PROJECTLIST = "projectList";
	public static final String RETURN_PROJECT = "project";
	public static final String RETURN_USER = "user";
	public static final String RETURN_INVESTORCOUNT = "investorCount";
	public static final String RETURN_INVESTORLIST = "investorList";
	public static final String RETURN_ORDERLIST = "orderList";
	
	private Map<Object,Object> result = new HashMap<Object, Object>();
	private Map<Object,Object> data = new HashMap<Object, Object>();
	
	
	public void setRl(String key,Object value){
		this.result.put(key, value);			
	}
	
	public void setDt(String key,Object value){
		this.data.put(key, value);			
	}
	
	public Map<Object, Object> getResult() {
		return result;
	}

	public void setResult(Map<Object, Object> result) {
		this.result = result;
	}

	public Map<Object, Object> getData() {
		return data;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	public Return(){
		
	}
	
	public static Return getInstance(int code,String msg,Object object,String dataName){
		Return result = new Return();
		result.setRl(RETURN_CODE, code);
		result.setRl(RETURN_MESSAGE, msg);
		result.setDt(dataName, object);
		return result;
	}
	
	public static Return getInstanceByInvestor(int code,String msg,String dataName1,Object object1,String dataName2,Object object2){
		Return result = new Return();
		result.setRl(RETURN_CODE, code);
		result.setRl(RETURN_MESSAGE, msg);
		result.setDt(dataName1, object1);
		result.setDt(dataName2, object2);
		return result;
	}
	
	
	public Return(Map<Object,Object> result, Map<Object,Object> data) {
		this.result = result;
		this.data = data;
	}
	
	public static Map<String,Object> errorReturn() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", Consts.SERVER_ERRORS);
		map.put("success", Consts.FALSE);
		map.put("msg", "服务器出错");
		return map;
	}
}
