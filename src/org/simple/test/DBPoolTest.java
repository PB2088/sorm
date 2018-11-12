package org.simple.test;

import java.util.List;

import org.junit.Test;
import org.simple.orm.core.QueryFactory;
import org.simple.orm.vo.EmpVo;

/**
 * 连接池效率测试
 * @author boge.peng
 *
 */
public class DBPoolTest {
	public void testQueryVo() {
		String sql = "SELECT e.emp_no,e.ename,e.salary,d.dept_no,d.dname,d.addres "
				+ "FROM employees e "
				+ "JOIN department d on e.dept_no = d.dept_no";
		
		List<EmpVo> emps = QueryFactory.createQuery().queryForList(sql, EmpVo.class, null);
		
		for (EmpVo e : emps) {
			System.out.println(e.getEmpNo()+"---->"+e.getEname()+"---->"+
					e.getSalary()+"---->"+e.getDeptNo()+"------->"+e.getDname()+"--->"+e.getAddres());
		}
	}
	
	@Test
	public void testDBPoolPerformance() {
		//未使用连接池：29507 5298
		long startTime = System.currentTimeMillis();
		for (int i=0;i<5000;i++) {
			testQueryVo();
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("===================运行耗时："+(endTime-startTime)+"===================");
	}
}
