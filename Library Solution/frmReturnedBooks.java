


import java.util.*;
import java.text.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.PrintJob;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.plaf.metal.*;

public class frmReturnedBooks extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	ReturnTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTReturnTbl;
	
	Connection cnReturn;
	
	public static Statement stmtReturn;
	public static ResultSet rsReturn;	//Recordset
	
	public static String sSQL;
	public static String Content[][];
	
	public static int rowNum = 0;
	public static int total = 0;
	
	boolean goEOF;
	
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	//JButton Variables
	JButton bttnSearch 	= new JButton("Search", new ImageIcon("@imgs/search.gif"));
	JButton bttnPreview	= new JButton("Preview",new ImageIcon("@imgs/preview.gif"));
	JButton bttnRefresh	= new JButton("Refresh",new ImageIcon("@imgs/refresh.gif"));
	JButton bttnExit   	= new JButton("Cancel", new ImageIcon("@imgs/cancel.gif"));

	//JLabel Variables
	JLabel  lblHeader	= new JLabel();
	JLabel  lblIcon		= new JLabel();
	JLabel  lblCaption	= new JLabel("NOTE: This form contains all information about the Returned Book.");

	JFrame JFParentFrame;
		
	mdlFunctions module_func 		= new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();
	
	public frmReturnedBooks(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Returned Books",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		JFParentFrame = getParentFrame;
		
		cnReturn = conn;
		stmtReturn = cnReturn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM qryReturnedBooks ORDER BY BookNo ASC";

		//-- Add the Table
		JTReturnTbl = CreateTable();
		ReturnTblJSP.getViewport().add(JTReturnTbl);
		ReturnTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(ReturnTblJSP);

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
		lblIcon.setIcon(new ImageIcon("@imgs/returnedbooks.gif"));

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
		jpnlMain.add(bttnSearch);
		jpnlMain.add(bttnPreview);
		jpnlMain.add(bttnRefresh);
		jpnlMain.add(bttnExit);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setFrameIcon(new ImageIcon("@imgs/return.gif"));
		setSize(728,450);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 728)/2,((screen.height-450)/2)-45);
	}


	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj=="search")
			{
				JDialog JDSearch = new frmSearch(JFParentFrame, "Returned");
				JDSearch.show();			
			}
			else if(srcObj == "refresh"){reloadRecord("SELECT * FROM qryReturnedBooks ORDER BY BookNo ASC");}
			else if(srcObj == "exit"){dispose();}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Book Number", "Title", "Barrowers Names","Date Returned","NO Days After Due Date ","Fines"
		};
		try{
			rsReturn = stmtReturn.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsReturn.afterLast(); 
			//Get the current record position
			if(rsReturn.previous())total = rsReturn.getRow();
			//Move back to the first record; 
			rsReturn.beforeFirst(); 
			if(total != 0)
			{
				Content = new String[total][8];				
				while(rsReturn.next())
				{
					Content[rowNum][0] = "" + rsReturn.getString("BookNo");
					Content[rowNum][1] = "" + rsReturn.getString("Title");
					Content[rowNum][2] = "" + rsReturn.getString("BarrowersName");
					Content[rowNum][3] = "" + rsReturn.getDate("DateReturned");
					Content[rowNum][4] = "" + rsReturn.getDouble("NO_OF_DAYS_AFTER_DUE_DATE");
					Content[rowNum][5] = "" + rsReturn.getDouble("Fines");
					rowNum++;
				}
			}
			else
			{
				Content = new String[0][6];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
				Content[0][5] = " ";
			}
		}
		catch(Exception eE){}
		JTable NewTable = new JTable (Content,ColumnHeaderName)
		{
			public boolean isCellEditable (int iRows, int iCols)
			{return false;}
		};
		
		NewTable.setPreferredScrollableViewportSize(new Dimension(708, 323));
		NewTable.setBackground(Color.white);
		
		//Start resize the table column
		NewTable.getColumnModel().getColumn(0).setMinWidth(0);
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(0);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(80);
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
		ReturnTblJSP.getViewport().remove(JTReturnTbl);
		JTReturnTbl=CreateTable();
		ReturnTblJSP.getViewport().add(JTReturnTbl);
		jpnlMain.repaint();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
