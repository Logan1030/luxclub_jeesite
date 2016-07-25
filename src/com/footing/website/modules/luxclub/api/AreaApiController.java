package com.footing.website.modules.luxclub.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.footing.website.modules.luxclub.api.request.BaseRequest;
import com.footing.website.modules.luxclub.api.response.AreaGroupResponse;
import com.footing.website.modules.luxclub.api.response.AreaResponse;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.sys.service.AreaService;

@Controller
@RequestMapping(value = "${frontPath}/api/area", method = RequestMethod.POST)
public class AreaApiController extends APIBaseController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AreaService areaService;
    
    /**
     * 城市列表
     * @param baseRequest
     * @param response
     */
    @RequestMapping(value = "getAreaList", method = RequestMethod.POST)
    public void getAreaList(BaseRequest baseRequest, HttpServletResponse response){
        logger.info("=== 城市列表接口,输入参数：{}", baseRequest.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AreaGroupResponse> resultList = areaService.findAreaList();
        ApiUtil.mapRespData(map, resultList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        String result = super.renderString(response, map);
        logger.info("=== 城市列表接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

    /**
     * 热门搜索--城市列表
     * @param feeRecordReq
     * @param response
     */
    @RequestMapping(value = "hotSearchAreaList", method = RequestMethod.POST)
    public void hotSearchAreaList(BaseRequest baseRequest, HttpServletResponse response){
        logger.info("=== 热门搜索--城市列表接口,输入参数：{}", baseRequest.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AreaResponse> resultList = areaService.hotSearchAreaList();
        ApiUtil.mapRespData(map, resultList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        String result = super.renderString(response, map);
        logger.info("=== 热门搜索--城市列表接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

    /**
     * 城市名称搜索
     * @param feeRecordReq
     * @param response
     */
    @RequestMapping(value = "searchListByAreaName", method = RequestMethod.POST)
    public void searchListByAreaName(BaseRequest baseRequest, @RequestParam(required=true)String areaName, HttpServletResponse response){
        logger.info("=== 城市名称搜索接口,输入参数：areaName:{}, {}",areaName, baseRequest.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AreaResponse> resultList = areaService.searchListByAreaName(areaName);
        ApiUtil.mapRespData(map, resultList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        String result = super.renderString(response, map);
        logger.info("=== 城市名称搜索接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

}
