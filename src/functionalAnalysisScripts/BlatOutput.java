package functionalAnalysisScripts;

import java.io.IOException;

public class BlatOutput extends databaseSearchOutput
{
	private String queryID;
	private String targetID;
	private double percentIdentity;
	private int alnLength;
	private int mismatchCount;
	private int gapOpenCount;
	private String queryStart;
	private String queryEnd;
	private String targetStart;
	private String targetEnd;
	private double eValue;
	private double bitScore;

	
	/******************************
	 * Constructors                
	 * @throws IOException *
	 ******************************/ 
	public BlatOutput(String aLine) throws IOException
	{
		this.queryID = aLine.split("\t")[0];
		this.targetID = aLine.split("\t")[1];
		this.percentIdentity = Double.parseDouble(aLine.split("\t")[2]);
		this.alnLength = Integer.parseInt(aLine.split("\t")[3]);
		this.mismatchCount = Integer.parseInt(aLine.split("\t")[4]);
		this.gapOpenCount = Integer.parseInt(aLine.split("\t")[5]);
		this.queryStart = aLine.split("\t")[6];
		this.queryEnd = aLine.split("\t")[7];
		this.targetStart = aLine.split("\t")[8];
		this.targetEnd = aLine.split("\t")[9];
		this.eValue = Double.parseDouble(aLine.split("\t")[10]);
		this.bitScore = Double.parseDouble(aLine.split("\t")[11]);
		
	}

	public String getQueryID()
	{
		return queryID;
	}

	public String getTargetID()
	{
		return targetID;
	}

	public double getPercentIdentity()
	{
		return percentIdentity;
	}

	public int getAlnLength()
	{
		return alnLength;
	}

	public int getMismatchCount()
	{
		return mismatchCount;
	}

	public int getGapOpenCount()
	{
		return gapOpenCount;
	}

	public String getQueryStart()
	{
		return queryStart;
	}

	public String getQueryEnd()
	{
		return queryEnd;
	}

	public String getTargetStart()
	{
		return targetStart;
	}

	public String getTargetEnd()
	{
		return targetEnd;
	}

	public double geteValue()
	{
		return eValue;
	}
	
	public double getBitScore()
	{
		return bitScore;
	}
}
