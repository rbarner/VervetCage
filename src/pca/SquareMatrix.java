package pca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class SquareMatrix  implements DistanceMeasureInterface
{

	public double[][] getDistanceMatrix(double[][] d) throws Exception
	{
		BufferedReader reader2 = new BufferedReader(new FileReader("/Applications/mothur/dateBrayCurt/jan12/brayCurtForMothur_phylumLogged.txt"));
		double[][] returnArray = new double[14][14];
		String nextLine = reader2.readLine();
		int x = 0;
		while(nextLine != null)
		{
			StringTokenizer sToken = new StringTokenizer(nextLine, "\t");
			sToken.nextToken();
			int y=0;
			while(sToken.hasMoreTokens())
			{
				returnArray[x][y] = Double.parseDouble(sToken.nextToken());
				y++;
			}
			x++;
			nextLine = reader2.readLine();
		}
		reader2.close();
		return returnArray;
	}
	
	@Override
	public String getName()
	{
		return "Bray Curtis Matrix";
	}

}
