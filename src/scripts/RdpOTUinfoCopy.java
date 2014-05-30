package scripts;

import java.util.ArrayList;
import java.util.List;

public class RdpOTUinfoCopy implements Comparable<RdpOTUinfoCopy>
{
		private String ID;
		private List<Double> abundances = new ArrayList<Double>();
		private List<Double> logAbundances = new ArrayList<Double>();
		private double pValue =1;
		private double adjPvalue=1;
		private boolean significant;
		
		@Override
		public int compareTo(RdpOTUinfoCopy o)
		{
			return Double.compare(this.pValue, o.pValue);
		}
		
		/******************************
		 * Constructors                *
		 ******************************/ 
		public RdpOTUinfoCopy(String aLine)
		{
			this.ID = aLine.split("\t")[0];
		}
		public RdpOTUinfoCopy(String aLine, int samples)
		{
			this.ID = aLine.split("\t")[0];
			for(int i=0; i<samples; i++)
			{
				this.abundances.add(Double.parseDouble(aLine.split("\t")[i+1]));
			}
		}
		
		public RdpOTUinfoCopy(String Kingdom, String Phylum, String Class1, String Order, String Family, String Genus, List<Double> abundances)
		{
			this.ID =Kingdom+"_"+Phylum+"_"+Class1+"_"+Order+"_"+Family+"_"+Genus;
			this.abundances= abundances;
		}
		
		public RdpOTUinfoCopy(String Kingdom, String Phylum, String Class1, String Order, String Family, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1+"_"+Order+"_"+Family;
			this.abundances= abundances;
		}
		
		public RdpOTUinfoCopy(String Kingdom, String Phylum, String Class1, String Order, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1+"_"+Order;
			this.abundances= abundances;
		}
		
		public RdpOTUinfoCopy(String Kingdom, String Phylum, String Class1, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1;
			this.abundances= abundances;
		}
		
		public RdpOTUinfoCopy(String Kingdom, String Phylum, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum;
			this.abundances= abundances;
		}
		
		public RdpOTUinfoCopy(String Kingdom, List<Double> abundances)
		{
			this.ID = Kingdom;
			this.abundances= abundances;
		}
		/******************************
		 * Getters and Setters        *
		 ******************************/

		public List<Double> getAbundances()
		{
			return abundances;
		}

		public String getID() 
		{
			return ID;
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
