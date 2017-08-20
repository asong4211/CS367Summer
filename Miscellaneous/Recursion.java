
public class Recursion {
	//This is the method used to calculate the factorial of 
	//n using an ITERATIVE APPROACH! 
	public static int factorial (int n) {
		int result = 1;
		for (; n > 1; n--) 
			result = result * n;
		return result;
	}
	public static int factorialRec (int n ){
		//Base case:
		if(n == 1) return 1;
		//Recursive case
		else if(n > 1) {
			return n * factorialRec(n-1);
		}
		return 0;
	}
	public static double powerCount( double n , int m) {
		//Base case
		if( m == 1) return n;
		//Recursive case
		else if( m > 1 ) return n * powerCount(n, m-1);
		//Considering the case when you are taking the negative power of some random number,
		//turns out, you just have to do 1/that number. 
		//Below is another recursive call 
		else if ( m < 0)  return 1 / powerCount(n, -m);
		//else if m is zero, then its just n itself
		else 
			return 1;
		
	}	
	// This method is to test the problem of isPalindrome to see if two words are 
	// symmetrical. Test this with words like: eye, mom, radar, racecar.
	public static boolean isPalindrome (String s){

		if(s.length() <= 1) return true; //base case
		return s.charAt(0) == s.charAt(s.length() - 1) &&
				isPalindrome(s.substring(1, s.length() -1));

	}
	//****** Playing with displaying the Linkedlist using listnodes
		public static  void display(ListADT<String> list){
			display(list,0);
		}

		public static void display (ListADT<String> list, int currIdx){
			//When you don't have anything on your list
			if(list.isEmpty()) return; 
			//Explicit base case. When you reach the end of the list.You stop 
			//Otherwise, you will keep looping the line below. 
			if(currIdx >= list.size() ) return; 
			else{
				System.out.println(list.get(currIdx) + " ");
				display(list, currIdx + 1);
			}
		}

		//Implicit Base case example, we don't have the return base case. But you kept
		//calling this method until the condition is no longer met. 
		public static void displayAnother(ListADT<String>list, int currIdx){
			if(currIdx < list.size()){
				System.out.println(list.get(currIdx) + " ");
				displayAnother(list, currIdx + 1);
			}
		}

		
		public static void solveTowers(int count, String sourceDisk, String destinationDisk, String spareDisk){
			if(count == 1) printMove(sourceDisk, destinationDisk);
			else{
				solveTowers(count -1, sourceDisk, spareDisk, destinationDisk);
				solveTowers(1, sourceDisk, destinationDisk, spareDisk);
				solveTowers(count -1, spareDisk, destinationDisk, sourceDisk);
			}
				
			}

		public static void printMove(String from, String to){
			System.out.println("Move from " + from + " to " + to);
			}


		

	public static void main(String args[]) {
		/* Testing my Factorial and power method! 		
		 * 
		 * 
		 * 
		int result = factorial (3);
		System.out.println("The factorial of 3 is " + result);
		System.out.println("The factorial of 4 is " + factorial(4));
		System.out.println("The factorial of 5 is " + factorial(5));
		System.out.println("The recursive implementation of factorial of 3 is " + factorialRec(3));
		System.out.println("The recursive implementation of factorial of 4 is " + factorialRec(4));
		System.out.println("The recursive implementation of factorial of 5 is " + factorialRec(5));
		System.out.println("The recursive implementation of factorial of -3 is " + factorialRec(-3));
		System.out.println("The recursive imple of 2 to the first power is " + powerCount(2,1));
		System.out.println("The recursive imple of 3 to the second power is " + powerCount(3,2));
		System.out.println("The recursive imple of 4 to the third power is " + powerCount(4,3));
		System.out.println("The recursive imple of 4 to the -3 power is " + powerCount(4,-3));
		System.out.println("The recursive imple of -4 to the 3rd power is " + powerCount(-4,3));
		System.out.println("The recursive imple of 3 to the -2 power is " + powerCount(3,-2));
		System.out.println("The recursive imple of 3 to the 0th power is " + powerCount(3, 0));
		 */

		/****************************************************************************************************
		 ********************************  Playing with the Display method **********************************
	 	ListADT newAiden = new LinkedList();
		newAiden.add("Aiden");
		newAiden.add("Bob");
		newAiden.add("Cindy");
		newAiden.add("David");
		newAiden.add("Emily");
		newAiden.add("Frank");
		newAiden.add("Gabby");
		newAiden.add("Hannah");
		display(newAiden);
		
		*****************************************************************************************************
		 */
//		solveTowers(1, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
//		solveTowers(2, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
//		solveTowers(3, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
//		solveTowers(4, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
//		solveTowers(5, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
//		solveTowers(6, "SourceDisk","Destination Disk", "spareDisk");
//		System.out.println("Number of step it took " + numStep);
		solveTowers(4, "SourceDisk","Destination Disk", "spareDisk");
	}




	





}
