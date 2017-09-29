package com.gdi.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import com.gdi.bean.pojo.Order;
import com.gdi.util.Util;

/**
 * @author littleredhat
 */
public class ContractImpl extends Contract implements ContractInterface {

	/**
	 * CrowdFunding合约
	 * 
	 * @param contractAddress
	 *            合约地址
	 * @param web3j
	 *            RPC请求
	 * @param credentials
	 *            钱包凭证
	 * @param gasPrice
	 *            GAS价格
	 * @param gasLimit
	 *            GAS上限
	 */
	public ContractImpl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		super("", contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	@Override
	public Future<TransactionReceipt> addUser(String userAddress, String password, String userName) {
		Function function = new Function("addUser", Arrays.<Type>asList(new Address(userAddress),Util.stringToByte32(password),Util.stringToByte32(userName)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public Future<Bool> hasAlreadyRegister(String userName) {
		Function function = new Function("hasAlreadyRegister", Arrays.asList(Util.stringToByte32(userName)),Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> getPasswordByUserName(String userName) {
		Function function = new Function("userPassByName", Arrays.asList(Util.stringToByte32(userName)), 
				Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
				}, new TypeReference<Address>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}
	
	@Override
	public Future<Bool> existIcoProject(String icoName) {
		Function function = new Function("existIcoProject", Arrays.asList(Util.stringToByte32(icoName)),Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeCallSingleValueReturnAsync(function);
	}
	@Override
	public Future<TransactionReceipt> addIcoProject(String icoName, String jsonvalue, int preAmount, Long startTime, Long endTime, String userName) {
		Function function = new Function("addICOProject", Arrays.<Type>asList(Util.stringToByte32(icoName), new Utf8String(jsonvalue), new Uint256(preAmount), new Uint256(startTime), new Uint256(endTime), Util.stringToByte32(userName)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public CompletableFuture<List<Type>> fetchIcoProject(int i) {
		Function function = new Function("fetchICOProject", Arrays.asList(new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<Uint256> icoProjectsCount() {
		Function function = new Function("icoProjectsCount", Arrays.asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}
	
	@Override
	public CompletableFuture<List<Type>> searchByIcoName(String icoName) {
		Function function = new Function("searchByIcoName", Arrays.asList(Util.stringToByte32(icoName)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}
	
	@Override
	public CompletableFuture<List<Type>> searchByStatus(int i, int status) {
		Function function = new Function("searchByStatus", Arrays.asList(new Uint256(i),new Uint256(status)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
				}, new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<TransactionReceipt> attentionICOProject(String icoName, String userName) {
		Function function = new Function("attentionICOProject", Arrays.<Type>asList(Util.stringToByte32(icoName),Util.stringToByte32(userName)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public Future<TransactionReceipt> removeAttentionICOProject(String icoName, String userName) {
		Function function = new Function("removeAttentionICOProject", Arrays.<Type>asList(Util.stringToByte32(icoName),Util.stringToByte32(userName)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public Future<Bool> hasAttention(String icoName, String userName) {
		Function function = new Function("hasAttention", Arrays.asList(Util.stringToByte32(icoName), Util.stringToByte32(userName)),Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public Future<Uint256> myAttentionNumber(String userName) {
		Function function = new Function("myAttentionNumber", Arrays.asList(Util.stringToByte32(userName)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}
	
	@Override
	public Future<Uint256> allAttentionNumber(String userName) {
		Function function = new Function("allAttentionNumber", Arrays.asList(Util.stringToByte32(userName)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> myAttentionIcoInfo(String userName, int i) {
		Function function = new Function("myAttentionIcoInfo", Arrays.asList(Util.stringToByte32(userName),new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
				}, new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<TransactionReceipt> invest(String icoName, String fromAddress, String toAddress, String userName, int amount) {
		Function function = new Function("invest", Arrays.<Type>asList(Util.stringToByte32(icoName), new Address(fromAddress), new Address(toAddress), Util.stringToByte32(userName), new Uint256(BigInteger.valueOf(amount))), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public Future<TransactionReceipt> icoPrjFeedBackAmount(String icoName) {
		Function function = new Function("icoPrjFeedBackAmount", Arrays.<Type>asList(Util.stringToByte32(icoName)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public CompletableFuture<List<Type>> fetchInvetstor(String icoName, int i) {
		Function function = new Function("fetchInvetstor", Arrays.asList(Util.stringToByte32(icoName),new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<Uint256> fetchInvestorCount(String icoName) {
		Function function = new Function("fetchInvestorCount", Arrays.asList(Util.stringToByte32(icoName)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public Future<TransactionReceipt> storeUserInvest(Order order) {
		Function function = new Function("storeUserInvest", Arrays.<Type>asList(new Address(order.getUserAddress()),Util.stringToByte32(order.getTokenName()), new Utf8String(order.getTradeAddr()), new Uint256(BigInteger.valueOf(order.getAmount())),Util.stringToByte32(order.getDate())), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function); 
	}

	@Override
	public Future<Uint256> userInvestCount(String userAddress) {
		Function function = new Function("userInvestCount", Arrays.asList(new Address(userAddress)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> getUserInvest(String userAddress, int i) {
		Function function = new Function("getUserInvest", Arrays.asList(new Address(userAddress),new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
				}, new TypeReference<Bytes32>() {
				}, new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Bytes32>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}
	
	@Override
	public Future<Uint256> getMyPublishedProjectCount(String username) {
		Function function = new Function("getMyPublishedProjectCount", Arrays.asList(Util.stringToByte32(username)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> getMyPublishedProject(String username, int i) {
		Function function = new Function("getMyPublishedProject", Arrays.asList(Util.stringToByte32(username),new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<Uint256> getMyInvestedProjectCount(String username) {
		Function function = new Function("getMyInvestedProjectCount", Arrays.asList(Util.stringToByte32(username)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> getMyInvestedProject(String username, int i) {
		Function function = new Function("getMyInvestedProject", Arrays.asList(Util.stringToByte32(username),new Uint256(BigInteger.valueOf(i))),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
				}, new TypeReference<Uint256>() {
				}, new TypeReference<Uint256>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<TransactionReceipt> addAssessor(String personAddr, String password, String phone, String validDateInfo, String jsonvalue) {
		Function function = new Function("addAssessor", Arrays.<Type>asList(new Address(personAddr),Util.stringToByte32(password), Util.stringToByte32(phone), new Utf8String(validDateInfo), new Utf8String(jsonvalue)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function);
	}

	@Override
	public Future<Bool> hasPhoneRegistered(String phone) {
		Function function = new Function("hasPhoneRegistered", Arrays.asList(Util.stringToByte32(phone)),Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeCallSingleValueReturnAsync(function);
	}

	@Override
	public CompletableFuture<List<Type>> assessorPassByPhone(String phone) {
		Function function = new Function("assessorPassByPhone", Arrays.asList(Util.stringToByte32(phone)), 
				Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
				}, new TypeReference<Address>() {
				}, new TypeReference<Bytes32>() {
				}, new TypeReference<Utf8String>() {
				}, new TypeReference<Utf8String>() {
				}));
		return executeCallMultipleValueReturnAsync(function);
	}

	@Override
	public Future<TransactionReceipt> modifyValidDate(String phone, String validDateInfo) {
		Function function = new Function("modifyValidDate", Arrays.<Type>asList(Util.stringToByte32(phone), new Utf8String(validDateInfo)), Collections.<TypeReference<?>>emptyList());
		return executeTransactionAsync(function);
	}
}
