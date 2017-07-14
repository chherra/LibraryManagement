

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

public class frmFines extends JInternalFrame implements ActionListener 
{
	public static JPanel 		jpnlMain  	= new JPanel();
	
	Connection cnFines;
	
	public static Statement stmtFines;
	public static ResultSet rsFines;	//Recordset
	
	public static String sSQL;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	
	//JButton Variables
	JButton bttnUpdate  = new JButton("Update",new ImageIcon("@imgs/add new.gif"));

	//JLabel Variables
	JLabel  lblHeader	= new JLabel();
	JLabel  lblIcon		= new JLabel();
	JLabel  lblCaption	= new JLabel("FINES");
	JLabel  lblFines	= new JLabel("Fines:");

	JTextField txtFines = new JTextField();
	
	JFrame JFParentFrame;
		
	mdlFunctions module_func = new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();

	public frmFines(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Fines",false,true,false,true);
		
		cnFines = conn;
		sSQL = "SELECT * FROM tblFines";

		try
		{
			stmtFines = cnFines.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException sqlEx){}

		try
		{
			rsFines = stmtFines.executeQuery(sSQL);
			rsFines.next();
			txtFines.setText("" + rsFines.getInt("Fines"));
		}
		catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}

		module_func.setJButton(bttnUpdate,125,60,100,25,"update","Update");
		bttnUpdate.setMnemonic(KeyEvent.VK_A);		
		bttnUpdate.addActionListener(JBActionListener);

		lblHeader.setIcon(new ImageIcon("@imgs/Barrowers Records.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/fines list.gif"));

		module_func.setJTextField(txtFines,60,60,60,25);

		module_func.setJLabel(lblHeader,0,0,750,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		module_func.setJLabel(lblFines,5,60,100,20);
		
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);
		
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblFines);
		
		//Add TextField
		jpnlMain.add(txtFines);

		//Add Buttons
		jpnlMain.add(bttnUpdate);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setFrameIcon(new ImageIcon("@imgs/fines.gif"));
		setSize(240,140);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 240)/2,((screen.height-140)/2)-14);
	}
		
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj=="update")
			{
				try
				{	   	        
					stmtFines.executeUpdate("UPDATE tblFines SET Fines 	= '" + txtFines.getText() + " '");
	   				JOptionPane.showMessageDialog(null,"Changes in the record has been successfully save.","Library System ver. 1",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch(SQLException sqlEx){System.out.println(sqlEx.getMessage());}
			} 
		}
	};

		
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
