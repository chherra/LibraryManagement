
 
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditCategory extends JDialog
{
	public static JScrollPane DescJSP = new JScrollPane();

	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblCategoryID 	= new JLabel("Category ID:");
	JLabel lblCategory		= new JLabel("Category:");
	JLabel lblDescription	= new JLabel("Description:");

	JTextField txtCID 		= new JTextField();
	JTextField txtCategory 	= new JTextField();
	JTextArea  txtDesc		= new JTextArea();
	
	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnCategory_Rec;
	Statement stmtCategory_Rec;
	ResultSet rsCategory_Rec;

	boolean ADDING_STATE;

	public frmAddEditCategory(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm,true);
		cnCategory_Rec = conn;
		
		ADDING_STATE = ADD_STATE;
		try
		{
			stmtCategory_Rec = cnCategory_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		if(ADD_STATE==true){bttnUpdate.setText("Add");}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsCategory_Rec = stmtCategory_Rec.executeQuery(sSQL);
				rsCategory_Rec.next();
				bttnReset.setEnabled(false);
				txtCID.setEnabled(false);
				txtCID.setText("" + rsCategory_Rec.getString("CategoryID"));
				txtCategory.setText("" + rsCategory_Rec.getString("Category"));
				txtDesc.setText("" + rsCategory_Rec.getString("Description"));
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

		module_func.setJLabel(lblCategoryID,5,50,105,20);
		module_func.setJLabel(lblCategory,5,72,105,20);
		module_func.setJLabel(lblDescription,5,94,105,20);

		module_func.setJTextField(txtCID,110,50,200,20);
		module_func.setJTextField(txtCategory,110,72,200,20);
		
		//TextArea
		module_func.setJTextArea(txtDesc,0,0,200,150);
        
		DescJSP.getViewport().add(txtDesc);
		DescJSP.setBounds(110,94,200,150);
		jpnlMain.add(DescJSP);

		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblCategoryID);
		jpnlMain.add(lblCategory);
		jpnlMain.add(lblDescription);

		jpnlMain.add(txtCID);
		jpnlMain.add(txtCategory);

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
		txtCID.setText("");
		txtCategory.setText("");
		txtDesc.setText("");
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtCategory, lblCategory) == false){}
		if(module_func.isTextEmpty(txtCID, lblCategoryID) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtCategory_Rec.executeUpdate("INSERT INTO tblCategory (CategoryID,Category,Description) " +
	   							   	        "VALUES ('" 			+ 
	   							   	        txtCID.getText() 		+ "', '" +  
	   							   	        txtCategory.getText() 		+ "', '" +  
	   							   	        txtDesc.getText() 	+ "')");
	   					frmCategory.reloadRecord("SELECT * FROM tblCategory ORDER BY Category ASC");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0){
							resetField();
							txtCID.requestFocus(true);
						}
						else{dispose();}
	   				}
	   				catch(SQLException sqlEx)
	   				{
	   					JOptionPane.showMessageDialog(null,"Category ID is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
	   					System.out.println(sqlEx.getMessage());
	   				}
				}
			else
			{
				try
				{	   	        
					stmtCategory_Rec.executeUpdate("UPDATE tblCategory SET Category 	= 	'" +
							   	        txtCategory.getText() 		+ "',  Description 	= 	'" + 
							   	        txtDesc.getText() 	+
							   	        "' WHERE CategoryID = '" + txtCID.getText() + " '");
   					frmCategory.reloadRecord("SELECT * FROM tblCategory ORDER BY Category ASC");
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