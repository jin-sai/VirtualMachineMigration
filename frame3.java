import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;




public class frame3 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	final int a,b;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame3 frame = new frame3(0,0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frame3(int i,int j) {
		a=i;
		b=j;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHostCharacterstics = new JLabel("Datacenter "+i+" Host "+j+" Characterstics");
		lblHostCharacterstics.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHostCharacterstics.setBounds(46, 16, 335, 33);
		contentPane.add(lblHostCharacterstics);
		
		JLabel lblRam = new JLabel("RAM(MB)");
		lblRam.setBounds(46, 68, 69, 20);
		contentPane.add(lblRam);
		
		JLabel lblNewLabel = new JLabel("Storage(MB)");
		lblNewLabel.setBounds(46, 110, 69, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bw");
		lblNewLabel_1.setBounds(46, 152, 69, 20);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(137, 65, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(137, 107, 146, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("1000000");
		
		textField_2 = new JTextField();
		textField_2.setBounds(137, 149, 146, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("10000");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(147, 201, 115, 29);
		contentPane.add(btnSubmit);
		getRootPane().setDefaultButton(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
			        int ram = Integer.parseInt(textField.getText());
			        int storage = Integer.parseInt(textField_1.getText());
			        int bw = Integer.parseInt(textField_2.getText());
			        if(ram<=0||storage<=0||bw<=0){
			        	throw new NumberFormatException();
			        }
			        Greedy.AddHost(a,b,ram,storage,bw);
			        CloseFrame();
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
