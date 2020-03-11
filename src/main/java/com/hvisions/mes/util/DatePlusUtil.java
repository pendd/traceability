package com.hvisions.mes.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dpeng
 * @description 日期加天工具类
 * @date 2019-08-29 10:57
 */
public class DatePlusUtil {

    /**
     *  获取传入日期加上天数以后的日期
     * @param date  传入日期
     * @param day   天数
     * @return      加法以后的日期
     */
    public static Date addDate(Date date,int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        return  calendar.getTime();
    }


    /**
     *  判断两个日期是否是同一天
     * @param dt1 日期1
     * @param dt2 日期2
     * @return    是否是同一天
     */
    public static Boolean sameDate(Date dt1 , Date dt2 )
    {
        LocalDate ld1 = new LocalDate(new DateTime(dt1));
        LocalDate ld2 = new LocalDate(new DateTime(dt2));
        return ld1.equals( ld2 );
    }

    /**
     *  判断当前时间是否在两个日期之内  (开始时间-半小时)
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    public static boolean currentBetweenTime(Date startTime,Date endTime){
        long beginTime = startTime.getTime() - 30 * 60 * 1000;
        return System.currentTimeMillis() >= beginTime && System.currentTimeMillis() <= endTime.getTime();
    }

    /**
     *  判断当前时间是否在哪个日期之内  (只比较年月日)
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     * @throws ParseException
     */
    public static boolean compareDate(Date startTime,Date endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long start = sdf.parse(sdf.format(startTime)).getTime();
        long end = sdf.parse(sdf.format(endTime)).getTime();

        long now = sdf.parse(sdf.format(new Date())).getTime();
        System.out.println(start);
        System.out.println(end);
        System.out.println(now);
        return now >= start && now <= end;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse("2019-09-20 15:00:03");
        Date end = sdf.parse("2019-09-30 15:00:03");
        System.out.println(compareDate(start, end));

    }
}
