package com.footing.website.modules.luxclub.entity;

import org.hibernate.validator.constraints.Length;

import com.footing.website.common.persistence.DataEntity;

/**
 * 短信Entity
 * @author yubin
 * @version 2016-07-21
 */
public class SmsRecord extends DataEntity<SmsRecord> {
	
	private static final long serialVersionUID = 1L;
	private String mobile;		// 手机号码
	private String content;		// 短信内容
	private String sendstatus;		// 发送状态 1已发送
	private String mtmsgid;		// mtmsgid
	private String ip;		// ip
	private String templatetype;		// templateType
	private String mtstat;//返回状态
	
	public String getMtstat() {
		return mtstat;
	}

	public void setMtstat(String mtstat) {
		this.mtstat = mtstat;
	}

	public SmsRecord() {
		super();
	}

	public SmsRecord(Long id){
		super(id);
	}

	@Length(min=0, max=13, message="手机号码长度必须介于 0 和 13 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=255, message="短信内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="发送状态 1已发送长度必须介于 0 和 1 之间")
	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}
	
	@Length(min=0, max=32, message="mtmsgid长度必须介于 0 和 32 之间")
	public String getMtmsgid() {
		return mtmsgid;
	}

	public void setMtmsgid(String mtmsgid) {
		this.mtmsgid = mtmsgid;
	}
	
	@Length(min=0, max=32, message="ip长度必须介于 0 和 32 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=1, message="templateType长度必须介于 0 和 1 之间")
	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}
	
}