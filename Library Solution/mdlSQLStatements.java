
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.sql.*;

 public class mdlSQLStatements
 {
 	//This function is to remove item in the records
	public void recREMOVE(boolean sSTATUS, Statement stmt, String sTable, String sField, JTable JTBrrwrsTbl, int sNum) throws SQLException
	{
		if (true)
		{
			stmt.execute("DELETE FROM " + sTable + " WHERE " + sField + " ='" + JTBrrwrsTbl.getValueAt(JTBrrwrsTbl.getSelectedRow(),sNum) + "'");
		}
		else
		{
			stmt.execute("DELETE FROM " + sTable + " WHERE " + sField + " =" + JTBrrwrsTbl.getValueAt(JTBrrwrsTbl.getSelectedRow(),sNum));
		}
	}
 }