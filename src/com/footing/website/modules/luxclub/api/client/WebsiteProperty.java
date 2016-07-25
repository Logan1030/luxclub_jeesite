package com.footing.website.modules.luxclub.api.client;

import com.footing.website.common.persistence.DataEntity;

public class WebsiteProperty extends DataEntity<IosProperty>{
	private static final long serialVersionUID = 1L;
	public String deviceModel;	//设备型号
	public String systemVersion;	//操作系统版本，如：9.0.1
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	
}
