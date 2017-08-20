public interface ListADT<E> {
	void add(E item);
	void add(int pos, E item);
	E get(int pos);
	boolean isEmpty();
	int size();
	E remove(int pos);
	void print();
	boolean contains(E item);
}
