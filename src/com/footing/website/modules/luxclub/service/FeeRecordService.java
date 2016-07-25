package com.footing.website.modules.luxclub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.api.request.FeeRecordRequest;
import com.footing.website.modules.luxclub.api.request.PageBean;
import com.footing.website.modules.luxclub.api.response.FeeRecordResp;
import com.footing.website.modules.luxclub.dao.FeeRecordDao;
import com.footing.website.modules.luxclub.entity.FeeRecord;

/**
 * 费用记录Service
 * @author liuguoqing
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class FeeRecordService extends CrudService<FeeRecordDao, FeeRecord> {

	public FeeRecord get(Long id) {
		return super.get(id);
	}
	
	public List<FeeRecord> findList(FeeRecord feeRecord) {
		return super.findList(feeRecord);
	}
	
	public Page<FeeRecord> findPage(Page<FeeRecord> page, FeeRecord feeRecord) {
		return super.findPage(page, feeRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(FeeRecord feeRecord) {
		super.save(feeRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeeRecord feeRecord) {
		super.delete(feeRecord);
	}
	
	/**
	 * 消费/充值记录分页列表(API接口)
	 * @return
	 */
	public List<FeeRecordResp> findFeeRecordPageList(FeeRecordRequest feeRecordReq){
	    Map<String, Object> map = new HashMap<String,Object>();
        PageBean pageBean = new PageBean(feeRecordReq.getPageNumber(), feeRecordReq.getPageSize());
        map.put("startNumber", pageBean.getStartNumber());
        map.put("endNumber", pageBean.getEndNumber());
        map.put("cardno", feeRecordReq.getCardno());
        if(0 == feeRecordReq.getFeeType().intValue()){//消费
            map.put("feeType", "0,2");//消费 + 扣费
        }else{
            map.put("feeType", feeRecordReq.getFeeType());//充值
        }
        return dao.findFeeRecordPageList(map);
	}

	/**
	 * 消费/充值记录详细信息(API接口)
	 * @return
	 */
	public FeeRecordResp feeRecordDetails(Long feeId){
	    FeeRecordResp feeRecordResp = dao.feeRecordDetails(feeId);
	    return feeRecordResp;
	}
	
	
}