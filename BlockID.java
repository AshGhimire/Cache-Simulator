package PackageOne; //TO CHECK HOW UPDATE AND MODIFICATION WORKS --2ND TIME


/**
 * The Block Id class takes long variable as blockID of the memory.
 *@version 1.0
 */
public class BlockID
{
	/**
	*This method returns the Long value of blockID
	*@return Long This returns the Long value of blockID.
	*/
	 public Long getLongValue() {return blockID;}
	
	 /**
	 * This constructor takes the long value of blockID.
	 * @param blockID This is the long value of blockID.
	 */
	public BlockID(Long blockID) 
	{
		this.blockID = blockID;
	}
	
	/**
	 * This constructor takes the long value of blockID.
	 * @param blockID This is the long value of blockID.
	 */
	public BlockID(long blockID) 
	{
		this.blockID = new Long(blockID);
	}
	
	/** 
	* Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	* @return blockID The BlockID
	*/	
	public String toString() {return blockID.toString();}
	
	/**
	 * This method returns the hash code value for blockID.
	 * @return blockID The BlockID
	 */	
	public int hashCode() {return blockID.hashCode();}
	
	/**
	 * Indicates whether some other object is "equal to" this one. 
	 * @param obj the reference object with which to compare. 
	 * @return true  if this object is the same as the obj argument; false otherwise.
	 */	
	public boolean equals(Object obj)
	{
		boolean result = false;
		if (obj instanceof BlockID) 
		{
			BlockID otherBlockID= (BlockID) obj;
			result = this.blockID.equals( otherBlockID.blockID) ;
		}
		return result;
	}
	

	/** 
	* This is no argument constructor.
	*It is made private to avoid the blockID not having value.
	*/	
	private BlockID() {} //intentionally private
	
	/**
	*This represents the blockID of the memory.
	*/
	private Long blockID;
	
}