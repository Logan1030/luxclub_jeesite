package com.footing.website.modules.luxclub.dao;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.SmsRecord;

/**
 * 短信DAO接口
 * @author yubin
 * @version 2016-07-21
 */
@MyBatisDao
public interface SmsRecordDao extends CrudDao<SmsRecord> {
	
}