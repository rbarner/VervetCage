package pca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import utils.ConfigReader;

public class QuickAnnotate
{
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception
	{
		/**
		BufferedReader reader = new BufferedReader(new FileReader(new File( 
			ConfigReader.getWolfgangSep454Run() + File.separator + 
			"R_PCAOutPlate2.txt")));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File( 
			ConfigReader.getWolfgangSep454Run() + File.separator + 
			"R_PCAOutAnnotationPlate2.txt")));
		
		writer.write("sample\tpca1\tpca2\tpca3\tannotation\n");
		
		reader.readLine();
		String nextLine = reader.readLine();
		
		while( nextLine != null)
		{
			writer.write(nextLine);
			String annotation = "None";
			
			String firstString = new StringTokenizer(nextLine).nextToken();
			if( firstString.contains("BW"))
				annotation = "BW";
			else if (firstString.contains("MB"))
				annotation = "MB";
			else if (firstString.contains("SS"))
				annotation = "SS";
			else if ( firstString.contains("stable"))
				annotation = "stable";
			
			writer.write("\t" + annotation + "\n");
			nextLine = reader.readLine();
		}
		
		if( nextLine !=null)
			throw new Exception("Parsing error");
		
		writer.flush();  writer.close();
		**/
	}
}
