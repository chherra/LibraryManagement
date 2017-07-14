

import java.beans.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.*;

public class frmSettings extends JInternalFrame implements ActionListener 
{
	Connection cnSettings;

	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel 	jpnlMain  	= new JPanel();
	JButton bttnFines   = new JButton("", new ImageIcon("@imgs/fines.gif"));
	JButton bttnCategory= new JButton("", new ImageIcon("@imgs/category.gif"));
	JButton bttnYear   	= new JButton("", new ImageIcon("@imgs/year.gif"));
	JButton bttnCourse 	= new JButton("", new ImageIcon("@imgs/section.gif"));
	JButton bttnCancel 	= new JButton("", new ImageIcon("@imgs/_cancel.gif"));

	JFrame JFParentFrame;
	
	frmCategory objCategory;
	
	mdlFunctions module_func = new mdlFunctions();

	public frmSettings(Connection conn, JFrame getParentFrame) throws SQLException
	{
		super("",false,true,false,true);
		
		cnSettings = conn;

		module_func.setJButton(bttnFines,5,5,52,50,"fines","FINES");
		module_func.setJButton(bttnCategory,5,56,52,50,"category","CATEGORY");
		module_func.setJButton(bttnYear,5,106,52,50,"year","YEAR RECORDS");		
		module_func.setJButton(bttnCourse,5,156,52,50,"course","LIST OF COURSE");
		module_func.setJButton(bttnCancel,5,206,52,50,"cancel","CANCEL");

		jpnlMain.add(bttnFines);
		jpnlMain.add(bttnCategory);
		jpnlMain.add(bttnYear);
		jpnlMain.add(bttnCourse);
		jpnlMain.add(bttnCancel);

		bttnFines.addActionListener(this);
		bttnCategory.addActionListener(this);
		bttnYear.addActionListener(this);
		bttnCourse.addActionListener(this);
		bttnCancel.addActionListener(this);

		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(70,300);
		setResizable(false);
		setFrameIcon(new ImageIcon("@imgs/content.gif"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 70)/2,((screen.height-250)/2)-25);
	}

	protected void loadCategory() throws SQLException
	{
		JDialog JDAdd = new frmCategory();
		JDAdd.show();
	} //load Category
	

	public void actionPerformed(ActionEvent event) 
	{
		Object object = event.getSource();
		if(object == bttnCategory)
		{
			try{loadCategory();}
			catch(SQLException sqle){}
		}
		else if(object == bttnCancel)
		{
			setVisible(false);
			dispose();//Unload Form
		}
	}	
}
