package functionalAnalysisScripts;

public class SixteenSCopyCount
{
	private String strainName;
	private int copyCount;
	
	public SixteenSCopyCount(String aLine) throws Exception
	{
		this.strainName = aLine.split("\t")[0].toLowerCase().trim();
		this.copyCount = Integer.parseInt(aLine.split("\t")[6]);
	}

	public String getStrainName()
	{
		return strainName;
	}
	
	public int getCopyCount()
	{
		return copyCount;
	}

	
}
