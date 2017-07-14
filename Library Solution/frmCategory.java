

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

public class frmCategory extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	CategoryTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTCategoryTbl;
	
	Connection cnCategory;
	
	public static Statement stmtCategory;
	public static ResultSet rsCategory;	//Recordset
	
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
	JLabel  lblCaption	= new JLabel("NOTE: This form contains all information about the Category.");

	JFrame JFParentFrame;
		
	mdlFunctions module_func = new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();

	public frmCategory(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Category Records",false,true,false,true);
		
		cnCategory = conn;
		stmtCategory = cnCategory.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM tblCategory ORDER BY Category ASC";

		//-- Add the CusTable
		JTCategoryTbl = CreateTable();
		CategoryTblJSP.getViewport().add(JTCategoryTbl);
		CategoryTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(CategoryTblJSP);

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
		lblIcon.setIcon(new ImageIcon("@imgs/category list.gif"));

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

		setFrameIcon(new ImageIcon("@imgs/settings.gif"));
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
				JDialog JDAdd = new frmAddEditCategory(true,JFParentFrame,cnCategory,"");
				JDAdd.show();
			} 
			else if(srcObj=="edit")
			{
				if(total != 0)
				{
					try
					{
						if(JTCategoryTbl.getValueAt(JTCategoryTbl.getSelectedRow(),JTCategoryTbl.getSelectedColumn()) != null)
						{
							JDialog JDEdit = new frmAddEditCategory(false,JFParentFrame,cnCategory,"SELECT * FROM tblCategory WHERE CategoryID ='" + JTCategoryTbl.getValueAt(JTCategoryTbl.getSelectedRow(),0) + "'");
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
				JDialog JDSearch = new frmSearch(JFParentFrame, "Category");
				JDSearch.show();			
			}
			else if(srcObj=="remove")
			{
				if(total != 0){
					try
					{
						if(JTCategoryTbl.getValueAt(JTCategoryTbl.getSelectedRow(),JTCategoryTbl.getSelectedColumn()) != null)
						{
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to removed " + JTCategoryTbl.getValueAt(JTCategoryTbl.getSelectedRow(),1) + " in the record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0)
							{	
								module_sql.recREMOVE(true, stmtCategory,"tblCategory", "CategoryID", JTCategoryTbl,0);
								reloadRecord(sSQL);
								JOptionPane.showMessageDialog(null,"Record has been successfully removed.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					catch(Exception sqlE)
					{
						if(sqlE.getMessage()!=null)
						{
							JOptionPane.showMessageDialog(null,"You cannot delete this barrower because it is being used by another user.\nIn order to delete this barrower, delete its data from another table.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
			else if(srcObj == "refresh"){reloadRecord("SELECT * FROM tblCategory ORDER BY Category ASC");}
			else if(srcObj=="exit"){dispose();}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {"Category ID","Category","Description"};
		try
		{
			rsCategory = stmtCategory.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsCategory.afterLast(); 
			//Get the current record position
			if(rsCategory.previous())total = rsCategory.getRow();
			//Move back to the first record; 
			rsCategory.beforeFirst(); 
			if(total != 0)
			{
				Content = new String[total][3];				
				while(rsCategory.next())
				{
					Content[rowNum][0] = "" + rsCategory.getString("CategoryID");
					Content[rowNum][1] = "" + rsCategory.getString("Category");
					Content[rowNum][2] = "" + rsCategory.getString("Description");
					rowNum++;
				}
			}
			else
			{
				Content = new String[0][3];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
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
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(250);
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
		CategoryTblJSP.getViewport().remove(JTCategoryTbl);
		JTCategoryTbl=CreateTable();
		CategoryTblJSP.getViewport().add(JTCategoryTbl);
		jpnlMain.repaint();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
