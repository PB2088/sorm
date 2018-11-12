package org.simple.orm.core;

import static org.simple.orm.utils.CommonFunctions.isEmpty;
import static org.simple.orm.utils.ReflectUtils.invokeGetMethod;
import static org.simple.orm.utils.ReflectUtils.invokeSetMethod;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.simple.orm.bean.ColumnInfo;
import org.simple.orm.bean.TableInfo;
import org.simple.orm.utils.CommonFunctions;
import org.simple.orm.utils.JDBCUtils;

import com.google.common.base.CaseFormat;

/**
 * 查询接口通用实现类,使用代码重用
 * @author boge.peng
 * @version 0.8
 */
public abstract class QueryBaseImp implements Query{
	@Override
	public int execute(String sql, Object[] params) {
		
		Connection conn = DBManager.getConn();
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			JDBCUtils.handleParams(pstmt, params);
			
			count = pstmt.executeUpdate();
			System.out.println(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(null, pstmt, conn);
		}
		
		return count;
	}

	@Override
	public <T> void insert(T obj) {
		Class<? extends Object> c = obj.getClass();
		
		TableInfo tableInfo = TableContext.getPoClassTable().get(c);
		
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(tableInfo.getTableName());
		sql.append(" (");
		
		StringBuilder sqlValue = new StringBuilder(" values (");
		
		List<Object> params = new ArrayList<>();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			String fieldName = f.getName();
			Object fieldValue = invokeGetMethod(obj, fieldName);
			
			if (!isEmpty(fieldValue)) {
				sql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName));
				sql.append(",");
				
				sqlValue.append("?");
				sqlValue.append(",");
				
				params.add(fieldValue);
			}
		}
		
		sql.setCharAt(sql.length()-1, ')');
		sqlValue.setCharAt(sqlValue.length()-1, ')');
		
		sql.append(sqlValue);
		
		execute(sql.toString(), params.toArray());
		
	}
	
	@Override
	public <T,T1> void delete(Class<T> clazz, T1 id) {
		TableInfo tableInfo = TableContext.getPoClassTable().get(clazz);
		
		ColumnInfo primaryKeyInfo = tableInfo.getPrimaryKey();
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(tableInfo.getTableName());
		sql.append(" where ");
		sql.append(primaryKeyInfo.getName());
		sql.append(" = ?");
		
		execute(sql.toString(), new Object[]{id});
	}

	@Override
	public <T> void delete(T obj) {
		
		TableInfo tableInfo = TableContext.getPoClassTable().get(obj.getClass());
				
		delete(obj.getClass(), invokeGetMethod(obj, tableInfo.getPrimaryKey().getName()));		
	}

	@Override
	public <T> int update(T obj, String[] fieldNames) {
		
		Class<? extends Object> c = obj.getClass();
		
		TableInfo tableInfo = TableContext.getPoClassTable().get(c);
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("update ");
		sql.append(tableInfo.getTableName());
		sql.append(" set ");
		
		for (String fieldName : fieldNames) {
			Object fieldValue = invokeGetMethod(obj, fieldName);
			params.add(fieldValue);
			
			sql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName));
			sql.append(" = ?,");
		}
		sql.setCharAt(sql.length()-1, ' ');
		
		sql.append(" where ");
		
		ColumnInfo primaryKeyInfo = tableInfo.getPrimaryKey();
		sql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, primaryKeyInfo.getName()));
		sql.append(" = ?");
		
		params.add(invokeGetMethod(obj, primaryKeyInfo.getName()));
		
		return execute(sql.toString(), params.toArray());
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> clazz, Object[] params) {
		
		Connection conn = DBManager.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> results = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			
			System.out.println(ps);
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			
			while (rs.next()) {
				T obj = clazz.newInstance();
				
				for (int i=0;i<metaData.getColumnCount();i++) {
					String columnName = metaData.getColumnLabel(i+1);
					Object columnValue = rs.getObject(i+1);
					
					invokeSetMethod(obj,CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName),columnValue);
				}
				
				results.add(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, ps, conn);
		}
		
		return results;
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> clazz, Object[] params) {
		List<T> results = queryForList(sql, clazz, params);
		
		return CommonFunctions.isEmpty(results)?null:results.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryValue(String sql, Object[] params) {
		
		Connection conn = DBManager.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		T resutl = null;
		
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			
			System.out.println(ps);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				resutl = (T) rs.getObject(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, ps, conn);
		}
		
		return resutl;
	}
	
	@Override
	public <T, T1> T get(Class<T> clazz, T1 id) {
		TableInfo tableInfo = TableContext.getPoClassTable().get(clazz);
		
		ColumnInfo primaryKeyInfo = tableInfo.getPrimaryKey();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(tableInfo.getTableName());
		sql.append(" where ");
		sql.append(primaryKeyInfo.getName());
		sql.append(" = ?");
		
		return queryForObject(sql.toString(), clazz, new Object[]{id});
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public abstract <T> List<T> paging(int pageNum, int size);
}
