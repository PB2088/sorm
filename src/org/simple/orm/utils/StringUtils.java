package org.simple.orm.utils;

/**
 * 字符串常用工具类
 * @author boge.peng
 * @version 0.8
 */
public final class StringUtils {
	public static String firstChart2UpperCase(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}	
}
