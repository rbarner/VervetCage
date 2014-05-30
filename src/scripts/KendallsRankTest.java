package scripts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KendallsRankTest
{
	public static List<Pvalue> readInTtests(String filepath) throws Exception
	{
		List<Pvalue> list = new ArrayList<Pvalue>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String nextLine = reader.readLine();
		while(nextLine != null)
		{
			Pvalue pv= new Pvalue(nextLine);
			list.add(pv);
			nextLine = reader.readLine();
		}
		reader.close();
		return list;	
	}
	
	public static HashMap<String,ArrayList<Double>> combineILStoolpValues(List<Pvalue> iLlist, List<Pvalue> stoolList)
	{
		HashMap<String, ArrayList<Double>> pValueMap = new HashMap<String, ArrayList<Double>>();
		for(Pvalue pv : iLlist)
		{
			if(pv.getName() != null)
			{
				if(! pValueMap.containsKey(pv.getName()))
				{
					ArrayList<Double> pVals = new ArrayList<Double>();
					pVals.add(pv.getpValue());
					pValueMap.put(pv.getName(), pVals);
				}
				else
				{
					pValueMap.get(pv.getName()).add(pv.getpValue());
				}
			}
		}
		for(Pvalue pv : stoolList)
		{
			if(pv.getName() != null)
			{
				if(! pValueMap.containsKey(pv.getName()))
				{
					ArrayList<Double> pVals = new ArrayList<Double>();
					pVals.add(pv.getpValue());
					pValueMap.put(pv.getName(), pVals);
				}
				else
				{
					pValueMap.get(pv.getName()).add(pv.getpValue());
				}
			}
		}
		return pValueMap;
	}
	
	public static HashMap<String,ArrayList<Double>> filterpValueMap(HashMap<String,ArrayList<Double>> pValueMap)
	{
		HashMap<String, ArrayList<Double>> filteredpValueMap = new HashMap<String, ArrayList<Double>>();
		
		return filteredpValueMap;
	}
	public static void main(String[] args)
	{
		
	}
}
