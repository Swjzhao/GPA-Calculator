package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.*;

public class Intro extends JFrame implements ItemListener {

	private String current;
	private Dimension screenSize;
	double width;
	double height;

	JComboBox box;
	JButton confirm;
	JButton addnew;

	public Intro() {

		// create Jcombo box and JFrame
		// choose from existing students
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();

		setBounds(200, 200, (int) (width * 0.4), (int) (height * 0.4));

		setResizable(false);
		setTitle("Log in");
		JPanel contentPane = new JPanel();
		JPanel Pane = new JPanel(new GridLayout(2, 2, (int) (width * 0.05), (int) (height * 0.005)));
		Pane.setPreferredSize(new Dimension((int) (width * 0.3), (int) (height * 0.05)));

		// Pane.setBounds(225, 100, 0, 0);

		Driver driver = new Driver("students");
		ArrayList<String> arr = new ArrayList<>();
		try {
			while (driver.myRs.next()) {
				arr.add(driver.myRs.getString("first_name") + ":" + driver.myRs.getString("id"));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (arr.size() == 0) {
			current = "";
		} else {
			current = arr.get(0);
		}
		String[] sarr = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			sarr[i] = arr.get(i);
		}
		box = new JComboBox(sarr);
		box.addItemListener(this);

		confirm = new JButton("Enter");
		// confirm.setPreferredSize(new Dimension((int)(width*0.2), (int)(height*0.1)));

		addnew = new JButton("Add New User");

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] parts = current.split(":");
				String name = parts[0];
				int idd = Integer.parseInt(parts[1]);
				new UI(name, idd);
				dispose();
			}
		});

		addnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				String firstname = null;
				firstname = JOptionPane.showInputDialog(null, "Enter Student First Name");

				if (firstname == null || firstname.length() == 0) {

				} else {
					while (firstname.length() < 1) {
						firstname = JOptionPane.showInputDialog(null, "Enter Student Name");
						if (firstname == null || firstname.length() == 0) {
							return;
						}
					}
					String lastname = null;
					lastname = JOptionPane.showInputDialog(null, "Enter Student Last Name");

					if (lastname == null || lastname.length() == 0) {

					} else {
						while (lastname.length() < 1) {
							lastname = JOptionPane.showInputDialog(null, "Enter Student Name");
							if (lastname == null || lastname.length() == 0) {
								return;
							}
						}
						String id = null;
						id = JOptionPane.showInputDialog(null, "Enter Student ID");
						if (id == null || id.length() == 0) {

						} else {
							int idd = 0;
							try {
								idd = Integer.parseInt(id);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Please enter only integers");
							}
							
							String sql = "insert into students " + "(id, last_name, first_name)" + "values (" + id + ", '"
									+ lastname + "', '" + firstname + "')";
							System.out.println(sql);
							try {
								driver.myStmt.executeUpdate(sql);
								new CreateTable(firstname+id);
								System.out.println("Insert complete.");
							} catch (Exception e) {
								e.printStackTrace();
							}
							new UI(firstname, idd);
							dispose();
						}
					}
				}
			}
		});

		Pane.add(box);
		Pane.add(confirm);
		Pane.add(new JLabel());
		Pane.add(addnew);
		contentPane.add(Pane);
		// contentPane.setLocation(0, (int)(height*0.2));
		setContentPane(contentPane);

		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		// TODO Auto-generated method stub
		current = (String) evt.getItem();
	}

	public void changeFontBC(JComboBox jcb) {

		Font font = jcb.getFont();
		String text = jcb.getSelectedItem().toString();

		int sw = jcb.getFontMetrics(font).stringWidth(text);
		int cw = (int) (width * 0.15);
		double widthRatio = (double) (cw) / (double) (sw);
		System.out.println(cw + " " + sw);
		int newFontSize = (int) (font.getSize() * widthRatio);
		int componentHeight = (int) (height * 0.05);

		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		System.out.println(newFontSize + " " + componentHeight);
		jcb.setFont(new Font(font.getName(), Font.PLAIN, fontSizeToUse));

	}

}
