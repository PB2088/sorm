package org.simple.orm.core;

/**
 * 负责java数据类型和数据库类型的相互转换
 * @author boge.peng
 * @version 0.8
 */
public interface TypeConvertor {
	/**
	 * 数据库数据类型转换为java数据类型
	 * @param columnType
	 * @return
	 */
	public String databaseTypeConvJavaType(String columnType);
	
	/**
	 * java数据类型转换为数据库数据类型
	 * @param javaDataType
	 * @return
	 */
	public String javaTypeConvDatabaseType(String javaDataType);
}
