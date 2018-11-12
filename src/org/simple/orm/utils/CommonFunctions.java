package org.simple.orm.utils;

import java.util.List;

/**
 * 通用方法操作类
 * @author boge.peng
 * @version 0.8
 */
public final class CommonFunctions {
	public static <T> boolean isEmpty(T obj) {
		return obj == null || obj.toString() == null || obj.toString().trim() == "";
	}
	
	public static <E> boolean isEmpty(List<E> obj) {
		return obj == null || obj.isEmpty();
	}
	
	public static <T> boolean isEmpty(T[] obj) {
		return obj == null || obj.length == 0;
	}
}
