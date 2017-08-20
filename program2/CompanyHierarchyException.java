/**
 *  This is an unchecked Exception that could be used by the CompanyHierarchy class 
 *  to check and prints out error message for incorrect hierarchy
 *
 * <p>Bugs:  None known
 * 
 */
public class CompanyHierarchyException extends RuntimeException{
	public CompanyHierarchyException(){
		super();
	}
	public CompanyHierarchyException(String message){
		super(message);
	}
}
