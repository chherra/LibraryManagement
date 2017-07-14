


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

public class frmDueBooks extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	DueTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTDueTbl;
	
	Connection cnDue;
	
	public static Statement stmtDue;
	public static ResultSet rsDue;	//Recordset
	
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
	JLabel  lblCaption	= new JLabel("NOTE: This form contains all information about the Due Books.");

	JFrame JFParentFrame;
		
	mdlFunctions module_func 		= new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();
	
	public frmDueBooks(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Due Books",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		JFParentFrame = getParentFrame;
		
		cnDue = conn;
		stmtDue = cnDue.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM qryDueBooks ORDER BY BookNo ASC";

		//-- Add the Table
		JTDueTbl = CreateTable();
		DueTblJSP.getViewport().add(JTDueTbl);
		DueTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(DueTblJSP);

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
		lblIcon.setIcon(new ImageIcon("@imgs/due books list.gif"));

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

		setFrameIcon(new ImageIcon("@imgs/due books.gif"));
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
				JDialog JDSearch = new frmSearch(JFParentFrame, "Due Books");
				JDSearch.show();			
			}
			else if(srcObj == "refresh"){reloadRecord("SELECT * FROM qryDueBooks ORDER BY BookNo ASC");}
			else if(srcObj == "exit"){dispose();}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Book Number", "Title", "Category", "Barrowers Names","Date Barrowerd", "Date Due"
		};
		try{
			rsDue = stmtDue.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsDue.afterLast(); 
			//Get the current record position
			if(rsDue.previous())total = rsDue.getRow();
			//Move back to the first record; 
			rsDue.beforeFirst(); 
			if(total != 0)
			{
				Content = new String[total][6];				
				while(rsDue.next())
				{
					Content[rowNum][0] = "" + rsDue.getString("BookNo");
					Content[rowNum][1] = "" + rsDue.getString("Title");
					Content[rowNum][2] = "" + rsDue.getString("Category");
					Content[rowNum][3] = "" + rsDue.getString("BarrowersName");
					Content[rowNum][4] = "" + rsDue.getDate("DateBarrowerd");
					Content[rowNum][5] = "" + rsDue.getDate("DateDue");
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
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(150);
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
		DueTblJSP.getViewport().remove(JTDueTbl);
		JTDueTbl=CreateTable();
		DueTblJSP.getViewport().add(JTDueTbl);
		jpnlMain.repaint();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
