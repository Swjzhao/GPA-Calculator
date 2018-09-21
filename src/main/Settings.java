package main;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Settings extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane; 
	private JPanel pane;
	
	private JButton calib;
	private JButton view;
	private JButton resetSub;
	private JButton reset;
	
	private JComboBox resolution;
	private JLabel label;
	private UI u;
	private JFrame frame;
	
	public Settings(UI u){
		
		this.u = u;
		this.frame = frame;
		setTitle("Settings");
		setResizable(false);
		setBounds((int)(u.width*0.5-u.width*0.2/2),(int)(u.height*0.5-u.height*0.30/2),(int)(u.width*0.2),(int)(u.height*0.30));
		//setBounds(200,200,200,300);
		System.out.println(u.width + " " + u.height);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				u.setEnabled(true);
			}
		});
		

		contentPane = new JPanel();
		pane = new JPanel();
		pane.setSize(100, 350);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		//contentPane.setLayout(new FlowLayout(FlowLayout.LEADING));
		label = new JLabel("Settings for " + u.getCurrentSub().getName());
		
		calib = new JButton("Calibrate Marks");
		calib.addActionListener(this);
		
		view = new JButton("View All Grades  ");
		view.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Table(u); 
			}
		});
		
		resetSub = new JButton("Reset Score       ");
		
		resetSub.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(u.currentSub.getName());
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset scores for this subject", "Confirm", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	u.resetSub();
	        }
	        else {
	           
	        }
			}
		});
		reset = new JButton("Reset All Scores");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to All reset scores", "Confirm", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	u.reset();
	        }
	        else {
	           
	        }
			}
		});
		pane.add(label);
		pane.add(new JLabel(" "));
		
		pane.add(calib);
		pane.add(resetSub);
		pane.add(new JLabel(" "));
		pane.add(new JLabel("General Settings"));
		pane.add(new JLabel(" "));
		pane.add(view);
		pane.add(reset);
		pane.add(new JLabel(" "));
		pane.add(new JLabel("Created by Steven Zhao"));
		
		contentPane.add(pane);
		
		setContentPane(contentPane);
	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new Calib(u, u.currentSub);
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
