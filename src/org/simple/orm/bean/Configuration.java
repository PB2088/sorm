package org.simple.orm.bean;

/**
 * 配置信息对象
 * @author boge.peng
 * @version 0.8
 */
public class Configuration {
	/** 数据库连接驱动 */
	private String driverClass;
	
	/** 数据库连接URL */
	private String jdbcUrl;
	
	/** 数据库连接用户 */
	private String user;
	
	/** 数据库连接用户密码 */
	private String password;
	
	/** 数据库方言(正在使用的数据库) */
	private String dialect;
	
	/** 项目源码路径 */
	private String srcPath;
	
	/** 扫描生成java类的包 */
	private String poPackage;
	
	/** 当前数据库查询实现类路径 */
	private String queryClass;
	
	/** 连接池最小连接数 */
	private int poolMinSize;
	
	/** 连接池最大连接数 */
    private int poolMaxSize;
	
	public Configuration() {
		
	}

	public Configuration(String driverClass, String jdbcUrl, String user, String password, String dialect,
			String srcPath, String poPackage) {
		this.driverClass = driverClass;
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.dialect = dialect;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getPoPackage() {
		return poPackage;
	}

	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}

	public String getQueryClass() {
		return queryClass;
	}

	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}

	public int getPoolMinSize() {
		return poolMinSize;
	}

	public void setPoolMinSize(int poolMinSize) {
		this.poolMinSize = poolMinSize;
	}

	public int getPoolMaxSize() {
		return poolMaxSize;
	}

	public void setPoolMaxSize(int poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}
	
}
