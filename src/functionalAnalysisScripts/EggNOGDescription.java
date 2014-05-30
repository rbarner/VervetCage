package functionalAnalysisScripts;

import java.io.IOException;

public class EggNOGDescription
{
	private String eggNOG;
	private String description;
	
	public EggNOGDescription(String aLine) throws IOException
	{
		this.eggNOG = aLine.split("\t")[0];
		if(aLine.split("\t")[1]=="")
			this.description = "function unknown";
		else
			this.description = aLine.split("\t")[1];
	}

	public String getEggNOG()
	{
		return eggNOG;
	}

	public String getDescription()
	{
		return description;
	}

}
