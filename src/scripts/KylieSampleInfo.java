package scripts;

public class KylieSampleInfo
{
	private String num = null;
	private String ID = null;
	private String date = null;
	private String diet = null;
	private String cage = null;
	private Double ageNum = null;
	private String ageCat = null;
	private Integer richness = 0;
	private Double evenness  = 0.0;
	private Double shannonDiversity = 0.0;
	
	
	/******************************
	 * Constructor                *
	 ******************************/ 
	public KylieSampleInfo(String aLine)
	{
		this.num = aLine.split("\t")[0];
		this.ID = aLine.split("\t")[1];
		this.date = aLine.split("\t")[2];
		this.diet = aLine.split("\t")[3];
		this.cage = aLine.split("\t")[4];
		this.ageNum = Double.parseDouble(aLine.split("\t")[5]);
		this.ageCat = aLine.split("\t")[6];
		
	}
	
	
	/******************************
	 * Methods                    *
	 ******************************/

	public String getID() 
	{
		return ID;
	}

	public String getNum()
	{
		return num;
	}

	public String getDate()
	{
		return date;
	}

	public String getDiet() 
	{
		return diet;
	}
	
	public String getCage() 
	{
		return cage;
	}
	
	public Double getAgeNum() 
	{
		return ageNum;
	}
	
	public String getAgeCat() 
	{
		return ageCat;
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
	
}
