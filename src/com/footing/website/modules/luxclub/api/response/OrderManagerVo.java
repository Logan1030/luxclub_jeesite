package com.footing.website.modules.luxclub.api.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.footing.website.common.config.Global;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.sys.utils.DictUtils;

public class OrderManagerVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long orderId;
	private String orderCode;
	private String orderState;
	private long siteId;
	private String siteName;
	private String siteAddr;
	private Date createDate;
	private int reserveNumberType;
	private String contactMobile;
	private String consumerMoney;
	private Date realDate;
	@JsonIgnore
	private String voucher;
	private String cancelReason;
	private String  otherRequire;
    private String walletAmount;
    private String walletRemarks;
    @JsonIgnore
    private String walletVoucher;
    private String cardno;
    private String siteImageUrl;
    public String getSiteImageUrl() {
		if(getSiteId()==-1){
			return Global.webUrl()+BusinessConstants.URL_VIEW;
		}
		return Global.webUrl()+siteImageUrl;
	}
	public void setSiteImageUrl(String siteImageUrl) {
		this.siteImageUrl = siteImageUrl;
	}
	
	public String getWalletVoucher() {
		return walletVoucher;
	}
	public void setWalletVoucher(String walletVoucher) {
		this.walletVoucher = walletVoucher;
	}
	public String[] getWalletVoucherUrl() {
		String temp[]=null;
		int i=0;
		if(StringUtils.isNotEmpty(this.walletVoucher)){
			temp=new String[this.walletVoucher.split("[|]").length-1];
			for(String voucher:this.walletVoucher.split("[|]")){
	    		if(!StringUtils.isEmpty(voucher)){
		    		 temp[i++]=Global.webUrl()+voucher;
	    		}
	    	}
		}
		return temp;
	}
	public String[] getVoucherUrl() {
		String temp[]=null;
		int i=0;
		if(StringUtils.isNotEmpty(this.voucher)){
			temp=new String[this.voucher.split("[|]").length-1];
			for(String voucher:this.voucher.split("[|]")){
	    		if(!StringUtils.isEmpty(voucher)){
		    		 temp[i++]=Global.webUrl()+voucher;
	    		}
	    	}
		}
		return temp;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}
	public String getWalletRemarks() {
		return walletRemarks;
	}
	public void setWalletRemarks(String walletRemarks) {
		this.walletRemarks = walletRemarks;
	}
	public int getReserveNumberType() {
		return reserveNumberType;
	}
	public void setReserveNumberType(int reserveNumberType) {
		this.reserveNumberType = reserveNumberType;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getOtherRequire() {
		return otherRequire;
	}
	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateName() {
		if(this.orderState.equals(BusinessConstants.ORDER_STATE_SEND)){
		   return "待确认";	
		}else if(this.orderState.equals(BusinessConstants.ORDER_STATE_UNPAY)){
		   return "待结账";
		}else if(this.orderState.equals(BusinessConstants.ORDER_STATE_PAY)){
		   return "已结账";
		}else if(this.orderState.equals(BusinessConstants.ORDER_STATE_CANCLE)){
		   return "订单取消";
		}else{
		   return "已确认";	
		}
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		if(getSiteId()==-1){
			return BusinessConstants.PICTRUE_NAME;
		}
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteAddr() {
		return siteAddr;
	}
	public void setSiteAddr(String siteAddr) {
		this.siteAddr = siteAddr;
	}
	 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getReserveNumber() {
		return DictUtils.getDictLabel(String.valueOf(this.reserveNumberType), "reserve_number", "1");
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile (String contactMobile) {
		this.contactMobile = contactMobile;
	}
	 
	public String getConsumerMoney() {
		return consumerMoney;
	}
	public void setConsumerMoney(String consumerMoney) {
		this.consumerMoney = consumerMoney;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealDate() {
		return realDate;
	}
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}
	
	 
}
