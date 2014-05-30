package scripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.TTest;

public class RunTTests 
{
	public static void conductTtest(List<OTUInfo> otuList, List<SampleInfo> sampleList, String filepath) throws Exception
	{
		List<OTUInfo> filteredList = new ArrayList<OTUInfo>();
		
		for(OTUInfo otu : otuList)
		{
			List<Number> wildTypeSamples = new ArrayList<Number>();
			List<Number> knockOutSamples = new ArrayList<Number>();
				
			for(int x=0; x < otu.getFrequencies().size(); x++)
			{
				SampleInfo sInfo = sampleList.get(x);
				if( sInfo.getGenotype().equals("WT"))
					wildTypeSamples.add(otu.getLogFrequencies().get(x));
				else if ( sInfo.getGenotype().equals("KO"))
					knockOutSamples.add(otu.getLogFrequencies().get(x));
				else throw new Exception("Genotype must be WT or KO but is in fact " + sInfo.getGenotype());
			}

			double pValue =1;
			
			try
			{
				pValue = TTest.ttestFromNumber(wildTypeSamples, knockOutSamples).getPValue();
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
		for(OTUInfo otu : filteredList)
		{
			double adjPValue = (otu.getPvalue()*(double)filteredList.size())/(double)rank;
			otu.setAdjPvalue(adjPValue);
			otu.setSignificant(otu.getPvalue()>otu.getAdjPvalue());
			writer.write(otu.getID() + "\t" + otu.getPvalue() + "\t" + otu.getAdjPvalue());
			writer.write("\n");
			rank++;
		}
		writer.close();
	}

	
	public static void main(String[] args) throws Exception
	{
		AJayOTUparser otusIL = new AJayOTUparser("/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_mucosa.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_key.txt", "IL");
		
		List<OTUInfo> otuListIL = otusIL.getOTUlist();
		List<OTUInfo> filteredListIL = otusIL.filterOTUlist(otuListIL);
		List<OTUInfo> familyListIL = otusIL.clusterFamilyOTUs(filteredListIL);
		List<OTUInfo> phyloListIL = otusIL.clusterPhyloOTUs(filteredListIL);
		
		otusIL.setLogTransformFrequencies(filteredListIL);
		otusIL.setLogTransformFrequencies(familyListIL);
		otusIL.setLogTransformFrequencies(phyloListIL);
		
		List<SampleInfo> sampleListIL = otusIL.getSamplelist();
		
		conductTtest(filteredListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_IL_OTUs.txt");
		conductTtest(familyListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_IL_Family.txt");
		conductTtest(phyloListIL, sampleListIL, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_IL_phylo.txt");
		
		
		
		AJayOTUparser otusStool = new AJayOTUparser("/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_fecal.txt","/Users/rbarner/Dropbox/FodorLab/AjayData/rawData/data_for_AF_5.16.12_key.txt", "Stool");
		
		List<OTUInfo> otuListStool = otusStool.getOTUlist();
		List<OTUInfo> filteredListStool = otusStool.filterOTUlist(otuListStool);
		List<OTUInfo> familyListStool = otusStool.clusterFamilyOTUs(filteredListStool);
		List<OTUInfo> phyloListStool = otusStool.clusterPhyloOTUs(filteredListStool);
		
		otusStool.setLogTransformFrequencies(filteredListStool);
		otusStool.setLogTransformFrequencies(familyListStool);
		otusStool.setLogTransformFrequencies(phyloListStool);
		
		List<SampleInfo> sampleListStool = otusStool.getSamplelist();
	
		conductTtest(filteredListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_Stool_OTUs.txt");
		conductTtest(familyListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_Stool_family.txt");
		conductTtest(phyloListStool, sampleListStool, "/Users/rbarner/Dropbox/FodorLab/AjayData/tTests/WTvsKO/tTest_For_Stool_phylo.txt");
	}
}
