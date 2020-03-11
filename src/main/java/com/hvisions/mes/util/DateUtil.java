package com.hvisions.mes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 *
 * @author wenkb
 */
public class DateUtil {

    /**
     * Date转换为LocalDateTime
     *
     * @param d
     *            需要转换的Date对象
     * @return 转换后的LocalDateTime对象
     */
    public static final LocalDateTime toLocalDateTime(Date d) {
        if (d == null) {
            return null;
        }

        return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
    }
    /**
     * String转换为Date
     *
     * @param s
     *            需要转换的String对象
     * @return 转换后的Date对象
     */
    public static final Date toDate(String s) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.parse(s);
    }
    /**
     * String转换为Date
     *
     * @param s
     *            需要转换的String对象
     * @return 转换后的Date对象
     */
    public static final Date toTime(String s) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.parse(s);
    }

    /**
     * String转换为Date
     *
     * @param s
     *            需要转换的String对象
     * @return 转换后的Date对象
     */
    public static final Date toDates(String s) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(s);
    }

    /**
     * 格式化时间
     *
     * @param d
     *            时间
     * @param fmt
     *            格式{@link DateTimeFormatter}
     *
     * @return 格式化后的字符串
     */
    public static final String format(LocalDateTime d, String fmt) {
        if (d == null || fmt == null) {
            return "";
        }

        return d.format(DateTimeFormatter.ofPattern(fmt));
    }

    /**
     * 计算两个时间相差的分钟数
     *
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 分钟差
     */
    public static final long periodMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }

        return Duration.between(startTime, endTime).toMinutes();
    }

    //-------------------------------- 计算开始结束时间 START ----------------------------------
    /**
     * 获取日开始时间
     *
     * @param d 基准时间
     * @return 日开始时间
     */
    public static final LocalDateTime getDayStartTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * 获取日结束时间
     *
     * @param d 基准时间
     * @return 日结束时间
     */
    public static final LocalDateTime getDayEndTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.truncatedTo(ChronoUnit.DAYS).plusDays(1).minusSeconds(1);
    }

    /**
     * 获取周开始时间
     *
     * @param d 基准时间
     * @return 周开始时间
     */
    public static final LocalDateTime getWeekStartTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * 获取周结束时间
     *
     * @param d 基准时间
     * @return 周结束时间
     */
    public static final LocalDateTime getWeekEndTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS).plusWeeks(1).minusSeconds(1);
    }

    /**
     * 获取月开始时间
     *
     * @param d 基准时间
     * @return 月开始时间
     */
    public static final LocalDateTime getMonthStartTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * 获取月结束时间
     *
     * @param d 基准时间
     * @return 月结束时间
     */
    public static final LocalDateTime getMonthEndTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS).plusMonths(1).minusSeconds(1);
    }

    /**
     * 获取年开始时间
     *
     * @param d 基准时间
     * @return 年开始时间
     */
    public static final LocalDateTime getYearStartTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * 获取年结束时间
     *
     * @param d 基准时间
     * @return 年结束时间
     */
    public static final LocalDateTime getYearEndTime(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS).plusYears(1).minusSeconds(1);
    }
    //-------------------------------- 计算开始结束时间 END ----------------------------------

    // 获取日期时间段
    //---------------------------------------------------------------------------------------
    /**
     * 获得从开始时间到结束时间所有的日期
     *
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     */
    public static final List<LocalDate> getAllLocalDate(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> result = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.equals(endDate)) {
            result.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

    /**
     * 获得从开始时间到结束时间所有的日期
     *
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     */
    @SuppressWarnings("deprecation")
    public static final List<LocalDate> getAllLocalDate(Date startDate, Date endDate) {
        return getAllLocalDate(
                LocalDate.of(startDate.getYear(), startDate.getMonth() + 1, startDate.getDate()),
                LocalDate.of(endDate.getYear(), endDate.getMonth() + 1, endDate.getDate()));
    }

    /**
     * 获得从开始时间到结束时间所有的日期
     *
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     */
    @SuppressWarnings("deprecation")
    public static final List<Date> getAllDate(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> localDateList = getAllLocalDate(startDate, endDate);
        return localDateList.stream()
                .map(item -> new Date(item.getYear(), item.getMonthValue() - 1,
                        item.getDayOfMonth()))
                .collect(Collectors.toList());
    }

    /**
     * 获得从开始时间到结束时间所有的日期
     *
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     */
    @SuppressWarnings("deprecation")
    public static final List<Date> getAllDate(Date startDate, Date endDate) {
        List<LocalDate> localDateList = getAllLocalDate(startDate, endDate);
        return localDateList.stream()
                .map(item -> new Date(item.getYear(), item.getMonthValue() - 1,
                        item.getDayOfMonth()))
                .collect(Collectors.toList());
    }

    public static final Date getLastDayOfMonth(Date date) {
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 设置Calendar月份数为下一个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        // 设置Calendar日期为下一个月一号
        calendar.set(Calendar.DATE, 1);
        // 设置Calendar日期减一,为本月末
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    // 日期格式化----报表使用
    //---------------------------------------------------------------------------------------

    /**
     * 格式化date中的年.
     *
     * @return 类似 2017年 的String.
     */
    public static final String formatYear(Date date) {
        return DateFormatUtils.format(date, "yyyy年");
    }

    /**
     * 格式化date中月
     *
     * @return 类似 5月 的String.
     */
    public static final String formatMonth(Date date) {
        return DateFormatUtils.format(date, "M月");
    }

    /**
     * 格式化date中月
     *
     * @return 类似 5日 的String.
     */
    public static final String formatDay(Date date) {
        return DateFormatUtils.format(date, "d日");
    }

    /**
     * 格式化date中的年月
     *
     * @return 类似 2017年5月 的String.
     */
    public static final String formatYearMonth(Date date) {
        return DateFormatUtils.format(date, "yyyy年M月");
    }

    /**
     * 格式化 date 中的 年月日 部分.
     *
     * @return 类似 2017年5月3日 的String
     */
    public static final String formatDate(Date date) {
        return DateFormatUtils.format(date, "yyyy年M月d日");
    }

    /**
     * 格式化 开始日期和结束日期
     *
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 类似 2017年3月4日 -- 2017年7月22日 的String
     */
    public static final String formatDateDuration(Date startDate, Date endDate) {
        String patten = "yyyy年M月d日";
        return DateFormatUtils.format(startDate, patten) + " -- "
                + DateFormatUtils.format(endDate, patten);
    }

    /**
     * 生成报表文件名称中的时间部分.
     *
     * @return 类似 20170215105903444 的字符串. 表示2017年02月15日10点59分03秒444毫秒
     */
    public static final String getReportFileNameTimestamp() {
        return DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS");
    }

    /**
     * 生成报表文件名称中的时间部分.
     *
     * @return 类似 20170215105903444 的字符串. 表示2017年02月15日10点59分03秒444毫秒
     */
    public static final String getReportFileNameDate(Date d) {
        return DateFormatUtils.format(d, "yyyyMMdd");
    }
    /**
     * 生成报表文件名称中的时间部分.
     *
     * @return 类似 20170215105903444 的字符串. 表示2017年02月15日10点59分03秒444毫秒
     */
    public static final String getReportFileNameTimestamp(Date d) {
        return DateFormatUtils.format(d, "yyyy-MM-dd HHmmss");
    }

    public static final String getFileNameTimestamp(Date d) {
        return DateFormatUtils.format(d, "yyyyMMddHHmmss");
    }
    /**
     * 生成报表文件名称中的时间部分.
     *
     * @return 类似 20170215105903444 的字符串. 表示2017年02月15日10点59分03秒444毫秒
     */
    public static final String getTimeNameTimestamp(Date d) {
        return DateFormatUtils.format(d, "yyyy-MM-dd HH:mm:ss");
    }

    public static final String getTimeNameDate(Date d) {
        return DateFormatUtils.format(d, "yyyy-MM-dd");
    }

    public static final String getTimeNameTime(Date d) {
        return DateFormatUtils.format(d, "HHmmss");
    }

}
