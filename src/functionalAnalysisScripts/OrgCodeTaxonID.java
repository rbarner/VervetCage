package functionalAnalysisScripts;

public class OrgCodeTaxonID
{
	private String orgCode;
	private String taxonID;
	private String strainName;
	
	public OrgCodeTaxonID(String aLine) throws Exception
	{
		this.taxonID = aLine.split("TAX:")[1].split("\\]")[0];
		this.orgCode = aLine.split("GN:")[1].split("\\]")[0];
		this.strainName = aLine.split("\\[")[0].trim();
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public String getTaxonID()
	{
		return taxonID;
	}
	
	public String getStrainName()
	{
		return strainName;
	}

	
}
