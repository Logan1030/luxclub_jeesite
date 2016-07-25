package com.footing.website.modules.luxclub.dao;

import java.util.List;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.entity.PicInfo;

/**
 * 会员图片信息表DAO接口
 * @author yubin
 * @version 2016-03-16
 */
@MyBatisDao
public interface PicInfoDao extends CrudDao<PicInfo> {
    
    public List<PicInfo> findListByBusinessId(PicInfo picInfo);
	
}