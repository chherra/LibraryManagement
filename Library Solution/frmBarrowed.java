

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

public class frmBarrowed extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	BrrwdTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTBrrwdTbl;
	
	Connection cnBarrowed;
	
	public static Statement stmtBarrowed;
	public static ResultSet rsBarrowed;	//Recordset
	
	public static String sSQL;
	public static String Content[][];
	
	public static int rowNum = 0;
	public static int total = 0;
	
	boolean goEOF;
	
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	JButton bttnAddNew  = new JButton("Add New",new ImageIcon("@imgs/add new.gif"));
	JButton bttnEdit	= new JButton("Edit",   new ImageIcon("@imgs/edit.gif"));
	JButton bttnRemove	= new JButton("Remove", new ImageIcon("@imgs/remove.gif"));
	JButton bttnSearch 	= new JButton("Search", new ImageIcon("@imgs/search.gif"));
	JButton bttnReturn	= new JButton("Return",	new ImageIcon("@imgs/barrowed.gif"));
	JButton bttnRefresh	= new JButton("Refresh",new ImageIcon("@imgs/refresh.gif"));
	JButton bttnExit   	= new JButton("Cancel", new ImageIcon("@imgs/cancel.gif"));

	//JLabel Variables
	JLabel  lblHeader	= new JLabel();
	JLabel  lblIcon		= new JLabel();
	JLabel  lblCaption	= new JLabel("NOTE: This form contains all information about the Books Barrowed.");

	JFrame JFParentFrame;
		
	mdlFunctions module_func = new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();

	public frmBarrowed(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Barrowed Books",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		JFParentFrame = getParentFrame;

		cnBarrowed = conn;
		stmtBarrowed = cnBarrowed.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM qryBarrowedBooks ORDER BY Category ASC";

		//-- Add the CusTable
		JTBrrwdTbl = CreateTable();
		BrrwdTblJSP.getViewport().add(JTBrrwdTbl);
		BrrwdTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(BrrwdTblJSP);

		module_func.setJButton(bttnAddNew,5,390,105,25,"add","Add New");
		bttnAddNew.setMnemonic(KeyEvent.VK_A);		
		bttnAddNew.addActionListener(JBActionListener);

		module_func.setJButton(bttnEdit,112,390,99,25,"edit","Edit");
		bttnEdit.setMnemonic(KeyEvent.VK_E);		
		bttnEdit.addActionListener(JBActionListener);

		module_func.setJButton(bttnReturn,212,390,99,25,"return","Return");
		bttnReturn.setMnemonic(KeyEvent.VK_T);		
		bttnReturn.addActionListener(JBActionListener);

		module_func.setJButton(bttnSearch,313,390,99,25,"search","Search");
		bttnSearch.setMnemonic(KeyEvent.VK_S);		
		bttnSearch.addActionListener(JBActionListener);

		module_func.setJButton(bttnRemove,414,390,100,25,"remove","Remove");
		bttnRemove.setMnemonic(KeyEvent.VK_R);		
		bttnRemove.addActionListener(JBActionListener);

		module_func.setJButton(bttnRefresh,514,390,99,25,"refresh","Refresh");
		bttnRefresh.setMnemonic(KeyEvent.VK_R);		
		bttnRefresh.addActionListener(JBActionListener);

		module_func.setJButton(bttnExit,614,390,99,25,"exit","Unload Form");
		bttnExit.setMnemonic(KeyEvent.VK_C);
		bttnExit.addActionListener(JBActionListener);

		lblHeader.setIcon(new ImageIcon("@imgs/Barrowers Records.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/returners.gif"));

		module_func.setJLabel(lblHeader,0,0,750,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblCaption,60,2,500,40);
		lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCaption.setForeground(new Color(255,255,255));
				
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);

		//Add Buttons
		jpnlMain.add(bttnAddNew);
		jpnlMain.add(bttnEdit);
		jpnlMain.add(bttnRemove);
		jpnlMain.add(bttnSearch);
		jpnlMain.add(bttnReturn);
		jpnlMain.add(bttnRefresh);
		jpnlMain.add(bttnExit);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setFrameIcon(new ImageIcon("@imgs/barrowed.gif"));
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
				JDialog JDAdd = new frmAddEditBarrowed(true,JFParentFrame,cnBarrowed,"");
				JDAdd.show();
			} 
			else if(srcObj=="edit")
			{
				if(total != 0)
				{
					try
					{
						if(JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),JTBrrwdTbl.getSelectedColumn()) != null)
						{
							JDialog JDEdit = new frmAddEditBarrowed(false,JFParentFrame,cnBarrowed,"SELECT * FROM qryBarrowedBooks WHERE BookNo ='" + JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),0) + "'");
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
			else if(srcObj=="remove")
			{
				if(total != 0){
					try
					{
						if(JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),JTBrrwdTbl.getSelectedColumn()) != null)
						{
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to removed " + JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),1) + " in the record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0)
							{	
								module_sql.recREMOVE(true, stmtBarrowed,"tblBarrowedBooks", "BookNo", JTBrrwdTbl,0);
								reloadRecord(sSQL);
								JOptionPane.showMessageDialog(null,"Record has been successfully removed.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					catch(Exception sqlE)
					{
						if(sqlE.getMessage()!=null)
						{
							JOptionPane.showMessageDialog(null,"You cannot delete this barrowed book because it is being used by another user.\nIn order to delete this barrowed book, delete its data from another table.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
			else if(srcObj=="search")
			{
				JDialog JDSearch = new frmSearch(JFParentFrame, "Barrowed");
				JDSearch.show();			
			}
			else if(srcObj=="return")
			{
				if(total != 0)
				{
					try
					{
						if(JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),JTBrrwdTbl.getSelectedColumn()) != null)
						{
							JDialog JDReturn = new frmReturn(JFParentFrame,cnBarrowed,"SELECT * FROM qryBarrowedBooks WHERE BookNo ='" + JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),0) +
														 "' AND BarrowersID ='" + JTBrrwdTbl.getValueAt(JTBrrwdTbl.getSelectedRow(),3) + "'");
							JDReturn.show();
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
			else if(srcObj == "refresh"){reloadRecord("SELECT * FROM qryBarrowedBooks ORDER BY Category ASC");}
			else if(srcObj=="exit"){dispose();}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Book Number","Title","Category", "Barrowers ID", "Barrowers Name","Date Barrowerd","Date Due"
		};
		
		try
		{
			rsBarrowed = stmtBarrowed.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsBarrowed.afterLast(); 
			//Get the current record position
			if(rsBarrowed.previous())total = rsBarrowed.getRow();
			//Move back to the first record; 
			rsBarrowed.beforeFirst(); 
			if(total != 0)
			{
				Content = new String[total][7];				
				while(rsBarrowed.next())
				{				
					Content[rowNum][0] = "" + rsBarrowed.getString("BookNo");
					Content[rowNum][1] = "" + rsBarrowed.getString("Title");
					Content[rowNum][2] = "" + rsBarrowed.getString("Category");
					Content[rowNum][3] = "" + rsBarrowed.getString("BarrowersID");
					Content[rowNum][4] = "" + rsBarrowed.getString("BarrowersName");
					Content[rowNum][5] = "" + rsBarrowed.getDate("DateBarrowerd");
					Content[rowNum][6] = "" + rsBarrowed.getDate("DateDue");
					rowNum++;
				}
			}
			else
			{
				Content = new String[0][7];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
				Content[0][5] = " ";
				Content[0][6] = " ";
			}
		}catch(Exception eE){			
		}
		JTable NewTable = new JTable (Content,ColumnHeaderName){
			public boolean isCellEditable (int iRows, int iCols) {
				return false;
			}
		};
		
		NewTable.setPreferredScrollableViewportSize(new Dimension(708, 323));
		NewTable.setBackground(Color.white);
		
		//Start resize the table column
		NewTable.getColumnModel().getColumn(0).setMinWidth(0);
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(0);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(6).setPreferredWidth(200);
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
		BrrwdTblJSP.getViewport().remove(JTBrrwdTbl);
		JTBrrwdTbl=CreateTable();
		BrrwdTblJSP.getViewport().add(JTBrrwdTbl);
		jpnlMain.repaint();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
