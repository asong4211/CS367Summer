import java.util.EmptyStackException;


//This class will implements the Stack using the shadowArray class that we created.
//THe top of the stack will be located at index numItems. 
public class Stack<E> implements StackADT {

	//*******Instance Field
	ListADT<E> items;
	int numItems;

	//*******Constructor
	public Stack(){
		items = new SimpleArrayList(); //Create a new shadow array instance.
		numItems = 0;
	}
	//Add item to the very top of the list.
	@Override
	public void push(Object item) {
		if(item == null) throw new IllegalArgumentException();
		items.add((E)item);
		numItems++;
	}
	//Remove items from the end.
	@Override
	public Object pop() {
		if(numItems == 0) throw new EmptyStackException();
		E temp = items.remove(numItems - 1);
		numItems--;
		return temp;
	}
	//Look at the first item
	@Override
	public Object peek() {
		if(numItems == 0) throw new EmptyStackException();
		return items.get(numItems -1);
	}

	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}
	
	public void print(){
		for(int i = 0; i < numItems; i++){
			System.out.print("["+ items.get(i) + "] ");
		}
		System.out.println();
	}

}
