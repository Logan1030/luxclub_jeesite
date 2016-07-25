package com.footing.website.modules.luxclub.dao;

import java.util.List;
import java.util.Map;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.api.response.FeeRecordResp;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;

/**
 * 零钱包费用记录DAO接口
 * @author liuguoqing
 */
@MyBatisDao
public interface WalletFeeRecordDao extends CrudDao<WalletFeeRecord> {
    
    /**
     * 零钱包消费/充值记录分页列表(API接口)
     * @param paramsMap
     * @return
     */
    public List<FeeRecordResp> findFeePageList(Map<String,Object> paramsMap);
    
    /**
     * 零钱包消费/充值记录详细信息(API接口)
     * @param feeId
     * @return
     */
    public FeeRecordResp getFeeDetails(Long feeId);
	
}