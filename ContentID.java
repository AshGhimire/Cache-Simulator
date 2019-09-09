package PackageOne;

/**
 * This class identifies each content with a contentID.
 *@version 1.0
 */
public class ContentID
	{
	
		/**
		 *This method returns the Long value of contentID.
		 *@return Long This returns the Long value of contentID.
		 */
		public Long getLongValue() {return contentID;}
		
		/**
		 * This constructor takes the long value of contentID.
		 * @param contentID This is the long value of contentID. 
		 */
		public ContentID(Long contentID) {this.contentID = contentID;}
		
		/**
		 * This constructor takes the long value of contentID.
		 * @param contentID This is the new long value of contentID.
		 */
		public ContentID(long contentID) {this.contentID = new Long(contentID);}
		
		/** 
		* Returns a string representation of the object. 
		* In general, the toString method returns a string that "textually represents" this object.
		* @return contentID The ContentID
		*/
		public String toString() {return contentID.toString();}
		
		/**
		 * This method returns the hash code value for contentID.
		 * @return contentID The ContentID
		 */
		public int hashCode() {return contentID.hashCode();}
		
		/**
		 * Indicates whether some other object is "equal to" this one. 
		 * @param obj the reference object with which to compare. 
		 * @return true  if this object is the same as the obj argument; false otherwise.
		 */	
		public boolean equals(Object obj)
		{
			boolean result = false;
			if (obj instanceof ContentID) 
			{
				ContentID otherContentID = (ContentID) obj;
				result = this.contentID.equals( otherContentID.contentID) ;
			}
			
			return result;
		}
		
		
		private ContentID() {} // intentionally private
		private Long contentID;
	}