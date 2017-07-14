
 
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditBarrowers extends JDialog
{
	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: Text Fields must not empty.");
	JLabel lblBarrowersID 	= new JLabel("Barrower's ID:");
	JLabel lblName 			= new JLabel("Barrower's Name:");
	JLabel lblAddress 		= new JLabel("Address:");
	JLabel lblYear 			= new JLabel("Current Year:");
	JLabel lblCourse 		= new JLabel("Course:");
	JLabel lblSection 		= new JLabel("Section:");

	JTextField txtBID 		= new JTextField();
	JTextField txtBName 	= new JTextField();
	JTextField txtAddress 	= new JTextField();
	
	JComboBox cbYear;
	JComboBox cbCourse;
	JComboBox cbSection;
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnBarrowers_Rec;
	Statement stmtBarrowers_Rec;
	ResultSet rsBarrowers_Rec;

	boolean ADDING_STATE;

	public frmAddEditBarrowers(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm, true);
		cnBarrowers_Rec = conn;

		ADDING_STATE = ADD_STATE;
		try
		{
			stmtBarrowers_Rec = cnBarrowers_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		cbYear = mdlFunctions.fillCombo("SELECT * FROM qryYearList",cnBarrowers_Rec,"Year");
		cbCourse = mdlFunctions.fillCombo("SELECT * FROM qryCourseList",cnBarrowers_Rec,"Course");
		cbSection = mdlFunctions.fillCombo("SELECT * FROM qrySectionList",cnBarrowers_Rec,"Section");

		if(ADD_STATE==true){bttnUpdate.setText("Add");}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsBarrowers_Rec = stmtBarrowers_Rec.executeQuery(sSQL);
				rsBarrowers_Rec.next();
				bttnReset.setEnabled(false);
				txtBID.setEnabled(false);
				txtBID.setText("" + rsBarrowers_Rec.getString("BarrowersID"));
				txtBName.setText("" + rsBarrowers_Rec.getString("BarrowersName"));
				txtAddress.setText("" + rsBarrowers_Rec.getString("Address"));
				cbYear.setSelectedItem("" + rsBarrowers_Rec.getString("CurrentYear"));
				cbCourse.setSelectedItem("" + rsBarrowers_Rec.getString("Course"));
				cbSection.setSelectedItem("" + rsBarrowers_Rec.getString("Section"));
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}
		
		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/ListBarrowers.gif"));

		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblBarrowersID,5,50,105,20);
		module_func.setJLabel(lblName,5,72,105,20);
		module_func.setJLabel(lblAddress,5,94,105,20);
		module_func.setJLabel(lblYear,5,116,105,20);
		module_func.setJLabel(lblCourse,5,138,105,20);
		module_func.setJLabel(lblSection,5,160,105,20);

		module_func.setJTextField(txtBID,110,50,200,20);
		module_func.setJTextField(txtBName,110,72,200,20);
		module_func.setJTextField(txtAddress,110,94,200,20);
		
		module_func.setJComboBox(cbYear,110,116,200,20);
		module_func.setJComboBox(cbCourse,110,138,200,20);
		module_func.setJComboBox(cbSection,110,160,200,20);

		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblBarrowersID);
		jpnlMain.add(lblName);
		jpnlMain.add(lblAddress);
		jpnlMain.add(lblYear);
		jpnlMain.add(lblCourse);
		jpnlMain.add(lblSection);

		jpnlMain.add(txtBID);
		jpnlMain.add(txtBName);
		jpnlMain.add(txtAddress);
		jpnlMain.add(cbYear);
		jpnlMain.add(cbCourse);
		jpnlMain.add(cbSection);

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
	
	private void resetField()
	{
		txtBID.setText("");
		txtBName.setText("");
		txtAddress.setText("");
		cbYear.setSelectedIndex(0);
		cbCourse.setSelectedIndex(0);
		cbSection.setSelectedIndex(0);
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtAddress, lblAddress) == false){}
		if(module_func.isTextEmpty(txtBName, lblName) == false){}
		if(module_func.isTextEmpty(txtBID, lblBarrowersID) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtBarrowers_Rec.executeUpdate("INSERT INTO tblBarrowers (BarrowersID,BarrowersName,Address,CurrentYear,Course,Section) " +
	   							   	        "VALUES ('" 							+ 
	   							   	        txtBID.getText() 						+ "', '" +  
	   							   	        txtBName.getText() 						+ "', '" +  
	   							   	        txtAddress.getText() 					+ "', '" +  
	   							   	        cbYear.getSelectedItem().toString()		+ "', '" +  
	   							   	        cbCourse.getSelectedItem().toString() 	+ "', '" +  
	   							   	        cbSection.getSelectedItem().toString() 	+ "')");
	   					frmBarrowers.reloadRecord("SELECT * FROM tblBarrowers ORDER BY BarrowersName ASC");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0){
							resetField();
							txtBID.requestFocus(true);
						}else{
							dispose();
						}
	   				}
	   				catch(SQLException sqlEx)
	   				{
	   					JOptionPane.showMessageDialog(null,"Barrowers ID is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
	   					System.out.println(sqlEx.getMessage());
	   				}
				}
			else
			{
				try
				{	   	        
					stmtBarrowers_Rec.executeUpdate("UPDATE tblBarrowers SET BarrowersName 	= 	'" +
							   	        txtBName.getText() 		+ "', 	 Address 		= 	'" + 
							   	        txtAddress.getText() 	+ "', 	 CurrentYear 	= 	'" +
							   	        cbYear.getSelectedItem().toString() 		+ "', 	 Course 		= 	'" +
							   	        cbCourse.getSelectedItem().toString()	+ "', 	 Section 		= 	'" +
							   	        cbSection.getSelectedItem().toString() 	+
							   	        "' WHERE BarrowersID = '" + txtBID.getText() + " '");
	   				frmBarrowers.reloadRecord("SELECT * FROM tblBarrowers ORDER BY BarrowersName ASC");
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