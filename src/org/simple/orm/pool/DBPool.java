package org.simple.orm.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.simple.orm.core.DBManager;
import org.simple.orm.utils.CommonFunctions;

/**
 * 数据库连接池类
 * @author boge.peng
 * @version 0.8
 */
public class DBPool {
	
	/** Connection连接池 */
	private List<Connection> pool;
	
	/** 连接池最大连接数 */
	private final static int POOL_MAX_SIZE = DBManager.getConfig().getPoolMaxSize();
	
	/** 连接池最小连接数 */
	private final static int POOL_MIN_SIZE = DBManager.getConfig().getPoolMinSize();
	
	public DBPool() {
		init();
	}
	
	public void init() {
		if (CommonFunctions.isEmpty(pool)) {
			pool = new ArrayList<>();
		}
		
		while (pool.size() < POOL_MIN_SIZE) {
			pool.add(DBManager.createConn());
			System.out.println("初始化连接池,池中连接数："+pool.size());
		}
	}
	
	/**
	 * 从连接池中获得一个连接
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection conn = pool.get(pool.size()-1);
		pool.remove(conn);
		
		return conn;
	}
	
	/**
	 * 将连接放回连接池
	 * @param conn
	 */
	public synchronized void close(Connection conn) {
		
		if (pool.size() == POOL_MAX_SIZE) {
			try {
				if (conn !=null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			pool.add(conn);			
		}
		
	}
}
