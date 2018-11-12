package org.simple.orm.utils;

import static org.simple.orm.utils.CommonFunctions.isEmpty;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC常用操作工具
 * @author boge.peng
 * @version 0.8
 */
public final class JDBCUtils {
	public static void  handleParams(PreparedStatement pstmt,Object[] params) {
		try {
			if (!isEmpty(params)) {
				for (int i=0;i<params.length;i++) {
					pstmt.setObject(i+1, params[i]);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
