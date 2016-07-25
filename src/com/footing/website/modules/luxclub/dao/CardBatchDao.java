package com.footing.website.modules.luxclub.dao;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.CardBatch;

/**
 * 会员卡批次生成DAO接口
 * @author liuguoqing
 * @version 2016-03-15
 */
@MyBatisDao
public interface CardBatchDao extends CrudDao<CardBatch> {
	
}