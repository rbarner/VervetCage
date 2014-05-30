package scripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.OneWayAnova;

public class RunOneWayAnova
{
	public static void conductOneWayAnovaCage(List<OTUInfo> otuList, List<SampleInfo> sampleList, String filepath) throws Exception
	{
		List<OTUInfo> filteredList = new ArrayList<OTUInfo>();
		
		for(OTUInfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getFrequencies().size(); x++)
			{
				SampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogFrequencies().get(x));
				factorList.add(sInfo.getCage());
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
		for(OTUInfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			if(otu.getPhylum()!=null)
				writer.write( otu.getPhylum() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else
				writer.write( otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaLitter(List<OTUInfo> otuList, List<SampleInfo> sampleList, String filepath) throws Exception
	{
		List<OTUInfo> filteredList = new ArrayList<OTUInfo>();
		
		for(OTUInfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getFrequencies().size(); x++)
			{
				SampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogFrequencies().get(x));
				factorList.add(sInfo.getLitter());
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
		for(OTUInfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write( otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaParent(List<OTUInfo> otuList, List<SampleInfo> sampleList, String filepath) throws Exception
	{
		List<OTUInfo> filteredList = new ArrayList<OTUInfo>();
		
		for(OTUInfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getFrequencies().size(); x++)
			{
				SampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getLogFrequencies().get(x));
				factorList.add(sInfo.getParent());
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
		for(OTUInfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write( otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		/*
		AJayOTUparser otusIL = new AJayOTUparser("/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_mucosa.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_key.txt", "IL");
		
		List<OTUInfo> otuListIL = otusIL.getOTUlist();
		List<OTUInfo> filteredListIL = otusIL.filterOTUlist(otuListIL);
		List<OTUInfo> familyListIL = otusIL.clusterFamilyOTUs(filteredListIL);
		List<OTUInfo> phyloListIL = otusIL.clusterPhyloOTUs(filteredListIL);
		
		otusIL.setLogTransformFrequencies(filteredListIL);
		otusIL.setLogTransformFrequencies(familyListIL);
		otusIL.setLogTransformFrequencies(phyloListIL);
		
		List<SampleInfo> sampleListIL = otusIL.getSamplelist();
		
		conductOneWayAnovaCage(filteredListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_IL_Genus.txt");
		conductOneWayAnovaCage(familyListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_IL_Family.txt");
		conductOneWayAnovaCage(phyloListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_IL_phylo.txt");
		
		conductOneWayAnovaLitter(filteredListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_IL_Genus.txt");
		conductOneWayAnovaLitter(familyListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_IL_Family.txt");
		conductOneWayAnovaLitter(phyloListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_IL_phylo.txt");
		
		conductOneWayAnovaParent(filteredListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_IL_Genus.txt");
		conductOneWayAnovaParent(familyListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_IL_Family.txt");
		conductOneWayAnovaParent(phyloListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_IL_phylo.txt");
		**/
		
		AJayOTUparser otusStool = new AJayOTUparser("/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_fecal.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_key.txt", "Stool");
		
		List<OTUInfo> otuListStool = otusStool.getOTUlist();
		List<OTUInfo> filteredListStool = otusStool.filterOTUlist(otuListStool);
		List<OTUInfo> familyListStool = otusStool.clusterFamilyOTUs(filteredListStool);
		List<OTUInfo> phyloListStool = otusStool.clusterPhyloOTUs(filteredListStool);
		
		otusStool.setLogTransformFrequencies(filteredListStool);
		otusStool.setLogTransformFrequencies(familyListStool);
		otusStool.setLogTransformFrequencies(phyloListStool);
		
		List<SampleInfo> sampleListStool = otusStool.getSamplelist();
	
		conductOneWayAnovaCage(filteredListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_Stool_Genus.txt");
		//conductOneWayAnovaCage(familyListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_Stool_family.txt");
		//conductOneWayAnovaCage(phyloListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Cage/CageAnova_For_Stool_phylo.txt");
		
		conductOneWayAnovaLitter(filteredListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_Stool_Genus.txt");
		//conductOneWayAnovaLitter(familyListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_Stool_family.txt");
		//conductOneWayAnovaLitter(phyloListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Litter/LitterAnova_For_Stool_phylo.txt");
		
		//conductOneWayAnovaParent(filteredListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_Stool_Genus.txt");
		//conductOneWayAnovaParent(familyListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_Stool_family.txt");
		//conductOneWayAnovaParent(phyloListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/Anova/Parent/ParentAnova_For_Stool_phylo.txt");
	}

}
