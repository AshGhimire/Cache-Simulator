package PackageOne;

// Cache operations

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This represents the cache memory.
 * @version 1.0
 */
public class Cache implements CacheInterface
{
    final static Logger logger = LogManager.getLogger();
	
    /**
     * This returns primary dataSource.
     * @return dataSource - {@link DataSource}
     */
	public DataSource getDataSource() {return dataSource;} 
	
	/**
	 * This returns cacheSize.
	 * @return cacheSize 
	 */
	public long getCacheSize() {return cacheSize;}
	
	/**
	 * This returns setSize.i.e. The number of blocks per set.
	 * @return setSize
	 */
	public long getSetSize()   {return setSize;}
	
	/**
	 * This returns the size of the block.
	 * @return - blockSize
	 */
	public long getBlockSize() {return blockSize;}
	
	/**
	 * This function returns the replacement policy for the given cache. Replacement policies are FIFO, LRU or Random.
	 * @return -replacementPolicy - The ReplacementPolicy {@link ReplacementPolicy}
	 */
	public ReplacementPolicy getReplacementPolicy() {return replacementPolicy;}
	
	/**
	 * This function returns the mapping policy to bring data blocks into the cache.
	 * @return - cacheMapping {@link CacheMapping}
	 */
	public CacheMapping getMapping() {return cacheMapping;}
	
	/**
	 * This function returns the set associativity in the cache. This specify how many blocks are in the set.
	 * @return setAssocivity - The Associvity 
	 */
	public long getAssociativity() {return setAssocivity;}
	
	/**
	 * This returns the name of the cache.
	 * @return - cacheName - The Cache Name
	 */
	public String getCacheName() { return cacheName;}
	
	/**
	 * This returns a list of secondary data sources.
	 * @return - secondarySources - The Secondary Sources
	 */
	public Collection getSecondarySources() { return secondarySources;}
	
	
	//ctors
	private Cache(){} // purposely private
	
	// setAssocivity value will be ignored for direct mapping & fully-associative. It will be set by the ctor, therefor, set value to 0
	// setSize parameter will be ignored for direct mapping & fully associative mapping. It will be set by the ctor.
	/**
	 * This creates the empty cache. The cache size should be the power of two(2^n).
	 * @param cacheName -The Cache Name
	 * @param cacheSize -The Cache Size
	 * @param cacheMapping {@link CacheMapping}
	 * @param setAssocivity - SetAssocivity 
	 * @param replacementPolicy {@link ReplacementPolicy}
	 * @param dataSource {@link DataSource}
	 * @param secondarySources The Secondary Sources
	 * @param levelStatistics The Level Statistics 
	 */
	public Cache (String cacheName, CacheLevel cacheLevel, CacheMapping cacheMapping, long setAssocivity, ReplacementPolicy replacementPolicy, DataSource primaryDataSource, Collection<DataSource> secondarySources )
	{
		this.cacheName = cacheName;
		//this.cacheSize = cacheSize;
		//this.setSizeInByte = blockSize * setAssocivity;
		this.cacheMapping = cacheMapping;
		this.replacementPolicy = replacementPolicy;
		this.localStatistics = new Statistics();
		//this.cacheLatency = 
		switch(cacheLevel)
		{
			case LEVEL_ONE:
				this.cacheLatency = Constants.L1_LATENCY;
				this.cacheSize = Constants.L1_CACHE_SIZE;
				this.levelStatistics = Constants.STATISTICS_L1;
				break;
				
			case LEVEL_TWO:
				this.cacheLatency = Constants.L2_LATENCY;
				this.cacheSize = Constants.L2_CACHE_SIZE;
				this.levelStatistics = Constants.STATISTICS_L2;
				break;
			
			case LEVEL_THREE:
				this.cacheLatency = Constants.L3_LATENCY;
				this.cacheSize = Constants.L3_CACHE_SIZE;
				this.levelStatistics = Constants.STATISTICS_L3;
				break;
			
			case LEVEL_FOUR: 
				this.cacheLatency = Constants.L4_LATENCY;
				this.cacheSize = Constants.L4_CACHE_SIZE;
				this.levelStatistics = Constants.STATISTICS_L4;
				break;
				
			default:
	            	logger.fatal("Unknown CacheLevel. Terminating program ");
					System.exit(0); 
			
			
		}
		
		if(primaryDataSource==null)
		{
			logger.fatal("DataSource cannot be null when passed in cache constuctor. Terminating the program. ");
			System.exit(1);
		}
		this.dataSource = primaryDataSource;  // ** If dataSource is still null, log severe and exit(1)   - Ashim
		this.secondarySources = secondarySources;
		//this.levelStatistics = levelStatistics;
		
		switch (cacheMapping)
		{
			case DIRECT_MAPPING:
				this.setSize = 1;  // one block per set
				this.setAssocivity = 1;
				this.setSizeInByte = blockSize * this.setAssocivity;
				break;
					
			case FULLY_ASSOCIATIVE:
			    // check for other parms to set here
				this.setSizeInByte = cacheSize;
				this.setSize =  cacheSize / blockSize;
				break;
					
			case SET_ASSOCIATIVE:
			    // ** For this case, make sure setAssocivity is a power ot two before setting it - Ashim
				if(!isPositivePowerOfTwo(setAssocivity))
				{
					logger.fatal("setAssocivity not a power of 2. setAssocivity = : "+ setAssocivity+ ". Terminating program.");
					System.exit(1);
				}
				this.setAssocivity = setAssocivity;
				this.setSize =  setAssocivity;
				this.setSizeInByte = blockSize * setAssocivity;
                break;
					
            default:
            	logger.fatal("Unknown mapping type. Terminating program ");
				System.exit(0); 
		}
		
		//ensure sizes are powers of 2,
		if( !isPositivePowerOfTwo(cacheSize) || !isPositivePowerOfTwo(setSizeInByte) || !isPositivePowerOfTwo(blockSize) )
		{
			logger.fatal("Cache.java: Cache ctor Terminating program!");
			logger.fatal("cacheSize, setSize, and/or blockSize is not a power of 2, sizes: cache= " + cacheSize + ", set= " + setSize + ", block= " + blockSize);
			System.exit(0); 
		}
		
		this.numberOfCacheSets = cacheSize /*in bytes*/ / setSizeInByte  /*in bytes*/ ;
		logger.info("Cache ctor. Cache name: " + cacheName + ", cacheSize: " + cacheSize + ", setSize: " + 
		            setSize + ", blockSize: " + blockSize + ", numberOfCacheSets: " + numberOfCacheSets);
		setCollection = new HashMap<SetID,CacheSet>((int) numberOfCacheSets);  //** Change to ctor with initial capacity = number of sets in cache -Ashim
	}
	
	
	
	
	/**
	 * This returns the dataObject.
	 * @param dataLocator {@link DataLocator}
	 * @param isPrimary Primary Source
	 * @return dataObject - {@link DataObject}
	 */
	@Override  // DataSource interface method
	public DataObject getData(DataLocator dataLocator, boolean isPrimary)
	{
		DataObject dataObject;
		if(isPrimary)
		{
			this.levelStatistics.updateRequestCount();
			this.localStatistics.updateRequestCount();
		}
		
		logger.debug("Cache Name: "+ getCacheName() );
		logger.debug("In getData(), dataLocator type= " + dataLocator );
		if(  dataLocator.getDataLocatorType() == DataLocatorType.ADDRESS  )
		{
			Data data = this.getData(dataLocator.getAddress());
			dataObject = new DataObject(DataObjectType.DATA, data, null, null);
			dataObject.updateLatency(this.cacheLatency);			
		}
		else if(  dataLocator.getDataLocatorType() == DataLocatorType.BLOCK  )
		{
			CacheDataBlock cacheDataBlock = this.getData(dataLocator.getBlockID(), isPrimary);
			if(cacheDataBlock == null)
			{
				return null;
			}
			
			dataObject = new DataObject(DataObjectType.CACHEDATABLOCK, null, cacheDataBlock, null);
		}
		else if(dataLocator.getDataLocatorType() == DataLocatorType.CONTENT)
		{
			Content content = this.getData(dataLocator.getContentID(), isPrimary);
			dataObject = new DataObject(DataObjectType.CONTENT,null, null, content);
		}
		else
		{
			logger.fatal("Unknown or invalid DataLocator type. Terminating program ");
			System.exit(0);
			return null; //Not reachable statement, but just to make the compiler happy!
		}
		
		dataObject.updateLatency(this.cacheLatency);
		return dataObject;
	}
	
	
	private Content getData(ContentID contentID, boolean isPrimary) 
	{
		logger.debug("This is to check if this line get called. and ContentID = "+ contentID);
		ArrayList<CacheDataBlock> blockList = new ArrayList<>();
		ContentMetaData contentMetaData = Utility.catalog.getContentMetaData(contentID);
		logger.debug("ContentMeta Data= " + contentMetaData);
		ArrayList<BlockID> listOfBlockIDs = contentMetaData.getListOfBlockIDs();
		logger.debug("List of Block IDs = " + listOfBlockIDs);
		for(BlockID blockID : listOfBlockIDs)
		{
			CacheDataBlock cacheDataBlock = this.getData(blockID, isPrimary);
			blockList.add(cacheDataBlock);
		
		}
		long contentSize =listOfBlockIDs.size() * Constants.BLOCK_SIZE ;
		return new Content(contentID,contentSize,blockList );
	}
	
	
	private Data  getData(Address address)
	{
		SetID setID = getSetIDFromAddress(address);
		CacheSet cacheSet = setCollection.get(setID);
		if(cacheSet == null)
		{
			logger.debug("CacheSet is null");
			cacheSet = new CacheSet(setID, this.setSize, this.blockSize, this.replacementPolicy, cacheName);
			setCollection.put(setID, cacheSet);
		}
		
		logger.debug("This if before cacheSet.getData(address.....) is about to called. ");
		if(secondarySources == null)
		{
			logger.debug("Secondary Source is Null");
		}
		else
		{
			logger.debug("Secondary Source is not null");
		}
		return cacheSet.getData(address, this.dataSource, this.secondarySources, getCacheName(), levelStatistics, localStatistics);
	}
	
	
	private CacheDataBlock  getData(BlockID blockID, boolean isPrimary)
	{
		CacheSet cacheSet;
		SetID setID = getSetIDFromBlockID(blockID);
		logger.debug("In getData(BlockID= " + blockID + "), " + getCacheName() + " setCollection contains " + setCollection.size() + " sets");
		if(setCollection.containsKey(setID))
		{
			logger.debug("In getData(BlockID= " + blockID + "), setCollection contains Key(SetID= " + setID + ")");
		}
		else
		{
			if(!(isPrimary))
			{
				logger.debug(" Didn't find Set in Secondary Source at All...moving to next secondary Source OR Primary Source");
				return null ;
			}
			
			logger.debug("In getData(BlockID= " + blockID + "), setCollection does NOT contain Key(SetID= " + setID + ")");
			cacheSet = new CacheSet(setID, this.setSize, this.blockSize, this.replacementPolicy, cacheName);
			setCollection.put(setID, cacheSet );
		}
				
		cacheSet = setCollection.get(setID);
		return cacheSet.getBlock(blockID, this.dataSource ,this.secondarySources, getCacheName(), levelStatistics, localStatistics, isPrimary);
	}
	
	private SetID  getSetIDFromAddress(Address address)
	{
		long setID = (address.getLongValue() / setSizeInByte) % numberOfCacheSets;
		logger.debug("In getSetIDFromAddress(), address= " + address + ", setID= " + setID);
		return new SetID(setID);
	}
	
	
	private SetID  getSetIDFromBlockID(BlockID blockID)
	{
		SetID setID = new SetID( (blockID.getLongValue() / setSize) % numberOfCacheSets); 
		logger.debug("In getSetIDFromBlockID(), blockID= " + blockID + ", setID= " + setID);
		return setID;
	}
	
	

	
	
	private boolean isPositivePowerOfTwo(long value)
	{
		return (  (value > 0) && ((value & (value-1)) == 0)  );
	}
	
	
	//********************
	private String cacheName = "";
	private long cacheSize = Constants.CACHE_SIZE;  // in Bytes (or number of address-data entries
	private long setSize = Constants.INVALID_VALUE;  // number of blocks per set (associvity)
	private long blockSize = Constants.BLOCK_SIZE;
	private long setAssocivity = Constants.INVALID_VALUE;
	private ReplacementPolicy replacementPolicy = null;
	private CacheMapping cacheMapping = null;
	private long setSizeInByte = blockSize * setAssocivity; // This is the number of address-data entries in a set
	private DataSource dataSource = null;
	private long numberOfCacheSets = Constants.INVALID_VALUE;// cacheSize /*in bytes*/ / setSizeInByte  /*in bytes*/ ;
	private Map<SetID,CacheSet> setCollection = null; // pair (setID, CacheSet)
	private Statistics levelStatistics = null;
	private Statistics localStatistics = null;
	private Collection<DataSource> secondarySources = null;
	private long cacheLatency = Constants.INVALID_VALUE;
	

   //class CacheSet
	
	//util classes
}