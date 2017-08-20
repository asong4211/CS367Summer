
public interface QueueADT<E> {
	void enqueue(E item); //add item to the Queue;
	E dequeue(); //remove at the front of the queue
	boolean isEmpty();
	void print();
}
