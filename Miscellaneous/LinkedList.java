mport java.util.Iterator;

public class LinkedList <E> implements ListADT<E>{

	//Private Instance

	private Listnode<E> head;
	private int numItems;


	//Constructor

	public LinkedList(){
		this.head = null;
		this.numItems = 0;
	}
	public LinkedList(Listnode<E> item){
		this.head = item;
	}

	//Override methods


	/*This add method add the new node into the end of the list. */
	@Override
	public void add(E item)  {
		if(item == null){
			throw new IllegalArgumentException();
		}
		//Create a new list of nodes
		Listnode<E> newNode = new Listnode<E>(item);
		//special case if the list is empty, then the newNode would become the first item.
		if(head == null)
			this.head = newNode;
		else{
			//travers through the very last node
			Listnode<E> curr = head;
			while(curr.getNext() != null)
				curr = curr.getNext();
			curr.setNext(newNode);
		}
		numItems++;

	}

	@Override
	public void add(int pos, E item) {
		if (pos <0 || pos > numItems +1 )
			throw new IndexOutOfBoundsException();
		if(item == null)
			throw new IllegalArgumentException();
		if(pos == numItems +1)
			this.add(item);
		else{
			Listnode<E> curr = head;
			for (int i = 0; i<pos; i++){
				curr = curr.getNext(); //this is how I got n-1
			}
			Listnode<E> addItem = new Listnode<E>(item);
			addItem.setNext(curr.getNext());
			curr.setNext(addItem);
			numItems++;
		}
	}

	@Override
	public boolean contains(E item) {
		if(item == null) throw new IllegalArgumentException();
		Listnode<E> curr = head;
		for(int i=0; i<numItems; i++){
			if(curr.getData().equals(item))
				return true;
			else if(curr.getNext() == null) return false;
			curr = curr.getNext();
		}
		return false;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public boolean isEmpty() {  return numItems == 0;
	}


	//We will base our programming on index starts at one. 
	@Override
	public E get(int pos) {
		if(pos<0 || pos> numItems) throw new IndexOutOfBoundsException();
		if(head == null) throw new IllegalArgumentException();
		Listnode<E> curr = head;
		for(int i =0; i<pos; i++)
			curr = curr.getNext();
		return curr.getData();

	}

	@Override
	public E remove(int pos) {
		if(pos<0 || pos > this.size()) throw new IndexOutOfBoundsException();
		Listnode<E> curr = head;
		for (int i = 0; i< pos; i++)
			curr = curr.getNext();
		E returnNode = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		numItems--;
		return returnNode;
	}

	@Override
	public void print() {
		if(head == null) throw new IllegalArgumentException();
		Listnode<E> curr = head;
		try{
			while(true){
				System.out.print(curr.getData() + "\t");
				curr = curr.getNext();
			}
		}catch(NullPointerException e){
		}
		System.out.println();

	}

	






}
