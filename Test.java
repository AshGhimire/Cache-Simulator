package PackageOne;
//This Test class is loaded with the multi-level caches and different types of requests.


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test 
{
	final static Logger logger = LogManager.getLogger();
	private static Memory memory = new Memory();
	
	 
	  private static ArrayList<Cache> firstCache = new ArrayList<Cache>();
	  private static ArrayList<Cache> secondCache = new ArrayList<Cache>();

	public static void main(String[] args) 
	{	
	    ArrayList<Long> addressList = new ArrayList<Long>();
		
		secondCache.add(new Cache ("L2 Cache, #1",1024, CacheMapping.SET_ASSOCIATIVE, 4, ReplacementPolicy.FIFO, memory,null, statisticsL2 ));
		secondCache.add(new Cache("L2 Cache: #2", 1024, CacheMapping.SET_ASSOCIATIVE, 4, ReplacementPolicy.FIFO, memory, null, statisticsL2 ));
		
		firstCache.add(new Cache("L1 Cache #1",128,CacheMapping.FULLY_ASSOCIATIVE,4, ReplacementPolicy.LRU, secondCache.get(0), null,statisticsL1));
		firstCache.add(new Cache("L1 Cache #2",128,CacheMapping.FULLY_ASSOCIATIVE,4, ReplacementPolicy.LRU, secondCache.get(0), null, statisticsL1));
		firstCache.add(new Cache("L1 Cache #3",128,CacheMapping.FULLY_ASSOCIATIVE,4, ReplacementPolicy.LRU, secondCache.get(1), null, statisticsL1));
		firstCache.add(new Cache("L1 Cache #4",128,CacheMapping.FULLY_ASSOCIATIVE,4, ReplacementPolicy.LRU, secondCache.get(1), null, statisticsL1));
			
		

		for (long j=0; j < numberOfIterations; j++)
		{
			for (long i = 0; i < totalRequests; i++)
			{
				
				addressList.clear();
				
				
				for (int b = 0; b <20; b++)
				{
				    addressList.add(request.nextNormalRequest());
					addressList.add(request.nextNormalRequestPositive());
					addressList.add(request.nextUniformRequest());
				}
				
				
				for(int k = 0; k<20; k++)
				{
					Address address = new Address(addressList.get(k));
					logger.error("AddressOnly: "+address);
					DataLocator dataLocator =  new DataLocator(DataLocatorType.ADDRESS, address , null,null) ;
					DataObject dataObject = firstCache.get(k/5).getData(dataLocator,true); 
					//Data data = firstCache.get(k/5).getData(address);
					logger.trace("address = "+address+ " , dataObject = "+ dataObject);
					
				}
			    
			} 
			sumOfRatiosL1 = sumOfRatiosL1 + statisticsL1.getHitRatio();
			sumOfRatiosL2 = sumOfRatiosL2 + statisticsL2.getHitRatio();
		}
		
		System.out.println( "L1 Cache size: "+ firstCache.get(0).getCacheSize() + "L1 Hit Ratio: " + (sumOfRatiosL1/numberOfIterations));
		System.out.println( "L2 Cache size: "+ secondCache.get(0).getCacheSize() + ",L2 Hit Ratio: " + (sumOfRatiosL2/numberOfIterations));
	}

	private static Statistics statisticsL1 = new Statistics();
	private static Statistics statisticsL2 = new Statistics();
	private static Statistics statisticsL3 = new Statistics();
		
	private static long totalRequests = Constants.TOTAL_REQUESTS; 
    private static long numberOfIterations = Constants.NUMBER_OF_ITERATIONS;
	private static Request request = new Request();
	private static double sumOfRatiosL1 = Constants.INITIAL_SUM_OF_RATIOS;
	private static double sumOfRatiosL2 = Constants.INITIAL_SUM_OF_RATIOS;
	private static double sumOfRatiosL3 = Constants.INITIAL_SUM_OF_RATIOS;
}