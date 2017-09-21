pragma solidity ^0.4.13;

contract IcoPrjContract {
    //================代币相关======================
    mapping (address => uint) balances;

    event Transfer(address indexed _from, address indexed _to, uint256 _value);

    // 初始化默认值为10000枚
    function MetaCoin() {
        balances[tx.origin] = 10000;
    }

    function sendCoin(address receiver, uint amount) {
        if (balances[msg.sender] < amount) return;
        balances[msg.sender] -= amount;
        balances[receiver] += amount;
        Transfer(msg.sender, receiver, amount);
    }

    function getBalanceInEth(address addr) returns(uint){
        return getBalance(addr);
    }

    function getBalance(address addr) returns(uint) {
        return balances[addr];
    }
    
    //=================投资ICO相关=====================
    // 投资investor结构体
    struct Investor {
        address from;       // 投资人标志
        address to;         // ICO收款地址
        uint    amount;     // 投资额度
        bytes32 iconName;   
        bytes32 userName;   // 投资人名称
    }
    
    // 用户交易记录
    struct UserInvest {
        address from;      //当前用户信息
        bytes32 icoName;
        string tradeAddr;  // 交易hash值
        uint amount;
        string date; 
    }
    mapping(address=>UserInvest[]) userInvestMap;
    
    // 根据icoName,投资人address,获取对应的投资人所在index坐标
    mapping(bytes32=>mapping(address=>uint)) investMap;     
    
    // 存放用户交易记录
    function storeUserInvest(address _from, bytes32 icoName, string tradeAddr, uint amount, string date) {
        userInvestMap[_from].push(UserInvest(_from, icoName, tradeAddr, amount, date));
    }
    
    // 获取用户交易记录条数
    function userInvestCount(address from) returns(uint) {
        return userInvestMap[from].length;
    } 
    
    // 根据index获取记录
        function getUserInvest(address from, uint i) returns (address, bytes32, string, uint, string) {
        var sender = userInvestMap[from][i];
        return (sender.from, sender.icoName, sender.tradeAddr, sender.amount, sender.date);
    }
    
    
    
    // 投资记录
    event Invest(bytes32 indexed icoName, address indexed _from, address indexed _to, uint amount);
    
    // 支持操作
    function invest(bytes32 icoName, address _from, address _to, bytes32 userName, uint amount) {
        
        var sender = icoProjectList[icoName];
        
        // 添加投资记录到链上
        Invest(icoName, _from, _to, amount); 
        
        // 投资人记录
        if(investMap[icoName][_from] == 0) {
            sender.investors.push(Investor(_from, _to, amount, icoName, userName));
            investMap[icoName][_from] = sender.investors.length;
        } else {
            var index = investMap[icoName][_from] - 1;
            sender.investors[index].amount += amount;
        }
        
        // 进度变化
        sender.realAmount += amount;
    }
    
    // 项目失败返回给投资人金额
    function icoPrjFeedBackAmount(bytes32 icoName) {
        var sender = icoProjectList[icoName];
        for(uint i = 0;i < sender.investors.length; i++) {
            var investor = sender.investors[i];
            balances[investor.to] -= investor.amount;
            balances[investor.from] += investor.amount;
        }
    }
    
    // 根据icoName跟index获取投资信息
    function fetchInvetstor(bytes32 icoName, uint index) returns (bytes32, uint) {
        var sender = icoProjectList[icoName];
         return (sender.investors[index].userName, sender.investors[index].amount);
    }
    
    // 根据icoName获取投资人数量
    function fetchInvestorCount(bytes32 icoName) returns(uint) {
        var sender = icoProjectList[icoName];
        return sender.investors.length;
    }
    
    
    //=================ico项目=====================
    struct IcoProject {
        bool exist;
        string jsonvalue;       // 项目信息转过来的json字符串
        Investor[] investors;       // 项目ICO记录
        Investor[] investorInfo;    // 项目投资人记录
        uint preAmount;             // 目标额度
        uint realAmount;            // 实际额度
        uint startTime;             // 起始时间
        uint endTime;               // 结束时间
        IcoStatus icoStatus;        // 项目状态
    }
    
    enum IcoStatus {IcoPrepare, IcoPublishFail, IcoStart, IcoDoing, IcoFinished}    // ICO项目状态 
    mapping(bytes32 => IcoProject) public icoProjectList;   // string表示的是ICO代币名
    bytes32[] public icoNames;                              // icoName数组
    
    // 判断记录是否存在
    function existIcoProject(bytes32 icoName) returns(bool) {
        var sender = icoProjectList[icoName];
        if(sender.exist) 
            return true; 
        return false;
    }
    
    // 发布ICO项目
    event AddICOProject(bytes32 indexed iconName, string jsonvalue);
    
    // 发布ICO项目
    function addICOProject(bytes32 icoName, string jsonvalue, uint preAmount, uint startTime, uint endTime) {
        var sender = icoProjectList[icoName];
        sender.exist = true;
        sender.jsonvalue = jsonvalue;
        sender.preAmount = preAmount;
        sender.startTime = startTime;
        sender.endTime = endTime;
        sender.realAmount = 0;
        sender.icoStatus = IcoStatus.IcoPrepare;
        icoNames.push(icoName);
        AddICOProject(icoName, jsonvalue);
    }
    
    // 审核设置
    function icoProjectStatus(bytes32 icoName, IcoStatus icoStatus) {
        var sender = icoProjectList[icoName];
        if(icoStatus == IcoStatus.IcoPublishFail) {
            sender.icoStatus = icoStatus;
        } else {
            if(sender.startTime > now) {
                sender.icoStatus = IcoStatus.IcoStart;
            } else if(now >= sender.startTime && now < sender.endTime) {
                sender.icoStatus = IcoStatus.IcoDoing;
            } else {
                sender.icoStatus = IcoStatus.IcoFinished;
            }
        }
    }
    
    // 状态变更
    function icoPrjChangeStatus(bytes32 icoName) {
        var sender = icoProjectList[icoName];
        if(sender.icoStatus != IcoStatus.IcoPublishFail && sender.icoStatus != IcoStatus.IcoPrepare) {
            if(sender.startTime > now) {
                sender.icoStatus = IcoStatus.IcoStart;
            } else if(now >= sender.startTime && now < sender.endTime) {
                sender.icoStatus = IcoStatus.IcoDoing;
            } else {
                sender.icoStatus = IcoStatus.IcoFinished;
            }
        }
    }
    
    // 根据index获取记录
    function fetchICOProject(uint i) returns (string, uint, IcoStatus) {
        var sender = icoNames[i];
        icoPrjChangeStatus(sender);
        return (icoProjectList[sender].jsonvalue, icoProjectList[sender].realAmount, icoProjectList[sender].icoStatus);
    }
    
    // 根据iconame搜索
    function searchByIcoName(bytes32 icoName) returns(string, uint, IcoStatus) {
        icoPrjChangeStatus(icoName);
        return (icoProjectList[icoName].jsonvalue, icoProjectList[icoName].realAmount, icoProjectList[icoName].icoStatus);
    }
    
    // 根据index获取记录
    function searchByStatus(uint i, IcoStatus icoStatus) returns (bool, string, uint, IcoStatus) {
        var sender = icoNames[i];
        icoPrjChangeStatus(sender);
        if(icoProjectList[sender].icoStatus == icoStatus)
            return (true, icoProjectList[sender].jsonvalue, icoProjectList[sender].realAmount, icoProjectList[sender].icoStatus);
        return (false, "", 0, IcoStatus.IcoPrepare);
    }
    
    // 记录个数
    function icoProjectsCount() returns (uint) {
        return icoNames.length;
    }
    
    
    ///=====================用户信息====================
    struct User {
        address userAddress;
        bytes32 password;
        string userName;
        mapping(bytes32=>uint) attentMap;
        bytes32[] attentList;
    }
    
    mapping(string => User) userMap;
    User[] public userList;
    string[] public nameList;
    
    // 添加用户监听
    event AddUser(address _userAdd, bool isSuccess, string message);
    
    // 关注ICO项目
    function attentionICOProject(bytes32 icoName, string username) {
        var sender = userMap[username];
        if(sender.attentMap[icoName] == 0) {
            sender.attentMap[icoName] = 1;
            if(attentionListExistIcoName(icoName, username) == false) {
                sender.attentList.push(icoName);
            }
        }
    }
    
    function attentionListExistIcoName(bytes32 icoName, string username) returns(bool) {
        var sender = userMap[username];
        for(uint i = 0; i < sender.attentList.length; i++) {
            var info = sender.attentList[i];
            if(bytesEqual(info, icoName)) {
                return true;
            }
        }
        return false;
    }
    
    // 取消关注ICO项目
    function removeAttentionICOProject(bytes32 icoName, string username) {
        var sender = userMap[username];
        sender.attentMap[icoName] = 0;
    }
    
    // 判断项目是否被用户关注
    function hasAttention(bytes32 icoName, string username) returns(bool) {
        var sender = userMap[username].attentMap[icoName];
        if(sender == 0)
            return false;
        return true;
    }
    
    // 我的关注个数
    function myAttentionNumber(string username) returns(uint) {
        var sender = userMap[username];
        var maxLength = sender.attentList.length;
        for(uint i = 0; i < sender.attentList.length; i++) {
            var icoName = sender.attentList[i];
            if(sender.attentMap[icoName] == 0) {
                maxLength--;
            }
        }
        if(maxLength < 0 )
            maxLength = 0;
        return maxLength;
    }
    
    //获取我所有关注的项目数量个数(包括已取关的项目)
    function allAttentionNumber(string username) returns(uint) {
        var sender = userMap[username];
        return sender.attentList.length;
    }
    
    // 根据index索引关注ICO信息
    function myAttentionIcoInfo(string username, uint index) returns(bool, string , uint, IcoStatus) {
        var sender = userMap[username];
        var icoName = sender.attentList[index];
        if(sender.attentMap[icoName] == 0) {
            return (false, "", 0, IcoStatus.IcoPrepare);
        } else {
            return (true, icoProjectList[icoName].jsonvalue, icoProjectList[icoName].realAmount, icoProjectList[icoName].icoStatus);   
        }
    }
    
    // 注册用户
    function addUser(address userAdd, bytes32 password, string userName) {
        var sender = userMap[userName];
        sender.userAddress = userAdd;
        sender.password = password;
        sender.userName = userName;
        userList.push(sender);
        nameList.push(userName);
        AddUser(userAdd, true, "该用户注册成功");
    }
    
    // 根据name获取password
    function userPassByName(string userName) returns (bytes32,address) {
        return (userMap[userName].password, userMap[userName].userAddress);
    }
  
    
    // 判断用户名是否存在
    function hasAlreadyRegister(string userName)returns(bool) {
        for(uint i = 0; i < nameList.length; i++) {
            var sender = nameList[i];
            if (stringEqual(sender,userName)){
                return true;
            }
        }
        return false;
    }
    
    /// @dev Compares two strings and returns true iff they are equal.
    function stringEqual(string _a, string _b) returns (bool) {
        return compare(_a, _b) == 0;
    }

    
    function compare(string _a, string _b) returns (int) {
        bytes memory a = bytes(_a);
        bytes memory b = bytes(_b);
        uint minLength = a.length;
        if (b.length < minLength) minLength = b.length;
        //@todo unroll the loop into increments of 32 and do full 32 byte comparisons
        for (uint i = 0; i < minLength; i ++)
            if (a[i] < b[i])
                return -1;
            else if (a[i] > b[i])
                return 1;
        if (a.length < b.length)
            return -1;
        else if (a.length > b.length)
            return 1;
        else
            return 0;
    }
    
    function bytesEqual(bytes32 _a, bytes32 _b) returns (bool) {
        return compareBytes(_a, _b) == 0;
    }
    
    function compareBytes(bytes32 a, bytes32 b) returns(int) {
        uint minLength = a.length;
        if (b.length < minLength) minLength = b.length;
        //@todo unroll the loop into increments of 32 and do full 32 byte comparisons
        for (uint i = 0; i < minLength; i ++)
            if (a[i] < b[i])
                return -1;
            else if (a[i] > b[i])
                return 1;
        if (a.length < b.length)
            return -1;
        else if (a.length > b.length)
            return 1;
        else
            return 0;
    }
    
}
