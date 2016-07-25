package com.footing.website.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.footing.website.common.persistence.DataEntity;

/**
 * 流水号Entity
 * @author yubin
 * @version 2016-07-23
 */
public class SerialNumber extends DataEntity<SerialNumber> {
	
	private static final long serialVersionUID = 1L;
	private String appType;		// app_type
	private Date serialDate;		// serial_date
	private Integer number;		// number
	
	public SerialNumber() {
		super();
	}

	public SerialNumber(Long id){
		super(id);
	}

	@Length(min=0, max=18, message="app_type长度必须介于 0 和 18 之间")
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSerialDate() {
		return serialDate;
	}

	public void setSerialDate(Date serialDate) {
		this.serialDate = serialDate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	 
	
    
	
}