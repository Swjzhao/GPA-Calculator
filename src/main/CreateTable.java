package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable{
	
	private Driver driver;
	CreateTable(String name) throws SQLException{
		driver = new Driver("students");
		
		driver.myStmt = driver.myConn.createStatement();
		String sql = "CREATE TABLE " + name + " " + 
				"(id int(11) NOT NULL AUTO_INCREMENT," + 
				"course varchar(64) DEFAULT NULL," +
				"c1 DOUBLE DEFAULT 0.0 ," +
				"c2 DOUBLE DEFAULT 0.0," +
				"c3 DOUBLE DEFAULT 0.0," +
				"c4 DOUBLE DEFAULT 0.0," +
				"PRIMARY KEY (id))";
		
		driver.myStmt.executeUpdate(sql);
		
		
				
	}
	
}
