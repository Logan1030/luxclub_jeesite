package com.footing.website.modules.luxclub.entity;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.footing.website.common.persistence.DataEntity;

/**
 * 费用记录Entity
 * @author liuguoqing
 * @version 2016-03-16
 */
public class FeeRecord extends DataEntity<FeeRecord> {
	
	private static final long serialVersionUID = 1L;
	private String memberCardno;		// 会员卡号
	private Integer feeType;		// 费用类型
	private BigDecimal feeMoney;		// 消费金额
	private BigDecimal balance;		// 余额
	private String voucher;         //凭证
	private String orderCode;		// 订单编号
	private Long siteId;		// 场所编号
	
    private SiteInfo siteInfo;      // 场所对象
    
    private String[] voucherArray;
	
	public FeeRecord() {
		super();
	}

	public FeeRecord(Long id){
		super(id);
	}

	@Length(min=0, max=64, message="会员卡号长度必须介于 0 和 64 之间")
	public String getMemberCardno() {
		return memberCardno;
	}

	public void setMemberCardno(String memberCardno) {
		this.memberCardno = memberCardno;
	}
	
	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	
	public BigDecimal getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
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

    public SiteInfo getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(SiteInfo siteInfo) {
        this.siteInfo = siteInfo;
    }

    public String[] getVoucherArray() {
        if(StringUtils.isNotBlank(getVoucher())){
            String voucher = getVoucher().replaceFirst("[|]", "");
            voucherArray = voucher.split("[|]");
        }
        return voucherArray;
    }

    public void setVoucherArray(String[] voucherArray) {
        this.voucherArray = voucherArray;
    }

}