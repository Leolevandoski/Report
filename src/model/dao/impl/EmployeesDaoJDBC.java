package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.CompanyPayrollDAO;
import db.DbException;
import model.dao.EmployeesDao;
import model.entities.Department;
import model.entities.Employees;

public class EmployeesDaoJDBC implements EmployeesDao{
	
	
	private Connection conn;
	
	public EmployeesDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Employees obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Employees obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Employees findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT employees.*,department.NAME as DEPARTMENT " 
					+ "FROM employees INNER JOIN department "
					+ "ON employees.DEPARTMENT_ID = department.Id "  
					+ "WHERE employees.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Employees obj = instantiateEmployees(rs, dep);
				return obj;
			
				
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
				
		}
		finally {
			CompanyPayrollDAO.closeStatement(st);
			CompanyPayrollDAO.closeResultSet(rs);
					
		}

		
	}

	private Employees instantiateEmployees(ResultSet rs, Department dep) throws SQLException {
		Employees obj = new Employees();
		obj.setId(rs.getInt("ID"));
		obj.setName(rs.getString("NAME"));
		obj.setSalary(rs.getDouble("SALARY"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("DEPARTMENT_ID"));
		dep.setName(rs.getString("DEPARTMENT"));
		return dep;
	}

	@Override
	public List<Employees> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employees> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT employees.*,department.NAME as DEPARTMENT "
					+ "FROM employees INNER JOIN department "
					+ "ON employees.DEPARTMENT_ID = department.Id "
					+ "WHERE DEPARTMENT_ID = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
	
			List<Employees> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DEPARTMENT_ID"));
				
				if(dep == null) {
					
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DEPARTMENT_ID"),dep);
					
						
				}
				
				Employees obj = instantiateEmployees(rs, dep);
				list.add(obj);
			
				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
				
		}
		finally {
			CompanyPayrollDAO.closeStatement(st);
			CompanyPayrollDAO.closeResultSet(rs);
					
		}
	}

}
