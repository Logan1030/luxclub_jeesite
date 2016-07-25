package com.footing.website.modules.luxclub.api;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footing.website.modules.luxclub.api.request.UploadRequest;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.service.UploadService;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 
 * <p>
 * Description:上传图片接口<br />
 * </p>
 * @title UploadController.java
 * @package com.footing.website.modules.luxclub.api 
 * @author yubin
 * @version 0.1 2016年4月15日
 */
@Controller
@RequestMapping(value = "${frontPath}/api/upload", method = RequestMethod.POST)
public class UploadController extends APIBaseController{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadService uploadService;
    /**
     * 
     * <p>
     * Description:上传图片<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月15日
     * @param response
     * @param upload
     * void
     */
	@RequestMapping(value = "image")
	public void upload(HttpServletResponse response,UploadRequest upload){
    	Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(upload.getToken());
        try{
        if(clientToken != null){
        	String result=uploadService.uploadImageBy(upload,currentRequest(),map);
            if(!BusinessConstants.SUCCESS.equals(result)){
            	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
            }
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        }catch(Exception e){
        	logger.error("上传图片", e);
        }
        printLog("上传图片", upload, startTime, response, map);
    }
}
