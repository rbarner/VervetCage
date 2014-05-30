package functionalAnalysisScripts;

public class Mbgd
{
	private String cluster;
	private String homCluster;
	private String gene;
	private String mbgd;
	private String cog;
	private String kegg;
	private String tigr;
	

	/******************************
	 * Constructors                *
	 ******************************/ 
	public Mbgd(String aLine)
	{
		this.cluster = aLine.split("\t")[0];
		this.homCluster = aLine.split("\t")[1];
		this.gene = aLine.split("\t")[2];
		this.mbgd = aLine.split("\t")[3];
		this.cog = aLine.split("\t")[4];
		this.kegg = aLine.split("\t")[5];
		this.tigr = aLine.split("\t")[6];
	}

	/******************************
	 * Getters                    *
	 ******************************/
	public String getCluster()
	{
		return cluster;
	}

	public String getHomCluster()
	{
		return homCluster;
	}

	public String getGene()
	{
		return gene;
	}

	public String getMbgd()
	{
		return mbgd;
	}

	public String getCog()
	{
		return cog;
	}

	public String getKegg()
	{
		return kegg;
	}

	public String getTigr()
	{
		return tigr;
	}
}
