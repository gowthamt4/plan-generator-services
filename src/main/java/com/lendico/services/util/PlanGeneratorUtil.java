package com.lendico.services.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanGeneratorUtil {
  
  private static final String ZULU_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  
  private static final Logger logger = LoggerFactory.getLogger(PlanGeneratorUtil.class);
  
  public static double strToDoubleRound(String str) {
    return Math.round(Double.valueOf(str)*100)/100.00; 
  }
  
  public static double doubleRoundOffTwoDecimals(double doubleValue) {
    return Math.round(doubleValue*100)/100.00; 
  }

  public static Date strToDate(final String dateStr) {
    try {
      TimeZone utc = TimeZone.getTimeZone("UTC");
      SimpleDateFormat dateformat = new SimpleDateFormat(ZULU_FORMAT);
      dateformat.setTimeZone(utc);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(dateformat.parse(dateStr));
      return cal.getTime();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      logger.error("Invalid Date format in the request");
      return null;
    }
  }
  
  public static String dateToStr(final Date date) {
      SimpleDateFormat dateformat = new SimpleDateFormat(ZULU_FORMAT);
      return dateformat.format(date);
  }
  
  public static Date addMonth(Date date, int month){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.MONTH, month);
      return cal.getTime();
  }
}
