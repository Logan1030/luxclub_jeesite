package com.footing.website.modules.luxclub.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.DateUtils;
import com.footing.website.common.utils.IdGen;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.api.client.ClientProperty;
import com.footing.website.modules.luxclub.api.request.CardPasswordRequest;
import com.footing.website.modules.luxclub.api.request.MemberCardRequest;
import com.footing.website.modules.luxclub.api.response.MemberCardResp;
import com.footing.website.modules.luxclub.api.response.TokenResp;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.common.FeeType;
import com.footing.website.modules.luxclub.common.MemberHisOpreationType;
import com.footing.website.modules.luxclub.common.MemberState;
import com.footing.website.modules.luxclub.common.WalletFeeType;
import com.footing.website.modules.luxclub.dao.FeeRecordDao;
import com.footing.website.modules.luxclub.dao.MemberInfoDao;
import com.footing.website.modules.luxclub.dao.MemberInfoHisDao;
import com.footing.website.modules.luxclub.dao.OrderDao;
import com.footing.website.modules.luxclub.dao.WalletFeeRecordDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.entity.FeeRecord;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.ClientCacheUtils;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 会员信息Service
 * @author liuguoqing
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class MemberInfoService extends CrudService<MemberInfoDao, MemberInfo> {

    @Autowired
    private FeeRecordDao feeRecordDao;

    @Autowired
    private CustomerClientTokenService customerClientTokenService;

    @Autowired
    private MemberInfoHisDao memberInfoHisDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private WalletFeeRecordDao walletFeeRecordDao;
    @Autowired
    private MessageNotifyService messageNotifyService;
    public MemberInfo get(Long id) {
        return super.get(id);
    }

    /**
     * 根据ID获取会员信息(记录锁)
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public MemberInfo getById(Long id) {
        return dao.getById(id);
    }

    public List<MemberInfo> findList(MemberInfo memberInfo) {
        return super.findList(memberInfo);
    }

    public Page<MemberInfo> findPage(Page<MemberInfo> page, MemberInfo memberInfo) {
        return super.findPage(page, memberInfo);
    }

    @Transactional(readOnly = false)
    public void save(MemberInfo memberInfo) {
        memberInfo.preInsert();
        super.save(memberInfo);
    }

    @Transactional(readOnly = false)
    public void delete(MemberInfo memberInfo) {
        super.delete(memberInfo);
    }

    /**
     * 解绑
     * @param memberInfo
     * @return
     */
    @Transactional(readOnly = false)
    public boolean unbindMemberCard(MemberInfo memberInfo) {
        List<CustomerClientToken> customerClientTokenList = customerClientTokenService.getCustomerClientTokenList(memberInfo.getMemberCardno());
        for (CustomerClientToken customerClientToken : customerClientTokenList) {
            CustomerClientUtils.clearCache(customerClientToken);
        }
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.UNBIND, null, null, null);
        memberInfoHis.setRemarks("人工解绑会员卡");
        int result = memberInfoHisDao.insert(memberInfoHis);
        return result > 0 ? true : false;
    }
    
    /**
     * 更新卡状态(激活、冻结、注销)
     * @param memberInfo
     * @return
     */
    @Transactional(readOnly = false)
    public boolean changeMemberCard(MemberInfo memberInfo, Integer opreationType) {
        memberInfo.preUpdate();
        boolean result = dao.updateMemberState(memberInfo);
        if (result) {
            MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, opreationType, null, null, null);
            memberInfoHisDao.insert(memberInfoHis);
        }
        return result;
    }
    
    /**
     * 余额充值
     * @param memberInfo
     */
    @Transactional(readOnly = false)
    public void balanceRecharge(MemberInfo memberInfo) {
        //更新卡内余额
        memberInfo.preUpdate();
        dao.changeBalance(memberInfo);
        // 充值后余额
        memberInfo.setMemberBalance(memberInfo.getMemberBalance().add(memberInfo.getAmount()));
        // 添加费用记录
        this.saveFeeRecord(memberInfo, FeeType.RECHARGE, memberInfo.getMemberBalance());
        // 添加会员卡历史
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.BALANCE_RECHARGE, null, memberInfo.getAmount(), null);
        memberInfoHisDao.insert(memberInfoHis);
    }

    /**
     * 余额扣费
     * @param memberInfo
     */
    @Transactional(readOnly = false)
    public void balanceDeduct(MemberInfo memberInfo) {
        //更新卡内余额
        memberInfo.setAmount(memberInfo.getAmount().multiply(new BigDecimal(-1)));// 转为负数
        memberInfo.preUpdate();
        dao.changeBalance(memberInfo);
        // 扣费后余额
        memberInfo.setMemberBalance(memberInfo.getMemberBalance().add(memberInfo.getAmount()));
        // 添加费用记录
        this.saveFeeRecord(memberInfo, FeeType.DEDUCT, memberInfo.getMemberBalance());
        // 添加会员卡历史
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.BALANCE_DEDUCT, null, memberInfo.getAmount(), null);
        memberInfoHisDao.insert(memberInfoHis);
    }
    
    /**
     * 零钱包充值
     * @param memberInfo
     */
    @Transactional(readOnly = false)
    public void walletRecharge(MemberInfo memberInfo) {
        //更新零钱包余额
        memberInfo.preUpdate();
        dao.changeWallet(memberInfo);
        // 充值后余额
        memberInfo.setWalletBalance(memberInfo.getWalletBalance().add(memberInfo.getAmount()));
        // 添加费用记录
        this.saveWalletFeeRecord(memberInfo, WalletFeeType.RECHARGE, memberInfo.getWalletBalance());
        // 添加会员卡历史
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.WALLET_RECHARGE, null, null, memberInfo.getAmount());
        memberInfoHisDao.insert(memberInfoHis);
    }
    
    /**
     * 零钱包扣费
     * @param memberInfo
     * @throws Exception 
     */
    @Transactional(readOnly = false)
    public void walletDeduct(MemberInfo memberInfo) {
        //更新零钱包余额
        memberInfo.setAmount(memberInfo.getAmount().multiply(new BigDecimal(-1)));// 转为负数
        memberInfo.preUpdate();
        dao.changeWallet(memberInfo);
        // 扣费后余额
        memberInfo.setWalletBalance(memberInfo.getWalletBalance().add(memberInfo.getAmount()));
        // 添加费用记录
        this.saveWalletFeeRecord(memberInfo, WalletFeeType.DEDUCT, memberInfo.getWalletBalance());
        // 添加会员卡历史
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.WALLET_DEDUCT, null, null, memberInfo.getAmount());
        memberInfoHisDao.insert(memberInfoHis);
    }
    
    /**
     * 保存卡余额费用记录
     * @param memberInfo
     * @param type
     * @param balance
     * @return
     */
    private FeeRecord saveFeeRecord(MemberInfo memberInfo, Integer type, BigDecimal balance){
        FeeRecord feeRecord = new FeeRecord();
        feeRecord.preInsert();
        feeRecord.setMemberCardno(memberInfo.getMemberCardno());
        feeRecord.setFeeType(type);
        feeRecord.setFeeMoney(memberInfo.getAmount());
        feeRecord.setBalance(balance);
        feeRecord.setRemarks(memberInfo.getRemarks());
        feeRecord.setVoucher(memberInfo.getVoucherPhoto());
        feeRecordDao.insert(feeRecord);
        return feeRecord;
    }
    
    /**
     * 保存零钱包费用记录
     * @param memberInfo
     * @param type
     * @param balance
     * @return
     */
    private WalletFeeRecord saveWalletFeeRecord(MemberInfo memberInfo, Integer type, BigDecimal balance){
        WalletFeeRecord walletFeeRecord = new WalletFeeRecord();
        walletFeeRecord.preInsert();
        walletFeeRecord.setMemberCardno(memberInfo.getMemberCardno());
        walletFeeRecord.setFeeType(type);
        walletFeeRecord.setAmount(memberInfo.getAmount());
        walletFeeRecord.setBalance(balance);
        walletFeeRecord.setRemarks(memberInfo.getRemarks());
        walletFeeRecord.setVoucher(memberInfo.getVoucherPhoto());
        walletFeeRecordDao.insert(walletFeeRecord);
        return walletFeeRecord;
    }
    
    /**
     * 修改密码
     * @param id
     * @param newPassword
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updatePassword(Long id, String newPassword) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", id);
        paramsMap.put("password", newPassword);
        MemberInfo memberInfo=this.get(id);
        messageNotifyService.sendSmsMessage(memberInfo, BusinessConstants.SMS_MODIFY_CARD, 
        		DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"),memberInfo.getMemberCardno());
        return dao.updatePassword(paramsMap);
    }

    public List<MemberInfo> findListByBatchId(Long batchId) {
        return dao.findListByBatchId(batchId);
    }

    /**
     * 绑定会员卡
     * 1、验证卡号或密码不能为空
     * 2、验证卡号是否存在
     * 3、验证密码是否正确
     * 4、验证会员卡是否已被绑定
     * 5、验证会员卡状态,未激活或正常状态的可进行绑定
     * 6、将未激活状态更新为正常状态
     * 7、添加会员卡历史记录--绑卡
     * 8、添加到客户端缓存，并获取token
     * @param memberCard
     */
    @Transactional(readOnly = false)
    public Map<String, Object> bindMemberCard(MemberCardRequest memberCard) {

        Map<String, Object> map = new HashMap<String, Object>();
        MemberInfo memberInfo = dao.queryMemberInfoByCardno(memberCard.getCardno(), null);
        //验证数据
        String result=validationByCard(memberInfo,memberCard);
        if(!BusinessConstants.SUCCESS.equals(result)){
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
            return map;
        }
        //获取终端类型
        ClientProperty cProperty = ApiUtil.getClient(memberCard.getClient());
        String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
        if (MemberState.INACTIVE == memberInfo.getState().intValue() ||MemberState.NORMAL==memberInfo.getState().intValue()) {
            // 未激活的状态,更新为正常状态
            memberInfo.setState(MemberState.NORMAL);
            memberInfo.setTermType(opTerm);
            dao.updateMemberState(memberInfo);
        }
        MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.BIND, memberCard.getClient(), null, null);
        memberInfoHisDao.insert(memberInfoHis);
        String token = this.bindCard(memberInfo.getMemberCardno(), opTerm);
        if (StringUtils.isNotBlank(token)) {
            TokenResp data = new TokenResp();
            data.setToken(token);
            ApiUtil.mapRespData(map, data, "绑卡会员卡成功", true);
            //发送短信通知用户
            messageNotifyService.sendSmsMessage(memberInfo, BusinessConstants.SMS_ACTIVATE_CARD, memberInfo.getMemberCardno());
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "绑定会员卡失败", false);
        }
        return map;
    }

    /**
     * 绑定会员卡时，验证卡信息数据
     * @param memberInfo
     * @return
     */
    public String validationByCard(MemberInfo memberInfo,MemberCardRequest memberCard){
        //验证基本数据
        if(StringUtils.isEmpty(memberCard.getCardno())){
            return "请输入会员卡号";
        }
        if(StringUtils.isEmpty(memberCard.getPassword())){
            return "请输入会员卡号密码";
        }
        if(memberInfo==null) {
            return "卡号或密码错误";
        }
        if(!memberInfo.getMemberPwd().equalsIgnoreCase(memberCard.getPassword())) {
            return "卡号或密码错误";
        }
        CustomerClientToken clientToken = CustomerClientUtils.getByAccount(memberInfo.getMemberCardno());
        // 验证是否已被其手机绑卡
        if(clientToken!=null){
            return "该会员卡已被其他手机绑定,不允许再次绑定";
        }
        // 判断卡状态是否为未激活或正常状态
        if(!(MemberState.INACTIVE == memberInfo.getState().intValue() || MemberState.NORMAL == memberInfo.getState().intValue())){
            return "会员卡非正常状态,不允许绑定";
        }
        return BusinessConstants.SUCCESS;
    }
    /** 解绑会员卡接口
     * 1、验证卡号或密码不能为空
     * 2、验证卡号是否存在
     * 3、验证密码是否正确
     * 4、token是否有效
     * 5、添加会员卡历史记录--解绑
     * 6、清理客户端缓存，token为空
     * @param memberCard
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> unbindMemberCard(MemberCardRequest memberCard) {
        Map<String, Object> map = new HashMap<String, Object>();
        MemberInfo memberInfo = dao.queryMemberInfoByCardno(memberCard.getCardno(), null);
        // 验证是否是正常操作途径
        if(memberInfo ==null){
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "解绑会员卡失败", false);
            return map;
        }
        // 验证解绑密码与卡号是否匹配
        if (!memberInfo.getMemberPwd().equalsIgnoreCase(memberCard.getPassword())) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "你输入的密码错误", false);
            return map;
        }
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(memberCard.getToken());
        if (clientToken != null) {
            MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.UNBIND, memberCard.getClient(), null, null);
            memberInfoHisDao.insert(memberInfoHis);
            CustomerClientUtils.clearCache(clientToken);
            clientToken.setLastDate(new Date());
            int count = customerClientTokenService.update(clientToken);
            if (count > 0) {
                TokenResp data = new TokenResp();
                data.setToken("");
                ApiUtil.mapRespData(map, data, "解绑会员卡成功", true);
            } else {
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "解绑会员卡失败", false);
            }
        } else {
            ApiUtil.tokenInvalidByCardNo(map);
            // 清理缓存信息
            CustomerClientUtils.clearCache(clientToken);
        }
        return map;
    }

    private String bindCard(String account, String opTerm) {
        CustomerClientToken clientToken = new CustomerClientToken();
        clientToken.setAccount(account);
        clientToken.setTermType(opTerm);
        clientToken.setToken(IdGen.uuid());
        clientToken.setLastDate(new Date());
        String token = customerClientTokenService.operaCustomerClientToken(clientToken);
        return token;
    }

    private MemberInfoHis createMemberInfoHis(MemberInfo memberInfo, int opreationType, String client, BigDecimal memberAmount, BigDecimal walletAmount) {
        MemberInfoHis memberInfoHis = new MemberInfoHis();
        memberInfoHis.setBatchId(memberInfo.getBatchId());
        if (StringUtils.isNotBlank(client)) {
            ClientProperty clientPropery = ApiUtil.getClient(client);
            memberInfoHis.setDeviceId(ApiUtil.getDeviceId(clientPropery));
        }
        memberInfoHis.setExpireDate(memberInfo.getExpireDate());
        memberInfoHis.setIssuingDate(memberInfo.getIssuingDate());
        memberInfoHis.setMemberBalance(memberInfo.getMemberBalance());
        memberInfoHis.setMemberCardno(memberInfo.getMemberCardno());
        memberInfoHis.setMemberLevel(memberInfo.getMemberLevel());
        memberInfoHis.setMemberMobile(memberInfo.getMemberMobile());
        memberInfoHis.setMemberName(memberInfo.getMemberName());
        memberInfoHis.setMemberPwd(memberInfo.getMemberPwd());
        memberInfoHis.setTermType(memberInfo.getTermType());
        memberInfoHis.setMemberAmount(memberAmount);
        memberInfoHis.setState(memberInfo.getState());
        memberInfoHis.setOperationType(opreationType);
        memberInfoHis.setOperationDate(new Date());
        memberInfoHis.setRemarks(memberInfo.getRemarks());
        memberInfoHis.setWalletBalance(memberInfo.getWalletBalance());
        memberInfoHis.setWalletAmount(walletAmount);
        memberInfoHis.setCreateBy(memberInfo.getCreateBy());
        memberInfoHis.setCreateDate(memberInfo.getCreateDate());
        memberInfoHis.setUpdateBy(memberInfo.getUpdateBy());
        memberInfoHis.setUpdateDate(memberInfo.getUpdateDate());
        return memberInfoHis;
    }

    /**
     * 修改卡密码
     * 1、验证token是否有效
     * 2、验证卡号是否存在
     * 3、验证旧密码是否正确
     * 4、生成新的token并更新客户端缓存记录
     * 5、添加会员卡历史记录--修改密码
     * @param cardPwdReq
     */
    @Transactional(readOnly = false)
    public Map<String, Object> updateCardPassword(CardPasswordRequest cardPwdReq) {
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(cardPwdReq.getToken());
        if (clientToken != null) {
            MemberInfo memberInfo = dao.queryMemberInfoByCardno(cardPwdReq.getCardno(), null);
            if (memberInfo != null) {
                if (cardPwdReq.getOldPassword().equals(memberInfo.getMemberPwd())) {
                    // 修改会员卡密码
                    this.updatePwd(map, clientToken, memberInfo, cardPwdReq.getNewPassword(), cardPwdReq.getClient());
                } else {
                    ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "旧密码不正确", false);
                }
            } else {
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "会员卡号不正确", false);
            }
        } else {
            ApiUtil.tokenInvalidByCardNo(map);
            // 清理缓存信息
            CustomerClientUtils.clearCache(clientToken);
        }
        return map;
    }

    /**
     * 修改密码
     * @param map
     * @param oldToken
     * @param memberInfo
     * @param newPassword
     * @param client
     */
    private void updatePwd(Map<String, Object> map, CustomerClientToken oldToken, MemberInfo memberInfo, String newPassword, String client) {
        boolean result = this.updatePassword(memberInfo.getId(), newPassword);
        if (result) {
            String newToken = IdGen.uuid();// token
            CustomerClientToken clientToken = new CustomerClientToken();
            clientToken.setAccount(memberInfo.getMemberCardno());
            clientToken.setTermType(oldToken.getTermType());
            clientToken.setToken(newToken);
            customerClientTokenService.update(clientToken);
            // 清除缓存
            CustomerClientToken customerClientToken = (CustomerClientToken) ClientCacheUtils.get(CustomerClientUtils.CLIENT_CACHE, CustomerClientUtils.CLIENT_CACHE_TOKEN_ + oldToken.getToken());
            if (customerClientToken != null) {
                CustomerClientUtils.clearCache(customerClientToken);
            }
            TokenResp data = new TokenResp();
            data.setToken(newToken);
            ApiUtil.mapRespData(map, data, "修改密码成功", true);
            // 添加会员卡历史记录--修改密码
            MemberInfoHis memberInfoHis = this.createMemberInfoHis(memberInfo, MemberHisOpreationType.UPDATE_PASSWORD, client, null, null);
            memberInfoHisDao.insert(memberInfoHis);
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "修改密码失败", false);
        }
    }

    /**
     * 获取会员卡信息
     * @param cardno
     * @param client
     * @param token
     * @return
    */
    public Map<String, Object> findMemberInfo(String cardno, String client, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(token);
        if (clientToken != null) {
            MemberInfo memberInfo = dao.queryMemberInfoByCardno(cardno, null);
            if (memberInfo != null) {
                MemberCardResp data = new MemberCardResp(memberInfo);
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("cardno", cardno);
                paramsMap.put("state", "0,1,2,4,5");// 未完成的订单
                int orderNumber = orderDao.countNotFinishOrderNumber(paramsMap);
                data.setOrderNumber(orderNumber);
                ApiUtil.mapRespData(map, data, "ok", true);
            } else {
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "ok", true);
            }
        } else {
            ApiUtil.tokenInvalidByCardNo(map);
            // 清理缓存信息
            CustomerClientUtils.clearCache(clientToken);
        }
        return map;
    }
    @Transactional(readOnly = false)
    public boolean activeMemberinfo(MemberInfo memberInfo,String ip){
    	boolean flag = this.changeMemberCard(memberInfo, MemberHisOpreationType.ACTIVE);
    	if (!flag) {
    		return flag;
    	}
    	memberInfo=dao.get(memberInfo.getId());
    	memberInfo.setIp(ip);
    	messageNotifyService.sendSmsMessage(memberInfo,BusinessConstants.SMS_ACTIVATE_CARD, memberInfo.getMemberCardno(),memberInfo.getMemberPwd());
    	return true;
    }
    /**
     * 会员的零钱按照年化3.68%进行计息
     * <p>
     * Description:会员的零钱按照年化3.68%进行计息<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月8日
     * @return
     * List<MemberInfo>
     */
    public List<MemberInfo> findListbyInterestAccrual(){
        return dao.findListbyInterestAccrual();
    }
    @Transactional(readOnly = false)
    public void updateMemberState(MemberInfo memberInfo){
    	dao.updateMemberState(memberInfo);
    }
}