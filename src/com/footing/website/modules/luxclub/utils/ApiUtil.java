package com.footing.website.modules.luxclub.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;

import com.footing.website.common.beanvalidator.BeanValidators;
import com.footing.website.common.mapper.JsonMapper;
import com.footing.website.common.utils.Base64Utils;
import com.footing.website.common.utils.SpringContextHolder;
import com.footing.website.modules.luxclub.api.client.ClientProperty;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.common.ProjectConstant;
import com.wmios.util.base.type.StringUtil;

public class ApiUtil {
	
	private static Validator validator = SpringContextHolder.getBean(Validator.class);
	
	/**
	 * 操作渠道转码
	 * @param channel
	 * @return
	 */
	public static String getOperaChannel(String channel) {
		channel = StringUtil.dealString(channel).toUpperCase();
		String opTerm = ProjectConstant.OP_TERM_DICT_PC;
		if (ApiConstant.OP_TERM_DICT_WECHAT.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_WEIXIN;
		} else if (ApiConstant.OP_TERM_DICT_ANDROID.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_ANDROID;
		} else if (ApiConstant.OP_TERM_DICT_IOS.equals(channel)) {
			opTerm = ProjectConstant.OP_TERM_DICT_IOS;
		}
		return opTerm;
	}
	
	/**
	 * 格式化投资进度(保留一位小数)
	 * @param rate
	 * @return
	 */
	public static Double formatRate(Double rate) {
		return new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	//FIXME 易宝浏览器返回地址前缀（使用时将端口号修改为相应值）
    public static String YEEPAY_NOTIFI_BASE_URL = "http://139.196.5.141:8080/fd/";
	
	/**
	 * 图片url转换
	 * @param cPath
	 * @param url
	 * @return
	 */
	public static String imageUrlConver(String cPath, String url){
		if(StringUtils.isBlank(url)){
			return "";
		}
		String path = YEEPAY_NOTIFI_BASE_URL;
		path = path.substring(0, path.length() - 1);
		if(url.contains(cPath)){
			url = url.replaceAll(cPath, "");
		}
		return path + url;
	}
	
	/**
	 * 获取ClientProperty
	 * @param client
	 * @return
	 */
	public static ClientProperty getClient(String client){
		String jsonStr = Base64Utils.getDecodeBASE64(client);
		ClientProperty cProperty = (ClientProperty)JsonMapper.fromJsonString(jsonStr, new ClientProperty().getClass());
		if(cProperty == null){
			cProperty = new ClientProperty();
		}
		return cProperty;
	}
	
	/**
	 * 判断是否强制更新
	 * @param index
	 * @param oldVersion
	 * @param newVersion
	 * @return
	 */
	public static boolean isNewVersion(int index, String oldVersion,
			String newVersion) {
		boolean resultFlag = false;
		int forCount = 0;
		int oldVersionCount = subStringCount(oldVersion, ".");
		int newVersionCount = subStringCount(newVersion, ".");
		String oldVersionArray[] = null;
		String newVersionArray[] = null;
		if (oldVersionCount > newVersionCount) {
			forCount = oldVersionCount - newVersionCount;
			for (int i = 0; i < forCount; i++)
				newVersion = newVersion + ".0";
		} else {
			forCount = newVersionCount - oldVersionCount;
			for (int i = 0; i < forCount; i++)
				oldVersion = oldVersion + ".0";
		}
		oldVersionArray = oldVersion.split("\\.");
		newVersionArray = newVersion.split("\\.");
		forCount = oldVersionArray.length;
		if (index >= 0) {
			if (index < forCount) {
				resultFlag = Integer.parseInt(newVersionArray[index]) > Integer
						.parseInt(oldVersionArray[index]);
			}
		} else {
			for (int i = 0; i < forCount; i++) {
				if (Integer.parseInt(newVersionArray[i]) > Integer
						.parseInt(oldVersionArray[i])) {
					resultFlag = true;
					break;
				}
			}
		}
		return resultFlag;
	}
	
	public static int subStringCount(String strTest, String strSub) {
        int count = 0, start = 0;
        while ((start = strTest.indexOf(strSub, start)) >= 0) {
            start += strSub.length();
            count++;
        }
        return count;
    }
	
	/**
	 * 数字金额大写转换
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	
	/**
	 * 结果返回数据
	 * @param resultCode
	 * @param resultText
	 * @param statusCode
	 * @param statusText
	 * @param reMap
	 * @param map
	 */
	public static void resultMapData(int resultCode, String resultText,
			int statusCode, String statusText, Map<String, Object> reMap,
			HashMap<String, Object> map) {
		Map<Object,Object> dataMap = new HashMap<Object,Object>();
		dataMap.put("resultCode", resultCode);
		dataMap.put("resultText", resultText);
		map.put(ApiConstant.API_STATUS_CODE, statusCode);
		map.put(ApiConstant.API_STATUS_TEXT, statusText);
		map.put(ApiConstant.API_RESP_DATA, dataMap);
	}
	
	/**
	 * 卡号 token失效提示
	 * @param map
	 */
	public static void tokenInvalidByCardNo(Map<String, Object> map){
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_INVALID_TOKEN);
		map.put(ApiConstant.API_STATUS_TEXT, "请重新绑定会员卡");
	}

	
	/**
	 * 登录 token失效提示
	 * @param map
	 */
	public static void tokenInvalidByLogin(Map<String, Object> map){
	    map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_INVALID_TOKEN);
	    map.put(ApiConstant.API_STATUS_TEXT, "请重新登录");
	}
	
	/**
	 * 后台异常
	 * @param map
	 */
	public static void sysExceptionRespMap(Map<String, Object> map){
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		map.put(ApiConstant.API_STATUS_TEXT, ApiConstant.API_EXCEPTION_RESP_TEXT);
	}
	
	/**
	 * map响应数据
	 * @param map
	 * @param dataObject
	 * @param isSuccess
	 * @param isToken
	 */
	public static void mapRespData(Map<String, Object> map, Object dataObject, Object textObject, boolean isSuccess){
		if(isSuccess){
			textObject = textObject !=null && !"".equals(textObject) ? textObject : "ok";
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		}else{
			textObject = textObject != null && !"".equals(textObject) ? textObject : "fail";
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
		}
		map.put(ApiConstant.API_STATUS_TEXT, textObject);
		if(dataObject != null && !"".equals(dataObject)){
			map.put(ApiConstant.API_RESP_DATA, dataObject);
		}
	}
	
	/**
	 * 入参校验
	 * @param entity
	 * @return
	 */
	public static <T> List<String> validateBean(T entity) {
		List<String> messages = null;
		try{
			BeanValidators.validateWithException(validator, entity);
		}catch(ConstraintViolationException ex){
			messages =  BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
		}
		return messages;
	}
	/**
	 * 
	 * <p>
	 * Description:获取设备ID<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param clint
	 * @return
	 * String
	 */
	public static String getDeviceId(ClientProperty clint){
		String channel = StringUtil.dealString(clint.type).toUpperCase();
		if (ApiConstant.OP_TERM_DICT_ANDROID.equals(channel)) {
			return clint.getAndroid().getDeviceNumber();
		} 
		if (ApiConstant.OP_TERM_DICT_IOS.equals(channel)) {
			return clint.getIos().getDeviceNumber();
		}
		return null;
	}

}
