package PackageOne;

/**
 * This is an enumeration that represents the type of data requested. 
 *@version 1.0 
 */
public enum DataObjectType
{
	/**
	 * This is the inital value..
	 */
	INVALID,
	
	/**
	 * This is asking for data.
	 */
	DATA,
	
	/**
	 * This is asking for content.
	 */
	CONTENT,
	
	/**
	 * This is asking for contentMetaData.
	 */
	CACHEDATABLOCK;	
}