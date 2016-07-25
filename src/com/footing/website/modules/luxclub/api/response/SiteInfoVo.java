package com.footing.website.modules.luxclub.api.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.footing.website.common.config.Global;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.sys.utils.DictUtils;

/**
 * 场所信息Entity
 * @author yubin
 * @version 2016-03-16
 */
public class SiteInfoVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long siteId;
	private String contacter;		// 联系人
	private String contactPhone;		// 联系电话
	private String email;		// 邮件
	private int siteType;		// 场所类型
	private String siteName;		// 场所名称
	private String siteAddr;		// 场所地址
	private String siteDescription;		// 场所描述
	private String areaName;		// 场所区域
	private String activeInfo;		// 活动信息
	private String shopAlbum;		// 店家相册
	private String siteImageUrl;		// 店家主图
    private String isOpen;
    
    
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String[] getSiteAlbum() {
		String temp[]=null;
		int i=0;
		if(!StringUtils.isEmpty(getShopAlbum())){
			int length=getShopAlbum().split("[|]").length;
			if(length%2==0){
				temp=new String[getShopAlbum().split("[|]").length];
			}else{
				temp=new String[getShopAlbum().split("[|]").length-1];
			}
	    	for(String addr:getShopAlbum().split("[|]")){
	    		if(!StringUtils.isEmpty(addr)){
		    		 temp[i++]=Global.webUrl()+addr;
	    		}
	    	}
	    	if(i==1 ||i==3){
	    		temp[i++]=Global.webUrl()+BusinessConstants.URL_BLANK;
	    	}
	    }
		return temp;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSiteType() {
		return siteType;
	}
	public void setSiteType(int siteType) {
		this.siteType = siteType;
	}
	public String getSiteTypeName() {
		return DictUtils.getDictLabel(String.valueOf(getSiteType()), "site_type","1");
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteAddr() {
		return siteAddr;
	}
	public void setSiteAddr(String siteAddr) {
		this.siteAddr = siteAddr;
	}
	public String getSiteDescription() {
		return siteDescription;
	}
	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getActiveInfo() {
		return activeInfo;
	}
	public void setActiveInfo(String activeInfo) {
		this.activeInfo = activeInfo;
	}
	@JsonIgnore
	public String getShopAlbum() {
		return shopAlbum;
	}
	public void setShopAlbum(String shopAlbum) {
		this.shopAlbum = shopAlbum;
	}
	public String getSiteImageUrl() {
		return Global.webUrl()+siteImageUrl;
	}
	public void setSiteImageUrl(String siteImageUrl) {
		this.siteImageUrl = siteImageUrl;
	}
	
	
	
	 
	
}