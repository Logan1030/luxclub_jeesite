package com.footing.website.modules.luxclub.api.client;

import com.footing.website.common.persistence.DataEntity;

public class ClientProperty extends DataEntity<ClientProperty>{
	private static final long serialVersionUID = 1L;
	public String language;		//语言
	public String type;		//类型：website、wechat、android、ios
	public String version;		//版本
	public WechatProperty website;		//网站参数
	public WechatProperty wechat;		//微信参数
	public AndroidProperty android;	//android属性
	public IosProperty ios;	//ios属性
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	
	public WechatProperty getWebsite() {
		return website;
	}
	public void setWebsite(WechatProperty website) {
		this.website = website;
	}
	public WechatProperty getWechat() {
		return wechat;
	}
	public void setWechat(WechatProperty wechat) {
		this.wechat = wechat;
	}
	public AndroidProperty getAndroid() {
		return android;
	}
	public void setAndroid(AndroidProperty android) {
		this.android = android;
	}
	public IosProperty getIos() {
		return ios;
	}
	public void setIos(IosProperty ios) {
		this.ios = ios;
	}
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClientProperty [language=");
        builder.append(language);
        builder.append(", type=");
        builder.append(type);
        builder.append(", version=");
        builder.append(version);
        builder.append(", website=");
        builder.append(website);
        builder.append(", wechat=");
        builder.append(wechat);
        builder.append(", android=");
        builder.append(android);
        builder.append(", ios=");
        builder.append(ios);
        builder.append("]");
        return builder.toString();
    }
    
}
