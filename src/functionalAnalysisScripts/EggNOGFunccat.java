package functionalAnalysisScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EggNOGFunccat
{
	private String eggNOG;
	private List<Character> funccat= new ArrayList<Character>();
	
	public EggNOGFunccat(String aLine) throws IOException
	{
		this.eggNOG = aLine.split("\t")[0];
		String info = aLine.split("\t")[1];
		for (char c : info.toCharArray())
		{
		  funccat.add(c);
		}
	}

	public String getEggNOG()
	{
		return eggNOG;
	}

	public List<Character> getFunccat()
	{
		return funccat;
	}
}
