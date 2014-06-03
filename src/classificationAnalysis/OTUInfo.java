package classificationAnalysis;

import java.util.ArrayList;
import java.util.List;

public class OTUInfo implements Comparable<OTUInfo>
{
	private String ID;
	private String Kingdom = null;
	private String Phylum = null;
	private String Class1 = null;
	private String Order = null;
	private String Family = null;
	private String Genus = null;
	private String Species = null;
	private List<Integer> Frequencies = new ArrayList<Integer>();
	private List<Double> logFrequencies = new ArrayList<Double>();
	private int numSequences = 0;
	private double pValue =1;
	private double adjPvalue=1;
	private boolean significant;
	
	@Override
	public int compareTo(OTUInfo o)
	{
		return Double.compare(this.pValue, o.pValue);
	}
	
	/******************************
	 * Constructors                *
	 ******************************/ 
	public OTUInfo(String aLine, int samples)
	{
		//place holders for your variables
		if(!aLine.startsWith("None"))
		{
			if(aLine.split("\t")[0].split(";").length>1)
			{
				if((aLine.split("\t")[0].split(";")[1].split("__")).length>1)
					this.Kingdom = aLine.split("\t")[0].split(";")[1].split("__")[1];
				if((aLine.split("\t")[0].split(";")).length>2)
					if((aLine.split("\t")[0].split(";")[2].split("__")).length>1)
						this.Phylum = aLine.split("\t")[0].split(";")[2].split("__")[1];
				if((aLine.split("\t")[0].split(";")).length>3)
					if((aLine.split("\t")[0].split(";")[3].split("__")).length>1)
						this.Class1 = aLine.split("\t")[0].split(";")[3].split("__")[1];
				if((aLine.split("\t")[0].split(";")).length>4)
					if((aLine.split("\t")[0].split(";")[4].split("__")).length>1)
						this.Order = aLine.split("\t")[0].split(";")[4].split("__")[1];
				if((aLine.split("\t")[0].split(";")).length>5)
					if((aLine.split("\t")[0].split(";")[5].split("__")).length>1)
						this.Family = aLine.split("\t")[0].split(";")[5].split("__")[1];
				if((aLine.split("\t")[0].split(";")).length>6)
					if((aLine.split("\t")[0].split(";")[6].split("__")).length>1)
						this.Genus = aLine.split("\t")[0].split(";")[6].split("_")[1];
				//this.Species = aLine.split("\t")[0].split(";")[7].split("_")[1];
			}
		}
		this.ID = aLine.split("\t")[1];
		for(int i=0; i<samples; i++)
		{
			this.numSequences = this.numSequences + Integer.parseInt(aLine.split("\t")[i+3]);
			this.Frequencies.add(Integer.parseInt(aLine.split("\t")[i+3]));
		}
	}
	
	public OTUInfo(String Kingdom, String Phylum, String Class1, String Order, String Family, List<Integer> Frequencies)
	{
		this.ID = Family;
		this.Kingdom = Kingdom;
		this.Phylum = Phylum;
		this.Class1 = Class1;
		this.Order = Order;
		this.Family = Family;
		this.Frequencies= Frequencies;
		for(int i=0; i<Frequencies.size(); i++)
		{
			this.numSequences = this.numSequences + Frequencies.get(i);
		}
	}

	public OTUInfo(String Kingdom, String Phylum, List<Integer> Frequencies)
	{
		this.ID = Phylum;
		this.Kingdom = Kingdom;
		this.Phylum = Phylum;
		this.Frequencies= Frequencies;
		for(int i=0; i<Frequencies.size(); i++)
		{
			this.numSequences = this.numSequences + Frequencies.get(i);
		}
	}
	
	/******************************
	 * Getters and Setters        *
	 ******************************/

	public List<Integer> getFrequencies() 
	{
		return Frequencies;
	}

	public String getID() 
	{
		return ID;
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


	public String getSpecies() 
	{
		return Species;
	}
	
	public int getNumSequences() 
	{
		return numSequences;
	}
	
	public List<Double> getLogFrequencies()
	{
		return logFrequencies;
	}

	public void setLogFrequencies(List<Double> logFrequencies)
	{
		this.logFrequencies = logFrequencies;
	}

	public double getPvalue()
	{
		return pValue;
	}

	public void setPvalue(double pValue)
	{
		this.pValue = pValue;
	}

	public double getAdjPvalue()
	{
		return adjPvalue;
	}

	public void setAdjPvalue(double adjPvalue)
	{
		this.adjPvalue = adjPvalue;
	}

	public boolean isSignificant()
	{
		return significant;
	}

	public void setSignificant(boolean significant)
	{
		this.significant = significant;
	}
}
