package com.footing.website.modules.luxclub.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footing.website.common.utils.DateUtils;
import com.footing.website.modules.luxclub.api.response.SiteInfoVo;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.service.SiteInfoService;
import com.footing.website.modules.luxclub.utils.ApiUtil;
/**
 * 
 * <p>
 * Description:场所接口<br />
 * </p>
 * @title SiteInfoBusinessController.java
 * @package com.footing.website.modules.luxclub.api 
 * @author yubin
 * @version 0.1 2016年3月23日
 */
@Controller
@RequestMapping(value="${frontPath}/api/siteinfo/" ,method = RequestMethod.POST)
public class SiteInfoApiController extends APIBaseController{
	
	private  final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteInfoService siteInfoService;
	/**
	 * 
	 * <p>
	 * Description:热门搜索--场所列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param response
	 * @param client
	 * @param siteType
	 * void
	 */
	@RequestMapping("hotSearchSiteList")
	public void hotSearchSiteList(HttpServletResponse response,String client,int siteType){
		Date startTime = new Date();
	    
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    List<SiteInfoVo> siteInfoVos= siteInfoService.querySiteInfoByType(siteType);
	    ApiUtil.mapRespData(map,siteInfoVos,"正常", true); 
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】hotSearchSiteList out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api hotSearchSiteList end...");
        logger.info("api hotSearchSiteList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        String result = super.renderString(response, map);
        logger.info("=== 热门搜索接口，输出参数：{}", result);
	}
	/**
	 * 
	 * <p>
	 * Description:根据场所名称搜索<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param response
	 * @param client
	 * @param siteType
	 * void
	 */
	@RequestMapping("searchListBySiteName")
	public void searchListBySiteName(HttpServletResponse response,String client,int siteType,String siteName){
		Date startTime = new Date();
	    
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    List<SiteInfoVo> siteInfoVos= siteInfoService.searchListBySiteName(siteType, siteName);
	    ApiUtil.mapRespData(map,siteInfoVos,"正常", true); 
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】searchListBySiteName out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api searchListBySiteName end...");
        logger.info("api searchListBySiteName total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        String result = super.renderString(response, map);
        logger.info("=== 根据场所名称搜索接口，输出参数：{}", result);
	}
	/**
	 * 
	 * <p>
	 * Description:场所详细信息<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param response
	 * @param client
	 * @param siteType
	 * void
	 */
	@RequestMapping("siteInfoDetails")
	public void siteInfoDetails(HttpServletResponse response,String client,long siteId){
		Date startTime = new Date();
	    
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    SiteInfoVo siteInfoVos= siteInfoService.siteInfoDetails(siteId);
	    ApiUtil.mapRespData(map,siteInfoVos,"正常", true); 
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】siteInfoDetails out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api siteInfoDetails end...");
        logger.info("api siteInfoDetails total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        String result = super.renderString(response, map);
        logger.info("=== 场所详细信息接口，输出参数：{}", result);
	}
	/**
	 * 
	 * <p>
	 * Description:场所信息分页列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @param response
	 * @param client
	 * @param siteType
	 * void
	 */
	@RequestMapping("siteInfoPageList")
	public void siteInfoPageList(HttpServletResponse response,String client,int siteType,int areaId,int pageSize,int pageNumber){
		Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    List<SiteInfoVo> siteInfoVos= siteInfoService.siteInfoPageList(areaId, siteType, pageSize, pageNumber);
	    ApiUtil.mapRespData(map,siteInfoVos,"正常", true); 
        Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(startTime) + "】siteInfoPageList out parameter: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】api siteInfoPageList end...");
        logger.info("api siteInfoPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        String result = super.renderString(response, map);
        logger.info("=== 场所信息分页列表，输出参数：{}", result);
	}
}
