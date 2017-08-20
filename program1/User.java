import java.util.Random;
/**
 * This class creates a user profile who can interact with the machines on
 * the cloud with their username, password, and credits. 
 *
 * <p>Bugs: None Known
 *
 */
public class User {
	//Random number generator, used for generateMachineStock. DO NOT CHANGE
	private static Random randGen = new Random(1234);

	private String username;
	private String passwd;
	private double credit;
	private ListADT<Machine> machineList;

	/**
	 * Constructs a User instance with a name, password, credit and an empty 
	 * machineList. 
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @param credit amount of credit the user had in $ 
	 */
	public User(String username, String passwd, double credit) {
		this.username = username;
		this.passwd = passwd;
		this.credit = credit;
		//Creates an empty machine list
		machineList = new DLinkedList<Machine>(); 

	}

	/**
	 * Checks if login for this user is correct.
	 *
	 * @param username the name to check
	 * @param passwd the password to check
	 * @return true if credentials correct, false otherwise
	 * @throws IllegalArgumentException if arguments are null 
	 */
	public boolean checkLogin(String username, String passwd) {
		//check if the username and passwd are valid.
		if(username == null || passwd == null) 
			throw new IllegalArgumentException();
		//Only if username and passwd are both matched, then return true.
		//false otherwose.
		if(username.equals(this.username)){
			if(passwd.equals(this.passwd))
				return true;
		}
		return false;
	}

	/**
	 * Adds a machine to the user's machineList. 
	 * Maintain the order of the machineList from highest priced to lowest 
	 * priced machines.
	 * @param machine the Machine to add
	 * @throws IllegalArgumentException if arguments are null 
	 */
	public void addToMachineList(Machine machine) {
		//First check if machine is valid
		if(machine == null) throw new IllegalArgumentException();
		//Case 1 when there is no machine, then the new machine will be added 
		//as the first one.
		if(machineList.size() == 0){
			machineList.add(machine);
			return;
		}
		//if it is not an empty machineList.
		if(machineList.size() > 0){
			if(machineList.get(machineList.size() -1 ).getPrice() 
					>= machine.getPrice()){
				//if it is cheaper than the cheapest on the list, add it to the
				//end of the list.
				machineList.add(machineList.size(),machine);
				return;
			}
			//otherwise, cycle through the machine list and add it to the position
			//where its price is less than that of the last machine. 
			for(int i = 0; i < machineList.size(); i++){
				if(machine.getPrice() > machineList.get(i).getPrice()){
					machineList.add(i,machine);
					return;
				}
			}
		}
	}


	/**
	 * Removes a machine from the user's machineList. 
	 * Do not charge the user for the price of this machine
	 * @param machineName the name of the machine to remove
	 * @return the machine on success, null if no such machine found
	 * @throws IllegalArgumentException if arguments are null
	 */
	public Machine removeFromMachineList(String machineName) {
		if(machineName == null) throw new IllegalArgumentException();
		//If we have an empty machineList.
		if(machineList.size() == 0){
			return null;
		}
		//if the machinelist is not empty, cycles through the machinelist.
		for(int i = 0; i < machineList.size(); i++){
			if( machineName.equals(machineList.get(i).getName())){
				//check if the machine that is trying to remove is on the list.
				//if found, put it at the temporarily variable.
				Machine temp = machineList.get(i);
				//then perform the remove operation in DlinkedList.
				machineList.remove(i);
				return temp;
			}
		}
		return null;
	}

	/**
	 * Print each machine in the user's machineList in its own line by using
	 * the machine's toString function.
	 */
	public void printMachineList() {
		//Only print the machine when there is at least one in the list.
		 if(machineList.size() > 0 ){
			//cycles through the machineList, and print each machine.
			for(int i = 0; i < machineList.size(); i++)
				System.out.println(machineList.get(i).toString());
		}
		
	}

	/**
	 * Rents the specified machine in the user's machineList.
	 * Charge the user according to the price of the machine by updating the 
	 * credit.
	 * Remove the machine from the machineList as well.
	 * Throws an InsufficientCreditException if the price of the machine is 
	 * greater than the credit available. Throw the message as:
	 * Insufficient credit for <username>! Available credit is $<credit>.
	 * 
	 * @param machineName name of the machine
	 * @return true if successfully bought, false if machine not found 
	 * @throws InsufficientCreditException if price > credit 
	 */
	public boolean rent(String machineName) throws InsufficientCreditException{
		//Cycles through the machinelist.
		for(int i = 0; i < machineList.size(); i++){
			//find the machine that the user wants to rent
			if(machineName.equals(machineList.get(i).getName())){
				//if the user does not have enough credit to rent the machine,
				//throw an InsufficientCreditException and print out the error
				//message.
				if(credit < machineList.get(i).getPrice()){
					throw new InsufficientCreditException(
							machineList.get(i).getName(),username,credit);
				}else{
					//if the credit is sufficient, charge the user the price of 
					//the machine and removed it from his/her machineList.
					credit = credit - machineList.get(i).getPrice();
					removeFromMachineList(machineName);
					return true;
				}
			}
		}
		return false;
	}

	/** 
	 * Returns the credit of the user
	 * @return the credit
	 */
	public double getCredit() {
		return this.credit;
	}

	/**
	 * This method is already implemented for you. Do not change.
	 * Declare the first n machines in the currentUser's machineList to be 
	 * available.
	 * n is generated randomly between 0 and size of the machineList.
	 * 
	 * @returns list of machines in stock 
	 */
	public ListADT<Machine> generateMachineStock() {
		ListADT<Machine> availableMachines = new DLinkedList<Machine>();

		int size = machineList.size();
		if(size == 0) return availableMachines;

		//N items in stock where n >= 0 and n < size
		int n = randGen.nextInt(size+1); 

		//pick first n items from machineList
		for(int ndx = 0; ndx < n; ndx++)
			availableMachines.add(machineList.get(ndx));

		return availableMachines;
	}

}
