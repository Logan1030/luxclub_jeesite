package com.footing.website.modules.luxclub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.entity.PicInfo;
import com.footing.website.modules.luxclub.dao.PicInfoDao;

/**
 * 会员图片信息表Service
 * @author yubin
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class PicInfoService extends CrudService<PicInfoDao, PicInfo> {

	public PicInfo get(Long id) {
		return super.get(id);
	}
	
	public List<PicInfo> findList(PicInfo picInfo) {
		return super.findList(picInfo);
	}
	
	public Page<PicInfo> findPage(Page<PicInfo> page, PicInfo picInfo) {
		return super.findPage(page, picInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PicInfo picInfo) {
		super.save(picInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PicInfo picInfo) {
		super.delete(picInfo);
	}
	
	public List<PicInfo> findListByBusinessId(Long businessId){
	    PicInfo picInfo = new PicInfo();
	    picInfo.setBusinessId(businessId);
	    return dao.findListByBusinessId(picInfo);
	}
	
}