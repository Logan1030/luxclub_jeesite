package com.footing.website.modules.luxclub.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.footing.website.common.persistence.DataEntity;

/**
 * 会员卡信息历史Entity
 * @author liuguoqing
 * @version 2016-03-16
 */
public class MemberInfoHis extends DataEntity<MemberInfoHis> {

    private static final long serialVersionUID = 1L;
    private String memberCardno; // 会员卡号
    private String memberPwd; // 会员密码
    private Integer memberLevel; // 级别
    private String memberName; // 卡主姓名
    private String memberMobile; // 卡主电话
    private BigDecimal memberBalance; // 会员卡余额
    private BigDecimal memberAmount; // 会员卡金额
    private Integer state; // 卡状态
    private Long batchId; // 批次编号
    private Date issuingDate; // 发放日期
    private Date expireDate; // 过期日期
    private String deviceId; // 设备ID
    private Integer operationType; // 操作类型
    private Date operationDate; // 操作日期
    private BigDecimal walletBalance; // 零钱包余额
    private BigDecimal walletAmount; // 零钱包金额
    private BigDecimal walletProfit; // 零钱包累计收益
    private BigDecimal walletLastProfit; // 零钱包昨日收益
    
    private Date beginOperationDate; // 开始 操作日期
    private Date endOperationDate; // 结束 操作日期
    private String termType;//终端类型

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public MemberInfoHis() {
        super();
    }

    public MemberInfoHis(Long id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "会员卡号长度必须介于 0 和 64 之间")
    public String getMemberCardno() {
        return memberCardno;
    }

    public void setMemberCardno(String memberCardno) {
        this.memberCardno = memberCardno;
    }

    @Length(min = 0, max = 64, message = "会员密码长度必须介于 0 和 64 之间")
    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    @Length(min = 0, max = 64, message = "卡主姓名长度必须介于 0 和 64 之间")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Length(min = 0, max = 13, message = "卡主电话长度必须介于 0 和 13 之间")
    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    public BigDecimal getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(BigDecimal memberAmount) {
        if(null == memberAmount){
            this.memberAmount = new BigDecimal("0.00");
        }else{
            this.memberAmount = memberAmount;
        }
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(Date issuingDate) {
        this.issuingDate = issuingDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Length(min = 0, max = 128, message = "设备ID长度必须介于 0 和 128 之间")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public BigDecimal getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(BigDecimal walletAmount) {
        if(null == walletAmount){
            this.walletAmount = new BigDecimal("0.00");
        }else{
            this.walletAmount = walletAmount;
        }
    }

    public Date getBeginOperationDate() {
        return beginOperationDate;
    }

    public void setBeginOperationDate(Date beginOperationDate) {
        this.beginOperationDate = beginOperationDate;
    }

    public Date getEndOperationDate() {
        return endOperationDate;
    }

    public void setEndOperationDate(Date endOperationDate) {
        this.endOperationDate = endOperationDate;
    }

    public BigDecimal getWalletProfit() {
        return walletProfit;
    }

    public void setWalletProfit(BigDecimal walletProfit) {
        this.walletProfit = walletProfit;
    }

    public BigDecimal getWalletLastProfit() {
        return walletLastProfit;
    }

    public void setWalletLastProfit(BigDecimal walletLastProfit) {
        this.walletLastProfit = walletLastProfit;
    }

}