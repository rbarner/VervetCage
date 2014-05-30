package pca;

public class CovarianceMatrixBinaryDistanceMeasure extends CovarianceMatrixDistanceMeasure
{
	@Override
	public double[][] getDistanceMatrix(double[][] d) throws Exception
	{
		double[][] newArray = new double[d.length][d[0].length];
		
		for( int x=0; x < d.length; x++)
			for( int y=0; y < d[0].length; y++)
				if( d[x][y] >0 )
					newArray[x][y]=1;
				else
					newArray[x][y] =0;
		
		return super.getDistanceMatrix(newArray);
	}
	
	@Override
	public String getName()
	{
		return "CovarianceMatrixBinary";
	}
}
