package scripts;

	/**
	 * Created By: Roshonda Barner
	 * On: Oct 1,2012
	 * Last Modified: Oct 12, 2012
	 * 
	 * Purpose: Parse excel worksheet containing frequency of bacteria in a set of samples.
	 * Input: otuFilepath - String name of tab deliminated file containing bacteria frequency
	 * 		  sampleFilepath - String name of tab deliminated file describing the samples
	 * 		  niche - String describing where the samples were taken.
	 */

	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.HashMap;
import java.util.List;

	public class RdpParser 
	{
		
		//private List<RdpOTUinfo> OTUlist;
		private List<KylieSampleInfo> SampleList;
		private Integer numOTUs= 0;
		private List<RdpOTUinfoCopy> OTUlist;
		
		/******************************
		 * Constructor                *
		 ******************************/
		public RdpParser(String otuFilepath, String sampleFilepath, String niche) throws Exception
		{
			this.OTUlist = readOTUCopyFile(otuFilepath);
			this.SampleList = readSampleFile(sampleFilepath, niche);
		}
		
		public RdpParser(String otuFilepath, String sampleFilepath) throws Exception
		{
			this.OTUlist = readOTUCopyFile(otuFilepath);
			this.SampleList = readSampleFile(sampleFilepath); 
		}
		
		/******************************
		 * Getters                    *
		 ******************************/
		/**
		public List<RdpOTUinfo> getOTUlist()
		{
			return this.OTUlist;
		}
		**/
		
		public List<RdpOTUinfoCopy> getOTUList()
		{
			return this.OTUlist;
		}
		
		public List<KylieSampleInfo> getSamplelist()
		{
			return SampleList;
		}
		
		public Integer getNumOTUs()
		{
			return numOTUs;
		}

		public void addNumOTUs()
		{
			numOTUs = numOTUs +1;
		}
		
		
		/******************************
		 * Helper Methods             *
		 ******************************/

		/****    Read in files *******/
		public List<RdpOTUinfo> readOTUFile(String filepath) throws Exception
		{
			List<RdpOTUinfo> list = new ArrayList<RdpOTUinfo>();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			int samples = ((reader.readLine().split("\t")).length)-2;
			String nextLine = reader.readLine();
			while(nextLine != null)
			{
				RdpOTUinfo otu = new RdpOTUinfo(nextLine,samples);
				list.add(otu);
				nextLine = reader.readLine();
				this.addNumOTUs();
			}
			reader.close();
			return list;	
		}
		
		public List<RdpOTUinfoCopy> readOTUCopyFile(String filepath) throws Exception
		{
			List<RdpOTUinfoCopy> list = new ArrayList<RdpOTUinfoCopy>();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			int samples = ((reader.readLine().split("\t")).length)-1;
			String nextLine = reader.readLine();
			while(nextLine != null)
			{
				RdpOTUinfoCopy otu = new RdpOTUinfoCopy(nextLine,samples);
				list.add(otu);
				nextLine = reader.readLine();
				this.addNumOTUs();
			}
			reader.close();
			return list;	
		}
		
		public static List<KylieSampleInfo> readSampleFile(String filepath, String niche) throws Exception
		{
			List<KylieSampleInfo> list = new ArrayList<KylieSampleInfo>();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			reader.readLine();
			String nextLine = reader.readLine();
			while(nextLine != null)
			{
				if(nextLine.split("\t")[1].trim().equals(niche))
				{
					KylieSampleInfo sample = new KylieSampleInfo(nextLine);
					list.add(sample);
					nextLine = reader.readLine();
				}
				else
				{
					nextLine = reader.readLine();
				}
			}
			reader.close();
			return list;	
		}
		
		public static List<KylieSampleInfo> readSampleFile(String filepath) throws Exception
		{
			List<KylieSampleInfo> list = new ArrayList<KylieSampleInfo>();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			reader.readLine();
			String nextLine = reader.readLine();
			while(nextLine != null)
			{
				KylieSampleInfo sample = new KylieSampleInfo(nextLine);
				list.add(sample);
				nextLine = reader.readLine();
			}
			reader.close();
			return list;	
		}
		
		/**** Filter out OTUs that occur in less than 25% of samples ****/
		public static List<RdpOTUinfoCopy> filterOTUlist(List<RdpOTUinfoCopy> OTUList)
		{
			List<RdpOTUinfoCopy> filteredList = new ArrayList<RdpOTUinfoCopy>();
			
			for(RdpOTUinfoCopy otu : OTUList)
			{
				int greaterZero = 0;
				for(double freq : otu.getAbundances())
				{
					if(freq > 0.0)
						greaterZero++;
				}
				if(((double)greaterZero/(double)otu.getAbundances().size()) >= 0.25)
					filteredList.add(otu);
			}
			return filteredList;
		}

		/**** Set log base 10 transform of frequencies ****/
		public void setLogTransformAbundances(List<RdpOTUinfoCopy> OTUlist)
		{
			List<Double> sampleSequences = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
			double totalSequences = 0.0;
			double avgSeqPerSample = 0.0;
			
			for(RdpOTUinfoCopy met : OTUlist)
			{
				for(int i=0; i<sampleSequences.size(); i++)
				{
					sampleSequences.set(i, sampleSequences.get(i)+met.getAbundances().get(i));
					totalSequences = totalSequences + met.getAbundances().get(i);
				}
			}
			avgSeqPerSample = (double)totalSequences/(double)sampleSequences.size();
			for(RdpOTUinfoCopy met : OTUlist)
			{
				List<Double> logAbundances = new ArrayList<Double>();
				for(int i=0; i<sampleSequences.size(); i++)
				{
					double toBeLogged = ((double)met.getAbundances().get(i)/(double)sampleSequences.get(i))*avgSeqPerSample +1.0;
					logAbundances.add(Math.log10(toBeLogged));
				}
				met.setLogAbundances(logAbundances);
			}
		}
			
		
		/**** Set sample evenness ****/
		public void setEvenness(List<KylieSampleInfo> sampleList)
		{	
			for(KylieSampleInfo sample : sampleList)
			{
				if(numOTUs !=0)
					sample.setEvenness(numOTUs);
			}
		}
		
		
		/****    Cluster OTUs *******/
		/**
		public List<RdpOTUinfo> clusterKingdomOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> kingdomMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> kingdomList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! kingdomMap.containsKey(otu.getKingdom()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					kingdomMap.put(otu.getKingdom(), newOTUs);
				}
				else
				{
					kingdomMap.get(otu.getKingdom()).add(otu);
				}
			}
			for(String king: kingdomMap.keySet())
			{
				String Kingdom = kingdomMap.get(king).get(0).getKingdom();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<kingdomMap.get(king).size(); i++)
				{
					for(int j=0; j<kingdomMap.get(king).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + kingdomMap.get(king).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo famOtu = new RdpOTUinfo(Kingdom, abundances);
				kingdomList.add(famOtu);
			}
			return kingdomList;
		}
		
		public List<RdpOTUinfo> clusterPhyloOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> phyloMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> phyList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! phyloMap.containsKey(otu.getKingdom()+"_"+otu.getPhylum()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					phyloMap.put(otu.getKingdom()+"_"+otu.getPhylum(), newOTUs);
				}
				else
				{
					phyloMap.get(otu.getKingdom()+"_"+otu.getPhylum()).add(otu);
				}
			}
			for(String phy: phyloMap.keySet())
			{
				String Kingdom = phyloMap.get(phy).get(0).getKingdom();
				String Phylo = phyloMap.get(phy).get(0).getPhylum();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<phyloMap.get(phy).size(); i++)
				{
					for(int j=0; j<phyloMap.get(phy).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + phyloMap.get(phy).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo famOtu = new RdpOTUinfo(Kingdom, Phylo, abundances);
				phyList.add(famOtu);
			}
			return phyList;
		}
		
		public List<RdpOTUinfo> clusterClassOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> classMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> classList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! classMap.containsKey(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					classMap.put(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1(), newOTUs);
				}
				else
				{
					classMap.get(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()).add(otu);
				}
			}
			for(String class1: classMap.keySet())
			{
				String Kingdom = classMap.get(class1).get(0).getKingdom();
				String Phylo = classMap.get(class1).get(0).getPhylum();
				String Class1 = classMap.get(class1).get(0).getClass1();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<classMap.get(class1).size(); i++)
				{
					for(int j=0; j<classMap.get(class1).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + classMap.get(class1).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo classOtu = new RdpOTUinfo(Kingdom, Phylo,Class1, abundances);
				classList.add(classOtu);
			}
			return classList;
		}
		
		public List<RdpOTUinfo> clusterOrderOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> orderMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> orderList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! orderMap.containsKey(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					orderMap.put(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder(), newOTUs);
				}
				else
				{
					orderMap.get(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()).add(otu);
				}
			}
			for(String order: orderMap.keySet())
			{
				String Kingdom = orderMap.get(order).get(0).getKingdom();
				String Phylo = orderMap.get(order).get(0).getPhylum();
				String Class1 = orderMap.get(order).get(0).getClass1();
				String Order = orderMap.get(order).get(0).getOrder();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<orderMap.get(order).size(); i++)
				{
					for(int j=0; j<orderMap.get(order).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + orderMap.get(order).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo orderOtu = new RdpOTUinfo(Kingdom, Phylo,Class1, Order, abundances);
				orderList.add(orderOtu);
			}
			return orderList;
		}
		
		public List<RdpOTUinfo> clusterFamilyOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> familyMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> famList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! familyMap.containsKey(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					familyMap.put(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily(), newOTUs);
				}
				else
				{
					familyMap.get(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()).add(otu);
				}
			
			}
			for(String fam: familyMap.keySet())
			{
				String Kingdom = familyMap.get(fam).get(0).getKingdom();
				String Phylum = familyMap.get(fam).get(0).getPhylum();
				String Class1 = familyMap.get(fam).get(0).getClass1();
				String Order = familyMap.get(fam).get(0).getOrder();
				String Family = familyMap.get(fam).get(0).getFamily();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<familyMap.get(fam).size(); i++)
				{
					for(int j=0; j<familyMap.get(fam).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + familyMap.get(fam).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo famOtu = new RdpOTUinfo(Kingdom, Phylum,Class1, Order, Family, abundances);
				famList.add(famOtu);
			}
			return famList;
		}
		
		public List<RdpOTUinfo> clusterGenusOTUs(List<RdpOTUinfo> OTUList)
		{
			HashMap<String, ArrayList<RdpOTUinfo>> genusMap = new HashMap<String, ArrayList<RdpOTUinfo>>();
			List<RdpOTUinfo> genusList = new ArrayList<RdpOTUinfo>();
			for(RdpOTUinfo otu : OTUList)
			{
				if(! genusMap.containsKey(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus()))
				{
					ArrayList<RdpOTUinfo> newOTUs = new ArrayList<RdpOTUinfo>();
					newOTUs.add(otu);
					genusMap.put(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus(), newOTUs);
				}
				else
				{
					genusMap.get(otu.getKingdom()+"_"+otu.getPhylum()+"_"+otu.getClass1()+"_"+otu.getOrder()+"_"+otu.getFamily()+"_"+otu.getGenus()).add(otu);
				}
			}
			for(String gen: genusMap.keySet())
			{
				String Kingdom = genusMap.get(gen).get(0).getKingdom();
				String Phylum = genusMap.get(gen).get(0).getPhylum();
				String Class1 = genusMap.get(gen).get(0).getClass1();
				String Order = genusMap.get(gen).get(0).getOrder();
				String Family = genusMap.get(gen).get(0).getFamily();
				String Genus = genusMap.get(gen).get(0).getGenus();
				List<Double> abundances = new ArrayList<Double>(Collections.nCopies(getSamplelist().size(), 0.0));
				for(int i=0; i<genusMap.get(gen).size(); i++)
				{
					for(int j=0; j<genusMap.get(gen).get(i).getAbundances().size();j++)
					{
						abundances.set(j, (abundances.get(j) + genusMap.get(gen).get(i).getAbundances().get(j)));
					}
				}
				RdpOTUinfo genOtu = new RdpOTUinfo(Kingdom, Phylum,Class1, Order, Family, Genus, abundances);
				genusList.add(genOtu);
			}
			return genusList;
		}
		**/
		/****    Write Files *******/	
		/**
		public void writeOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(OTUList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: OTUList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				writer.write(";c__"); writer2.write(";c__");
				if(o.getClass1() != null)
				{
					writer.write(o.getClass1()); writer2.write(o.getClass1());
				}
				writer.write(";o__"); writer2.write(";o__");
				if(o.getOrder() != null)
				{
					writer.write(o.getOrder()); writer2.write(o.getOrder());
				}
				writer.write(";f__"); writer2.write(";f__");
				if(o.getFamily() != null)
				{
					writer.write(o.getFamily()); writer2.write(o.getFamily());
				}
				writer.write(";g__"); writer2.write(";g__");
				if(o.getGenus() != null)
				{
					writer.write(o.getGenus()); writer2.write(o.getGenus());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		**/
		public void writeOTUs(List<RdpOTUinfoCopy> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(OTUList);
			//writer.write("\t"); writer2.write("\t");
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+"Sample"+s.getNum()); writer2.write("\t"+"Sample"+s.getNum());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfoCopy o: OTUList)
			{
				writer.write(o.getID()) ;writer2.write(o.getID());
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		/**
		public void writeKingdomOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> kingdomList = clusterKingdomOTUs(OTUList);
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(kingdomList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: kingdomList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		
		public void writePhyloOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> phylumList = clusterPhyloOTUs(filterOTUlist(OTUList));
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(phylumList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: phylumList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");			
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		
		public void writeClassOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> classList = clusterClassOTUs(OTUList);
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(classList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: classList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				writer.write(";c__"); writer2.write(";c__");
				if(o.getClass1() != null)
				{
					writer.write(o.getClass1()); writer2.write(o.getClass1());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		
		public void writeOrderOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> orderList = clusterOrderOTUs(OTUList);
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(orderList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: orderList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				writer.write(";c__"); writer2.write(";c__");
				if(o.getClass1() != null)
				{
					writer.write(o.getClass1()); writer2.write(o.getClass1());
				}
				writer.write(";o__"); writer2.write(";o__");
				if(o.getOrder() != null)
				{
					writer.write(o.getOrder()); writer2.write(o.getOrder());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();

		}
		
		public void writeFamilyOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> familyList = clusterFamilyOTUs(filterOTUlist(OTUList));
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(familyList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: familyList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				writer.write(";c__"); writer2.write(";c__");
				if(o.getClass1() != null)
				{
					writer.write(o.getClass1()); writer2.write(o.getClass1());
				}
				writer.write(";o__"); writer2.write(";o__");
				if(o.getOrder() != null)
				{
					writer.write(o.getOrder()); writer2.write(o.getOrder());
				}
				writer.write(";f__"); writer2.write(";f__");
				if(o.getFamily() != null)
				{
					writer.write(o.getFamily()); writer2.write(o.getFamily());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		
		public void writeGenusOTUs(List<RdpOTUinfo> OTUList, String rawFilepath, String logFilepath) throws IOException
		{
			List<RdpOTUinfo> genusList = clusterGenusOTUs(OTUList);
			//List<RdpOTUinfo> filteredList = filterOTUlist(OTUList);
			BufferedWriter writer= new BufferedWriter(new FileWriter(rawFilepath));
			BufferedWriter writer2= new BufferedWriter(new FileWriter(logFilepath));
			setLogTransformAbundances(genusList);
			for(KylieSampleInfo s : this.getSamplelist())
			{
				writer.write("\t"+s.getDiet()); writer2.write("\t"+s.getDiet());
			}
			writer.write("\n"); writer2.write("\n");
			for(RdpOTUinfo o: genusList)
			{
				writer.write("k__"); writer2.write("k__");
				if(o.getKingdom() != null)
				{
					writer.write(o.getKingdom()) ;writer2.write(o.getKingdom());
				}
				writer.write(";p__"); writer2.write(";p__");
				if(o.getPhylum() != null)
				{
					writer.write(o.getPhylum()); writer2.write(o.getPhylum());
				}
				writer.write(";c__"); writer2.write(";c__");
				if(o.getClass1() != null)
				{
					writer.write(o.getClass1()); writer2.write(o.getClass1());
				}
				writer.write(";o__"); writer2.write(";o__");
				if(o.getOrder() != null)
				{
					writer.write(o.getOrder()); writer2.write(o.getOrder());
				}
				writer.write(";f__"); writer2.write(";f__");
				if(o.getFamily() != null)
				{
					writer.write(o.getFamily()); writer2.write(o.getFamily());
				}
				writer.write(";g__"); writer2.write(";g__");
				if(o.getGenus() != null)
				{
					writer.write(o.getGenus()); writer2.write(o.getGenus());
				}
				for(int i = 0; i<o.getAbundances().size(); i++)
				{
					writer.write("\t"); writer2.write("\t"); 
					writer.write("" + o.getAbundances().get(i)); writer2.write("" + o.getLogAbundances().get(i));
				}
				writer.write("\n"); writer2.write("\n");
			}
			writer.close(); writer2.close();
		}
		**/
		
		/******************************
		 * Main Method                *
		 ******************************/
		public static void main(String[] args) throws Exception 
		{
			
			RdpParser gOTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_gen_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
			System.out.println("It's working!");
			List<RdpOTUinfoCopy> gotuList = gOTUs.getOTUList();
			//List<RdpOTUinfoCopy> gfilteredList = gOTUs.filterOTUlist(gotuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			//gOTUs.writeOTUs(gfilteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/GenusLevelFilteredPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/GenusLevelFilteredLoggedPivot.txt");
			gOTUs.writeOTUs(gotuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/GenusLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/GenusLevelLoggedPivot.txt");
			
			RdpParser fOTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_fam_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
			System.out.println("It's working!");
			List<RdpOTUinfoCopy> fotuList = fOTUs.getOTUList();
			//List<RdpOTUinfoCopy> ffilteredList = fOTUs.filterOTUlist(fotuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			//fOTUs.writeOTUs(ffilteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/FamilyLevelFilteredPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/FamilyLevelFilteredLoggedPivot.txt");
			fOTUs.writeOTUs(fotuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/FamilyLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/FamilyLevelLoggedPivot.txt");
			
			RdpParser oOTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_ord_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
			
			System.out.println("It's working!");
			List<RdpOTUinfoCopy> ootuList = oOTUs.getOTUList();
			//List<RdpOTUinfoCopy> ofilteredList = oOTUs.filterOTUlist(ootuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			//oOTUs.writeOTUs(ofilteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/OrderLevelFilteredPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/OrderLevelFilteredLoggedPivot.txt");
			oOTUs.writeOTUs(ootuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/OrderLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/OrderLevelLoggedPivot.txt");
			
			RdpParser cOTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_cls_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
			
			System.out.println("It's working!");
			List<RdpOTUinfoCopy> cotuList = cOTUs.getOTUList();
			//List<RdpOTUinfoCopy> cfilteredList = cOTUs.filterOTUlist(cotuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			//cOTUs.writeOTUs(cfilteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/ClassLevelFilteredPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/ClassLevelFilteredLoggedPivot.txt");
			cOTUs.writeOTUs(cotuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/ClassLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/ClassLevelLoggedPivot.txt");
			
			RdpParser pOTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/kylieVervet_raw_phy_transposed.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/vervet/2012-06-22_WholeGenomeKey.txt");
			
			System.out.println("It's working!");
			List<RdpOTUinfoCopy> potuList = pOTUs.getOTUList();
			//List<RdpOTUinfoCopy> pfilteredList = pOTUs.filterOTUlist(potuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			//pOTUs.writeOTUs(pfilteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/PhylumLevelFilteredPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/PhylumLevelFilteredLoggedPivot.txt");
			pOTUs.writeOTUs(potuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/PhylumLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/sixteenSseqs/transposed/orderedPivots/processedPivotTables/PhylumLevelLoggedPivot.txt");
			
			/**
			RdpParser OTUs = new RdpParser("/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/rdpClassifier/classifierFiles/kyliePivotTableRevised.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/2012-06-22_WholeGenomeKeyRevised.txt");
			
			System.out.println("It's working!");
			List<RdpOTUinfo> otuList = OTUs.getOTUlist();
			List<RdpOTUinfo> filteredList = OTUs.filterOTUlist(otuList);
			//List<KylieSampleInfo> sampleList = OTUs.getSamplelist();
			//OTUs.setEvenness(sampleList);
			OTUs.writeOTUs(filteredList, "/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/OTULevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedOTULevelPivot.txt");
			
			OTUs.writeKingdomOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/KingdomLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedKingdomLevelPivot.txt");
			OTUs.writePhyloOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/PhyloLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedPhyloLevelPivot.txt");
			OTUs.writeClassOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/ClassLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedClassLevelPivot.txt");
			OTUs.writeOrderOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/OrderLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedOrderLevelPivot.txt");
			OTUs.writeFamilyOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/FamilyLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedFamilyLevelPivot.txt");
			OTUs.writeGenusOTUs(filteredList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/GenusLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/filtered/revised/loggedGenusLevelPivot.txt");
			
			
			OTUs.writeOTUs(otuList, "/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/OTULevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/loggedOTULevelPivot.txt");
			
			OTUs.writeKingdomOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/KingdomLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedKingdomLevelPivot.txt");
			OTUs.writePhyloOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/PhyloLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedPhyloLevelPivot.txt");
			OTUs.writeClassOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/ClassLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedClassLevelPivot.txt");
			OTUs.writeOrderOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/OrderLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedOrderLevelPivot.txt");
			OTUs.writeFamilyOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/FamilyLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedFamilyLevelPivot.txt");
			OTUs.writeGenusOTUs(otuList,"/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/GenusLevelPivot.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/parsedData/rdpClassifier/all/revised/loggedGenusLevelPivot.txt");
			**/
			
		}

	}
