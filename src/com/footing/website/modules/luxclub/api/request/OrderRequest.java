package com.footing.website.modules.luxclub.api.request;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.footing.website.common.utils.StringUtils;

public class OrderRequest extends BaseRequest implements Serializable{
	
 	private static final long serialVersionUID = 1L;
	private String cardno;
    private String reserveDate;		// 消费时间
	private String reserveNumber;		// 消费人数
	private String reserveCost;		// 费用范围
	private String reserveRequire;		// 其他要求
	private Long siteId;		// 场所编号
	private String  contactMobile;		// 联系电话	
	private Long orderId;
	private String amount;		// 消费金额
	private String voucherNames;		// 消费凭证
	private String cancelReason;//取消原因
	private String otherRequire;//其他要求
	private String realDate;		// 实际时间
	private String siteAddr;
	private Integer   status;
	private Integer state;
    private String walletAmount;
    private String walletRemarks;
    private String walletVoucher;
    
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getWalletVoucher() {
		return walletVoucher;
	}
	public void setWalletVoucher(String walletVoucher) {
		this.walletVoucher = walletVoucher;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSiteAddr() {
		return siteAddr;
	}
	public void setSiteAddr(String siteAddr) {
		this.siteAddr = siteAddr;
	}
	public String getRealDate() {
		return realDate;
	}
	public void setRealDate(String realDate) {
		this.realDate = realDate;
	}
	public String getOtherRequire() {
		return otherRequire;
	}
	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	} 
	@NotBlank(message="会员卡号不能为空")
	@Length(min=8, max=8, message="会员卡号必须是8位数字")
	@Pattern(regexp = "^[0-9a-zA-Z]{8,8}$", message = "非法会员卡号")
	public String getCardno() {
		return StringUtils.upperCase(cardno);
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getReserveNumber() {
		return reserveNumber;
	}
	public void setReserveNumber(String reserveNumber) {
		this.reserveNumber = reserveNumber;
	}
	public String getReserveCost() {
		return reserveCost;
	}
	public void setReserveCost(String reserveCost) {
		this.reserveCost = reserveCost;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getReserveRequire() {
		return reserveRequire;
	}
	public void setReserveRequire(String reserveRequire) {
		this.reserveRequire = reserveRequire;
	}
	 
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
        builder.append("BaseRequest [client=");
        builder.append(client);
        builder.append(", token=");
        builder.append(token);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", pageNumber=");
        builder.append(pageNumber);
        builder.append("]");
		return "OrderRequest [cardno=" + cardno + ", reserveDate=" + reserveDate + ", reserveNumber=" + reserveNumber
				+ ", reserveCost=" + reserveCost + ", reserveRequire=" + reserveRequire + ", siteId=" + siteId
				+ ", contactMobile=" + contactMobile + ", orderId=" + orderId + ", consumerMoney=" + amount
				+ "]"+builder;
	}
	 
	public String getVoucherNames() {
		return voucherNames;
	}
	public void setVoucherNames(String voucherNames) {
		this.voucherNames = voucherNames;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
