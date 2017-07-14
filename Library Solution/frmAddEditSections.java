
 
 
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditSections extends JDialog
{
	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblYear 			= new JLabel("Year:");
	JLabel lblCourse		= new JLabel("Course:");
	JLabel lblSections 		= new JLabel("Sections:");
	JLabel lblAdviser		= new JLabel("Adviser:");

	JComboBox cbYear;
	JComboBox cbCourse;
	
	JTextField txtSections	= new JTextField();
	JTextField txtAdviser	= new JTextField();
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnSections_Rec;
	Statement stmtSections_Rec;
	ResultSet rsSections_Rec;

	boolean ADDING_STATE;

	public frmAddEditSections(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm,true);
		cnSections_Rec = conn;
		
		ADDING_STATE = ADD_STATE;
		try
		{
			stmtSections_Rec = cnSections_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		cbYear = mdlFunctions.fillCombo("SELECT * FROM tblYear",cnSections_Rec,"Year");
		cbCourse = mdlFunctions.fillCombo("SELECT * FROM qryCourseList",cnSections_Rec,"Course");

		if(ADD_STATE==true){bttnUpdate.setText("Add");}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsSections_Rec = stmtSections_Rec.executeQuery(sSQL);
				rsSections_Rec.next();
				bttnReset.setEnabled(false);
				txtSections.setEnabled(false);
				cbYear.setSelectedItem("" + rsSections_Rec.getString("Year"));
				cbCourse.setSelectedItem("" + rsSections_Rec.getString("Course"));
				txtSections.setText("" + rsSections_Rec.getString("Section"));
				txtAdviser.setText("" + rsSections_Rec.getString("Adviser"));
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}		

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/sections list.gif"));

		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblYear,5,50,105,20);
		module_func.setJLabel(lblCourse,5,72,105,20);
		module_func.setJLabel(lblSections,5,94,105,20);
		module_func.setJLabel(lblAdviser,5,116,105,20);

		module_func.setJComboBox(cbYear,110,50,200,20);
		module_func.setJComboBox(cbCourse,110,72,200,20);
		
		module_func.setJTextField(txtSections,110,94,200,20);
		module_func.setJTextField(txtAdviser,110,116,200,20);
		
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblYear);
		jpnlMain.add(lblCourse);
		jpnlMain.add(lblSections);
		jpnlMain.add(lblAdviser);

		jpnlMain.add(cbYear);
		jpnlMain.add(cbCourse);
		jpnlMain.add(txtSections);
		jpnlMain.add(txtAdviser);

		//-- Add the Update Button
		module_func.setJButton(bttnUpdate,5,160,105,25,"update","UPDATE");
		bttnUpdate.setMnemonic(KeyEvent.VK_A);
		bttnUpdate.addActionListener(JBActionListener);

		//-- Add the Reset Button
		module_func.setJButton(bttnReset,112,160,99,25,"reset","RESET");
		bttnReset.setMnemonic(KeyEvent.VK_R);
		bttnReset.addActionListener(JBActionListener);

		//-- Add the Cancel Button
		module_func.setJButton(bttnCancel,212,160,99,25,"cancel","CANCEL");
		bttnCancel.setMnemonic(KeyEvent.VK_C);
		bttnCancel.addActionListener(JBActionListener);

		jpnlMain.add(bttnUpdate);
		jpnlMain.add(bttnReset);
		jpnlMain.add(bttnCancel);

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(325,220);
		setResizable(false);
		setLocation((screen.width - 325)/2,((screen.height-220)/2));
	}
	
	private void resetField()
	{
		cbYear.setSelectedIndex(0);
		cbCourse.setSelectedIndex(0);
		txtSections.setText("");
		txtAdviser.setText("");
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtAdviser, lblAdviser) == false){}
		if(module_func.isTextEmpty(txtSections, lblSections) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtSections_Rec.executeUpdate("INSERT INTO tblCourse (Year,Course,Section,Adviser)"+
	   							   	        "VALUES ('" 												+ 
	   							   	        cbYear.getSelectedItem().toString()  						+ "', '" +  
	   							   	        cbCourse.getSelectedItem().toString()  						+ "', '" +  
	   							   	        txtSections.getText() 										+ "', '" +  
	   							   	        txtAdviser.getText() 										+ "')");
	   					frmCourse.reloadRecord("SELECT * FROM qrySections ORDER BY Year ASC, Course ASC, Section ASC, Adviser ASC");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0)
						{
							resetField();
							cbYear.requestFocus(true);
						}
						else{dispose();}
	   			}
   				catch(SQLException sqlEx)
   				{
   					JOptionPane.showMessageDialog(null,"Course is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
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