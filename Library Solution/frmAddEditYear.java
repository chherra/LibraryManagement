
 
 
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditYear extends JDialog
{
	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblYear 			= new JLabel("Enter Year:");

	JTextField txtYear 		= new JTextField();
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnYear_Rec;
	Statement stmtYear_Rec;

	boolean ADDING_STATE;

	public frmAddEditYear(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm,true);
		cnYear_Rec = conn;
		
		ADDING_STATE = ADD_STATE;
		try
		{
			stmtYear_Rec = cnYear_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/year list.gif"));

		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblYear,5,50,105,20);
		module_func.setJTextField(txtYear,110,50,200,20);        

		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblYear);
		jpnlMain.add(txtYear);

		//-- Add the Update Button
		module_func.setJButton(bttnUpdate,5,130,105,25,"update","UPDATE");
		bttnUpdate.setMnemonic(KeyEvent.VK_A);
		bttnUpdate.addActionListener(JBActionListener);

		//-- Add the Reset Button
		module_func.setJButton(bttnReset,112,130,99,25,"reset","RESET");
		bttnReset.setMnemonic(KeyEvent.VK_R);
		bttnReset.addActionListener(JBActionListener);

		//-- Add the Cancel Button
		module_func.setJButton(bttnCancel,212,130,99,25,"cancel","CANCEL");
		bttnCancel.setMnemonic(KeyEvent.VK_C);
		bttnCancel.addActionListener(JBActionListener);

		jpnlMain.add(bttnUpdate);
		jpnlMain.add(bttnReset);
		jpnlMain.add(bttnCancel);

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(325,200);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-200)/2));
	}
	
	private void resetField()
	{
		txtYear.setText("");
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtYear, lblYear) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtYear_Rec.executeUpdate("INSERT INTO tblYear (Year) " +
	   							   	        "VALUES ('" + 
	   							   	        txtYear.getText() + "')");
	   					frmYear.reloadRecord("SELECT * FROM tblYear ORDER BY Year ASC");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0)
						{
							resetField();
							txtYear.requestFocus(true);
						}
						else{dispose();}
	   				}
	   				catch(SQLException sqlEx)
	   				{
	   					JOptionPane.showMessageDialog(null,"Year is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
	   					System.out.println(sqlEx.getMessage());
	   				}
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