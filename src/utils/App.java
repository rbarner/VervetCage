package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
 
public class App
{
	 public static void main(String[] args) {
		 
			Properties prop = new Properties();
			OutputStream output = null;
		 
			try {
		 
				output = new FileOutputStream("/Users/rbarner/Dropbox/FodorLab/Roshonda_OTUsAnalysis/src/utils/MetagenomicsTools.properties");
		 
				// set the properties value
				prop.setProperty("BLAST_DIRECTORY", "/Applications/mothur/blast/bin");
				prop.setProperty("MOTHUR_DIR", "/Applications/mothur");

		 
				// save properties to project root folder
				prop.store(output, null);
		 
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		 
			}
		  }
}
