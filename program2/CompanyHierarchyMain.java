
import java.util.*;
import java.io.*;
/**
 * 
 * The CompanyHierarchyMain class provide an interactive environment to allow
 * users to interact with the program
 *
 * <p>Bugs: None known
 *
 */
public class CompanyHierarchyMain {
	
	/**
	 * Private helper method used to parse integer and trim them with regular 
	 * expressions.
	 * @param trimMe the String of input that needs to be parsed and trimmed.
	 * @param splitMe the regular expression symbol ("," in this program).
	 * @param position the position of the particular integer in the String
	 * @return the integer that is parsed and trimmed.
	 */
	private static int parseAndTrimInt(String trimMe,String splitMe, int position){
		return Integer.parseInt(trimMe.split(splitMe)[position].trim());
	}
	/**
	 * Private helper method used to trim the String with regular expressions.
	 * @param trimMe the String of input that needs to be trimmed.
	 * @param splitMe the regular expression symbol ("," in this program).
	 * @param position the position of the particular subString in the String
	 * @return the subString that it has found and trimmed.
	 */
	private static String splitandTrim(String trimMe, String splitSym, int position){
		return  trimMe.split(splitSym)[position].trim();
	}

	/**
	 * This method is used to load the file and add the employees to form the initial
	 * company tree.
	 * @param args[] the file that contains the information about the existing 
	 * 					company's hierarchy
	 */
	private static CompanyHierarchy checkInputAndReturnTree (String [] args) {
		// *** Step 1: Check whether exactly one command-line argument is given *** 
		if(args.length != 1){
			System.out.println("Usage: java -cp . CompanyHierarchyMain FileName");
			return null;
		}
		else{
			try{
				//Load the file.
				File newFile = new File(args[0]);
				Scanner readFile = new Scanner(newFile);
				//create a new companyHierarchy instance 
				CompanyHierarchy myHierarchy = new CompanyHierarchy();;
				if (readFile.hasNextLine()){
					//add the ceo to the tree.
					String newLine = readFile.nextLine();
				    // splitandTrim method to split and trim the input string
					String CEOName = splitandTrim(newLine, ",", 0);
					//parseAndTrimInt method split and trim the integer input
					int CEOId = parseAndTrimInt(newLine, ",", 1);
					String CEODOJ = splitandTrim(newLine, ",", 2);
					String CEO = splitandTrim(newLine, ",", 3);
					Employee ceo = new Employee(CEOName,CEOId,CEODOJ,CEO);
					myHierarchy.addEmployee(ceo, 0, null);
					//add the regular employees
					while(readFile.hasNextLine()){
						String employeeLine = readFile.nextLine().trim();
						String name = splitandTrim(employeeLine, ",", 0);
						int Id = parseAndTrimInt(employeeLine, ",", 1);
						String DOJ = splitandTrim(employeeLine, ",", 2);
						String empTitle = splitandTrim(employeeLine, ",", 3);
						String supervisor = splitandTrim(employeeLine, ",", 4);
						int supervisorID = parseAndTrimInt(employeeLine, ",", 5);
						Employee newEmployee = new Employee(name,Id,DOJ,
								empTitle);
						myHierarchy.addEmployee(newEmployee, supervisorID, supervisor);
					}
				}
				readFile.close();
				return myHierarchy;
			}catch(FileNotFoundException e){
				System.out.println("Error: Cannot access input file");
			}catch(CompanyHierarchyException e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * The main method allows users to interact with the program by entering
	 * different inputs. 
	 * @param args the file that could be loaded to form the initial company tree.
	 */
	public static void main(String[] args) {
		CompanyHierarchy tree = checkInputAndReturnTree(args);
		if(tree == null){
			System.exit(0); //if no initial tree can be formed. 
		}
		boolean stop = false;
		Scanner stdin = new Scanner(System.in);
		while (!stop) {
			System.out.println("\nEnter Command: ");
			String input = stdin.nextLine();
			String remainder = null;
			//if the user enters more than one letter, the extra command is set 
			//as remainder
			if (input.length() > 0) {
				char option = input.charAt(0);
				if (input.length() > 1) remainder = input.substring(1).trim();
				//switch statement for the letter/command entered from the user
				switch (option) {
				/** a newid,newname,DOJ,title,supervisorId,supervisorName		
				 * Add a new employee with given details to the company tree. 
				 * Display "Employee added" if the addition was successful. 
				 * If there is no such supervisor in the company tree, 
				 * display "Cannot add employee as supervisor was not found!"
				 */
				case 'a': {
					try{
						//convert the user input into correct formats.
						int employeeID= parseAndTrimInt(remainder, ",", 0);
						String newNameString = splitandTrim(remainder, ",", 1);
						String dateJoinedString = splitandTrim(remainder, ",", 2);
						String titleString  = splitandTrim(remainder, ",", 3);
						int supervisorID = parseAndTrimInt(remainder, ",", 4);
						String supervisorName = splitandTrim(remainder, ",", 5);
						Employee newEmployee = new Employee(newNameString,
								employeeID, dateJoinedString, titleString);
						//if the particular employee is successfully added to the tree
						if(tree.addEmployee(newEmployee,supervisorID,supervisorName))
							System.out.println("Employee added");
						//if addEmployee return false, that means the employee already exists.
						else
							System.out.println("Employee already exists!");
					}catch(CompanyHierarchyException e){
						System.out.println(e.getMessage());
					}catch (RuntimeException e) { //avoid program crashes
						System.out.println("Invalid Command");
					}
					break;
				}
				/** s id name		Print the name(s) of all the 
				 * supervisors in the supervisor chain of the given 
				 * employee. Print the names on separate lines. 
				 * If no such employee is found, display 
				 * "Employee not found!"*/	
				case 's':{
					try{
						int id = Integer.parseInt(remainder.split(",")[0].trim());
						String nameString = remainder.split(",")[1].trim();
						//call the recursively method getSupervisorChain to 
						//search for the list of supervisors on the company tree.
						List<Employee> empList = tree.getSupervisorChain(id, nameString);
						if(!empList.isEmpty()){ //if found at least one supervisor
							//iterate and prints out all the supervisors
							Iterator<Employee> supItr = empList.iterator();
							while(supItr.hasNext())
								System.out.println(supItr.next().getName());
						}
						else //if the list is empty
							System.out.println("Employee not found!");
					}catch (CompanyHierarchyException e){
						System.out.println(e.getMessage());
					}catch(RuntimeException e){ //catch all the invalid command
						System.out.println("Invalid Command");
					}
					break;
				}
				/** d		Display information about the company tree 
				 * by doing the following:
				 * Display on a line: "# of employees in company tree: integer"
				 * This is the number of employees in this company tree.
				 *
				 * Display on a line: "max levels in company tree: integer"
				 * This is the maximum number of levels in the company tree.
				 *
				 * Display on a line: "CEO: name"
				 * This is the CEO in the company tree*/
				case 'd': {
					System.out.println("# of employees in company tree: " + 
							tree.getNumEmployees());
					System.out.println("max levels in company tree: " + 
							tree.getMaxLevels());
					System.out.println("CEO: " + tree.getCEO());
					break;
				}
				/** e title		Print the name(s) of the employee(s) 
				 *  that has the given title. Print the names on 
				 *  separate lines. If no such employee is found, 
				 *  display "Employee not found!" */
				case 'e': {
					try{
						//Create list for found employees with given title
						List<Employee> employeesList = 
								tree.getEmployeeWithTitle(remainder.trim());
						//If no employees with that title, print message
						if(employeesList.isEmpty()) 
							System.out.println("Employee not found!");
						else{
							//Otherwise, print the employees
							Iterator<Employee> itr = employeesList.iterator();
							while(itr.hasNext())
								System.out.println(itr.next().getName());
						}
					}catch(RuntimeException e){
						//Handles all invalid commands
						System.out.println("Invalid Command");
					}
					break;
				}
				/** r id name		Remove the employee with given id 
				 * and name from the company tree and re-assign the 
				 * worker's to the removed employee's supervisor. 
				 * Display "Employee removed" after the removal. 
				 * If there is no such employee in the company tree, 
				 * display "Employee not found!" */
				case 'r': {
					try{
						int id = Integer.parseInt(remainder.split(",")[0].trim());
						String nameString = remainder.split(",")[1].trim();
						//if the remove is successful, print message
						if(tree.removeEmployee(id, nameString))
							System.out.println("Employee removed");
					}catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}catch(RuntimeException e){ //catch  all the invalid commands
						System.out.println("Invalid Command");
					}
					break;
				}
				/** c id name		Print the name(s) of the 
				 * co-employees(sharing the same supervisor) of the 
				 * employee with given id and name. Print the names on 
				 * separate lines. If no such employee is found, 
				 * display "Employee not found!". If the employee has 
				 * no co-employee under the same supervisor, display 
				 * "The employee has no co-workers." */
				case 'c': {
					try{
						int id = Integer.parseInt(remainder.split(",")[0].trim());
						String nameString = remainder.split(",")[1].trim();
						//call the getCoworkers method to retrieve the list of the 
						//employee's co-workers.
						List<Employee> coworkers = tree.getCoWorkers(id, nameString);
						//if no employee found on the tree
						if(coworkers == null) System.out.println("Employee not found!");
						//if found at least one co-worker
						else if(!coworkers.isEmpty()){
							//iterate and print them out.
							Iterator<Employee> itr = coworkers.iterator();
							while(itr.hasNext())
								System.out.println(itr.next().getName());
						}
						else if(coworkers.isEmpty()) //if the employee has no co-worker
							System.out.println("The employee has no co-workers.");
					}catch (CompanyHierarchyException e) {
						System.out.println(e.getMessage());
					}catch(RuntimeException e){ 
						//avoid program crashing with invalid command
						System.out.println("Invalid Command");
					}
					break;
				}
				/** u id name newid newname DOJ title		Replace the 
				 * employee with give id and name from the company tree 
				 * with the provided employee details. 
				 * Display "Employee replaced" after the removal. If 
				 * there is no such employee in the company tree, 
				 * display "Employee not found!" */
				case 'u': {
					try{
						int employeeID = parseAndTrimInt(remainder, ",", 0);
						String oldNameString = splitandTrim(remainder, ",", 1);
						int newEmployeeID = parseAndTrimInt(remainder, ",", 2);
						String newNameString = splitandTrim(remainder, ",", 3);
						String newdateJoinedString = splitandTrim(remainder, ",",4);
						String newTitle = splitandTrim(remainder, ",", 5);
						if(tree.replaceEmployee(employeeID, oldNameString, 
								new Employee(newNameString, newEmployeeID,
										newdateJoinedString, newTitle))) 
							//if the replacement is successful.
							System.out.println("Employee replaced");
						//if the replacement is not successful,that means employee 
						//does not exist on the tree.
						else System.out.println("Employee not found!");
					}catch(CompanyHierarchyException e){
						System.out.println(e.getMessage());
					}catch (RuntimeException e){ //prevent program from crashing
						System.out.println("Invalid Command");
					}
					break;
				}
				/** j startDate endDate		Print the name(s) of the 
				 * employee(s) whose date of joining are between 
				 * startDate and endDate(you may assume that startDate 
				 * is equal to or before end date). Print the names on 
				 * separate lines. If no such employee is found, 
				 * display "Employee not found!" */
				case 'j': {
				try{
						String startDateString = splitandTrim(remainder, ",", 0);
						String endDateString = splitandTrim(remainder, ",", 1);
						//call the getEmployeeInJoingingDateRange function to retrieve
						//a list of employee whose joining date is within the particular range.
						List<Employee> myEmployees = 
							tree.getEmployeeInJoiningDateRange(startDateString, 
									endDateString);
					if(myEmployees != null){
						if(!myEmployees.isEmpty()){ 
								//iterate and prints them all out,
								Iterator<Employee> itr = myEmployees.iterator();
								while(itr.hasNext())
									System.out.println(itr.next().getName());
						}else  //if there is no such employee in the company tree.
						System.out.println("Employee not found!");
					}
					}catch(RuntimeException e){
						//To avoid program crash
						System.out.println("Invalid Command");
					}
					break;
				}
				//***exits program***
				case 'x':{
					stop = true;
					System.out.println("exit");
					break;
				}
				default:
					break;
				}

			}
		}
	}
}
