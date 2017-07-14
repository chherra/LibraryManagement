

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.text.NumberFormat;

public class frmAddEditBarrowed extends JDialog
{
	
	final static String slash   	 = "/";
	String sDateBrrwd 		= "";
	String sDateDue 		= "";
	String sDateReturned 	= "";

	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblBookNo	 	= new JLabel("Book Number:");
	JLabel lblBarrowersID	= new JLabel("Barrowers ID:");
	JLabel lblDateBarrowerd	= new JLabel("Date Barrowerd:");
	JLabel lblDateDue		= new JLabel("Date Due:");
	JLabel lblDateReturned 	= new JLabel("Date Returned:");
	JLabel lblNumdays 		= new JLabel("Num. of Days:");
	JLabel lblFines 		= new JLabel("Fines:");

	JComboBox cbBookNo;
	JComboBox cbBrrwsID;
	
	JComboBox cbMonth1 		= new JComboBox();
	JComboBox cbMonth2 		= new JComboBox();
	JComboBox cbMonth3 		= new JComboBox();
	JComboBox cbDay1 		= new JComboBox();
	JComboBox cbDay2 		= new JComboBox();
	JComboBox cbDay3 		= new JComboBox();

	JTextField txtDateBrrwd	= new JTextField("",4);
	JTextField txtDateDue	= new JTextField("",4);
	JTextField txtDateRtrn 	= new JTextField("",4);
	JTextField txtNumdays 	= new JTextField();
	JTextField txtFines 	= new JTextField();
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnBarrowed_Rec;
	Statement stmtBarrowed_Rec;
	Statement stmtBooks;
	ResultSet rsBarrowed_Rec;
	ResultSet rsBooks;
	String sqlBooks;
	String strRemain;

	boolean ADDING_STATE;
	
	public frmAddEditBarrowed(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm, true);
		cnBarrowed_Rec = conn;
		initComboBoxes();

		ADDING_STATE = ADD_STATE;
		try
		{
			stmtBarrowed_Rec = cnBarrowed_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmtBooks = cnBarrowed_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		}
		catch(SQLException sqlEx){}

		cbBookNo = mdlFunctions.fillCombo("SELECT * FROM tblBooks",cnBarrowed_Rec,"BookNo");
		cbBrrwsID = mdlFunctions.fillCombo("SELECT * FROM tblBarrowers",cnBarrowed_Rec,"BarrowersID");
		
		if(ADD_STATE==true)
		{
			bttnUpdate.setText("Add");
			txtFines.setVisible(false);
			txtNumdays.setVisible(false);
			txtDateRtrn.setVisible(false);
			lblFines.setVisible(false);
			lblNumdays.setVisible(false);
			lblDateReturned.setVisible(false);
			cbMonth3.setVisible(false);
			cbDay3.setVisible(false);
		}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsBarrowed_Rec = stmtBarrowed_Rec.executeQuery(sSQL);
				rsBarrowed_Rec.next();

				bttnReset.setEnabled(false);
				cbBookNo.setEnabled(false);
				cbBookNo.setSelectedItem ("" + rsBarrowed_Rec.getString("BookNo"));
				cbBrrwsID.setSelectedItem("" + rsBarrowed_Rec.getString("BarrowersID"));
				
				String dtBarrowed = rsBarrowed_Rec.getString("DateBarrowerd");
				String dtDue = rsBarrowed_Rec.getString("DateDue");
				String dtReturned = rsBarrowed_Rec.getString("DateReturned");
				
				txtDateBrrwd.setText(module_func.displayYear(dtBarrowed));
				cbDay1.setSelectedItem(module_func.displayDay(dtBarrowed));
				cbMonth1.setSelectedIndex((module_func.displayMonth(dtBarrowed)) -1);

				txtDateDue.setText(module_func.displayYear(dtDue));
				cbDay2.setSelectedItem(module_func.displayDay(dtDue));
				cbMonth2.setSelectedIndex((module_func.displayMonth(dtDue)) -1);
				
				if(dtReturned != null)
				{
					cbMonth3.setSelectedIndex((module_func.displayMonth(dtReturned)) -1);
					txtDateRtrn.setText(module_func.displayYear(dtReturned));
					cbDay3.setSelectedItem(module_func.displayDay(dtReturned));
				}
				else if(dtReturned == null)
				{
					txtFines.setVisible(false);
					txtNumdays.setVisible(false);
					txtDateRtrn.setVisible(false);
					lblFines.setVisible(false);
					lblNumdays.setVisible(false);
					lblDateReturned.setVisible(false);
					cbMonth3.setVisible(false);
					cbDay3.setVisible(false);
				}
				
				txtNumdays.setText("" 	+ rsBarrowed_Rec.getString("NO_OF_DAYS_AFTER_DUE_DATE"));
				txtFines.setText("" 	+ rsBarrowed_Rec.getDouble("Fines"));
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/returners.gif"));

		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblBookNo,5,50,105,20);
		module_func.setJLabel(lblBarrowersID,5,72,105,20);
		module_func.setJLabel(lblDateBarrowerd,5,94,105,20);
		module_func.setJLabel(lblDateDue,5,116,105,20);
		module_func.setJLabel(lblDateReturned,5,138,105,20);
		module_func.setJLabel(lblNumdays,5,160,105,20);
		module_func.setJLabel(lblFines,5,182,105,20);

		module_func.setJComboBox(cbBookNo,110,50,200,20);
		module_func.setJComboBox(cbBrrwsID,110,72,200,20);
		module_func.setJComboBox(cbMonth1,160,94,90,20);
		module_func.setJComboBox(cbMonth2,160,116,90,20);
		module_func.setJComboBox(cbMonth3,160,138,90,20);
		module_func.setJComboBox(cbDay1,250,94,60,20);
		module_func.setJComboBox(cbDay2,250,116,60,20);
		module_func.setJComboBox(cbDay3,250,138,60,20);

		module_func.setJTextField(txtDateBrrwd,110,94,50,20);
		module_func.setJTextField(txtDateDue,110,116,50,20);
		module_func.setJTextField(txtDateRtrn,110,138,50,20);
		module_func.setJTextField(txtNumdays,110,160,200,20);
		module_func.setJTextField(txtFines,110,182,200,20);

		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblBookNo);
		jpnlMain.add(lblBarrowersID);
		jpnlMain.add(lblDateBarrowerd);
		jpnlMain.add(lblDateDue);
		jpnlMain.add(lblDateReturned);
		jpnlMain.add(lblNumdays);
		jpnlMain.add(lblFines);

		jpnlMain.add(cbBookNo);
		jpnlMain.add(cbBrrwsID);
		jpnlMain.add(cbMonth1);
		jpnlMain.add(cbMonth2);
		jpnlMain.add(cbMonth3);
		jpnlMain.add(cbDay1);
		jpnlMain.add(cbDay2);
		jpnlMain.add(cbDay3);

		jpnlMain.add(txtDateBrrwd);
		jpnlMain.add(txtDateDue);
		jpnlMain.add(txtDateRtrn);
		jpnlMain.add(txtNumdays);
		jpnlMain.add(txtFines);

		//-- Add the Update Button
		module_func.setJButton(bttnUpdate,5,270,105,25,"update","UPDATE");
		bttnUpdate.setMnemonic(KeyEvent.VK_A);
		bttnUpdate.addActionListener(JBActionListener);

		//-- Add the Reset Button
		module_func.setJButton(bttnReset,112,270,99,25,"reset","RESET");
		bttnReset.setMnemonic(KeyEvent.VK_R);
		bttnReset.addActionListener(JBActionListener);

		//-- Add the Cancel Button
		module_func.setJButton(bttnCancel,212,270,99,25,"cancel","CANCEL");
		bttnCancel.setMnemonic(KeyEvent.VK_C);
		bttnCancel.addActionListener(JBActionListener);

		jpnlMain.add(bttnUpdate);
		jpnlMain.add(bttnReset);
		jpnlMain.add(bttnCancel);

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);
				
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(325,335);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-335)/2));
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
			cbMonth2.addItem(String.valueOf(months[m]));
			cbMonth3.addItem(String.valueOf(months[m]));
		}
		
		// set integer format to 2 digits (00)
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);

		// list of day in days combo box
    	for(int day = 1; day < 32; day ++){
			cbDay1.addItem(nf.format(day));
			cbDay2.addItem(nf.format(day));
			cbDay3.addItem(nf.format(day));
		}

	}	


	private void resetField()
	{
		cbBookNo.setSelectedIndex(0);
		cbBrrwsID.setSelectedIndex(0);
		cbMonth1.setSelectedIndex(0);
		cbMonth2.setSelectedIndex(0);
		cbMonth3.setSelectedIndex(0);
		cbDay1.setSelectedIndex(0);
		cbDay2.setSelectedIndex(0);
		cbDay3.setSelectedIndex(0);
		
		txtDateBrrwd.setText("");
		txtDateDue.setText("");
		txtDateRtrn.setText("");
		txtNumdays.setText("");
		txtFines.setText("");
	}
	
	private void updateField()
	{
		String sDateBarrwd   = (String)txtDateBrrwd.getText() + slash +
			   		 (cbMonth1.getSelectedIndex() + 1) + slash +
			  	     (String)cbDay1.getSelectedItem();
		String sDateDue   = (String)txtDateDue.getText() + slash +
			   		 (cbMonth2.getSelectedIndex() + 1) + slash +
			  	     (String)cbDay2.getSelectedItem();

		String sDateReturned   = (String)txtDateRtrn.getText() + slash +
			   		 (cbMonth3.getSelectedIndex() + 1) + slash +
			  	     (String)cbDay3.getSelectedItem();

		if(module_func.isTextEmpty(txtDateDue, lblDateDue) == false){}
		if(module_func.isTextEmpty(txtDateBrrwd, lblDateBarrowerd) == false)
		{
			
			if(ADDING_STATE == true)
			{
				try
				{
					stmtBarrowed_Rec.executeUpdate("INSERT INTO tblBarrowedBooks (BookNo,BarrowersID,DateBarrowerd,DateDue) " +
	   							   	        "VALUES ('" 			+ 
	   							   	        cbBookNo.getSelectedItem().toString() 		+ "', '" +  
	   							   	        cbBrrwsID.getSelectedItem().toString()  		+ "', '" +  
	   							   	        sDateBarrwd 	+ "', '" +  
	   							   	        sDateDue 		+ "')");
   					frmBarrowed.reloadRecord("SELECT * FROM qryBarrowedBooks ORDER BY Category ASC");
   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
   					String ObjButtons[] = {"Yes","No"};
					int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
					if(PromptResult==0)
					{
						resetField();
						cbBookNo.requestFocus(true);
					}
					else{dispose();}
	   			}
   				catch(SQLException sqlEx)
   				{
   					JOptionPane.showMessageDialog(null,"Barrowed ID is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
   					System.out.println(sqlEx.getMessage());
   				}
				}
			else
			{
				try
				{	   	        
					stmtBarrowed_Rec.executeUpdate("UPDATE tblBarrowedBooks SET BarrowersID 	= 	'" +
							   	        cbBrrwsID.getSelectedItem().toString()  		+ "', 	 DateBarrowerd		= 	'" + 
							   	        sDateBarrwd 	+ "', 	 	 DateDue 	= 	'" +
							   	        sDateDue 		+ "', 	 DateReturned 		= 	'" +
							   	        sDateReturned	+ "', 	 NO_OF_DAYS_AFTER_DUE_DATE 		= 	'" +
							   	        txtNumdays.getText() 	+ "', 	 Fines		= 	'" +
							   	        txtFines.getText() 	+
							   	        "' WHERE BookNo = '" + cbBookNo.getSelectedItem().toString() + " '");
	   				frmBarrowed.reloadRecord("SELECT * FROM qryBarrowedBooks ORDER BY Category ASC");
	   				JOptionPane.showMessageDialog(null,"Changes in the record has been successfully save.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch(SQLException sqlEx)
				{
					
	   				JOptionPane.showMessageDialog(null,sqlEx.getMessage(),"Library System ver. 1",JOptionPane.ERROR_MESSAGE);
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