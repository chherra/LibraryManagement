


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class frmSearch extends JDialog
{
	Dimension screen 		 = 	Toolkit.getDefaultToolkit().getScreenSize();
	JPanel 	jpnlMain  		 = new JPanel();

	JButton bttnSearch 		 = new JButton("Search",	new ImageIcon("@imgs/search.gif"));
	JButton bttnExit 		 = new JButton("Cancel",	new ImageIcon("@imgs/cancel.gif"));
	
	JLabel  lblHeader	 	 = new JLabel();
	JLabel  lblIcon		 	 = new JLabel();
	JLabel  lblCaption		 = new JLabel("IMPORTANT: All Fields are required.");
	JLabel  lblSearchFor	 = new JLabel("Search For:");
	JLabel 	lblSearchIn 	 = new JLabel("Look In:");

	JTextField txtSearchFor  = new JTextField();
	JComboBox cbSearchIn;
	
	String sFilter;
	
	mdlFunctions module_func = new mdlFunctions();

	public frmSearch(JFrame OwnerForm, String sSearch)
	{
		super(OwnerForm,true);
		
		sFilter = sSearch;
		
		String sBarrowers[]={"BarrowersID","BarrowersName","Address","CurrentYear", "Course", "Section"};
		String sBarrowed[]={"BookNo", "BarrowersID","DateBarrowerd","DateDue","DateReturned", "NO_OF_DAYS_AFTER_DUE_DATE", "Fines"};
		String sBooks[]={"BookNo", "ISBN","Title","Author","CaegoryID", "YearPublished", "Price", "Quantity", "Barrowed", "Remaining"};
		String sDueBooks[]={"BookNo", "Title","Author", "Category", "BarrowersName", "DateBarrowerd", "DateDue"};
		String sReturned[]={"BookNo", "Title","Author","BarrowersName", "DateReturned"};
		String sCategory[]={"CategoryID","Category", "Description"};
		String sYear[]={"Year"};
		String sCourse[]={"Course","Year"};
		String sSections[]={"Year","Course", "Section", "Adviser"};
		String sUsers[]={"UserName"};

		if(sFilter == "Barrowers"){cbSearchIn = new JComboBox(sBarrowers);}
		else if(sFilter == "Barrowed"){cbSearchIn = new JComboBox(sBarrowed);}
		else if(sFilter == "Books"){cbSearchIn = new JComboBox(sBooks);}
		else if(sFilter == "Returned"){cbSearchIn = new JComboBox(sReturned);}
		else if(sFilter == "Due Books"){cbSearchIn = new JComboBox(sDueBooks);}
		else if(sFilter == "Category"){cbSearchIn = new JComboBox(sCategory);}
		else if(sFilter == "Course"){cbSearchIn = new JComboBox(sCourse);}
		else if(sFilter == "Sections"){cbSearchIn = new JComboBox(sSections);}
		else if(sFilter == "Users"){cbSearchIn = new JComboBox(sUsers);}

		module_func.setJComboBox(cbSearchIn,110,72,225,20);
		module_func.setJTextField(txtSearchFor,110,50,225,20);

		lblHeader.setIcon(new ImageIcon("@imgs/header.gif"));
		lblIcon.setIcon(new ImageIcon("@imgs/filter.gif"));
		
		module_func.setJLabel(lblCaption,60,2,500,40);
			lblCaption.setFont(new Font("Dialog", Font.BOLD, 12));
			lblCaption.setForeground(new Color(255,255,255));
		module_func.setJLabel(lblHeader,0,0,500,40);
		module_func.setJLabel(lblIcon,5,2,50,40);
		module_func.setJLabel(lblSearchFor,5,50,105,20);
		module_func.setJLabel(lblSearchIn,5,72,105,20);

		module_func.setJButton(bttnExit,235,100,100,24,"exit","Unload Form");
		module_func.setJButton(bttnSearch,135,100,100,24,"search","Search");
		bttnExit.setMnemonic(KeyEvent.VK_C);
		bttnSearch.setMnemonic(KeyEvent.VK_S);
		bttnExit.addActionListener(JBActionListener);
		bttnSearch.addActionListener(JBActionListener);

		//Add ComboBox
		jpnlMain.add(cbSearchIn);
		//Add TextField
		jpnlMain.add(txtSearchFor);
		//Add Labels
		jpnlMain.add(lblCaption);
		jpnlMain.add(lblIcon);
		jpnlMain.add(lblHeader);
		jpnlMain.add(lblSearchFor);
		jpnlMain.add(lblSearchIn);
		//Add Buttons
		jpnlMain.add(bttnExit);
		jpnlMain.add(bttnSearch);	
			
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		setSize(350,160);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-170)/2));

	}
	
	ActionListener JBActionListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String srcObj = e.getActionCommand();
			if(srcObj=="search")
			{
				if(sFilter == "Barrowers")
				{
					frmBarrowers.reloadRecord("SELECT * FROM tblBarrowers WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY BarrowersName ASC");
					dispose();
				}
				else if(sFilter == "Category")
				{
					frmCategory.reloadRecord("SELECT * FROM tblCategory WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY Category ASC");
					dispose();
				}
				else if(sFilter == "Books")
				{
					frmBooks.reloadRecord("SELECT * FROM qryBooks WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY BookNo ASC");
					dispose();
				}
				else if(sFilter == "Barrowed")
				{
					frmBooks.reloadRecord("SELECT * FROM tblBarrowedBooks WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY BookNo ASC");
					dispose();
				}				
				else if(sFilter == "Returned")
				{
					frmReturnedBooks.reloadRecord("SELECT * FROM qryReturnedBooks WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY BookNo ASC");
					dispose();
				}
				else if(sFilter == "Due Books")
				{
					frmDueBooks.reloadRecord("SELECT * FROM qryDueBooks WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY BookNo ASC");
					dispose();
				}	
				else if(sFilter == "Course")
				{
					frmCourse.reloadRecord("SELECT * FROM tblCourse WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY Year ASC, Course ASC");
					dispose();
				}	
				else if(sFilter == "Sections")
				{
					frmSections.reloadRecord("SELECT * FROM tblCourse WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY Year ASC, Course ASC");
					dispose();
				}
				else if(sFilter == "Users")
				{
					frmUsers.reloadRecord("SELECT * FROM tblUsers WHERE " + cbSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + txtSearchFor.getText() + "%' ORDER BY UserName ASC");
					dispose();
				}									
			}
			else if(srcObj=="exit"){dispose();}
		}
	};
}