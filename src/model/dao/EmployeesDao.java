package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Employees;

public interface EmployeesDao {
	
	void insert (Employees obj);
	void update(Employees obj);
	void deleteById(Integer id);
	Employees findById(Integer id);
	List<Employees> findAll();
	List<Employees> findByDepartment(Department department);
	

}
