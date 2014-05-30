package functionalAnalysisScripts;

import java.util.ArrayList;
import java.util.List;




public class FunctionCountsObject implements Comparable<FunctionCountsObject>
{
	private String name;
	private List<Long> counts = new ArrayList<Long>();
	private long total= 0;
	private List<Double> abundances = new ArrayList<Double>();
	private List<Double> logAbundances = new ArrayList<Double>();
	private double pValue =1;
	private double adjPvalue=1;
	private boolean significant;
	
	@Override
	public int compareTo(FunctionCountsObject o)
	{
		return Double.compare(this.pValue, o.pValue);
	}
	/**
	@Override
	public int compareTo(FunctionCountsObject o)
	{
		return this.name.compareTo(o.name);
	}
	**/
	/******************************
	 * Constructors                *
	 ******************************/ 
	public FunctionCountsObject(String aLine, int samples)
	{
		this.name = aLine.split("\t")[0];
		long tempTotal = 0;
		for(int i=0; i<samples; i++)
		{
			//System.out.print(aLine.split("\t")[i+1] + "\t");
			this.counts.add(Long.parseLong(aLine.split("\t")[i+1]));
			tempTotal = tempTotal+Long.parseLong(aLine.split("\t")[i+1]);
		}
		this.total = tempTotal;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<Long> getCounts()
	{
		return counts;
	}
	
	public List<Double> getAbundances()
	{
		return abundances;
	}

	public void setAbundances(List<Double> abundances)
	{
		this.abundances = abundances;
	}

	public long getTotal()
	{
		return total;
	}
	
	public List<Double> getLogAbundances()
	{
		return logAbundances;
	}

	public void setLogAbundances(List<Double> logAbundances)
	{
		this.logAbundances = logAbundances;
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
	
	public void setSignificant(boolean significant)
	{
		this.significant = significant;
	}
	
	public boolean isSignificant()
	{
		return significant;
	}

}
