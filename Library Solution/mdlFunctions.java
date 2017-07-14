

 
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.PrintJob;
import javax.swing.*;

 public class mdlFunctions
 {
 	public JButton setJButton(JButton bttn, int sLeft, int sTop, int sWidth, int sHeight, String setActionCmd, String srcToolTipText)
	{
		bttn.setBounds(sLeft,sTop,sWidth, sHeight);
		bttn.setFont(new Font("Dialog", Font.PLAIN, 12));
		bttn.setBackground(new Color(255,255,255));
		bttn.setToolTipText(srcToolTipText);
		bttn.setActionCommand(setActionCmd);
		return bttn;
	}
	
	public JLabel setJLabel(JLabel lbl, int sLeft, int sTop, int sWidth, int sHeight)
	{
		lbl.setBounds(sLeft,sTop,sWidth, sHeight);
		lbl.setFont(new Font("Dialog",Font.PLAIN,12));
		lbl.setBackground(new Color(255,255,255));
		return lbl;
	}
	
	public JTextField setJTextField(JTextField txtfield, int sLeft, int sTop, int sWidth, int sHeight)
	{
		txtfield.setBounds(sLeft,sTop,sWidth, sHeight);
		txtfield.setFont(new Font("Dialog",Font.PLAIN,12));
		txtfield.setBackground(new Color(255,255,255));
		return txtfield;
	}
	
	public JTextArea setJTextArea(JTextArea txtArea, int sLeft, int sTop, int sWidth, int sHeight)
	{
		txtArea.setBounds(sLeft,sTop,sWidth, sHeight);
		txtArea.setFont(new Font("Dialog",Font.PLAIN,12));
		txtArea.setBackground(new Color(255,255,255));
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
		return txtArea;
	}
	
	public JComboBox setJComboBox(JComboBox cbField, int sLeft, int sTop, int sWidth, int sHeight)
	{
		cbField.setBounds(sLeft,sTop,sWidth, sHeight);
		cbField.setFont(new Font("Dialog",Font.PLAIN,12));
		cbField.setBackground(new Color(255,255,255));
		return cbField;
	}
	
	public static JComboBox fillCombo(String strSQL,Connection sCN,String strFieldName)
	{
		Statement stFC;
		ResultSet rsFC;
		String cmbList[] =new String[0];
		int TotalRow = 0;
		int rowNum = 0;
		try
		{
			stFC = sCN.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsFC = stFC.executeQuery(strSQL);
			rsFC.afterLast();
			//Get the current record position
			if(rsFC.previous())TotalRow = rsFC.getRow();
			//Move back to the first record; 
			rsFC.beforeFirst();
			if(TotalRow > 0)
			{
				cmbList = new String[TotalRow];				
				while(rsFC.next())
				{
					cmbList[rowNum] = "" + rsFC.getString(strFieldName);
					rowNum++;
				}
			}
			else{cmbList[0] ="";}
		}
		catch(SQLException sqlEx)
		{
			System.out.println("\nError Found in mdlFunctions(fillCombo):" + sqlEx + "\n");
		}
		rsFC = null;
		stFC = null;
		TotalRow=0;
		return new JComboBox(cmbList);
	}	
	
	public boolean isTextEmpty(JTextField txtCaption, JLabel lblCaption)
	{
		if(txtCaption.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, lblCaption.getText() + " is required fields.\nPlease check it and try again.","Library System ver. 1",JOptionPane.WARNING_MESSAGE);
			return true;
		}
		else{return false;}
	}

	public String displayYear(String date)
	{
		// parse 4 character year from String date
		String year = date.substring(0,4);
		return year;
	}	
		
	public int displayMonth(String date)
	{
		// parse 2 character month from String date, then convert to integer		
		int month = Integer.parseInt(date.substring(5,7));
		return month;
	}	

	public String displayDay(String date)
	{
		// parse 2 character day from String date
		String day = date.substring(8,10);
		return day;
	}
 }