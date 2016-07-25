package com.footing.website.modules.luxclub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.api.response.SiteInfoVo;
import com.footing.website.modules.luxclub.dao.SiteInfoDao;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.sys.service.AreaService;

/**
 * 场所信息Service
 * @author yubin
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class SiteInfoService extends CrudService<SiteInfoDao, SiteInfo> {	
	@Autowired
	private AreaService areaService;
	public SiteInfo get(Long id) {
		return super.get(id);
	}
	
	public List<SiteInfo> findList(SiteInfo siteInfo) {
		return super.findList(siteInfo);
	}
	
	public Page<SiteInfo> findPage(Page<SiteInfo> page, SiteInfo siteInfo) {
		return super.findPage(page, siteInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SiteInfo siteInfo) {
		super.save(siteInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SiteInfo siteInfo) {
		super.delete(siteInfo);
	}
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	public void saveSiteAndPic(SiteInfo siteInfo) {
		siteInfo.setSiteAreaId(siteInfo.getArea().getId());
		siteInfo.setCode(areaService.get(siteInfo.getArea().getId()).getCode());
		if(siteInfo.getIsNewRecord()){
			Integer sort=dao.countBySiteType(siteInfo.getSiteType());
		    siteInfo.setSort(sort.intValue()+1);
		}
		super.save(siteInfo);
	}
	/**
	 * 
	 * <p>
	 * Description:置顶功能<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年5月30日
	 * @param id
	 * void
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	public void stickSiteInfo(Long id)throws Exception{
		SiteInfo siteInfo=this.get(id);
		List<SiteInfo> siteInfos=dao.findUpdateSite(siteInfo.getSiteType(), siteInfo.getSort());
		if(siteInfos!=null &&siteInfos.size()>0){
			siteInfo.setSort(1);
			this.save(siteInfo);
			dao.batchUpdateSite(siteInfos);
		}
	}
	
	public List<SiteInfo> findAllList(){
        List<SiteInfo> list = new ArrayList<>();
        list = dao.findAllList(new SiteInfo());
        return list;
    }
	@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
	public List<SiteInfoVo> querySiteInfoByType(int siteType){
		return dao.querySiteInfoByType(siteType);
	}
	@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
    public List<SiteInfoVo> searchListBySiteName(  int siteType, String siteName){
    	return dao.searchListBySiteName(siteType, siteName);
    }
	@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
	public SiteInfoVo siteInfoDetails(long id){
		return dao.siteInfoDetails(id);
	}
	@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
	public List<SiteInfoVo> siteInfoPageList( long areaId, int siteType, int pageSize,int pageNumber){
		return dao.siteInfoPageList(areaId, siteType, ( pageNumber-1)*pageSize, pageSize);
	}
	
}