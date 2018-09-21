package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddingWindow extends JFrame implements ItemListener{

	//for action with adding window
	//creates user interface to add name, type, score/maxscore, taskscore
	
	private JPanel contentPane;
	private JComboBox cb;
	private JFrame frame;
	private String str;
	
	public AddingWindow(Subject sub,UI u){
		
		setBounds(200,200,450,270);
		setTitle("Create new item");
		frame = this;
		contentPane = new JPanel(new GridLayout(5,2,0,15));
		
		
		
		String[] arr = {"Test and Quiz","Assignment","Project","Other"};
		cb = new JComboBox(arr);
		cb.setEditable(false);
		cb.addItemListener(this);
		
		str = arr[0];
		
		JLabel nl = new JLabel("	Name");
		JTextField name = new JTextField(10);
		JLabel tl = new JLabel("	Type");
		//JTextField type = new JTextField(10);
		JLabel sl = new JLabel("	Score");
		JTextField score = new JTextField(10);
		
		JLabel nullL = new JLabel();
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				int i = 0;
				double d = 0;
				try{
					String[] parts = score.getText().split("/");
					d = Double.parseDouble(parts[0]) ;
					i = Integer.parseInt(parts[1]);
					sub.addScore(name.getText(),str,d,i);
					
					u.repaint(true);
					dispose();
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "PLEASE INPUT IN THIS FORMAT: 0/0");
					//score.setText("PLEASE INPUT IN THIS FORMAT: 0/0");
				}
				
				
			}
		});
		
		contentPane.add(nl);
		contentPane.add(name);
		contentPane.add(tl);
		contentPane.add(cb);
		contentPane.add(sl);
		contentPane.add(score);

		contentPane.add(nullL);
		contentPane.add(confirm);
		
		setContentPane(contentPane);
		//pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		str = (String)e.getItem();
		
	}
	
	
}
