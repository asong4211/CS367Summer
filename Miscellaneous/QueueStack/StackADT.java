//Stack ADT is an abstract data type that will be Last in, First Out.


public interface StackADT <E>{
	void push(E item); //add the item to the top
	E pop(); //remove the item at the top
	E peek(); //look at the first item at the top
	boolean isEmpty();
	void print();
	
}
