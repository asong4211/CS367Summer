
/**
 * Neighbor class represents an edge between two nodes and the associated cost.
 */

public class Neighbor implements Comparable<Neighbor> {
    private int cost;
    private GraphNode neighbor;

    /** 
     * A neighbor is added to an existing GraphNode by creating an instance 
     * of Neighbor that stores the neighbor and the cost to reach that 
     * neighbor.
     * 
     * @param cost of the edge
     * @param neighbor GraphNode
     */
    public Neighbor(int cost, GraphNode neighbor) {
    	this.cost = cost;
    	this.neighbor = neighbor;
    }

    /** 
     * Returns the cost of the edge. 
     *
     * @return the cost of this edge.
     */
    public int getCost() {
    	return cost;
    }

    /** 
     * Returns the neighbor on the other end of the edge. 
     *
     * @return the neighbor
     */
    public GraphNode getNeighborNode() {
    	return neighbor;
    }

    /** 
     * Return the results of comparing this node's neighbor name to the other 
     * node's neighbor name. Allows List of Neighbors to be sorted. 
     * Hint: Read the java docs for String class carefully 
     *
     * @param otherNode Neighbor instance whose Graphnode needs to be compared.
     * @return negative value or 0 or positive value
     */
    public int compareTo(Neighbor otherNode) {
    	return neighbor.getNodeName().compareTo(
    			otherNode.getNeighborNode().getNodeName());
    }

    /**
     * Returns a String representation of this Neighbor.
     * The String that is returned shows an arrow (with the cost in the middle) 
     * and then the Neighbor node's name. 
     *
     * Example:  
     * " --1--> b"
     * indicates a cost of 1 to get to node b
     * Note: Quotes are given here for clarification, do not print the quotes.
     *
     * @return String representation
     */
    public String toString() {
    	return "--" + this.getCost() + "-->" + neighbor.getNeighborNames();
    }
}
