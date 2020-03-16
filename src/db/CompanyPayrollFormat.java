package db;

public class CompanyPayrollFormat {
	
	public static final String PIPE = "|";
	public static final String NAME = "NAME";
	public static final String SALARY = "SALARY";
	public static final String DEPARTMENT = "DEPARTMENT";

	public static String header() {
		
		return  NAME + PIPE + SALARY + PIPE + DEPARTMENT;

	}

}