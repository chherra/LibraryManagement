

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

public class frmBooks extends JInternalFrame implements ActionListener 
{
	public static JScrollPane 	BooksTblJSP = new JScrollPane();
	public static JPanel 		jpnlMain  	= new JPanel();
	public static JTable 		JTBooksTbl;
	
	Connection cnBooks;
	
	public static Statement stmtBooks;
	public static ResultSet rsBooks;	//Recordset
	
	public static String sSQL;
	public static String Content[][];
	
	public static int rowNum = 0;
	public static int total = 0;
	
	boolean goEOF;
	
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	//JButton Variables
		//JLabel Variables
	
	JFrame JFParentFrame;
		
	mdlFunctions module_func = new mdlFunctions();
	mdlSQLStatements module_sql 	= new mdlSQLStatements();

	public frmBooks(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("Books Records",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		JFParentFrame = getParentFrame;
		
		cnBooks = conn;
		stmtBooks = cnBooks.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		sSQL = "SELECT * FROM qryBooks ORDER BY BookNo ASC";

		//-- Add the Table
		JTBooksTbl = CreateTable();
		BooksTblJSP.getViewport().add(JTBooksTbl);
		BooksTblJSP.setBounds(5,55,708,323);
		jpnlMain.add(BooksTblJSP);

		

		
		//Add Buttons
		
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setFrameIcon(new ImageIcon("@imgs/Books.gif"));
		setSize(728,450);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 728)/2,((screen.height-450)/2)-45);
	}
		
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Book Number","ISBN","Title","Author", "Category"
		};
		try{
			rsBooks = stmtBooks.executeQuery(sSQL);
			total = 0;
			//Move to the last record
			rsBooks.afterLast(); 
			//Get the current record position
			if(rsBooks.previous())total = rsBooks.getRow();
			//Move back to the first record; 
			rsBooks.beforeFirst(); 
			if(total != 0){
				Content = new String[total][5];				
				while(rsBooks.next())
				{
					Content[rowNum][0] = "" + rsBooks.getString("BookNo");
					Content[rowNum][1] = "" + rsBooks.getString("ISBN");
					Content[rowNum][2] = "" + rsBooks.getString("Title");
					Content[rowNum][3] = "" + rsBooks.getString("Author");
					Content[rowNum][4] = "" + rsBooks.getString("Category");
					rowNum++;
				}
			}else{
				Content = new String[0][5];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
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
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(100);
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
		BooksTblJSP.getViewport().remove(JTBooksTbl);
		JTBooksTbl=CreateTable();
		BooksTblJSP.getViewport().add(JTBooksTbl);
		jpnlMain.repaint();
	}
		
	public void actionPerformed(ActionEvent event) 
	{
		setVisible(false);
		dispose();
	}

}
