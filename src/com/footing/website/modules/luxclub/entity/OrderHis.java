package com.footing.website.modules.luxclub.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.footing.website.common.persistence.DataEntity;

/**
 * 订单历史表Entity
 * @author yubin
 * @version 2016-03-16
 */
public class OrderHis extends DataEntity<OrderHis> {
	
	private static final long serialVersionUID = 1L;
	private String orderCode;		// 订单编号
	private Date reserveDate;		// 消费时间
	private String reserveNumber;		// 消费人数(1-5人，5-10人，10-20人，20人以上)
	private String reserveCost;		// 费用范围
	private String reserveRequire;		// 其他要求
	private Long reserveSiteId;		// 场所编号
	private String state;		// 订单状态( 0 提交、1 派单、2 确认、3 取消、4 异常（比如账户余额不足）、5 待结账、6 已结账。)
	private BigDecimal consumerMoney;		// 消费金额
	private String consumerVouchers;		// 消费凭证
	private String memberCardno;		// 会员编号
	private String contactMobile;		// 联系电话
	private Date sendDate;		// 派单时间
	private String deviceId;		// 设备ID
	private Long businessId;		// 业务员编号
	private Date realDate;		// 实际时间
	private Integer realNumber;		// 实际人数
	private String realRequire;		// 实际要求
	private String businessName; //客户经理
	private Date beginReserveDate;		// 开始 消费时间
	private Date endReserveDate;		// 结束 消费时间
	
	
	
	public Date getBeginReserveDate() {
		return beginReserveDate;
	}

	public void setBeginReserveDate(Date beginReserveDate) {
		this.beginReserveDate = beginReserveDate;
	}

	public Date getEndReserveDate() {
		return endReserveDate;
	}

	public void setEndReserveDate(Date endReserveDate) {
		this.endReserveDate = endReserveDate;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public OrderHis() {
		super();
	}

	public OrderHis(Long id){
		super(id);
	}

	@Length(min=0, max=16, message="订单编号长度必须介于 0 和 16 之间")
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	
	@Length(min=0, max=1, message="消费人数(1-5人，5-10人，10-20人，20人以上)长度必须介于 0 和 1 之间")
	public String getReserveNumber() {
		return reserveNumber;
	}

	public void setReserveNumber(String reserveNumber) {
		this.reserveNumber = reserveNumber;
	}
	
	@Length(min=0, max=1, message="费用范围长度必须介于 0 和 1 之间")
	public String getReserveCost() {
		return reserveCost;
	}

	public void setReserveCost(String reserveCost) {
		this.reserveCost = reserveCost;
	}
	
	@Length(min=0, max=255, message="其他要求长度必须介于 0 和 255 之间")
	public String getReserveRequire() {
		return reserveRequire;
	}

	public void setReserveRequire(String reserveRequire) {
		this.reserveRequire = reserveRequire;
	}
	
	public Long getReserveSiteId() {
		return reserveSiteId;
	}

	public void setReserveSiteId(Long reserveSiteId) {
		this.reserveSiteId = reserveSiteId;
	}
	
	@Length(min=0, max=1, message="订单状态( 0 提交、1 派单、2 确认、3 取消、4 异常（比如账户余额不足）、5 待结账、6 已结账。)长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public BigDecimal getConsumerMoney() {
		return consumerMoney;
	}

	public void setConsumerMoney(BigDecimal consumerMoney) {
		this.consumerMoney = consumerMoney;
	}
	
	@Length(min=0, max=255, message="消费凭证长度必须介于 0 和 255 之间")
	public String getConsumerVouchers() {
		return consumerVouchers;
	}

	public void setConsumerVouchers(String consumerVouchers) {
		this.consumerVouchers = consumerVouchers;
	} 
	
	public String getMemberCardno() {
		return memberCardno;
	}

	public void setMemberCardno(String memberCardno) {
		this.memberCardno = memberCardno;
	}

	@Length(min=0, max=16, message="联系电话长度必须介于 0 和 16 之间")
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@Length(min=0, max=128, message="设备ID长度必须介于 0 和 128 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}
	
	public Integer getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(Integer realNumber) {
		this.realNumber = realNumber;
	}
	
	@Length(min=0, max=255, message="实际要求长度必须介于 0 和 255 之间")
	public String getRealRequire() {
		return realRequire;
	}

	public void setRealRequire(String realRequire) {
		this.realRequire = realRequire;
	}
	
}