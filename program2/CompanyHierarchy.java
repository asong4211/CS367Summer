import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * 
 * The CompanyHierarchy class creates the Company Hierarchy tree that allow 
 * users to create, access, insert, remove, replace, check for titles, check for 
 * joining dates, get lists of co-workers and supervisors, etc.
 *
 * <p>Bugs: None known
 *
 */
public class CompanyHierarchy {
	private TreeNode root;
	private int numOfPeople;
	/**
	 *Constructs a CompanyHierarchy tree 
	 */
	public CompanyHierarchy(){
		this.root = null;
		this.numOfPeople = 0;
	}
	/**
	 *Get the name of the CEO in this company tree 
	 * @return returns the the name of the CEO (root of the tree)
	 */
	public String getCEO(){
		if(root == null) return null;
		return root.getEmployee().getName();
	}

	/**
	 * Return the number of employees in this company tree
	 * @return returns the the number of employees in the company .
	 */
	public int getNumEmployees() {
		if (root == null) return 0;
		else return numOfPeople;
	}
	/**
	 * Return the number of levels in the tree : 0+ values
	 * @return returns the the height / maximum level of the tree
	 */
	public int getMaxLevels() {
		if(root == null) return 0;
		else return getHeight(root);
	}

	/**
	 * Return the employee details of given employee id and name; return null 
	 * if no such employee was found 
	 * @param id the id of the employee that trying to search
	 * @param name the name of the employee that trying to search
	 * @return returns the instance of the employee that found on the tree.
	 */
	public Employee getEmployee(int id, String name) {
		//check for invalid parameters
		checkIntParam(id);
		checkParam(name);
		try{
			//search for a particular employee with the id
			TreeNode temp = searchEmployee(id, root, null, null, null, null, 1);
			if(temp != null) { //if found on the tree
				//check if the name and the id is a match
				if(checkForNameID(name,temp)) return temp.getEmployee();
				else throw new 
				CompanyHierarchyException("Incorrect employee name for id!");
			}
		}catch(CompanyHierarchyException e){
			System.out.println(e.getMessage());
		}
		return null;

	}
	/**
	 * Adds employee as a child to the given supervisor node if supervisor 
	 * exists on tree; adds employee as root node if root node is null 
	 * @param Employee the instance of the employee that they want to add.
	 * @param supervisorId the supervisor's id
	 * @param supervisorName the name of his/her supervisor
	 * @return returns true if the employee is successfully added, false otherwise/
	 */
	public boolean addEmployee(Employee employee, int supervisorId, 
			String supervisorName) {
		//check if the parameter is valid.
		checkParam(employee);
		TreeNode supervisor = searchEmployee(supervisorId,
				root, null, null, null, null, 1);
		//Trying to add the CEO
		if(root == null && supervisor == null){
			//the CEO is not going to have any supervisor
			root = new TreeNode(employee,null);
			numOfPeople++;
			return true;
		}
		//if did not find the supervisor 
		else if (supervisor == null){
			throw new CompanyHierarchyException("Cannot add employee "
					+ "as supervisor was not found!");
		}
		//check for the supervisor parameters
		checkIntParam(supervisorId);
		checkParam(supervisorName);
		//check if the supervisor id and name is a match
		boolean correctID = checkForNameID(supervisorName,supervisor);
		TreeNode temp = searchEmployee(employee.getId(), root, null, null,
				null, null, 1);
		if(temp != null) //found an id that is the same
			//if the name is also the same, that means the employee already exists
			if(temp.getEmployee().getName().equals(employee.getName())) 
				return false;
			//otherwise the employee id has already been used.
			else throw new CompanyHierarchyException("Id already used!");
		else if(correctID){ //if the supervisor name and id is a match
			TreeNode newEmployee = new TreeNode(employee,supervisor);
			//add to his/her supervisor's worker list.
			supervisor.addWorker(newEmployee); 
			numOfPeople++;
			return true;
		}
		//if the supervisor was not found in the company
		else if(!correctID) throw new CompanyHierarchyException("Incorrect "+
				"supervisor name for id!");
		return false;
	}




	/**
	 * Method to check if the particular employee is existed in the company
	 * Returns true/false based on whether the given employee exists on the 
	 * tree 
	 * @param id the id of the employee that they want to check if exists.
	 * @param name the name of the employee that they want to check if exists
	 * @param exceptionMessage if the employee did not exists on the tree, the 
	 *                 error message to print out.
	 * @return returns true if the employee is on the tree, false otherwise.
	 */
	public boolean contains(int id, String name, String exceptionMessage) {
		//check if the id, name and exception messages are valid.
		checkIntParam(id);
		checkParam(name);
		checkParam(exceptionMessage);
		//iterate through the tree to find the particular employees.
		TreeNode containsEmp = searchEmployee(id, root, null, null, null, null, 1);
		if(containsEmp != null){ //employee found
			if(checkForNameID(name, containsEmp))//check if the name and id matches.
				return true;
			//if did not match, then catch the exception and pass in the exception message
			else throw new CompanyHierarchyException(exceptionMessage);
		}
		return false;
	}


	/**
	 * Removes the given employee(if found on the tree) and updates all the 
	 * workers to report to the given employee's supervisor; Returns true or 
	 * false accordingly
	 * @param id the id of the employee that they want to remove
	 * @param name the name of the employee that they want to remove
	 * @return returns true if the remove is successful, false otherwise.
	 */
	public boolean removeEmployee(int id, String name) {
		checkIntParam(id);
		checkParam(name);
		//search for the particular employee with an input id
		TreeNode removeNode = searchEmployee(id, root, null, null, null, null, 1);
		//if the employee is not found on the tree.
		if(removeNode == null)
			throw new CompanyHierarchyException("Employee not found!");
		//If it is trying to remove the CEO
		else if(removeNode.equals(root))
			throw new CompanyHierarchyException("Cannot remove CEO of the company!");
		else{
			//if the employee id is correct but name did not match.
			if(!checkForNameID(name, removeNode))
				throw new CompanyHierarchyException("Incorrect employee name "
						+ "for id!");
			//if the name and id matches.
			else{
				Iterator<TreeNode> removesWorkers = 
						removeNode.getWorkers().iterator();
				while(removesWorkers.hasNext()){
					//Add the removed employee's workers to the removed employee's
					//supervisor's worker list
					TreeNode employee = removesWorkers.next();
					removeNode.getSupervisor().getWorkers().add(employee);
					//update the employee's new supervisor
					employee.updateSupervisor(removeNode.getSupervisor()); 
				}
				//remove the node from the supervisor's workers list.
				removeNode.getSupervisor().getWorkers().remove(removeNode);
				numOfPeople--;
				return true;

			}

		}
	}

	/**
	 * Replaces the given employee(if found on the tree) and if title of old
	 *  and new employee match; Returns true or false accordingly 
	 * @param id the id of the employee that they want to replace
	 * @param name the name of the employee that they want to replace
	 * @param newEmployee the instance of the new employee that they want it to 
	 *                 replace by.
	 * @return returns true if the replacement is successful, false otherwise.
	 */
	public boolean replaceEmployee(int id, String name, Employee newEmployee) {
		//Check for valid inputs
		checkIntParam(id);
		checkParam(name);
		checkParam(newEmployee);
		//Check if new employee already exists in tree
		TreeNode foundEmp = searchEmployee(newEmployee.getId(),
				root, null, null, null, null, 1);
		if(foundEmp != null){
			Employee  found = foundEmp.getEmployee();
			//New employee has same info as an existing employee
			if(newEmployee.getName().equals(found.getName())
					&& newEmployee.getTitle().equals(found.getTitle()) 
					&& newEmployee.getDateOfJoining().equals(
							found.getDateOfJoining()))
				throw  new CompanyHierarchyException("Replacing employee"
						+ " already exists on the Company Tree!");
			//Only the ID matches that of an existing employee
			else throw new CompanyHierarchyException("Id already used!");
		}
		//Employee to be replaced
		TreeNode oldEmp = searchEmployee(id, root, null, null, null, null, 1);
		if(oldEmp == null) return false; //Cannot find old employee
		else{
			if(oldEmp.getEmployee().getTitle().equals(newEmployee.getTitle())) {
				oldEmp.updateEmployee(newEmployee);//title match, perform updates.
				return true;
			}
			else //title not matched.
				throw new CompanyHierarchyException("Replacement title does not "
						+ "match existing title!");
		}
	}
	/**
	 * Search and return the list of employees with the provided title; if none 
	 *  found return null 
	 * @param  title the title of that particular employee
	 * @return list of employees who had that particular title.
	 */
	public List<Employee> getEmployeeWithTitle(String title) {
		//Check if title is not null
		checkParam(title);
		//Create new list to store employees with given title
		List<Employee> eWithTitle = new ArrayList<>();
		//Call recursive method to search for employees and add them
		//to the list
		searchEmployee(0, root, null, null, title, eWithTitle, 2);
		return eWithTitle;
	}

	/**
	 *  Search and return the list of employees with date of joining within the 
	 *  provided range; if none found return null 
	 * @param  startDate the start date of the range
	 * @param endDate the end date of the range
	 * @return list of employees who were in the range of joining date.
	 */
	public List<Employee> getEmployeeInJoiningDateRange(String startDate, 
			String endDate) {
		//Check for valid inputs
		checkParam(startDate);
		checkParam(endDate);
		//Create new list to store employees with joining date within given range
		List<Employee> myJoinEmployees = new ArrayList<Employee>();
		//Adds to the list until the tree has been fully traversed, then returns
		//the list
		if(searchEmployee(0, root, startDate, endDate, null, myJoinEmployees, 3) 
				!= null) return myJoinEmployees;
		//If parsing fails, return null
		else return null;
	}
	/**
	 *  	Return the list of employees who are in the same level as the given 
	 *    employee sharing the same supervisor 	 *\
	 * @param  id the id of the employee that needs the list of coworkers.
	 * @param name the name of the employee that needs the list of coworkers.
	 * @return list of employees who are coworkers of the particular node.
	 */
	public List<Employee> getCoWorkers(int id, String name) {
		//Check for valid parameters
		checkParam(name);
		checkIntParam(id);
		TreeNode currEmp = searchEmployee(id, root, null, null, null, null, 1);
		//Create list to store workers with same supervisor
		List<Employee> coworkersList = new ArrayList<Employee>();
		//If the tree is empty
		if(currEmp == null) return null;
		else{
			//Check that name matches ID and that employee has a supervisor
			if(checkForNameID(name,currEmp) && currEmp.getSupervisor() != null){
				Iterator<TreeNode> coworkers = 
						currEmp.getSupervisor().getWorkers().iterator();
				while(coworkers.hasNext()){
					TreeNode temp = coworkers.next();
					if(!temp.equals(currEmp))//add coworkers besides that employee
						coworkersList.add(temp.getEmployee());
				}
			}
			//If the employee has no supervisor, return empty list
			else if(currEmp.getSupervisor() == null) return coworkersList;
			else 
				//Name and ID do not match
				throw new CompanyHierarchyException( "Incorrect employee "
						+ "name for id!" );
		}
		return coworkersList;
	}

	/**
	 * Returns the supervisor list(till CEO) for a given employee. 
	 *
	 * @param  id the id of the employee that needs the supervisor chain.
	 * @param name the name of the employee that needs the supervisor chain.
	 * @return list of employees who are supervisors of the particular node.
	 */
	public List<Employee> getSupervisorChain(int id, String name) {
		//Check input parameters
		checkIntParam(id);
		checkParam(name);
		TreeNode currEmp = searchEmployee(id, root, null, null, null, null, 1);
		//create a new list to store the supervisor
		List<Employee> supList = new ArrayList<Employee>(); 
		if(currEmp != null){//if the particular employee is found in the tree.
			//check if the name and the id of the employee is a match
			if(checkForNameID(name,currEmp)){
				if(currEmp.getSupervisor() == null)//trying to access the CEO's supervisor
					throw new CompanyHierarchyException("No Supervisor Chain "
							+ "found for that employee!");
				while(currEmp.getSupervisor() != null){
					//get that employee's supervisor
					TreeNode currSupervisor = currEmp.getSupervisor(); 
					supList.add(currSupervisor.getEmployee());
					currEmp = currSupervisor; // update the current
				}
			}
			else throw new CompanyHierarchyException("Incorrect employee name "
					+ "for id!" );
		}
		return supList;
	}
	//**************************** PRIVATE HELPERS *****************************
	/**
	 * Private method is used to check if the name is a match. If it is, it
	 * returns true, if the id did not match the name, it returns false.
	 * Exception would be handled locally if the particular name and id did not match.
	 *
	 * @param id the integer id that needs to be checked
	 * @param name the name of the employee that needs to be checked
	 * @param node the treeNode that contains the employees that need to be checked.
	 * @return if the name and id is a match.
	 */
	private boolean checkForNameID( String name, TreeNode node){
		if(node.getEmployee().getName().equals(name)) return true;
		return false;
	}
	/**
	 * This method is used to search for a particular TreeNode in the tree. It will 
	 * recursively calls itself until a particular TreeNode is found(with the same id), 
	 * if no TreeNode is found, it will simply returns null.
	 *
	 * @param id This is the id for the employee that is searching for
	 * @param employee This is the node that it would start searching, usually 
	 * 		  it's the root node.
	 * @param startDate This is the lower bound of the date range
	 * @param endDate This is the upper bound of the date range 
	 * @param title This is title of the employee being searched for
	 * @param myList This is the list to store the employees that satisfy the 
	 * 		  given criteria
	 * @param option This tells the method what to search for:
	 * 			1: ID
	 * 			2: title
	 * 			3: joining date range
	 * @return TreeNode This will return the TreeNode that is found on the tree.
	 */
	private TreeNode searchEmployee(int id, TreeNode employee, String startDate,
			String endDate, String title, List<Employee> myList, int option){
		//Check if the tree is empty
		if(employee == null) return employee;
		switch (option){
		case 1:{ //Search for the employee with a particular id
			//Check if the id parameter matches the employee's id
			if(employee.getEmployee().getId() == id) return employee;
			TreeNode curr = null;
			//Iterate through the employee's worker list
			Iterator<TreeNode> itr = employee.getWorkers().iterator();
			while(itr.hasNext()){
				//Recursively call this method to check if the particular employee
				//is on the tree
				curr = searchEmployee(id, itr.next(), startDate, 
						endDate, title, myList, option);
				//If employee is found, stop search and return the employee
				if(curr !=  null) break;
			}
			return curr;
		}
		case 2:{//search for the employee with a particular title
			//If employee found with title, add to list
			if(employee.getEmployee().getTitle().equals(title))
				myList.add(employee.getEmployee());
			Iterator<TreeNode> itr = employee.getWorkers().iterator();
			while(itr.hasNext())searchEmployee(id, itr.next(), startDate, endDate, 
					title, myList, option);
			break;
		}
		case 3:{//Search for the employee with a particular joining date range
			try{
				//Convert the date to the correct format
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date startDateFormat = format.parse(startDate);
				Date endDateFormat = format.parse(endDate);
				//temporary variable to store the employee's joining date.
				String joinedDate = employee.getEmployee().getDateOfJoining();
				//check if the date is in the range (inclusive) 
				if((format.parse(joinedDate).before(endDateFormat) &&
						format.parse(joinedDate).after(startDateFormat)) ||
						(format.parse(joinedDate).equals(startDateFormat) ||
								format.parse(joinedDate).equals(endDateFormat)))
					myList.add(employee.getEmployee());
				Iterator<TreeNode> itr = employee.getWorkers().iterator();
				//recursively call this method to find all the employees in the range.
				while(itr.hasNext()) searchEmployee(id, itr.next(), startDate, 
						endDate, title,myList, option); 
				return root;//To signal that the date was in the correct format
			}catch(ParseException e){
				//If date parsing failed, then return null
				System.out.println("Date parsing failed!");
			}
			
		}
		}
		return null;
	}
	/**
	 * This is the method used to measure the height / depth of the trees.
	 *
	 * @param TreeNode This should be the root of the tree.
	 * @return the height of the tree.
	 */
	private int getHeight(TreeNode root){
		if(root == null) return 0; //reach the end of the tree
		//if the particular employee does not have works, it denotes that it is
		//the leaves of the tree, therefore return 1.
		if(root.getWorkers().isEmpty()) return 1;
		//temporarily variable used to keep track of the maximum height.
		int maxHeight = 0;
		Iterator<TreeNode> itr = root.getWorkers().iterator();
		while(itr.hasNext()){
			int childHeight = getHeight(itr.next());
			//if found the depth of a particular branch is greater than 
			//the maxHeight
			if(childHeight > maxHeight)  maxHeight = childHeight; 
		}
		return maxHeight + 1;
	}
	/**
	 * This is the private helper method used to help check if the parameter 
	 * is valid.
	 * @param parameter the parameter that needs to be checked.
	 */
	private void checkParam(Object parameter) {
		if (parameter == null) throw new IllegalArgumentException();
	}
	/**
	 * This is the private helper method used to help check if the int parameter 
	 * is valid.
	 * @param parameter the integer parameter that needs to be checked.
	 */
	private void checkIntParam(int parameter) {
		if(parameter < 0 ) throw new IllegalArgumentException();
	}

}
