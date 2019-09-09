package PackageOne;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class defines features of the content and holds starting address.
 *@version 1.0
 */
public class ContentMetaData 
{
    final static Logger logger = LogManager.getLogger();
    
    /**
     * This is a value between 1-100.This shows how much the content has been requested. 
     * @param address The Address - {@link Address}
     * @param listOfBlockIDs The list of BlockIDs
     * @param contentPopularity The popularity of each content.
     */
	public ContentMetaData(Address address, ArrayList<BlockID> listOfBlockIDs, long contentPopularity)
	{
		this.listOfBlockIDs = listOfBlockIDs;
		this.address = address;
		this.contentPopularity = contentPopularity;
		if((contentPopularity < 0) || (contentPopularity > 100))
		{
			logger.debug("Content Popularity should be 1 - 100. Setting Default Value = 50.");
			this.contentPopularity = 50;
		}
		
	}
	
	/**
	 * This makes a list of BlockID's.
	 * @return listOfBlockIDs - The list of BlockIDs
	 */
	public ArrayList<BlockID> getListOfBlockIDs()
	{
		return this.listOfBlockIDs;
	}
	
	/**
	 * This returns the address.
	 * @return address
	 */
	public Address getAddress()
	{
		return this.address;
	}
	
	/**
	 * This returns the amount of times the content has beem requested.
	 * @return contentPopularity 
	 */
	public long getContentPopularity()
	{
		return this.contentPopularity;
	}
	
	/**
	 * Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	 * This constructor takes the Long (wrapper class) value of BlcokID's.
	 */
	public String toString()
	{
		return "list of BlockIDs = "+ this.listOfBlockIDs + ", first address = " + address+ ", Content Popularity = "+ this.contentPopularity;
	}
	
	private ArrayList<BlockID> listOfBlockIDs;
	private Address address;
	private long contentPopularity;


}
