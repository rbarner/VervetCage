package functionalAnalysisScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class COGMember
{
	private String COG;
	private String taxonID;
	private String proteinName;
	private String description;
	private List<Character> funcCode = new ArrayList<Character>();
	private List<String> functBroad;
	private List<String> functNarrow;
	
	/******************************
	 * Constructors                
	 * @throws IOException *
	 ******************************/
	public COGMember(String aLine) throws IOException
	{
		this.COG = aLine.split("\t")[5];
		this.description = aLine.split("\t")[6];
		this.proteinName = aLine.split("\t")[4];
		char[] temp = aLine.split("\t")[3].toCharArray();
		for(char c : temp)
			this.funcCode.add(c);
	}

	public String getCOG()
	{
		return COG;
	}

	public String getTaxonID()
	{
		return taxonID;
	}

	public String getProteinName()
	{
		return proteinName;
	}

	public String getDescription()
	{
		return description;
	}

	public List<Character> getFuncCode()
	{
		return funcCode;
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
