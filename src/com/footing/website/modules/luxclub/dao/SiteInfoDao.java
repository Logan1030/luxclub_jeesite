package com.footing.website.modules.luxclub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.api.response.SiteInfoVo;
import com.footing.website.modules.luxclub.entity.SiteInfo;

/**
 * 场所信息DAO接口
 * @author yubin
 * @version 2016-03-16
 */
@MyBatisDao
public interface SiteInfoDao extends CrudDao<SiteInfo> {
   
	public List<SiteInfoVo> querySiteInfoByType(@Param("siteType") int siteType);
	
	public List<SiteInfoVo> searchListBySiteName(@Param("siteType") int siteType,@Param("siteName") String siteName);
	
	public SiteInfoVo siteInfoDetails(@Param("id") long id);
	
	public List<SiteInfoVo> siteInfoPageList(@Param("areaId")long areaId,@Param("siteType")int siteType,@Param("pageSize")int pageSize,
			                               @Param("pageNumber")int pageNumber); 
	
	public Integer countBySiteType(@Param("siteType")String siteType);
	
    public List<SiteInfo>findUpdateSite(@Param("siteType")String siteType,@Param("sort")int sort)throws Exception;
    
    public void batchUpdateSite(List<SiteInfo> siteInfos)throws Exception;
}