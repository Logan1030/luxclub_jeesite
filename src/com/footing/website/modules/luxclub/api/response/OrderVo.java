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

public class OrderVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	private long orderId;
	private String orderCode;
	private String orderState;
	private long siteId;
	private String siteName;
	private String siteAddr;
	private Date createDate;
	private String reserveNumber;
	private String contactMobile;
	private BigDecimal consumerMoney;
	private String  cardno;
	private String payModel;
	private Date realDate;
	private String siteImageUrl;
	private String remarks;
	private String walletAmount;
    private String walletRemarks;
	private String cancelReason;
	private String otherRequire;
	private String managerMobile;
	@JsonIgnore
	private String voucher;
	
	public String[] getWalletVoucherUrl() {
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
	public String getManagerMobile() {
		return managerMobile;
	}
	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}
	public String getOtherRequire() {
		return otherRequire==null?"":otherRequire;
	}
	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}
	public String getWalletRemarks() {
		return walletRemarks;
	}
	public void setWalletRemarks(String walletRemarks) {
		this.walletRemarks = walletRemarks;
	}
	public String getCancelReason() {
		return cancelReason==null?"":cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}
	public String getSiteImageUrl() {
		if(getSiteId()==-1){
			return Global.webUrl()+BusinessConstants.URL_VIEW;
		}
		return Global.webUrl()+siteImageUrl;
	}
	public void setSiteImageUrl(String siteImageUrl) {
		this.siteImageUrl = siteImageUrl;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
		if(this.orderState.equals(BusinessConstants.ORDER_STATE_SEND)
		  ||this.orderState.equals(BusinessConstants.ORDER_STATE_COMMIT)){
		   return "预约中";	
		}else if(this.orderState.equals(BusinessConstants.ORDER_STATE_CONFIRM)
		  ||this.orderState.equals(BusinessConstants.ORDER_STATE_UNPAY)		){
		   return "已预约";
		}else if(this.orderState.equals(BusinessConstants.ORDER_STATE_PAY)){
		   return "已结账";
	    }else if(this.orderState.equals(BusinessConstants.ORDER_STATE_CANCLE)){
		   return "已取消";
		}
		return DictUtils.getDictLabel(getOrderState(), "order_state","1");
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
		return DictUtils.getDictLabel(this.reserveNumber, "reserve_number", "1");
	}
	public void setReserveNumber(String reserveNumber) {
		this.reserveNumber = reserveNumber;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile (String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public BigDecimal getConsumerMoney() {
		return consumerMoney==null?new BigDecimal(0):consumerMoney;
	}
	public void setConsumerMoney(BigDecimal consumerMoney) {
		this.consumerMoney = consumerMoney;
	}
	public String getCardno() {
		return StringUtils.upperCase(cardno);
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getPayModel() {
		return DictUtils.getDictLabel(this.payModel, "pay_way", "1");
	}
	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealDate() {
		return realDate;
	}
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}
}
