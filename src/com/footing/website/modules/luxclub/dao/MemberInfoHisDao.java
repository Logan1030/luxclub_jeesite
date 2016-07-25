package com.footing.website.modules.luxclub.dao;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;

/**
 * 会员卡信息历史DAO接口
 * @author liuguoqing
 * @version 2016-03-16
 */
@MyBatisDao
public interface MemberInfoHisDao extends CrudDao<MemberInfoHis> {
	
}