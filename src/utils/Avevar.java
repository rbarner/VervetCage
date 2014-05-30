package utils;

import java.util.List;

/**  Calculates mean and variance from an array of doubles
 */
public class Avevar
{
	double ave;
	double var;
	
	public double getAve()
	{
	    return ave;
	}
	
	public double getVar()
	{
	    return var;
	}
	
	public double getSqrt()
	{
		return Math.sqrt(var);
	}
	
	public double getSD()
	{
		return Math.sqrt(var);
	}
	
	@Override
	public String toString()
	{
		return this.getAve() + " " + this.getSD();
	}
	
	public Avevar( double[] data ) 
	{
		double s, ep;
		int j;
		
		ave = 0.0;
		for ( j=0; j < data.length; j++ ) ave += data[j];
		ave /= data.length;
		var = ep = 00;
		
		for ( j = 0; j < data.length; j++) {
			s = data[j] - ave;
			ep += s;
			var += s*s;
		}
		
		var = ( var - ep * ep / data.length ) / ( data.length - 1);
	}
	
	
	public Avevar( List<? extends Number> data ) 
	{
		double s, ep;
		int j;
		
		ave = 0.0;
		for ( j=0; j < data.size(); j++ ) ave += (data.get(j)).doubleValue();
		ave /= data.size();
		var = ep = 00;
		
		for ( j = 0; j < data.size(); j++) {
			s = (data.get(j)).doubleValue() - ave;
			ep += s;
			var += s*s;
		}
		
		var = ( var - ep * ep / data.size() ) / ( data.size()- 1);
	}
    
	
    public Avevar( float[] data ) 
	{
        double s, ep;
		int j;
		
		ave = 0.0;
		for ( j=0; j < data.length; j++ ) ave += data[j];
		ave /= data.length;
		var = ep = 00;
		
		for ( j = 0; j < data.length; j++) {
			s = data[j] - ave;
			ep += s;
			var += s*s;
		}
		
		var = ( var - ep * ep / data.length ) / ( data.length - 1);
	}
}