

 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class frmKeys extends JInternalFrame implements ActionListener 
{
	public static JScrollPane AuthorJSP = new JScrollPane();

	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();

	JPanel 	jpnlMain  	= new JPanel();

	JButton bttnExit   	= new JButton("Cancel");
				
	JTextArea textArea = new JTextArea(
	    "\tLIST OF KEY SHORTCUTS \n\n" +
	    "FILES\n" +
	    "School Info 	\t Ctrl + I\n" +
	    "Users Records  \t Ctrl + U\n" +
	    "Exit Application	\t Ctrl + X\n\n" +
	    "RECORDS\n" +
	    "Barrowers Records\t Ctrl + B\n" +
	    "Barrowed Books  \t Ctrl + A\n" +
	    "Books Records	\t Ctrl + O\n" +
	    "Due Books	\t Ctrl + D\n" +
	    "Returned Books  \t Ctrl + R\n" +
	    "Category Records\t Ctrl + C\n" +
	    "Fines	\t Ctrl + F\n" +
	    "Year  	\t Ctrl + Y\n" +
	    "Course	\t Ctrl + U\n" +
	    "Sections	\t Ctrl + S\n\n" +
	    "TOOLS\n" +
	    "Calculator 	\t Alt + F1\n" +
	    "Notepad	  \t Alt + F2\n" +
	    "Wordpad	  \t Alt + F3\n" +
	    "MS Paint	\t Alt + F4\n\n" +
	    "WINDOWS\n" +
	    "Normal	\t Alt + F5\n" +
	    "Minimized  	\t Alt + F6\n" +
	    "Maximized	\t Alt + F7\n" +
	    "Cascade	\t Alt + F8\n" +
	    "Tile Horizontally  \t Alt + F9\n" +
	    "Tile Vertically	\t Alt + F10\n" +
	    "Arrange Icon	\t Alt + F11\n\n" +
	    "HELP\n" +
	    "Content... 	\t Alt + C\n" +
	    "Key Shortcuts	\t Alt + K\n" +
	    "Author	  \t Alt + A\n");

	mdlFunctions module_func = new mdlFunctions();

	public frmKeys(JFrame getParentFrame)
	{
		super("Key Shortcuts",false,true,false,true);
		
		jpnlMain.setBackground(Color.WHITE);
		jpnlMain.setLayout(null);

		setFrameIcon(new ImageIcon("@imgs/ktouch.gif"));
		setSize(350,400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((screen.width - 350)/2,((screen.height-400)/2)-40);
		
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, jpnlMain);

		//TextArea
		module_func.setJTextArea(textArea,0,0,320,195);
        textArea.setEditable(false);
        
		AuthorJSP.getViewport().add(textArea);
		AuthorJSP.setBounds(10,10,320,325);
		jpnlMain.add(AuthorJSP);
        
		module_func.setJButton(bttnExit,230,340,100,24,"exit","Unload Form");
		bttnExit.setMnemonic(KeyEvent.VK_C);

		jpnlMain.add(bttnExit);

		bttnExit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) 
	{
		Object object = event.getSource();
		if(object == bttnExit)
		{		
		setVisible(false);
		dispose();//Unload Form
		}
	}
	
}
