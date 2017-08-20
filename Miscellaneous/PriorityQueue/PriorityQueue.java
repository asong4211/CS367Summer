
import java.lang.Comparable;

public class PriorityQueue<E> implements PriorityQueueADT<E>, Comparable<E> {

	//****** Instance method
	private int numItems;
	private Comparable[] items;
	private Comparable[] shadowArray;
	private final int INIT = 10;



	//***** Constructor 
	public PriorityQueue(){
		items  = (Comparable[]) new Object [INIT];
		shadowArray = (Comparable[]) new Object [INIT * 2];
		numItems = 0;
	}



	//Insert into a Max Heap.
	//Algorithms: First, put item in the next free element (preserve the shape)
	//Second, reheapify by swapping with smaller parents => O(logn)
	//Third, incrementing numItems.
	@Override
	public void insert(Comparable item) {
		if(item == null) throw new IllegalArgumentException();
		//If queue is full, perform the expand operation, consider using a shadow array.
		boolean done = false;
		if(numItems ==  items.length) {
			expandArray();
		}
		
			//if the list is empty
			if(numItems == 0) items[++numItems] = item; 
			else{
				//Place items in the next available spot.
				items[numItems] = item;
				int child = numItems;
				while(!done){
					int parentsIndex = child / 2;
					if(parentsIndex == 0) done = true; // reach the end of the comparison.
					else if (items[parentsIndex].compareTo(items[child]) >= 0) done = true;
					else{
						//Swapping the two value
						Comparable temp = items[parentsIndex];
						items[parentsIndex] = items[child];
						items[child] = temp;
						child = parentsIndex;
					}
					numItems++;
				}
			}
		
	}

	@Override
	public Comparable getMax() {
		//For binary tree / heaps, the root is always going to 
		// be at the first index. i.e. the parents or
		return items[1]; 

	}

	
	//Remove root item from heap and save in temp so that it
	//can be returned. Replace root with last item in array, this
	//could allow you to preserve your shape 
	//reheapify by swapping with the largest child. 
	@Override
	public Comparable removeMax() {
		Comparable returnItem = items[1]; //remove item;
		//move the last item in the array to the root to preserve
		//the shape of the array.
		items[1] = items[numItems]; 
		items[numItems] = null;
		boolean done = false;
		int parents = 1;
		while(!done){
			//if the parent is greater than its children, then no need to swap
			if(items[parents].compareTo(items[parents*2]) > 1 &&
					items[parents].compareTo(items[parents *2 +1]) > 1){
			//if the left child is larger
				done = true;
				break;
			}
			if(items[parents * 2].compareTo(items[parents *2 + 1]) > 1){
				//swapping the value
				Comparable temp = items[parents];
				items[parents] = items[parents * 2];
				items[parents * 2] = temp;
				parents = parents * 2;
			}
			//if the right child is larger
			else if(items[parents * 2 +1].compareTo(items[parents * 2]) > 1){
				//swapping the value
				Comparable temp = items [parents];
				items[parents] = items[parents * 2 + 1];
				items[parents *2 +1] = temp;
				parents = parents * 2 + 1;
			}
		}
		numItems--;
		return returnItem;
	}

	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}
	//If you don't use a shadow array, you could use this to 
	//expand the array
	private void expandArray(){
		//Comparable[] newArray = (Comparable[]) new Object []
		Comparable [] newArray = (Comparable[]) new Object[items.length * 2];
		System.arraycopy(items, 0, newArray, 0, items.length);
		items = newArray;
	}



	@Override
	public int compareTo(E o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
