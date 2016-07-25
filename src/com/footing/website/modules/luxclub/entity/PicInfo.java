package com.footing.website.modules.luxclub.entity;

import org.hibernate.validator.constraints.Length;
 
import com.footing.website.common.persistence.DataEntity;

/**
 * 会员图片信息表Entity
 * @author yubin
 * @version 2016-03-16
 */
public class PicInfo extends DataEntity<PicInfo> {
	
	private static final long serialVersionUID = 1L;
	private String picAddr;		// 图片地址
	private String businessType;		// 图片类型(1 充值凭证类型 2 消费凭证类型3 场所图片)
	private Long businessId;		// 业务id
	
	public PicInfo() {
		super();
	}

	public PicInfo(Long id){
		super(id);
	}

	@Length(min=0, max=255, message="图片地址长度必须介于 0 和 255 之间")
	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	
	@Length(min=0, max=1, message="图片类型(1 充值凭证类型 2 消费凭证类型3 场所图片)长度必须介于 0 和 1 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
}