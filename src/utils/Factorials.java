package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Factorials
{
	private static BigInteger[] factorials = null;
	public static final int NUM_TO_CALCULATE=3000;
	
	private static synchronized BigInteger[] getFactorialArray() 
	{
		if ( factorials == null ) 
		{
			factorials = new BigInteger[ NUM_TO_CALCULATE];
			factorials[0] = new BigInteger( "1" );
			
			for ( int x=1; x<NUM_TO_CALCULATE; x++ ) 
			{
				factorials[x] = factorials[x-1].multiply( new BigInteger( "" + x ));
			}
		}
		
		return factorials;
	}
	
	public static double getStirlingsApproximation( long n ) throws Exception
	{	
		if( n==0 )
			return 1;
		
		return  n * Math.log(n) -n + 0.5 * Math.log(2 * Math.PI * n) + 1/(12.0*n) -
		1 / (360.0 * n*n*n);
	}
	
	public static BigInteger getFactorial( int n ) throws Exception
	{
		if ( n < 0 )
			throw new Exception("Error n must be positive");
		
		if ( n >= NUM_TO_CALCULATE ) 
			throw new Exception("Error!  We only caclulated to n=" + (NUM_TO_CALCULATE-1) );
		
		return getFactorialArray()[n];
	}
	
	// singleton only
	private Factorials()
	{
	}
	
	
	
	/**  returns ( N! / (N-n)!n! )  * p^n * ( 1- p)^(N-n)
	 */
	public static BigDecimal getBinomial( int N, int n, float p ) throws Exception
	{
		BigInteger firstTerm = choose(N,n);
		
		BigDecimal returnVal = new BigDecimal( Math.pow(p, n ));
		
		returnVal = returnVal.multiply( new BigDecimal ( Math.pow((1-p), N-n)));
		returnVal = returnVal.multiply( new BigDecimal( firstTerm));
		
		return returnVal;
	}
	
	public static double getLnBinomial( int N, int n, float p ) throws Exception
	{
		double val = getLnChoose(N,n);
		val += Math.log(Math.pow(p,n));
		val += Math.log(Math.pow((1-p), N-n));
		
		return val;	
	}
		
	public static boolean canDoIt( int N, int n ) throws Exception
	{
		int biggest = n;
		int smallest = N - n;
			
		if ( smallest > biggest )
		{
			biggest = N - n;
			smallest = n;
		}
			
		if ( smallest > NUM_TO_CALCULATE ) 
			return false;
		
		return true;
			
	}
	
	public static double getLnChoose( int N, int n) throws Exception
	{
		if ( n > N ) 
			throw new Exception("Error!  N must be >= n " + "N=" + N + " n=" + n);
		
		if ( N < 0 ) 
			throw new Exception("Error!  N must be positive");
		
		if ( N == n || n == 0 ) 
			return Math.log( 1 );
			
		double top = 0;
		
		if ( N < NUM_TO_CALCULATE ) 
		{				
			top =  Math.log(getFactorialArray()[N].doubleValue());
		}
		else // Stirling's approximation
		{
			top = N * Math.log( N ) - N;	
		}
		
		double bottom = 0;
		
		if ( N - n < NUM_TO_CALCULATE ) 
		{
			bottom = Math.log( getFactorialArray()[N-n].doubleValue() );
		}
		else
		{
			int NMinusN = N-n;
			bottom = (NMinusN ) * Math.log( NMinusN ) - NMinusN ;
		}
		
		if ( n < NUM_TO_CALCULATE ) 
		{
			bottom +=  Math.log( getFactorialArray()[n].doubleValue());
		}
		else
		{
			bottom += n * Math.log(n) - n;
		}
		
		return top - bottom;
	}
	
	/**  returns N! / ( N-n)!n!
	 */
	public static BigInteger choose( int N, int n ) throws Exception
	{
		if ( n > N ) 
			throw new Exception("Error!  N must be >= n");
		
		if ( N < 0 ) 
			throw new Exception("Error!  N must be positive");
		
		if ( N == n || n == 0 ) 
			return new BigInteger( "" + 1 );
		
		if ( N >= NUM_TO_CALCULATE ) 
		{
			int biggest = n;
			int smallest = N - n;
			
			if ( smallest > biggest )
			{
				biggest = N - n;
				smallest = n;
			}
			
			if ( smallest > NUM_TO_CALCULATE ) 
				throw new Exception("Error!  Need to have " + smallest + " factorial ");
			
			int x = N-1;
			BigInteger top = new BigInteger("" + N );
			
			while ( x > biggest )
			{
				top = top.multiply( new BigInteger( "" + x ) );
				x--;
			}
			
			return top.divide( getFactorialArray()[smallest] );
			
		}
		else
		{
			BigInteger top = getFactorialArray()[N];
			BigInteger bottom = getFactorialArray()[N-n];
			bottom =  bottom.multiply( getFactorialArray()[n]);
			return top.divide( bottom );
		}
	}

	public static double getBinomialSum(int N, int n, float prob ) throws Exception
	{
		BigDecimal sum = new BigDecimal( 0 );
	
		for ( int x=n; x<=N; x++ ) 
			sum= sum.add( Factorials.getBinomial(N,x,prob));
		
		return sum.doubleValue();
	}
	
	public static void main(String[] args) throws Exception
	{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"c:\\temp\\comparison.txt")));
		
		writer.write("n\texactLnN\tstirlingLnN\n");
		
		for( int x=2; x < 200; x++)
		{
			BigInteger bruteForceFactorial = 
				getFactorial(x);

			double exactLog = Math.log(bruteForceFactorial.doubleValue());
			
			writer.write(x + "\t" + exactLog+ "\t" + 
								getStirlingsApproximation(x) + "\n");
			
			System.out.println(x);
		}
		
		writer.flush();  writer.close();
		
		System.out.println( Long.MAX_VALUE + " " + getStirlingsApproximation(Long.MAX_VALUE));
	}

}
