package org.simple.orm.utils;

import org.simple.orm.bean.ColumnInfo;
import org.simple.orm.bean.JavaFieldSRC;
import org.simple.orm.bean.TableInfo;
import org.simple.orm.core.MySqlTypeConvertor;
import org.simple.orm.core.TableContext;
import org.simple.orm.core.TypeConvertor;
import static org.simple.orm.utils.StringUtils.*;
import static org.simple.orm.core.DBManager.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * java文件操作工具
 * @author boge.peng
 * @version 0.8
 */
public final class JavaFileUtils {
	/**
	 * 根据字段信息生成Java属性源码信息
	 * @param columnInfo 字段信息
	 * @param convertor 类型转化器
	 * @return java属性和setter/getter源码信息
	 */
	public static JavaFieldSRC buildJavaFieldSRC(ColumnInfo columnInfo,TypeConvertor convertor) {
		
		JavaFieldSRC fieldSRC = new JavaFieldSRC();
				
		fieldSRC.setFieldInfo(buildFieldInfo(columnInfo, convertor));
				
		fieldSRC.setSetterInfo(buildSetterInfo(columnInfo, convertor));
		
		fieldSRC.setGetterInfo(buildGetterInfo(columnInfo, convertor));
		
		return fieldSRC;
	}
	
	/**
	 * 根据表结构信息生成Java类源码信息
	 * @param tableInfo 表结构信息
	 * @param convertor 类型转换器
	 * @return java类源码信息
	 */
	public static String buildJavaBeanSRC(TableInfo tableInfo,TypeConvertor convertor) {
		
		Map<String, ColumnInfo> columns = tableInfo.getColumns();
		
		List<JavaFieldSRC> javaFieldSRCs = new ArrayList<>();
		for (ColumnInfo columnInfo : columns.values()) {
			javaFieldSRCs.add(buildJavaFieldSRC(columnInfo,convertor));
		}
		
		StringBuilder srcBuild = new StringBuilder();
		//生成package语句
		srcBuild.append("package ");
		srcBuild.append(getConfig().getPoPackage());
		srcBuild.append(";\n");
		srcBuild.append("\n");
		
		//生成import语句
		srcBuild.append("import java.sql.*;\n");
		srcBuild.append("import java.util.*;\n\n");
		
		//生成类声明语句
		srcBuild.append("public class ");
		srcBuild.append(firstChart2UpperCase(tableInfo.getTableName()));
		srcBuild.append(" {\n\n");
		
		//生成属性列表
		for (JavaFieldSRC fieldSRC : javaFieldSRCs) {
			srcBuild.append(fieldSRC.getFieldInfo());
		}
		srcBuild.append("\n");
		
		//生成getter、setter方法
		for (JavaFieldSRC fieldSRC : javaFieldSRCs) {
			srcBuild.append(fieldSRC.getGetterInfo());
			srcBuild.append(fieldSRC.getSetterInfo());
		}
		
		//类结束符
		srcBuild.append("}");
		
		return srcBuild.toString();
	}
	
	public static void createJavaPOFile(TableInfo tableInfo,TypeConvertor convertor) {
		String src = buildJavaBeanSRC(tableInfo, convertor);
		
		String srcPath = getConfig().getSrcPath() + File.separator;
		
		String poPackage = getConfig().getPoPackage().replaceAll("\\.", "\\\\");
		
		File file = new File(srcPath+poPackage);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()+File.separator+firstChart2UpperCase(tableInfo.getTableName()+".java")));
			writer.write(src);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	private static String buildGetterInfo(ColumnInfo columnInfo, TypeConvertor convertor) {
		StringBuilder getterInfo = new StringBuilder();
		getterInfo.append("\tpublic ");
		getterInfo.append(convertor.databaseTypeConvJavaType(columnInfo.getDataType()));
		getterInfo.append(" ");
		getterInfo.append("get"+firstChart2UpperCase(columnInfo.getName()));
		getterInfo.append("()");
		getterInfo.append(" ");
		getterInfo.append("{\n");
		getterInfo.append("\t\t");
		getterInfo.append("return");
		getterInfo.append(" ");
		getterInfo.append(columnInfo.getName());
		getterInfo.append(";\n");
		getterInfo.append("\t}");
		getterInfo.append("\n");
		getterInfo.append("\n");
		return getterInfo.toString();
	}

	private static String buildSetterInfo(ColumnInfo columnInfo, TypeConvertor convertor) {
		StringBuilder setterInfo = new StringBuilder();
		setterInfo.append("\tpublic void ");
		setterInfo.append("set"+firstChart2UpperCase(columnInfo.getName()));
		setterInfo.append("(");
		setterInfo.append(convertor.databaseTypeConvJavaType(columnInfo.getDataType()));
		setterInfo.append(" ");
		setterInfo.append(columnInfo.getName());
		setterInfo.append(")");
		setterInfo.append(" ");
		setterInfo.append("{\n");
		setterInfo.append("\t\t");
		setterInfo.append("this.");
		setterInfo.append(columnInfo.getName());
		setterInfo.append(" = ");
		setterInfo.append(columnInfo.getName());
		setterInfo.append(";\n");
		setterInfo.append("\t}");
		setterInfo.append("\n");
		setterInfo.append("\n");
		return setterInfo.toString();
	}

	private static String buildFieldInfo(ColumnInfo columnInfo, TypeConvertor convertor) {
		StringBuilder fieldInfo = new StringBuilder();
		fieldInfo.append("\tprivate ");
		fieldInfo.append(convertor.databaseTypeConvJavaType(columnInfo.getDataType()));
		fieldInfo.append(" ");
		fieldInfo.append(columnInfo.getName());
		fieldInfo.append(";\n");
		return fieldInfo.toString();
	}
	
	public static void main(String[] args) {
		Map<String, TableInfo> tableInfos = TableContext.getTableInfos();
		TableInfo tableInfo = tableInfos.get("employees");
		
		createJavaPOFile(tableInfo,new MySqlTypeConvertor());
		
		//System.out.println(buildJavaBeanSRC(tableInfo, new MySqlTypeConvertor()));
		
	}
}
