package com.footing.website.modules.luxclub.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.footing.website.common.persistence.DataEntity;

/**
 * 客户端缓存信息Entity
 */
public class CustomerClientToken extends DataEntity<CustomerClientToken> {
	
	private static final long serialVersionUID = 1L;
	private String account;		// 账号(会员卡号、客户经理ID)
	private String token;		// 令牌
	private String termType;    //终端类型
	private Date lastDate;		// 最后一次更改时间
	
	public CustomerClientToken() {
		super();
	}

	public CustomerClientToken(Long id){
		super(id);
	}
	
	@NotNull(message="账号不能为空")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Length(min=0, max=500, message="令牌长度必须介于 0 和 500 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	
	
}