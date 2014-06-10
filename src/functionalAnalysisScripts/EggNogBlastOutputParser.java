package functionalAnalysisScripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class EggNogBlastOutputParser
{
	
	private List<BlastOutput> resultsList; 
	
	/******************************
	 * Constructor                *
	 ******************************/
	public EggNogBlastOutputParser(String blastFilepath) throws Exception
	{
		this.resultsList= readBlastFile(blastFilepath);
	}
	
	
	/******************************
	 * Getters                    *
	 ******************************/
	public List<BlastOutput> getResultsList()
	{
		return this.resultsList;
	}
	

	/******************************
	 * Helper Methods             *
	 ******************************/

	/****    Read in files *******/
	public List<BlastOutput> readBlastFile(String blastFilepath) throws Exception
	{
		List<BlastOutput> list = new ArrayList<BlastOutput>();
		BufferedReader reader = new BufferedReader(new FileReader(blastFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			//System.out.println(nextLine);
			BlastOutput info = new BlastOutput(nextLine);
			list.add(info);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	
	public static class MutableInt
	{
		int value = 1; // note that we start at 1 since we're counting
		public void increment () { ++value;      }
		public int  get ()       { return value; }
		//public void set(int newValue)      {this.value = value + newValue;}
	}
	
	public static HashMap<String,MutableInt> readWGSBlastFile(String blastFilepath, HashMap<String,String> orgTaxonMap) throws Exception
	{
		HashMap<String,MutableInt> map = new HashMap<String,MutableInt>();
		BufferedReader reader = new BufferedReader(new FileReader(blastFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			MutableInt count = map.get(orgTaxonMap.get(nextLine.split("\t")[1].split(":")[0])+"."+nextLine.split("\t")[1].split(":")[1]);
			
			if (count == null)
			{
				System.out.println(orgTaxonMap.get(nextLine.split("\t")[1].split(":")[0])+"."+nextLine.split("\t")[1].split(":")[1]);
				map.put(orgTaxonMap.get(nextLine.split("\t")[1].split(":")[0])+"."+nextLine.split("\t")[1].split(":")[1], new MutableInt());
			}
			
			else
			{
				count.increment();
			}
			nextLine = reader.readLine();
		}
		reader.close();
		return map;	
	}
       	
	public static List<EggNOGMember> readMemberFile(String memberFilepath) throws Exception
	{
		List<EggNOGMember> memberList = new ArrayList<EggNOGMember>();
		BufferedReader reader = new BufferedReader(new FileReader(memberFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			EggNOGMember info = new EggNOGMember(nextLine);
			memberList.add(info);
			nextLine = reader.readLine();
		}
		reader.close();
		return memberList;
	}

	
	public static HashMap<String,List<Character>> readFunccatFile(String filepath) throws Exception
	{
		HashMap<String,List<Character>> map = new HashMap<String,List<Character>>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			EggNOGFunccat info = new EggNOGFunccat(nextLine);
			map.put(info.getEggNOG(),info.getFunccat());
			nextLine = reader.readLine();
		}
		reader.close();
		return map;
	}
	
	public static HashMap<String,String> readDescriptionFile(String filepath) throws Exception
	{
		HashMap<String,String> map = new HashMap<String,String>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			if(nextLine.split("\t").length > 1)
				map.put(nextLine.split("\t")[0], nextLine.split("\t")[1]);
			else
				map.put(nextLine.split("\t")[0], "unknown description");
			nextLine = reader.readLine();
		}
		reader.close();
		return map;
		
	}
	
	
	public static List<FunctionObject> readFunctionFile(String functionFilepath) throws Exception
	{
		List<FunctionObject> functionList = new ArrayList<FunctionObject>();
		BufferedReader reader = new BufferedReader(new FileReader(functionFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			FunctionObject info = new FunctionObject(nextLine);
			functionList.add(info);
			nextLine = reader.readLine();
		}
		reader.close();
		return functionList;
	}
	
	public static HashMap<String, String> makeOrgCodeTaxonMap(String filepath) throws Exception
	{
		HashMap<String, String> map = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			if(nextLine.split("TAX:").length > 1)
			{
				//System.out.println(nextLine);
				OrgCodeTaxonID info = new OrgCodeTaxonID(nextLine);
				map.put(info.getOrgCode(), info.getTaxonID());
			}
			nextLine = reader.readLine();
		}
		reader.close();
		return map;
		
	}
	
	public static HashMap<String, List<String>> makeWGSMemberHashMap(List<EggNOGMember> memberList)
	{
		HashMap<String, List<String>> map = new HashMap<String,List<String>>();
		for(EggNOGMember m : memberList)
		{
			map.put(m.getFullProteinName(),m.getFunctNarrow());
			//System.out.println(m.getFunctNarrow() + "  "+ m.getEggNOG());
	
		}
		return map;
		
	}
	
	
	public static HashMap<String, List<String>> makeWGSMemberHashMapLevel2(List<EggNOGMember> memberList)
	{
		HashMap<String, List<String>> map = new HashMap<String,List<String>>();
		for(EggNOGMember m : memberList)
		{
			map.put(m.getFullProteinName(),m.getFunctNarrow());
		}
		return map;
		
	}

	public static HashMap<String, List<String>> makeWGSMemberHashMapLevel1(List<EggNOGMember> memberList)
	{
		HashMap<String, List<String>> map = new HashMap<String,List<String>>();
		for(EggNOGMember m : memberList)
		{
			map.put(m.getFullProteinName(),m.getFunctBroad());
		}
		return map;
		
	}
	
	public static HashMap<String, EggNOGMember> makeMemberDescriptionHashMap(List<EggNOGMember> memberList)
	{
		HashMap<String, EggNOGMember> map = new HashMap<String,EggNOGMember>();
		for(EggNOGMember m : memberList)
		{
			map.put(m.getFullProteinName(),m);
		}
		return map;
		
	}
	
	public static HashMap<String, HashMap<String,Long>> makeFunctionPivotHashMap(String functionFilepath, String dirPath, String level ) 
			throws Exception
	{
		HashMap<String, HashMap<String,Long>> outerMap = new HashMap<String, HashMap<String,Long>>();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(functionFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			HashMap<String,Long> innerMap = outerMap.get(nextLine.trim());
			if(innerMap == null)
			{
				innerMap = new HashMap <String,Long>();
				outerMap.put(nextLine,innerMap);
			}

			//List<FunctionObject> functionList =EggNogBlastOutputParser.readFunctionFile(dirPath + "/" + nextLine +"/" + nextLine + "_16SFunctionalListLevel"+level+".txt");
			List<FunctionObject> functionList =EggNogBlastOutputParser.readFunctionFile(dirPath + "/" + nextLine);
			for(FunctionObject function : functionList)
			{
				Long count = innerMap.get(function.getName());
				if(count == null)
				{
					count=function.getCount();
				}
				innerMap.put(function.getName(),count);			
			}
			nextLine = reader.readLine();
		}
		return outerMap;
	}
	
	public static HashMap<String,Integer> makeWGSHashMap(HashMap<String,MutableInt> blastMap, HashMap<String,List<String>> memberMap)
	{
		HashMap<String, Integer> outerMap = new HashMap<String,Integer>();
		for(String item: blastMap.keySet())
		{
			if(memberMap.get(item)!=null)
			{
				for(String s : memberMap.get(item))
				{
					Integer count = outerMap.get(s);
					if(count == null)
					{
						count=blastMap.get(item).get();
					}
					else
					{
						count = count + blastMap.get(item).get();
					}
					outerMap.put(s,count);
					
				}
			}
		}
		return outerMap;
		
	}
	
	
	public static HashMap<String,Integer> makeWGSHashMapDescription(HashMap<String,MutableInt> blastMap, HashMap<String,EggNOGMember> memberMap)
	{
		HashMap<String, Integer> outerMap = new HashMap<String,Integer>();
		for(String item: blastMap.keySet())
		{
			if(memberMap.get(item) != null)
			{
				Integer count = outerMap.get(memberMap.get(item).getDescription());
				if(count == null)
				{
					count=blastMap.get(item).get();
				}
				else
				{
					count = count + blastMap.get(item).get();
				}
				outerMap.put(memberMap.get(item).getDescription(),count);
			}
			
		}
		return outerMap;
		
	}
	
	
	public static HashMap<String,Integer> makeWGSHashMapLevel4(HashMap<String,MutableInt> blastMap, HashMap<String,EggNOGMember> memberMap)
	{
		HashMap<String, Integer> outerMap = new HashMap<String,Integer>();
		for(String item: blastMap.keySet())
		{
			if(memberMap.get(item) != null)
			{
				Integer count = outerMap.get(memberMap.get(item).getEggNOG());
				if(count == null)
				{
					count=blastMap.get(item).get();
				}
				else
				{
					count = count + blastMap.get(item).get();
				}
				outerMap.put(memberMap.get(item).getEggNOG(),count);
			}
			
		}
		return outerMap;
		
	}
	
	
	public static HashMap<String,Long> makeSplitsToTotalFunction(String splitsFileList, String splitDir) throws IOException
	{
		HashMap<String, Long> outerMap = new HashMap<String,Long>();
		BufferedReader splitsListReader = new BufferedReader(new FileReader(splitsFileList));
		String splitsLine = splitsListReader.readLine();
		while(splitsLine != null)
		{
			BufferedReader functReader = new BufferedReader(new FileReader(splitDir + "/"+splitsLine));
			String functLine = functReader.readLine();
			while(functLine != null)
			{
				FunctionObject funct = new FunctionObject(functLine);
				if(!outerMap.containsKey(funct.getName()))
				{
					Long count=funct.getCount();
					outerMap.put(funct.getName(),count);
				
				}
				else
				{
					Long count=outerMap.get(funct.getName())+funct.getCount();
					outerMap.put(funct.getName(),count);
				}
				functLine = functReader.readLine();
			}
			functReader.close();
			splitsLine = splitsListReader.readLine();
		}
		splitsListReader.close();
		return outerMap;
		
	}
	
	
	public static HashMap<Character,List<String>> functionalMap(String filepath) throws Exception
	{
		HashMap<Character,List<String>> outerMap= new HashMap<Character,List<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine();
		String funcName ="";
		while(nextLine != null)
		{
			if(!nextLine.startsWith(" "))
			{
				funcName = nextLine;
			}
			else
			{
				List<String> valueList= new ArrayList<String>();
				valueList.add(funcName); valueList.add(nextLine.split("] ")[1]);
				outerMap.put(nextLine.charAt(2),valueList);
				
			}
			nextLine = reader.readLine();
		}
		reader.close();
		return outerMap;
	}
	
	
	public static void setDescriptionInfo(List<EggNOGMember> memberList,HashMap<String,String> descriptionMap)
	{
		for(EggNOGMember member : memberList)
		{
			String family = member.getEggNOG();
			String description = descriptionMap.get(family);
			member.setDescription(description);
		}
	}
	
	
	public static void setFunccatInfo(List<EggNOGMember> memberList, HashMap<Character,List<String>> functionalMap, String filepath) throws Exception
	{
		HashMap<String,List<Character>> famTofunc = readFunccatFile(filepath);
		for(EggNOGMember member : memberList)
		{
			List<Character> charList = famTofunc.get(member.getEggNOG());
			member.setFuncCode(charList);
			List<String> broadList = new ArrayList<String>();
			List<String> narrowList = new ArrayList<String>();
			if(charList != null)
				for(int i = 0; i < charList.size(); i++)
				{
					broadList.add(functionalMap.get(charList.get(i)).get(0));
					narrowList.add(functionalMap.get(charList.get(i)).get(1));
				}
			member.setFunctBroad(broadList);
			member.setFunctNarrow(narrowList);
		}	
	}
	
	
	/*** Write files ***/
	
	public static void writeWGSLevel34FunctionList( String outFile, HashMap<String,Integer> functionMap ) throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for( String funct : functionMap.keySet())
		{
			writer.write(funct+"\t");
			writer.write(functionMap.get(funct)+"\n"); 
		}
		writer.flush();  writer.close();
	}
	  
	public static void writeWGSsplitsToTotalFunctionList( String outFile, HashMap<String,Long> functionMap ) throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for( String funct : functionMap.keySet())
		{
			writer.write(funct+"\t");
			writer.write(functionMap.get(funct)+"\n"); 
		}
		writer.flush();  writer.close();
	}

	  // taken from Dr. Fodor's metagenomics tools eTree/PivotToSpreadsheet
	    private static class Holder implements Comparable<Holder>
	    {
	            String nodeName;
	            int totalNum=0;
	            
	            @Override
	            public int compareTo(Holder o)
	            {
	                    return o.totalNum - this.totalNum;
	            }
	    }
	    
	    // taken from Dr. Fodor's metagenomics tools eTree/PivotToSpreadsheet
	    public static void writeFunctionPivotTable( String outFile, HashMap<String, HashMap<String, Long>> outerMap, int sampleNameLength) throws Exception
	    {
	    	HashSet<String> families = new HashSet<String>();
	        List<Holder> nodes = new ArrayList<Holder>();
	        
	        for( String samples : outerMap.keySet() )
	        {
	           Holder h= new Holder();
	           h.nodeName = samples;
	           families.addAll(outerMap.get(samples).keySet());
	                      
	           for( Long i : outerMap.get(samples).values() )
	           {   
	            	h.totalNum += i;
	           }
	                    
	           nodes.add(h);
	         } 
	        
	        List<String> familyList = new ArrayList<String>(families);
	        //Collections.sort(familyList);
	        Collections.sort(nodes);    
	        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));      
	        writer.write("Sample ID");  
	        for( Holder h : nodes)
	        {
	        	writer.write("\t" + h.nodeName.substring(0, sampleNameLength));    
	        }
	        writer.write("\n");
	              
	        for( String fam : familyList)
	        {
	        	writer.write(fam);
	            for(Holder h : nodes)
	            {
	                    Long val = null;
	                    
	                    HashMap<String, Long> innerMap = outerMap.get(h.nodeName);
	                    
	                    if(  innerMap != null )
	                            val= innerMap.get(fam);
	                    
	                    if( val == null)
	                            writer.write("\t0");
	                    else
	                            writer.write("\t" + val);
	            }
	            
	            writer.write("\n");
	        }
	        writer.flush();  writer.close();
	    }
	  
	/******************************
	 * Main Method                *
	 ******************************/
	
	public static void main(String[] args) throws Exception 
	{
		
		//Make WGS blast split lists
		 // /**
		List<EggNOGMember> memberList = EggNogBlastOutputParser.readMemberFile("/Users/rbarner/hmp_data/eggNog/eggNOGfiles/bactNOG.members.txt");

		HashMap<Character,List<String>> funcMap = EggNogBlastOutputParser.functionalMap("/Users/rbarner/hmp_data/eggNog/fun.txt");
		System.out.println("Made functional map");
		HashMap<String,String> descriptionMap = EggNogBlastOutputParser.readDescriptionFile("/Users/rbarner/hmp_data/eggNog/eggNOGfiles/bactNOG.description.txt");
		System.out.println("Made description map : "+ descriptionMap.size());
		EggNogBlastOutputParser.setFunccatInfo(memberList, funcMap, "/Users/rbarner/hmp_data/eggNog/eggNOGfiles/bactNOG.funccat.txt");
		System.out.println("Set function in member list");
		EggNogBlastOutputParser.setDescriptionInfo(memberList, descriptionMap);
		System.out.println("Set description info for member list");
				
		HashMap<String, List<String>> memberHash1 = EggNogBlastOutputParser.makeWGSMemberHashMapLevel1(memberList);
		HashMap<String, List<String>> memberHash2 = EggNogBlastOutputParser.makeWGSMemberHashMapLevel2(memberList);
		HashMap<String, EggNOGMember> memberHash34 = EggNogBlastOutputParser.makeMemberDescriptionHashMap(memberList);
		System.out.println("Created member description hash map");
		
		
		HashMap<String,String> orgTaxonMap = makeOrgCodeTaxonMap("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/eggNog/kegg_taxonomy.txt");
		System.out.println(orgTaxonMap.keySet().size());
		
		BufferedReader sampleReader = new BufferedReader(new FileReader("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/list.txt"));
		String nextSample = sampleReader.readLine();
		
		while(nextSample != null)
		{
			System.out.println(nextSample);
			BufferedReader splitReader = new BufferedReader(new FileReader("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+nextSample+"/list.txt"));
			String nextSplit = splitReader.readLine();
			
			while(nextSplit != null)
			{
				System.out.println(nextSplit);
				HashMap<String,MutableInt> blastMap = EggNogBlastOutputParser.readWGSBlastFile("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+ nextSample + "/"+ nextSplit, orgTaxonMap);
				System.out.println("read in blast and member files");
	
				HashMap<String,Integer> outerMap1 = EggNogBlastOutputParser.makeWGSHashMap(blastMap,memberHash1);
				HashMap<String,Integer> outerMap2 = EggNogBlastOutputParser.makeWGSHashMap(blastMap,memberHash2);
				HashMap<String,Integer> outerMap3 = EggNogBlastOutputParser.makeWGSHashMapDescription(blastMap,memberHash34);
				HashMap<String,Integer> outerMap4 = EggNogBlastOutputParser.makeWGSHashMapLevel4(blastMap,memberHash34);
				System.out.println("created description count map");

				EggNogBlastOutputParser.writeWGSLevel34FunctionList("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+ nextSample +"/"+ nextSplit + "_WGSFunctionalListLevel1.txt", outerMap1);
				EggNogBlastOutputParser.writeWGSLevel34FunctionList("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+ nextSample +"/"+ nextSplit + "_WGSFunctionalListLevel2.txt", outerMap2);
				EggNogBlastOutputParser.writeWGSLevel34FunctionList("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+ nextSample +"/"+ nextSplit + "_WGSFunctionalListLevel3.txt", outerMap3);
				EggNogBlastOutputParser.writeWGSLevel34FunctionList("/Users/rbarner/hmp_data/eggNog/Vervet/_WGSblast/"+ nextSample +"/"+ nextSplit + "_WGSFunctionalListLevel4.txt", outerMap4);
				System.out.println("Wrote wgs "+ nextSplit +" table");
	
				nextSplit = splitReader.readLine();
				
			}
			splitReader.close();
			nextSample = sampleReader.readLine();
		}
		sampleReader.close();
	
		// **/
		
		/**
		HashMap<String, HashMap<String,Long>> outerMap1 = EggNogBlastOutputParser.makeFunctionPivotHashMap("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/level1list.txt","/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/","1");
		EggNogBlastOutputParser.writeFunctionPivotTable( "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level1/16SLevel1NormalizedPivot.txt",outerMap1, 10 );
		
		HashMap<String, HashMap<String,Long>> outerMap2 = EggNogBlastOutputParser.makeFunctionPivotHashMap("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/level2list.txt","/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/","2");
		EggNogBlastOutputParser.writeFunctionPivotTable( "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level2/16SLevel2NormalizedPivot.txt",outerMap2, 10 );
		
		HashMap<String, HashMap<String,Long>> outerMap3 = EggNogBlastOutputParser.makeFunctionPivotHashMap("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/level3list.txt","/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/","3");
		EggNogBlastOutputParser.writeFunctionPivotTable( "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level3/16SLevel3NormalizedPivot.txt",outerMap3, 10 );
		
		HashMap<String, HashMap<String,Long>> outerMap4 = EggNogBlastOutputParser.makeFunctionPivotHashMap("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/level4list.txt","/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/","4");
		EggNogBlastOutputParser.writeFunctionPivotTable( "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naive16Sblast/level4/16SLevel4NormalizedPivot.txt",outerMap4, 10 );
		**/
		
		/**
		
		BufferedReader sampleReader = new BufferedReader(new FileReader("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/list19.txt"));
		String nextSample = sampleReader.readLine();
		while(nextSample != null)
		{
			System.out.println(nextSample);
			HashMap<String,Long> splitsFunctsMap1 = makeSplitsToTotalFunction("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"/level1list.txt", "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample);
			writeWGSsplitsToTotalFunctionList( "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"_16SFunctionalListLevel1Corrected.txt", splitsFunctsMap1 );
			
			HashMap<String,Long> splitsFunctsMap2 = makeSplitsToTotalFunction("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"/level2list.txt", "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample);
			writeWGSsplitsToTotalFunctionList( "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"_16SFunctionalListLevel2Corrected.txt", splitsFunctsMap2 );
			
			HashMap<String,Long> splitsFunctsMap3 = makeSplitsToTotalFunction("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"/level3list.txt", "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample);
			writeWGSsplitsToTotalFunctionList( "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"_16SFunctionalListLevel3Corrected.txt", splitsFunctsMap3 );
			
			HashMap<String,Long> splitsFunctsMap4 = makeSplitsToTotalFunction("/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"/level4list.txt", "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample);
			writeWGSsplitsToTotalFunctionList( "/Users/rbarner/FunctionalPrediction/Vervet/vervet16Sblast/"+nextSample+"_16SFunctionalListLevel4Corrected.txt", splitsFunctsMap4 );
			
			nextSample = sampleReader.readLine();
		}
		sampleReader.close();
		// **/ 
		 
		
	}
	

}
