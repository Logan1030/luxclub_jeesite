package com.footing.website.modules.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.service.TreeService;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.entity.Area;
import com.footing.website.modules.luxclub.api.response.AreaGroupResponse;
import com.footing.website.modules.luxclub.api.response.AreaResponse;
import com.footing.website.modules.sys.dao.AreaDao;
import com.footing.website.modules.sys.utils.UserUtils;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * 区域Service
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
	    try {
	        String namePinyin = PinyinHelper.convertToPinyinString(area.getName(), "", PinyinFormat.WITHOUT_TONE);
            if(StringUtils.isNotBlank(namePinyin)){
                String nameFirst = namePinyin.substring(0,1);
                area.setNameFirst(nameFirst.toUpperCase());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	/**
	 * 获取区域按首字母分组数据list
	 * @return
	 */
	public List<AreaGroupResponse> findAreaList(){
	    List<AreaGroupResponse> areaGroupList = new ArrayList<AreaGroupResponse>();
	    Map<String,List<AreaResponse>> areaMap = new HashMap<String,List<AreaResponse>>();
	    List<Area> areaList = this.findAll();
	    for (Area area : areaList) {
            String key = area.getNameFirst();
            List<AreaResponse> tempList = areaMap.get(key);
            AreaResponse areaResp = new AreaResponse(area);
            if(tempList !=null && tempList.size() > 0){
                tempList.add(areaResp);
                areaMap.put(key, tempList);
            }else{
                tempList = new ArrayList<AreaResponse>();
                tempList.add(areaResp);
                areaMap.put(key, tempList);
            }
        }
	    for (Entry<String, List<AreaResponse>> entry: areaMap.entrySet()) {
	        AreaGroupResponse areaGroup = new AreaGroupResponse();
	        areaGroup.setAreaKey(entry.getKey());
	        areaGroup.setAreaList(entry.getValue());
	        areaGroupList.add(areaGroup);
        }
	    //按首字母排序
	    Collections.sort(areaGroupList, new Comparator<AreaGroupResponse>(){  
            public int compare(AreaGroupResponse arg0, AreaGroupResponse arg1) {  
                return arg0.getAreaKey().compareTo(arg1.getAreaKey());  
            }  
        });  
	    return areaGroupList;
	}
	
	/**
	 *  热门搜索--城市列表
	 * @return
	 */
	public List<AreaResponse> hotSearchAreaList(){
	    List<AreaResponse> resultList = new ArrayList<>();
	    List<Area> areaList = this.findAll();
	    //按sort排序
        Collections.sort(areaList, new Comparator<Area>(){  
            public int compare(Area arg0, Area arg1) {  
                return arg1.getSort().compareTo(arg0.getSort());  
            }  
        });
        int count = 3;
        for (int i = 0; i < count; i++) {
            Area area = areaList.get(i);
            resultList.add(new AreaResponse(area));
        }
	    return resultList;
	}

	/**
	 *  热门搜索--城市列表
	 * @return
	 */
	public List<AreaResponse> searchListByAreaName(String areaName){
	    List<AreaResponse> resultList = new ArrayList<>();
        for (Area area : this.findAll()) {
            if(area.getName().contains(areaName)){
                resultList.add(new AreaResponse(area));
            }
        }
        return resultList;
	}
	
}
