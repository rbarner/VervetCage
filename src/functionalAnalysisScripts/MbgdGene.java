package functionalAnalysisScripts;

public class MbgdGene
{
	private String speciesID;
	private String geneName;
	private String gene;
	private String startPos;
	private String endPos;
	private String strand;
	private String location;
	private String type;
	private String transTable;
	private String codonStart;
	private String protID;
	private String gi;
	private String geneID;
	private String description;
	private String aminoAcidLength;
	private String chrmName;

	/******************************
	 * Constructors                *
	 ******************************/ 
	public MbgdGene(String aLine)
	{
		this.speciesID = aLine.split("\t")[0];
		this.geneName = aLine.split("\t")[1];
		this.gene = aLine.split("\t")[2];
		this.startPos = aLine.split("\t")[3];
		this.endPos = aLine.split("\t")[4];
		this.strand = aLine.split("\t")[5];
		this.location = aLine.split("\t")[6];
		this.type = aLine.split("\t")[7];
		this.transTable = aLine.split("\t")[8];
		this.codonStart = aLine.split("\t")[9];
		this.protID = aLine.split("\t")[10];
		this.gi = aLine.split("\t")[11];
		this.geneID = aLine.split("\t")[12];
		this.description = aLine.split("\t")[13];
		this.aminoAcidLength = aLine.split("\t")[14];
		this.chrmName = aLine.split("\t")[15];
		
		
	}

	public String getSpeciesID()
	{
		return speciesID;
	}

	public String getGeneName()
	{
		return geneName;
	}

	public String getGene()
	{
		return gene;
	}

	public String getStartPos()
	{
		return startPos;
	}

	public String getEndPos()
	{
		return endPos;
	}

	public String getStrand()
	{
		return strand;
	}

	public String getLocation()
	{
		return location;
	}

	public String getType()
	{
		return type;
	}

	public String getTransTable()
	{
		return transTable;
	}

	public String getCodonStart()
	{
		return codonStart;
	}

	public String getProtID()
	{
		return protID;
	}

	public String getGi()
	{
		return gi;
	}

	public String getGeneID()
	{
		return geneID;
	}

	public String getDescription()
	{
		return description;
	}

	public String getAminoAcidLength()
	{
		return aminoAcidLength;
	}

	public String getChrmName()
	{
		return chrmName;
	}
	
}
