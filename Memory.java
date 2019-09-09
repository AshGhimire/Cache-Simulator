package PackageOne;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * class Memory is a singelton static factory to be shared between all Ln caches
 * @version 1.0
 *
 */
public class Memory implements DataSource
{
	// given address, returns data. Progam crash if address is out of range
	@Override 
	/**
	 * This returns the data requested.
	 * @param dataLocator {@link DataLocator}  -The DataLocator
	 * @return dataObject  {@link DataObject} -The DataObject
	 */
	public DataObject getData(DataLocator dataLocator, boolean isPrimary)
	{
		DataObject dataObject = null;
		
		if(dataLocator.getDataLocatorType() == DataLocatorType.INVALID)
		{
			logger.fatal("Data locator type not set.");
			System.exit(1); 
		}
		else if(dataLocator.getDataLocatorType() == DataLocatorType.ADDRESS)
		{
			Address address = dataLocator.getAddress();
			Data data = this.getData(address);
			dataObject = new DataObject( DataObjectType.DATA, data, null,null );
		}
		else if(dataLocator.getDataLocatorType() == DataLocatorType.BLOCK)
		{
			BlockID blockID = dataLocator.getBlockID();
			CacheDataBlock cacheDataBlock = this.getDataBlock(blockID);
			dataObject = new DataObject( DataObjectType.CACHEDATABLOCK, null, cacheDataBlock,null);
		}
		
		else if(dataLocator.getDataLocatorType() == DataLocatorType.CONTENT)
		{
			ContentID contentID = dataLocator.getContentID();
			Content content = this.getContent(contentID);
			dataObject = new DataObject(DataObjectType.CONTENT, null, null, content);
		}
		else
		{
			logger.fatal("Unknown getData() error.");
			System.exit(1);
		}
		
		return dataObject;
	}
	
	
	private Content getContent(ContentID contentID) 
	{
		
		return null;
	}

	/** 
	 * @param address {@link Address}-The Address
	 * @return data {@link Data} - The Data
	 */
	public Data getData(Address address)
	{
		if (!addressDataCollection.containsKey(address))
		{
			logger.fatal("Address not found in memory, address: "+address+ ", Terminating Program");
			System.exit(1); 
		}
		
		return addressDataCollection.get(address) ; 
	}
	
	/**
	 * This functions returns dataSource Interface.
	 * @return dataSource 
	 */
	public DataSource getDataSource()
	{
		return this;
	}
	
	/**
	 * This builds a data block.
	 * @param address {@link Address}-The Address
	 * @param data {@link Data}-The Data
	 * @return data {@link Data}-The Data
	 */
	public Data insertData(Address address, Data data)
	{
		return addressDataCollection.put(address, data);
	}
	
	/**
	 * 	This returns the number of blocks.
	 * @return long - The memory size 
	 */
	public Long getMemorySize()
	{
		return new Long(this.MEMORY_SIZE);
	}
	
	/**
	 * This constructs a memory object. This function will fail if the memory size is not a power of 2. 
	 */
	public Memory()
	{
		
		addressDataCollection = new HashMap<Address, Data>();
		if( ! (Utility.isPowerOfTwo(MEMORY_SIZE)))
		{
			System.out.println("Memory.java: Memory ctor Terminating program!");
			System.out.println("Memory size not a power of 2, sizes: Memory_size= " + MEMORY_SIZE );
			System.exit(0); 
		}
		populateMemory();
	}
	
	
	
	private CacheDataBlock getDataBlock(BlockID blockID)
	{  
		CacheDataBlock cacheDataBlock = new CacheDataBlock(blockID);
		Address firstAddressInBlock = Utility.getFirstAddressInBlock(blockID);
		long firstAddress = firstAddressInBlock.getLongValue().longValue();
		long lastAddress = firstAddress + blockSize;
			
		for(long i = firstAddress; i < lastAddress; i++)
		{ 
			Data data = addressDataCollection.get(new Address(i)) ;
			if( data == null)
			{
				// ** log severe, also display address value in log msg - Ashim
				logger.fatal("Data = null for the given address in memory. Exiting Program....");
				System.exit(1); 
			}
			
			cacheDataBlock.addData(new Address(i), new Data(data) );
		}
		
		if(cacheDataBlock.size() == blockSize)
		{ 
			logger.debug("CacheDataBlock = blockSize");
			return cacheDataBlock;
		}
		else
		{
			// We should not be here. 
			//** Change log to severe and call System.exit(1); to crash the program - Ashim
			logger.fatal("CacheDataBlock != block size. Not expected to be here.");
			System.exit(1);
			return null;
		}
	}
	
	
	
	// Method to populate memory
	private  void populateMemory()
	{
		for( long i = 0; i < MEMORY_SIZE; i++) // loop to go through all values
		{ 
		    Address address  = new Address(Long.valueOf(i));
			Data data =  new Data(Long.valueOf( MEMORY_SIZE - i));
			insertData(address, data); // puts values into map
		} 
		
	}
	
	private static final long memoryLatency = Constants.MEMORY_LATENCY;
	private static final long MEMORY_SIZE = Constants.MEMORY_SIZE;
	private static final long blockSize = Constants.BLOCK_SIZE;
	private static MemoryInterface Interface_INSTANCE = null;
	private static Map<Address, Data> addressDataCollection = null; 
	private static final Logger logger = LogManager.getLogger();
	
}// End of class
