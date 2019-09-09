package PackageOne;

import java.util.ArrayList;
/**
 * This class represents content and every content is identified with a contentID.
 *@version 1.0 
 */
public class Content
{
	
	//Catalog - HashMap < ContentID, ContentMetaData > 
	/**
	 * This constructor gets the size, contentID and contentBlocks.
	 * @param contentID The ContentID - {@link ContentID}.
	 * @param size The size of content.
	 * @param contentBlocks The block size of content.
	 */
	public Content(ContentID contentID, Long size, ArrayList<CacheDataBlock> contentBlocks)
	{
		this.contentID = contentID;
		this.size = size;
		this.contentBlocks = contentBlocks;
	}
	
	/**
	 * This gets and returns the contentID.
	 * @return contentID - The ContentID - {@link ContentID}
	 */
	public ContentID getContentID()
	{
		return this.contentID;
	}
	
	/**
	 * This gets and returns the size of the content.
	 * @return size
	 */
	public Long getSize()
	{
		return this.size;
	}
	
	/**
	 * This returns the contentBlocks.
	 * @return contentBlocks
	 */
	public ArrayList<CacheDataBlock> getContentBlocks()
	{
		return this.contentBlocks;
	}
	
	/**
	 * Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	 * This constructor takes the Long (wrapper class) value of content.
	 */
	public String toString()
	{
		return "ContentID = "+contentID+", size = "+size+", contentBlockList = "+this.contentBlocks;
	}
	
	/**
	 * @param content - The Content
	 */
	public Content(Content content) 
	{
		this.contentID = content.contentID;
		this.size = content.size;
		this.contentBlocks = content.contentBlocks;
	}
		
	private Content() {}
	

	private ContentID contentID;
	private long size;
	private ArrayList<CacheDataBlock> contentBlocks;
}