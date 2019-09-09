package PackageOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This represents the data returned from a getData request.
 * This can be a single data item,a block or a content. 
 *@version 1.0 
 */
public class DataObject
{
	final static Logger logger = LogManager.getLogger();
	
	/**
	 * This constructs a dataObject.
	 * @param dataObjectType {@link DataObjectType}-DataObjectType
	 * @param data {@link Data}-The Data
	 * @param cacheDataBlock {@link CacheDataBlock}-The CacheDataBlock
	 * @param content {@link Content}-The Content 
	 */
	public DataObject(DataObjectType dataObjectType, Data data, CacheDataBlock cacheDataBlock, Content content)
	{
		if ((dataObjectType == DataObjectType.INVALID) || 
	    (dataObjectType == DataObjectType.DATA && data == null) ||
		(dataObjectType == DataObjectType.CACHEDATABLOCK && cacheDataBlock == null) ||
		(dataObjectType == DataObjectType.CONTENT && content == null ) ||
		(dataObjectType != DataObjectType.DATA && dataObjectType != DataObjectType.CACHEDATABLOCK && dataObjectType != DataObjectType.CONTENT) ||
		(data == null && cacheDataBlock == null && dataObjectType == null) ||
		(data != null && cacheDataBlock != null && content == null) || 
		(data != null && cacheDataBlock  != null & content == null) ||
		(cacheDataBlock != null && content  != null && data == null) ||
		(data != null && content != null && cacheDataBlock == null) )
		
		//( (dataObjectType == DataObjectType.INVALID) || (data == null && cacheDataBlock == null) ||  (data != null && cacheDataBlock != null))
		{
			//Must specify type, set one or the other data object, not both, not neither
			//add/log4j severe message here - Ashim
			logger.fatal("Must specify type, set one or other data object, not both, not neither. Terminating Program: ");
			System.exit(1);
		}
		
		this.dataObjectType = dataObjectType;
		this.data = data;
		this.cacheDataBlock = cacheDataBlock;
		this.content = content;
	}
	
	/**
	 * This function returns a cacheDataBlock
	 * @return cacheDataBlock - {@link CacheDataBlock}- CacheDataBlock
	 */
	public CacheDataBlock getDataBlock() 
	{
		if( cacheDataBlock == null)
		{
			//add/log4j severe message here - Ashim
			logger.fatal("CachedataBlock is null. Terminating program. ");
			System.exit(1);
		}
		
		return cacheDataBlock;
	}
	
	/**
	 * This constructor returns data.
	 * @return data - {@link Data}-The Data
	 */
	public Data getData() 
	{
		if( data == null)
		{
			//add/log4j severe message here - Ashim
			logger.fatal("Data is null. Terminating program. ");
			System.exit(1);
		}
		
		return data;
	}
	
	public Content getContent()
	{
		if( content == null)
		{
			
			logger.fatal("content is null. Terminating program. ");
			System.exit(1);
		}
		return this.content;
	}
	
	public long updateLatency(long amount)
	{
		dataObjectlatency = dataObjectlatency+amount;
		return dataObjectlatency;
	}
	private DataObject() {}
	private DataObjectType dataObjectType = DataObjectType.INVALID;
	
	private Data data = null;
	private CacheDataBlock cacheDataBlock = null;
	private Content content = null;
	private long dataObjectlatency = Constants.ZERO_VALUE;
}