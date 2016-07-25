package com.footing.website.modules.luxclub.utils;
 
 import java.io.PrintStream;
 import java.math.BigDecimal;
 import java.text.DecimalFormat;
 import org.apache.log4j.Logger;
 
 public class NumberUtil
 {
   private static final Logger logger = Logger.getLogger(NumberUtil.class);
   private static DecimalFormat df = new DecimalFormat("0000");
 
   public static String formatNumber(int number, int len)
   {
     String numberStr = null;
     df.setMaximumIntegerDigits(len);
     numberStr = df.format(number);
     return numberStr;
   }
 
   public static String doble2String(Double o, int len)
   {
     String result = "";
     if (o == null) {
       return result;
     }
 
     BigDecimal b = new BigDecimal(o.doubleValue()).setScale(len, 5);
     result = b.toString();
     return result;
   }
 
   public static String double2Str(Double d)
   {
     BigDecimal b = new BigDecimal(d.doubleValue()).setScale(2, 5);
     return b.toString();
   }
   public static void main(String[] a) {
     String str = formatNumber(55, 4);
     System.out.println(str);
   }
 }

/* 
 
 
 */