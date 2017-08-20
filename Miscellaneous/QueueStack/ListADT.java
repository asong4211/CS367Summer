
public interface ListADT<E> {
	boolean add(E item);
	E get(int pos);
	boolean isEmpty();
	int size();
	E remove(int pos);
	void NormalPrint();
	void print();
	boolean contains(E item);
}
