/**
 * Generic doubly linked list node. It serves as the basic building block for 
 * storing data in doubly linked chains of nodes.
 * 
 * <b>Do not modify this file in any way!</b>
 */
public class DblListnode<E> {
  //*** fields ***
    private DblListnode<E> prev;    // link to the previous node in the list
    private E data;                 // data to be stored   
    private DblListnode<E> next;    // link to the next node in the list
 
  //*** constructors ***
    // 2 constructors
    /**
     * Constructs a new list node with no links to its next or previous node.
     * 
     * @param data the data to be stored in this node
     */
    public DblListnode(E d) {
        this(null, d, null);
    }

    /**
     * Constructs a new list node with links to its next and previous.
     * 
     * @param data the data to be stored in this node
     * @param next the node after this one
     * @param prev the node before this one
     */  
    public DblListnode(DblListnode<E> p, E d, DblListnode<E> n) {
        prev = p;
        data = d;
        next = n;
    }
    
  //*** methods ***
    // access to fields
    /**
     * Returns the current data.
     * 
     * @return the current data
     */
    public E getData() {
        return data;
    }
    
    /**
     * Returns the next node.
     * 
     * @return the next node
     */  
    public DblListnode<E> getNext() {
        return next;
    }

    /**
     * Returns the previous node.
     * 
     * @return the previous node
     */ 
    public DblListnode<E> getPrev() {
        return prev;
    }
 
    // modify fields
    /**
     * Sets the data to the given new value.
     * 
     * @param data the new data
     */
    public void setData(E d) {
        data = d;
    }

    /**
     * Sets the next node to the given new value.
     * 
     * @param next the new next node
     */ 
    public void setNext(DblListnode<E> n) {
        next = n;
    }

    /**
     * Sets the previous node to the given new value.
     * 
     * @param prev the new previous node
     */ 
    public void setPrev(DblListnode<E> p) {
        prev = p;
    }
}
    
