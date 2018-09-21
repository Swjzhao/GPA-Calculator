/**
 * 
 */
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author S Z
 *
 */
public class Subject {

	// remember to add type

	private String subName;
	private ArrayList<String> name;
	private ArrayList<String> type;
	private ArrayList<Double> score;
	private ArrayList<Integer> maxscore;
	private ArrayList<Double> itemscore;
	private PrintWriter pw;
	private PrintStream ps;

	private String nos;
	private int id;

	private double tq = 0, a = 0, p = 0, o = 0;

	public double getTq() {
		return tq;
	}

	public void setTq(double tq) {
		this.tq = tq;

	}

	public double getA() {
		return a;

	}

	public void setA(double a) {
		this.a = a;

	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;

	}

	public double getO() {
		return o;
	}

	public void setO(double o) {
		this.o = o;

	}

	public String getName() {
		return subName;
	}

	public String getLabel(int i) {

		return name.get(i);

	}

	public String getType(int i) {
		return type.get(i);
	}

	public double getScore(int i) {
		return score.get(i);
	}

	public int getMaxScore(int i) {
		return maxscore.get(i);
	}

	public double getTaskscore(int i) {
		return itemscore.get(i);
	}

	public int getID() {
		return id;
	}

	public String getStudName() {
		return nos;
	}

	public int numAssign() {
		return name.size();
	}

	Driver coursescores;
	Driver course;

	public Subject(String subName, String nos, int id) {

		coursescores = new Driver(subName + id);
		course = new Driver(nos + id);
		this.subName = subName;
		name = new ArrayList<>();
		type = new ArrayList<>();
		score = new ArrayList<>();
		maxscore = new ArrayList<>();
		itemscore = new ArrayList<>();
		this.nos = nos;
		this.id = id;
		try {
			while (coursescores.myRs.next()) {
				name.add(coursescores.myRs.getString("name"));
				type.add(coursescores.myRs.getString("type"));
				double tempscore = coursescores.myRs.getDouble("score");
				double tempmaxscore = coursescores.myRs.getInt("maxscore");
				score.add(coursescores.myRs.getDouble("score"));
				maxscore.add(coursescores.myRs.getInt("maxscore"));
				itemscore.add((tempscore / tempmaxscore));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			//System.out.println("select * from " +  course.getName() + " where course = " + subName );
			course.myRs = course.myStmt.executeQuery("select * from " +  course.getName() + " where course = '" + subName +"'" );
			
			while (course.myRs.next()) {
				tq = course.myRs.getInt("c1");
				a = course.myRs.getInt("c2");
				p = course.myRs.getInt("c3");
				o = course.myRs.getInt("c4");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(itemscore.size());
		
	}

	public Subject(String subName, boolean b, String nos, int id, Driver driver) {

		this.subName = subName;
		String sql = "insert into " + (driver.getName()).toLowerCase() + " " + "(course) " + "values ('" + subName
				+ "')";

		System.out.println(sql);
		try {
			driver.myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = new Driver("students");

		try {
			driver.myStmt = driver.myConn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql = "CREATE TABLE " + subName + id + " " + "(id int(11) NOT NULL AUTO_INCREMENT,"
				+ "name varchar(64) DEFAULT NULL," + "type varchar(64) DEFAULT NULL," + "score DOUBLE DEFAULT 0,"
				+ "maxscore INTEGER DEFAULT 1," + "itemscore DOUBLE DEFAULT 0," + "PRIMARY KEY (id))";

		try {
			driver.myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		name = new ArrayList<>();
		type = new ArrayList<>();
		score = new ArrayList<>();
		maxscore = new ArrayList<>();
		itemscore = new ArrayList<>();

	}

	public void removeScore(String sname) {
		// not used?
		for (int i = 0; i < name.size(); i++) {
			if (name.get(i).equalsIgnoreCase(sname)) {
				name.remove(i);
				type.remove(i);
				score.remove(i);
				maxscore.remove(i);
				itemscore.remove(i);
			}
			// and remove from database
		}
	}

	public void addScore(String sname, String stype, Double sscore, int smaxscore) {
		try {
			if (sname.length() > 0) {
				name.add(sname);
				type.add(stype);
				score.add(sscore);
				maxscore.add(smaxscore);
				itemscore.add((sscore / smaxscore));

				String sql = "insert into " + (coursescores.getName()).toLowerCase() + " "

						+ "(name, type, score, maxscore, itemscore) " + "values('" + sname + "','" + stype + "','"
						+ sscore + "','" + smaxscore + "','" + (sscore / smaxscore) + "')";
				System.out.println(sql);
				try {
					course.myStmt.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public double total() {
		double t = 0;
		int td = 0;
		for (int i = 0; i < score.size(); i++) {
			t += score.get(i);
			td += maxscore.get(i);
		}

		return (t / td) * 100;
	}

	public String toString() {
		return subName;
	}

}
