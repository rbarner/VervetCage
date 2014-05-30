package scripts;

public class RunPCOA
{
	public static void main(String[] args) throws Exception
	{
		OtuWrapper otuW = new OtuWrapper("/Users/rbarner/Dropbox/FodorLab/KylieData/Analysis/FunctionPrediction/vervet/picrust/parsedTables/date/jan12WGSLevel2Pivot.txt");
		otuW.createMothurBrayCurtis(false);
	}
}
