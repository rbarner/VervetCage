package scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;	
import java.util.concurrent.LinkedBlockingQueue;

//import ashleeCombinedOTUs.FileManagerForV3V5_Version2;

public class NewRDPParserFileLine
{
	private String sequenceId;
	private Map<String, NewRDPNode> taxaMap;
	private static long startTime;
	
	public static final String DOMAIN = "domain";
	public static final String PHYLUM = "phylum";
	public static final String CLASS = "class";
	public static final String ORDER = "order";
	public static final String FAMILY = "family";
	public static final String GENUS = "genus";
	
	public void setSequenceId(String sequenceId)
	{
		this.sequenceId = sequenceId;
	}
	
	public static final String[] TAXA_ARRAY = 
	{
		DOMAIN, PHYLUM, CLASS, ORDER, FAMILY, GENUS
	};
	
	private NewRDPParserFileLine()
	{
		
	}
	
	private NewRDPParserFileLine(String fileLine) throws Exception
	{
		//System.out.println(fileLine);
		HashMap<String, NewRDPNode> tempMap = new LinkedHashMap<String, NewRDPNode>();
		StringTokenizer sToken = new StringTokenizer(fileLine, "\t");
		
		this.sequenceId = sToken.nextToken();
		
		while( sToken.hasMoreTokens())
		{
			String name = sToken.nextToken();
			
			if( name.trim().equals("-"))
				name= sToken.nextToken();
			
			String rank = sToken.nextToken();
			String scoreString = sToken.nextToken();
			//System.out.println(name + " " + rank + " " + scoreString);
			NewRDPNode rdpNode = new NewRDPNode(rank, name, scoreString);
			tempMap.put( rank, rdpNode );
		}
		
		taxaMap = Collections.unmodifiableMap(tempMap);
	}
	
	public static int getNumInCommon(String consensusString1, String consensusString2)
		throws Exception
	{
		StringTokenizer sToken1 = new StringTokenizer(consensusString1, ";");
		StringTokenizer sToken2 = new StringTokenizer(consensusString2, ";");
		int numToDo = Math.min(sToken1.countTokens(), sToken2.countTokens());
		
		int val =0 ;
		
		for( int x=0;x < numToDo; x++)
		{
			if( sToken1.nextToken().equals(sToken2.nextToken()) )
				val++;
			else
				return val;
		}
		
		return val;
	}
	
	public static String getConsensusString(List<NewRDPParserFileLine> list, float threshold) 
		throws Exception
	{
		return getConsensusString(list, threshold, list.size()/2);
	}
	
	public static String getConsensusString( List<NewRDPParserFileLine> list, float threshold,
			int minNumber)
	{
		StringBuffer buff = new StringBuffer();
		
		for( int x=0; x < TAXA_ARRAY.length; x++)
		{
			HashMap<String, Integer> countMap = new LinkedHashMap<String, Integer>();
			
			for( NewRDPParserFileLine fileLine : list )
			{
				NewRDPNode rdpNode = fileLine.getTaxaMap().get(TAXA_ARRAY[x]);
				
				if( rdpNode != null && rdpNode.getScore() >= threshold )
				{
					Integer i = countMap.get(rdpNode.getTaxaName());
					
					if( i == null)
						i =0;
					
					i++;
					
					countMap.put(rdpNode.getTaxaName(), i);
				}
			}
			
			boolean gotOne = false;
			
			for( Iterator<String> i = countMap.keySet().iterator(); i.hasNext() && ! gotOne ; )
			{
				String key  =i.next();
				if( countMap.get(key) >= minNumber )
				{
					gotOne = true;
					buff.append( TAXA_ARRAY[x] + ":" +   key + ";");
				}
			}
			
			if( ! gotOne)
				return stripTrailingSemicoling(buff.toString());
		}
			
		return stripTrailingSemicoling(buff.toString());
	}
	
	private static String stripTrailingSemicoling(String inString)
	{
		if( inString.length() == 0 )
			return ";";
		
		if(inString.charAt(inString.length()-1) == ';')
			inString = inString.substring(0, inString.length() -1);
		
		return inString;
	}
	
	public String getSequenceId()
	{
		return sequenceId;
	}
	
	public Map<String, NewRDPNode> getTaxaMap()
	{
		return taxaMap;
	}
	
	public static List<NewRDPParserFileLine> getRdpList(File rdpFile) throws Exception
	{
		int numCPUs = Runtime.getRuntime().availableProcessors();
		return getRdpList(rdpFile, numCPUs);
	}
	
	public static List<NewRDPParserFileLine> getRdpListSingleThread(String rdpFilePath)
		throws Exception
	{
		return getRdpListSingleThread(new File(rdpFilePath));
	}
	
	public static List<NewRDPParserFileLine> getRdpListSingleThread(File rdpFile) 
			throws Exception
	{
		List<NewRDPParserFileLine> list = new ArrayList<NewRDPParserFileLine>();
		
		BufferedReader reader = new BufferedReader(new FileReader(rdpFile));
		
		String nextLine = reader.readLine();
		
		while( nextLine != null)
		{
			//System.out.println(nextLine);
			list.add(new NewRDPParserFileLine(nextLine));
			nextLine= reader.readLine();
		}
		
		reader.close();
		long endTime = System.currentTimeMillis();
		System.out.println( "Parse time = " + (endTime - startTime)/1000f + " seconds " );
		
		return list;
	}
	
	public static List<NewRDPParserFileLine> getRdpList(File rdpFile, int numCPUs) 
		throws Exception
	{
		System.out.println("Parsing with " + numCPUs + " cpus ");
		System.out.println("Workers started at " + (System.currentTimeMillis() - startTime)/1000f);
		List<SubFileParser> workers = getCompletedWorkers(rdpFile, numCPUs);
		System.out.println("Workers finished at " + (System.currentTimeMillis() - startTime)/1000f);
		
		//single threaded
		List<NewRDPParserFileLine> returnList = new ArrayList<NewRDPParserFileLine>();
		
		for( SubFileParser sfp : workers )
		{
			if( sfp.returnException != null)
				 throw new Exception(sfp.returnException);
			
			returnList.addAll(sfp.resultsList);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println( "Parse time = " + (endTime - startTime)/1000f + " seconds " );
		
		return Collections.unmodifiableList(returnList);
		
	}
	
	private static List<SubFileParser> getCompletedWorkers( File rdpFile, int numCPUs)
		throws Exception
	{
		BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
		List<SubFileParser> subList = new ArrayList<SubFileParser>();
		int numAdded = 0;
		List<Thread> startedThreads = new ArrayList<Thread>();
		
		for( int x=0; x < numCPUs; x++)
		{
			SubFileParser sfp = new SubFileParser(blockingQueue);
			subList.add(sfp);
			Thread t =  new Thread(sfp);
			t.start();
			startedThreads.add(t);
		}
			
		BufferedReader reader = new BufferedReader(new FileReader(rdpFile));
		String nextLine = reader.readLine();
		
		while(nextLine != null)
		{
			blockingQueue.add(nextLine);
			nextLine = reader.readLine();
			numAdded++;
		}
		
		reader.close();
		
		while(! blockingQueue.isEmpty())
			Thread.yield();
		
		boolean keepLooping = true;
		
		while(keepLooping)
		{
			int assignedCount =0;
			
			for( SubFileParser sfp : subList )
				assignedCount += sfp.listSize;
			
			if( assignedCount > numAdded )
				throw new Exception("Threading error");
			
			if( assignedCount == numAdded)
			{
				keepLooping = false;
			}
			else
			{
				Thread.yield();
			}
		}
		
		for( Thread t : startedThreads)
			t.interrupt();
		
		return subList;
	}
	
	public static HashMap<String, NewRDPParserFileLine> getAsMapFromSingleThread( 
			String rdpFilePath) throws Exception
	{
		return getAsMapFromSingleThread(new File(rdpFilePath));
	}
	
	public static HashMap<String, NewRDPParserFileLine> getAsMapFromSingleThread( 
		File rdpFile) throws Exception
	{
		HashMap<String, NewRDPParserFileLine> map = new LinkedHashMap<String, NewRDPParserFileLine>();
		
		List<NewRDPParserFileLine> list = getRdpListSingleThread(rdpFile);
		
		for( NewRDPParserFileLine rdp : list )
		{
			String key = rdp.sequenceId;
			
			if( key.startsWith(">"))
				key = key.substring(1);
			
			if( map.containsKey(key) )
				throw new Exception("Duplicate key " + key);
			
			map.put(key, rdp);
		}
		
		return map;
	}
	
	private static class SubFileParser implements Runnable
	{	
		private final List<NewRDPParserFileLine> resultsList = 
			new ArrayList<NewRDPParserFileLine>();
		
		private final BlockingQueue<String> blockingQueue;
		
		private volatile Exception returnException = null;
		private volatile int listSize = 0;
		
		private SubFileParser(BlockingQueue<String> blockingQueue)
		{
			this.blockingQueue = blockingQueue;
		}
		
		public void run()
		{
			try
			{
				while( true)
				{
					String s= blockingQueue.take();
					resultsList.add( new NewRDPParserFileLine( s));
					listSize = resultsList.size();
				}
			}
			catch(Exception ex)
			{
				if( ex instanceof InterruptedException)
				{
					if( ! blockingQueue.isEmpty() )
						returnException= new Exception("Inappropriate interruption");
				}
				else
				{
					returnException = ex;
				}
			}
			finally
			{
				long endTime= System.currentTimeMillis();
				System.out.println("Thread finished " + (endTime - startTime)/1000f);
			}	
		}
	}
	
	public String getSummaryString()
	{
		StringBuffer buff = new StringBuffer();
		
		for( Iterator<NewRDPNode> i = this.taxaMap.values().iterator(); i.hasNext(); )
		{
			NewRDPNode node = i.next();
			
			buff.append(node.getTaxaName() + "(");
			buff.append(node.getScore() + ")");
			
			if( i.hasNext())
				buff.append(";");
		}
		
		return buff.toString();
	}
	
	public String getSummaryStringNoScore(float threshold, int skip)
	{
		StringBuffer buff = new StringBuffer();
		

		boolean firstTime = true;
		for( Iterator<NewRDPNode> i = this.taxaMap.values().iterator(); i.hasNext(); )
		{
			for( int x=0; x < skip && firstTime; x++)
				i.next();
			
			firstTime= false;
			NewRDPNode node = i.next();
				
				if(node.getScore() >= threshold )
					buff.append(node.getTaxaName() + ";");
				else
					buff.append(  "unclassified;");
				
		}
					
		String s = buff.toString();
		
		if( s.endsWith(";"))
			s = s.substring(0, s.length() -1);
		
		return s;
	}
	
	public String getSummaryStringNoScoreTaxaLevels(float threshold, int skip)
	{
		StringBuffer buff = new StringBuffer();

		boolean firstTime = true;
		for( Iterator<NewRDPNode> i = this.taxaMap.values().iterator(); i.hasNext(); )
		{

			for(int x=0; x < skip && firstTime; x++)
				i.next();
			firstTime= false;
			NewRDPNode node = i.next(); 
				//int j=0;
				if(node.getScore() >= threshold )
					buff.append(node.getRank().charAt(0)+"_"+node.getTaxaName() + ";");
				else
					buff.append( node.getRank().charAt(0) +"_"+ "unclassified;");
		}	
		String s = buff.toString();
		
		if( s.endsWith(";"))
			s = s.substring(0, s.length() -1);
		
		return s;
	}
	
	public String getSummaryStringCurtis(float threshold, int skip)
	{
		StringBuffer buff = new StringBuffer();
		boolean firstTime = true;
		for( Iterator<NewRDPNode> i = this.taxaMap.values().iterator(); i.hasNext(); )
		{
			for( int x=0; x < skip && firstTime; x++)
				i.next();
			
			firstTime= false;
			NewRDPNode node = i.next();
				
			if(node.getScore() >= threshold )
				buff.append(node.getTaxaName() + "|");				
		}
		
		String s=  buff.toString();
		
		if( s.length()==0)
			s = "unclassified|";
					
		return s.replace(" ", "_");
	}

/**	
	public static HashMap<String,Integer> calculateFrequencies(List<NewRDPParserFileLine> list ) 
			throws Exception
	{
		HashMap<String, RdpOTUinfo> map = new HashMap<String,RdpOTUinfo>();
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		
		for( NewRDPParserFileLine line : list )
		{
			String infoString=line.getSummaryStringNoScoreTaxaLevels(80, 0);
			//System.out.println(infoString);
			if(map.containsKey(infoString))
				map.get(infoString).addFrequency();
			else
			{
				RdpOTUinfo taxa = new RdpOTUinfo(infoString);
				map.put(infoString, taxa);
			}
		}
		for(String infoString : map.keySet())
		{
			count.put(infoString, map.get(infoString).getFrequency());
		}
	
		return count;
	}
	*/
	/**
	public static HashMap<String, Object[]> makePivotHashMap(String fastaFilePath, String fastaDirPath, String classifiersfilepath, String dirPath ) 
			throws Exception
	{
		HashMap<String, Object[]> map = new HashMap<String, Object[]>();
		BufferedReader reader = new BufferedReader(new FileReader(classifiersfilepath));
		BufferedReader fastaReader = new BufferedReader(new FileReader(fastaFilePath));
		String nextLine = reader.readLine();
		int i = 1;
		while(nextLine != null)
		{
			HashMap<String, ArrayList<Double>> freqMap = new HashMap<String,ArrayList<Double>>();
			String fastaFile = fastaReader.readLine();
			List<FastaSequence> fastaList = FastaSequence.readFastaFile(fastaDirPath + "/" + fastaFile);
			for(FastaSequence fs : fastaList)
			{
				if(freqMap.containsKey(fs.getID()))
				{
					freqMap.get(fs.getID()).set(0, fs.getAbundance()+freqMap.get(fs.getID()).get(0));
					freqMap.get(fs.getID()).set(1, fs.getRelativeAbundance()+freqMap.get(fs.getID()).get(1));
					
				}
				else
				{
					freqMap.put(fs.getID(), new ArrayList<Double>(Collections.nCopies(2, 0.0)));
					freqMap.get(fs.getID()).set(0, fs.getAbundance());
					freqMap.get(fs.getID()).set(1, fs.getRelativeAbundance());
				}

			}
			List<NewRDPParserFileLine> list = 
					NewRDPParserFileLine.getRdpListSingleThread(dirPath + "/" + nextLine);
			System.out.println(i);
			for( NewRDPParserFileLine line : list )
			{
				
				//String infoString= line.getSequenceId().split("\\|")[1].split("_")[0];
				String infoString= line.getSequenceId().split("\\|")[1];
				if(map.containsKey(infoString))
				{
					System.out.println(map.get(infoString)[0] + "    " + line.getSummaryString());
					/**
					for(int j=0; j<infoString.split(";").length; j++)
					{
						
					}
					//
					map.get(infoString)[i]=freqMap.get(infoString).get(1);
				}
				else
				{
					Object[] newArray=new Object[53];
					for(int j=0;j<newArray.length;j++)
						newArray[j]=0.0;
					newArray[0]=line.getSummaryStringNoScoreTaxaLevels(80, 0);
					newArray[i]=freqMap.get(infoString).get(1);
					map.put(infoString, newArray);
				}
			}
			i++;
			nextLine = reader.readLine();
		}
		return map;
	}
	
	
	public static void makePivotTable(String fastaFilePath, String fastaDirPath, String classifiersfilepath, String dirPath, String outFile) 
			throws Exception
	{
		HashMap<String,Object[]> pivotMap = makePivotHashMap(fastaFilePath, fastaDirPath, classifiersfilepath,dirPath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for(String infoString : pivotMap.keySet())
		{
			writer.write(infoString);
			for(Object count : pivotMap.get(infoString))
			{
				writer.write("\t"+count);
			}
			writer.newLine();
		}
		writer.close();
	}
	*/
	
	public static void main(String[] args) throws Exception
	{
		//makePivotTable("/Users/rbarner/Dropbox/FodorLab/KylieData/Sequences/kylie_allSamples_emirge/filenames.txt","/Users/rbarner/Dropbox/FodorLab/KylieData/Sequences/kylie_allSamples_emirge","/Users/rbarner/Dropbox/FodorLab/KylieData/classifierFiles/sequenceFiles.txt", "/Users/rbarner/Dropbox/FodorLab/KylieData/classifierFiles","/Users/rbarner/Dropbox/FodorLab/KylieData/classifierFiles/kyliePivotTable.txt");
		//makePivotTable("/Users/lorainelab/Dropbox/FodorLab/KylieData/Sequences/kylie_allSamples_emirge/filenames.txt","/Users/lorainelab/Dropbox/FodorLab/KylieData/Sequences/kylie_allSamples_emirge","/Users/lorainelab/Dropbox/FodorLab/KylieData/classifierFiles/sequenceFiles.txt", "/Users/lorainelab/Dropbox/FodorLab/KylieData/classifierFiles","/Users/lorainelab/Dropbox/FodorLab/KylieData/classifierFiles/kyliePivotTable.txt");
		// iterate through the taxa for each sequence
		///
		/**
		for( NewRDPParserFileLine line : list )
		{
			System.out.println(line.getSummaryStringNoScoreTaxaLevels(80, 0));
			/**
			for( int x=0; x < TAXA_ARRAY.length; x++)
			{
				NewRDPNode node = line.getTaxaMap().get(TAXA_ARRAY[x]);

				if( node != null)
				{
					System.out.println(node.getTaxaName() + " " + node.getScore());
				}
			}
			//
		}
		*/
	}
}
