package PackageOne;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.*;

/**
 * This class represent each set of the cache. 
 *@version 1.0 
 */
public class CacheSet 
{
    final static Logger logger = LogManager.getLogger();
    
    /**
     * This function return the dataBlock from the cache.
     * @param blockID {@link BlockID} - The BlockID
     * @param dataSource {@link DataSource} - The DataSource
     * @param secondarySources  The Secondary Sources
     * @param cacheName The Cache Name
     * @param levelStatistics The Level Statistics
     * @param localStatistics The LocalStatistics
     * @param isPrimary Is Primary
     * @return cacheDataBlock - {@link CacheDataBlock} - The CacheDataBlock
     */
    
    
    public CacheDataBlock getBlock(BlockID blockID, DataSource dataSource, Collection <DataSource> secondarySources, String cacheName, Statistics levelStatistics, Statistics localStatistics, boolean isPrimary)
    {
    	//statistics.updateRequestCount();
    	if( cacheSetBlockCollection == null )
		{
			//This should never happen since ctor calls new on this collection 
			logger.fatal("cacheSetBlockCollection is null. Terminating program.");
			System.exit(0); 
		}
		
    	if(cacheSetBlockCollection.containsKey(blockID))	
    	{
			logger.debug("Cache Name: "+ cacheName);
			if(!isPrimary)
			{
				logger.debug("***** Secondary Cache HIT********");
			}
			if(isPrimary)
			{
				logger.debug("***** Cache HIT********");
				levelStatistics.updateHitCount();
				localStatistics.updateHitCount();
			}
		} 
    	else
    	{
    		if(!(isPrimary))
    		{
    			logger.debug("******* Secondary Cache Miss ******** ");
    			return null;
    		}
			logger.debug("******* Cache Miss ********* ");
			localStatistics.updateMissCount();
			levelStatistics.updateMissCount();
			

			//if set is full, select and remove a victim block
			if(cacheSetBlockCollection.size() == setSize)  //set is full
			{ 
				this.selectAndRemoveVictimBlock();
			}
			
			CacheDataBlock cacheDataBlock = readDataBlockFromDataSource( blockID, dataSource, secondarySources );
						
			//Insert block into set
			insertBlockInSet(blockID, cacheDataBlock);    //insert updates the block's FIFO & LRU timestamps
			
			if(cacheSetBlockCollection.size() > setSize) 
			{
				logger.fatal("Removed a victim block form a non-full set? Block collection not equals set size. Terminating program!");
				System.exit(0);
			}
    	}
	
    	CacheDataBlock cacheDataBlock = cacheSetBlockCollection.get(blockID);
    	return cacheDataBlock;
    }

	/**
	 * This function returns data.
	 * @param address {@link Address} - The Address
	 * @param dataSource {@link DataSource} - The DataSource
	 * @param secondarySources The Secondary Sources
	 * @param cacheName The Cache Name
	 * @param levelStatistics The Level Statistics
	 * @param localStatistics The Local Statistics
	 * @return data - {@link Data} - The Data
	 */
	public Data getData(Address address, DataSource dataSource, Collection <DataSource> secondarySources, String cacheName, Statistics levelStatistics, Statistics localStatistics)
	{
		//statistics.updateRequestCount();
		BlockID blockID = Utility.getBlockIDFromAddress(address);
		CacheDataBlock cacheDataBlock = null;
		
		if(cacheSetBlockCollection == null)
		{
			logger.debug("CacheSetBlockCollection is Null"); 
			//levelStatistics.updateMissCount();
			cacheDataBlock = this.readDataBlockFromDataSource(blockID, dataSource, secondarySources);
		}
		
		if(cacheSetBlockCollection.containsKey(blockID))
		{
			logger.debug("Cache Name: "+ cacheName);
			logger.debug("***** Cache HIT********");
			levelStatistics.updateHitCount();
			localStatistics.updateHitCount();
		}
		else
		{
			logger.debug("Cache Name: "+ cacheName);
			logger.debug("*******Cache Miss ********* ");
			if(secondarySources == null)
			{
				logger.debug("Secondary Source is Null");
			}
			else
				logger.debug("Secondary Source is not null");
			//process miss stats
			levelStatistics.updateMissCount();
			localStatistics.updateMissCount();
			
			if(cacheSetBlockCollection.size() == setSize)  //set is full
			{ 
			    this.selectAndRemoveVictimBlock();
			}
			
			cacheDataBlock  = readDataBlockFromDataSource(blockID, dataSource, secondarySources); 
			insertBlockInSet(blockID, cacheDataBlock);  // insert also updates the block's FIFO & LRU timestamps
			if(cacheSetBlockCollection.size() > setSize) 
			{
				logger.fatal("Removed a victim block form a non-full set? Block collection not equals set size. Terminating program!");
				System.exit(0);
			}
		}
		
		cacheDataBlock = cacheSetBlockCollection.get(blockID);
		if(cacheDataBlock == null)
		{
			logger.fatal("cache Block stays null. Terminating program.");
			System.exit(0);
		}
		
		return cacheDataBlock.getData(address);
	}
	
	
	
	private void selectAndRemoveVictimBlock()
	{
		BlockID victimBlockID = getVictimBlockID();
		logger.debug("In selectAndRemoveVictimBlock(), victim BlockID= " + victimBlockID );
		if( victimBlockID == null)
		{ 
			logger.fatal("Victim BlockID is null. Terminating program.");
			System.exit(0); 
		}
				
		CacheDataBlock victimCacheDataBlock = this.removeVictimBlock(victimBlockID);
		if( victimCacheDataBlock == null)
		{ 
			logger.fatal("Victim Block is null. Terminating program.");
			System.exit(0); 
		}
	}
	
	
	private CacheDataBlock readDataBlockFromDataSource(BlockID blockID, DataSource dataSource, Collection <DataSource> secondarySources)
	{
		logger.debug("In readDataBlockFromDataSource(), blockID= " + blockID );
		DataLocator dataLocator = new DataLocator(DataLocatorType.BLOCK , null, blockID, null);
		logger.debug("Secondary Sources = "+ secondarySources);
		if(secondarySources != null)
		{
			for(DataSource secSource : secondarySources)
			{
				DataObject dataObject = secSource.getData(dataLocator, false);
				
				if( dataObject != null)
				{
					CacheDataBlock resultBlock = dataObject.getDataBlock();
					return resultBlock;
				}
			}
		}
		
		DataObject dataobject = dataSource.getData(dataLocator, true);
		return dataobject.getDataBlock();
	}
	
	
	
	private BlockID getVictimBlockID()
	{
		BlockID blockID = null;
		
		switch(replacementPolicy)
		{
			case RANDOM:
				blockID = getRANDOM_Block();
				break;
				
			case FIFO:
				blockID = getFIFO_Block();
				break;
				
			case LRU:
				blockID = getLRU_Block();
				break;
				
			default:
				blockID = null;
		}
		
		logger.debug("In getVictimBlockID(), Victim BlockID= "+ blockID);
		return blockID;
	}
	

	
	private CacheDataBlock removeVictimBlock(BlockID blcokID)
	{
		if(cacheSetBlockCollection.size() < setSize)
		{
			logger.fatal("Attempting to remove a block while Set is not already full. Terminating program!");
			System.exit(0);
		}
		
		return cacheSetBlockCollection.remove(blcokID);
	}
	
	
	
	private void insertBlockInSet(BlockID blockID, CacheDataBlock cacheDataBlock)
	{
		cacheDataBlock.updateLRU_Timestamp();
		cacheDataBlock.updateFIFO_Timestamp();
		cacheSetBlockCollection.put( blockID, cacheDataBlock);
		
		if(cacheSetBlockCollection.size() > setSize)
		{
			logger.fatal("Cache.java: items in block > block size. Terminating program!");
			logger.fatal("cacheSetBlockCollection.size() = "+ cacheSetBlockCollection.size() + ",  setSize = "+setSize );
			System.exit(0);
		}
	}
	
	
	
	private BlockID getLRU_Block()
	{
		Set<BlockID> blockIDs = cacheSetBlockCollection.keySet();
		Iterator<BlockID>	iterator = blockIDs.iterator();
		
		long leastLRU_timestamp = Long.MAX_VALUE; //initialize to highest value
		BlockID leastLRU_BlockID = null;
		
		while( iterator.hasNext() )
		{
			BlockID blockID = iterator.next();
			CacheDataBlock cacheDataBlock = cacheSetBlockCollection.get(blockID);
			
			if(cacheDataBlock.getLRU_timestamp() < leastLRU_timestamp)
			{
				leastLRU_timestamp = cacheDataBlock.getLRU_timestamp();
				leastLRU_BlockID = blockID;
			}
		}
		
		if(leastLRU_BlockID.getLongValue() == Constants.INVALID_VALUE)
		{
			logger.fatal("Failed to set LRU block ID. Exiting....");
			//System.out.println("Cache.java: Failed to set LRU block ID. Exiting ...\n");
			System.exit(0);				
		}
		
		return leastLRU_BlockID;
	}
	
	
	private BlockID getFIFO_Block()
	{
		Set<BlockID> blockIDs = cacheSetBlockCollection.keySet();
		Iterator<BlockID>	iterator = blockIDs.iterator();
		
		long leastFIFO_timestamp = Long.MAX_VALUE; //initialize to highest value
		BlockID leastFIFO_BlockID = null;
		BlockID blockID = null;
		
		while( iterator.hasNext() )
		{
			blockID = iterator.next();
			CacheDataBlock cacheDataBlock = cacheSetBlockCollection.get(blockID);
			
			if(cacheDataBlock.getFIFO_timestamp() < leastFIFO_timestamp)
			{
				leastFIFO_timestamp = cacheDataBlock.getFIFO_timestamp();
				leastFIFO_BlockID = blockID;
			}
		}
		
		if( leastFIFO_BlockID.getLongValue() == Constants.INVALID_VALUE )
		{
			logger.fatal("Failed to set FIFO block ID. Terminating Program");
			System.exit(0);
		}
		
		return leastFIFO_BlockID;
	}
	
	
	
	private BlockID getRANDOM_Block()
	{
		BlockID victimBlockID = null;
		long victimIndex = Constants.ZERO_VALUE;
		Random random = Constants.random;
		int randomID =  random.nextInt((int)setSize);
		logger.debug("In getRANDOM_Block(),  RandomID = "+randomID);
		 
		Set<BlockID> blockIDs = cacheSetBlockCollection.keySet();
		Iterator<BlockID> iterator = blockIDs.iterator();
		BlockID blockID = null;
		
		while( iterator.hasNext() )
		{
			blockID = iterator.next();
			
			if( victimIndex == randomID)
			{
				victimBlockID = blockID;
			}
		   
			victimIndex++;
		}
		
		if(victimBlockID == null)
		{
			logger.fatal("Failed to set RANDOM block ID. Terminating program");
			//System.out.println("Cache.java: Failed to set RANDOM block ID. Exiting ...\n");
			System.exit(0);
		}
		return victimBlockID;
	}
	
	
	//ctors
	/**
	 * This constructor creates the object for cacheSet with an empty set.
	 * @param setID {@link SetID} - The SetID
	 * @param setSize The Set Size
	 * @param blockSize The Block Size
	 * @param replacementPolicy {@link ReplacementPolicy} - The ReplacemntPolicy
	 * @param cacheName The Cache Name
	 */
	public CacheSet(SetID setID, long setSize, long blockSize, ReplacementPolicy replacementPolicy, String cacheName)
	{
		this.setID = setID;
		this.setSize = setSize;
		this.blockSize = blockSize;
		//this.numberOfCacheSets = numberOfCacheSets; // *********************************   &&&&&&&&&&&&&&&&&&&&
		this.cacheSetBlockCollection = new HashMap<BlockID,CacheDataBlock>(); //change to ctor with capacity (= set size in number of blocks)
		this.replacementPolicy = replacementPolicy;
		this.cacheName = cacheName;
	}
	
	
	private CacheSet(){}
	
	
	private BlockID getBlockID(Address address)
	{
		return new BlockID( getBlockID(address.getLongValue() ) );
	}
	
	
	private Long getBlockID(Long address)
	{
		return address.longValue() / blockSize;
	}
	
	
	private ReplacementPolicy replacementPolicy;
	private HashMap<BlockID, CacheDataBlock> cacheSetBlockCollection;
	private SetID setID;
	private long setSize;  //in number of blocks (dataLines)
	private long blockSize ; // in Bytes
	//private long numberOfCacheSets;
	private String cacheName;
}