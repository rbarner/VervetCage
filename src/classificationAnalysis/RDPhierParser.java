package classificationAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.lang.*;
import java.io.*;



public class RDPhierParser
{
	private List<RDPhier> hierList;
	
	/******************************
	 * Constructor                *
	 ******************************/
	public RDPhierParser(String hierFilepath) throws Exception
	{
		this.hierList = readHierFile(hierFilepath);
	}
	
	/******************************
	 * Getters                    *
	 ******************************/
	public List<RDPhier> getHierList()
	{
		return this.hierList;
	}
	
	/******************************
	 * Helper Methods             *
	 ******************************/

	public static List<RDPhier> readHierFile(String filepath) throws Exception
	{
		List<RDPhier> list = new ArrayList<RDPhier>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine(); nextLine = reader.readLine(); nextLine = reader.readLine();
		while(nextLine != null)
		{
			System.out.println(nextLine);
			RDPhier hier = new RDPhier(nextLine);
			list.add(hier);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	
	
	public static HashMap<String, HashMap<RDPhier,Integer>> makePivotHashMap(String classifiersfilepath, String dirPath ) 
			throws Exception
	{
		HashMap<String, HashMap<RDPhier,Integer>> outerMap = new HashMap<String, HashMap<RDPhier,Integer>>();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(classifiersfilepath));
		String nextLine = reader.readLine();
		int i = 1;
		while(nextLine != null)
		{
			HashMap<RDPhier,Integer> innerMap = outerMap.get(nextLine.trim());
			if(innerMap == null)
			{
				innerMap = new HashMap <RDPhier,Integer>();
				outerMap.put(nextLine,innerMap);
			}
			//RDPhierParser rdpParse1 = new RDPhierParser(dirPath + "/" + nextLine);
			//List<RDPhier> rdpList = rdpParse1.getHierList();
			//System.out.println(nextLine);
			List<RDPhier> rdpList =RDPhierParser.readHierFile(dirPath + "/" + nextLine);
			for(RDPhier hier : rdpList)
			{
				if(hier.getRank().equals("phylum"))
				{
					Integer count = innerMap.get(hier);
					if(count == null)
					{
						count=hier.getCount();
					}
					innerMap.put(hier,count);
				}
				
			}
			i++;
			nextLine = reader.readLine();
		}
		//System.out.println(i);
		return outerMap;
	}
	
	
	public static HashMap<String, HashMap<String,Integer>> makeClusteredPivotHashMap(String classifiersfilepath, String dirPath ) 
			throws Exception
	{
		HashMap<String, HashMap<String,Integer>> outerMap = new HashMap<String, HashMap<String,Integer>>();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(classifiersfilepath));
		String nextLine = reader.readLine();
		int i = 1;
		while(nextLine != null)
		{
			HashMap<String,Integer> innerMap = outerMap.get(nextLine.trim());
			if(innerMap == null)
			{
				innerMap = new HashMap <String,Integer>();
				outerMap.put(nextLine,innerMap);
			}
			//RDPhierParser rdpParse1 = new RDPhierParser(dirPath + "/" + nextLine);
			//List<RDPhier> rdpList = rdpParse1.getHierList();
			//System.out.println(nextLine);
			List<RDPhier> rdpList =RDPhierParser.readHierFile(dirPath + "/" + nextLine);
			for(RDPhier hier : rdpList)
			{
				if(hier.getRank().equals("phylum"))
				{
					Integer count = innerMap.get(hier.getName());
					if(count == null)
					{
						count=0;
					}
					count = count + hier.getCount();
					innerMap.put(hier.getName(),count);
				}
			}
			i++;
			nextLine = reader.readLine();
		}
		//System.out.println(i);
		return outerMap;
	}
	
	  // taken from Dr. Fodor's metagenomics tools eTree/PivotToSpreadsheet
    private static class Holder implements Comparable<Holder>
    {
            String nodeName;
            int totalNum=0;
            
            @Override
            public int compareTo(Holder o)
            {
            	return this.nodeName.compareTo(o.nodeName);
            }
    }
    
	 // taken from Dr. Fodor's metagenomics tools eTree/PivotToSpreadsheet
    public static void writeFunctionalPivotTable( String outFile, HashMap<String, HashMap<RDPhier, Integer>> outerMap ) throws Exception
    {
    	HashSet<RDPhier> families = new HashSet<RDPhier>();
        List<Holder> nodes = new ArrayList<Holder>();
        
        for( String samples : outerMap.keySet() )
        {
           Holder h= new Holder();
           h.nodeName = samples;
           families.addAll(outerMap.get(samples).keySet());
                      
           for( int i : outerMap.get(samples).values() )
           {   
            	h.totalNum += i;
           }
                    
           nodes.add(h);
         } 
        
        List<RDPhier> familyList = new ArrayList<RDPhier>(families);
        Collections.sort(familyList);
        Collections.sort(nodes);    
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));      
        writer.write("Sample ID");  
        for( Holder h : nodes)
        {
        	writer.write("\t" + h.nodeName.substring(0, 10));    
        }
        writer.write("\n");
              
        for( RDPhier fam : familyList)
        {
        	writer.write(fam.getName() + "_"+fam.getRank());
            for(Holder h : nodes)
            {
                    Integer val = null;
                    
                    HashMap<RDPhier, Integer> innerMap = outerMap.get(h.nodeName);
                    
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
    
    
    public static void writeClusteredFunctionalPivotTable( String outFile, HashMap<String, HashMap<String, Integer>> outerMap ) throws Exception
    {
    	HashSet<String> families = new HashSet<String>();
        List<Holder> nodes = new ArrayList<Holder>();
        
        for( String samples : outerMap.keySet() )
        {
           Holder h= new Holder();
           h.nodeName = samples;
           families.addAll(outerMap.get(samples).keySet());
                      
           for( int i : outerMap.get(samples).values() )
           {   
            	h.totalNum += i;
           }
                    
           nodes.add(h);
         } 
        
        List<String> familyList = new ArrayList<String>(families);
        Collections.sort(familyList);
        Collections.sort(nodes);    
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));      
        writer.write("Sample ID");  
        for( Holder h : nodes)
        {
        	writer.write("\t" + h.nodeName.substring(0, 10));    
        }
        writer.write("\n");
              
        for( String fam : familyList)
        {
        	writer.write(fam);
            for(Holder h : nodes)
            {
                    Integer val = null;
                    
                    HashMap<String, Integer> innerMap = outerMap.get(h.nodeName);
                    
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
    
    public static void main(String[] args) throws Exception 
	{
		// RDPhierParser test1 = new RDPhierParser("/Users/rbarner/HMP/16S_classifications/SRS011405.hier.txt");
		HashMap<String, HashMap<String,Integer>> outerMap = RDPhierParser.makeClusteredPivotHashMap("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/rdpClassifications/list.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/rdpClassifications");
		System.out.println("Does it mess up here?");
		RDPhierParser.writeClusteredFunctionalPivotTable( "/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/rdpClassifications/vervet"
				+ "Samples.phylum_classified.txt",outerMap );

	}
    
}


