package org.simple.orm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.simple.orm.bean.Configuration;
import org.simple.orm.pool.DBPool;

/**
 * 根据配置信息,维护连接对象的管理
 * @author boge.peng
 * @version 0.8
 */
public class DBManager {
	/** 配置信息 */
	private static Configuration config;
	
	private static DBPool dbPool;
	
	static {
		Properties pros = new Properties();
		
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dataSource.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		config = new Configuration();
		config.setDriverClass(pros.getProperty("connection.driver_class"));
		config.setJdbcUrl(pros.getProperty("connection.url"));
		config.setUser(pros.getProperty("connection.username"));
		config.setPassword(pros.getProperty("connection.password"));
		config.setDialect(pros.getProperty("sorm.dialect"));
		config.setSrcPath(pros.getProperty("src.path"));
		config.setPoPackage(pros.getProperty("po.package"));
		config.setQueryClass(pros.getProperty("queryClass"));
		config.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
		config.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
		
		System.out.println(TableContext.class);
	}
	
	/**
	 * 获得Connection对象
	 * @return
	 */
	public static Connection getConn() {
		if (dbPool == null) {
			dbPool = new DBPool();
		}
		return dbPool.getConnection();
	}
	
	/**
	 * 创建新的Connection对象
	 * @return
	 */
	public static Connection createConn() {
		try {
			Class.forName(config.getDriverClass());
			
			return DriverManager.getConnection(config.getJdbcUrl(), config.getUser(), config.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/**
	 * 关闭数据库操作对象
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void close(ResultSet rs,Statement stmt,Connection conn) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbPool.close(conn);
		
	}
	
	public static Configuration getConfig() {
		return config;
	}
}
