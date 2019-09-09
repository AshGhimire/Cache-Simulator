package PackageOne;

/**
 * This enumeration represents a user asking for data.
 *@version 1.0
 */
public enum RequestType 
{
	/**
	 * Uniform Distribution is a distribution that is constant.
	 */
	UNIFORM, 
	
	/**
	 * This is a random distribution where anything is picked.
	 */
	RANDOM,
	
	/**
	 * Normal Distribution is a standard distribution (bell curve).
	 */
	NORMAL, 
	
	/**
	 * Normal Positive Distribution is only half(positive) side of normal distribution. 
	 */
	NORMAL_POSITIVE; 
}