package com.footing.website.modules.luxclub.utils;
 
 import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
 
 public class DateUtil
 {
   private static final Logger logger = Logger.getLogger(DateUtil.class);
   private static SimpleDateFormat df = new SimpleDateFormat();
   public static final String FMT_A = "yyyy-MM-dd HH:mm:ss";
   public static final String FMT_C = "yyyy-MM-dd HH:mm";
   public static final String FMT_B = "yyyy-MM-dd";
   public static final String FMT_D = "yyyy/MM/dd HH:mm:ss";
   public static final String FMT_E = "yyyyMMdd";
   public static final String FMT_F = "yyyyMMddHHmm";
   public static final String FMT_G = "yyyy/MM/dd";
 
   public static String date2Str(Date date, String partten)
   {
     String result = null;
     df.applyPattern(partten);
     result = df.format(date);
     return result;
   }
   public static void main(String[] args) {
	System.out.println(date2Str(new Date(), DateUtil.FMT_B));
}
 
   public static Date str2Date(String dateStr, String pattern)
   {
     Date date = null;
     df.applyPattern(pattern);
     try {
       date = df.parse(dateStr);
     } catch (ParseException e) {
       logger.debug("string 2 Date error,pattern:" + pattern, e);
     }
     return date;
   }
 
   
   public static Map<String, String> findLastMonth(int count) {
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
 
     Calendar cal = Calendar.getInstance();
     GregorianCalendar gcLast = (GregorianCalendar)Calendar.getInstance();
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(new Date());
 
     calendar.add(2, -count);
     Date theDate = calendar.getTime();
     gcLast.setTime(theDate);
     gcLast.set(5, 1);
     String day_first_prevM = df.format(gcLast.getTime());
     StringBuffer str = new StringBuffer().append(day_first_prevM).append(
       " 00:00:00");
     day_first_prevM = str.toString();
 
     calendar.add(2, 1);
     calendar.set(5, 1);
     calendar.add(5, -1);
     String day_end_prevM = df.format(calendar.getTime());
     StringBuffer endStr = new StringBuffer().append(day_end_prevM).append(
       " 23:59:59");
     day_end_prevM = endStr.toString();
 
     Map map = new HashMap();
     map.put("prevMonthFD", day_first_prevM);
     map.put("prevMonthPD", day_end_prevM);
     return map;
   }
 }

/* 
 
 
 */