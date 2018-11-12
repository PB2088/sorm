package org.simple.orm.utils;

import static org.simple.orm.utils.StringUtils.firstChart2UpperCase;

import java.lang.reflect.Method;

/**
 * 反射常用操作工具
 * @author boge.peng
 * @version 0.8
 */
public final class ReflectUtils {
	/**
	 * 调用obj对象对应属性fieldName的get方法
	 * @param obj
	 * @param fieldName
	 * @return 返回方法运行的值
	 */
	@SuppressWarnings("all")
	public static <T> Object invokeGetMethod(T obj,String fieldName) {
		try {
			
			Method method = obj.getClass().getMethod("get"+firstChart2UpperCase(fieldName),null);
			
			return method.invoke(obj, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> void invokeSetMethod(T obj,String fieldName,Object value) {
		try {
			Method method = obj.getClass().getMethod("set"+firstChart2UpperCase(fieldName), value.getClass());
			
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
