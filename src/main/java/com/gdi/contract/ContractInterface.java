package com.gdi.contract;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import com.gdi.bean.pojo.Order;

/**
 * @author littleredhat
 */
public interface ContractInterface {

	/********** 用户 **********/
	//用户注册
	public Future<TransactionReceipt> addUser(String userAddress, String password, String userName);
	
	//判断用户是否已注册
	public Future<Bool> hasAlreadyRegister(String userName);
	
	//根据用户名获取密码
	public CompletableFuture<List<Type>> getPasswordByUserName(String userName);
	
	/********** ICO项目 **********/
	//发布项目
	public Future<TransactionReceipt> addIcoProject(String icoName, String jsonvalue, int preAmount, Long startTime, Long endTime, String userName);
	
	// 获取信息
	public CompletableFuture<List<Type>> fetchIcoProject(int index);
	
	// 获取数量
	public Future<Uint256> icoProjectsCount();
	
	//判断项目是否存在
	public Future<Bool> existIcoProject(String icoName);
	
	//根据代币名称搜索项目
	public CompletableFuture<List<Type>> searchByIcoName(String icoName);
	
	//根据项目状态搜索项目
	public CompletableFuture<List<Type>> searchByStatus(int index, int status);
	
	/********** 关注 **********/
	//关注ico项目
	public Future<TransactionReceipt> attentionICOProject(String icoName, String userName);
	
	//取消ico项目
	public Future<TransactionReceipt> removeAttentionICOProject(String icoName, String userName);
	
	//判断项目是否被用户关注(项目详情)
	public Future<Bool> hasAttention(String icoName, String userName);
	
	//获取我所有关注的项目数量个数(不包括已取关的项目)
	public Future<Uint256> myAttentionNumber(String userName);
	
	//获取我所有关注的项目数量个数(包括已取关的项目)
	public Future<Uint256> allAttentionNumber(String userName);
	
	// 根据index索引关注ICO信息（我的关注列表）
	public CompletableFuture<List<Type>> myAttentionIcoInfo(String userName, int index);
	
	/********** 投资 **********/
	//投资项目
	public Future<TransactionReceipt> invest(String icoName, String fromAddress, String toAddress, String userName, int amount);
	
	//项目失败返回给投资人金额
	public Future<TransactionReceipt> icoPrjFeedBackAmount(String icoName);
	
	//根据icoName跟index获取投资信息
	public CompletableFuture<List<Type>> fetchInvetstor(String icoName, int index);
	
	//根据icoName获取投资人数量
	public Future<Uint256> fetchInvestorCount(String icoName);
	
	//存放用户交易记录
	public Future<TransactionReceipt> storeUserInvest(Order order);
	
	//根据userAddress获取订单数量
	public Future<Uint256> userInvestCount(String userAddress);
	
	//根据userAddress跟index获取订单信息
	public CompletableFuture<List<Type>> getUserInvest(String userAddress, int index);
	
	//根据username获取我发布的ico项目数量
	public Future<Uint256> getMyPublishedProjectCount(String username);
	
	//根据username获取我发布的ico项目
	public CompletableFuture<List<Type>> getMyPublishedProject(String username, int index);
	
	//根据username获取我投资的ico项目数量
	public Future<Uint256> getMyInvestedProjectCount(String username);
		
	//根据username获取我投资的ico项目
	public CompletableFuture<List<Type>> getMyInvestedProject(String username, int index);
	
	/********** 评审用户 **********/
	
	//评审人员（专业投资人）注册
	public Future<TransactionReceipt> addAssessor(String personAddr, String password, String phone, String validDateInfo, String jsonvalue);
	
	//判断用户是否已注册
	public Future<Bool> hasPhoneRegistered(String phone);
		
	//根据用户名获取密码
	public CompletableFuture<List<Type>> assessorPassByPhone(String phone);
	
	//评审人员（专业投资人）修改工期
	public Future<TransactionReceipt> modifyValidDate(String phone, String validDateInfo);
	
	//获取专业投资人的数量
	public Future<Uint256> getAllAssessorCount();
	
	//根据index获取专业投资人的信息
    public CompletableFuture<List<Type>> getAssessorInfoByIndex(int index);
    
    //将用户放入项目的审核列表中
    public Future<TransactionReceipt> pushAssessorProjects(String phone, int status, String icoName);
    
    //根据审核类型和手机号 获取当前专业投资人的审核信息数量
  	public Future<Uint256> getAssessorTrialListCount(String phone, int trialType);
  	
    //根据审核类型和手机号以及下标 获取当前专业投资人的审核信息
    public CompletableFuture<List<Type>> getAssessorTrialProject(String phone, int index, int trialType);
    
    //只绑定初审状态的Assessor与项目
    public Future<TransactionReceipt> pushSimpleTrialAssessorProjectInfo(String phone, String icoName);
    
    //审核人员添加投票和评论
    public Future<TransactionReceipt> addVoteMessgae(String phone, String icoName, int scam, String comment, int voteType, String username, Long createTime);
    
    //获取项目投票各个状态下投票条数
    public Future<Uint256> getVoteMessageCount(String icoName,int voteType);
    
    //根据投票类型获取项目的评论内容
    public CompletableFuture<List<Type>> getVoteMessage(String icoName, int index, int voteType);
    
} 
