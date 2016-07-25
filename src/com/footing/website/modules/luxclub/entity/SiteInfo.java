package com.footing.website.modules.luxclub.entity;

import org.hibernate.validator.constraints.Length;

import com.footing.website.common.persistence.DataEntity;
import com.footing.website.modules.entity.Area;

/**
 * 场所信息Entity
 * @author yubin
 * @version 2016-03-16
 */
public class SiteInfo extends DataEntity<SiteInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contactMan;		// 联系人
	private String contactPhone;		// 联系电话
	private String email;		// 邮件
	private String siteType;		// 场所类型
	private String siteName;		// 场所名称
	private String siteAddr;		// 场所地址
	private String siteDescription;		// 场所描述
	private Long siteAreaId;		// 场所区域
	private String activeInfo;		// 活动信息
	private String shopAlbum;		// 店家相册
	private String shopPhoto;		// 店家主图
	private Area area;//归属区域
	private String code;//区域编码
	private String isOpen;//是否开业
	private int sort;//置顶
	
	
	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public SiteInfo() {
		super();
	}

	public SiteInfo(Long id){
		super(id);
	}

	@Length(min=1, max=64, message="联系人长度必须介于 1 和 64 之间")
	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	
	@Length(min=1, max=16, message="联系电话长度必须介于 1 和 16 之间")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Length(min=0, max=16, message="邮件长度必须介于 0 和 16 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=1, max=1, message="场所类型长度必须介于 1 和 1 之间")
	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	
	@Length(min=1, max=255, message="场所名称长度必须介于 1 和 255 之间")
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@Length(min=1, max=255, message="场所地址长度必须介于 1 和 255 之间")
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
	
	public Long getSiteAreaId() {
		return siteAreaId;
	}

	public void setSiteAreaId(Long siteAreaId) {
		this.siteAreaId = siteAreaId;
	}
	
	public String getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(String activeInfo) {
		this.activeInfo = activeInfo;
	}
	
	public String getShopAlbum() {
		return shopAlbum;
	}

	public void setShopAlbum(String shopAlbum) {
		this.shopAlbum = shopAlbum;
	}
	public String getShopPhoto() {
		return shopPhoto;
	}

	public void setShopPhoto(String shopPhoto) {
		this.shopPhoto = shopPhoto;
	}
	
}