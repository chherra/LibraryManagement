

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.text.NumberFormat;


public class frmReturn extends JDialog
{
	
	final static String slash   	 = "/";
	String sDateReturned = "";

	Dimension screen 		 = 	Toolkit.getDefaultToolkit().getScreenSize();
	JPanel 	jpnlMain  		 = new JPanel();

	JButton bttnFines 		 = new JButton("Fines",		new ImageIcon("@imgs/update.gif"));
	JButton bttnUpdate 		 = new JButton("Update",	new ImageIcon("@imgs/save.gif"));
	JButton bttnExit 		 = new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));
	
	JLabel  lblHeader	 	 = new JLabel();
	JLabel  lblIcon		 	 = new JLabel();
	JLabel  lblCaption		 = new JLabel("DATE FORMAT: YYYY-MMM-DD.");
	JLabel  lblReturn	 	 = new JLabel("Date Return:");
	JLabel 	lblNumDays 	 	 = new JLabel("NO. of Days:");
	JLabel 	lblFines	 	 = new JLabel("Fines:");

	JComboBox cbMonth1 		 = new JComboBox();
	JComboBox cbDay1 		 = new JComboBox();

	JTextField txtYear  	 = new JTextField();	
	JTextField txtNoDays	 = new JTextField();	
	JTextField txtFines  	 = new JTextField();	

	Connection cnReturn;
	Statement stmtReturn;
	Statement stmtFines;
	Statement stmtBooks;
	ResultSet rsReturn;		//Recordset
	ResultSet rsFines;		//Recordset
	ResultSet rsBooks;		//Recordset
	
	String sqlFines;
	String sqlReturn;
	String sqlBooks;
	String strFines;
	String strRemain;
	String sBookNum;
	String sBrrwsID;
	String sReturned;
	
	mdlFunctions module_func = new mdlFunctions();

	public frmReturn(JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm,true);
		
		cnReturn = conn;
		sqlFines = "SELECT * FROM tblFines";
		
		initComboBoxes();

		try
		{
			stmtReturn= cnReturn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmtFines = cnReturn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}
				
		try
		{
			rsFines = stmtFines.executeQuery(sqlFines);		
			rsFines.next();
		 	strFines = ("" + rsFines.getInt("Fines"));
		}
		catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}

		try
		{
			rsReturn = stmtFines.executeQuery(sSQL);
			rsReturn.next();
			
		 	sBookNum = ("" + rsReturn.getString("BookNo"));
		 	sBrrwsID = ("" + rsReturn.getString("BarrowersID"));
		}
		catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}

		sqlBooks = "SELECT * FROM tblBooks WHERE BookNo ='" + sBookNum + " '";
		
		try
		{
			stmtBooks= cnReturn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		try
		{
			rsBooks = stmtBooks.executeQuery(sqlBooks);
			rsBooks.next();
		 	strRemain = ("" + rsBooks.getInt("Remaining"));
		}
		catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/returners.gif"));
		
		module_func.setJLabel(lblCaption,60,2,500,40);
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));
			
		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblReturn,5,50,105,20);
		module_func.setJLabel(lblNumDays,5,72,105,20);
		module_func.setJLabel(lblFines,5,94,105,20);
		
		module_func.setJComboBox(cbMonth1,160,50,90,20);
		module_func.setJComboBox(cbDay1,250,50,60,20);

		module_func.setJTextField(txtYear,110,50,50,20);
		module_func.setJTextField(txtNoDays,110,72,200,20);
		module_func.setJTextField(txtFines,110,94,200,20);
		txtFines.setEditable(false);
		
		module_func.setJButton(bttnExit,210,140,100,24,"exit","Unload Form");
		module_func.setJButton(bttnUpdate,110,140,100,24,"update","Update");
		module_func.setJButton(bttnFines,5,140,100,24,"fines","Fines");
		
		bttnExit.setMnemonic(KeyEvent.VK_E);
		bttnUpdate.setMnemonic(KeyEvent.VK_U);
		bttnFines.setMnemonic(KeyEvent.VK_F);
		
		bttnExit.addActionListener(JBActionListener);
		bttnUpdate.addActionListener(JBActionListener);
		bttnFines.addActionListener(JBActionListener);

		//Add TextField
		jpnlMain.add(txtYear);
		jpnlMain.add(txtNoDays);
		jpnlMain.add(txtFines);
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblReturn);
		jpnlMain.add(lblNumDays);
		jpnlMain.add(lblFines);
		//ComboBox
		jpnlMain.add(cbMonth1);
		jpnlMain.add(cbDay1);
		//Add Buttons
		jpnlMain.add(bttnExit);
		jpnlMain.add(bttnUpdate);	
		jpnlMain.add(bttnFines);	

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(325,200);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-180)/2));

	}
	
	void initComboBoxes()
	{
		String[] months = 
		{
	      	"Jan", "Feb", "Mar", "Apr", "May", "Jun",
	      	"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
	    };
    
    	for(int m = 0; m < 12; m++)
    	{
			cbMonth1.addItem(String.valueOf(months[m]));
		}
		
		// set integer format to 2 digits (00)
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);

		// list of day in days combo box
    	for(int day = 1; day < 32; day ++)
    	{
			cbDay1.addItem(nf.format(day));
		}

	}	

	protected void update()
	{
		String sDateReturned   = (String)txtYear.getText()  + slash +
	   		 (cbMonth1.getSelectedIndex() + 1) 				+ slash +
	  	     (String)cbDay1.getSelectedItem();
	  	     
		if(module_func.isTextEmpty(txtYear,  lblReturn) == false){}
		if(module_func.isTextEmpty(txtNoDays,lblNumDays)== false){}
		if(module_func.isTextEmpty(txtFines, lblFines)  == false)
		{
			try
			{	   	        
				int iRemain = (int)((Double.parseDouble(strRemain) + 1 ));

				stmtReturn.executeUpdate("UPDATE tblBarrowedBooks SET DateReturned 	= 	'" +
						   	        sDateReturned	+ "', 	 NO_OF_DAYS_AFTER_DUE_DATE 		= 	'" +
						   	        txtNoDays.getText() 	+ "', 	 Fines		= 	'" +
						   	        txtFines.getText() 	+
						   	        "' WHERE BookNo = '" + sBookNum + " ' and BarrowersID ='" + sBrrwsID + " '");								
				stmtBooks.executeUpdate("UPDATE tblBooks SET Remaining 	= " + iRemain + " WHERE BookNo = '" + sBookNum + " '");
				frmBarrowed.reloadRecord("SELECT * FROM qryBarrowedBooks ORDER BY Category ASC");
				frmReturnedBooks.reloadRecord("SELECT * FROM qryReturnedBooks ORDER BY BookNo ASC");
				frmDueBooks.reloadRecord("SELECT * FROM qryDueBooks ORDER BY BookNo ASC");
				JOptionPane.showMessageDialog(null,"Changes in the record has been successfully save.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}
	}
	
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj == "update"){update();}
			else if(srcObj == "fines")
			{
				int iFines = (int)((Double.parseDouble(txtNoDays.getText())) 
								 * (Double.parseDouble(strFines)));
				txtFines.setText(iFines + "");
			}
			else if(srcObj=="exit"){dispose();}
		}
	};
}