package com.footing.website.modules.luxclub.api.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPost {

    private final static Logger logger = LoggerFactory.getLogger(SendPost.class);

    public static String sendPost(String url, String json) {
        PrintWriter out = null;
        BufferedReader br = null;
        String result = "";
        try {
            URL rqPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) rqPath.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            // conn.connect();

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.write(json);// 发送
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
            conn.disconnect();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
  
}
