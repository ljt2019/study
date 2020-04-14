package com.tiger;
/**
 * @description: 常用工具方法
 * @author: tiger
 * @create: 2020-04-14 22:27
 */
public class CommonUtils {
	
    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }
	
	/**
     * 比较时间的方法
     * thisDay.after(anotherDay)【java.util.Date.after()】
     * thisDay.compareTo(anotherDay) >= 0  【java.util.Date.compareTo()】
     * 
     */
	 


}

