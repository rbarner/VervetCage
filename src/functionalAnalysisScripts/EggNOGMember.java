package functionalAnalysisScripts;

import java.io.IOException;
import java.util.List;

public class EggNOGMember
{
	private String eggNOG;
	private String taxonID;
	private String proteinName;
	private String fullProteinName;
	private int start;
	private int end;
	private String description = "";
	private List<Character> funcCode;
	private List<String> functBroad;
	private List<String> functNarrow;
	
	/******************************
	 * Constructors                
	 * @throws IOException *
	 ******************************/
	public EggNOGMember(String aLine) throws IOException
	{
		this.eggNOG = aLine.split("\t")[0];
		this.taxonID = aLine.split("\t")[1].split("\\.")[0];
		this.proteinName = aLine.split("\t")[1].split("\\.")[1];
		this.fullProteinName = aLine.split("\t")[1];
		this.start = Integer.parseInt(aLine.split("\t")[2]);
		this.end = Integer.parseInt(aLine.split("\t")[3]);
	}

	public String getEggNOG()
	{
		return eggNOG;
	}

	public String getTaxonID()
	{
		return taxonID;
	}

	public String getProteinName()
	{
		return proteinName;
	}

	public String getFullProteinName()
	{
		return fullProteinName;
	}

	public int getStart()
	{
		return start;
	}

	public int getEnd()
	{
		return end;
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
