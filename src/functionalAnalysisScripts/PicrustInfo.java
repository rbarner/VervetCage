package functionalAnalysisScripts;

import java.util.ArrayList;
import java.util.List;
import scripts.StringUtil;

public class PicrustInfo implements Comparable<PicrustInfo>
{
		private String name;
		private List<Double> frequencies = new ArrayList<Double>();
		private List<Double> abundances = new ArrayList<Double>();
		private List<Double> logAbundances = new ArrayList<Double>();
		private double pValue =1;
		private double adjPvalue=1;
		private boolean significant;
		
		@Override
		public int compareTo(PicrustInfo o)
		{
			return Double.compare(this.pValue, o.pValue);
		}
		
		/******************************
		 * Constructors                *
		 ******************************/ 
		public PicrustInfo(String aLine, int samples)
		{
			this.name = StringUtil.trimQuotes(aLine.split("\t")[0]);
			for(int i=0; i<samples; i++)
			{
				this.frequencies.add(Double.parseDouble(aLine.split("\t")[i+1]));
			}
		}

		/******************************
		 * Getters and Setters        *
		 ******************************/

		public String getName()
		{
			return name;
		}
		
		public List<Double> getFrequencies()
		{
			return frequencies;
		}
		
		public List<Double> getAbundances()
		{
			return abundances;
		}
		
		public void setAbundances(List<Double> abundances)
		{
			this.abundances = abundances;
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

		public boolean isSignificant()
		{
			return significant;
		}

		public void setSignificant(boolean significant)
		{
			this.significant = significant;
		}
}
