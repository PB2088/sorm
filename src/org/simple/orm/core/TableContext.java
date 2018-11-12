package org.simple.orm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.simple.orm.bean.ColumnInfo;
import org.simple.orm.bean.TableInfo;
import static org.simple.orm.utils.JavaFileUtils.*;
import static org.simple.orm.utils.StringUtils.*;

import com.google.common.base.CaseFormat;

/**
 * 管理数据库所有表结构和类结构的关系,并可以根据表结构生成类结构
 * @author peng.bo
 * @version 0.8
 */
public class TableContext {
	/**
	 * 所有的表信息:表名为kye,表信息为value
	 */
	private static Map<String, TableInfo> tables = new HashMap<>();
	
	private static Map<Class<?>, TableInfo> poClassTableMap = new HashMap<>();
	
	static {
		try {
			Connection conn = DBManager.getConn();
			DatabaseMetaData dbmd = conn.getMetaData();
			
			ResultSet tablesRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while (tablesRet.next()) {
				String tableName = tablesRet.getString("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new HashMap<String, ColumnInfo>(),new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet columns = dbmd.getColumns(null, "%", tableName, "%");
				while (columns.next()) {
					ColumnInfo ci = new ColumnInfo(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columns.getString("COLUMN_NAME")), columns.getString("TYPE_NAME").replaceAll("\\s", "_"), 0);
					ti.getColumns().put(columns.getString("COLUMN_NAME"), ci);
				}
				
				ResultSet primaryKeys = dbmd.getPrimaryKeys(null, "%", tableName);
				while (primaryKeys.next()) {
					ColumnInfo ci2 = ti.getColumns().get(primaryKeys.getString("COLUMN_NAME"));
					ci2.setKeyType(1);//设置主键类型
					ti.getPrimaryKeys().add(ci2);
				}
				
				if(ti.getPrimaryKeys().size() > 0) {
					ti.setPrimaryKey(ti.getPrimaryKeys().get(0));
				}
			}
			
			updateJavaPoFile();
			
			loadPoTables();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据表结构,更新配置的po包下面的java类
	 */
	private static void updateJavaPoFile() {
		TypeConvertor convertor = new MySqlTypeConvertor();
		for (TableInfo tableInfo : tables.values()) {
			createJavaPOFile(tableInfo, convertor);
		}
	}
	
	/**
	 * 加载po包下面所有的类,便于重用,提高效率
	 */
	private static void loadPoTables() {
		try {
			
			for (TableInfo tableInfo : tables.values()) {
				
				Class<?> c = Class.forName(DBManager.getConfig().getPoPackage()+"."+firstChart2UpperCase(tableInfo.getTableName()));
				
				poClassTableMap.put(c, tableInfo);
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static Map<String, TableInfo> getTableInfos() {
		return tables;
	}
	
	public static Map<Class<?>, TableInfo> getPoClassTable() {
		return poClassTableMap;
	}
	
}
