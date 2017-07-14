
 
 
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditUser extends JDialog
{
	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblUsername 		= new JLabel("Username :");
	JLabel lblPassword		= new JLabel("Password:");

	JTextField txtUsername	= new JTextField();
	JTextField txtPassword	= new JTextField();
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnCourse_Rec;
	Statement stmtCourse_Rec;
	ResultSet rsCourse_Rec;

	boolean ADDING_STATE;

	public frmAddEditUser(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm,true);
		cnCourse_Rec = conn;
		
		ADDING_STATE = ADD_STATE;
		try
		{
			stmtCourse_Rec = cnCourse_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		if(ADD_STATE==true){bttnUpdate.setText("Add");}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsCourse_Rec = stmtCourse_Rec.executeQuery(sSQL);
				rsCourse_Rec.next();
				bttnReset.setEnabled(false);
				txtUsername.setEnabled(false);
				txtUsername.setText("" + rsCourse_Rec.getString("UserName"));
				txtPassword.setText("" + rsCourse_Rec.getString("Password"));
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}		

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/users list.gif"));

		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblUsername,5,50,105,20);
		module_func.setJLabel(lblPassword,5,72,105,20);

		module_func.setJTextField(txtUsername,110,50,200,20);
		module_func.setJTextField(txtPassword,110,72,200,20);
		
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblUsername);
		jpnlMain.add(lblPassword);

		jpnlMain.add(txtUsername);
		jpnlMain.add(txtPassword);

		//-- Add the Update Button
		module_func.setJButton(bttnUpdate,5,100,105,25,"update","UPDATE");
		bttnUpdate.setMnemonic(KeyEvent.VK_A);
		bttnUpdate.addActionListener(JBActionListener);

		//-- Add the Reset Button
		module_func.setJButton(bttnReset,112,100,99,25,"reset","RESET");
		bttnReset.setMnemonic(KeyEvent.VK_R);
		bttnReset.addActionListener(JBActionListener);

		//-- Add the Cancel Button
		module_func.setJButton(bttnCancel,212,100,99,25,"cancel","CANCEL");
		bttnCancel.setMnemonic(KeyEvent.VK_C);
		bttnCancel.addActionListener(JBActionListener);

		jpnlMain.add(bttnUpdate);
		jpnlMain.add(bttnReset);
		jpnlMain.add(bttnCancel);

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(325,160);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-160)/2));
	}
	
	private void resetField()
	{
		txtUsername.setText("");
		txtPassword.setText("");
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtPassword, lblPassword) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtCourse_Rec.executeUpdate("INSERT INTO tblUsers (UserName,Password) " +
	   							   	        "VALUES ('" 			+ 
	   							   	        txtUsername.getText().toString()  		+ "', '" +  
	   							   	        txtPassword.getText() 	+ "')");
	   					frmUsers.reloadRecord("SELECT * FROM tblUsers ORDER BY UserName ASC ");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0){
							resetField();
							txtUsername.requestFocus(true);
						}else{
							dispose();
						}
	   				}
	   				catch(SQLException sqlEx)
	   				{
	   					JOptionPane.showMessageDialog(null,"Course is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
	   					System.out.println(sqlEx.getMessage());
	   				}
				}
			else
			{
				try
				{	   	        
					stmtCourse_Rec.executeUpdate("UPDATE tblUsers SET Password 	= 	'" +
							   	        txtPassword.getText()  	+
							   	        "' WHERE UserName = '" + txtUsername.getText() + " '");
	   				frmUsers.reloadRecord("SELECT * FROM tblUsers ORDER BY UserName ASC ");
	   				JOptionPane.showMessageDialog(null,"Changes in the record has been successfully save.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
			}
		}
	}
	
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj == "update"){updateField();}
			else if(srcObj == "reset"){resetField();}
			else if(srcObj=="cancel"){dispose();}
		}
	};

}