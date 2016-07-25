package com.footing.website.modules.luxclub.api.response;

import com.footing.website.common.utils.DateUtils;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.sys.utils.DictUtils;

/**
 * 会员卡信息返回参数
 * @author liuguoqing
 *
 */
public class MemberCardResp {
    /** 会员卡号*/
    private String memberCardno;
    /** 级别(1 普通、2白金、3 钻石)*/
    private Integer memberLevel;
    /** 级别名称*/
    private String memberLevelName;
    /** 卡主姓名*/
    private String memberName;
    /** 卡主电话*/
    private String memberMobile;
    /** 余额*/
    private String memberBalance;
    /** 待付款*/
    private String obligation;
    /** 零钱包余额 */
    private String walletBalance;
    /** 零钱包待付款 */
    private String walletPrepay;
    /** 零钱包累计收益 */
    private String walletProfit;
    /** 零钱包昨日收益 */
    private String walletLastProfit;
    /** 年化利率 */
    private String annualizedRates;
    /** 状态(0 未激活、1 正常、2 欠费、3 冻结、4 注销)*/
    private Integer state;
    /** 状态名称*/
    private String stateName;
    /** 发放日期*/
    private String issuingDate;
    /** 过期日期*/
    private String expireDate;

    /** 未完成订单数量*/
    private Integer orderNumber;
    
    public MemberCardResp(){
    }
    
    public MemberCardResp(MemberInfo memberInfo){
        this.memberBalance = memberInfo.getMemberBalance().toString();
        this.memberCardno = memberInfo.getMemberCardno();
        this.memberLevel = memberInfo.getMemberLevel();
        this.memberLevelName = DictUtils.getDictLabel(memberInfo.getMemberLevel()+"", "luxclub_card_type", "");
        this.memberMobile = memberInfo.getMemberMobile();
        this.memberName = memberInfo.getMemberName();
        this.obligation = memberInfo.getObligation().toString();
        this.walletBalance = memberInfo.getWalletBalance().toString();
        this.walletPrepay = memberInfo.getWalletPrepay().toString();
        this.walletProfit = memberInfo.getWalletProfit().toString();
        this.walletLastProfit = memberInfo.getWalletLastProfit().toString();
        this.state = memberInfo.getState();
        this.stateName = DictUtils.getDictLabel(memberInfo.getState()+"", "luxclub_member_state", "");
        if(memberInfo.getExpireDate()!=null){
            this.expireDate = DateUtils.formatDate(memberInfo.getExpireDate(), "yyyy-MM-dd");
        }
        if(memberInfo.getIssuingDate()!=null){
            this.issuingDate = DateUtils.formatDate(memberInfo.getIssuingDate(), "yyyy-MM-dd");
        }
        this.annualizedRates = String.format("%.2f",BusinessConstants.YEAR_INTEREST)+"%";
    }

    public String getMemberCardno() {
        return memberCardno;
    }

    public void setMemberCardno(String memberCardno) {
        this.memberCardno = memberCardno;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(String memberBalance) {
        this.memberBalance = memberBalance;
    }

    public String getObligation() {
        return obligation;
    }

    public void setObligation(String obligation) {
        this.obligation = obligation;
    }

    public String getWalletProfit() {
        return walletProfit;
    }

    public void setWalletProfit(String walletProfit) {
        this.walletProfit = walletProfit;
    }

    public String getWalletLastProfit() {
        return walletLastProfit;
    }

    public void setWalletLastProfit(String walletLastProfit) {
        this.walletLastProfit = walletLastProfit;
    }

    public String getAnnualizedRates() {
        return annualizedRates;
    }

    public void setAnnualizedRates(String annualizedRates) {
        this.annualizedRates = annualizedRates;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(String issuingDate) {
        this.issuingDate = issuingDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getWalletPrepay() {
        return walletPrepay;
    }

    public void setWalletPrepay(String walletPrepay) {
        this.walletPrepay = walletPrepay;
    }

}
