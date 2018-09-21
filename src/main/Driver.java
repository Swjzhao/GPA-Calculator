package main;


import java.sql.*;

public class Driver {


	private String dbUrl = "jdbc:mysql://localhost:3306/gradecalc";
	
	private String user = "root";
	private String password = "W@yl786543";
	private String name;
	public Connection myConn = null;
	public Statement myStmt = null;
	public ResultSet myRs = null;
	
	Driver(String name){
		this.name = name;
		System.out.println(name);
		try {
			// Get a connection
			myConn = DriverManager.getConnection(dbUrl, user, password);
			// Create a statement
			myStmt = myConn.createStatement();
			// Execute SQL query
			System.out.println("select * from " +  name + "");
			myRs = myStmt.executeQuery("select * from " +  name + "");
			
			// Process the result set
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}
	
}
