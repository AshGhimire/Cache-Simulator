package PackageOne;

/**
 * A class implement the CacheInterface to be a valid cache. Cache class implements this interface.
 *@version 1.0 
 */
public interface CacheInterface extends DataSource
{
	/**
	 * This function returns the size of the cache.
	 * @return cacheSize - The size of the cache.
	 */
	public long getCacheSize();
	
	/**
	 *This function returns the size of the set inside the cache. i.e. The number of blocks per set.
	 *@return setSize - The Set Size ( number of blocks per set)
	 */
	public long getSetSize();
	
	/**
	 *This function returns the size of each data block in the cache.
	 *@return blockSize - The Block Size
	 */
	public long getBlockSize();
	
	/**
	 * This function returns the replacement policy for the given cache. Replacement policies are FIFO, LRU or Random.
	 * @return replacementPolicy {@link ReplacementPolicy}
	 */
	public ReplacementPolicy getReplacementPolicy();
	
	/**
	 *This function returns the mapping policy to bring data blocks into the cache.
	 *@return cacheMapping {@link CacheMapping}
	 */
	public CacheMapping getMapping();
	
	/**
	 *This function returns the set associativity in the cache. This specify how many blocks are in the set.
	 *@return setAssociativity 
	 */
	public long getAssociativity();
}