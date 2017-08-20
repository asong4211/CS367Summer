
/**
 * This class allows users to rent, add, or remove machines from their machine
 * list in the Cloud.
 *
 * <p>Bugs: none known
 */

import java.util.Scanner;
import java.io.*;

/**
 * Main class which simulates the cloud environment.
 */
public class Cloud {

	//Store record of users and machines
	private static ListADT<Machine> machines = new DLinkedList<Machine>();
	private static ListADT<User> users = new DLinkedList<User>();
	private static User currentUser = null;//current user logged in

	//scanner for console input
	public static final Scanner stdin = new Scanner(System.in);

	//main method
	public static void main(String args[]) {

		//Populate the two lists using the input files: Machines.txt User1.txt 
		//User2.txt ... UserN.txt
		if (args.length < 2) {
			System.out.println("Usage: java Cloud [MACHINE_FILE] [USER1_FILE] [USER2_FILE] ...");
			System.exit(0);
		}

		//load store machines
		loadMachines(args[0]);

		//load users one file at a time
		for(int i = 1; i< args.length; i++) {
			loadUser(args[i]);
		}
		//User Input for login
		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter username : ");
			String username = stdin.nextLine();
			System.out.print("Enter password : ");
			String passwd = stdin.nextLine();

			if(login(username, passwd) != null)
			{
				//generate random items in stock based on this user's machine 
				//list
				ListADT<Machine> inStock = currentUser.generateMachineStock();
				//show user menu
				userMenu(inStock);
			}
			else
				System.out.println("Incorrect username or password");

			System.out.println("Enter 'exit' to exit program or anything else to go back to login");
			if(stdin.nextLine().equals("exit"))
				done = true;
		}

	}

	/**
	 * Tries to login for the given credentials. Updates the currentUser if 
	 * successful login
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @returns the currentUser 
	 */
	public static User login(String username, String passwd) {
		for(int i = 0; i < users.size(); i++){
			//check if the passed in username and password is correct.
			if(users.get(i).checkLogin(username, passwd) == true){
				//if it is, then currentUser will be set.
				currentUser = users.get(i);
				return currentUser;
			}
		}
		return null;
	}

	/**
	 * Reads the specified file to create and load machines into the cloud. 
	 * Every line in the file has the format: 
	 * <MACHINE NAME>#<NUMBER OF CPUs>#<MEMORY SIZE>#<DISK SIZE>#<PRICE> 
	 * Create new machines based on the attributes specified in each line and insert 
	 * them into the machines list.
	 * Order of machines list should be the same as the machines in the file.
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadMachines(String fileName) {

		try{
			//First create a variable to store the input file. 
			File newFile = new File(fileName); 
			//read the particular file that had passed in.
			Scanner readFile = new Scanner(newFile);
			while(readFile.hasNextLine()){
				String newLine = readFile.nextLine();
				//Split every line at the "#" symbol and put them to
				//according variable.
				String machineName = newLine.split("#")[0];
				int numCPUs = Integer.parseInt(newLine.split("#")[1]);
				int memorySize = Integer.parseInt(newLine.split("#")[2]);
				int diskSize = Integer.parseInt(newLine.split("#")[3]);
				double price = Double.parseDouble(newLine.split("#")[4]);
				//add the new machine to the machine list.
				machines.add(new Machine(machineName, numCPUs, memorySize,
						diskSize, price));
			}
			readFile.close();
		}catch(IOException e){
			System.out.println("Error: Cannot access file");
		}



	}

	/**
	 * Reads the specified file to create and load a user into the cloud. 
	 * The first line in the file has the format:<NAME>#<PASSWORD>#<CREDIT> 
	 * Every other line after that is the same format as the machines file:
	 * <MACHINE NAME>#<NUMBER OF CPUs>#<MEMORY SIZE>#<DISK SIZE>#<PRICE> 
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadUser(String fileName) {

		try{
			//First create a variable to store the input file. 
			File newFile = new File(fileName);
			//read the particular file that had passed in.
			Scanner readFile = new Scanner(newFile);
			if(readFile.hasNextLine()){
				String firstLine = readFile.nextLine();
				String userName = firstLine.split("#")[0];
				String password = firstLine.split("#")[1];
				double credit = Double.parseDouble(firstLine.split("#")[2]);
				//Temporarily stored the new user's information at temp.
				User temp = new User(userName,password,credit);
				//add this temporarily user to the users list.
				users.add(temp);
				while(readFile.hasNextLine()){
					String otherLines = readFile.nextLine();
					//separate the new machine information and put them at the 
					//according variable
					String machineName = otherLines.split("#")[0];
					int numCPUs = Integer.parseInt(otherLines.split("#")[1]);
					int memorySize = Integer.parseInt(otherLines.split("#")[2]);
					int diskSize = Integer.parseInt(otherLines.split("#")[3]);
					double price = Double.parseDouble(otherLines.split("#")[4]);
					//add the information about the new machine to the User temporarily
					//variable.
					temp.addToMachineList(new Machine(machineName,numCPUs,
							memorySize,diskSize,price));
				}
				readFile.close();
			}
		}catch(IOException e){
			System.out.println("Error: cannot access file");
		}
	}


	/** 
	 * Prints the entire machine inventory.
	 * The order of the machines should be the same as in input machines file.
	 * The output format should be the consolidated String format mentioned
	 * in Machine class.
	 */

	public static void printMachines() {
		//Only print the machines when there is at least one in the list.
		if(machines.size() >= 1){ 
			for(int i = 0; i < machines.size(); i++)
				System.out.println(machines.get(i).toString());
		}
	}


	/**
	 * Interacts with the user by processing commands
	 * 
	 * @param inStock list of machines that are in stock
	 */
	public static void userMenu(ListADT<Machine> inStock) {

		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter option : ");
			String input = stdin.nextLine();

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");
				//split on colon, because names have spaces in them
				if(commands[0].length() > 1)
				{
					System.out.println("Invalid Command");
					continue;
				}
				switch(commands[0].charAt(0)) {
				case 'v':
					//Check if there is valid command after v
					if(commands.length != 2)
						System.out.println("Invalid Command");
					//Print all machines in the cloud.
					else if (commands[1].equals("all"))
						printMachines();
					//Print all machines the current user has in his/her machineList.
					else if(commands[1].equals("machinelist")){
						currentUser.printMachineList();
					}
					//Print all machines in the inStock list.
					else if(commands[1].equals("instock")){
						for (int i = 0; i < inStock.size(); i++)
							System.out.println(inStock.get(i).toString());
					}
					//Any other invalid command
					else
						System.out.println("Invalid Command");
					break;


					//s: search for a particular machine.
				case 's':
					//First check if there is any input after the colon.
					if(commands.length == 2){
						//Cycles through the list to find the particular machine.
						for(int i = 0; i < machines.size(); i++){
							if(machines.get(i).getName().contains(commands[1]))
								System.out.println(machines.get(i).toString());
						}
					}
					else
						System.out.println("Invalid Command");
					break;


					//add a new machine to the user's machine list.
				case 'a':
					//check if there is any command after the colon.
					if(commands.length == 2){
						//variable to keep track of whether or not certain machine
						//is added
						boolean added = false;
						for(int i = 0; i < machines.size(); i++){
							//cycles through the machinesList to check if the
							//added machine is valid.
							if(commands[1].equals(machines.get(i).getName())){
								currentUser.addToMachineList(machines.get(i));
								added = true;
								break;
							}
						}
						//If the machine is not successfully added.
						if(!added)
							System.out.println("Machine not found");
					}
					//If there is no input after the colon.
					else
						System.out.println("Invalid Command");
					break;


					//remove a particular machine from the machine list.
				case 'r':
					if(commands.length == 2){
						//This instance variable stores the machine that is 
						//removed from the list. Null if no machine is removed.
						Machine removeMachine = 
								currentUser.removeFromMachineList(commands[1]);
						//if the machine that the user trying to remove
						//is not on the list.
						if(removeMachine == null)
							System.out.println("Machine not found");
					}
					else
						System.out.println("Invalid Command");
					break;

					//rent a particular machine from the machine list.
				case 'b':

					for(int i = 0; i < inStock.size(); i++){
						try{
							//temporarily variable to keep track of whether the
							//machine is successfully rented.
							boolean tryRent = 
									currentUser.rent(inStock.get(i).getName());
							//if it is not, then print a message.
							if(tryRent == false)
								System.out.println("Machine not needed: "+
										inStock.get(i).getName());
							else if(tryRent == true)
								System.out.println("Rented "+ 
										inStock.get(i).getName());
						}catch(InsufficientCreditException e){
							//Error message is printed in the 
							//InsufficientCreditException class
						}
					}

					break;

					//Show the credit of the current user as $Credit.
				case 'c':
					System.out.println("$" + currentUser.getCredit());
					break;

					//Log out from the current user
				case 'l':
					done = true;
					System.out.println("Logged Out");
					break;

					//a command with no argument
				default:  
					System.out.println("Invalid Command");
					break;
				}
			}
		}
	}


}
