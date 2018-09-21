package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class TimesTable extends Student {

	private ArrayList<Subject> sub;
	private PrintWriter p;
	public Driver driver;

	public TimesTable(String name, int ID) {
		super(name, ID);
		driver = new Driver(name +"" +ID);
		System.out.println(name + ID);
		sub = new ArrayList<Subject>();
		

		try {
			while (driver.myRs.next())
				sub.add(new Subject(driver.myRs.getString("course"), getName(), getID()));
			
			System.out.println(sub.size());
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Error");
		}
	}

	public void add(Subject s) {
		

		sub.add(s);
		// System.out.println(s);
	}

	public boolean remove(Subject s) {

		int i = getIndex(s.getName());
		if (i < 0) {
			return false;
		} else {
			
			String sql = "drop table if exists " + s.getName()+s.getID();
			System.out.println(sql);
			try {
			driver.myStmt.executeUpdate(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
			sql = "delete from " + driver.getName() + " where course = '" + s.getName() + "'"; 
			try {
				driver.myStmt.executeUpdate(sql);
				sub.remove(i);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
	}

	public Subject get(String name) {

		for (int i = 0; i < sub.size(); i++) {

			if (sub.get(i).getName().equalsIgnoreCase(name))
				return sub.get(i);
		}
		return null;
	}

	public int getIndex(String name) {
		for (int i = 0; i < sub.size(); i++) {

			if (sub.get(i).getName().equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}

	public int size() {
		return sub.size();
	}

	public ArrayList<Subject> getList() {
		return sub;
	}

}
