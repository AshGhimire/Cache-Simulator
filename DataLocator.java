package PackageOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is known as the resource locator. This represents an address, blockID or contentID.
 *@version 1.0
 */
public class DataLocator
{
	final static Logger logger = LogManager.getLogger();
	
	/**
	 * This creates a dataLocator Object.
	 * @param dataLocatorType {@link DataLocatorType}-DataLocatorType
	 * @param address {@link Address}-Address
	 * @param blockID {@link BlockID}BlockID
	 * @param contentID {@link ContentID}-ContentID
	 */
	public DataLocator(DataLocatorType dataLocatorType, Address address, BlockID blockID, ContentID contentID)  
	{
		if( (dataLocatorType == DataLocatorType.INVALID) || 
		    (dataLocatorType == DataLocatorType.ADDRESS && address == null) ||
			(dataLocatorType == DataLocatorType.BLOCK && blockID == null) ||
			(dataLocatorType == DataLocatorType.CONTENT && contentID == null ) ||
			(dataLocatorType != DataLocatorType.ADDRESS && dataLocatorType != DataLocatorType.BLOCK && dataLocatorType != DataLocatorType.CONTENT) ||
			(address == null && blockID == null && dataLocatorType == null) ||
			(address != null && blockID != null && contentID == null) || 
			(address != null && blockID  != null & contentID == null) ||
			(blockID != null && contentID  != null && address == null) ||
			(address != null && contentID != null && blockID == null)
			)
		{
			//Must specify type, set one or the other locator,  not none
			//Also, if requesting address, address field cannot be null, likewise for Block & Block_ID
			//add/log4j severe message here - Ashim
			logger.fatal("Must specify type, set one or the other locator, not none. "+"\n"+"If requesting address, address field cannot be null, likewise for Block & Block_ID");
			System.exit(1);
		}
		
		this.dataLocatorType = dataLocatorType;
		this.address = address;
		this.blockID = blockID;
		this.contentID = contentID;
	}
	
	/**
	 * This function returns dataLocatorType.
	 * @return dataLocatorType - {@link DataLocatorType}-The DataLocatorType
	 */
	public DataLocatorType getDataLocatorType() {return this.dataLocatorType;}
	
	/**
	 * This returns the address associated with this dataLocator.
	 * @return address -{@link Address}-The Address or null if its not set.
	 */
	public Address getAddress() {return this.address;}
	
	/**
	 * This returns the blockID associated with this dataLocator.
	 * @return blockID - {@link BlockID}-The BlockID or null if its not set.
	 */
	public BlockID getBlockID() {return this.blockID;}
	
	/**
	 * This returns the contentID associated with this dataLocator.
	 * @return contentID - {@link ContentID}-The ContentID or null if its not set. 
	 */
	
	public ContentID getContentID() {return this.contentID;}
	
	/**
	 * Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	 */
	public String toString()
	{
		if(dataLocatorType == DataLocatorType.INVALID) return "INVALID";
		else if(dataLocatorType == DataLocatorType.ADDRESS) return "ADDRESS";
		else if(dataLocatorType == DataLocatorType.BLOCK) return "BLOCK";
		else if(dataLocatorType == DataLocatorType.CONTENT) return "CONTENT";
		else return "Unknown locator type";
	}
	private DataLocator() {} // disable default object ctor. 
	
	private DataLocatorType dataLocatorType = DataLocatorType.INVALID;
	private Address address = null;
	private BlockID blockID = null;
	private ContentID contentID = null;
}

