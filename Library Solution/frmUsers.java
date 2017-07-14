

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

public class frmUsers extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	UsersTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTUsersTbl;
	
	Connection cnUsers;
	
	public static Statement stmtUsers;
	public static ResultSet rsUsers;	//Recordset
	
	public static String sSQL;
	public static String Content[][];
	
	public static int rowNum = 0;
	public static int total = 0;
	
	boolean goEOF;
	
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	
	//JButton Variables
	JButton bttnAddNew  = new JButton("Add New",new ImageIcon("@imgs/add new.gif"));
	JButton bttnEdit	= new JButton("Edit",   new ImageIcon("@imgs/edit.gif"));
	JButton bttnRemove	= new JButton("Remove", new ImageIcon("@imgs/remove.gif"));
	JButton bttnSearch 	= new JButton("Search", new ImageIcon("@imgs/search.gif"));
	JButton bttnPreview	= new JButton("Preview",new ImageIcon("@imgs/preview.gif"));
	JButton bttnRefresh	= new JButton("Refresh",new ImageIcon("@imgs/refresh.gif"));
	JButton bttnExit   	= new JButton("Cancel", new ImageIcon("@imgs/cancel.gif"));

	//JLabel Variables
	JLabel  lblHeader	= new JLabel();
	JLabel  lblIcon		= new JLabel();
	JLabel  lblCaption	= new JLabel("NOTE: This form contains all information about the Users Records.");

	JFrame JFParentFrame;
		
	mdlFunctions module_func = new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();

	public frmUsers(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Users",false,true,false,true);
		
		cnUsers = conn;
		stmtUsers = cnUsers.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM tblUsers ORDER BY UserName ASC";

		//-- Add the CusTable
		JTUsersTbl = CreateTable();
		UsersTblJSP.getViewport().add(JTUsersTbl);
		UsersTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(UsersTblJSP);

		module_func.setJButton(bttnAddNew,5,390,105,25,"add","Add New");
		bttnAddNew.setMnemonic(KeyEvent.VK_A);		
		bttnAddNew.addActionListener(JBActionListener);

		module_func.setJButton(bttnEdit,112,390,99,25,"edit","Edit");
		bttnEdit.setMnemonic(KeyEvent.VK_E);		
		bttnEdit.addActionListener(JBActionListener);

		module_func.setJButton(bttnRemove,212,390,100,25,"remove","Remove");
		bttnRemove.setMnemonic(KeyEvent.VK_R);		
		bttnRemove.addActionListener(JBActionListener);

		module_func.setJButton(bttnSearch,313,390,99,25,"search","Search");
		bttnSearch.setMnemonic(KeyEvent.VK_S);		
		bttnSearch.addActionListener(JBActionListener);

		module_func.setJButton(bttnPreview,414,390,99,25,"preview","Preview");
		bttnPreview.setMnemonic(KeyEvent.VK_P);		
		bttnPreview.addActionListener(JBActionListener);

		module_func.setJButton(bttnRefresh,514,390,99,25,"refresh","Refresh");
		bttnRefresh.setMnemonic(KeyEvent.VK_R);		
		bttnRefresh.addActionListener(JBActionListener);

		module_func.setJButton(bttnExit,614,390,99,25,"exit","Unload Form");
		bttnExit.setMnemonic(KeyEvent.VK_C);
		bttnExit.addActionListener(JBActionListener);

		lblHeader.setIcon(new ImageIcon("@imgs/Barrowers Records.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/users list.gif"));

		module_func.setJLabel(lblHeader,0,0,750,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);
		
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);

		//Add Buttons
		jpnlMain.add(bttnAddNew);
		jpnlMain.add(bttnEdit);
		jpnlMain.add(bttnRemove);
		jpnlMain.add(bttnSearch);
		jpnlMain.add(bttnPreview);
		jpnlMain.add(bttnRefresh);
		jpnlMain.add(bttnExit);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setFrameIcon(new ImageIcon("@imgs/users.gif"));
		setSize(728,450);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 728)/2,((screen.height-450)/2)-45);
	}
		
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj=="add")
			{
				JDialog JDAdd = new frmAddEditUser(true,JFParentFrame,cnUsers,"");
				JDAdd.show();
			} 
			else if(srcObj=="edit")
			{
				if(total != 0){
					try
					{
						if(JTUsersTbl.getValueAt(JTUsersTbl.getSelectedRow(),JTUsersTbl.getSelectedColumn()) != null)
						{
							JDialog JDEdit = new frmAddEditUser(false,JFParentFrame,cnUsers,"SELECT * FROM tblUsers WHERE UserName ='" + JTUsersTbl.getValueAt(JTUsersTbl.getSelectedRow(),0) + "'");
							JDEdit.show();
						}
					}
					catch(Exception sqlE)
					{
						if(sqlE.getMessage() != null){System.out.println(sqlE.getMessage());}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to modify.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
				}
			} 
			else if(srcObj=="search")
			{
				JDialog JDSearch = new frmSearch(JFParentFrame, "Users");
				JDSearch.show();			
			}
			else if(srcObj=="remove")
			{
				if(total != 0){
					try
					{
						if(JTUsersTbl.getValueAt(JTUsersTbl.getSelectedRow(),JTUsersTbl.getSelectedColumn()) != null)
						{
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to removed " + JTUsersTbl.getValueAt(JTUsersTbl.getSelectedRow(),1) + " in the record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0)
							{	
								module_sql.recREMOVE(true, stmtUsers,"tblUsers", "UserName", JTUsersTbl,0);
								reloadRecord(sSQL);
								JOptionPane.showMessageDialog(null,"Record has been successfully removed.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					catch(Exception sqlE)
					{
						if(sqlE.getMessage()!=null)
						{
							JOptionPane.showMessageDialog(null,"You cannot delete this Username because it is being used by another user.\nIn order to delete this Username, delete its data from another table.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
			else if(srcObj == "refresh"){reloadRecord("SELECT * FROM tblUsers ORDER BY UserName ASC");}
			else if(srcObj=="exit"){dispose();}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {"User ID","Password"};
		try
		{
			rsUsers = stmtUsers.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsUsers.afterLast(); 
			//Get the current record position
			if(rsUsers.previous())total = rsUsers.getRow();
			//Move back to the first record; 
			rsUsers.beforeFirst(); 
			if(total != 0)
			{
				Content = new String[total][2];				
				while(rsUsers.next())
				{
					Content[rowNum][0] = "" + rsUsers.getString("UserName");
					Content[rowNum][1] = "" + rsUsers.getString("Password");
					rowNum++;
				}
			}
			else
			{
				Content = new String[0][2];
				Content[0][0] = " ";
				Content[0][1] = " ";
			}
		}
		catch(Exception eE){}
		JTable NewTable = new JTable (Content,ColumnHeaderName)
		{
			public boolean isCellEditable (int iRows, int iCols){return false;}
		};
		
		NewTable.setPreferredScrollableViewportSize(new Dimension(708, 323));
		NewTable.setBackground(Color.white);
		
		//Start resize the table column
		NewTable.getColumnModel().getColumn(0).setMinWidth(0);
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		//End resize the table column
		
		//Disposed variables
		ColumnHeaderName=null;
		Content=null;
		
		rowNum = 0;
		
		return NewTable;
	}
	
	public static void reloadRecord(String srcSQL)
	{
		sSQL = srcSQL;
		UsersTblJSP.getViewport().remove(JTUsersTbl);
		JTUsersTbl=CreateTable();
		UsersTblJSP.getViewport().add(JTUsersTbl);
		jpnlMain.repaint();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
