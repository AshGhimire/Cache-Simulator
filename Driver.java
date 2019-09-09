package PackageOne;
//In this class, there are only two caches made. 

import java.util.ArrayList;

import org.apache.logging.log4j.*;

public class Driver 
{
	final static Logger logger = LogManager.getLogger();
	
	private static ArrayList<DataSource> l1Secondary = new ArrayList<DataSource>(); 


	public static void main(String[] args) 
	{	
		Cache cache3 = new Cache("Cache 3", CacheLevel.LEVEL_THREE, CacheMapping.FULLY_ASSOCIATIVE, 4, ReplacementPolicy.FIFO, memory,null);
		Cache cache2 = new Cache ("Cache2",CacheLevel.LEVEL_TWO,  CacheMapping.FULLY_ASSOCIATIVE,  4,  ReplacementPolicy.LRU, cache3,null);
		Cache cacheSecondary = new Cache ("CacheSecondary",CacheLevel.LEVEL_ONE,  CacheMapping.FULLY_ASSOCIATIVE,  4,  ReplacementPolicy.LRU, cache2,null );		
		l1Secondary.add(cacheSecondary);
		
		Cache cache1 = new Cache ("Cache1",CacheLevel.LEVEL_ONE,  CacheMapping.FULLY_ASSOCIATIVE,  4,  ReplacementPolicy.LRU, cache2,l1Secondary );
		//DataLocator tempDataLocator =  new DataLocator(DataLocatorType.ADDRESS, new Address( 0), null,null) ;
		//DataObject tempDataObject = cacheSecondary.getData(tempDataLocator, true);

		for (long j=0; j < numberOfIterations; j++)
		{
			for (long i = 0; i < 12; i++)
			{
				long address = i;
				//long address = request.nextNormalRequest();  //requesting address 
				//long address = request.nextNormalRequest();  
				
				logger.info("Address : " +address);
				DataLocator dataLocator =  new DataLocator(DataLocatorType.ADDRESS, new Address( address), null,null) ;
				logger.debug("calling cache.getData(), dataLocator type= " + dataLocator );
				DataObject dataObject = cache1.getData(dataLocator, true); 

				logger.debug("current request number= " + i + ", address= " + address + ", data= " + dataObject.getData() );
				logger.info("--- --- END of Current request sequence. New address request below --- ---\n\n"); // to space out the log blocks
			}
		}
		
		System.out.println( "L1 Cache size: "+ cache1.getCacheSize() + ",L1 Hit Ratio: " + statisticsL1.getHitRatio());
		System.out.println( "L2 Cache size: "+ cache2.getCacheSize() + ",L2 Hit Ratio: " + statisticsL2.getHitRatio());
		System.out.println( "L3 Cache size: "+ cache3.getCacheSize() + ",L3 Hit Ratio: " + statisticsL3.getHitRatio());		 
	
	}
	
	private static Statistics statisticsL1 = Constants.STATISTICS_L1;
	private static Statistics statisticsL2 = Constants.STATISTICS_L2;
	private static Statistics statisticsL3 =Constants.STATISTICS_L3;
	
	private static Memory memory = new Memory();
	
	private static Request request = new Request();
	//l1Secondary.add(cacheSecondary);

	
	private static long totalRequests = Constants.TOTAL_REQUESTS;// Constants.TOTAL_REQUESTS; 
    private static long numberOfIterations = Constants.NUMBER_OF_ITERATIONS;
	private static double sumOfRatiosL1 = Constants.INITIAL_SUM_OF_RATIOS;
	private static double sumOfRatiosL2 = Constants.INITIAL_SUM_OF_RATIOS;
	private static double sumOfRatiosL3 = Constants.INITIAL_SUM_OF_RATIOS;

}
