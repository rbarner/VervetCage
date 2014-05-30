package functionalAnalysisScripts;




public class FunctionObject
{
	private String name;
	private Long count;

	/******************************
	 * Constructors                *
	 ******************************/ 
	public FunctionObject(String aLine)
	{
		this.name = aLine.split("\t")[0];
		this.count = Long.parseLong(aLine.split("\t")[1]);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Long getCount()
	{
		return count;
	}
}
