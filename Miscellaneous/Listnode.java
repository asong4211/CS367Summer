import java.util.*;

public class Listnode<E> {

	
	//Private Instance
	private E data;
	private Listnode<E> next;
	
	//Constructor
	public Listnode(E data, Listnode<E> next){
		this.data = data;
		this.next = next;
	}
	
	public Listnode(){
		this(null,null);
	}
	
	public Listnode(E data){
		this(data, null);
	}
	

	//Getter and setter
	public Listnode<E> getNext(){
		return this.next;
	}
	
	public void setNext(Listnode<E> n){
		this.next = n;
	}
	
	public E getData(){
		return this.data;
	}
	
	public void setData(E data1){
		this.data = data1;
	}
	


	
}
