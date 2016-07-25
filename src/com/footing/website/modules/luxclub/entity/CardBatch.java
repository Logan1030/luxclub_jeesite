package com.footing.website.modules.luxclub.entity;

import org.hibernate.validator.constraints.Length;

import com.footing.website.common.persistence.DataEntity;

/**
 * 会员卡批次生成Entity
 * @author liuguoqing
 * @version 2016-03-15
 */
public class CardBatch extends DataEntity<CardBatch> {
	
	private static final long serialVersionUID = 1L;
	private String batichName;		// 批次名称
	private Long createCount;		// 生成数量
	private Integer cardType;		// 生成卡类型(普通 1、白金 2、钻石3)
	
	public CardBatch() {
		super();
	}

	public CardBatch(Long id){
		super(id);
	}

	@Length(min=0, max=64, message="批次名称长度必须介于 0 和 64 之间")
	public String getBatichName() {
		return batichName;
	}

	public void setBatichName(String batichName) {
		this.batichName = batichName;
	}
	
	public Long getCreateCount() {
		return createCount;
	}

	public void setCreateCount(Long createCount) {
		this.createCount = createCount;
	}
	
	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	
}