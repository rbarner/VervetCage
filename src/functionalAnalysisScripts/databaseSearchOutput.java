package functionalAnalysisScripts;

public abstract class databaseSearchOutput
{
	public abstract String getQueryID();
	public abstract String getTargetID();
	public abstract double getPercentIdentity();
	public abstract int getAlnLength();
	public abstract int getMismatchCount();
	public abstract int getGapOpenCount();
	public abstract String getQueryStart();
	public abstract String getQueryEnd();
	public abstract String getTargetStart();
	public abstract String getTargetEnd();
	public abstract double geteValue();
	public abstract double getBitScore();
}
