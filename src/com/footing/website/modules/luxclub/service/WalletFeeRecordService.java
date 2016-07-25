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
import com.footing.website.modules.luxclub.common.WalletFeeType;
import com.footing.website.modules.luxclub.dao.WalletFeeRecordDao;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;

/**
 * 零钱包费用记录Service
 */
@Service
@Transactional(readOnly = true)
public class WalletFeeRecordService extends CrudService<WalletFeeRecordDao, WalletFeeRecord> {

	public WalletFeeRecord get(Long id) {
		return super.get(id);
	}
	
	public List<WalletFeeRecord> findList(WalletFeeRecord feeRecord) {
		return super.findList(feeRecord);
	}
	
	public Page<WalletFeeRecord> findPage(Page<WalletFeeRecord> page, WalletFeeRecord feeRecord) {
		return super.findPage(page, feeRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WalletFeeRecord feeRecord) {
		super.save(feeRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(WalletFeeRecord feeRecord) {
		super.delete(feeRecord);
	}
	
	/**
	 * 消费/充值记录分页列表(API接口)
	 * @return
	 */
	public List<FeeRecordResp> findFeePageList(FeeRecordRequest feeRecordReq){
	    Map<String, Object> map = new HashMap<String,Object>();
        PageBean pageBean = new PageBean(feeRecordReq.getPageNumber(), feeRecordReq.getPageSize());
        map.put("startNumber", pageBean.getStartNumber());
        map.put("endNumber", pageBean.getEndNumber());
        map.put("cardno", feeRecordReq.getCardno());
        if(0 == feeRecordReq.getFeeType().intValue()){//消费
            map.put("feeType", "0,2");//消费 + 扣费
        }else if(2 == feeRecordReq.getFeeType().intValue()){
            map.put("feeType", WalletFeeType.INTREEST_ACCURAL);//计息
        }else{
            map.put("feeType", feeRecordReq.getFeeType());//充值
        }
        return dao.findFeePageList(map);
	}

	/**
	 * 消费/充值记录详细信息(API接口)
	 * @return
	 */
	public FeeRecordResp getFeeDetails(Long feeId){
	    FeeRecordResp feeRecordResp = dao.getFeeDetails(feeId);
	    return feeRecordResp;
	}
}