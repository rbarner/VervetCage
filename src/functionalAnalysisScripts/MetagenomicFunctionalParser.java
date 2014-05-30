package functionalAnalysisScripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;

public class MetagenomicFunctionalParser
{
	
	private List<PicrustInfo> PicrustFunctionalList; 
	private List<HumannInfo> HumannFunctionalList;
	private List<KylieSampleInfo> SampleList;
	private Integer numPicrustEntries= 0;
	private Integer numHumannEntries= 0;
	private Integer SampleSize;
	
	/******************************
	 * Constructor                *
	 ******************************/
	public MetagenomicFunctionalParser(String PicrustFilepath, String HumannFilepath, String sampleFilepath, String keggType) throws Exception
	{
		this.PicrustFunctionalList = readPicrustFunctionFile(PicrustFilepath);
		this.HumannFunctionalList = readHumannFunctionFile(HumannFilepath,keggType);
		this.SampleList = readSampleFile(sampleFilepath);
	}
	
	public MetagenomicFunctionalParser(String PicrustFilepath, String HumannFilepath, int SampleSize, String keggType) throws Exception
	{
		this.PicrustFunctionalList = readPicrustFunctionFile(PicrustFilepath);
		this.HumannFunctionalList = readHumannFunctionFile(HumannFilepath,keggType);
	}
	/******************************
	 * Getters                    *
	 ******************************/
	
	public List<PicrustInfo> getPicrustFunctionalList()
	{
		return this.PicrustFunctionalList;
	}
	
	public List<HumannInfo> getHumannFunctionalList()
	{
		return this.HumannFunctionalList;
	}
	
	
	public List<KylieSampleInfo> getSamplelist()
	{
		return SampleList;
	}
	
	public int getSampleSize()
	{
		return SampleSize;
	}
	
	
	public Integer getNumPicrustEntries()
	{
		return numPicrustEntries;
	}

	public void addNumPicrustEntries()
	{
		numPicrustEntries = numPicrustEntries +1;
	}
	
	
	public Integer getNumHumannEntries()
	{
		return numHumannEntries;
	}

	public void addNumHumannEntries()
	{
		numHumannEntries = numHumannEntries +1;
	}
	
	
	/******************************
	 * Helper Methods             *
	 ******************************/

	/****    Read in files *******/
	
	public List<PicrustInfo> readPicrustFunctionFile(String PicrustFilepath) throws Exception
	{
		List<PicrustInfo> list = new ArrayList<PicrustInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(PicrustFilepath));
		int samples = ((reader.readLine().split("\t")).length)-1;
		System.out.println(samples);
		String nextLine = reader.readLine();
		System.out.println(nextLine);
		while(nextLine != null)
		{
			PicrustInfo info = new PicrustInfo(nextLine,samples);
			list.add(info);
			nextLine = reader.readLine();
			this.addNumPicrustEntries();
		}
		reader.close();
		return list;	
	}
	
	
	public List<HumannInfo> readHumannFunctionFile(String HumannFilepath, String keggType, int SampleSize) throws Exception
	{
		List<HumannInfo> list = new ArrayList<HumannInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(HumannFilepath));
		int samples = SampleSize;
		System.out.println(samples);
		String nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine();
		System.out.println(nextLine);
		while(nextLine != null)
		{
			HumannInfo info = new HumannInfo(nextLine,samples,keggType);
			list.add(info);
			nextLine = reader.readLine();
			this.addNumHumannEntries();
		}
		reader.close();
		return list;	
	}
	
	public List<HumannInfo> readHumannFunctionFile(String HumannFilepath, String keggType) throws Exception
	{
		List<HumannInfo> list = new ArrayList<HumannInfo>();
		BufferedReader reader = new BufferedReader(new FileReader(HumannFilepath));
		int samples = ((reader.readLine().split("\t")).length)-2;
		System.out.println(samples);
		String nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); 
		System.out.println(nextLine);
		while(nextLine != null)
		{
			HumannInfo info = new HumannInfo(nextLine,samples,keggType);
			list.add(info);
			nextLine = reader.readLine();
			this.addNumHumannEntries();
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
	
	/**** Filter out functions that occur in less than 25% of samples ****/
	public static List<PicrustInfo> filterPicrustFunctionlist(List<PicrustInfo> PicrustFunctionalList)
	{
		List<PicrustInfo> filteredList = new ArrayList<PicrustInfo>();
		
		for(PicrustInfo function : PicrustFunctionalList)
		{
			int greaterZero = 0;
			for(double freq : function.getAbundances())
			{
				if(freq > 0.0)
					greaterZero++;
			}
			if(((double)greaterZero/(double)function.getAbundances().size()) >= 0.25)
				filteredList.add(function);
		}
		return filteredList;
	}
	
	public static List<HumannInfo> filterHumannFunctionlist(List<HumannInfo> HumannFunctionalList)
	{
		List<HumannInfo> filteredList = new ArrayList<HumannInfo>();
		
		for(HumannInfo function : HumannFunctionalList)
		{
			int greaterZero = 0;
			for(double freq : function.getAbundances())
			{
				if(freq > 0.0)
					greaterZero++;
			}
			if(((double)greaterZero/(double)function.getAbundances().size()) >= 0.25)
				filteredList.add(function);
		}
		return filteredList;
	}

	/**** Set abundances and log base 10 transform of abundances from frequencies ****/
	public void setLogTransformPicrustAbundances(List<PicrustInfo> PicrustFunctionalList)
	{
		List<Double> sampleSequences = new ArrayList<Double>(Collections.nCopies(63, 0.0));
		double totalSequences = 0.0;
		//double avgSeqPerSample = 0.0;
		
		for(PicrustInfo info : PicrustFunctionalList)
		{
			for(int i=0; i<sampleSequences.size(); i++)
			{
				sampleSequences.set(i, sampleSequences.get(i) + info.getFrequencies().get(i));
				totalSequences = totalSequences + info.getFrequencies().get(i);
			}
		}
		//avgSeqPerSample = (double)totalSequences/(double)sampleSequences.size();
		for(PicrustInfo info : PicrustFunctionalList)
		{
			List<Double> logAbundances = new ArrayList<Double>();
			List<Double> newAbundances = new ArrayList<Double>();
			for(int i=0; i<sampleSequences.size(); i++)
			{
				double abundance=((double)info.getFrequencies().get(i)/(double)sampleSequences.get(i));
				newAbundances.add(abundance);
				double toBeLogged =0.0;
				if(abundance == 0.0)
				{
					toBeLogged = abundance +1e-15;
				}
				else
				{
					toBeLogged = abundance;
				}
				logAbundances.add(Math.log10(toBeLogged));
				logAbundances.add(Math.log10(toBeLogged));
			}
			info.setLogAbundances(logAbundances);
			info.setAbundances(newAbundances);
		}
	}
	
	public void setLogTransformHumannAbundances(List<HumannInfo> HumannFunctionalList)
	{
		List<Double> sampleSequences = new ArrayList<Double>(Collections.nCopies(63, 0.0));
		double totalSequences = 0.0;
		//double avgSeqPerSample = 0.0;
		
		for(HumannInfo info : HumannFunctionalList)
		{
			for(int i=0; i<sampleSequences.size(); i++)
			{
				sampleSequences.set(i, sampleSequences.get(i)+info.getAbundances().get(i));
				totalSequences = totalSequences + info.getAbundances().get(i);
			}
		}
		//avgSeqPerSample = (double)totalSequences/(double)sampleSequences.size();
		for(HumannInfo info : HumannFunctionalList)
		{
			List<Double> logAbundances = new ArrayList<Double>();
			for(int i=0; i<sampleSequences.size(); i++)
			{
				double toBeLogged =0.0;
				if((double)info.getAbundances().get(i) == 0.0)
				{
					toBeLogged = (double)info.getAbundances().get(i) +1e-15;
				}
				else
				{
					toBeLogged = (double)info.getAbundances().get(i);
				}
				logAbundances.add(Math.log10(toBeLogged));
			}
			info.setLogAbundances(logAbundances);
		}
	}
		
	
	/**** Set sample evenness ****/
	/*
	public void setPicrustEvenness(List<KylieSampleInfo> sampleList)
	{	
		for(KylieSampleInfo sample : sampleList)
		{
			if(numPicrustEntries !=0)
				sample.setEvenness(numPicrustEntries);
		}
	}
	
	
	public void setHumannEvenness(List<KylieSampleInfo> sampleList)
	{	
		for(KylieSampleInfo sample : sampleList)
		{
			if(numHumannEntries !=0)
				sample.setEvenness(numHumannEntries);
		}
	}
	**/
	
	/**** Cluster by hierarchy levels ****/
	public List<HumannInfo> clusterPathwaysLevel1(List<HumannInfo> HumannFunctionalList)
	{
		HashMap<String, ArrayList<HumannInfo>> level1Map = new HashMap<String, ArrayList<HumannInfo>>();
		List<HumannInfo> level1List = new ArrayList<HumannInfo>();
		for(HumannInfo info : HumannFunctionalList)
		{
			if(! level1Map.containsKey(info.getPathwayLevel1()))
			{
				ArrayList<HumannInfo> newEntries = new ArrayList<HumannInfo>();
				newEntries.add(info);
				level1Map.put(info.getPathwayLevel1(), newEntries);
			}
			else
			{
				level1Map.get(info.getPathwayLevel1()).add(info);
			}
		}
		for(String level1: level1Map.keySet())
		{
			String Level1Pathway = level1Map.get(level1).get(0).getPathwayLevel1();
			List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSampleSize(), 0.0));
			for(int i=0; i<level1Map.get(level1).size(); i++)
			{
				for(int j=0; j<level1Map.get(level1).get(i).getAbundances().size();j++)
				{
					abundances.set(j, (abundances.get(j) + level1Map.get(level1).get(i).getAbundances().get(j)));
				}
			}
			HumannInfo level1Info = new HumannInfo(Level1Pathway, abundances);
			level1List.add(level1Info);
		}
		return level1List;
	}

	public List<HumannInfo> clusterPathwaysLevel2(List<HumannInfo> HumannFunctionalList)
	{
		HashMap<String, ArrayList<HumannInfo>> level2Map = new HashMap<String, ArrayList<HumannInfo>>();
		List<HumannInfo> level2List = new ArrayList<HumannInfo>();
		for(HumannInfo info : HumannFunctionalList)
		{
			if(! level2Map.containsKey(info.getPathwayLevel1() +"_"+info.getPathwayLevel2()))
			{
				ArrayList<HumannInfo> newEntries = new ArrayList<HumannInfo>();
				newEntries.add(info);
				level2Map.put(info.getPathwayLevel1()+"_"+info.getPathwayLevel2(), newEntries);
			}
			else
			{
				level2Map.get(info.getPathwayLevel1()+"_"+info.getPathwayLevel2()).add(info);
			}
		}
		for(String level2: level2Map.keySet())
		{
			String Level1Pathway= level2Map.get(level2).get(0).getPathwayLevel1();
			String Level2Pathway = level2Map.get(level2).get(0).getPathwayLevel2();
			List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSampleSize(), 0.0));
			for(int i=0; i<level2Map.get(level2).size(); i++)
			{
				for(int j=0; j<level2Map.get(level2).get(i).getAbundances().size();j++)
				{
					abundances.set(j, (abundances.get(j) + level2Map.get(level2).get(i).getAbundances().get(j)));
				}
			}
			HumannInfo level2Info = new HumannInfo(Level1Pathway,Level2Pathway, abundances);
			level2List.add(level2Info);
		}
		return level2List;
	}
	/****    Write Files *******/	
	public void writePicrustFunctions(List<PicrustInfo> PicrustFunctionalList, String rawFilepath, String logFilepath) throws IOException
	{
		//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformPicrustAbundances(PicrustFunctionalList);
		writer.write("Name"); writer2.write("Name");
		/**
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
		}
		**/
		writer.write("\n"); writer2.write("\n");
		for(PicrustInfo o: PicrustFunctionalList)
		{
			if(o.getName() != null)
			{
				writer.write(o.getName()) ;writer2.write(o.getName());
			}
			for(int i = 0; i<o.getAbundances().size(); i++)
			{
				writer.write("\t"); writer2.write("\t"); 
				writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close(); writer2.close();
	}
	
	public void writeHumannFunctions(List<HumannInfo> HumannFunctionalList, String rawFilepath, String logFilepath) throws IOException
	{
		//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformHumannAbundances(HumannFunctionalList);
		writer.write("Name"); writer2.write("Name");
		/*
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
		}
		**/
		writer.write("\n"); writer2.write("\n");
		for(HumannInfo o: HumannFunctionalList)
		{
			if(o.getName() != null)
			{
				writer.write(o.getName()) ;writer2.write(o.getName());
			}
			for(int i = 0; i<o.getAbundances().size(); i++)
			{
				writer.write("\t"); writer2.write("\t"); 
				writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close(); writer2.close();
	}
	
	public void writeLevel1HumannFunctions(List<HumannInfo> HumannFunctionalList, String rawFilepath, String logFilepath) throws IOException
	{
		List<HumannInfo> Level1List = clusterPathwaysLevel1(HumannFunctionalList);
		//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformHumannAbundances(Level1List);
		writer.write("Name"); writer2.write("Name");
		/*
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
		}
		**/
		writer.write("\n"); writer2.write("\n");
		for(HumannInfo o: Level1List)
		{
			if(o.getPathwayLevel1() != null)
			{
				writer.write(o.getPathwayLevel1()) ;writer2.write(o.getPathwayLevel1());
			}
			for(int i = 0; i<o.getAbundances().size(); i++)
			{
				writer.write("\t"); writer2.write("\t"); 
				writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close(); writer2.close();
	}
	
	public void writeLevel2HumannFunctions(List<HumannInfo> HumannFunctionalList, String rawFilepath, String logFilepath) throws IOException
	{
		List<HumannInfo> Level2List = clusterPathwaysLevel2(HumannFunctionalList);
		//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
		BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
		BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
		setLogTransformHumannAbundances(Level2List);
		writer.write("Name"); writer2.write("Name");
		/*
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
		}
		**/
		writer.write("\n"); writer2.write("\n");
		for(HumannInfo o: Level2List)
		{
			if(o.getPathwayLevel2() != null)
			{
				writer.write(o.getPathwayLevel2()) ;writer2.write(o.getPathwayLevel2());
			}
			for(int i = 0; i<o.getAbundances().size(); i++)
			{
				writer.write("\t"); writer2.write("\t"); 
				writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
			}
			writer.write("\n"); writer2.write("\n");
		}
		writer.close(); writer2.close();
	}
	
	/******************************
	 * Main Method                *
	 ******************************/
	
	public static void main(String[] args) throws Exception 
	{
		
		MetagenomicFunctionalParser functions2 = new MetagenomicFunctionalParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/picrust/stool_commonSamples_Pathway_picrust.txt",
				"/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/stool_commonSamples_Pathway_abundances.txt",63, "Pathway");
		
		System.out.println("It's working!");
		
		List<PicrustInfo> picrustfunctionalList2 = functions2.getPicrustFunctionalList();
		//List<PicrustInfo> picrustfunctionalList1 = functions1.getPicrustFunctionalList();
		
		List<HumannInfo> humannfunctionalList2 = functions2.getHumannFunctionalList();
		
		functions2.writePicrustFunctions(picrustfunctionalList2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/picrust/parsedStool_commonSamples_Pathway_picrust.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/picrust/logParsedStool_commonSamples_Pathway_picrust.txt");
		//functions2.writePicrustFunctions(picrustfunctionalList2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedPicrustMetagenomics_at_level2.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/logParsedPicrustMetagenomics_at_level2.txt");
		//functions1.writePicrustFunctions(picrustfunctionalList1, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedPicrustMetagenomics_at_level1.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/logParsedPicrustMetagenomics_at_level1.txt");
		
		
		
		functions2.writeHumannFunctions(humannfunctionalList2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/parsedHumannMetagenomics_at_level3.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/logParsedHumann_at_level3.txt");
		//functions2.writeLevel2HumannFunctions(humannfunctionalList2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/parsedHumannMetagenomics_at_level2.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/logParsedHumann_at_level2.txt");
		//functions2.writeLevel1HumannFunctions(humannfunctionalList2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/parsedHumannMetagenomics_at_level1.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/hmp/humann/logParsedHumann_at_level1.txt");
		//List<PicrustInfo> filteredList = functions.filterFunctionalList(functionalList);

		
		
	}
}
