package PackageOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is where general used functions is placed meaning that the functiobs is used for one or more classes.
 *@version 1.0
 */
public final class Utility
{
	
    final static Logger logger = LogManager.getLogger();

	public static final Catalog catalog = new Catalog();
	
	/**
	 * This gets the first address in the block by the blockID.
	 * @param blockID The BlockID - {@link BlockID}
	 */
	public static Address getFirstAddressInBlock(BlockID blockID)
	{
		return new Address(blockSize * blockID.getLongValue().longValue());
	}

	/**
	 * This gets the first address in the block by the address.
	 * @param address The Address - {@link Address}
	 */
	public static  Address getFirstAddressInBlock(Address address)
	{
		return getFirstAddressInBlock(getBlockIDFromAddress(address));
	}
	
	/**
	 * This gets the block Id from address.
	 * @param address The Address - {@link Address}
	 */
	public static BlockID getBlockIDFromAddress(Address address)
	{
		return new BlockID (address.getLongValue().longValue() / blockSize);
	}
	
	/**
	 * This ensures and check to see if everything is at the power of two.
	 * @param value The value
	 * @return value - The value
	 */
	public static boolean isPowerOfTwo(long value)
	{
		return ( (value & (value-1)) == 0 );
	}
	

	private static final long blockSize = Constants.BLOCK_SIZE;
	
}
