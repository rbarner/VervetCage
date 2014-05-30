package functionalAnalysisScripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scripts.KylieSampleInfo;


public class PicrustParser2
{
	private List<KylieSampleInfo> SampleList;
	private Integer numFunctions= 0;
	private List<FunctionCountsObject> functionList; 
	
	/******************************
	 * Constructor                *
	 ******************************/
	public PicrustParser2(String filepath, String sampleFilepath) throws Exception
	{
		this.functionList = readFunctionListFile(filepath);
		this.SampleList = readSampleFile(sampleFilepath);
	}
	
	
	/******************************
	 * Getters                    *
	 ******************************/
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
	

	/******************************
	 * Helper Methods             *
	 ******************************/

	/****    Read in files *******/
	public List<FunctionCountsObject> readFunctionListFile(String filepath) throws Exception
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
	
	
	public static class MutableInt
	{
		  int value = 1; // note that we start at 1 since we're counting
		  public void increment () { ++value;      }
		  public int  get ()       { return value; }
		  //public void set(int newValue)      {this.value = value + newValue;}
	}
	

	public static List<EggNOGMember2> readMemberFile(String memberFilepath) throws Exception
	{
		List<EggNOGMember2> memberList = new ArrayList<EggNOGMember2>();
		BufferedReader reader = new BufferedReader(new FileReader(memberFilepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			EggNOGMember2 info = new EggNOGMember2(nextLine);
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

	
	public static HashMap<String, EggNOGMember2> makeMemberHashMap(List<EggNOGMember2> memberList)
	{
		HashMap<String, EggNOGMember2> map = new HashMap<String,EggNOGMember2>();
		for(EggNOGMember2 m : memberList)
		{
			map.put(m.getEggNOG(),m);
		}
		return map;
	}
	
	public static HashMap<EggNOGMember2, List<Long>> makeMemberToFunctionHashMap(List<FunctionCountsObject> FunctionList, HashMap<String, EggNOGMember2> memberMap)
	{
		HashMap<EggNOGMember2, List<Long>> map = new HashMap<EggNOGMember2, List<Long>>();
		for(FunctionCountsObject f : FunctionList)
		{
			map.put(memberMap.get(f.getName()),f.getCounts());
		}
		return map;
	}

	
	public static HashMap<String, List<Long>> makeMemberToFunctionHashMapLevel3(HashMap<EggNOGMember2, List<Long>> FunctionListMap)
	{
		HashMap<String, List<Long>> map = new HashMap<String, List<Long>>();
		for(EggNOGMember2 m: FunctionListMap.keySet())
		{
			String desc = m.getDescription();
			if(!map.containsKey(desc))
			{
				map.put(desc,FunctionListMap.get(m));
			}
			else
			{
				for(int i=0; i< 52; i++)
					map.get(desc).set(i, map.get(desc).get(i) + FunctionListMap.get(m).get(i));
			}	
			
		}
		return map;
	}
	public static HashMap<String, List<Long>> makeMemberToFunctionHashMapLevel2(HashMap<EggNOGMember2, List<Long>> FunctionListMap)
	{
		HashMap<String, List<Long>> map = new HashMap<String, List<Long>>();
		for(EggNOGMember2 m: FunctionListMap.keySet())
		{
			for ( String s : m.getFunctNarrow())
			{
				String desc = s;
				if(!map.containsKey(desc))
				{
					map.put(desc,FunctionListMap.get(m));
				}
				else
				{
					for(int i=0; i< 52; i++)
						map.get(desc).set(i, map.get(desc).get(i) + FunctionListMap.get(m).get(i));
				}	
			}
		}
		return map;
	}
	
	public static HashMap<String, List<Long>> makeMemberToFunctionHashMapLevel1(HashMap<EggNOGMember2, List<Long>> FunctionListMap)
	{
		HashMap<String, List<Long>> map = new HashMap<String, List<Long>>();
		for(EggNOGMember2 m: FunctionListMap.keySet())
		{
			String broad = "-";
			for ( String s : m.getFunctBroad())
			{
				if(!s.equals(broad))
				{
					System.out.println(s+"\t"+broad);
					if(map.containsKey(s))
					{
						for(int j=0; j< 52; j++)
							map.get(s).set(j, map.get(s).get(j) + FunctionListMap.get(m).get(j));
					}
					else
					{
						map.put(s,FunctionListMap.get(m));
					}	
				}
				broad=s;
			}
		}
		return map;
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
	public static void setFunccatInfo(List<EggNOGMember2> memberList, HashMap<String,List<Character>> funcCatMap) throws Exception
	{
		for(EggNOGMember2 member : memberList)
		{
			member.setFuncCode(funcCatMap.get(member.getEggNOG()));
		}	
	}	
	
	public static void setFuncInfo(List<EggNOGMember2> memberList, HashMap<Character,List<String>> functionalMap) throws Exception
	{
		for(EggNOGMember2 member : memberList)
		{
			List<Character> charList = member.getFuncCode();
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
	
	public void writeFunctionsLevel4(HashMap<EggNOGMember2,List<Long>> functionListMap, String outFile) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		writer.write("Function");
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum());
		}
		writer.write("\n");
		for(EggNOGMember2 m : functionListMap.keySet())
		{
			writer.write(m.getEggNOG());
			for(Long l : functionListMap.get(m))
				writer.write("\t" + l);
			writer.write("\n");
		}
		writer.flush(); writer.close();
	}
	
	public void writeFunctionsLevel123(HashMap<String,List<Long>> functionListMap, String outFile) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		writer.write("Function");
		for(KylieSampleInfo s : this.getSamplelist())
		{
			writer.write("\t"+"Sample"+s.getNum());
		}
		writer.write("\n");
		for(String m : functionListMap.keySet())
		{
			writer.write(m);
			for(Long l : functionListMap.get(m))
				writer.write("\t" + l);
			writer.write("\n");
		}
		writer.flush(); writer.close();
	}

	/******************************
	 * Main Method                *
	 ******************************/
	
	public static void main(String[] args) throws Exception 
	{

		List<EggNOGMember2> memberList = PicrustParser2.readMemberFile("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/eggNog/bactNOG.description.txt");
		HashMap<String,EggNOGMember2> memberMap = PicrustParser2.makeMemberHashMap(memberList);
		HashMap<String,List<Character>> funcCatMap = PicrustParser2.readFunccatFile("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/eggNog/bactNOG.funccat.txt");
		PicrustParser2.setFunccatInfo(memberList, funcCatMap);
		HashMap<Character,List<String>> funcMap = PicrustParser2.functionalMap("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/eggNog/fun.txt");
		System.out.println("Made functional map");
		PicrustParser2.setFuncInfo(memberList, funcMap);
		/**
		for (EggNOGMember2 c : memberList)
		{
			EggNOGMember2 cm = memberMap.get(c.getEggNOG());
			System.out.print(cm.getEggNOG()+"\t"+cm.getDescription()+"\t");
			for(int i=0; i < cm.getFuncCode().size(); i++)
			{
				System.out.print(cm.getFuncCode().get(i)+"\t"+cm.getFunctNarrow().get(i)+"\t"+cm.getFunctBroad().get(i));
			}
			System.out.print("\n");
		}
		**/
		System.out.println("Set function in member list");
		System.out.println("Set description info for member list");

		PicrustParser2 pparser1 = new PicrustParser2("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/level4/WGSLevel4PivotOrdered.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
		List<FunctionCountsObject> FunctionList = pparser1.getFunctionList();
		HashMap<EggNOGMember2, List<Long>> functionMap = pparser1.makeMemberToFunctionHashMap(FunctionList,memberMap);

		//HashMap<String, List<Long>> functionMapLevel3 = pparser1.makeMemberToFunctionHashMapLevel3(functionMap);
		//HashMap<String, List<Long>> functionMapLevel2 = pparser1.makeMemberToFunctionHashMapLevel2(functionMap);
		HashMap<String, List<Long>> functionMapLevel1 = pparser1.makeMemberToFunctionHashMapLevel1(functionMap);
		
		//pparser1.writeFunctionsLevel4(functionMap, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel4.txt");
		//pparser1.writeFunctionsLevel123(functionMapLevel3, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel3.txt");
		//pparser1.writeFunctionsLevel123(functionMapLevel2, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel2.txt");
		pparser1.writeFunctionsLevel123(functionMapLevel1, "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/naiveWGSblast/WGSLevel1.txt");

	}
	

}
