package PackageOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This records how often the cache hits and cache miss.
 *@version 1.0
 */
public class Statistics
{

	final static Logger logger = LogManager.getLogger();
	
	/**
	 * This is the initial count for hitCounter, missCounter and requestCounter.
	 * The initial count starts at 0.
	 */
	public Statistics() 
	{
		this.hitCounter = Constants.INITIAL_COUNT;
		this.missCounter = Constants.INITIAL_COUNT;
		this.requestCounter = Constants.INITIAL_COUNT;

	}
	
	/**
	 * This updates and adds 1 to the hitCounter.
	 */
	public long updateHitCount() {return hitCounter++;}
	
	/**
	 * This updates and adds 1 to the missCounter.
	 */
	public long updateMissCount() {return missCounter++;}
	
	/**
	 * This updates and adds 1 to the requestCounter.
	 */
	public long updateRequestCount() {return requestCounter++;}
	
	/**
	 * This returns the long value of hitCounter.
	 * @return hitCounter
	 */
	public long getHitCount() {return hitCounter;}
	
	/**
	 * This returns the long value of missCounter.
	 * @return missCounter
	 */
	public long getMissCount() {return missCounter;}
	
	/**
	 * This returns the long value of requestCounter.
	 * @return requestCounter
	 */
	public long getRequestCount() {return requestCounter;}
	
	/**
	 * This returns the hit ratio. 
	 * @return ratio
	 */
	public double getHitRatio()
	{
		return (double)hitCounter / (double)requestCounter;
	}
	
	/**
	 * This returns the hit percentage.
	 * @return percentage
	 */
	public double getHitPercentage()
	{
		return getHitRatio() * 100.0;
	}
	
	private long hitCounter;
	private long missCounter;
	private long requestCounter;
	
	//private vars
}