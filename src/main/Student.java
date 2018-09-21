package main;

import java.util.ArrayList;

public abstract class Student {
	

	private String NameofStudent;
	private int StudentID;
	
	
	public Student(String name, int ID){
		NameofStudent = name;
		StudentID = ID;
	}
	public String getName(){
		return NameofStudent;
	}
	
	public int getID(){
		return StudentID;
	}
	public abstract void add(Subject subject);
	public abstract Subject get(String name);
	
	
	
}
