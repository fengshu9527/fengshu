package com.gdi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author littleredhat
 */
public class Consts {

	private static Properties p;

	// 初始化配置
	static {
		p = new Properties(); 
		InputStream in = Consts.class.getResourceAsStream("/config/config.properties");
		InputStreamReader r = new InputStreamReader(in, Charset.forName("UTF-8"));
		try {
			p.load(r);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GAS价格
	public static BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
	// GAS上限
	public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000L);
	// ETHER以太币
	public static BigInteger ETHER = new BigInteger("1000000000000000000");
	// 临时文件前缀
	public static String PREFIX = "key";
	// 临时文件后缀
	public static String SUFFIX = ".tmp";
	// 分页大小
	public static int PAGE = 10;
	// 钱包密码
	public static String PASSWORD = p.getProperty("password");
	// 钱包路径
	public static String PATH = p.getProperty("path");
	// 合约地址
	public static String CROWDFUNDING_ADDR = p.getProperty("crowdfundingAddr");

	//请求状态码
	public static final int OK = 200;//一切正常。
	public static final int BAD_REQUEST = 400;//一般由缺失参数，参数格式不正确等引起。
	public static final int UNAUTHORIZED = 401;//没有提供正确的 API Key。
	public static final int REQUEST_FAILED = 402;//参数格式正确但是请求失败，一般由业务错误引起。 
	public static final int NOT_FOUND = 404;//请求的资源不存在。
	public static final int SERVER_ERRORS = 500;//服务器出错。 
	
	//业务是否成功
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	
	//时间类
	public final static String DATETIME_FORMAT_STR = "yyyy-MM-dd";
	public final static String DATETIMEM_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat(DATETIMEM_FORMAT_STR);

}
