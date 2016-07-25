package com.footing.website.modules.luxclub.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.footing.website.common.config.Global;
import com.footing.website.common.utils.FileUtils;
import com.footing.website.common.utils.ImageUtils;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.api.request.UploadRequest;
import com.footing.website.modules.luxclub.api.response.UploadImage;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.wmios.util.base.CommonUtil;

@Service
public class UploadService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public String uploadImageBy(UploadRequest uploadRequest,HttpServletRequest request,HashMap<String, Object> map)throws Exception{
		String result=BusinessConstants.SUCCESS;
		UploadImage uploadImage=new UploadImage();
		generateImage(uploadRequest.getImage(), request,uploadImage);
		Map<String,String> mapData = new HashMap<String,String>();
        mapData.put("imageName", uploadImage.getImageName());
        mapData.put("imageUrl",uploadImage.getImageUrl().replace("\\", "/"));
        ApiUtil.mapRespData(map, mapData, "提交订单成功", true);
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:解析图片流及返回地址临时图片<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月5日
	 * @param voucher
	 * @return
	 * String
	 */
	public void  generateImage(String imageBase,HttpServletRequest request,UploadImage uploadImage){
	   
	    String basePath = "temp";
	    // 照片名字
	    StringBuffer picName = new StringBuffer();
		// 文件保存目录路径
	    String savePath = request.getSession().getServletContext().getRealPath("/") + basePath + File.separator;
		// 文件保存目录URL
		String saveUrl = request.getContextPath() + File.separator + basePath + File.separator;
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {// 检查目录
			uploadDir.mkdir();
		}   
		if(!uploadDir.canWrite()) {
			logger.debug("上传目录[" + savePath + "]没有写权限");
		} else {
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			
			picName.append(CommonUtil.getFileNamePrefix()).append(".").append("jpg");
			savePath += ymd;
			saveUrl += ymd +File.separator+picName;
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
             
		}
		//写入图片
	    ImageUtils.GenerateImage(imageBase, savePath+File.separator+picName);
		uploadImage.setImageName(picName.toString()); 
		uploadImage.setImageUrl(Global.webUrl()+saveUrl);
	}
	/**
	 * 
	 * <p>
	 * Description:获取凭证图片的数组<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月15日
	 * @param voucherNames
	 * @param request
	 * @return
	 * String
	 */
	public String  getVoucherUrl(String voucherNames,HttpServletRequest request){
		if(StringUtils.isEmpty(voucherNames)){
			return BusinessConstants.ILLEGAL_OPT;
		}
		String picUrls="";
		String basePath = "upload"+File.separator+"images";
		String contenxtPath=request.getSession().getServletContext().getRealPath("/");
		// 文件保存目录路径
		String savePath = contenxtPath + basePath;
		// 文件保存目录URL
		String saveUrl = request.getContextPath() +File.separator+ basePath + File.separator;
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {// 检查目录
			uploadDir.mkdir();
		}   
		if(!uploadDir.canWrite()) {
			logger.debug("上传目录[" + savePath + "]没有写权限");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath =savePath+File.separator+ymd+File.separator;;
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			String srcDirName=contenxtPath+"temp"+File.separator+ymd+File.separator;
			
			if(!voucherNames.contains(",")){
				//上传一张图片
				if(FileUtils.copyFileCover(srcDirName+voucherNames, savePath+voucherNames, true)){
					FileUtils.deleteFile(srcDirName+voucherNames);					
					picUrls+="|"+saveUrl+ymd +File.separator+voucherNames;
				}
			}else{
				//上传多张图片
				String[]picNames=voucherNames.split(",");
				for(String picName:picNames){
					if(FileUtils.copyFileCover(srcDirName+picName, savePath+picName, true)){
						FileUtils.deleteFile(srcDirName+picName);					
						picUrls+="|"+saveUrl+ymd +File.separator+picName;
					}
				}
			}
		}
		return picUrls.replace("\\", "/"); 
	}
	  
}
