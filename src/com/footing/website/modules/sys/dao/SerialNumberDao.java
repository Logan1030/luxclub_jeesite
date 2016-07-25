package com.footing.website.modules.sys.dao;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.sys.entity.SerialNumber;

/**
 * 流水号DAO接口
 * @author yubin
 * @version 2016-07-23
 */
@MyBatisDao
public interface SerialNumberDao extends CrudDao<SerialNumber> {
	
}