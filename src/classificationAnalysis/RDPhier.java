package classificationAnalysis;

public class RDPhier implements Comparable<RDPhier>
{
	private String taxID;
	private RDPLineage lineage;
	private String name;
	private String rank;
	private int count;
	
	@Override
	public int compareTo(RDPhier o)
	{
		return this.name.compareTo(o.name);
	}
	

	/******************************
	 * Constructors                *
	 ******************************/ 
	public RDPhier(String aLine)
	{
		if((aLine.split("\t")).length>4)
		{
			this.taxID = aLine.split("\t")[0];
			this.lineage = new RDPLineage(aLine.split("\t")[1]);
			this.name = aLine.split("\t")[2].replaceAll("^\"|\"$", "");
			System.out.println(aLine.split("\t")[3]);
			this.rank = aLine.split("\t")[3];
			this.count = Integer.parseInt(aLine.split("\t")[4]);
		}
		
	}

	public String getTaxID()
	{
		return taxID;
	}

	public RDPLineage getLineage()
	{
		return lineage;
	}

	public String getName()
	{
		return name;
	}

	public String getRank()
	{
		return rank;
	}

	public int getCount()
	{
		return count;
	}
	
}
