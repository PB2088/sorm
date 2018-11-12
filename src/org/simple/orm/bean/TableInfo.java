package org.simple.orm.bean;

import java.util.List;
import java.util.Map;

/**
 * 数据表结构信息
 * @author boge.peng
 * @version 0.8
 */
public class TableInfo {
	/** 表名 */
	private String tableName;
	
	/** 所有字段 */
	private Map<String, ColumnInfo> columns;
	
	/** 主键(目前唯一主键) */
	private ColumnInfo primaryKey;
	
	/** 联合主键 */
	private List<ColumnInfo> primaryKeys;
	
	public TableInfo() {
		
	}
	
	public TableInfo(String tableName, Map<String, ColumnInfo> columns, ColumnInfo primaryKey) {
		this.tableName = tableName;
		this.columns = columns;
		this.primaryKey = primaryKey;
	}
	
	public TableInfo(String tableName, Map<String, ColumnInfo> columns, List<ColumnInfo> primaryKeys) {
		super();
		this.tableName = tableName;
		this.columns = columns;
		this.primaryKeys = primaryKeys;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}

	public ColumnInfo getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ColumnInfo primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnInfo> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<ColumnInfo> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	
}
