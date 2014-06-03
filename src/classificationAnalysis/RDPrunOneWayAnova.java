package classificationAnalysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.OneWayAnova;

public class RDPrunOneWayAnova
{
	public static void conductOneWayAnovaDate(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getAbundances().get(x));
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
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			if(otu.getGenus()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getFamily()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getOrder()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getClass1()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getPhylum()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getKingdom()!=null)
				writer.write(otu.getKingdom() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else
				writer.write(otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaCage(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getAbundances().get(x));
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
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			if(otu.getGenus()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getFamily()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getOrder()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getClass1()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getPhylum()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getKingdom()!=null)
				writer.write(otu.getKingdom() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else
				writer.write(otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductOneWayAnovaAnimal(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> dataList = new ArrayList<Number>();
			List<String> factorList = new ArrayList<String>();
				
			for(int x=0; x < otu.getAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				dataList.add(otu.getAbundances().get(x));
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
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
			//double adjPValue = (filteredList.size()*otu.getpValue())/rank;
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			if(otu.getGenus()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getFamily()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getOrder()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getClass1()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getPhylum()!=null)
				writer.write(otu.getKingdom()+"_"+otu.getPhylum() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else if(otu.getKingdom()!=null)
				writer.write(otu.getKingdom() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			else
				writer.write(otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		/**
		RdpParser OTUs = new RdpParser("/Users/lorainelab/Dropbox/FodorLab/KylieData/rawData/rdpClassifier/classifierFiles/kyliePivotTableRevised.txt","/Users/lorainelab/Dropbox/FodorLab/KylieData/rawData/key/2012-06-22_WholeGenomeKeyRevised.txt");
		
		List<RdpOTUinfo> otuList = OTUs.getOTUlist();
		List<RdpOTUinfo> allFamilyList = OTUs.clusterFamilyOTUs(otuList);
		List<RdpOTUinfo> allPhylumList = OTUs.clusterPhyloOTUs(otuList);
		List<RdpOTUinfo> allGenusList = OTUs.clusterGenusOTUs(otuList);
		List<RdpOTUinfo> allKingdomList = OTUs.clusterKingdomOTUs(otuList);
		List<RdpOTUinfo> allClassList = OTUs.clusterClassOTUs(otuList);
		List<RdpOTUinfo> allOrderList = OTUs.clusterOrderOTUs(otuList);
		
		List<RdpOTUinfo> filteredList = OTUs.filterOTUlist(otuList);
		List<RdpOTUinfo> familyList = OTUs.clusterFamilyOTUs(filteredList);
		List<RdpOTUinfo> phylumList = OTUs.clusterPhyloOTUs(filteredList);
		List<RdpOTUinfo> genusList = OTUs.clusterGenusOTUs(filteredList);
		List<RdpOTUinfo> kingdomList = OTUs.clusterKingdomOTUs(filteredList);
		List<RdpOTUinfo> classList = OTUs.clusterClassOTUs(filteredList);
		List<RdpOTUinfo> orderList = OTUs.clusterOrderOTUs(filteredList);
		
		List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
		
		conductOneWayAnovaDate(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_OTUs.txt");
		conductOneWayAnovaDate(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Genus.txt");
		conductOneWayAnovaDate(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Family.txt");
		conductOneWayAnovaDate(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Order.txt");
		conductOneWayAnovaDate(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Class.txt");
		conductOneWayAnovaDate(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Phylum.txt");
		conductOneWayAnovaDate(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/all/revised/DateAnova_For_Kingdom.txt");
		
		conductOneWayAnovaDate(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_OTUs.txt");
		conductOneWayAnovaDate(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Genus.txt");
		conductOneWayAnovaDate(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Family.txt");
		conductOneWayAnovaDate(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Order.txt");
		conductOneWayAnovaDate(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Class.txt");
		conductOneWayAnovaDate(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Phylum.txt");
		conductOneWayAnovaDate(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/date/rdpClassifier/filtered/revised/DateAnova_For_Kingdom.txt");
		
		conductOneWayAnovaAnimal(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_OTUs.txt");
		conductOneWayAnovaAnimal(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Genus.txt");
		conductOneWayAnovaAnimal(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Family.txt");
		conductOneWayAnovaAnimal(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Order.txt");
		conductOneWayAnovaAnimal(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Class.txt");
		conductOneWayAnovaAnimal(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Phylum.txt");
		conductOneWayAnovaAnimal(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/all/revised/AnimalAnova_For_Kingdom.txt");
		
		conductOneWayAnovaAnimal(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_OTUs.txt");
		conductOneWayAnovaAnimal(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Genus.txt");
		conductOneWayAnovaAnimal(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Family.txt");
		conductOneWayAnovaAnimal(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Order.txt");
		conductOneWayAnovaAnimal(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Class.txt");
		conductOneWayAnovaAnimal(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Phylum.txt");
		conductOneWayAnovaAnimal(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/animal/rdpClassifier/filtered/revised/AnimalAnova_For_Kingdom.txt");
		
		conductOneWayAnovaCage(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_OTUs.txt");
		conductOneWayAnovaCage(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Genus.txt");
		conductOneWayAnovaCage(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Family.txt");
		conductOneWayAnovaCage(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Order.txt");
		conductOneWayAnovaCage(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Class.txt");
		conductOneWayAnovaCage(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Phylum.txt");
		conductOneWayAnovaCage(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/all/revised/CageAnova_For_Kingdom.txt");
		
		conductOneWayAnovaCage(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_OTUs.txt");
		conductOneWayAnovaCage(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Genus.txt");
		conductOneWayAnovaCage(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Family.txt");
		conductOneWayAnovaCage(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Order.txt");
		conductOneWayAnovaCage(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Class.txt");
		conductOneWayAnovaCage(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Phylum.txt");
		conductOneWayAnovaCage(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/anova/cage/rdpClassifier/filtered/revised/CageAnova_For_Kingdom.txt");
		**/
	}


}
