package com.footing.website.modules.luxclub.api;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footing.website.common.utils.DateUtils;
import com.footing.website.common.web.BaseController;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.utils.ApiUtil;

public class APIBaseController extends BaseController {
	private  final Logger logger=LoggerFactory.getLogger(getClass());
	/**
	 * 后台异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler({Exception.class})
    public Map<String,Object> exception(Exception e) {  
		Map<String, Object> map = new HashMap<String, Object>();
		ApiUtil.sysExceptionRespMap(map);
		logger.error("后台异常，系统忙原因",e);
        return map;
    }
	/**
	 * 
	 * <p>
	 * Description:通用打印方法<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月26日
	 * @param printName
	 * @param object
	 * @param start
	 * @param response
	 * @param map
	 * void
	 */
	public void printLog(String printName,Object object,Date start,HttpServletResponse response,HashMap<String, Object> map){
		logger.info("=== "+printName+",输入参数：{}",object.toString());
		Date endTime = new Date();
        logger.info("【" + DateUtils.formatDateTime(start) + "】"+printName+"接口输出参数: " + ApiConstant.API_STATUS_TEXT + "=" + map.get(ApiConstant.API_STATUS_TEXT) + "");
        logger.info("【" + DateUtils.formatDateTime(endTime) + "】"+printName+"接口结束...");
        logger.info(printName+"接口花费时间：【" + (endTime.getTime() - start.getTime()) / 1000 + "s】");
        String result = super.renderString(response, map);
        logger.info("=== "+printName+"接口列表接口，输出参数：{}", result);
	}
}
