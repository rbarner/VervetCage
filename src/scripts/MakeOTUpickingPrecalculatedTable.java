package scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MakeOTUpickingPrecalculatedTable
{
	
	/******************************
	 * Constructor                *
	 ******************************/
	public MakeOTUpickingPrecalculatedTable(String filepath) throws Exception
	{
	}
	
	/******************************
	 * Helper Methods             *
	 ******************************/
	public void writePrecalculatedTable(String filepath, String writeFilepath) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		BufferedWriter writer= new BufferedWriter(new FileWriter(writeFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			String[] list = nextLine.split("\t");
			int num = list.length - 1;
			System.out.println(list[0]);
			writer.write(list[0] +"\t"+ num + "\n");
			nextLine = reader.readLine();
		}
		reader.close();
		writer.close();
	}
	
	/******************************
	 * Main Method                *
	 ******************************/
	
	public static void main(String[] args) throws Exception 
	{
		MakeOTUpickingPrecalculatedTable precalc = new MakeOTUpickingPrecalculatedTable("/Users/rbarner/Downloads/inflated_denoised_seqs_otus.txt");

		precalc.writePrecalculatedTable("/Users/rbarner/Downloads/inflated_denoised_seqs_otus.txt", "/Users/rbarner/Dropbox/starFish/starfish_precalculated.tab");
		

	}
}
