package com.footing.website.modules.luxclub.api.response;

import java.util.List;

/**
 *  城市列表返回对象
 *  按首字母分组
 * @author liuguoqing
 *
 */
public class AreaGroupResponse {
    
    private String areaKey;
    
    private List<AreaResponse> areaList;

    public String getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(String areaKey) {
        this.areaKey = areaKey;
    }

    public List<AreaResponse> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaResponse> areaList) {
        this.areaList = areaList;
    }
    
}
