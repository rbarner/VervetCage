package functionalAnalysisScripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scripts.KylieSampleInfo;

public class FunctionalCountsParser
{
	private List<KylieSampleInfo> SampleList;
	private Integer numFunctions= 0;
	private List<FunctionCountsObject> functionList;
	
	
	
	public FunctionalCountsParser(String filepath, String sampleFilepath) throws Exception
	{
		this.functionList = readFunctionFile(filepath);
		this.SampleList = readSampleFile(sampleFilepath);
	}
	
	public List<FunctionCountsObject> getFunctionList()
	{
		return this.functionList;
	}
	
	public List<KylieSampleInfo> getSamplelist()
	{
		return SampleList;
	}
	
	public Integer getNumFunctions()
	{
		return numFunctions;
	}

	public void addNumFunctions()
	{
		numFunctions = numFunctions +1;
	}

	public List<FunctionCountsObject> readFunctionFile(String filepath) throws Exception
	{
		List<FunctionCountsObject> list = new ArrayList<FunctionCountsObject>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine(); nextLine = reader.readLine(); 
		while(nextLine != null)
		{
			//System.out.println(nextLine);
			FunctionCountsObject info = new FunctionCountsObject(nextLine,52);
			list.add(info);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	public static List<KylieSampleInfo> readSampleFile(String filepath) throws Exception
	{
		List<KylieSampleInfo> list = new ArrayList<KylieSampleInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		reader.readLine();
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			KylieSampleInfo sample = new KylieSampleInfo(nextLine);
			list.add(sample);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	public static List<FunctionCountsObject> filterFunctionlist(List<FunctionCountsObject> functionList)
	{
		List<FunctionCountsObject> filteredList = new ArrayList<FunctionCountsObject>();
		
		for(FunctionCountsObject func : functionList)
		{
			int greaterZero = 0;
			for(double freq : func.getCounts())
			{
				if(freq > 0.0)
					greaterZero++;
			}
			if(((double)greaterZero/(double)func.getCounts().size()) >= 0.5)
			{
				double sum = 0;
				for(double freq : func.getCounts())
				{
					sum = sum + freq;
				}
				if ( sum > 250)
					filteredList.add(func);
				
			}
		}
		return filteredList;
	}
	
	public static void setAbundanceList(List<FunctionCountsObject> functionList)
	{
		List<Double> totals = new ArrayList<Double>(Collections.nCopies(52, 0.0));
		for(FunctionCountsObject func : functionList)
		{
			for(int i=0; i<52; i++)
			{
				totals.set(i, totals.get(i)+func.getCounts().get(i));

			}
		}
		for(FunctionCountsObject func : functionList)
		{
			List<Double> abundances = new ArrayList<Double>();
			for(int i=0; i<52; i++)
			{
				abundances.add((double)func.getCounts().get(i)/totals.get(i));

			}
			func.setAbundances(abundances);
		}
	}
	
	
	/**** Set log base 10 transform of frequencies ****/
	public static void setLogTransformAbundances(List<FunctionCountsObject> functionList)
	{
		List<Double> sampleSequences = new ArrayList<Double>(Collections.nCopies(52, 0.0));
		double totalSequences = 0.0;
		double avgSeqPerSample = 0.0;
		
		for(FunctionCountsObject func : functionList)
		{
			for(int i=0; i<sampleSequences.size(); i++)
			{
				sampleSequences.set(i, sampleSequences.get(i)+func.getCounts().get(i));
				totalSequences = totalSequences + func.getCounts().get(i);
			}
		}
		avgSeqPerSample = (double)totalSequences/(double)sampleSequences.size();
		for(FunctionCountsObject func : functionList)
		{
			List<Double> logAbundances = new ArrayList<Double>();
			for(int i=0; i<sampleSequences.size(); i++)
			{
				double toBeLogged = ((double)func.getCounts().get(i)/(double)sampleSequences.get(i))*avgSeqPerSample +1.0;
				logAbundances.add(Math.log10(toBeLogged));
			}
			func.setLogAbundances(logAbundances);
		}
	}
	
	public void writeFunctions(List<FunctionCountsObject> thisFunctionList, String rawFilepath, String logFilepath) throws IOException
	{
		//List<RdpOTUinfo> filteredList = filterFunctionlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformAbundances(thisFunctionList);
		setAbundanceList(thisFunctionList);
		//writer.write("\t"); writer2.write("\t");
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
		}
		writer.write("\n"); writer2.write("\n");
		for(FunctionCountsObject o: thisFunctionList)
		{
			writer.write(o.getName()) ;writer2.write(o.getName());
			for(int i = 0; i<o.getCounts().size(); i++)
			{
				writer.write("\t"); writer2.write("\t"); 
				writer.write("" + o.getCounts().get(i)); writer2.write("" + o.getLogAbundances().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close(); writer2.close();
	}
	
	public void writeFunctions2(List<FunctionCountsObject> thisFunctionList,String logFilepath) throws IOException
	{
		//List<RdpOTUinfo> filteredList = filterFunctionlist(OTUList);
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformAbundances(thisFunctionList);
		setAbundanceList(thisFunctionList);
		//writer.write("\t"); writer2.write("\t");
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer2.write("\t"+"Sample"+s.getNum());
		}
		writer2.write("\n");
		for(FunctionCountsObject o: thisFunctionList)
		{
			writer2.write(o.getName());
			for(int i = 0; i<o.getCounts().size(); i++)
			{
				writer2.write("\t"); 
				writer2.write("" + o.getAbundances().get(i));
			}
			writer2.write("\n");
		}
		writer2.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("Does this work?");
		
		
		FunctionalCountsParser picrustLevel1 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel1.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> ssLevel1List = picrustLevel1.getFunctionList();
		picrustLevel1.writeFunctions2(ssLevel1List, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel1Abundances.txt");
		
		FunctionalCountsParser picrustLevel2 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel2.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> ssLevel2List = picrustLevel2.getFunctionList();
		picrustLevel2.writeFunctions2(ssLevel2List, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/picrustLevel2Abundances.txt");
		
		FunctionalCountsParser WGSLevel1 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level1/Level1Pivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> WGS1List = WGSLevel1.getFunctionList();
		WGSLevel1.writeFunctions2(WGS1List, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level1/Level1PivotAbundances.txt");
		
		FunctionalCountsParser WGSLevel2 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level2/Level2Pivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> WGS2List = WGSLevel2.getFunctionList();
		WGSLevel2.writeFunctions2(WGS2List, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level2/Level2PivotAbundances.txt");
		
		FunctionalCountsParser phylumLevel = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_phy_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> phylumList = phylumLevel.getFunctionList();
		phylumLevel.writeFunctions2(phylumList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_abundance_phy_transposed.txt");
		
		FunctionalCountsParser genusLevel = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_gen_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> genusList = genusLevel.getFunctionList();
		genusLevel.writeFunctions2(genusList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_abundance_gen_transposed.txt");
		

	}
}
