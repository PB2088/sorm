package org.simple.orm.vo;

public class EmpVo {
/*	SELECT e.emp_no empNo,e.ename ename,e.salary salary,d.dept_no deptNo,d.dname dname,d.addres dAddres 
	FROM employees e 
	JOIN department d on e.dept_no = d.dept_no*/
	
	private String empNo;
	private String ename;
	private Double salary;
	private String deptNo;
	private String dname;
	private String addres;
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}

}
