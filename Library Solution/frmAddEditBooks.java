

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class frmAddEditBooks extends JDialog
{
	Dimension screen 		= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jpnlMain 		= new JPanel();

	JButton bttnUpdate 		= new JButton("Update",  new ImageIcon("@imgs/save.gif"));
	JButton bttnReset 		= new JButton("Reset",	new ImageIcon("@imgs/reset.gif"));
	JButton bttnCancel 		= new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));

	JLabel lblHeader	 	= new JLabel();
	JLabel lblIcon		 	= new JLabel();
	JLabel lblCaption		= new JLabel("IMPORTANT: All Fields are required.");
	JLabel lblBookNo	 	= new JLabel("BookNo:");
	JLabel lblISBN			= new JLabel("ISBN:");
	JLabel lblTitle 		= new JLabel("Title:");
	JLabel lblAuthor		= new JLabel("Author:");
	JLabel lblCatNum 		= new JLabel("Category No:");
	JLabel lblPublished		= new JLabel("YearPublished:");
	JLabel lblPrice			= new JLabel("Price:");
	JLabel lblQuantity		= new JLabel("Quantity:");
	JLabel lblBarrowed		= new JLabel("Barrowed:");
	JLabel lblRemaining		= new JLabel("Remaining:");

	JTextField txtBookNo	= new JTextField();
	JTextField txtISBN	 	= new JTextField();
	JTextField txtTitle 	= new JTextField();
	JTextField txtAuthor	= new JTextField();
	JComboBox  cbCatNum;
	JTextField txtPublished = new JTextField();
	JTextField txtPrice 	= new JTextField();
	JTextField txtQuantity	= new JTextField();
	JTextField txtBarrowed 	= new JTextField();
	JTextField txtRemaining	= new JTextField();

	mdlFunctions module_func= new mdlFunctions();
	
	Connection cnBooks_Rec;
	Statement stmtBooks_Rec;
	ResultSet rsBooks_Rec;
	
	boolean ADDING_STATE;

	public frmAddEditBooks(boolean ADD_STATE,JFrame OwnerForm, Connection conn, String sSQL)
	{
		super(OwnerForm, true);
		cnBooks_Rec = conn;

		ADDING_STATE = ADD_STATE;
		try
		{
			stmtBooks_Rec = cnBooks_Rec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		cbCatNum = mdlFunctions.fillCombo("SELECT CategoryID FROM tblCategory",cnBooks_Rec,"CategoryID");

		if(ADD_STATE==true)
		{
			bttnUpdate.setText("Add");
			txtRemaining.setVisible(false);
			lblRemaining.setVisible(false);
			txtBarrowed.setVisible(false);
			lblBarrowed.setVisible(false);			
		}
		else
		{
			bttnUpdate.setText("Save");
			try
			{
				rsBooks_Rec = stmtBooks_Rec.executeQuery(sSQL);
				rsBooks_Rec.next();
				bttnReset.setEnabled(false);
				txtBookNo.setEnabled(false);
				txtBookNo.setText("" + rsBooks_Rec.getString("BookNo"));
				txtISBN.setText("" + rsBooks_Rec.getString("ISBN"));
				txtTitle.setText("" + rsBooks_Rec.getString("Title"));
				txtAuthor.setText("" + rsBooks_Rec.getString("Author"));
				cbCatNum.setSelectedItem("" + rsBooks_Rec.getString("CategoryID"));
				txtPublished.setText("" + rsBooks_Rec.getString("YearPublished"));
				txtPrice.setText("" + rsBooks_Rec.getString("Price"));
				txtQuantity.setText("" + rsBooks_Rec.getString("Quantity"));
				txtBarrowed.setText("" + rsBooks_Rec.getString("Barrowed"));
				txtRemaining.setText("" + rsBooks_Rec.getString("Remaining"));
			
			}
			catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
		}
				
		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/bookslist.gif"));

		module_func.setJComboBox(cbCatNum,110,138,200,20);
		
		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));

		module_func.setJLabel(lblBookNo,5,50,105,20);
		module_func.setJLabel(lblISBN,5,72,105,20);
		module_func.setJLabel(lblTitle,5,94,105,20);
		module_func.setJLabel(lblAuthor,5,116,105,20);
		module_func.setJLabel(lblCatNum,5,138,105,20);
		module_func.setJLabel(lblPublished,5,160,105,20);
		module_func.setJLabel(lblPrice,5,182,105,20);
		module_func.setJLabel(lblQuantity,5,204,105,20);
		module_func.setJLabel(lblBarrowed,5,226,105,20);
		module_func.setJLabel(lblRemaining,5,248,105,20);

		module_func.setJTextField(txtBookNo,110,50,200,20);
		module_func.setJTextField(txtISBN,110,72,200,20);
		module_func.setJTextField(txtTitle,110,94,200,20);
		module_func.setJTextField(txtAuthor,110,116,200,20);
		module_func.setJTextField(txtPublished,110,160,200,20);
		module_func.setJTextField(txtPrice,110,182,200,20);
		module_func.setJTextField(txtQuantity,110,204,200,20);
		module_func.setJTextField(txtBarrowed,110,226,200,20);
		module_func.setJTextField(txtRemaining,110,248,200,20);

		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblBookNo);
		jpnlMain.add(lblISBN);
		jpnlMain.add(lblTitle);
		jpnlMain.add(lblAuthor);
		jpnlMain.add(lblCatNum);
		jpnlMain.add(lblPublished);
		jpnlMain.add(lblPrice);
		jpnlMain.add(lblQuantity);
		jpnlMain.add(lblBarrowed);
		jpnlMain.add(lblRemaining);

		jpnlMain.add(txtBookNo);
		jpnlMain.add(txtISBN);
		jpnlMain.add(txtTitle);
		jpnlMain.add(txtAuthor);
		jpnlMain.add(cbCatNum);
		jpnlMain.add(txtPublished);
		jpnlMain.add(txtPrice);
		jpnlMain.add(txtQuantity);
		jpnlMain.add(txtBarrowed);
		jpnlMain.add(txtRemaining);

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
		txtBookNo.setText("");
		txtISBN.setText("");
		txtTitle.setText("");
		txtAuthor.setText("");
		cbCatNum.setSelectedIndex(0);
		txtPublished.setText("");
		txtPrice.setText("");
		txtQuantity.setText("");
		txtBarrowed.setText("");
		txtRemaining.setText("");
	}
	
	private void updateField()
	{
		if(module_func.isTextEmpty(txtQuantity, lblQuantity) == false){}
		if(module_func.isTextEmpty(txtPrice, lblPrice) == false){}
		if(module_func.isTextEmpty(txtPublished, lblPublished) == false){}
		if(module_func.isTextEmpty(txtAuthor, lblAuthor) == false){}
		if(module_func.isTextEmpty(txtTitle, lblTitle) == false){}
		if(module_func.isTextEmpty(txtISBN, lblISBN) == false){}
		if(module_func.isTextEmpty(txtBookNo, lblBookNo) == false)
		{
			if(ADDING_STATE == true)
			{
				try
				{
					stmtBooks_Rec.executeUpdate("INSERT INTO tblBooks (BookNo,ISBN,Title,Author,CategoryID,YearPublished,Price,Quantity,Barrowed,Remaining) " +
	   							   	        "VALUES ('" 			+ 
	   							   	        txtBookNo.getText() 	+ "', '" +  
	   							   	        txtISBN.getText() 		+ "', '" +  
	   							   	        txtTitle.getText() 		+ "', '" +  
	   							   	        txtAuthor.getText() 	+ "', '" +  
	   							   	        cbCatNum.getSelectedItem().toString() 	+ "', '" + 
	   							   	        txtPublished.getText() 	+ "', '" + 
	   							   	        txtPrice.getText() 		+ "', '" + 
	   							   	        txtQuantity.getText() 	+ "', '" + 
	   							   	        txtBarrowed.getText() 	+ "', '" + 
	   							   	        txtRemaining.getText() 	+ "')");
	   					frmBooks.reloadRecord("SELECT * FROM qryBooks ORDER BY BookNo ASC");
	   					JOptionPane.showMessageDialog(null,"New record has been successfully added.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
	   					String ObjButtons[] = {"Yes","No"};
						int PromptResult = JOptionPane.showOptionDialog(null,"Do you want add another record?","Library System ver. 1",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[0]);
						if(PromptResult==0)
						{
							resetField();
							txtBookNo.requestFocus(true);
						}
						else{dispose();}
	   				}
	   				catch(SQLException sqlEx)
	   				{
	   					JOptionPane.showMessageDialog(null,"Book ID is already exist. Pls. enter another one.","Library System ver. 1",JOptionPane.ERROR_MESSAGE);
	   					System.out.println(sqlEx.getMessage());
	   				}
				}
			else
			{
				try
				{	   	        
					stmtBooks_Rec.executeUpdate("UPDATE tblBooks SET ISBN 				= 	'" +
							   	        txtISBN.getText() 		+ "', 	 Title 			= 	'" + 
							   	        txtTitle.getText() 		+ "', 	 Author 		= 	'" +
							   	        txtAuthor.getText() 	+ "', 	 CategoryID 	= 	'" +
							   	        cbCatNum.getSelectedItem().toString() 	+ "', 	 YearPublished 	= 	'" +
							   	        txtPublished.getText() 	+ "', 	 Price 			= 	'" +
							   	        txtPrice.getText() 		+ "', 	 Quantity		= 	'" +
							   	        txtQuantity.getText() 	+ "', 	 Barrowed 		= 	'" +
							   	        txtBarrowed.getText() 	+ "', 	 Remaining		= 	'" +
							   	        txtRemaining.getText() 	+
							   	        "' WHERE BookNo = '" + txtBookNo.getText() + " '");
   					frmBooks.reloadRecord("SELECT * FROM qryBooks ORDER BY BookNo ASC");
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