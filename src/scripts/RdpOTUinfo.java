package scripts;

import java.util.ArrayList;
import java.util.List;

public class RdpOTUinfo implements Comparable<RdpOTUinfo>
{
		private String ID;
		private String Kingdom = null;
		private String Phylum = null;
		private String Class1 = null;
		private String Order = null;
		private String Family = null;
		private String Genus = null;
		private List<Double> abundances = new ArrayList<Double>();
		private List<Double> logAbundances = new ArrayList<Double>();
		private double pValue =1;
		private double adjPvalue=1;
		private boolean significant;
		
		@Override
		public int compareTo(RdpOTUinfo o)
		{
			return Double.compare(this.pValue, o.pValue);
		}
		
		/******************************
		 * Constructors                *
		 ******************************/ 
		public RdpOTUinfo(String aLine)
		{
			this.ID = aLine.split("\t")[0];
			if((aLine.split("\t")[1].split(";")).length>1)
				if(aLine.split("\t")[1].split(";")[1].startsWith("d"))
					this.Kingdom = aLine.split("\t")[1].split(";")[1].substring(2);
			if((aLine.split("\t")[1].split(";")).length>2)
				if(aLine.split("\t")[1].split(";")[2].startsWith("p"))
					this.Phylum = aLine.split("\t")[1].split(";")[2].substring(2);
			if((aLine.split("\t")[1].split(";")).length>3)
				if(aLine.split("\t")[1].split(";")[3].startsWith("c"))
					this.Class1 = aLine.split("\t")[1].split(";")[3].substring(2);
			if((aLine.split("\t")[1].split(";")).length>4)
				if(aLine.split("\t")[1].split(";")[4].startsWith("o"))
					this.Order = aLine.split("\t")[1].split(";")[4].substring(2);
			if((aLine.split("\t")[1].split(";")).length>5)
				if(aLine.split("\t")[1].split(";")[5].startsWith("f"))
					this.Family = aLine.split("\t")[1].split(";")[5].substring(2);
			if((aLine.split("\t")[1].split(";")).length>6)
				if(aLine.split("\t")[1].split(";")[6].startsWith("g"))
					this.Genus = aLine.split("\t")[1].split(";")[6].substring(2);
			
		}
		public RdpOTUinfo(String aLine, int samples)
		{
			this.ID = aLine.split("\t")[0];
			if((aLine.split("\t")[1].split(";")).length>1)
				if(aLine.split("\t")[1].split(";")[1].startsWith("d"))
					this.Kingdom = aLine.split("\t")[1].split(";")[1].substring(2);
			if((aLine.split("\t")[1].split(";")).length>2)
				if(aLine.split("\t")[1].split(";")[2].startsWith("p"))
					this.Phylum = aLine.split("\t")[1].split(";")[2].substring(2);
			if((aLine.split("\t")[1].split(";")).length>3)
				if(aLine.split("\t")[1].split(";")[3].startsWith("c"))
					this.Class1 = aLine.split("\t")[1].split(";")[3].substring(2);
			if((aLine.split("\t")[1].split(";")).length>4)
				if(aLine.split("\t")[1].split(";")[4].startsWith("o"))
					this.Order = aLine.split("\t")[1].split(";")[4].substring(2);
			if((aLine.split("\t")[1].split(";")).length>5)
				if(aLine.split("\t")[1].split(";")[5].startsWith("f"))
					this.Family = aLine.split("\t")[1].split(";")[5].substring(2);
			if((aLine.split("\t")[1].split(";")).length>6)
				if(aLine.split("\t")[1].split(";")[6].startsWith("g"))
					this.Genus = aLine.split("\t")[1].split(";")[6].substring(2);
			
			for(int i=0; i<samples; i++)
			{
				this.abundances.add(Double.parseDouble(aLine.split("\t")[i+2]));
			}
		}
		
		public RdpOTUinfo(String Kingdom, String Phylum, String Class1, String Order, String Family, String Genus, List<Double> abundances)
		{
			this.ID =Kingdom+"_"+Phylum+"_"+Class1+"_"+Order+"_"+Family+"_"+Genus;
			this.Kingdom = Kingdom;
			this.Phylum = Phylum;
			this.Class1 = Class1;
			this.Order = Order;
			this.Family = Family;
			this.Genus = Genus;
			this.abundances= abundances;
		}
		
		public RdpOTUinfo(String Kingdom, String Phylum, String Class1, String Order, String Family, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1+"_"+Order+"_"+Family;
			this.Kingdom = Kingdom;
			this.Phylum = Phylum;
			this.Class1 = Class1;
			this.Order = Order;
			this.Family = Family;
			this.abundances= abundances;
		}
		
		public RdpOTUinfo(String Kingdom, String Phylum, String Class1, String Order, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1+"_"+Order;
			this.Kingdom = Kingdom;
			this.Phylum = Phylum;
			this.Class1 = Class1;
			this.Order = Order;
			this.abundances= abundances;
		}
		
		public RdpOTUinfo(String Kingdom, String Phylum, String Class1, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum+"_"+Class1;
			this.Kingdom = Kingdom;
			this.Phylum = Phylum;
			this.Class1 = Class1;
			this.abundances= abundances;
		}
		
		public RdpOTUinfo(String Kingdom, String Phylum, List<Double> abundances)
		{
			this.ID = Kingdom+"_"+Phylum;
			this.Kingdom = Kingdom;
			this.Phylum = Phylum;
			this.abundances= abundances;
		}
		
		public RdpOTUinfo(String Kingdom, List<Double> abundances)
		{
			this.ID = Kingdom;
			this.Kingdom = Kingdom;
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
