package functionalAnalysisScripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import functionalAnalysisScripts.FunctionCountsObject;
import functionalAnalysisScripts.FunctionalCountsParser;
import utils.OneWayAnova;

public class runFunctionalOneWayAnova
{
	public static void conductOneWayAnovaDate(List<FunctionCountsObject> objectList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<FunctionCountsObject> filteredList = new ArrayList<FunctionCountsObject>();
		
		for(FunctionCountsObject otu : objectList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getLogAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogAbundances().get(x));
				factorList.add(sInfo.getDate());
			}

			double pValue =1;
			
			try
			{
				OneWayAnova own = new OneWayAnova(dataList, factorList);
				pValue = own.getPValue();
				otu.setPvalue(pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
			//System.out.println( otu.getID() + " " + otu.getGenus() + " " +  pValue + " "+ otu.getpValue());
			//count++;
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Function" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(FunctionCountsObject otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write(otu.getName() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaCage(List<FunctionCountsObject> objectList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<FunctionCountsObject> filteredList = new ArrayList<FunctionCountsObject>();
		
		for(FunctionCountsObject otu : objectList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getLogAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogAbundances().get(x));
				factorList.add(sInfo.getCage());
			}

			double pValue =1;
			
			try
			{
				OneWayAnova own = new OneWayAnova(dataList, factorList);
				pValue = own.getPValue();
				otu.setPvalue (pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
			//System.out.println( otu.getID() + " " + otu.getGenus() + " " +  pValue + " "+ otu.getpValue());
			//count++;
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Function" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(FunctionCountsObject otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write(otu.getName() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaAnimal(List<FunctionCountsObject> objectList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<FunctionCountsObject> filteredList = new ArrayList<FunctionCountsObject>();
		
		for(FunctionCountsObject otu : objectList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getLogAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogAbundances().get(x));
				factorList.add(sInfo.getID());
			}

			double pValue =1;
			
			try
			{
				OneWayAnova own = new OneWayAnova(dataList, factorList);
				pValue = own.getPValue();
				otu.setPvalue(pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
			//System.out.println( otu.getID() + " " + otu.getGenus() + " " +  pValue + " "+ otu.getpValue());
			//count++;
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Function" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(FunctionCountsObject otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write(otu.getName() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		
		FunctionalCountsParser sixteenSLevel1 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level1/16SLevel1PivotOrdered.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<KylieSampleInfo> sampleList = sixteenSLevel1.getSamplelist();
		List<FunctionCountsObject> ssLevel1List = sixteenSLevel1.getFunctionList();
		List<FunctionCountsObject> ssLevel1FilteredList = sixteenSLevel1.filterFunctionlist(ssLevel1List);
		FunctionalCountsParser.setLogTransformAbundances(ssLevel1FilteredList);
		
		conductOneWayAnovaCage(ssLevel1FilteredList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/anova/CageAnova_For_16Snorm_Level1.txt");
		System.out.println("Finished level 1");
		
		FunctionalCountsParser sixteenSLevel2 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level2/16SLevel2PivotOrdered.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> ssLevel2List = sixteenSLevel2.getFunctionList();
		List<FunctionCountsObject> ssLevel2FilteredList = sixteenSLevel2.filterFunctionlist(ssLevel2List);
		FunctionalCountsParser.setLogTransformAbundances(ssLevel2FilteredList);
		
		conductOneWayAnovaCage(ssLevel2FilteredList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/anova/CageAnova_For_16Snorm_Level2.txt");
		System.out.println("Finished level 2");
		
		FunctionalCountsParser sixteenSLevel3 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level3/16SLevel3PivotOrdered.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> ssLevel3List = sixteenSLevel3.getFunctionList();
		List<FunctionCountsObject> ssLevel3FilteredList = sixteenSLevel3.filterFunctionlist(ssLevel3List);
		FunctionalCountsParser.setLogTransformAbundances(ssLevel3FilteredList);
		
		conductOneWayAnovaCage(ssLevel3FilteredList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/anova/CageAnova_For_16Snorm_Level3.txt");
		System.out.println("Finished level 3");
		
		FunctionalCountsParser sixteenSLevel4 = new FunctionalCountsParser("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level4/16SLevel4PivotOrdered.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> ssLevel4List = sixteenSLevel4.getFunctionList();
		List<FunctionCountsObject> ssLevel4FilteredList = sixteenSLevel4.filterFunctionlist(ssLevel4List);
		FunctionalCountsParser.setLogTransformAbundances(ssLevel4FilteredList);
		
		conductOneWayAnovaCage(ssLevel4FilteredList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/anova/CageAnova_For_16Snorm_Level4.txt");
		System.out.println("Finished level 4");
		
	}


}
