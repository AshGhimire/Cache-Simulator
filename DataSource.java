package PackageOne;

/**
 *This is the interface for data source. Any class that can hold data implement this interface. 
 *@version 1.0 
 */
public interface DataSource
{
	/**
	 * This function give the dataSource.
	 * @return dataSource
	 */
	public DataSource  getDataSource();
	
	/**
	 * This returns dataObject for the given dataSource.
	 * @param dataLocator {@link DataLocator} - The DataLocator
	 * @param isPrimary - Indicates if the dataSource is primary
	 * @return dataObject
	 */
	public DataObject  getData(DataLocator dataLocator, boolean isPrimary);
}