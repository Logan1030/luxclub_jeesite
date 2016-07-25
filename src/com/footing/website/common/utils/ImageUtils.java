package com.footing.website.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.wmios.util.base.tool.FileUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtils {
    
	/**
	 * 图片转化成base64字符串
	 * @param imgFile
	 * @return
	 */
	public static String GetImageStr(String imgFile){
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
	
	/**
	 * base64字符串转化成图片
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath){
		FileUtil.createFolderByFilePathName(imgFilePath);
		if(imgStr == null ){
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)
        {  
            return false;  
        }    
	}
}
