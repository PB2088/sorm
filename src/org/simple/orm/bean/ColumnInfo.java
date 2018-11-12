package org.simple.orm.bean;

/**
 * 表中一个字段对应的信息
 * @author peng.bo
 * @version 0.8
 */
public class ColumnInfo {
	
	/** 字段名称 */
	private String name;
	
	/** 字段的数据类型 */
	private String dataType;
	
	/** 字段的键类型(0:普通字段; 1:主键 ; 2:外键) */
	private int keyType;
	
	public ColumnInfo() {
		
	}
	
	public ColumnInfo(String name, String dataType, int keyType) {
		this.name = name;
		this.dataType = dataType;
		this.keyType = keyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}
	
	
}
