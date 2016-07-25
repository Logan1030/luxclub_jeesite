package com.footing.website.modules.luxclub.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderCnd implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
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
	
}
