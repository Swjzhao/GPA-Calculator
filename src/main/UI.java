package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class UI extends JFrame implements ItemListener, ActionListener, MouseListener {

	// need to fix the update when updates removed item
	protected ArrayList<JPanel> panels;
	protected ArrayList<JPanel> analysis;
	protected ArrayList<JPanel> totals;
	private JPanel display; // Wrapper Panel
	private JPanel choose;
	private JPanel function;

	private JScrollPane scrollPane;
	private JPanel center;
	private JPanel main;
	private JPanel secondary;

	private JPanel bottom;
	private JPanel addButton;
	private JPanel total;

	private JComboBox cb;
	private JButton add;
	private JButton addSub;
	private JButton removeSub;
	private JButton settings;

	private ArrayList<Subject> subjects;
	private ArrayList<Double> scores;
	protected TimesTable tt;
	private DecimalFormat df;
	private int start;
	private int ss;
	private double[] types;
	private int[] nt;

	protected Subject currentSub;

	private JFrame jf;
	private UI u;

	private Dimension screenSize;
	double width;
	double height;
	private Driver driver;

	public UI(String student, int sid) {
		tt = new TimesTable(student, sid);
		driver = tt.driver;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();

		setBounds(100, 100, (int) (width * 0.8), (int) (height * 0.8));
		// setBounds(100, 100, (int)width/1024*768, (int)height/1024*768);
		// System.out.println((int)(width/4*3) + " " + (int)(height/4*3) + " " +
		// width + " " + height);
		setTitle("Grade Calculator");

		// JOptionPane.showMessageDialog(this, "There's currently a bug that will shift
		// the view after you add an item or reset score. However, the data remains in
		// the right place");

		jf = this;
		start = 0;
		u = this;
		df = new DecimalFormat("###.##%");
		scores = new ArrayList<>();

		subjects = tt.getList();
		ss = subjects.size();
		String[] arr = new String[subjects.size()];
		for (int i = 0; i < tt.size(); i++) {
			arr[i] = subjects.get(i).getName();
			// System.out.println(subjects.get(i).getName());
		}
		if (subjects.size() != 0) {
			currentSub = subjects.get(0);
		} else {
			currentSub = null;
		}

		choose = new JPanel(new BorderLayout());
		cb = new JComboBox(arr);
		cb.setEditable(false);
		cb.addItemListener(this);

		choose.add(cb, BorderLayout.WEST);

		addSub = new JButton("Add Subject");
		addSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame jframe = new JFrame();
				String sub = null;
				sub = JOptionPane.showInputDialog(jframe, "Enter the subject");
				if (sub == null || sub.length() == 0) {

					return;
				} else {
					// System.out.println(sub);
					Subject tempsub = new Subject(sub, true, student, sid, driver);
					tt.add(tempsub);
					currentSub = tempsub;
					System.out.println(currentSub);
					// System.out.println(tempsub.getName());
					update(subjects, ss);

				}
			}

		});
		removeSub = new JButton("Remove Subject");
		removeSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame jframe = new JFrame();
				String sub = null;
				sub = JOptionPane.showInputDialog(jframe, "Enter the subject");
				if (sub == null || sub.length() == 0) {

					return;
				} else {
					Subject tempsub = new Subject(sub, student, sid);
					System.out.println("Temp:" + sid);
					boolean b = tt.remove(tempsub);
					if (b) {
						currentSub = subjects.get(0);
						update(subjects, 0);

					} else {
						JOptionPane.showMessageDialog(jf, "Subject has not been entered");

					}
				}
			}

		});

		settings = new JButton(new ImageIcon("Setting.png"));
		settings.addActionListener(this);
		function = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		function.add(new JLabel("User: " + student + " " + sid));
		function.add(addSub);
		function.add(removeSub);
		function.add(settings);
		choose.add(function, BorderLayout.EAST);
		// cb.getSelectedItem();

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension((int) (width * 0.5), (int) (height * 0.5)));
		center = new JPanel(new BorderLayout());
		main = new JPanel(new CardLayout());
		secondary = new JPanel(new CardLayout());

		bottom = new JPanel(new BorderLayout());
		addButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add = new JButton("Add New Item");

		addButton.add(add);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new AddingWindow(currentSub, u);

			}

		});
		total = new JPanel(new CardLayout());

		panels = new ArrayList<>();
		analysis = new ArrayList<>();

		totals = new ArrayList<JPanel>();

		// testing adding subject function
		// apply to add button
		// try {
		// tt.add(new Subject("k", true));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// testing update function

		paint();
		display = new JPanel();
		display.setLayout(new BorderLayout());
		display.add(choose, BorderLayout.PAGE_START);
		scrollPane.setViewportView(main);

		center.add(scrollPane, BorderLayout.PAGE_START);

		center.add(secondary, BorderLayout.WEST);
		display.add(center, BorderLayout.CENTER);
		bottom.add(addButton, BorderLayout.PAGE_START);

		bottom.add(total);
		display.add(bottom, BorderLayout.PAGE_END);
		display.setBackground(Color.WHITE);
		display.setOpaque(true);
		setContentPane(display);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public void CreateUI() {
		// Creating the assignment marks lists
		// Perhaps for later use
	}

	public void update(ArrayList<Subject> choices, int ss) {

		if (ss == 0) {
			cb.removeAllItems();
		}
		for (int i = ss; i < tt.size(); i++) {

			cb.addItem(choices.get(i).getName());

		}
		
		ss = tt.size();
		paint();
	}

	public void createTable(Subject sub, JPanel c, int ind, boolean b) {
		// creating JLabels for subject
		// DecimalFormat df = new DecimalFormat("###.##%");
		JPanel a = new JPanel();
		JPanel t = new JPanel();

		types = new double[4];
		nt = new int[4];

		JLabel j1 = new JLabel("Name");
		JLabel j2 = new JLabel("Type");
		JLabel j3 = new JLabel("Score / Maxscore");
		JLabel j4 = new JLabel("Score for item");

		j1.setBorder(new EmptyBorder(0, 20, 0, 0));
		j2.setBorder(new EmptyBorder(0, 0, 0, 0));
		j3.setBorder(new EmptyBorder(0, 0, 0, 0));
		j4.setBorder(new EmptyBorder(0, 0, 0, 0));

		c.add(j1);
		c.add(j2);
		c.add(j3);
		c.add(j4);

		c.add(new JLabel());
		c.add(new JLabel());
		c.add(new JLabel());
		c.add(new JLabel());

		if (sub.numAssign() < 10) {
			for (int i = 0; i < sub.numAssign(); i++) {
				j1 = new JLabel(sub.getLabel(i));
				j1.setBorder(new EmptyBorder(0, 20, 0, 0));
				j1.addMouseListener(this);
				c.add(j1);
				c.add(new JLabel(sub.getType(i)));
				c.add(new JLabel(sub.getScore(i) + "/" + sub.getMaxScore(i)));
				c.add(new JLabel(df.format((sub.getTaskscore(i)))));
				switch (sub.getType(i)) {

				case "Test and Quiz":
					if (types[0] != 0) {
						types[0] = (types[0] * nt[0] + sub.getTaskscore(i)) / (nt[0] + 1);
						nt[0]++;
					} else {
						types[0] += sub.getTaskscore(i);
					}
					break;
				case "Assignment":
					if (types[1] != 0) {
						types[1] = (types[1] * nt[1] + sub.getTaskscore(i)) / (nt[1] + 1);
						nt[1]++;
					} else {
						types[1] += sub.getTaskscore(i);
					}
					break;
				case "Project":
					if (types[2] != 0) {
						types[2] = (types[2] * nt[2] + sub.getTaskscore(i)) / (nt[2] + 1);
						nt[2]++;
					} else {
						types[2] += sub.getTaskscore(i);
					}
					break;
				case "Other":
					if (types[3] != 0) {
						types[3] = (types[3] * nt[3] + sub.getTaskscore(i)) / (nt[3] + 1);
						nt[3]++;
					} else {
						types[3] += sub.getTaskscore(i);
					}
					break;

				}

			}
			for (int i = sub.numAssign(); i < 10; i++) {
				c.add(new JLabel());
				c.add(new JLabel());
				c.add(new JLabel());
				c.add(new JLabel());

			}
		} else {

			for (int i = 0; i < sub.numAssign(); i++) {
				j1 = new JLabel(sub.getLabel(i));
				j1.setBorder(new EmptyBorder(0, 20, 0, 0));
				j1.addMouseListener(this);
				c.add(j1);
				c.add(new JLabel(sub.getType(i)));
				c.add(new JLabel(sub.getScore(i) + "/" + sub.getMaxScore(i)));
				c.add(new JLabel(df.format((sub.getTaskscore(i)))));
				switch (sub.getType(i)) {

				case "Test and Quiz":
					if (types[0] != 0) {
						types[0] = (types[0] * nt[0] + sub.getTaskscore(i)) / (nt[0] + 1);
						nt[0]++;
					} else {
						types[0] += sub.getTaskscore(i);
					}
					break;
				case "Assignment":
					if (types[1] != 0) {
						types[1] = (types[1] * nt[1] + sub.getTaskscore(i)) / (nt[1] + 1);
						nt[1]++;
					} else {
						types[1] += sub.getTaskscore(i);
					}
					break;
				case "Project":
					if (types[2] != 0) {
						types[2] = (types[2] * nt[2] + sub.getTaskscore(i)) / (nt[2] + 1);
						nt[2]++;
					} else {
						types[2] += sub.getTaskscore(i);
					}
					break;
				case "Other":
					if (types[3] != 0) {
						types[3] = (types[3] * nt[3] + sub.getTaskscore(i)) / (nt[3] + 1);
						nt[3]++;
					} else {
						types[3] += sub.getTaskscore(i);
					}
					break;

				}

			}
		}

		main.add(c, sub.getName() + "");
		createAna(sub, analysis.get(ind), types, nt);
		createTotal(sub, totals.get(ind), types, b, ind);
		System.out.println(currentSub.getName());

	}

	public void paint() {

		for (int i = start; i < subjects.size(); i++) {
			panels.add(new JPanel(new GridLayout(0, 4, 0, 10)));
			analysis.add(new JPanel());
			totals.add(new JPanel());

			createTable(subjects.get(i), panels.get(i), i, false);

		}
		start = subjects.size();

	}

	public void repaint(boolean b) {

		int i = getCurrentIndex();
		// System.out.println(i + " " + subjects.get(i));

		panels.get(i).removeAll();

		analysis.get(i).removeAll();

		totals.get(i).removeAll();

		createTable(subjects.get(i), panels.get(i), i, true);
		update(subjects, ss);
	}

	public void repaintAll() {
		for (int i = 0; i < subjects.size(); i++) {
			panels.get(i).removeAll();
			analysis.get(i).removeAll();
			totals.get(i).removeAll();
			createTable(subjects.get(i), panels.get(i), i, true);

		}
	}

	public void createAna(Subject sub, JPanel a, double[] types, int[] nt) {

		JPanel ana = new JPanel(new GridLayout(2, 0));
		JPanel c = new JPanel(new GridLayout(5, 2));
		JLabel jl = new JLabel("Anaylsis for " + sub.getName());
		jl.setBorder(new EmptyBorder(0, 20, 0, 0));
		JLabel j2 = new JLabel("Not in enough Data               "
				+ "                                                                                                           ");
		// int[] types = new int[4];

		// System.out.println(sub.numAssign());
		if (sub.numAssign() < 2) {

		} else {
			// create analysis

			boolean b;
			int j = 0;

			boolean failing = true;
			for (int i = 0; i < types.length; i++) {

				if (types[i] > 0.70) {

					failing = false;
					break;
				}

			}
			if (failing) {
				j2 = new JLabel(
						"You seem to be struggling quite a bit. Perhap ask for help or stop wasting your time.");
			} else {

				int max = 0, min = 0;
				for (int i = 1; i < 4; i++) {
					if (types[max] < types[i] && types[i] != 0.0) {
						max = i;
					}

					if (types[min] > types[i] && types[i] != 0.0) {
						min = i;
					}
				}
				String best = "", worst = "";
				switch (max) {

				case 0:
					best = "Test and Quiz";
					break;
				case 1:
					best = "Assignment";
					break;
				case 2:
					best = "Project";
					break;
				case 3:
					best = "Other";
					break;

				}
				switch (min) {

				case 0:
					worst = "Test and Quiz";
					break;
				case 1:
					worst = "Assignment";
					break;
				case 2:
					worst = "Project";
					break;
				case 3:
					worst = "Other";
					break;

				}
				if (best.equals(worst)) {
					j2 = new JLabel("You seem to be well balanced."
							+ "                                                                                                           ");
				} else {

					j2 = new JLabel("You seem to be doing well in " + best + ", however you seem to be struggling in "
							+ worst + ".");
				}
			}
		}

		c.add(new JLabel("Current Calibration"));
		c.add(new JLabel());
		c.add(new JLabel("Test and Quiz : "));
		c.add(new JLabel(sub.getTq() + "%"));
		c.add(new JLabel("Assignments : "));
		c.add(new JLabel(sub.getA() + "%"));
		c.add(new JLabel("Projects : "));
		c.add(new JLabel(sub.getP() + "%"));
		c.add(new JLabel("Other : "));
		c.add(new JLabel(sub.getO() + "%"));

		a.setLayout(new GridLayout(0, 2, 20, 0));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		j2.setBorder(new EmptyBorder(0, 20, 0, 0));
		ana.add(jl);
		ana.add(j2);
		a.add(ana);
		a.add(c);
		secondary.add(a, sub.getName());
		// System.out.println(sub.getName());
	}

	public void createTotal(Subject sub, JPanel t, double[] types, boolean b, int ind) {

		if (sub.getName().equals("None")) {
			total.add(t, "None");
			return;
		}
		double d = 0.0;

		if (sub.getTq() == 0 && sub.getA() == 0 && sub.getP() == 0 && sub.getO() == 0) {
			d = getTotal(sub);

		} else {
			double dd = 0;
			// System.out.println("ok");
			if (sub.getTq() != 0) {
				d = (sub.getTq() / 100) * types[0];

			} else {
				dd += sub.getTq();
			}

			if (sub.getA() != 0) {
				d += (sub.getA() / 100) * types[1];

			} else {
				dd += sub.getA();
			}

			if (sub.getP() != 0) {
				d += (sub.getP() / 100) * types[2];

			} else {
				dd += sub.getP();
			}

			if (sub.getO() != 0) {
				d += (sub.getO() / 100) * types[3];

			} else {
				dd += sub.getO();
			}

			d += dd * (100 - sub.getTq() - sub.getA() - sub.getP() - sub.getO());

		}
		String grade = "";
		if (d >= 0.86) {
			grade = "A";
		} else if (d >= 0.73) {
			grade = "B";
		} else if (d >= 0.67) {
			grade = "C+";
		} else if (d >= 0.60) {
			grade = "C";
		} else if (d >= 0.50) {
			grade = "C-";
		} else {
			grade = "F";
		}
		// System.out.println(d);
		if (b) {
			// scores.set(ind - 1, d);
		} else {
			scores.add(d);
		}
		if (d < 0.01) {
			t.add(new JLabel("Final Percent :N/A"));
		} else {
			t.add(new JLabel("Final Percent : " + df.format(d) + "; Grade: " + grade));
		}
		total.add(t, sub.getName() + "");
	}

	public double getTotal(Subject sub) {
		double d = 0;
		if (sub.numAssign() == 0) {
			return d;
		}
		for (int j = 0; j < sub.numAssign(); j++) {
			d += sub.getTaskscore(j);
		}

		d /= sub.numAssign();
		return d;
	}

	public Subject getCurrentSub() {
		return currentSub;
	}

	public int getCurrentIndex() {
		return tt.getIndex(currentSub.getName());
	}

	public Dimension getDimension() {
		return screenSize;
	}

	public ArrayList<Subject> getSubs() {
		ArrayList<Subject> arr = (ArrayList<Subject>) subjects.clone();
		return arr;
	}

	public ArrayList<Double> getScores() {
		ArrayList<Double> arr = (ArrayList<Double>) scores.clone();
		return arr;
	}

	public void calibratemarks(Subject sub, double tq, double a, double p, double o) {
		sub.setTq(tq);
		sub.setA(a);
		sub.setP(p);
		sub.setO(o);

	}

	public void resetSub() {
		String name = u.currentSub.getName();
		String sql = "truncate table " + name + u.currentSub.getID();
		try {
			driver.myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subjects.set(getCurrentIndex(), new Subject(currentSub.getName(), tt.getName(), tt.getID()));
		repaint(true);
	}

	public void reset() {
		for (int i = 0; i < subjects.size(); i++) {
			String name = subjects.get(i).getName();

			String sql = "truncate table " + name + subjects.get(i).getID();
			try {
				driver.myStmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			subjects.set(i, new Subject(subjects.get(i).getName(), tt.getName(), tt.getID()));
		}
		repaintAll();
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {

		CardLayout cl = (CardLayout) (main.getLayout());
		cl.show(main, (String) evt.getItem());
		CardLayout cl2 = (CardLayout) (secondary.getLayout());
		cl2.show(secondary, (String) evt.getItem());
		// System.out.println((String)evt.getItem());
		CardLayout cl3 = (CardLayout) (total.getLayout());
		cl3.show(total, (String) evt.getItem());
		currentSub = tt.get((String) evt.getItem());
		// System.out.println(currentSub);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (!currentSub.getName().equals("None")) {
			setEnabled(false);
			new Settings(this);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		// if(e.getButton() == MouseEvent.BUTTON3){
		//
		// }

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}