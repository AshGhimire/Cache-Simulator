package PackageOne;

import java.util.Random;
import java.lang.Math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// ask for certain range within the cache 
/**
 * This represnts the type of request from the user.
 *
 */
public class Request 
{	
	final static Logger logger = LogManager.getLogger();
	
	// request random values up to 2 million from cache
	/**
	 * This returns the uniform distribution.
	 * @return uniformDisturbution
	 */
	public long nextUniformRequest()
	{
		return uniformdistribution.nextRequestUniform();
	}
	
	/**
	 * This uses normal distribution to return the address.
	 * @return address The Address - {@link Address}
	 */
	public long nextNormalRequest()
	{
		double mean = this.lastAddress;
		long address =  (long) normalDistribution.getNextRequestNormal( mean, Constants.NORMAL_DISTRIBUTION_DEVIATION);
		this.lastAddress = address;
		return address;
	}
	
	/**
	 * This uses normal distrubution to return the address.
	 * @return address The Address - {@link Address}
	 */
	public long nextNormalRequestPositive( )
	{
		double mean = this.lastAddressPositive;
		long address = (long) normalDistribution.getNextRequestNormalPositive( mean, Constants.NORMAL_DISTRIBUTION_DEVIATION);
		lastAddressPositive = address;
		return address;
	}
	// returns mean for normal distribution
	public long getMean()
	{
		return getMean();
	}
	
	// returns standard deviation for normal distribution
	public long getStandardDeviation()
	{
		return getStandardDeviation();
	}
	
	private static Request_UniformDistribution uniformdistribution = new Request_UniformDistribution();
	private static Request_NormalDistribution  normalDistribution  = new Request_NormalDistribution();
	
	
	// ********************************
	private static class Request_NormalDistribution 
	{
		public double getNextRequestNormal(double mean, double deviation )
		{
			double nextG = random.nextGaussian();
			logger.debug("Next gaussian Number = " + nextG);
			return mean + (nextG * deviation);
		}
	  
		public double getNextRequestNormalPositive(double mean, double deviation)
		{
			return mean + Math.abs((random.nextGaussian()) * deviation);
		}
	   
		private static Random random = Constants.random;
	} //class Request_NormalDistribution 
	
	private static class Request_UniformDistribution 
	{
		// uses uniform distribution, also known as "rectangular distribution".
		// equal probability for all values of the random variables
		public long nextRequestUniform()
		{			
			return Math.abs(random.nextLong() % Constants.MEMORY_SIZE) ;			
		}
	
		private Random random = Constants.random;
		
	} //class Request_UniformDistribution
	
	private  double  lastAddress = Constants.NORMAL_DISTRIBUTION_MEAN;
	private double lastAddressPositive = Constants.NORMAL_DISTRIBUTION_MEAN_FOR_POSITIVE;

	
}
