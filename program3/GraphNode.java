import java.util.*;

/**
 * GraphNode class maintains a vertex name and a list of adjacent vertices which
 * stores the neighbors in a sorted list.
 */

public class GraphNode implements Comparable<GraphNode> {
    private String nodeName;
    private boolean spycam;
    private List<Neighbor> neighbors;
    private boolean visited;
    
    /** 
     * Constructs a GraphNode with the vertex name and empty neighbors list.
     * 
     * @param name of the vertex, which needs to be unique
     */
    public GraphNode(String name) {
    	nodeName = name;
    	neighbors = new LinkedList<Neighbor>();
    	visited = false;
    }

    /** 
     * Returns the name of the vertex. 
     *
     * @return the name of this GraphNode
     */
    public String getNodeName() {
    	return nodeName;
    }

    /** 
     * Returns the neigbors of the vertex. 
     *
     * @return the neighbors of this GraphNode
     */
    public List<Neighbor> getNeighbors() {
    	return neighbors;
    }

    /**
     * Sets the visited flag of this vertex.
     *
     * @param flagVal boolean value used to set the flag
     */
    public void setVisited(boolean flagVal) {
    	visited = flagVal;
    }
    
    /**
     * Gets the visited flag of this vertex.
     *
     * @return visited boolean value 
     */
    public boolean getVisited() {
    	return visited;
    }

    /** 
     * Return the results of comparing this node's name to the other node's 
     * name. 
     * 
     * @param otherNode GraphNode instance whose vertex name is required for 
     * comparison
     * @return negative value or 0 or positive value
     */
    public int compareTo(GraphNode otherNode) {
    	return nodeName.compareTo(otherNode.getNodeName());
    }


    /** 
     * Adds a new neighbor and maintains sorted order of neighbors by neighbor 
     * name.
     *
     * @param neighbor an adjacent node 
     * @param cost to move to that node (from this node)
     */
    public void addNeighbor(GraphNode neighbor, int cost) {
    	neighbors.add(new Neighbor(cost, neighbor));
    	neighbors.sort(null);
    }

    /** 
     * Prints a list of neighbors of this GraphNode and the cost of the edge to 
     * them. 
     * Example:
     * "1 b"
     * "4 c"
     * Note: Quotes are given here for clarification, do not print the quotes.
     */
    public void displayCostToEachNeighbor() {
    	Iterator<Neighbor> itr = neighbors.iterator();
    	while (itr.hasNext()) {
    		Neighbor curr = itr.next();
    		System.out.println(curr.getCost() + " " +
    		curr.getNeighborNode().getNodeName());
    	}
    }

    /** 
     * Returns cost to reach the neighbor.
     *
     * @param neighborName name of neighbor
     * @return cost to neighborName
     * @throws NotNeighborException if neighborName is not a neighbor
     */
    public int getCostTo(String neighborName) throws NotNeighborException {
    	Iterator<Neighbor> itr = neighbors.iterator();
    	while (itr.hasNext()) {
    		Neighbor curr = itr.next();
    		//return cost to neighbor node with name neighborName
    		if (curr.getNeighborNode().getNodeName().equals(neighborName))
    			return curr.getCost();
    	}
    	//if not found
    	throw new NotNeighborException();
    }

    /** 
     * Returns the GraphNode associated with name that is a neighbor of the 
     * current node.
     *
     * @param neighborName name of neighbor
     * @return the GraphNode associated with name that is neighbor of this node
     * @throws NotNeighborException if neighborName is not a neighbor
     */
    public GraphNode getNeighbor(String neighborName) 
    		throws NotNeighborException {
    	Iterator<Neighbor> itr = neighbors.iterator();
    	while (itr.hasNext()) {
    		Neighbor curr = itr.next();
    		//return the neighbor that points to the node with name neighborName
    		if (curr.getNeighborNode().getNodeName().equals(neighborName))
    			return curr.getNeighborNode();
    	}
    	//if not found
    	throw new NotNeighborException();
    }

    /** 
     * Returns an iterator that can be used to find neighbor names
     * of this GraphNode.
     *
     * @return iterator of String node labels
     */
    public Iterator<String> getNeighborNames() {
    	//create list of Strings
    	List<String> neighborNames = new ArrayList<String>();
    	//iterator for neighbors
    	Iterator<Neighbor> neighborItr = neighbors.iterator();
    	while (neighborItr.hasNext()) {
    		Neighbor curr = neighborItr.next();
    		//add neighbor names to list of Strings
    		neighborNames.add(curr.getNeighborNode().getNodeName());
    	}
    	//create iterator for list of Strings
    	Iterator<String> neighborNamesItr = neighborNames.iterator();
    	return neighborNamesItr;
    }

    /** 
     * Sets/unsets spycam at this node.
     *
     * @param cam indicates whether the node now has a spycam
     */
    public void setSpycam(boolean cam) {
    	spycam = cam;
    }

    /** 
     * Returns information about spycam presense in this node.
     *
     * @return true if the GraphNode has a spycam
     */
    public boolean getSpycam() {
    	return spycam;
    }

    /** 
     * Returns true if this node name is a neighbor of current node.
     *
     * @param neighborName name of neighbor
     * @return true if the node is an adjacent neighbor
     */
    public boolean isNeighbor(String neighborName) {
    	Iterator<String> itr = this.getNeighborNames();
    	while (itr.hasNext()) {
    		if (itr.next().equals(neighborName))
    			return true;	
    	}
    	return false;
    }

    /** 
     * Returns the name of this node.
     *
     * @return name of node
     */
    public String toString() {
    	return nodeName;
    }

}
