package com.footing.website.modules.luxclub.dao;

import java.util.List;
import java.util.Map;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.api.response.FeeRecordResp;
import com.footing.website.modules.luxclub.entity.FeeRecord;

/**
 * 费用记录DAO接口
 * @author liuguoqing
 * @version 2016-03-16
 */
@MyBatisDao
public interface FeeRecordDao extends CrudDao<FeeRecord> {
    
    /**
     * 消费/充值记录分页列表(API接口)
     * @param paramsMap
     * @return
     */
    public List<FeeRecordResp> findFeeRecordPageList(Map<String,Object> paramsMap);
    
    /**
     * 消费/充值记录详细信息(API接口)
     * @param feeId
     * @return
     */
    public FeeRecordResp feeRecordDetails(Long feeId);
	
}