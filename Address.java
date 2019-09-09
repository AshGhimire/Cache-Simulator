package PackageOne; //THIS IS TO CHECK Eclipse-SVN Syncing in same folder


/**
* The Address class simply takes long variable as address
*of the memory.
* @version 1.0
*/
public class Address
{
	/**
	*This method returns the Long value of address
	*@return Long This returns the Long value of address.
	*/
	public Long getLongValue()  {return address;}
	
	/** 
	* This constructor takes the Long (wrapper class) value of address.
	* @param address This is the value of the address. 
	*/
	public Address(Long address)  { this.address = address; }
	
	/** 
	* This constructor takes the long value of address.
	* @param address This is the new value of the address. 
	*/
	public Address(long address)  { this.address = new Long(address); }
	
	/** 
	* Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.
	* @return address The Address
	*/
	public String toString() { return address.toString(); }
	
	/**
	 * This method returns the hash code value for address.
	 * @return address The Address
	 */
	public int hashCode() { return address.hashCode(); }
	
	/**
	 * Indicates whether some other object is "equal to" this one. 
	 * @param obj the reference object with which to compare. 
	 * @return true  if this object is the same as the obj argument; false otherwise.
	 */
	public boolean equals(Object obj)
	{
		
		boolean result = false;
		if (obj instanceof Address) 
		{
			Address otherAddress = (Address) obj;
			result = this.address.equals( otherAddress.address) ;
		}
		
		return result;
	}
	
	/** 
	* This is no argument constructor.
	*It is made private to avoid the address not having value.
	*/
	private Address() {}

	/**
	*This represents the address of the memory.
	*/
	private Long address;
}