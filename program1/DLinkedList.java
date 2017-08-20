import java.awt.List;
/**
 * 
 * This class implements the ListADT interface using a doubly-linked 
 * chain of nodes. 
 *
 * <p>Bugs: None Known
 *
 */

public class DLinkedList<E> implements ListADT<E> {
	
	
	//Instance Field
	private DblListnode<E> head; //This head node points to the first node.
	private DblListnode<E> tail; //This tail node points to the last node.
	private int numItems; //keep track of the number of Items on the list.
	
	
    /**
     * Constructor: Originally, assign the head and tail reference to null, and
     * numItems to 0.
     */
	public DLinkedList(){
		this.head = null;
		this.tail = null;
		this.numItems = 0;
	}
	
	/**
     * Adds item to the end of the List.
     * 
     * @param item the item to add
     * @throws IllegalArgumentException if item is null 
     */
	@Override
	public void add(E item) {
		if (item == null) throw new IllegalArgumentException();
		//create a new node and copy the item's information to it.
		DblListnode<E> newNode = new DblListnode<E>((E)item);
		//This is the special case if the list is empty.
		if(numItems == 0){
			this.head = newNode; 
			this.tail = newNode;
		}
		//General case, when the list is not empty.
		else{
			newNode.setPrev(tail); //assign the new node's previous pointer.
			tail.setNext(newNode);  //ask the tail to point to the new node.
			tail = newNode;//set the tail reference to the new node.
		}
		//increment the numItems 
		numItems++;
	}
	/**
     * Adds item at position pos in the List, moving the items originally in 
     * positions pos through size() - 1 one place to the right to make room.
     * 
     * @param pos the position at which to add the item
     * @param item the item to add
     * @throws IllegalArgumentException if item is null 
     * @throws IndexOutOfBoundsException if pos is less than 0 or greater 
     * than size()
     */
	@Override
	public void add(int pos, E item) {
		//check if both of the parameters are valid.
		if(item == null) throw new IllegalArgumentException();
		if(pos < 0 || pos > numItems ) throw new IndexOutOfBoundsException();
		// Special case: trying to add it at the end of the list.
		if(pos == numItems){
			this.add(item); //call the other add method.
			return;
		}
		//create a new node and copy the item's information to it.
		DblListnode<E> newNode = new DblListnode<E> ((E) item);
		//curr reference points to the first node in the list.
		DblListnode<E> curr = head;
		//Special case: trying to add the machine at the very front.
		if(pos == 0) {
			//change the curr head reference to point to the new node,
			//then change the head to the new node.
			newNode.setNext(curr);
			curr.setPrev(newNode);
			head = newNode;
		}
		//General case: trying to add the machine in the middle of the list.
		else{
			//traverse through the list until the particular position.
			for(int i = 0; i < pos; i++){
				curr = curr.getNext();
			}
			curr.getPrev().setNext(newNode);
			newNode.setPrev(curr.getPrev());
			newNode.setNext(curr);
			curr.setPrev(newNode);
		}
		numItems++;
	}
	/**
     * Returns true iff item is in the List (i.e., there is an item x in the 
     * List such that x.equals(item))
     * 
     * @param item the item to check
     * @return true if item is in the List, false otherwise
     * @throws IllegalArgumentException if item is null or list is null
     */
	@Override
	public boolean contains(E item) {
		//Check if the item is null 
		if(item == null) throw new IllegalArgumentException();
		//Create the curr reference to point to the first node in the list.
		DblListnode<E> curr = head;
		//traverse through the list.
		for (int i = 0; i < numItems; i++){
			//if the item is found in the list.
			if(curr.getData().equals(item)){
				return true;
			}
			else if(curr.getNext() == null){
				return false;
			}
			//Move curr to point to the next node.
			curr = curr.getNext(); 
		}
		return false;
	}
	/**
     * Returns the item at position pos in the List.
     * 
     * @param pos the position of the item to return
     * @return the item at position pos
     * @throws IllegalArgumentException if list is null
     * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
     * or equal to size()
     */
	@Override
	public E get(int pos) {
		//Check if the list is empty and the pos is invalid.
		if(head == null) throw new IllegalArgumentException();
		if(pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException();
		//Create a new curr reference to point to the first node.
		DblListnode<E> curr = head;
		//traverse through the list to that particular position.
		for(int i = 0; i < pos; i++)
			curr = curr.getNext();
		return curr.getData();
	}
	 /**
     * Returns true iff the List is empty.
     * 
     * @return true if the List is empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}

	 /**
     * Removes and returns the item at position pos in the List, moving the 
     * items originally in positions pos+1 through size() - 1 one place to the 
     * left to fill in the gap.
     * 
     * @param pos the position at which to remove the item
     * @return the item at position pos
     * @throws IllegalArgumentException if list is null
     * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
     * or equal to size()
     */
	@Override
	public E remove(int pos) {
		//Check if the list is empty and if the position is invalid.
		if(head == null) throw new IllegalArgumentException();
		if(pos < 0 || pos >= numItems ) throw new IndexOutOfBoundsException();
		//new curr reference to point to the fist node
		DblListnode<E> curr = head; 
		if(numItems == 1){ // If there is only one item in the list.
			head = null;
			tail = null;
		}
		else if(pos == numItems -1) { //trying to remove the last node
			tail.getPrev().setNext(null);
			// the curr will point to the tail temporarily. Curr will be
			// returned as the node that was removed.
			curr = tail; 
			tail = tail.getPrev(); //new tail reference
		}
		else if(pos == 0){ //trying to remove the first node
			head.getNext().setPrev(null);
			head = head.getNext(); //change the head reference.
		}
		//General case, trying to remove an item in the middle. First traverse
		//through the list to point to that item.
		else{
			for(int i = 0; i < pos; i++)
				curr = curr.getNext();
			curr.getPrev().setNext(curr.getNext());
			curr.getNext().setPrev(curr.getPrev());
		}
		numItems--;
		return curr.getData();
	}
	 /**
     * Returns the number of items in the List.
     * 
     * @return the number of items in the List
     */
	@Override
	public int size() {
		return numItems;
	}
}
