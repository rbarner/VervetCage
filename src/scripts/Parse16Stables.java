package scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parse16Stables
{
	private List<OTUInfo> OTUlist;
	private List<SampleInfo> SampleList;
	private Integer numOTUs= 0;
	
	/******************************
	 * Constructor                *
	 ******************************/
	public Parse16Stables(String otuFilepath, String sampleFilepath) throws Exception
	{
		this.OTUlist = readOTUFile(otuFilepath);
		this.SampleList = readSampleFile(sampleFilepath); 
	}
	
	/******************************
	 * Getters                    *
	 ******************************/
	public List<OTUInfo> getOTUlist()
	{
		return this.OTUlist;
	}
	
	public List<SampleInfo> getSamplelist()
	{
		return SampleList;
	}
	
	public Integer getNumOTUs()
	{
		return numOTUs;
	}

	public void addNumOTUs()
	{
		numOTUs = numOTUs +1;
	}
	
	
	/******************************
	 * Helper Methods             *
	 ******************************/

	/****    Read in files *******/
	public List<OTUInfo> readOTUFile(String filepath) throws Exception
	{
		List<OTUInfo> list = new ArrayList<OTUInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		reader.readLine();
		int samples = ((reader.readLine().split("\t")).length)-3;
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			OTUInfo otu = new OTUInfo(nextLine,samples);
			list.add(otu);
			nextLine = reader.readLine();
			this.addNumOTUs();
		}
		reader.close();
		return list;	
	}
	
	
	public static List<SampleInfo> readSampleFile(String filepath) throws Exception
	{
		List<SampleInfo> list = new ArrayList<SampleInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		reader.readLine();
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			SampleInfo sample = new SampleInfo(nextLine);
			list.add(sample);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	/**** Filter out OTUs that occur in less than 25% of samples ****/
	public static List<OTUInfo> filterOTUlist(List<OTUInfo> OTUList)
	{
		List<OTUInfo> filteredList = new ArrayList<OTUInfo>();
		
		for(OTUInfo otu : OTUList)
		{
			int greaterZero = 0;
			for(int freq : otu.getFrequencies())
			{
				if(freq > 0)
					greaterZero++;
			}
			if(((double)greaterZero/(double)otu.getFrequencies().size()) >= 0.25)
				filteredList.add(otu);
		}
		return filteredList;
	}
	
	
	public void writeFilteredOTUs(List<OTUInfo> OTUList, String rawFilepath, String logFilepath) throws IOException
	{
		List<OTUInfo> filteredList = filterOTUlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		for(SampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+s.getGenotype());writer2.write("\t"+s.getGenotype());
		}
		writer.write("\n"); writer2.write("\n");
		for(OTUInfo o: filteredList)
		{
			writer.write("k__"); writer2.write("k__");
			if(o.getKingdom() != null)
			{
				writer.write(o.getKingdom()); writer2.write(o.getKingdom());
			}
			writer.write(";p__"); writer2.write(";p__");
			if(o.getPhylum() != null)
			{
				writer.write(o.getPhylum()); writer2.write(o.getPhylum());
			}
			writer.write(";c__"); writer2.write(";c__");
			if(o.getClass1() != null)
			{
				writer.write(o.getClass1()); writer2.write(o.getClass1());
			}
			writer.write(";o__"); writer2.write(";o__");
			if(o.getOrder() != null)
			{
				writer.write(o.getOrder()); writer2.write(o.getOrder());
			}
			writer.write(";f__"); writer2.write(";f__");
			if(o.getFamily() != null)
			{
				writer.write(o.getFamily()); writer2.write(o.getFamily());
			}
			for(int i = 0; i<o.getFrequencies().size(); i++)
			{
				writer.write("\t"); writer2.write("\t");
				writer.write("" + o.getFrequencies().get(i)); writer2.write("" + o.getLogFrequencies().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close();writer2.close();	
	}
	
	/******************************
	 * Main Method                *
	 ******************************/
	public static void main(String[] args) throws Exception 
	{
		//Parse16Stables OTUs = new Parse16Stables("/Users/lorainelab/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_mucosa.txt","/Users/lorainelab/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_key.txt");
		
		//List<OTUInfo> otuList = OTUs.getOTUlist();
		//List<SampleInfo> sampleList = OTUs.getSamplelist();
		/*
		OTUs.setSampleRichnessAndNumSequences(otuList,sampleList);
		OTUs.setSampleDiversity(otuList,sampleList);
		OTUs.setEvenness(sampleList);
		OTUs.writeSampleRichnessEvennessFile(sampleList,  "/Users/lorainelab/Dropbox/FodorLab/AjayData/ILsampleRichness.txt");
		
		
		OTUs.writeFilteredOTUs(otuList, "/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/filtered_IL_OTUdata.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/loggedfiltered_IL_OTUdata.txt");
		OTUs.writePhyloOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/phylo_IL_OTUdata.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/loggedphylo_IL_OTUdata.txt");
		OTUs.writeFamilyOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/family_IL_OTUdata.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/parsedData/IL/loggedFamily_IL_OTUdata.txt");
		**/
		
	}
	

}
