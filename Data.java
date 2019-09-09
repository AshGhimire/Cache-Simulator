package PackageOne;


/**
* The Data class simply takes long variable as data.
*
* @version 1.0
*/
public class Data
{
	/**
	*This method returns the Long value of data.
	*@return Long This returns the Long value of data.
	*/	
	public Long getLongValue() 
	{
		return data;
	}
	
	/** 
	* Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object.This constructor takes the Long (wrapper class) value of data.
	*/
	public String toString()
	{
		return data.toString();
	}
	
	public Data(Long data) 
	{
		this.data = data;
	}
	
	/** 
	* This constructor takes the long value of data.
	* @param data This is the value of the data.
	*/
	public Data(long data) 
	{
		this.data = new Long(data);
	}
	
	/** 
	* This copy constructor takes Data value parameter.
	* It is important to avoid deleting data in memory when
	* data in cache is removed
	* @param data This is the value of the data.
	*/
	public Data(Data data) 
	{
		this(data.getLongValue());
	}
	
	/** 
	* This is no argument constructor.
	*It is made private to avoid the data not having value.
	*/
	private Data() {}
	
	/**
	*This represents the data.
	*/
	private Long data;
}