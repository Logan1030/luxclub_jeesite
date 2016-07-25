package com.footing.website.modules.luxclub.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.footing.website.common.persistence.DataEntity;
import com.footing.website.common.utils.excel.annotation.ExcelField;

/**
 * 会员信息Entity
 * @author liuguoqing
 * @version 2016-03-16
 */
public class MemberInfo extends DataEntity<MemberInfo> {

    private static final long serialVersionUID = 1L;
    private String memberCardno; // 会员卡号
    private String memberPwd; // 会员密码
    private Integer memberLevel; // 级别
    private String memberName; // 卡主姓名
    private String memberMobile; // 卡主电话
    private BigDecimal memberBalance; // 余额
    private BigDecimal obligation; // 待付款
    private Date issuingDate; // 发放日期
    private Date expireDate; // 过期日期
    private Integer state; // 状态
    private Date beginIssuingDate; // 开始 发放日期
    private Date endIssuingDate; // 结束 发放日期
    private Date beginExpireDate; // 开始 过期日期
    private Date endExpireDate; // 结束 过期日期
    private Long batchId; // 批次编号
    private BigDecimal amount; // 充值、扣费金额
    private String voucherPhoto; //凭证图片
    private BigDecimal walletBalance; // 零钱包余额
    private BigDecimal walletPrepay; // 零钱包待付款
    private BigDecimal walletProfit; // 零钱包累计收益
    private BigDecimal walletLastProfit; // 零钱包昨日收益
    private String termType; // 操作终端
    private String ip;
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public MemberInfo() {
        super();
    }

    public MemberInfo(Long id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "会员卡号长度必须介于 0 和 64 之间")
    @ExcelField(title="卡号", align=2, sort=20)
    public String getMemberCardno() {
        return memberCardno;
    }

    public void setMemberCardno(String memberCardno) {
        this.memberCardno = memberCardno;
    }

    @Length(min = 0, max = 64, message = "会员密码长度必须介于 0 和 64 之间")
    @ExcelField(title="密码", align=2, sort=40)
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

    public BigDecimal getObligation() {
    	if(obligation==null){
    		return new BigDecimal(0);
    	}
        return obligation;
    }

    public void setObligation(BigDecimal obligation) {
        this.obligation = obligation;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(Date issuingDate) {
        this.issuingDate = issuingDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBeginIssuingDate() {
        return beginIssuingDate;
    }

    public void setBeginIssuingDate(Date beginIssuingDate) {
        this.beginIssuingDate = beginIssuingDate;
    }

    public Date getEndIssuingDate() {
        return endIssuingDate;
    }

    public void setEndIssuingDate(Date endIssuingDate) {
        this.endIssuingDate = endIssuingDate;
    }

    public Date getBeginExpireDate() {
        return beginExpireDate;
    }

    public void setBeginExpireDate(Date beginExpireDate) {
        this.beginExpireDate = beginExpireDate;
    }

    public Date getEndExpireDate() {
        return endExpireDate;
    }

    public void setEndExpireDate(Date endExpireDate) {
        this.endExpireDate = endExpireDate;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVoucherPhoto() {
        return voucherPhoto;
    }

    public void setVoucherPhoto(String voucherPhoto) {
        this.voucherPhoto = voucherPhoto;
    }

    public BigDecimal getWalletBalance() {
    	if(walletBalance==null){
    		return new BigDecimal(0);
    	}
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public BigDecimal getWalletPrepay() {
    	if(walletBalance==null){
    	   return new BigDecimal(0);
    	}
        return walletPrepay;
    }

    public void setWalletPrepay(BigDecimal walletPrepay) {
        this.walletPrepay = walletPrepay;
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

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }
}