package pca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class DsspRelative
{
	private DsspRelative()
	{
		
	}
	
	public static void makeTable(String fileName, double normalize, String outfile) throws Exception
	{
		//System.out.println(fileLine);
		List<Double> absolutes = new ArrayList<Double>();
		List<Double> relatives = new ArrayList<Double>();
		List<String> residues = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String nextLine = reader.readLine();
		while(nextLine.endsWith("."))
			nextLine=reader.readLine();
		nextLine=reader.readLine();
		
		while(nextLine!=null)
		{
			String residue = nextLine.substring(7,10).trim();
			if(nextLine.substring(11,12).contains("A") && nextLine.substring(13,14).contains("A"))
			{
				double acc = Double.parseDouble(nextLine.substring(35,38).trim());
				double rel = acc/normalize;
				int rela= (int)(rel*100.0);
				double rel2 = ((double)rela/100.0);
				absolutes.add(acc);
				relatives.add(rel2);
				residues.add(residue);
			}
			nextLine=reader.readLine();
		}
		System.out.println(residues);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
		writer.write("Residue\tACC\tRSA");
		for(int i =0; i < residues.size(); i++)
		{
			writer.write("\n"+residues.get(i)+"\t"+absolutes.get(i)+"\t"+relatives.get(i));
		}
		writer.close();
		
	}
	

	public static void main(String[] args) throws Exception
	{
		makeTable("/Users/lorainelab/Desktop/StructuralBinf/Homework/Homework#4/results.txt",108,"/Users/lorainelab/Desktop/StructuralBinf/Homework/Homework#4/relatives.txt");
	}
}


