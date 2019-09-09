package PackageOne;

/**
 * This is the enumeration of replacement policy to replace dataBlocks in Cache.
 *@version 1.0 
 */
public enum ReplacementPolicy
{
	/**
	 * This picks a random block.
	 */
	RANDOM, 	
	
	/**
	 * This find the oldest block.
	 */
	FIFO,  
	
	/**
	 * This find the least recently used block.
	 */
	LRU ;	
}