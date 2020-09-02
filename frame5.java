import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class frame5 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	final int a,b;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame5 frame = new frame5(0,0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frame5(int i,int j) {
		a=i;
		b=j;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVmCharacterstics = new JLabel("Datacenter "+i+"Vm "+j+" Characterstics");
		lblVmCharacterstics.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVmCharacterstics.setBounds(65, 16, 310, 20);
		contentPane.add(lblVmCharacterstics);
		
		JLabel lblRam = new JLabel("RAM(MB)");
		lblRam.setBounds(39, 68, 69, 20);
		contentPane.add(lblRam);
		
		JLabel lblSize = new JLabel("Size(MB)");
		lblSize.setBounds(39, 104, 69, 20);
		contentPane.add(lblSize);
		
		JLabel lblBw = new JLabel("Bw");
		lblBw.setBounds(39, 146, 69, 20);
		contentPane.add(lblBw);
		
		textField = new JTextField();
		textField.setBounds(131, 65, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(131, 101, 146, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("10000");
		
		textField_2 = new JTextField();
		textField_2.setBounds(131, 143, 146, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("1000");
		
		JButton btnSubmit = new JButton("Submit");
		getRootPane().setDefaultButton(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
			        int ram = Integer.parseInt(textField.getText());
			        int size = Integer.parseInt(textField_1.getText());
			        int bw = Integer.parseInt(textField_2.getText());
			        if(ram<=0||size<=0||bw<=0){
			        	throw new NumberFormatException();
			        }
			        int k=Greedy.AddVM(a,b,ram,size,bw);
			        if(k==0){
			        	throw new Exception("vm ram should be less than or equal to "+Greedy.biggesthost[a]);
			        }
			        if(k==2){
			        	throw new Exception("vm ram should be less than or equal to "+(Greedy.hostsize[a]-Greedy.vmsize[a]));
			        }
			        CloseFrame();
			    } catch (NumberFormatException nfe) {
			    	JOptionPane.showMessageDialog(null,"Enter valid input","Invalid Input",JOptionPane.WARNING_MESSAGE);
			    }
				catch (Exception e) {
			    	JOptionPane.showMessageDialog(null,e,"Invalid Input",JOptionPane.WARNING_MESSAGE);
			    }

			}
		});
		btnSubmit.setBounds(141, 185, 115, 29);
		contentPane.add(btnSubmit);
	}
	public void CloseFrame(){
	    super.dispose();
	}
	

}
