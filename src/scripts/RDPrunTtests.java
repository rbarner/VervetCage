package scripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.TTest;

public class RDPrunTtests 
{
	public static void conductTtest(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> chowSamples = new ArrayList<Number>();
			List<Number> hfSamples = new ArrayList<Number>();
				
			for(int x=0; x < otu.getAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				if( sInfo.getDiet().equals("Chow"))
					chowSamples.add(otu.getAbundances().get(x));
				else if ( sInfo.getDiet().equals("HFr"))
					hfSamples.add(otu.getAbundances().get(x));
				else throw new Exception("Diet must be Chow or HF but is in fact " + sInfo.getDiet());
			}

			double pValue =1;
			
			try
			{
				pValue = TTest.ttestFromNumber(chowSamples, hfSamples).getPValue();
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
			else
				writer.write(otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}
	
	public static void conductAgeTtest(List<RdpOTUinfo> otuList, List<KylieSampleInfo> sampleList, String filepath) throws Exception
	{
		List<RdpOTUinfo> filteredList = new ArrayList<RdpOTUinfo>();
		
		for(RdpOTUinfo otu : otuList)
		{
			List<Number> oldSamples = new ArrayList<Number>();
			List<Number> youngSamples = new ArrayList<Number>();
				
			for(int x=0; x < otu.getAbundances().size(); x++)
			{
				KylieSampleInfo sInfo = sampleList.get(x);
				if( sInfo.getAgeCat().equals("old"))
					oldSamples.add(otu.getAbundances().get(x));
				else if ( sInfo.getAgeCat().equals("young"))
					youngSamples.add(otu.getAbundances().get(x));
				else throw new Exception("Age must be young or old but is in fact " + sInfo.getAgeCat());
			}

			double pValue =1;
			
			try
			{
				pValue = TTest.ttestFromNumber(oldSamples, youngSamples).getPValue();
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
		RdpParser OTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/rdpClassifier/classifierFiles/kyliePivotTable.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/2012-06-22_WholeGenomeKey.txt");
		
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
		
		conductAgeTtest(otuList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgeOTUsTTests.txt");;
		conductAgeTtest(allGenusList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgegenusTTests.txt");
		conductAgeTtest(allFamilyList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgefamilyTTests.txt");
		conductAgeTtest(allPhylumList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgephylumTTests.txt");
		conductAgeTtest(allKingdomList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgekingdomTTests.txt");
		conductAgeTtest(allClassList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgeclassTTests.txt");
		conductAgeTtest(allOrderList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/all/AgeorderTTests.txt");
		
		conductAgeTtest(filteredList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgeOTUsTTests.txt");
		conductAgeTtest(genusList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgegenusTTests.txt");
		conductAgeTtest(familyList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgefamilyTTests.txt");
		conductAgeTtest(phylumList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgephylumTTests.txt");
		conductAgeTtest(kingdomList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgekingdomTTests.txt");
		conductAgeTtest(classList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgeclassTTests.txt");
		conductAgeTtest(orderList, sampleList, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/ttest/rdpClassifier/filtered/AgeorderTTests.txt");
		**/
	}
}

