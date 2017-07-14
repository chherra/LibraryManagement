

import java.beans.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.*;

public class MDI_FORM extends JFrame implements ActionListener,InternalFrameListener
{
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	JDesktopPane desktop = new JDesktopPane();
	JLabel StatusLabel = new JLabel("Copyright © 2013.All Rights Reserved. Visit http://www.mandelia.edu.in",JLabel.CENTER);
	
	JMenuBar menubar = new JMenuBar();
	JMenu mnuFile =  new JMenu("File");														//Set the Caption of JMenu
	JMenu mnuRecords =  new JMenu("Records");												//Set the Caption of JMenu
	JMenu mnuTools =  new JMenu("Tools");													//Set the Caption of JMenu
	JMenu mnuWindows =  new JMenu("Windows");												//Set the Caption of JMenu
	JMenu mnuHelp =  new JMenu("Help");														//Set the Caption of JMenu

	JMenuItem itmInfo = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmUsers = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmSwitch = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmExit = new JMenuItem();													//Set JMenuItem	

	JMenuItem itmBarrowers = new JMenuItem();												//Set JMenuItem	
	JMenuItem itmBarrowed = new JMenuItem();												//Set JMenuItem	
	JMenuItem itmBooks = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmDue = new JMenuItem();														//Set JMenuItem	
	JMenuItem itmReturned = new JMenuItem();												//Set JMenuItem	
	JMenuItem itmCategory = new JMenuItem();												//Set JMenuItem	
	JMenuItem itmFines = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmYear = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmCourse = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmSections = new JMenuItem();												//Set JMenuItem	

	JMenuItem itmCalc = new JMenuItem();													//Set JMenuItem	
	JMenuItem itmNotepad = new JMenuItem();													//Set JMenuItem				
	JMenuItem itmWordpad = new JMenuItem();													//Set JMenuItem			
	JMenuItem itmPaint = new JMenuItem();													//Set JMenuItem	
			
	JMenuItem itmNormal = new JMenuItem();													//Set JMenuItem			
	JMenuItem itmMinimized = new JMenuItem();												//Set JMenuItem
	JMenuItem itmMaximized = new JMenuItem();												//Set JMenuItem			
	JMenuItem itmCascade = new JMenuItem();													//Set JMenuItem
	JMenuItem itmHorizontal = new JMenuItem();												//Set JMenuItem
	JMenuItem itmVertical = new JMenuItem();												//Set JMenuItem
	JMenuItem itmArrange = new JMenuItem();	

	JMenuItem itmContent = new JMenuItem();													//Set JMenuItem
	JMenuItem itmKeys = new JMenuItem();													//Set JMenuItem
	JMenuItem itmAuthor = new JMenuItem();													//Set JMenuItem

	String DBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String DBSource = "jdbc:odbc:LibrarySystem";
	String DBUserName = "Admin";
	String DBPassword = "libsys";
	Connection conn;
	
	frmSplash FormSplash= new frmSplash();
	Thread ThFormSplash = new Thread(FormSplash);

	frmAuthor objAuthor;
	frmBarrowers objBarrowers;
	frmBarrowed objBarrowed;
	frmBooks objBooks;
	frmReturnedBooks objReturnedBooks;
	frmDueBooks objDueBooks;
	frmCategory objCategory;
	frmYear objYear;
	frmCourse objCourse;
	frmSections objSections;
	frmUsers objUsers;
	frmKeys objKeys;
	frmFines objFines;

	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true); 
		JDialog.setDefaultLookAndFeelDecorated(true);
    	MetalTheme myXPStyleTheme = new XPStyleTheme();
    	MetalLookAndFeel.setCurrentTheme(myXPStyleTheme);
    	try 
    	{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    	} catch (Exception err) 
    	{
        	System.out.println("Error loading myXPStyleTheme");
			System.out.println(err);
   		}
		new MDI_FORM();
	}
	
	public MDI_FORM()
	{
		super("Mandelia Institutions - Library Management System");
		StatusLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		loadSplashScreen();
		FormSplash.dispose(); //dispose

		desktop.setBackground(Color.GRAY);
		desktop.setBorder(BorderFactory.createLoweredBevelBorder());
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		getContentPane().add(CreateJToolBar(),BorderLayout.PAGE_START);
		getContentPane().add(desktop,BorderLayout.CENTER);
		getContentPane().add(StatusLabel,BorderLayout.PAGE_END);
		
		addWindowListener(new WindowAdapter(){
      	public void windowClosing(WindowEvent e){UnloadWindow();}});
      
		setJMenuBar(CreateJMenuBar());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(new ImageIcon("@imgs/MDI_ico.gif").getImage());	//Menu Icon
		setLocation(0,0);
		setSize(screen);
		setResizable(true);
		setVisible(true);
		show();	
		
		try
		{
			Class.forName(DBDriver);
			conn = DriverManager.getConnection(DBSource,DBUserName ,DBPassword);
		}
		catch(ClassNotFoundException e)  
		{
 			System.err.println("Failed to load driver");
 			e.printStackTrace();
 			System.exit(1);
 		}
 		catch(SQLException e)
 		{
 			System.err.println("Unable to connect");
 			e.printStackTrace();
 			System.exit(1);
 		}		
 		
		try{loadLogin();}
		catch(SQLException sqle){}
	}
	
	protected JMenuBar CreateJMenuBar()		//Create a Menubar
	{
		
		//START MENUBAR	******************************************************************************************************
		
		mnuFile.setMnemonic('F');
		mnuRecords.setMnemonic('R');
		mnuTools.setMnemonic('T');
		mnuWindows.setMnemonic('W');
		mnuHelp.setMnemonic('H');
		
		menubar.add(setJMenu(mnuFile));																//Add JMenuItem			
		menubar.add(setJMenu(mnuRecords));															//Add JMenuItem					
		menubar.add(setJMenu(mnuTools));															//Add JMenuItem			
		menubar.add(setJMenu(mnuWindows));															//Add JMenuItem			
		menubar.add(setJMenu(mnuHelp));																//Add JMenuItem			

		
		//END MENUBAR	*******************************************************************************************************
			
		//START SUBMENU	*******************************************************************************************************
			//Menu Files			
			itmInfo.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_I,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmUsers.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_U,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmSwitch.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_S,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmExit.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
			
			itmInfo.addActionListener(this);
			itmUsers.addActionListener(this);
			itmExit.addActionListener(this);

			mnuFile.add(setJMenuItem(itmInfo,"School Info.","@imgs/info.gif"));					//Add JMenuItem			
			mnuFile.add(setJMenuItem(itmUsers,"Users Records","@imgs/users.gif"));					//Add JMenuItem			
			mnuFile.addSeparator();																	//Create a Separator.
			mnuFile.add(setJMenuItem(itmExit,"Exit Application","@imgs/update.gif"));					//Add JMenuItem			
		
			//Menu Records
			itmBarrowers.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_B,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmBarrowed.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_A,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmBooks.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_O,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmDue.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_D,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmReturned.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_R,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmCategory.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_C,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmFines.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmYear.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmCourse.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_U,ActionEvent.CTRL_MASK));													//Set Key Shortcuts
			itmSections.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_S,ActionEvent.CTRL_MASK));													//Set Key Shortcuts

			mnuRecords.add(setJMenuItem(itmBarrowers,"Barrowers","@imgs/Barrowers.gif"));			//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmBarrowed,"Barrowed Books","@imgs/barrowed.gif"));		//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmBooks,"Books","@imgs/Books.gif"));						//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmDue,"Due Books","@imgs/due books.gif"));					//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmReturned,"Returned Books","@imgs/return.gif"));			//Add JMenuItem			
			mnuRecords.addSeparator();																//Create a Separator
			mnuRecords.add(setJMenuItem(itmCategory,"Category","@imgs/settings.gif"));				//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmFines,"Fines","@imgs/fines.gif"));						//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmYear,"Year","@imgs/year-icon.gif"));						//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmCourse,"Course","@imgs/course.gif"));					//Add JMenuItem			
			mnuRecords.add(setJMenuItem(itmSections,"Sections","@imgs/sections.gif"));				//Add JMenuItem			

			itmBarrowers.addActionListener(this);
			itmBarrowed.addActionListener(this);
			itmBooks.addActionListener(this);
			itmDue.addActionListener(this);
			itmReturned.addActionListener(this);
			itmCategory.addActionListener(this);			
			itmFines.addActionListener(this);			
			itmYear.addActionListener(this);
			itmCourse.addActionListener(this);
			itmSections.addActionListener(this);			

			//Tools
			itmCalc.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_F1,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmNotepad.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F2,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmWordpad.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F3,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmPaint.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F4,ActionEvent.ALT_MASK));													//Set Key Shortcuts

			mnuTools.add(setJMenuItem(itmCalc,"Calculator","@imgs/calc.gif"));						//Add JMenuItem			
			mnuTools.add(setJMenuItem(itmNotepad,"Notepad","@imgs/notepad.gif"));					//Add JMenuItem			
			mnuTools.add(setJMenuItem(itmWordpad,"Wordpad","@imgs/wordpad.gif"));					//Add JMenuItem			
			mnuTools.add(setJMenuItem(itmPaint,"MS Paint","@imgs/mspaint.gif"));					//Add JMenuItem			

			//Windows			
			itmNormal.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_F5,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmMinimized.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F6,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmMaximized.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F7,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmCascade.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_F8,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmHorizontal.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F9,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmVertical.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F10,ActionEvent.ALT_MASK));												//Set Key Shortcuts
			itmArrange.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_F11,ActionEvent.ALT_MASK));												//Set Key Shortcuts

			mnuWindows.add(setJMenuItem(itmNormal,"Normal","@imgs/content.gif"));					//Add JMenuItem			
			mnuWindows.add(setJMenuItem(itmMinimized,"Minimized","@imgs/content.gif"));				//Add JMenuItem			
			mnuWindows.add(setJMenuItem(itmMaximized,"Maximized","@imgs/content.gif"));				//Add JMenuItem			
			mnuWindows.addSeparator();																//Create a Separator
			mnuWindows.add(setJMenuItem(itmCascade,"Cascade","@imgs/content.gif"));					//Add JMenuItem			
			mnuWindows.add(setJMenuItem(itmHorizontal,"Tile Horizontally","@imgs/content.gif"));	//Add JMenuItem			
			mnuWindows.add(setJMenuItem(itmVertical,"Tile Vertically","@imgs/content.gif"));		//Add JMenuItem			
			mnuWindows.add(setJMenuItem(itmArrange,"Arrange Icon","@imgs/content.gif"));			//Add JMenuItem			
			
			itmNormal.addActionListener(this);
			itmMinimized.addActionListener(this);
			itmMaximized.addActionListener(this);

			//Help
			itmContent.setAccelerator(KeyStroke.getKeyStroke	
			(KeyEvent.VK_C,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmKeys.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_K,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			itmAuthor.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_A,ActionEvent.ALT_MASK));													//Set Key Shortcuts
			
			itmContent.addActionListener(this);
			itmKeys.addActionListener(this);
			itmAuthor.addActionListener(this);
			
			mnuHelp.add(setJMenuItem(itmContent,"Content...","@imgs/Help.gif"));					//Add JMenuItem			
			mnuHelp.add(setJMenuItem(itmKeys,"Key Shortcuts","@imgs/ktouch.gif"));					//Add JMenuItem
			mnuHelp.add(setJMenuItem(itmAuthor,"Author","@imgs/author.gif"));						//Add JMenuItem

		//END SUBMENU	***********************************************************************************************************
		
		menubar.setBackground(new Color(255,255,255));//Set the Background of JMenuBar. 
		return menubar;
	}

	protected JMenu setJMenu(JMenu menu)
	{
		menu.setFont(new Font("Dialog", Font.BOLD, 12)); 			//Set the Font of JMenu
		menu.setBackground(new Color(255,255,255));					//Set the Background of JMenu. 
		return menu;
	}	//Create a Menu
	
	protected JMenuItem setJMenuItem(JMenuItem mnuitem, String sCaption, String imgLocation)
	{
		// = new JMenuItem();			//Set the Caption of JMenuItem
		
		mnuitem.setText(sCaption);
		mnuitem.setIcon(new ImageIcon(imgLocation));
		mnuitem.setFont(new Font("Dialog", Font.PLAIN, 12));  
		mnuitem.setBackground(new Color(255,255,255));
		return 	mnuitem;							

	}
	
	protected JToolBar CreateJToolBar()		//Creat a Toolbar
	{
		JToolBar toolbar = new JToolBar("Toolbar");
		
		toolbar.setMargin(new Insets(0,0,0,0));
		
		toolbar.add(CreateJToolbarButton("Barrowers Records",
		"@imgs/Barrowers.gif","tlBarrowers"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Barrowed Records",
		"@imgs/barrowed.gif","tlBarrowed"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Books Records",
		"@imgs/Books.gif","tlBooks"));//Create Icon
		toolbar.add(CreateJToolbarButton("Due Books Records",
		"@imgs/due books.gif","tlDueBooks"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Returned Books",
		"@imgs/return.gif","tlReturned"));		//Create Icon
		toolbar.addSeparator();
		toolbar.add(CreateJToolbarButton("Category",
		"@imgs/settings.gif","tlCategory"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Fines",
		"@imgs/fines.gif","tlFines"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Year",
		"@imgs/year-icon.gif","tlYear"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Course",
		"@imgs/course.gif","tlCourse"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Sections",
		"@imgs/sections.gif","tlSections"));		//Create Icon
		toolbar.addSeparator();
		toolbar.add(CreateJToolbarButton("Content...",
		"@imgs/Help.gif","tlContents"));//Create Icon
		toolbar.add(CreateJToolbarButton("Key Shortcuts",
		"@imgs/ktouch.gif","tlKeys"));		//Create Icon
		toolbar.add(CreateJToolbarButton("Author",
		"@imgs/author.gif","tlAuthor"));		//Create Icon
		toolbar.setBackground(new Color(255,255,255));							//Set the Background of JMenuBar. 
		return toolbar;
		
	}
	
	protected JButton CreateJToolbarButton(String srcToolTipText,String srcImageLocation,String srcActionCommand)
	{
		JButton NewJButton = new JButton(new ImageIcon(srcImageLocation));
		
		NewJButton.setActionCommand(srcActionCommand);
		NewJButton.setToolTipText(srcToolTipText);
		NewJButton.setBackground(new Color(255,255,255));			//Set the Background of JMenuBar. 
		NewJButton.addActionListener(JToolBarActionListener);
		return NewJButton;
	}
		
	ActionListener JToolBarActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)	
		{
			String srcObject = e.getActionCommand();
			if(srcObject=="tlBarrowers")
			{
				try{loadBarrowers();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlBarrowed")
			{
				try{loadBarrowed();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlBooks")
			{
				try{loadBooks();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlReturned")
			{
				try{loadReturnedBook();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlDueBooks")
			{
				try{loadDueBooks();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlCategory")
			{
				try{loadCategory();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlYear")
			{
				try{loadYear();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlCourse")
			{
				try{loadCourse();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlSections")
			{
				try{loadSections();}
				catch(SQLException sqle){}
			}
			else if(srcObject=="tlFines")
			{
				try{loadFines();}
				catch(SQLException sqle){}
			}		
			else if(srcObject=="tlContents"){loadContent();}
			else if(srcObject=="tlKeys"){loadKeys();}
			else if(srcObject=="tlAuthor"){loadAuthor();}
		}
	};

	protected void loadContent()
	{
		try 
		{
	    	JOptionPane.showConfirmDialog(this,
			"\n\n" +
			"\n"+
			"\n\n"+
			"\n\n"+
			"\n"+
			"\n" +
			"\n"+
			"\n\n"+
			"\n"+ "",
			
			"Library System ",  	
          	JOptionPane.OK_OPTION,
          	JOptionPane.INFORMATION_MESSAGE);
		} catch(Exception e) {}
	}//Load Content

	protected void loadSplashScreen()
	{
		//Start the thread
		ThFormSplash.start();
		while(!FormSplash.isShowing())
		{
			try
			{
				//Display the FormSplash for 10 seconds
				Thread.sleep(10000);
			}
			catch(InterruptedException e){}
		}
	}//:Load Splash Screen
	
	protected void loadBarrowers() throws SQLException
	{
		boolean load_barrowers = isLoaded("Barrowers Records");
		if(load_barrowers == false)
		{
			objBarrowers = new frmBarrowers(conn,this); 
			desktop.add(objBarrowers);
			
			objBarrowers.setVisible(true);
			objBarrowers.show();
		}else
		{
			try{
				objBarrowers.setIcon(false);
				objBarrowers.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	} //load Barrowers Records
	
	protected void loadLogin() throws SQLException
	{
 		JDialog JDLogin = new frmLogin(this);
		JDLogin.show();
	}
	
	protected void loadBarrowed() throws SQLException
	{
		boolean load_barrowed = isLoaded("Barrowed Books");
		if(load_barrowed == false)
		{
			objBarrowed = new frmBarrowed(conn,this); 
			desktop.add(objBarrowed);
			
			objBarrowed.setVisible(true);
			objBarrowed.show();
		}else
		{
			try{
				objBarrowed.setIcon(false);
				objBarrowed.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadFines() throws SQLException
	{
		boolean load_fines = isLoaded("Fines");
		if(load_fines == false)
		{
			objFines = new frmFines(conn,this); 
			desktop.add(objFines);
			
			objFines.setVisible(true);
			objFines.show();
		}else
		{
			try{
				objFines.setIcon(false);
				objFines.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadReturnedBook() throws SQLException
	{
		boolean load_returnedbooks = isLoaded("Returned Books");
		if(load_returnedbooks == false)
		{
			objReturnedBooks = new frmReturnedBooks(conn,this); 
			desktop.add(objReturnedBooks);
			
			objReturnedBooks.setVisible(true);
			objReturnedBooks.show();
		}else
		{
			try{
				objReturnedBooks.setIcon(false);
				objReturnedBooks.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	} //load Returned Books

	protected void loadDueBooks() throws SQLException
	{
		boolean load_duebooks = isLoaded("Due Books");
		if(load_duebooks == false)
		{
			objDueBooks = new frmDueBooks(conn,this); 
			desktop.add(objDueBooks);
			
			objDueBooks.setVisible(true);
			objDueBooks.show();
		}else
		{
			try{
				objDueBooks.setIcon(false);
				objDueBooks.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}//load Due Books
	
	protected void loadYear() throws SQLException
	{
		boolean load_year = isLoaded("Year");
		if(load_year == false)
		{
			objYear = new frmYear(conn, this); 
			desktop.add(objYear);
			
			objYear.setVisible(true);
			objYear.show();
		}else
		{
			try{
				objYear.setIcon(false);
				objYear.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadCategory() throws SQLException
	{
		boolean load_category = isLoaded("Category Records");
		if(load_category == false)
		{
			objCategory = new frmCategory(conn, this); 
			desktop.add(objCategory);
			
			objCategory.setVisible(true);
			objCategory.show();
		}else
		{
			try{
				objCategory.setIcon(false);
				objCategory.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadCourse() throws SQLException
	{
		boolean load_course = isLoaded("Course");
		if(load_course == false)
		{
			objCourse = new frmCourse(conn, this); 
			desktop.add(objCourse);
			
			objCourse.setVisible(true);
			objCourse.show();
		}else
		{
			try{
				objCourse.setIcon(false);
				objCourse.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadSections() throws SQLException
	{
		boolean load_sections = isLoaded("Sections");
		if(load_sections == false)
		{
			objSections = new frmSections(conn, this); 
			desktop.add(objSections);
			
			objSections.setVisible(true);
			objSections.show();
		}else
		{
			try{
				objSections.setIcon(false);
				objSections.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}
	
	protected void loadBooks() throws SQLException
	{
		boolean load_books = isLoaded("Books Records");
		if(load_books == false)
		{
			objBooks = new frmBooks(conn, this); 
			desktop.add(objBooks);
			
			objBooks.setVisible(true);
			objBooks.show();
		}else
		{
			try{
				objBarrowers.setIcon(false);
				objBarrowers.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}//Load Books Records
	
	protected void loadAuthor()
	{
		boolean load_author = isLoaded("About the Author");
		if(load_author == false)
		{
			objAuthor = new frmAuthor(this); 
			desktop.add(objAuthor);
			
			objAuthor.setVisible(true);
			objAuthor.show();
		}else
		{
			try{
				objAuthor.setIcon(false);
				objAuthor.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}//Load the Author Form
	
	protected void loadKeys()
	{
		boolean load_keys = isLoaded("Key Shortcuts");
		if(load_keys == false)
		{
			objKeys = new frmKeys(this); 
			desktop.add(objKeys);
			
			objKeys.setVisible(true);
			objKeys.show();
		}else
		{
			try{
				objKeys.setIcon(false);
				objKeys.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}//Load Key Shortcuts
	
	protected void loadUsers() throws SQLException
	{
		boolean load_users = isLoaded("Users");
		if(load_users == false)
		{
			objUsers = new frmUsers(conn, this); 
			desktop.add(objUsers);
			
			objUsers.setVisible(true);
			objUsers.show();
		}else
		{
			try{
				objUsers.setIcon(false);
				objUsers.setSelected(true);
			}catch(PropertyVetoException e){
			}
		}
	}//Load Users
	
	private void menubar_actionPerformed(ActionEvent event, int intWhich) 
	{
		switch(intWhich) 
		{
			case 0:		
				UnloadWindow();
				break;
			case 1:	
				try{loadUsers();}
				catch(SQLException sqle){}
				break;
			case 2:	
				loadContent();
				break;
			case 3:	
				try{loadBarrowers();}
				catch(SQLException sqle){}
				break;
			case 4:
				try{loadBarrowed();}
				catch(SQLException sqle){}
				break;
			case 5:
				try{loadBooks();}
				catch(SQLException sqle){}
				break;
			case 6:
				try{loadCategory();}
				catch(SQLException sqle){}
				break;
			case 7:
				try{loadReturnedBook();}
				catch(SQLException sqle){}
				break;
			case 8:
				try{loadDueBooks();}
				catch(SQLException sqle){}
				break;
			case 9:
				try{loadCourse();}
				catch(SQLException sqle){}
				break;
			case 10:
				try{loadSections();}
				catch(SQLException sqle){}
				break;
			case 11:
				try{loadYear();}
				catch(SQLException sqle){}
				break;
			case 12:
				try{loadYear();}
				catch(SQLException sqle){}
				break;
			case 13:
				loadAuthor();		
				break;
			case 14:
				loadKeys();		
				break;
				
		}
	}

	public void actionPerformed(ActionEvent event) 
	{
		Object object = event.getSource();
		if(object == itmAuthor){menubar_actionPerformed(event,13);}
		else if(object == itmUsers){menubar_actionPerformed(event,1);}
		else if(object == itmContent){menubar_actionPerformed(event,2);}
		else if(object == itmBarrowers){menubar_actionPerformed(event,3);}
		else if(object == itmBarrowed){menubar_actionPerformed(event,4);}
		else if(object == itmBooks){menubar_actionPerformed(event,5);}
		else if(object == itmCategory){menubar_actionPerformed(event,6);}
		else if(object == itmReturned){menubar_actionPerformed(event,7);}
		else if(object == itmDue){menubar_actionPerformed(event,8);}
		else if(object == itmCourse){menubar_actionPerformed(event,9);}
		else if(object == itmSections){menubar_actionPerformed(event,10);}
		else if(object == itmYear){menubar_actionPerformed(event,11);}
		else if(object == itmFines){menubar_actionPerformed(event,12);}
		else if(object == itmKeys){menubar_actionPerformed(event,14);}
		else if(object == itmExit){menubar_actionPerformed(event,0);}
	}
	
	protected boolean isLoaded(String FormTitle) {

		JInternalFrame Form[] = desktop.getAllFrames();
		for (int i = 0; i < Form.length; i++) {
			if (Form[i].getTitle().equalsIgnoreCase (FormTitle)) {
				Form[i].show ();
				try{
					Form[i].setIcon(false);
					Form[i].setSelected(true);
				}catch(PropertyVetoException e){
				}			
				return true;
			}
		}
		return false;
	}
	
	protected void UnloadWindow()
	{
		try 
		{
	    	int reply = JOptionPane.showConfirmDialog(this,
	    	                                          "Are you sure you want to exit?",
	    	                                          "Library System 1.0" ,
	    	                                          JOptionPane.YES_NO_OPTION,
	    	                                          JOptionPane.WARNING_MESSAGE);
			// If the confirmation was affirmative, handle exiting.
			if (reply == JOptionPane.YES_OPTION) 
			{
				conn.close();
		    	setVisible(false); // hide the Frame
		    	dispose();         // free the system resources
		    	System.exit(0);    // close the application
			}
		}
		catch(Exception e) {}
	}
      
	public void internalFrameClosing(InternalFrameEvent e){UnloadWindow();}
	public void internalFrameClosed(InternalFrameEvent e){}
	public void internalFrameOpened(InternalFrameEvent e){}
	public void internalFrameIconified(InternalFrameEvent e){}
	public void internalFrameDeiconified(InternalFrameEvent e){}
	public void internalFrameActivated(InternalFrameEvent e){}
	public void internalFrameDeactivated(InternalFrameEvent e){}
	
}