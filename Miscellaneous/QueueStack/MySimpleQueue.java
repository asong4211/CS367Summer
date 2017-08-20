import java.lang.*;
import java.util.*;
import java.lang.Exception;
//Implementing the QueueADT using a circular array. The concept is reuse free
//elements at front of the array. Expand the array only if all items are used. 
public class MySimpleQueue<E> implements QueueADT{
	//***Instance Field
	E[] items;
	int numItems, rearEnd, frontItem;
	public final int INITSIZE = 5;

	//***Constructor
	public MySimpleQueue(){
		this.numItems = 0;
		this.items = (E[]) new Object [INITSIZE];
		this.rearEnd = 0;
		this.frontItem = 0;
	}

	//****Override methods
	//add an item to the rear end
	@Override
	public void enqueue(Object item) {
		if(numItems == items.length){
			//when the array is full
			this.expand();
			frontItem = 0;
			rearEnd = numItems -1 ;
		}
		//reach the end of the array, goes back to -1.
		if(rearEnd == items.length - 1)
			rearEnd = -1;
		if(numItems == 0){
			items[rearEnd] = (E) item;
			numItems++;
			return;
		}
		this.rearEnd++;
		items[rearEnd] = (E)item;
		numItems++;
	}


	//remove an item at the front of the queue
	@Override
	public Object dequeue() {
		if(numItems <= 0) throw new IndexOutOfBoundsException();
		E returnItem = items[frontItem];
		items[frontItem] = null;
		frontItem++;
		numItems--;
		return returnItem;
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void print(){
		for(int i = 0; i < items.length; i++){
			System.out.print("[" + items[i] + "] ");

		}
		System.out.println();
		System.out.println("Current front is "+ items[frontItem]);
		System.out.println("Current rear is " + items[rearEnd]);
		System.out.println("Current number of item is " + numItems);
	}

	private void expand(){
		E[] newArray = (E[]) new Object[items.length * 2];
		/*
		 * System.arraycopy(src,srcPos,dest,destPos,length) method:
		First parameter: this is the source array
		Second parameter: starting position of your source array
		Third parameter: destination array
		Fourth parameter: Starting position in the destination data.
		Fifth parameter: number of array elements to be copied.
		 */

		//Copy the first half of the array
		System.arraycopy(items, frontItem, newArray, 0 , items.length - frontItem); 
		System.arraycopy(items, 0, newArray, items.length - frontItem, frontItem);
		items = newArray;
	}



}
