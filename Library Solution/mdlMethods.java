


import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.PrintJob;
import javax.swing.*;

public class mdlMethods 
{
	public void printRecord (String rec,JFrame JFMainParent) 
	{

		StringReader SRReader = new StringReader (rec);
		LineNumberReader LNRReader = new LineNumberReader (SRReader);
		Font typeface = new Font ("Courier New", Font.PLAIN, 10);
		PrintJob PJPrint = Toolkit.getDefaultToolkit().getPrintJob (JFMainParent, "Print Customer Balance Report", new Properties());

		if (PJPrint != null) {
			Graphics GGraph = PJPrint.getGraphics ();
			if (GGraph != null) {
				FontMetrics FMFont = GGraph.getFontMetrics (typeface);
				int PAGE_HEIGHT = PJPrint.getPageDimension().height - 75;
    				int FONT_HEIGHT = FMFont.getHeight();
	    			int FONT_DESCENT = FMFont.getDescent();
    				int CURRENT_HEIGHT = 75;
				String NEXT_LINE;
				GGraph.setFont (typeface);

				try {
					do {
						NEXT_LINE = LNRReader.readLine ();
						if (NEXT_LINE != null) {         
							if ((CURRENT_HEIGHT + FONT_HEIGHT) > PAGE_HEIGHT) {
								GGraph.dispose();
								GGraph = PJPrint.getGraphics ();
								CURRENT_HEIGHT = 75;
							}							
							CURRENT_HEIGHT += FONT_HEIGHT;
							if (GGraph != null) {
								GGraph.setFont (typeface);
								GGraph.drawString (NEXT_LINE, 75, CURRENT_HEIGHT - FONT_DESCENT);
							}
						}
					}
					while (NEXT_LINE != null);					
				}
				catch (EOFException EOF_EXCEPTION) { }
				catch (Throwable TROW_ERR) { }
			}
			GGraph.dispose();
		}
		if (PJPrint != null)
			PJPrint.end ();
	}
}