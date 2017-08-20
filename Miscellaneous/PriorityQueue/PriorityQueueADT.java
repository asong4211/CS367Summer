public interface PriorityQueueADT<E> {
	
	
	void insert(Comparable item);
	Comparable getMax(); 
	Comparable removeMax();
	boolean isEmpty();
	
	
}

