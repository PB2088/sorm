package org.simple.orm.bean;

/**
 * Java Bean属性源码信息
 * @author boge.peng
 * @version 0.8
 */
public class JavaFieldSRC {
	/** 属性源码信息 */
	private String fieldInfo;
	
	/** get方法源码信息 */
	private String getterInfo;
	
	/** set方法源码信息  */
	private String setterInfo;

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetterInfo() {
		return getterInfo;
	}

	public void setGetterInfo(String getterInfo) {
		this.getterInfo = getterInfo;
	}

	public String getSetterInfo() {
		return setterInfo;
	}

	public void setSetterInfo(String setterInfo) {
		this.setterInfo = setterInfo;
	}
	
}
