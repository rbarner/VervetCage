package scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import utils.ConfigReader;

public class FastaSequenceOneAtATime
{
	private BufferedReader reader;
	private String nextLine;
	
	public FastaSequenceOneAtATime(String filePath) throws Exception
	{
		this(new File(filePath),false);
	}
	
	public FastaSequenceOneAtATime(File file) throws Exception
	{
		this(file,false);
	}
	
	public FastaSequenceOneAtATime(String filePath, boolean gzipped) throws Exception
	{
		this(new File(filePath), gzipped);
	}
	
	public FastaSequenceOneAtATime(File file, boolean gzipped) throws Exception
	{
		reader = 
			gzipped ?
				new BufferedReader(new InputStreamReader( 
						new GZIPInputStream( new FileInputStream( file) ) ))
				:
					new BufferedReader(new FileReader(file));
		
		nextLine= reader.readLine();
		
		//consume blank lines at the top of the file
		while( nextLine != null && nextLine.trim().length() == 0  )
			nextLine= reader.readLine();

	}
	
	public void close() throws Exception
	{
		reader.close();
	}

	public FastaSequence getNextSequence() throws Exception
	{	
		if(nextLine == null)
		{
			return null;
		}
					
		FastaSequence fs = new FastaSequence();
		fs.setHeader( nextLine);
			
		nextLine = reader.readLine();
		
		StringBuffer buff = new StringBuffer();
		while ( nextLine != null && ! nextLine.startsWith(">") ) 
		{
			buff.append( FastaSequence.stripWhiteSpace( nextLine ) );
			nextLine = reader.readLine();
		}	
		
		fs.setSequence(buff);
			
		//consume blanks that might occur after the ">"
		while( nextLine != null && nextLine.trim().length() == 0  )
			nextLine= reader.readLine();
		
		return fs;
	}
	
	/**
	 * 
	 * Just some test code
	 */
	public static void main(String[] args) throws Exception
	{
		FastaSequenceOneAtATime fsone = 
			new FastaSequenceOneAtATime("/Users/rbarner/hmp_data/seqs/seqs_v13.fna");
		
		BufferedWriter writer = new BufferedWriter( new FileWriter("/Users/rbarner/hmp_data/seqs/stool_seqs_v13_2.fna"));
		List<String> stoolSamples = new ArrayList<String>();
		BufferedReader reader = new BufferedReader( new FileReader( "/Users/rbarner/Dropbox/FodorLab/KylieData/rawData/key/stool_samples.txt" ));
		String nextLine = reader.readLine();
		while ( nextLine != null  ) 
		{
			stoolSamples.add(nextLine.trim());
			nextLine = reader.readLine();	
		}
		
		
		
		for( FastaSequence fs = fsone.getNextSequence(); fs != null; fs = fsone.getNextSequence())
		{
			System.out.print(fs.getHeader().substring(1).split("_")[0]);
			if(stoolSamples.contains(fs.getHeader().substring(1).split("_")[0]))
					{
						System.out.print("   Made it to the if loop");
						writer.write(fs.getHeader().split(" ")[0]); writer.write("\n");
						writer.write(fs.getSequence()); writer.write("\n");	
					}
			System.out.print("\n");
		}
		writer.close();
		reader.close();
			// do something with fs
		
	}
}
