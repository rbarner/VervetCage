package scripts;

public class SampleInfo 
{
	private String ID = null;
	private String Niche = null;
	private String Genotype = null;
	private String Cage = null;
	private String Litter = null;
	private String Parent = null;
	private Integer richness = 0;
	private Double evenness  = 0.0;
	private Double shannonDiversity = 0.0;
	private Integer numSequences = 0;
	
	
	/******************************
	 * Constructor                *
	 ******************************/ 
	public SampleInfo(String aLine)
	{
		this.ID = aLine.split("\t")[0];
		if(aLine.split("\t").length>1)
			this.Niche = aLine.split("\t")[1];
		if(aLine.split("\t").length>2)
			this.Genotype = aLine.split("\t")[2];
		if(aLine.split("\t").length>3)
			this.Cage = aLine.split("\t")[3];
		if(aLine.split("\t").length>4)
			this.Litter = aLine.split("\t")[4];
		if(aLine.split("\t").length>5)
			this.Parent = aLine.split("\t")[5];
	}
	
	
	/******************************
	 * Methods                    *
	 ******************************/

	public String getID() 
	{
		return ID;
	}

	public String getNiche()
	{
		return Niche;
	}

	public String getGenotype()
	{
		return Genotype;
	}

	public String getCage() 
	{
		return Cage;
	}

	public String getLitter()
	{
		return Litter;
	}

	public String getParent() 
	{
		return Parent;
	}

	public Integer getRichness()
	{
		return richness;
	}

	public void addRichness()
	{
		this.richness=getRichness()+1;
	}

	public Double getEvenness()
	{
		return evenness;
	}

	public void setEvenness(Integer numOTUs)
	{
		this.evenness=getShannonDiversity()/(Math.log((double)numOTUs));
	}
	
	public Double getShannonDiversity()
	{
		return shannonDiversity;
	}

	public void setShannonDiversity(double shannonDiversity)
	{
		this.shannonDiversity=shannonDiversity;
	}
	
	public Integer getNumSequences()
	{
		return numSequences;
	}

	public void addNumSequences(Integer numSequences)
	{
		this.numSequences = getNumSequences()+numSequences;
	}
}
