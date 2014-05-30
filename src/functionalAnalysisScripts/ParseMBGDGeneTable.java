package functionalAnalysisScripts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseMBGDGeneTable
{
	private List<Mbgd> resultsList; 
	
	/******************************
	 * Constructor                *
	 ******************************/
	public ParseMBGDGeneTable(String filepath) throws Exception
	{
		this.resultsList= readMBGDFile(filepath);
	}
	
	
	/******************************
	 * Getters                    *
	 ******************************/
	public List<Mbgd> getResultsList()
	{
		return this.resultsList;
	}
	
	public List<Mbgd> readMBGDFile(String filepath) throws Exception
	{
		List<Mbgd> list = new ArrayList<Mbgd>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		System.out.println(nextLine);
		while(nextLine != null)
		{
			Mbgd info = new Mbgd(nextLine);
			list.add(info);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	public static HashMap<String, ArrayList<String>> makeMGDBHashMap(List<Mbgd> list ) throws Exception
	{
		HashMap<String, ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		map.put("mbgd", new ArrayList<String>());
		map.put("cog", new ArrayList<String>());
		map.put("kegg", new ArrayList<String>());
		map.put("tigr", new ArrayList<String>());
		for(Mbgd info:list)
		{
			if(map.get("mbgd").contains(info.getMbgd()))
			{
					continue;
			}
			else
			{
				map.get("mbgd").add(info.getMbgd());
			}
			
			if(map.get("cog").contains(info.getCog()))
			{
					continue;
			}
			else
			{
				map.get("cog").add(info.getCog());
			}
			
			if(map.get("kegg").contains(info.getKegg()))
			{
					continue;
			}
			else
			{
				map.get("kegg").add(info.getKegg());
			}
			
			if(map.get("tigr").contains(info.getTigr()))
			{
					continue;
			}
			else
			{
				map.get("tigr").add(info.getTigr());
			}
			
		}
		return map;
	}

	/******************************
	 * Main Method                *
	 ******************************/
	public static void main(String[] args) throws Exception 
	{
		ParseMBGDGeneTable sample = new ParseMBGDGeneTable("/Users/rbarner/Downloads/mbgd_2013-02_extended.tab");
		
		List<Mbgd> thisList = sample.getResultsList();
		
		HashMap<String, ArrayList<String>> thisMap = sample.makeMGDBHashMap(thisList);
		System.out.println("There are "+thisMap.get("mbgd").size() + " in mbgd.");
		System.out.println("There are "+thisMap.get("cog").size() + " in cog.");
		System.out.println("There are "+thisMap.get("kegg").size() + " in kegg.");
		System.out.println("There are "+thisMap.get("tigr").size() + " in tigr.");
		
		
	}
}
