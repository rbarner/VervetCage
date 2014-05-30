package scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import scripts.FastaSequence;

public class SplitFastaFile
{
	private List<FastaSequence> fastaList;
	private int numSequencesPerFile;
	
	/**
	This is a constructor that builds a fasta sequence object from the alignment file.  
	The arguments are the file for the alignment and a boolean determining if the
	alignment is a protein alignment.
	
	This method will make sure that all residues in the alignment are upper case.
	*/
	public SplitFastaFile(File file, int numSeqs) throws Exception
	{
	    //Check that the file exists.
		if(! file.exists())
		{
			throw new Exception("File does not exist.");
		}
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(file.toString());
		
		//Check that the sequences in the alignment are the same length
		int checkLength=fastaList.get(0).getSequence().length();
		for(int x=0; x<fastaList.size(); x++)
		{
			fastaList.get(x).getSequence().toUpperCase();
			if(fastaList.get(x).getSequence().length() != checkLength)
			{
				throw new Exception("Alignment is not valid. Sequences are not the same length.");
			}
		}
		this.fastaList=fastaList;
		this.numSequencesPerFile=numSeqs;
	}
	
	/**
	This is a constructor that builds a fasta sequence object from the alignment file.  
	The arguments are the filepath for the alignment and a boolean determining if the
	alignment is a protein alignment.
	
	This method will make sure that all residues in the alignment are upper case.
	*/
	public SplitFastaFile(String filepath, int numSeqs) throws Exception
	{
		//Check that the file exists.
		File read = new File( filepath );
		if(! read.exists())
		{
			throw new Exception("File does not exist.");
		}
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(filepath);
		//Check that the sequences in the alignment are the same length
		int checkLength=fastaList.get(0).getSequence().length();
		for(int x=0; x<fastaList.size(); x++)
		{
			fastaList.get(x).getSequence().toUpperCase();
			if(fastaList.get(x).getSequence().length() != checkLength)
			{
				throw new Exception("Alignment is not valid. Sequences are not the same length.");
			}
		}
		this.fastaList=fastaList;
		this.numSequencesPerFile=numSeqs;
	}
	

	
	public void makeSplitFastaFile(List<FastaSequence> fastaList, int numSeqs) throws IOException
	{
		
		int numFiles = getNumberOfSequences()/numSeqs;
		String fileName = new String("/Users/rbarner/hmp_data/eggNog/splits/splitFasta.fasta");
		int i = 0;
		for(int x = 0; x< numFiles; x++)
		{
			int y = 0;
			BufferedWriter writer = new BufferedWriter( new FileWriter(fileName+"_"+ String.valueOf(x) +".fasta" ));
			while(y < numSeqs)
			{
				writer.write(fastaList.get(i).getHeader()+ "\n");
				writer.write(fastaList.get(i).getSequence() + "\n");
				y++;
				i++;
			}
			
			writer.flush();  writer.close();
		}
	}
	/**
	Returns the number of sequences in the alignment.
	*/
	public int getNumberOfSequences()
	{
		return fastaList.size();
	}
	
	
	public List<FastaSequence> getFastaList()
	{
		return fastaList;
	}

	/******************************
	 * Main Method                *
	 ******************************/
	public static void main(String[] args) throws Exception 
	{
		SplitFastaFile fastas= new SplitFastaFile("/Users/lorainelab/Dropbox/FodorLab/KylieData/parsedData/metaphlan/read1/all/OTUPivotTableRevised.txt",200);
		
		List<FastaSequence> fastaList = fastas.getFastaList();
		fastas.makeSplitFastaFile(fastaList, 200);
		
	}

}
