package functionalAnalysisScripts;

import java.io.IOException;
import java.util.List;

public class EggNOGMember2
{
	private String eggNOG;
	private String description = "";
	private List<Character> funcCode;
	private List<String> functBroad;
	private List<String> functNarrow;
	
	/******************************
	 * Constructors                
	 * @throws IOException *
	 ******************************/
	public EggNOGMember2(String aLine) throws IOException
	{
		this.eggNOG = aLine.split("\t")[0];
		if(aLine.split("\t").length > 1)
			this.description= aLine.split("\t")[1];
	}

	public String getEggNOG()
	{
		return eggNOG;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Character> getFuncCode()
	{
		return funcCode;
	}

	public void setFuncCode(List<Character> funcCode)
	{
		this.funcCode = funcCode;
	}

	public List<String> getFunctBroad()
	{
		return functBroad;
	}

	public void setFunctBroad(List<String> functBroad)
	{
		this.functBroad = functBroad;
	}

	public List <String> getFunctNarrow()
	{
		return functNarrow;
	}

	public void setFunctNarrow(List <String> functNarrow)
	{
		this.functNarrow = functNarrow;
	}
	

}
