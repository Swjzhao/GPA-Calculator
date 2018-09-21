package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calib extends JFrame implements ActionListener{
	
	private JPanel contentPane; 
	private JTextField tqt;
	private JTextField agnt;
	private JTextField pjtt;
	private JTextField otht;
	
	private UI u;
	private Subject sub;
	public Calib(UI u, Subject sub){
		
		setTitle("Settings");
		setResizable(false);
		setBounds(200,200,450,270);
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(0,2));
		
		this.u = u;
		this.sub = sub;
		
		JLabel t = new JLabel("Score Distribution");
		JLabel ept = new JLabel("Ex. 50");
		JLabel tq = new JLabel("Test: ");
		tqt = new JTextField(10);
		tqt.setText("" + 0);
		JLabel agn = new JLabel("Assignment: ");
		agnt = new JTextField(10);
		agnt.setText("" + 0);
		JLabel pjt = new JLabel("Project: ");
		pjtt = new JTextField(10);
		pjtt.setText("" + 0);
		JLabel oth = new JLabel("Other: ");
		otht = new JTextField(10);
		otht.setText("" + 0);
		JLabel ee = new JLabel();
		JButton confirm = new JButton("Confirm");
		
		confirm.addActionListener(this);
		contentPane.add(t);
		contentPane.add(ept);
		contentPane.add(tq);
		contentPane.add(tqt);
		contentPane.add(agn);
		contentPane.add(agnt);
		contentPane.add(pjt);
		contentPane.add(pjtt);
		contentPane.add(oth);
		contentPane.add(otht);
		contentPane.add(ee);
		contentPane.add(confirm);
		
		
		setContentPane(contentPane);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		double a = 0,b = 0,c = 0,d =0 ;
		try{
			a = Double.parseDouble(tqt.getText());
			b = Double.parseDouble(agnt.getText());
			c = Double.parseDouble(pjtt.getText());
			d = Double.parseDouble(otht.getText());
			if(a+b+c+d > 100){
				JOptionPane.showMessageDialog(this, "This doesnt add up to 100%");
				return;
			}
			
			u.calibratemarks(sub, a, b, c, d);
			
			Driver driver = new Driver(sub.getStudName() + "" +sub.getID());
			String sql = "update " + driver.getName().toLowerCase() + " " +
					"set c1 = '" + a + "' , " + 
					"c2 = '" + b + "' , " + 
					"c3 = '" + c + "' , " + 
					"c4 = '" + d + "' " + 
					"where course= '" + sub.getName() + "'";
			System.out.println(sql);
			driver.myStmt.execute(sql);
			u.repaint(true);
			dispose();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "PLEASE ENTER ONLY THE PERCENTAGE. EX. 40");
			e.printStackTrace();
		}
		
	}

}
