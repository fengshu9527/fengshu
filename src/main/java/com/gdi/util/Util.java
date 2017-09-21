package com.gdi.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;

import com.gdi.contract.ContractImpl;

/**
 * @author littleredhat
 */
public class Util {

	/**
	 * 存储文件
	 * 
	 * @param content
	 * @return
	 */
	public static File StoreFile(String content) {
		// 临时文件
		File tmp = null;
		try {
			tmp = File.createTempFile(Consts.PREFIX, Consts.SUFFIX);
			// 自动删除
			tmp.deleteOnExit();
			// 写入内容
			BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tmp;
	}

	/**
	 * 获取合约
	 * 
	 * @param credentials
	 * @param contractAddress
	 * @return
	 */
	public static ContractImpl GetCrowdFundingContract(Credentials credentials, String contractAddress) {
		// defaults to http://localhost:8545/
		Web3j web3j = Web3j.build(new HttpService());
		return new ContractImpl(contractAddress, web3j, credentials, Consts.GAS_PRICE, Consts.GAS_LIMIT);
	}
	
	/**
	 * 获取Parity
	 * 
	 * @param credentials
	 * @param contractAddress
	 * @return
	 */
	public static Parity GetParity() {
		// defaults to http://localhost:8545/
		return Parity.build(new HttpService());
	}
	
	
	//根据地址返回余额（单位以太币，不带小数）
	public static int getBalance(String address) throws IOException {
		Web3j web3 = Web3j.build(new HttpService());
		EthGetBalance web3ClientVersion = web3.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
		BigInteger balance = web3ClientVersion.getBalance();
		return balance.divide(Consts.ETHER).intValue();
	}


	/**
	 * 获取当前时间的UTC和date以及Unix时间戳
	 *
	 * @return
	 */
	public static Map<String, Object> getNowUTCAndDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			String t = df.format(date);  
			long unixStamp = df.parse(t).getTime()/1000;
			map.put("unixStamp", unixStamp);
			map.put("date", date);
			map.put("utc", cal.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return map;
	}

	/**
	 * 根据时间字符串转UnixStamp
	 *
	 * @param timeStr       时间字符串
	 * @return UnixStamp
	 * @throws ParseException 
	 */
	public static long stringToUnixStamp(String timeStr) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.parse(timeStr).getTime()/1000;
	}


	/**
	 * 获得当前Unix时间戳
	 *
	 * @return
	 */
	public static long  getDateToUTC() {
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			String t = df.format(date);
			return df.parse(t).getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * byte32转String
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String  byte32ToString(Bytes32 bytes32input){
		byte[] input = bytes32input.getValue();
		ByteArrayOutputStream iByteArrayInputStream=new ByteArrayOutputStream();
		for(int i=0;i<input.length;i++){
			if(input[i]==0)continue;
			iByteArrayInputStream.write(input[i] );
		}
		return iByteArrayInputStream.toString();
	}


	/**
	 * String转byte32
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Bytes32 stringToByte32(String input){
		byte[] inputByte=input.getBytes();
		if(inputByte.length >32)throw new RuntimeException("length > 32 ");
		else
			if(inputByte.length==32) return new Bytes32(inputByte);
			else{
				byte[]   byte32 = new byte[32];
				int i=byte32.length - inputByte.length;
				for(int j=i;j<32;j++ ){
					byte32[j]=inputByte[j-i];
				} 
				return new Bytes32(byte32);
			}
	}
	
	/**
	 * 补齐小数点后两位
	 * @param price
	 * @return
	 */
	public static String priceFormat(Double price){
		String priceformat = "";
		String pricef = String.valueOf(price);
		DecimalFormat df = new DecimalFormat("#.00");
		String  [] p= pricef.split("\\.");
		if(p.length>1){
			if(p [0].equals("0")){
				priceformat = "0" + String.valueOf(df.format(price));
			}else{
				priceformat = df.format(price);
			}
		}else{
			priceformat = df.format(price);
		}
		return priceformat;
	}
}
