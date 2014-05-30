package functionalAnalysisScripts;

import java.io.IOException;

public class EggNOGSpecies
{
	private String taxonID;
	private String type;
	private String nameOfficial;
	private String nameCompact;
	private String nameNCBI;
	private String nameImported;
	private String numLoci;
	
	public EggNOGSpecies(String aLine) throws IOException
	{
		this.taxonID = aLine.split("\t")[0];
		this.type = aLine.split("\t")[1];
		this.nameOfficial = aLine.split("\t")[2];
		this.nameCompact = aLine.split("\t")[3];
		this.nameNCBI = aLine.split("\t")[4];
		this.nameImported = aLine.split("\t")[5];
		this.numLoci = aLine.split("\t")[6];
	}

	public String getTaxonID()
	{
		return taxonID;
	}

	public String getType()
	{
		return type;
	}

	public String getNameOfficial()
	{
		return nameOfficial;
	}

	public String getNameCompact()
	{
		return nameCompact;
	}

	public String getNameNCBI()
	{
		return nameNCBI;
	}

	public String getNameImported()
	{
		return nameImported;
	}

	public String getNumLoci()
	{
		return numLoci;
	}
	
	
	

}
