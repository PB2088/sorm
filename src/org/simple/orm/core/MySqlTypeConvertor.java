package org.simple.orm.core;

import java.io.IOException;
import java.util.Properties;

public class MySqlTypeConvertor implements TypeConvertor {
	private static Properties pros = null;
	
	static {
		try {
			pros = new Properties();
			
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mysql_dataType.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String databaseTypeConvJavaType(String columnType) {
		return 	columnType == null ?
				"":pros.getProperty(columnType.toUpperCase());
	}

	@Override
	public String javaTypeConvDatabaseType(String javaDataType) {
		return null;
	}

	public static void main(String[] args) {
		System.out.println(new MySqlTypeConvertor().databaseTypeConvJavaType("INT UNSIGNED".replaceAll("\\s", "_")));
	}
}
