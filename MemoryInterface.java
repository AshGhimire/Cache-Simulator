package PackageOne;

/**
 * This represents the behaviour of a memory object.
 * This is not currently used.
 *@version 1.0
 */
public interface MemoryInterface
{
	Long getData(Long address);
	Long insertData(Long address, Long data);
}