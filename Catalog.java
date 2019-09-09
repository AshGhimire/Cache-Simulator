package PackageOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This represents the relationship/connection between ContentID and ContentMetaData.
 *@version 1.0
 */
public class Catalog 
{
    final static Logger logger = LogManager.getLogger();
    
    /**
     * This contains ContentID and ContentMetaData.
     */
	public Catalog()
	{
		this.contentCatalog = new HashMap<ContentID, ContentMetaData>();
		this.populateCatalog();
	}
	
	/**
	 * This returns the contentCatalog from contentID.
	 * @param contentID The ContentID - {@link ContentID}
	 * @return contentCatalog
	 */
	public ContentMetaData getContentMetaData(ContentID contentID)
	{
		return this.contentCatalog.get(contentID);
	}
	
	private void populateCatalog()
	{
			
		BlockID blockID;
		Address firstAddressOfBlock;
		for(long i = 1; i <= Constants.NUMBER_OF_CONTENTS; i++ )
		{
			ArrayList<BlockID> listOfBlockIDs = new ArrayList<BlockID>();
			long address = (long) Constants.random.nextInt( (int) Constants.MEMORY_SIZE);
			firstAddressOfBlock = Utility.getFirstAddressInBlock(new Address(address));
			blockID = Utility.getBlockIDFromAddress(new Address(address));
			logger.debug("first Address in Block from address in populating catalog: "+ firstAddressOfBlock);
			logger.debug("BlockID from address in populating catalog: "+ blockID);
			for(int j = 0; j <3; j++)
			{
				listOfBlockIDs.add(blockID);
				blockID = new BlockID(blockID.getLongValue() + 1);
			}
			logger.debug("list of BlockIDs = " + listOfBlockIDs);
			ContentMetaData contentMetaData = new ContentMetaData(firstAddressOfBlock,listOfBlockIDs, 50);
			this.insertContent(new ContentID(i), contentMetaData);
			logger.debug("List of Block ID in contentCatalog: = " + this.contentCatalog.get(new ContentID(i)));
		}
	}
	
	/**
	 * This returns contentCatalog from contentID and ContentMetaData.
	 * @param contentID The ContentID - {@link ContentID}
	 * @param contentMetaData The ContentMetaData - {@link COontentMetaData}
	 * @return contentCatalog
	 */
	public ContentMetaData insertContent(ContentID contentID, ContentMetaData contentMetaData)
	{
		return this.contentCatalog.put(contentID, contentMetaData);
	}

	private HashMap <ContentID, ContentMetaData> contentCatalog = null;
	private long blockSize = Constants.BLOCK_SIZE;
}
