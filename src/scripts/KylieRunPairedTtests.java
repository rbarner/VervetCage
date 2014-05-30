package scripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.TTest;

public class KylieRunPairedTtests
{
	public static void conductPairedTtestBaseVs0105(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		List<String> animalIDs= new ArrayList<String>();
		animalIDs.add("1448");animalIDs.add("1211");animalIDs.add("1291");
		animalIDs.add("1467");animalIDs.add("1030");animalIDs.add("1245");animalIDs.add("1347");
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> baseSamples = new ArrayList<Number>();
			List<Number> weekSamples = new ArrayList<Number>();
			for(int y=0; y< animalIDs.size(); y++)
			{
				for(int x=0; x < otu.getAbundances().size(); x++)
				{
					KylieSampleInfo sInfo = sampleList.get(x);
					if( sInfo.getDate().equals("baseline") && sInfo.getID().equals(animalIDs.get(y)))
						baseSamples.add(otu.getAbundances().get(x));
					else if ( sInfo.getDate().equals("1/5/12") && sInfo.getID().equals(animalIDs.get(y)))
						weekSamples.add(otu.getAbundances().get(x));
				}
			}

			double pValue =0;
			try
			{
				pValue = TTest.pairedTTest(baseSamples, weekSamples).getPValue();
				otu.setPvalue(pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
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
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductPairedTtestBaseVs0112(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		List<String> animalIDs= new ArrayList<String>();
		animalIDs.add("1448");animalIDs.add("1211");animalIDs.add("1291");
		animalIDs.add("1467");animalIDs.add("1030");animalIDs.add("1245");animalIDs.add("1347");
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> baseSamples = new ArrayList<Number>();
			List<Number> weekSamples = new ArrayList<Number>();
			for(int y=0; y< animalIDs.size(); y++)
			{
				for(int x=0; x < otu.getAbundances().size(); x++)
				{
					KylieSampleInfo sInfo = sampleList.get(x);
					if( sInfo.getDate().equals("baseline") && sInfo.getID().equals(animalIDs.get(y)))
						baseSamples.add(otu.getAbundances().get(x));
					else if ( sInfo.getDate().equals("1/12/12") && sInfo.getID().equals(animalIDs.get(y)))
						weekSamples.add(otu.getAbundances().get(x));
				}
			}

			double pValue =1;
			try
			{
				pValue = TTest.pairedTTest(baseSamples, weekSamples).getPValue();
				otu.setPvalue(pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getAdjPvalue()<= 0.1);
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
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductPairedTtestBaseVsBiopsy(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		List<String> animalIDs= new ArrayList<String>();
		animalIDs.add("1448");animalIDs.add("1211");animalIDs.add("1291");
		animalIDs.add("1467");animalIDs.add("1030");animalIDs.add("1245");animalIDs.add("1347");
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> baseSamples = new ArrayList<Number>();
			List<Number> weekSamples = new ArrayList<Number>();
			for(int y=0; y< animalIDs.size(); y++)
			{
				for(int x=0; x < otu.getAbundances().size(); x++)
				{
					KylieSampleInfo sInfo = sampleList.get(x);
					if( sInfo.getDate().equals("baseline") && sInfo.getID().equals(animalIDs.get(y)))
						baseSamples.add(otu.getAbundances().get(x));
					else if ( sInfo.getDate().equals("biopsy") && sInfo.getID().equals(animalIDs.get(y)))
						weekSamples.add(otu.getAbundances().get(x));
				}
			}

			double pValue =1;
			try
			{
				pValue = TTest.pairedTTest(baseSamples, weekSamples).getPValue();
				otu.setPvalue(pValue);
			}
			catch(Exception ex)
			{
			}
			filteredList.add(otu);
		}
		Collections.sort(filteredList);
		int rank =1;
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write("Taxa" + "\t" + "P-value" + "\t" + "Adjusted P-value"+"\n");
		for(RdpOTUinfo otu : filteredList)
		{
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
		
		conductPairedTtestBaseVs0105(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVs0105(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVs0105(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVs0105(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVs0105(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVs0105(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVs0105(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/all/paired_ttest_For_Kingdom.txt");
		
		conductPairedTtestBaseVs0105(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVs0105(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVs0105(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVs0105(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVs0105(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVs0105(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVs0105(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0105/rdpClassifier/filtered/paired_ttest_For_Kingdom.txt");
		
		conductPairedTtestBaseVs0112(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVs0112(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVs0112(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVs0112(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVs0112(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVs0112(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVs0112(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/all/paired_ttest_For_Kingdom.txt");
		
		conductPairedTtestBaseVs0112(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVs0112(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVs0112(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVs0112(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVs0112(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVs0112(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVs0112(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVs0112/rdpClassifier/filtered/paired_ttest_For_Kingdom.txt");
		
		conductPairedTtestBaseVsBiopsy(otuList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVsBiopsy(allGenusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVsBiopsy(allFamilyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVsBiopsy(allOrderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVsBiopsy(allClassList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVsBiopsy(allPhylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVsBiopsy(allKingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/all/paired_ttest_For_Kingdom.txt");
		
		conductPairedTtestBaseVsBiopsy(filteredList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_OTUs.txt");
		conductPairedTtestBaseVsBiopsy(genusList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Genus.txt");
		conductPairedTtestBaseVsBiopsy(familyList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Family.txt");
		conductPairedTtestBaseVsBiopsy(orderList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Order.txt");
		conductPairedTtestBaseVsBiopsy(classList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Class.txt");
		conductPairedTtestBaseVsBiopsy(phylumList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Phylum.txt");
		conductPairedTtestBaseVsBiopsy(kingdomList, sampleList, "/Users/lorainelab/Dropbox/FodorLab/KylieData/Analysis/pairedTTest/baselineVsBiopsy/rdpClassifier/filtered/paired_ttest_For_Kingdom.txt");
		
		**/
	}
}


