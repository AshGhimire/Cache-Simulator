package PackageOne;

import java.util.Random;
/**
 * This defines global stimulation parameter values.
 * @version 1.0
 */
public interface Constants
{
	// Driver Constants
	//totalRequests
	public static final long TOTAL_REQUESTS = 100;
		
	//numOfIterations
	public static final long NUMBER_OF_ITERATIONS = 10;
	
	//memory size (addressable locations)
	public static final long MEMORY_SIZE = 8192;
	
	//Cache size (addressable cache lines)
	public static final long CACHE_SIZE = 512;
	public static final long L1_CACHE_SIZE = 256;
	public static final long L2_CACHE_SIZE =  1024;
	public static final long L3_CACHE_SIZE = 2048;
	public static final long L4_CACHE_SIZE = 8192;
	
	//Level Statistics
	public static Statistics STATISTICS_L1 = new Statistics();
	public static Statistics STATISTICS_L2 = new Statistics();
	public static Statistics STATISTICS_L3 = new Statistics();
	public static Statistics STATISTICS_L4 = new Statistics();
	
	
	//Latency for each level of Cache and memory
	public static final long L1_LATENCY = 5;
	public static final long L2_LATENCY =  10;
	public static final long L3_LATENCY = 15;
	public static final long L4_LATENCY = 20;
	public static final long MEMORY_LATENCY = 25;
	
	//Number of contents in the Catalog
	public static final long NUMBER_OF_CONTENTS = 10;
	
	//Block  size (number of address-data entries)
	public static final long BLOCK_SIZE = 16;
	
	
	
	//-------------------------------------------------------------------
	
	//CacheDataRecord Constants & Global Variables
	//sumOfRatios
	public static final double INITIAL_SUM_OF_RATIOS = 0.0;
	
	//record_TimeStamp
	public static final long INITIAL_RECORD_TIMESTAMP = 0; 
	
	//record_AgeCounter
	public static final long  INITIAL_RECORD_AGE_COUNTER = 0;
	
	//--------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------
	
	//Cache Constants
	//hitCounter
	public static final long INITIAL_COUNT = 0;
	
	//requestCounter
    public static final long INITIAL_REQUEST_COUNTER = 0;
		
	public static double NORMAL_DISTRIBUTION_MEAN = 4000.0;
	public static double NORMAL_DISTRIBUTION_MEAN_FOR_POSITIVE = 2000.0;
	public static double NORMAL_DISTRIBUTION_DEVIATION = 1.0;
	
	//--------------------------------------------------------------------
	//Random class instance
	public static Random random = new Random();
	public static final long ZERO_VALUE = 0;
	public static final long MINUS_ONE = -1;
	public static final long INVALID_VALUE = MINUS_ONE ;
	
	//--------------------------------------------------------------------
	public static final CacheMapping CACHE_MAPPING = CacheMapping.DIRECT_MAPPING;
	public static final RequestType REQUEST_TYPE = RequestType.UNIFORM;

		
}