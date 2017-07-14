
import java.util.*;
import java.text.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.plaf.metal.*;

public class frmLogin extends JDialog
{
	public static JPanel jpnlMain  	 = new JPanel();
	
	final static String error      	 = "ERROR";		
	final static String genexception = "GENERAL EXCEPTION";		
	final static String relogin 	 = "Log-in failed! Please relog-in!";
	final static String login   	 = "LOG-IN";
	final static String driver  	 = "sun.jdbc.odbc.JdbcOdbcDriver";
	final static String url     	 = "jdbc:odbc:LibrarySystem";
	final static String DBUserName 	 = "Admin";
	final static String DBPassword 	 = "libsys";
	
	String sSQL			= "";

	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	
	//JButton Variables
	JButton bttnLogin  	= new JButton("Log-in",new ImageIcon("@imgs/switch.gif"));

	//JLabel Variables
	JLabel lblHeader	= new JLabel();
	JLabel lblCaption	= new JLabel("Pls. enter the correct username/password");
	JLabel lblUserName	= new JLabel("Username:");
	JLabel lblPassword	= new JLabel("Password:");

	//JTextField Variables
	JTextField txtUserName		= new JTextField("",10  );
	JPasswordField txtPassword 	= new JPasswordField(10 );
		
	mdlFunctions module_func = new mdlFunctions();

	public frmLogin(JFrame getParentFrame) throws SQLException
	{
		super(getParentFrame, true);
		
		module_func.setJButton(bttnLogin,135,110,105,25,"login","login");
		bttnLogin.addActionListener(new ButtonHandler());
		bttnLogin.setMnemonic('L');

		lblHeader.setIcon(new ImageIcon("@imgs/Barrowers Records.gif"));

		module_func.setJLabel(lblHeader,0,0,750,40);
		module_func.setJLabel(lblCaption,5,2,500,40);
		module_func.setJLabel(lblUserName,5,50,105,20);
		module_func.setJLabel(lblPassword,5,72,105,20);
		
		lblCaption.setForeground(new Color(255,255,255));
		
		module_func.setJTextField(txtUserName,70,50,170,20);
		module_func.setJTextField(txtPassword,70,72,170,20);
		txtPassword.setFont(new Font("Dialog", Font.BOLD, 14));

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);
		
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblUserName);
		jpnlMain.add(lblPassword);

		//Add TextBox
		jpnlMain.add(txtUserName);
		jpnlMain.add(txtPassword);

		//Add Buttons
		jpnlMain.add(bttnLogin);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(255,170);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 255)/2,((screen.height-170)/2)-17);	
 	}
	
	public void login()
	{
		
		String user = txtUserName.getText();
		user = user.trim();
		
		String password = txtPassword.getText();

		sSQL = "SELECT * FROM tblUsers WHERE UserName='"+
			   user+"'  AND Password='"+password+"'";		
				
		try
		{
			Class.forName(driver);
		}
		catch(java.lang.ClassNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(), error ,
			JOptionPane.PLAIN_MESSAGE);
		}
		
		try
		{
			Connection conn = DriverManager.getConnection(url,DBUserName ,DBPassword);
			Statement stmt   = conn.createStatement();

			stmt.execute(sSQL);
			ResultSet rs = stmt.getResultSet();
			
			boolean recordfound = rs.next();

			if (recordfound)
			{
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null,relogin, error,
					JOptionPane.INFORMATION_MESSAGE);
				txtUserName.setText("");	
				txtPassword.setText("");
			}
			conn.close();
			
		}
		catch(Exception ex)
		{

			JOptionPane.showMessageDialog(null,ex.getMessage(), genexception,
			JOptionPane.INFORMATION_MESSAGE);
		}		
	}	

	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String action = e.getActionCommand();

			if(action=="login")
			{
				login();
				txtUserName.requestFocus();					
			}	
		}
	}
		
}
