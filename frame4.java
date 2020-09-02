import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class frame4 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSubmit;
	private int i;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame4 frame = new frame4(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public frame4(int a) {
		i=a;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNoOfVirtual = new JLabel("No of Virtual machines:");
		lblNoOfVirtual.setBounds(34, 96, 192, 36);
		contentPane.add(lblNoOfVirtual);
		
		textField = new JTextField();
		textField.setBounds(213, 101, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		getRootPane().setDefaultButton(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
			        int x = Integer.parseInt(textField.getText());
			        if(x<=0){
			        	throw new NumberFormatException();
			        }
			        CloseFrame();
			        if(i==Greedy.numofdc){
			        	Greedy.SetNvm(x);
			        }
			        for(int j=x;j>=1;j--){
			        	frame5 fifthframe = new frame5(i,j);
						fifthframe.setVisible(true);
			        }
			    } catch (NumberFormatException nfe) {
			    	JOptionPane.showMessageDialog(null,"Enter valid input","Invalid Input",JOptionPane.WARNING_MESSAGE);
			    }
			}
		});
		btnSubmit.setBounds(143, 148, 115, 29);
		contentPane.add(btnSubmit);
		
		JLabel label = new JLabel("DataCenter:"+i);
		label.setBounds(127, 16, 161, 29);
		contentPane.add(label);
	}
	public void CloseFrame(){
	    super.dispose();
	}

}
