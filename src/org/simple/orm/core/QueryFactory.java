package org.simple.orm.core;

/**
 * 创建Query对象的工厂类
 * @author boge.peng
 * @version 0.8
 */
public class QueryFactory {
		
	private static Query prototypeObj;
	
	private QueryFactory() {
		
	}
	
	static {
		try {
			 Class<?> c = Class.forName(DBManager.getConfig().getQueryClass());
			 prototypeObj = (Query) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Query createQuery() {
		try {
			return (Query) prototypeObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
}
