public class SimpleArrayList<E> implements ListADT<E>{

	
	//Instance Field
	private E[] myArray;
	private E[] shadowArray;
	private int numItems; //keep track of number of items
	private int secondIndex; //COME BACK to this.
	private final int INITIAL = 10;
	
	public SimpleArrayList(){
		this.myArray = (E[]) new Object [INITIAL]; //Because E[] could not be used for intantiation
		this.shadowArray = (E[]) new Object[INITIAL * 2]; //Shadow Array would always start with twice the size of the original array
		this.numItems = 0;
		this.secondIndex = 0;
	}
	
	@Override
	public boolean add(E item) {
		if(item == null) return false;
		//If the original array is full
		if(numItems >= myArray.length){
			//change the original array to point to the shadow array that we have kept.
			myArray = shadowArray;
			//Create a shadow array of twice the size of the original array
			shadowArray = (E[]) new Object[myArray.length * 2];
			//Start at position 0, we will incrementally copy everything from the original array to our 
			//new shadow array.
			secondIndex = 0;
			//add the item at the position numItems (which is currently the first position that is open).
			myArray [numItems] = item;
			//also add this to the shadow array so both of them have this new item added/
			shadowArray [numItems] = item;
			//Because the shadow array has twice the size of the original array, before the array used up, we could 
			//incrementally copy one item from the old shadow array to the new shadow array until all the items are copied
			//(at this time, the shadow array is full and it would point to the new shadow array).
			shadowArray [secondIndex] = myArray[secondIndex];
		}
		//If the original array is not full, then add it to both the original array and the shadow array.
		else{
			myArray[numItems] = item;
			shadowArray[numItems] = item;
			shadowArray[secondIndex] = myArray[secondIndex];
		}
		secondIndex++;
		numItems++;
		return true;
	}

	@Override
	public E get(int pos) {
		if(pos >= numItems || pos < 0) throw new IllegalArgumentException();
		else return myArray[pos];
	}

	@Override
	public boolean contains(E item){
		if(item == null) throw new IllegalArgumentException();
		for(int i = 0; i < numItems; i++) {
			if(myArray[i].equals(item)){
				return true;
			}
		}
		return false;
		
	}
	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public E remove(int pos) {
		//check position is valid
		if (numItems == 0) throw new IndexOutOfBoundsException();
		if (pos > numItems || pos < 0) throw new IllegalArgumentException();
		//Remove at the end of the items.
		int lastIndex = numItems - 1;
		if(pos == lastIndex){
			E returnItem = myArray[lastIndex];
			myArray[lastIndex] = null;
			shadowArray [lastIndex] = null;
			return returnItem;
		}
		//Remove items in the middle.
		else{
			E returnItem = myArray[pos];
			for(int i = pos; i < numItems -1; i++){
				myArray[i] = myArray[i + 1];
				shadowArray [i] = shadowArray [i + 1];
			}
			myArray[numItems -1] = null;
			shadowArray[numItems - 1] = null;
			return returnItem;
			
		}
		
		
		

	}
	//Invoking the recursive print function below
		public void print(){
			this.printArray(this.myArray, 0);
		}
		
		//Companion method for print function using recursion
		private void printArray(E[] myArray, int i){
			if(i >= numItems ) return;
				System.out.print("["+ myArray[i] + "]");
				printArray(myArray, i++);
			
		}
		
		//Nonrecursion print implementation
		public void NormalPrint(){
			System.out.println("This is what items you have of size " + numItems);
			for(int i = 0; i < numItems; i++){
				System.out.print("[" + myArray[i] + "] ");
			}
			System.out.println();
			System.out.println("This is what your array currently has now, of size: " + myArray.length);
			if(myArray.length >= 1){
				for(int i = 0; i < myArray.length; i++)
					System.out.print("[" + myArray[i] + "] " );
			}
			System.out.println();
			System.out.println("This is what your shadow array has now, of size: " + shadowArray.length);
			for(int i = 0; i < shadowArray.length; i++){
				System.out.print("[" + shadowArray[i] + "] ");
			}
			System.out.println();
		}
	






	

}
