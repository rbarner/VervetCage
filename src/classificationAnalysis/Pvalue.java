package classificationAnalysis;

public class Pvalue
{
	private String name;
	private double pValue;

	/******************************
	 * Constructors                *
	 ******************************/ 
	public Pvalue(String aLine)
	{
		this.name=aLine.split("\t")[0];
		this.pValue = Double.parseDouble(aLine.split("\t")[1]);
	}

	/******************************
	 * Getters                    *
	 ******************************/
	public String getName()
	{
		return name;
	}

	public double getpValue()
	{
		return pValue;
	}
	
}
