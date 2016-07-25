package com.footing.website.modules.luxclub.api.response;

import com.footing.website.modules.entity.Area;

public class AreaResponse {

    private Long areaId;
    
    private String areaName;
    
    public AreaResponse(){
    }

    public AreaResponse(Area area){
        this.areaId = area.getId();
        this.areaName = area.getName();
    }
    
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AreaResponse [areaId=");
        builder.append(areaId);
        builder.append(", areaName=");
        builder.append(areaName);
        builder.append("]");
        return builder.toString();
    }
    
}
