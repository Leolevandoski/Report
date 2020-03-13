package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.CompanyPayrollDAO;
import db.CompanyPayrollFormat;

import testdata.WriteTest;

public class CompanyPayrollGen {
	
	public static String pipe = CompanyPayrollFormat.pipe();
	

	public static void main(String[] args) throws IOException {
		
/*		Connection conn = CompanyPayrollDAO.getConnection();
		
		System.out.println("conectado");
		
		CompanyPayrollDAO.closeConnection(); */
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
		
			conn = CompanyPayrollDAO.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery ("SELECT e.*, d.Name as DEPARTMENT, "
					+ "(SELECT SUM(e2.SALARY) AS TOTAL FROM employees e2 WHERE e2.DEPARTMENT_ID = e.DEPARTMENT_ID) as total, "
					+ "(SELECT COUNT(e3.DEPARTMENT_ID) FROM employees e3 WHERE e3.DEPARTMENT_ID = d.ID) as ordering "
					+ "FROM employees e INNER JOIN department d "
					+ "ON e.DEPARTMENT_ID = d.Id "
					+ "ORDER BY ordering DESC, e.NAME;");

			StringBuffer buffer = new StringBuffer("");
			
			
			String dep = null;
			Double total = 0d;
						
//			Double s = rs.getDouble("SALARY");
//			String SALARY = String.format("%.2f", s); 
			
			System.out.println(CompanyPayrollFormat.header());
			while (rs.next()) {
				if(rs.getRow() == 1) {
					dep = rs.getString("DEPARTMENT");
					total = rs.getDouble("total");
				} else if(!rs.getString("DEPARTMENT").equals(dep)) {
					System.out.println("TOTAL = " + total);
					dep = rs.getString("DEPARTMENT");
					total = rs.getDouble("total");
				}	
				System.out.println(rs.getString("NAME") + pipe + String.format("%.2f",rs.getDouble("SALARY")) + pipe + rs.getString("DEPARTMENT") );	
				if(rs.isLast()) {
					System.out.println("TOTAL = " + String.format("%.2f", total));
				}
				buffer.append(rs.getString("NAME") + pipe + String.format("%.2f",rs.getDouble("SALARY")) + pipe + rs.getString("DEPARTMENT")).append("\n");
				
				
			}
			WriteTest.writeInJava(buffer.toString());
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			CompanyPayrollDAO.closeResultSet(rs);
			CompanyPayrollDAO.closeStatement(st);
			CompanyPayrollDAO.closeConnection();
		
		}
	
	}

}
