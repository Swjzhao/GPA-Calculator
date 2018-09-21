package main;

import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Table extends JFrame{
	
	private JPanel contentPane;
	
	public Table(UI u){
		
		contentPane = new JPanel();
		setBounds(200,200,0,0);
		contentPane.setLayout(new GridLayout(0,3,10,10));
		DecimalFormat df = new DecimalFormat("###.##%");
		for(int i = 0 ; i < u.getSubs().size(); i++){
			contentPane.add(new JLabel(u.getSubs().get(i).getName()));
			double d = u.getScores().get(i);
			contentPane.add(new JLabel(df.format(d)+ ""));
			String grade = "";
			if(d >= 0.86){
				grade = "A";
			}else if(d >= 0.73){
				grade = "B";
			}else if(d >= 0.67){
				grade = "C+";
			}else if(d >= 0.60){
				grade = "C";
			}else if(d >= 0.50){
				grade = "C-";
			}else{
				grade = "F";
			}
			contentPane.add(new JLabel(grade));
		}
		setContentPane(contentPane);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
}
