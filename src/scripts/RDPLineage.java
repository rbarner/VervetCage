package scripts;

public class RDPLineage
{
	private String Kingdom = null;
	private String Phylum = null;
	private String Class1 = null;
	private String Order = null;
	private String Family = null;
	private String Genus = null;
	
	/******************************
	 * Constructors                *
	 ******************************/ 
	public RDPLineage(String aLine)
	{
		if((aLine.split(";")).length>3)
			if(aLine.split(";")[3].startsWith("d"))
				this.Kingdom = aLine.split(";")[2];
		if((aLine.split(";")).length>5)
			if(aLine.split(";")[5].startsWith("p"))
				this.Phylum = aLine.split(";")[4];
		if((aLine.split(";")).length>7)
			if(aLine.split(";")[7].startsWith("c"))
				this.Class1 = aLine.split(";")[6];
		if((aLine.split(";")).length>9)
			if(aLine.split(";")[9].startsWith("o"))
				this.Order = aLine.split(";")[8];
		if((aLine.split(";")).length>11)
			if(aLine.split(";")[11].startsWith("f"))
				this.Family = aLine.split(";")[10];
		if((aLine.split(";")).length>13)
			if(aLine.split(";")[13].startsWith("g"))
				this.Genus = aLine.split(";")[12];
		
	}
	
	public String getKingdom() 
	{
		return Kingdom;
	}

	public String getPhylum() 
	{
		return Phylum;
	}

	public String getClass1() 
	{
		return Class1;
	}

	public String getOrder() 
	{
		return Order;
	}

	public String getFamily() 
	{
		return Family;
	}

	public String getGenus() 
	{
		return Genus;
	}
	
	
}
