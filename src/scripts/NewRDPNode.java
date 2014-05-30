package scripts;

public class NewRDPNode
{
	private final String rank;
	private final String taxaName;
	private final int score;
	
	private static String stripQuotes(String inString)
	{
		StringBuffer buff = new StringBuffer();
		
		for( int x=0; x < inString.length(); x++)
		{
			char c = inString.charAt(x);
			
			if( c != '\"')
				buff.append(c);
		}
		
		return buff.toString();
	}
	
	public NewRDPNode(String rank, String taxaName, String scoreString) throws Exception
	{
		this.taxaName = stripQuotes( taxaName);
		this.rank = stripQuotes(rank);
		
		scoreString = scoreString.trim();
		
		if( scoreString.equals("1") || scoreString.equals("1.0"))
		{
			this.score = 100;
		}
		else if ( scoreString.equals("0"))
		{
			this.score = 0;
		}
		else
		{
			if( ! scoreString.startsWith("0.") )
				throw new Exception("Unexpected score string "+ scoreString);
			
			
			this.score = Integer.parseInt(scoreString.substring(2));
		}
			
		if( this.score < 0 || this.score > 100 )
			throw new Exception("Unexpected score " + this.score);
	}

	/*
	 * Should be between 1 and 100
	 */
	public int getScore()
	{
		return score;
	}
	
	public String getRank()
	{
		return rank;
	}
	public String getTaxaName()
	{
		return taxaName;
	}
}
