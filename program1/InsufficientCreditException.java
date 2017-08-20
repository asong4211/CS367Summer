 /* 
 * This class creates a checked exception for when the credits are insufficient.
 *
 */
public class InsufficientCreditException extends Exception {

	//*** constructors ***
	/**
     * Default constructor when no argument is passed, call the super class.
     */
	public InsufficientCreditException(){
		super();
	}
	
	/**
     * Construct the second constructor to print the error message.
     * 
     * @param machineName particular machine that the user is trying to rent.
     * @param userName the user that is trying to rent.
     * @param currCredit the user's current credit
     */
	public InsufficientCreditException(String machineName, String userName, 
			double currCredit){
		super("For renting " + machineName + 
				": Insufficient credit for " + userName + 
				"! Available credit is $" + currCredit);
	}
}
