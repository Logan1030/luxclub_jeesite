package com.footing.website.modules.luxclub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.entity.OrderHis;
import com.footing.website.modules.luxclub.dao.OrderHisDao;

/**
 * 订单历史表Service
 * @author yubin
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class OrderHisService extends CrudService<OrderHisDao, OrderHis> {

	public OrderHis get(Long id) {
		return super.get(id);
	}
	
	public List<OrderHis> findList(OrderHis orderHis) {
		return super.findList(orderHis);
	}
	
	public Page<OrderHis> findPage(Page<OrderHis> page, OrderHis orderHis) {
		return super.findPage(page, orderHis);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderHis orderHis) {
		super.save(orderHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderHis orderHis) {
		super.delete(orderHis);
	}
	
}