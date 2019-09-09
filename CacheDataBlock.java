package PackageOne;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This represents a block of data in the cache.
 *@version 1.0
 */
public class CacheDataBlock //extends HashMap<Address, Data>// Map of (address, data) entries
{
	final static Logger logger = LogManager.getLogger();
	
	//Actors
	/**
	 * This constructor creates a new blockID.
	 * @param blockID {@link BlockID}-BlockID
	 */
	public CacheDataBlock(BlockID blockID)
	{
		blockDataCollection = new HashMap<Address, Data>();
		this.updateFIFO_Timestamp();
		this.blockID = blockID;
	}
		
	private CacheDataBlock(){} // intentionally made private
	
	/** 
	 * @param address {@link Address}-The Address 
	 * @return Data {@link Data}The Data
	 */
	public Data getData(Address address) 
	{
		if( !(this.containsData(address)) )
		{
			logger.fatal("Address = "+address);
			logger.fatal("Attempt to read non-existing address from associated block. Terminating program.");
			//System.out.println("Cache.java: attenpt to read non-exisiting address from associated block. Exiting ...\n");
			System.exit(0);
		}
			
		this.updateLRU_Timestamp(); 
		return blockDataCollection.get(address);
	}
	
	/**
	 * This builds a data block.
	 * @param address {@link Address}-The Address
	 * @param data {@link Data}The Data
	 */
	public void addData( Address address, Data data)
	{
		blockDataCollection.put(address, data);
	}
	
	/** 
	 * This check to see if the block contains the requested data. 	
	 * @param address {@link Address}-The Address
	 * @return true or false
	 */
	public boolean containsData(Address address)
	{
		return blockDataCollection.containsKey(address);
	}
	
	/**
	 * 	This returns the number of blocks.
	 * @return long - The size 
	 */
	public long size()
	{
		return (long) blockDataCollection.size();
	}
	
	/**
	 * This refreshes the block time stamp.
	 */
	public void updateLRU_Timestamp()
	{
		LRU_timestamp = LRU_TIMESTAMP++;
	}
	
	/**
	 * This sets the block creation time stamp.	
	 */
	public void updateFIFO_Timestamp()
	{
		FIFO_timestamp =  FIFO_TIMESTAMP++;
	}
	
	/**
	 * @return LRU_timestamp
	 */
	public long getLRU_timestamp() {return LRU_timestamp;}
	
	/**	
	 * @return FIFO_timestamp
	 */
	public long getFIFO_timestamp() {return FIFO_timestamp;}
	
	/**
	 * Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	 * This constructor takes the Long (wrapper class) value of content.
	 */
	public String toString()
	{
		return "BlockId = " + this.blockID + ", Block of Data = " + this.blockDataCollection;
	}
	private HashMap<Address, Data> blockDataCollection = null;
	private BlockID blockID = null;
	private long LRU_timestamp = Constants.INVALID_VALUE;
	private long FIFO_timestamp = Constants.INVALID_VALUE;
		
	//Global Variables
	//LRU_TimeStamp
	/**
	 * Keeps track of the time data was accessed in the cache.
	 */
	public static long LRU_TIMESTAMP = 0;  // keeps track of the time data was accessed in the cache
	
	//FIFO_recordAge
	/**
	 * Keeps track of how many times data was accessed in the cache.
	 */
	public static long FIFO_TIMESTAMP = 0;  // keeps track of how many times data was accessed in the cache
	
		
} // class CacheDataBlock
	