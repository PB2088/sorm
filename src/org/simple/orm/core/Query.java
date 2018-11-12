package org.simple.orm.core;

import java.util.List;

/**
 * 数据库操作接口
 * @author boge.peng
 * @version 0.8
 */
public interface Query extends Cloneable{
	/**
	 * 直接执行一个DML语句
	 * @param sql sql语句
	 * @param params 执行参数
	 * @return 执行sql语句后影响记录的行数
	 */
	public int execute(String sql,Object[] params);
	
	/**
	 * 将一个对象存储到数据库
	 * @param obj
	 */
	public <T> void insert(T obj);
	
	/**
	 * 根据clazz表示类对应的表中的记录(指定主键值ID的记录)
	 * @param clazz 和表对应的类的Class对象
	 * @param id 主键值
	 * @return
	 */
	public <T,T1> void delete(Class<T> clazz,T1 id);
	
	/**
	 * 删除对象在数据库中对应的记录
	 * @param obj
	 */
	public <T> void delete(T obj);
	
	/**
	 * 更新对象对应的记录,并且只更新指定的字段的值
	 * @param obj
	 * @param fieldNames
	 * @return
	 */
	public <T> int update(T obj,String[] fieldNames);
	
	/**
	 * 查询返回多行记录,并将每行记录封装到clazzz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params 查询参数
	 * @return
	 */
	public <T> List<T> queryForList(String sql,Class<T> clazz,Object[] params);
	
	/**
	 * 查询返回一行记录,并将该记录封装到clazz指定的类的对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params 查询参数
	 * @return
	 */
	public <T> T queryForObject(String sql,Class<T> clazz,Object[] params);
	
	/**
	 * 查询返回单个值
	 * @param sql 查询语句
	 * @param params 查询参数
	 * @return
	 */
	public <T> T queryValue(String sql,Object[] params);
	
	/**
	 * 根据主键获取一个对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T,T1> T get(Class<T> clazz,T1 id);
	
	/**
	 * 分页查询方法,由每个数据自己实现
	 * @param pageNum
	 * @param size
	 * @return
	 */
	public <T> List<T> paging(int pageNum,int size);
	
	public Object clone() throws CloneNotSupportedException;
}
