package PackageOne;

/**
* The SetID class takes long variable as setID 
*of the memory.
* @version 1.0
*/
public class SetID
	{
		
		/**
		 *This method returns the Long value of setID
		 *@return Long This returns the Long value of setID.
		 */
        public Long getLongValue() {return setID;}
		
        /**
		 * This constructor takes the long value of setID.
		 * @param setID This is the long value of setID. 
		 */
		public SetID(Long setID) {this.setID = setID;}
		
		/**
		 * This constructor takes the long value of setID.
		 * @param setID This is the new long value of setID.
		 */
		public SetID(long setID) {this.setID = new Long(setID);}
		
		/** 
		* Returns a string representation of the object. 
		* In general, the toString method returns a string that "textually represents" this object.
		* @return setID The SetID
		*/
		public String toString() {return setID.toString();}
		
		/**
		 * This method returns the hash code value for setID.
		 * @return setID The SetID
		 */
		public int hashCode() {return setID.hashCode();}
		
		/**
		 * Indicates whether some other object is "equal to" this one. 
		 * @param obj the reference object with which to compare. 
		 * @return true  if this object is the same as the obj argument; false otherwise.
		 */	
		public boolean equals(Object obj)
		{
			boolean result = false;
			if (obj instanceof SetID) 
			{
				SetID otherSetID = (SetID) obj;
				result = this.setID.equals( otherSetID.setID) ;
			}
			
			return result;
		}
		
		// intentionally private
		/** 
		* This is no argument constructor.
		*It is made private to avoid the setID not having value.
		*/
		private SetID() {} 
		
		/**
		*This represents the setID of the memory.
		*/
		private Long setID;
	}