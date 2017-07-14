

 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class frmAuthor extends JInternalFrame implements ActionListener 
{
	public static JScrollPane AuthorJSP = new JScrollPane();

	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel 	jpnlMain  	= new JPanel();

	JButton bttnExit   	= new JButton("Cancel");
	JButton bttnInfo   	= new JButton("Short Info.");
				
	JLabel 	lblPicture 	= new JLabel(new ImageIcon("@imgs/junald.gif"));
	JLabel  lblFullname = new JLabel("Fullname: Junald Astronomo Lagod");
	JLabel  lblMunicipal= new JLabel("Municipality: Kidapawan City");
	JLabel  lblProvince = new JLabel("Province: North Cotabato");
	JLabel  lblCountry 	= new JLabel("Country: Philippines");
	JLabel  lblCellNum	= new JLabel("Cellular Phone: +639195671599");
	JLabel  lblEmail 	= new JLabel("Email Add: junaldlagod@yahoo.com");
	JLabel  lblWebsite	= new JLabel("Website: www.junaldlagod.cjb.net");

	JTextArea textArea = new JTextArea();


	mdlFunctions module_func = new mdlFunctions();

	public frmAuthor(JFrame getParentFrame)
	{
		super("About the Author",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		setFrameIcon(new ImageIcon("@imgs/author.gif"));
		setSize(350,400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 350)/2,((screen.height-400)/2)-40);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		//module_func.setJLabel(lblPicture,15,25,100,100);
		module_func.setJLabel(lblFullname,130,5,200,50);
		module_func.setJLabel(lblMunicipal,130,20,200,50);
		module_func.setJLabel(lblProvince,130,35,200,50);
		module_func.setJLabel(lblCountry,130,50,200,50);
		module_func.setJLabel(lblCellNum,130,65,200,50);
		module_func.setJLabel(lblEmail,130,80,200,50);
		module_func.setJLabel(lblWebsite,130,95,200,50);
		
		jpnlMain.add(lblPicture);
		jpnlMain.add(lblFullname);
		jpnlMain.add(lblMunicipal);
		jpnlMain.add(lblProvince);
		jpnlMain.add(lblCountry);
		jpnlMain.add(lblCellNum);
		jpnlMain.add(lblEmail);
		jpnlMain.add(lblWebsite);

		//TextArea
		module_func.setJTextArea(textArea,0,0,320,195);
        textArea.setEditable(false);
        
		AuthorJSP.getViewport().add(textArea);
		AuthorJSP.setBounds(15,140,320,195);
		jpnlMain.add(AuthorJSP);
        
		module_func.setJButton(bttnExit,230,340,100,24,"exit","Unload Form");
		module_func.setJButton(bttnInfo,130,340,100,24,"info","Short Information");
		bttnExit.setMnemonic(KeyEvent.VK_C);
		bttnInfo.setMnemonic(KeyEvent.VK_S);

		jpnlMain.add(bttnExit);
		jpnlMain.add(bttnInfo);

		bttnInfo.addActionListener(this);
		bttnExit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) 
	{
		Object object = event.getSource();
		if(object == bttnInfo){ShortInfo();}
		else
		{		
		setVisible(false);
		dispose();//Unload Form
		}
	}
	
	protected void ShortInfo()
	{
		try {
	    	JOptionPane.showConfirmDialog(this,
			"INFO: If you want to have softwares\n like the following \n\n1. " +
			"Inventory System\n2. Marketing System\n3. Accounting System\n4. "+
			"Accounts Payable System\n5. Accounts Receivable System\n"+
			"6. and any other MIS System\n\n"+
			"Just contact me at\ncellphone number: +639195671599\n" +
			"Email Address:  junaldlagod@yahoo.com\n\n"+
			"For more details just visit the ff websites.\n"+
			"www.lagodcorporation.cjb.net\n"+
			"www.junaldlagod.cjb.net" ,
			"Library System ver. 1.0",  	
          	JOptionPane.OK_OPTION,
          	JOptionPane.INFORMATION_MESSAGE);
		
		} catch(Exception e) {}
	}

}
