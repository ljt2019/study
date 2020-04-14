import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @description: 时间处理工具类
 *  org.apache.commons.lang3.time.DateUtils
 *  org.apache.commons.lang3.time.DateFormatUtils
 * @author: tiger
 * @create: 2020-04-14 22:27
 */
public class DateTimeUtils {

    public final static  int[]  SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
    private final static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

    /**
     * 获得日期格式时间格式对象
     *
     * @return
     */
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 求两个日期之间的天数
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static int getIntervalDays(Date sDate, Date eDate) {
        if (sDate == null || eDate == null) {
            return -1;
        }
        long intervalMilli = eDate.getTime() - sDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取当前系统时间的日期，不包含时分秒
     *
     * @return
     */
    public static Date getTodayDate() {
        Date parse = null;
        try {
            parse = getDateFormat().parse(getDateFormat().format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 求两个日期之间的月数
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static int getIntervalMonths(Date sDate, Date eDate) {
        if (sDate == null || eDate == null) {
            return -1;
        }
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sDate);
        aft.setTime(eDate);

        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 根据日期获取年龄
     *
     * @param sDate
     * @return
     */
    public static int getAge(Date sDate) {
        if (sDate == null) {
            return -1;
        }
        // Date now = new Date();
        // int dayCount = getIntervalDays(sDate, now);
        // return new BigDecimal(dayCount).divide(new BigDecimal(365), 0,
        // BigDecimal.ROUND_CEILING).intValue();

        Calendar calendar = Calendar.getInstance();
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setTime(sDate);

        return calendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);

    }

    public static int getAgeBirthday(String birthday) {
        Date birthDay = DateTimeUtils.paseDate(birthday, "yyyy-MM-dd");
        return getAge(birthDay);
    }

    /**
     * 根据样式解析字符串为时间
     *
     * @param dateStr
     * @param patten
     * @return
     */
    public static Date paseDate(String dateStr, String patten) {

        SimpleDateFormat format = new SimpleDateFormat(patten);
        try {
            Date date = format.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 减少日期单位是天
     *
     * @param dateStr 被减时间
     * @param patten  天数
     * @return
     */
    public static Date subDate(Date dateStr, Integer patten) {

        if (dateStr == null) {
            dateStr = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dateStr);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - patten);
        Date time = c.getTime();
        return time;
    }

    /**
     * 得到unix时间戳，可直接存库表用
     *
     * @return
     */
    public static int getUnixTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 转换成Unix时间戳
     *
     * @param date
     * @return
     */
    public static int getUnixTimeStamp(Date date) {
        return (int) (date.getTime() / 1000);
    }

    /**
     * 转换成Unix时间戳
     *
     * @param date
     * @return
     */
    public static int getUnixTimeStamp(Calendar date) {
        return (int) date.getTimeInMillis();
    }

    /**
     * unix时间戳转换成java时间
     *
     * @param timeStamp
     * @return
     */
    public static Date unixTimeStamp2Date(int timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000l);
        return calendar.getTime();
    }

    /**
     * 根据分数返回对应的字符串时分 00:00格式
     *
     * @param minute
     * @return
     */
    public static String getTimeStrByMinute(Integer minute) {
        if (minute == null || minute < 0) {
            return "00:00";
        }
        Integer hour = minute / 60;
        hour %= 24;
        minute = minute % 60;
        return (hour < 10 ? "0" + hour : hour + "") + ":" + (minute < 10 ? "0" + minute : minute + "");
    }

    /**
     * 增加秒数
     *
     * @param date
     * @param
     * @return
     */
    public static Date addSeconds(Date date, int seCond) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seCond);
        return calendar.getTime();
    }

    /**
     * 增加天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 增加小时
     *
     * @param date
     * @param
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 增加月份
     *
     * @param date
     * @param
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 增加分钟
     *
     * @param date
     * @param
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 增加年份
     *
     * @param date
     * @param
     * @return
     */
    public static Date addYears(Date date, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 描述：两日期比较的差异值
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateDiff(Date date1, Date date2) {
        long date1ms = date1.getTime();
        long date2ms = date2.getTime();
        return date2ms - date1ms;
    }

    /**
     * 描述：两日期比较的差异值
     *
     * @param interval 可以比较的对象为 ：yyyy 年； m 月 ； d 日 ；w: 该月的第几个星期 ; ww: 年中的第几个星期; h
     *                 小时; n 分钟 ; s 秒;
     * @param dDate1
     * @param dDate2
     * @return
     */
    public static long dateDiff(String interval, Date dDate1, Date dDate2) {
        int desiredField = 0;
        int coef = 1;
        Date date1;
        Date date2;
        if (dDate1.getTime() > dDate2.getTime()) {
            coef = -1;
            date1 = dDate2;
            date2 = dDate1;
        } else {
            date1 = dDate1;
            date2 = dDate2;
        }
        int field;
        if (interval.equals("yyyy"))
            field = 1;
        else if (interval.equals("m"))
            field = 2;
        else if (interval.equals("d"))
            field = 5;
        else if (interval.equals("y"))
            field = 5;
        else if (interval.equals("w"))
            field = 4;
        else if (interval.equals("ww"))
            field = 3;
        else if (interval.equals("h")) {
            field = 5;
            desiredField = 11;
        } else if (interval.equals("n")) {
            field = 5;
            desiredField = 12;
        } else if (interval.equals("s")) {
            field = 5;
            desiredField = 13;
        } else {
            return -1L;
        }
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(date1);
        long nbOccurence = 0L;
        calTmp.add(field, 1);
        for (Date dateTemp = calTmp.getTime(); dateTemp.getTime() <= date2.getTime(); ) {
            calTmp.add(field, 1);
            dateTemp = calTmp.getTime();
            nbOccurence++;
        }

        if (desiredField == 11 || desiredField == 12 || desiredField == 13) {
            calTmp.setTime(date1);
            calTmp.add(field, (int) nbOccurence);
            Date dateTemp = calTmp.getTime();
            switch (desiredField) {
                case 11:
                    nbOccurence *= 24L;
                    break;

                case 12:
                    nbOccurence = nbOccurence * 24L * 60L;
                    break;

                case 13:
                    nbOccurence = nbOccurence * 24L * 60L * 60L;
                    break;
            }
            calTmp.add(desiredField, 1);
            for (dateTemp = calTmp.getTime(); dateTemp.getTime() <= date2.getTime(); ) {
                calTmp.add(desiredField, 1);
                dateTemp = calTmp.getTime();
                nbOccurence++;
            }

        }
        return nbOccurence * (long) coef;
    }

    /**
     * 描述：两个日期之间的天数
     */
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue() + 1;

        return days;
    }

    /**
     * 获取时间几号
     */
    public static int getDayNum(Date beginDate) {

        if (beginDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(beginDate);

        return instance.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间几月
     */
    public static int getMonthNum(Date beginDate) {

        if (beginDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(beginDate);
        // Calendar中 get(Calendar.MONTH) 得到的月份是从0开始计算的
        return instance.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取时间几时
     */
    public static int getHourNum(Date beginDate) {

        if (beginDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(beginDate);

        return instance.get(Calendar.HOUR);
    }

    /**
     * 获取时间几分
     */
    public static int getMinuteNum(Date beginDate) {

        if (beginDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(beginDate);

        return instance.get(Calendar.MINUTE);
    }

    /**
     * 比较两个日期志坚日期的先后
     */
    public static int compareTwoDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("compareTwoDate param is null!");
        }
        Integer a = null;
        Calendar instance = Calendar.getInstance();
        instance.setTime(date1);
        int sumDate1 = instance.get(Calendar.YEAR) + instance.get(Calendar.MONTH) + 1
                + instance.get(Calendar.DAY_OF_MONTH);
        instance.setTime(date2);
        int sumDate2 = instance.get(Calendar.YEAR) + instance.get(Calendar.MONTH) + 1
                + instance.get(Calendar.DAY_OF_MONTH);
        if (sumDate1 > sumDate2) {
            a = 1;
        } else if (sumDate1 == sumDate2) {
            a = 0;
        } else if (sumDate1 < sumDate2) {
            a = -1;
        }
        return a;
    }

    /**
     * 描述：格式化为自定义格式
     *
     * @param d      日期
     * @param format 格式
     * @return
     */
    public static String formatDate(Date d, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    /**
     * 获得指定日的后一天日期(按月计算)
     *
     * @param d
     * @return
     */
    public static Date getAfterDay(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 获得指定日的前一天日期(按月计算)
     *
     * @param d
     * @return
     */
    public static Date getBeforeDay(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.getTime();
    }

    /**
     * 获得指定时间的前n分钟
     *
     * @param d
     * @return
     */
    public static Date getBeforeMinute(Date d, int n) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, -n);
        return cal.getTime();
    }

    /**
     * 当前一天的开始时间
     *
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期的开始时间
     *
     * @param d
     * @return
     */
    public static Date getDayBegin(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date ds = new Date(cal.getTimeInMillis());
        return ds;

    }

    /**
     * 指定日期向前返回已小时开始的时间 eg: 2018-03-01 12:30 return 2018-03-01 12:00
     *
     * @param d
     * @return
     */
    public static Date getHourBegin(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date ds = new Date(cal.getTimeInMillis());
        return ds;

    }

    /**
     * 当前一天的结束时间
     *
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期的结束时间
     *
     * @return
     */
    public static Date getDayEnd(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 一天前的几点
     *
     * @return
     */
    public static Date subDayByHour(Date date, Integer d) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, -1);
        if (d != null) {
            instance.set(Calendar.HOUR_OF_DAY, d);
            instance.set(Calendar.MINUTE, 0);
            instance.set(Calendar.SECOND, 0);
            instance.set(Calendar.MILLISECOND, 0);
        }
        return instance.getTime();
    }

    /**
     * 减少秒数
     *
     * @return
     */
    public static Date subDayBySecond(Date date, Integer d) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.SECOND, d);
        return instance.getTime();
    }

    /**
     * 当周第一天
     *
     * @return
     */
    public static Date getFirstDayOfCurWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 当周最后一天
     *
     * @return
     */
    public static Date getEndDayOfCurWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return getAfterDay(cal.getTime());
    }

    /**
     * 当周第一天
     *
     * @return
     */
    public static Date getFirstDayOfCurWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 当周最后一天
     *
     * @return
     */
    public static Date getEndDayOfCurWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return getAfterDay(cal.getTime());
    }

    /**
     * 当月第一天
     */
    public static Date getFirstDayOfCurMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return getDayBegin(cal.getTime());
    }

    /**
     * 指定月第一天
     */
    public static Date getFirstDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return getDayBegin(cal.getTime());
    }

    /**
     * 当月最后一天
     */
    public static Date getEndDayOfCurMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return getDayBegin(cal.getTime());
    }

    /**
     * 指定月最后一天
     */
    public static Date getEndDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return getDayBegin(cal.getTime());
    }

    /**
     * 当前季度的第一天
     */
    public static Date getFirstDayOfCurSeason() {
        Calendar cal = Calendar.getInstance();
        int sean = getSeason(cal.get(Calendar.MONTH));
        cal.set(Calendar.MONTH, sean * 3 - 3);
        cal.set(Calendar.DATE, 1);
        return getDayBegin(cal.getTime());
    }

    /**
     * 当前季度的最后一天
     */
    public static Date getEndDayOfCurSeason() {
        Calendar cal = Calendar.getInstance();
        int sean = getSeason(cal.get(Calendar.MONTH));
        cal.set(Calendar.MONTH, sean * 3 - 1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 指定年份的第一天
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayBegin(cal.getTime());
    }

    /**
     * 一年的最后一天
     *
     * @param
     * @return
     */
    public static Date getEndDateOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);

        return getDayBegin(cal.getTime());
    }

    /**
     * 指定日期所在年份第一天
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayBegin(cal.getTime());
    }

    /**
     * 一年的最后一天
     *
     * @param date
     * @return
     */
    public static Date getEndDateOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);

        return getDayBegin(cal.getTime());
    }

    /**
     * 指定日期所在季度的第一天
     */
    public static Date getFirstDayOfSeason(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = getSeason(cal.get(Calendar.MONTH));
        cal.set(Calendar.MONTH, sean * 3 - 3);
        cal.set(Calendar.DATE, 1);
        return getDayBegin(cal.getTime());
    }

    /**
     * 指定日期所在季度的最后一天
     */
    public static Date getEndDayOfSeason(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = getSeason(cal.get(Calendar.MONTH));
        cal.set(Calendar.MONTH, sean * 3 - 1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return getDayBegin(cal.getTime());
    }

    /**
     * 根据月分返回季度
     *
     * @param mouth
     * @return
     */
    public static int getSeason(int mouth) {
        return SEASON[mouth];
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 最大日期
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date1;
        }
        return date2;
    }

    /**
     * 最小日期
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date min(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date2;
        }
        return date1;
    }

    /**
     * 获取 yyyy-m
     *
     * @param date
     * @return
     */
    public static String getYearMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "-" + month;
    }

    /**
     * 获取 yyyymm
     *
     * @param date
     * @return
     */
    public static String getYearMonth2(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr = String.valueOf(month);
        return monthStr.length() == 1 ? year + "0" + month : year + "" + month;
    }

    /**
     * 获取 年-周
     *
     * @param date
     * @return
     */
    public static String getYearWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return year + "-" + week;
    }

    /**
     * 周中的星期一
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 周中的星期天
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 查询当前时间是否是营业时间
     *
     * @param openTime  开店时间
     * @param closeTime 关店时间
     * @param date      当前时间（如果为空则取服务器当前时间）
     * @return
     */
    public static boolean isInBusiness(Time openTime, Time closeTime, Date date) {
        boolean isInBusiness = false;
        if (openTime == null || closeTime == null || openTime.compareTo(closeTime) == 0) {
            return isInBusiness;
        }

        // 若时间为空，则取当前时间
        if (date == null) {
            date = new Date();
        }

        // 转换成Time格式
        Time thisTime = new Time(date.getHours(), date.getMinutes(), date.getSeconds());

        if (openTime.compareTo(closeTime) < 0) {
            // 当openTime < closeTime 时，当前时间在 openTime~closeTime区间为营业中
            if (thisTime.compareTo(openTime) >= 0 && thisTime.compareTo(closeTime) <= 0) {
                isInBusiness = true;
            }
        } else {
            // 当openTime > closeTime
            // 时，当前时间在openTime~23:59:59或00:00:00~closeTime为营业中
            Time dayEndTime = Time.valueOf("23:59:59");
            Time dayBeginTime = Time.valueOf("00:00:00");
            if ((thisTime.compareTo(openTime) >= 0 && thisTime.compareTo(dayEndTime) <= 0)
                    || (thisTime.compareTo(dayBeginTime) >= 0 && thisTime.compareTo(closeTime) <= 0)) {
                isInBusiness = true;
            }
        }
        return isInBusiness;
    }

    /**
     * 获取距离关店时间还有多少分钟
     *
     * @param openTime  开店时间
     * @param closeTime 关店时间
     * @param date      当前时间（如果为空则取服务器当前时间）
     * @return
     */
    public static int getTimeToCloseTime(Time openTime, Time closeTime, Date date) {
        long toCloseTimeMills = 0;
        if (openTime == null || closeTime == null || openTime.compareTo(closeTime) == 0) {
            return (int) toCloseTimeMills;
        }
        // 若时间为空，则取当前时间
        if (date == null) {
            date = new Date();
        }
        // 转换成Time格式
        Time thisTime = new Time(date.getHours(), date.getMinutes(), date.getSeconds());

        long openTimeLongVal = openTime.getTime();
        long closeTimeLongVal = closeTime.getTime();
        long thisTimeLongVal = thisTime.getTime();

        if (openTimeLongVal > closeTimeLongVal) {
            // 开店时间 > 关店时间 ，则必须把关店时间的日期天数+1
            closeTimeLongVal = closeTimeLongVal + 24 * 60 * 60 * 1000;
            if (openTimeLongVal > thisTimeLongVal) {
                // 开店时间 > 当前时间 ，则必须把关店时间的日期天数+1
                thisTimeLongVal = thisTimeLongVal + 24 * 60 * 60 * 1000;
            }
        }
        // 当前时间距离关店时间的时间差 = 关店时间 - 当前时间
        toCloseTimeMills = closeTimeLongVal - thisTimeLongVal;
        int toCloseTimeMinutes = (int) (toCloseTimeMills / 1000 / 60);
        return toCloseTimeMinutes;
    }

    public static Integer getIntegerWeekDay(Date time) {
        if (time == null) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        Integer week = null;
        switch (dayOfWeek) {
            case 1:
                week = 7;
                break;
            case 2:
                week = 1;
                break;
            case 3:
                week = 2;
                break;
            case 4:
                week = 3;
                break;
            case 5:
                week = 4;
                break;
            case 6:
                week = 5;
                break;
            case 7:
                week = 6;
                break;
            default:
                break;
        }
        return week;
    }

    // 日期格式化
    public static String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String arEndSportString(Date datetime) {
        if (datetime == null) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(datetime);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;

            default:
                break;
        }
        String temp = formatDate(datetime, " HH:mm M月d日 yyyy");
        String outString = week + temp;
        return outString;
    }

    public static String weekStr(Date datetime) {
        if (datetime == null) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(datetime);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;

            default:
                break;
        }

        return week;
    }

    /**
     * @return
     */
    public static Date beforYearDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);

        return cal.getTime();
    }

    public static String paseMinute(int h) {
        String hour = null;
        if (h == 0) {
            hour = "00";
        } else if (h < 10) {
            hour = "0" + h;
        } else {
            hour = h + "";
        }
        return hour;
    }

    /**
     * 设置当天小时
     *
     * @return
     */
    public static Date setCurrentDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);// 时
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置当天小时
     *
     * @return
     */
    public static Date setCurrentDate(int hour, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);// 时
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置传入时间的时分
     *
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public static Date setHourAndMin(Date date, Integer hour, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Optional.ofNullable(hour).orElse(0));// 时
        calendar.set(Calendar.MINUTE, Optional.ofNullable(minute).orElse(0));// 分
        return calendar.getTime();
    }

    public static Date setCurrentDate(String dicValue) {
        Date parse = null;
        try {
            parse = new SimpleDateFormat("HH:mm:ss").parse(dicValue);
        } catch (ParseException e) {
            logger.info("时间转换异常" + e.getMessage());
        }
        Calendar resetDate = Calendar.getInstance();
        resetDate.setTime(parse);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, resetDate.get(Calendar.HOUR_OF_DAY));// 时
        calendar.set(Calendar.MINUTE, resetDate.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, resetDate.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // sportTime 秒
    public static String paseTimeStr(Long sportTime) {

        if (sportTime != null && sportTime != 0) {
            Long h = sportTime / 3600;
            Long m = sportTime % 3600 / 60;
            Long s = sportTime % 3600 % 60;
            String hour = DateTimeUtils.paseMinute(h.intValue());
            String minute = DateTimeUtils.paseMinute(m.intValue());
            String second = DateTimeUtils.paseMinute(s.intValue());
            return hour + ":" + minute + ":" + second;
        } else {
            return "00:00:00";
        }
    }

    /**
     * 时间戳转换成日期格式字符串，默认格式：【yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static String timeStamp2Date(Long millisecond, String format) {
        if (millisecond == null || String.valueOf(millisecond).isEmpty()) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(millisecond));
    }

    /**
     * 将秒转换成分钟
     *
     * @param sportTime
     * @return
     */
    public static String getMinute(Long sportTime) {

        if (sportTime != null && sportTime != 0) {
            Long m = sportTime / 60;
            String minute = DateTimeUtils.paseMinute(m.intValue());
            return minute;
        } else {
            return "0";
        }
    }

    public static String paseTimeStrMinute(Integer minute) {
        if (minute == null || minute == 0) {
            return "00:00:00";
        }
        return paseTimeStr(minute * 60L);
    }

    public static String paseShortTimeStr(Long sportTime) {
        String temp = paseTimeStr(sportTime);
        int index = temp.lastIndexOf(":");
        return temp.substring(0, index);
    }

    /**
     * 获取当前时间的上一周
     *
     * @param date ：当前时间点
     * @return ：字符串 年-周
     */
    public static String getLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null == date) {
            date = new Date();
        }
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR) - 1;
        return year + "-" + week;
    }

    /**
     * 获取上一周的周一
     *
     * @param date ：当前时间
     * @return
     */
    public static Date getBeginLastWeek(Date date) {
        return getFirstDayOfWeek(getLastWeekend(date));
    }

    /**
     * 获取上一周的 周末
     *
     * @param date ：当前时间
     * @return
     */
    public static Date getEndLastWeek(Date date) {
        return getEndDayOfWeek(getLastWeekend(date));
    }

    /**
     * 根据当前时间获取到 上周的 周末（周一为第一天）
     *
     * @param date
     * @return
     */
    private static Date getLastWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null == date) {
            date = new Date();
        }
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -dayOfWeek);
        return calendar.getTime();
    }

    public static String paseDeviceMinute(Integer time) {
        if (time != null) {
            String h = "";
            String m = "";
            if ((time / 60) < 10) {
                h = "0" + (time / 60);
            } else {
                h = time / 60 + "";
            }

            if ((time % 60) < 10) {
                m = "0" + (time % 60);
            } else {
                m = time % 60 + "";
            }
            return h + ":" + m + ":00";
        } else
            return "00:00:00";

    }

    /**
     * 自然周第一天（周一） 开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartDateOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        // 因是自然周，周日向前移一天
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date = getBeforeDay(date);
        }
        cal.setTime(date);
        cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDayBegin(cal.getTime());
    }

    /**
     * 当前周，自然周（周日），结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndDateOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        // 因是自然周，周日向前移一天
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date = getBeforeDay(date);
        }
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return getDayEnd(getAfterDay(cal.getTime()));
    }

    /**
     * 由输入的年周字符串获取显示的字符串 "2018-10" -> "2018.03.05-2018.03.11"
     * 获取做在周区间
     * @param dateKey
     * @return
     */
    public static String getRankWeekStr(String dateKey) {
        if (StringUtils.isBlank(dateKey)) {
            return "";
        }
        String[] arr = dateKey.split("-");
        int year = Integer.parseInt(arr[0]);
        int week = Integer.parseInt(arr[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        Date day = cal.getTime();
        return formatDate(getStartDateOfWeek(day), "yyyy.MM.dd") + "-"
                + formatDate(getEndDateOfWeek(day), "yyyy.MM.dd");

    }

    /**
     * 将 分 转换成 时 分 秒
     *
     * @param sportDuration ： 分钟
     * @return ： hh:mm:ss
     */
    public static String changeTime(Integer sportDuration, Boolean secondes) {
        StringBuffer sb = new StringBuffer();
        // 将分钟转为 秒
        Integer secodes = sportDuration * 60;
        // 转为时
        int hour = secodes / 3600;
        // 获取到 分
        secodes = secodes % 3600;
        int minuts = secodes / 60;
        secodes = secodes % 60;

        if (secondes) {
            return sb.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minuts)).append(":")
                    .append(String.format("%02d", secodes)).toString();
        } else {
            return sb.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minuts)).toString();
        }

    }

    /**
     * 将 秒 转换成 时 分 秒
     *
     * @param sportDuration ： 秒
     * @return ： hh:mm:ss
     */
    public static String changeTime2(Integer sportDuration, Boolean secondes) {
        StringBuffer sb = new StringBuffer();
        Integer secodes = sportDuration;
        // 转为时
        int hour = secodes / 3600;
        // 获取到 分
        secodes = secodes % 3600;
        int minuts = secodes / 60;
        secodes = secodes % 60;

        if (secondes) {
            return sb.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minuts)).append(":")
                    .append(String.format("%02d", secodes)).toString();
        } else {
            return sb.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minuts)).toString();
        }

    }

    public static String changeMinSec(int duration) {
        int min = duration / 60;
        int seconds = duration % 60;
        return new StringBuffer().append(String.format("%02d", min)).append(":").append(String.format("%02d", seconds))
                .toString();
    }

    /**
     * yyyy-MM-dd to yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static String getDateCN(String date) {
        Date _date = DateTimeUtils.paseDate(date, "yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(_date);
    }

    /**
     * 获取同一年内的随机日期
     *
     * @param date
     * @return
     */
    public static String getRandomDate(String date) {
        Date _date = DateTimeUtils.paseDate(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(_date);
        Random rand = new Random();
        int month = 1;
        int day = rand.nextInt(27) + 1;
        for (int i = 0; i < 12; i++) {
            month = rand.nextInt(12) + 1;
            if (month != (cal.get(Calendar.MONTH) + 1)) {
                break;
            }
        }
        String result = "";
        result += cal.get(Calendar.YEAR);
        result += "-";
        result += (month < 10) ? "0" + month : month;
        result += "-";
        result += (day < 10) ? "0" + day : day;
        return result;
    }

    /**
     * 根据年月，日，时，分，秒返回对应的日期
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date constituteDate(Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                      Integer second) {
        if (year == null) {
            year = 0;
        }
        if (month == null) {
            month = 0;
        }
        if (day == null) {
            day = 0;
        }
        if (hour == null) {
            hour = 0;
        }
        if (minute == null) {
            minute = 0;
        }
        if (second == null) {
            second = 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second);
        return cal.getTime();
    }

    /**
     * 根据传入的年月返回此月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static Integer getMonthToDay(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回传入时间对应的当天分钟数
     *
     * @param date
     * @return
     */
    public static Integer getDayTOMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
    }

    /**
     * 根据传入的日期判断是否是本周内日期 周一 --- 周日
     *
     * @param date
     * @return
     */
    public static boolean isThisWeek(Date date) {
        date = subDate(date, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(subDate(getTodayDate(), 1));
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.clear();
        calendar.setTime(subDate(date, 1));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int paramYear = calendar.get(Calendar.YEAR);
        if (paramWeek == currentWeek && paramYear == currentYear) {
            return true;
        }
        return false;
    }

    /**
     * 判断传入的日期是否是本月内日期
     *
     * @param date
     * @return
     */
    public static Boolean isThisMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String param = sdf.format(date);// 参数时间
        String now = sdf.format(new Date());// 当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 返回传入时间所在周的周i时间(不带时分秒)，按照 周一 -- 周日 为一周
     *
     * @param date
     * @param i    对应的星期几 如要获得周一传 1 获得周日传 7 传入其他数字返回null
     * @return
     */
    public static Date getMonday(Date date, Integer i) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day - 1);
        cal.add(Calendar.DATE, i);
        Date parse = null;
        try {
            parse = getDateFormat().parse(getDateFormat().format(cal.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parse;
    }

    /**
     * 返回传入日期中月的最后一天的日期（yyyy-MM-dd）
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date parse = null;
        try {
            parse = getDateFormat().parse(getDateFormat().format(cal.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 把毫秒级时间戳转换成yyyy-mm-dd hh:mm:ss格式的时间
     *
     * @throws ParseException
     */
    public static String getTimeStr(String timeStamp) throws ParseException {
        Long time = Long.parseLong(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 要转换的时间格式
        Date date;
        date = sdf.parse(sdf.format(time));
        return sdf.format(date);
    }

    /**
     * 根据传入日期获取此日期所在月的最后一天日期，如果此日期是本月则返回今天日期
     *
     * @param date
     * @return
     */
    public static Date getBackMostMonthOrNow(Date date) {
        if (DateTimeUtils.isThisMonth(date)) {
            // 是本月
            return getTodayDate();
        } else {
            // 不是本月,判断结束日期是否大于目前时间
            if (date.getTime() > System.currentTimeMillis()) {
                return getTodayDate();
            } else {
                // 把结束日期定位到结束日期对应的月的最后一天
                return DateTimeUtils.getMonthLastDay(date);
            }
        }
    }

    /**
     * 根据传入日期获取此日期所在周的最后一天日期，如果此日期是本周则返回今天日期
     *
     * @param date
     * @return
     */
    public static Date getBackWeekMonthOrNow(Date date) {
        if (DateTimeUtils.isThisWeek(date)) {
            // 是本周
            return getTodayDate();
        } else {
            // 不是本周,判断结束日期是否大于目前时间
            if (date.getTime() > System.currentTimeMillis()) {
                return getTodayDate();
            } else {
                // 把结束日期定位到结束日期对应的周的最后一天
                return DateTimeUtils.getMonday(date, 7);
            }
        }
    }

    /**
     * 把指定日期转换成"YYYY-MM-DD hh:mm:ss"格式
     *
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        StringBuilder str = new StringBuilder();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        String monthStr = String.valueOf(instance.get(Calendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        String dayStr = String.valueOf(instance.get(Calendar.DAY_OF_MONTH));
        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        String hourStr = String.valueOf(instance.get(Calendar.HOUR_OF_DAY));
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        String minuteStr = String.valueOf(instance.get(Calendar.MINUTE));
        if (minuteStr.length() == 1) {
            minuteStr = "0" + minuteStr;
        }
        String secondStr = String.valueOf(instance.get(Calendar.SECOND));
        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        str.append(instance.get(Calendar.YEAR)).append("-").append(monthStr).append("-").append(dayStr).append(" ")
                .append(hourStr).append(":").append(minuteStr).append(":").append(secondStr);
        return str.toString();
    }

    /**
     * 获取开始时间和结束时间相差的分钟数
     *
     * @param startDate ：开始时间
     * @param endDate   ：结束时间
     * @return ：四舍五入后的分钟数
     */
    public static int getDiffMin(Date startDate, Date endDate) {
        long endDateTime = endDate.getTime();
        long startDateTime = startDate.getTime();
        long diffTime = endDateTime - startDateTime;
        return (int) (diffTime % 60 == 0 ? diffTime / 60 : diffTime / 60 + 1);
    }

    /**
     * 判断时间格式
     *
     * @param date
     * @return
     */
    public static String getDatetimeFormat(String date) {
        date = date.trim();
        String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";// yyyyMMddHHmmss
        String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";// yyyyMMdd
        String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";// yyyy-MM-dd HH:mm:ss
        String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";// yyyy-MM-dd
        String a5 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";// yyyy-MM-dd HH:mm

        boolean datea1 = Pattern.compile(a1).matcher(date).matches();

        if (datea1) {
            return "yyyyMMddHHmmss";
        }
        boolean datea2 = Pattern.compile(a2).matcher(date).matches();
        if (datea2) {
            return "yyyyMMdd";
        }
        boolean datea3 = Pattern.compile(a3).matcher(date).matches();
        if (datea3) {
            return "yyyy-MM-dd HH:mm:ss";
        }
        boolean datea4 = Pattern.compile(a4).matcher(date).matches();
        if (datea4) {
            return "yyyy-MM-dd";
        }
        boolean datea5 = Pattern.compile(a5).matcher(date).matches();
        if (datea5) {
            return "yyyy-MM-dd HH:mm";
        }
        return "";
    }

    /**
     * 判断时间格式
     *
     * @param date
     * @return
     */
    public static boolean judgeDatetimeFormat(String date) {
        date = date.trim();
        String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";// yyyyMMddHHmmss
        String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";// yyyyMMdd
        String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";// yyyy-MM-dd HH:mm:ss
        String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";// yyyy-MM-dd
        String a5 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";// yyyy-MM-dd HH:mm
        boolean datea1 = Pattern.compile(a1).matcher(date).matches();
        boolean datea2 = Pattern.compile(a2).matcher(date).matches();
        boolean datea3 = Pattern.compile(a3).matcher(date).matches();
        boolean datea4 = Pattern.compile(a4).matcher(date).matches();
        boolean datea5 = Pattern.compile(a5).matcher(date).matches();
        if (datea5 || datea4 || datea3 || datea2 || datea1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算出两个日期之间的所有日期
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static List<String> dateDivide(String startDateStr, String endDateStr) {
        List<String> days = new ArrayList<>();//yyyy-MM-dd格式
        List<String> formatDays = new ArrayList<>();// yyyyMMdd格式
        days.add(startDateStr);// 添加第一个日期

        try {
            // 1.转换格式
            SimpleDateFormat sdf = getDateFormat();
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            // 2.计算两日期之间的差值
            int day = getIntervalDays(startDate, endDate);
            if (day == -1) {
                return null;
            }
            day -= 1;

            // 3.获取之间的日期
            for (int i = 1; i <= day; i++) {
                Date date = addDays(startDate, i);
                days.add(String.valueOf(sdf.format(date)));
            }
            if (!startDateStr.equals(endDateStr)) {
                days.add(endDateStr);// 添加最后一个日期
            }
            //4.格式转换
            for (String dayItem : days) {
                formatDays.add(dayItem.substring(0, 4) + dayItem.substring(5, 7) + dayItem.substring(8));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDays;
    }

    public static void main(String[] args) {
        dateDivide("2018-10-11", "2018-10-16");
    }
}
