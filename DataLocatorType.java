package PackageOne;

/**
 * This enumeration represents the different types that can be included in a type of data. 
 *@version 1.0 
 */
public enum DataLocatorType
{
	/**
	 * The request type doesnt have any value.
	 */
	INVALID, 
	
	/**
	 * This is asking for the address.
	 */
	ADDRESS, 
	
	/**
	 * This is asking for the block.
	 */
	BLOCK,
	
	/**
	 * This is asking for the content.
	 */
	CONTENT, 
	
	/**
	 * This is asking for content or block.
	 */
	BOTH;	
}
