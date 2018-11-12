package org.simple.test;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.simple.orm.core.QueryFactory;
import org.simple.orm.vo.EmpVo;
import org.simple.po.Employees;

public class QueryTest {
	@Test
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
	public void testQueryList() {
		String sql = "SELECT e.emp_no,e.ename,e.salary FROM employees e where e.salary > ?";
		List<Employees> emps = QueryFactory.createQuery().queryForList(sql, Employees.class, new Object[]{8000});
		
		for (Employees e : emps) {
			System.out.println(e.getEmpNo()+"---->"+e.getEname()+"---->"+e.getSalary());
		}
		
		System.out.println(emps);
	}
	
	@Test
	public void testQueryForObject() {
		String sql = "SELECT e.emp_no,e.ename,e.salary FROM employees e where e.salary > ? and e.emp_no = ?";
		List<Employees> emps = QueryFactory.createQuery().queryForList(sql, Employees.class, new Object[]{8000,"7728"});
		
		for (Employees e : emps) {
			System.out.println(e.getEmpNo()+"---->"+e.getEname()+"---->"+e.getSalary());
		}
		
		System.out.println(emps);
	}
	
	@Test
	public void testQueryValue() {
		String sql = "SELECT count(1) FROM employees e ";
		
		long count = QueryFactory.createQuery().queryValue(sql,null);
		
		System.out.println(count);		
	}
	
	@Test
	public void testUpdate() {
		Employees employees = new Employees();
		employees.setOid(6L);
		employees.setDeptNo("1002");
		employees.setEmpNo("7728");
		employees.setEname("lvbei");
		employees.setHireDate(new Date(System.currentTimeMillis()));
		employees.setSalary(8333.2);
		
		QueryFactory.createQuery().update(employees, new String[]{"ename","deptNo","empNo","salary"});
	}
	
	@Test
	public void testGet() {
		System.out.println(QueryFactory.createQuery().get(Employees.class, 10));
	}
}
