import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;


public class frame2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSubmit;
	private int i;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame2 frame = new frame2(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public frame2(int a) {
		i=a;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNoOfHosts = new JLabel("No of hosts:");
		lblNoOfHosts.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNoOfHosts.setBounds(86, 113, 114, 40);
		contentPane.add(lblNoOfHosts);
		
		textField = new JTextField();
		textField.setBounds(188, 121, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(148, 173, 115, 29);
		contentPane.add(btnSubmit);
		getRootPane().setDefaultButton(btnSubmit);
		
		JLabel lblDatacenter = new JLabel("DataCenter:"+i);
		lblDatacenter.setBounds(148, 16, 161, 29);
		contentPane.add(lblDatacenter);
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
			        int x = Integer.parseInt(textField.getText());
			        if(x<=0){
			        	throw new NumberFormatException();
			        }
			        CloseFrame();
			        for(int j=x;j>=1;j--){
			        	frame3 thirdframe = new frame3(i,j);
						thirdframe.setVisible(true);
			        }
			    } catch (NumberFormatException nfe) {
			    	JOptionPane.showMessageDialog(null,"Enter valid input","Invalid Input",JOptionPane.WARNING_MESSAGE);
			    }
			}
		});
	}
	public void CloseFrame(){
	    super.dispose();
	}
}
