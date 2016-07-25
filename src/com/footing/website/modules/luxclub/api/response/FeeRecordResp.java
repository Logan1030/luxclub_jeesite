package com.footing.website.modules.luxclub.api.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.footing.website.modules.sys.utils.DictUtils;

/**
 * 消费/充值记录接口返回对象
 * @author liuguoqing
 * @version 2016-03-23
 */
public class FeeRecordResp implements Serializable{

    private static final long serialVersionUID = 2751231947975569925L;
    
    private Long feeId; // 记录编号
    private String cardno; // 会员卡号
    private Long orderId; // 订单ID
    private String orderCode; // 订单编号
    private Long siteId; // 场所编号
    private String siteName; // 场所名称
    private Date createDate; // 记录时间
    private Integer feeType; // 费用类型
    private String feeMoney; // 金额
    private String payModel; // 支付方式
    private Date orderTime; // 订单时间
    
    
    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public String getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(String feeMoney) {
        this.feeMoney = feeMoney;
    }

    public String getPayModel() {
        return DictUtils.getDictLabel(payModel, "luxclub_pay_way", "");
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

}