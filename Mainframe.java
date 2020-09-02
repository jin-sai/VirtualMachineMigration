import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;


public class Mainframe {

	private JFrame frame;
	private JTextField textField;
	public static int no_of_datacenters;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainframe window = new Mainframe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Mainframe() {
		initialize();
	}

	
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNoOfData = new JLabel("No of data centers :");
		lblNoOfData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNoOfData.setBounds(71, 88, 167, 62);
		frame.getContentPane().add(lblNoOfData);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(152, 158, 115, 29);
		frame.getContentPane().add(btnNewButton);
		frame.getRootPane().setDefaultButton(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(236, 107, 146, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					no_of_datacenters = Integer.parseInt(textField.getText());
					if(no_of_datacenters<=0){
			        	throw new NumberFormatException();
			        }
					System.out.println("no of datacenters="+no_of_datacenters);
					frame.setVisible(false);
					Greedy.SetNDC(no_of_datacenters);
					for(int i=no_of_datacenters;i>=1;i--){
			        	frame4 fourthframe = new frame4(i);
						fourthframe.setVisible(true);
			        }
			        for(int i=no_of_datacenters;i>=1;i--){
			        	frame2 secondframe = new frame2(i);
						secondframe.setVisible(true);
			        }
			    } catch (NumberFormatException nfe) {
			    	JOptionPane.showMessageDialog(null,"Enter valid input","Invalid Input",JOptionPane.WARNING_MESSAGE);
			    }
			}
		});
	}
}
