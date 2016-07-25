package com.footing.website.modules.luxclub.dao;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.OrderHis;

/**
 * 订单历史表DAO接口
 * @author yubin
 * @version 2016-03-16
 */
@MyBatisDao
public interface OrderHisDao extends CrudDao<OrderHis> {
	
}