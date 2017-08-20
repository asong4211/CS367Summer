import java.util.*;

import javax.xml.soap.Node;
/**
 * Stores all vertexes as a list of GraphNodes.  Provides necessary graph operations as
 * need by the SpyGame class.
 */
public class SpyGraph implements Iterable<GraphNode> {

	private List<GraphNode> vlist;

	/**
	 * Initializes an empty list of GraphNode objects
	 */
	public SpyGraph(){
		vlist = new LinkedList<>();
	}

	/**
	 * Add a vertex with this label to the list of vertexes.
	 * No duplicate vertex names are allowed.
	 * @param name The name of the new GraphNode to create and add to the list.
	 */
	public void addGraphNode(String name){
		for(int i = 0 ; i < vlist.size(); i++){
			if(vlist.get(i).equals(name)) //trying to add a node already exists in the list
				return;
		}
		vlist.add(new GraphNode(name)); //add a new node with this label to the list 
	}
	/**
	 * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
	 * Also sets the cost for each neighbor pair.
	 *   
	 * @param v1name The name of the first vertex of this edge
	 * @param v2name The name of second vertex of this edge
	 * @param cost The cost of traveling to this edge
	 * @throws IllegalArgumentException if the names are the same
	 */
	public void addEdge(String v1name, String v2name, int cost) 
			throws IllegalArgumentException {
		if(v1name.equals(v2name)) throw new IllegalArgumentException();
		GraphNode startNode = null;
		GraphNode endNode = null;
		for (int i = 0; i < vlist.size(); i++) {
			if(vlist.get(i).getNodeName().equals(v1name))
				startNode = vlist.get(i);
			if(vlist.get(i).getNodeName().equals(v2name))
				endNode = vlist.get(i);
		}
		startNode.addNeighbor(endNode, cost);
		endNode.addNeighbor(startNode, cost);
	}
	/**
	 * Return an iterator through all nodes in the SpyGraph
	 * @return iterator through all nodes in alphabetical order.
	 */
	public Iterator<GraphNode> iterator() {
		return vlist.iterator();
	}

	/**
	 * Return Breadth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in BFS traversal
	 * @param end Last node (match node) in BFS traversal
	 * @return The BFS traversal from start to end node.
	 */
	public List<Neighbor> BFS( String start, String end ) {
		//return an empty list if the end node does not exist or if start and end are the
		//same node
		if (this.getNodeFromName(end) == null
				|| this.getNodeFromName(end).equals(this.getNodeFromName(start)))
			return new LinkedList<Neighbor>();
		//set all node visited fields to false
		setNodeUnvisited();
		//retrieve start node
		GraphNode first = getNodeFromName(start);
		//set visited to true
		first.setVisited(true);
		//iterator for neighbors of first
		Iterator<Neighbor> itr = first.getNeighbors().iterator();
		//queue to store neighbors
		Queue<Neighbor> queue = new LinkedList<Neighbor>();
		//create an arbitrary root for the tree
		TreeNode root = new TreeNode(null);
		//create a tree which stores BFS traversal
		Tree tree = new Tree(root);
		//used to store neighbor and treenode currently being parsed
		Neighbor currNeighbor = null;
		TreeNode currNode = null;
		//iterate through neighbors of first
		while (itr.hasNext()) {
			currNeighbor = itr.next();
			//if the node has not yet been visited
			if (!currNeighbor.getNeighborNode().getVisited()) {
				//set visited to true
				currNeighbor.getNeighborNode().setVisited(true);
				//add that neighbor as a child of root
				root.addChild(new TreeNode(currNeighbor));
				//add to queue
				queue.add(currNeighbor);
			}
		}
		//add Neighbors to the end of the queue that are neighbors (one away) of the 
		//node that is pointed to by the Neighbor at the beginning of the queue
		while (!queue.isEmpty()) {
			currNeighbor = queue.remove();
			itr = currNeighbor.getNeighborNode().getNeighbors().iterator();
			//node to make parent of newly found nodes in the next iteration
			currNode = tree.findNode(currNeighbor.getNeighborNode().getNodeName());
			while(itr.hasNext()) {
				currNeighbor = itr.next();
				if (!currNeighbor.getNeighborNode().getVisited()) {
					currNeighbor.getNeighborNode().setVisited(true);
					//add neighbor to the tree
					currNode.addChild(new TreeNode(currNeighbor));
					queue.add(currNeighbor);
				}
			}
		}
		//return a list of neighbors that represents the path from start to end
		return tree.getPathTo(end);
	}





	/**
	 * @param name Name corresponding to node to be returned
	 * @return GraphNode associated with name, null if no such node exists
	 */
	public GraphNode getNodeFromName(String name){
		for ( GraphNode n : vlist ) {
			if (n.getNodeName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}
	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 */
	public List<Neighbor> DFS(String start, String end) {
		GraphNode startNode = getNodeFromName(start);
		GraphNode endNode = getNodeFromName(end);
		//create an empty stack to store the list of neighbor edges
		Stack<Neighbor> myList = new Stack<>();
		setNodeUnvisited(); //set all the nodes unvisited
		//call the recursive helper method to return the list
		DFS(startNode, endNode, myList); 
		return myList ;
	}
	/**
	 * Private recursive helper method for DFS to traverse through the graph
	 *
	 * @param startNode the start node to traverse
	 * @param endNode the end node to traverse
	 * @param list store the list of neighbors
	 * @return boolean, true if the particular node is found in that path, false otherwise
	 */
	private boolean DFS(GraphNode startNode, GraphNode endNode,
			Stack<Neighbor> list){
		// if the particular node has already been visited
		if(startNode.getVisited()) return false; 
		if(startNode.equals(endNode)) return true; //found the particular path
		Iterator<Neighbor> neighborIterator = startNode.getNeighbors().iterator();
		startNode.setVisited(true); //set the node to visited
		while(neighborIterator.hasNext()){
			Neighbor neighbor= neighborIterator.next();
			//add it the list of neighbor
			list.add(neighbor);
			//recursively call the same method but incremented to the next neighbor
			//in the iterator.
			if(DFS(neighbor.getNeighborNode(), endNode, list)) return true;
			else list.pop(); //pop it from the stack.
		}
		return false;
	}
	/**
	 * Dijkstra is used to find the shortest path between two nodes
	 *
	 * @param start First node in path
	 * @param end Last node (match node) in path
	 * @return The shortest cost path from start to end node.
	 */
	public List<Neighbor> Dijkstra(String start, String end){
		GraphNode startNode =getNodeFromName(start);
		GraphNode endNode = getNodeFromName(end);
		setNodeUnvisited(); //set all the nodes unvisited
		//create the new empty arraylist to store the list of neighbors 
		List<Neighbor> neighborList = new ArrayList<>();
		//use to keep track of the tentative distance between two nodes.
		HashMap<GraphNode,Integer> myDistance = new HashMap<>(); 
		//use to keep track of the predecessor of the particular node
		HashMap<GraphNode, Neighbor> predecessor = new HashMap<>();
		//this is the same as the above one, but is used later on to iterate through the 
		//hash map
		HashMap<GraphNode,GraphNode> predecessorWithNode = new HashMap<>();
		//the comparator is used to specify the particular field to compare, in this
		//case, it's the edge's cost that is being compared. 
		Comparator<Neighbor> myComp = new myComparator();
		//Create an empty priority queue to store the list of neighbors that we want
		//to prioritized, passed in the comparator so that it compares the cost of the 
		//list of edges
		PriorityQueue<Neighbor> graphPQueue =
				new PriorityQueue<>(vlist.size(),myComp);
		if(startNode != null && endNode != null){ // if the start and end node is found
			for(GraphNode n: vlist) //assign all the distance to infinity.
				myDistance.put(n, Integer.MAX_VALUE);
			for(GraphNode n: vlist){//put all the nodes'predecessor to be null.
				predecessor.put(n, null);
				predecessorWithNode.put(n,null);
			}
			myDistance.put(startNode, 0);//the starter node 's distance starts with 0
			//add the starter node to the priority queue.
			graphPQueue.offer(new Neighbor(0,startNode));
			while(!graphPQueue.isEmpty()){
				//polled the first edge(neighbor) from the priority queue 
				Neighbor polledNodeNeighbor = graphPQueue.poll();
				//retrieve the graphnode on the other end of the edges
				GraphNode node = polledNodeNeighbor.getNeighborNode();
				Iterator<Neighbor> itr = node.getNeighbors().iterator();
				while(itr.hasNext()){
					Neighbor neighbor = itr.next();
					GraphNode currNode = neighbor.getNeighborNode();
					//if the particular node has already been visited, return back to the loop
					if(currNode.getVisited()) continue;
					//calculate the weight between those two nodes.
					int cost = myDistance.get(node) + neighbor.getCost();
					//if the cost is less, then update the distance table and the predecessor
					//table.
					if(cost < myDistance.get(currNode)){ 
						myDistance.put(currNode, cost);
						predecessor.put(currNode, neighbor); //update the predecessor
						predecessorWithNode.put(currNode, node);
					}
					graphPQueue.offer(neighbor); //dequeue that node from the priority queue
				}
				node.setVisited(true);//set its visited field to true to prevent infinite loop
			}
			if(predecessorWithNode.containsKey(endNode)){
				//iterate through the predecessor table until the starter node.
				while(predecessorWithNode.get(endNode) != null){
					neighborList.add(predecessor.get(endNode)); //add the neighbors 
					//change its reference to the node that links it.
					endNode = predecessorWithNode.get(endNode); 
				}
			}
			//reverse the neighboList order 
			Collections.reverse(neighborList);
		}
		return neighborList;
	}

	//shortest distance from the start node


	/**
	 * DO NOT EDIT THIS METHOD 
	 * @return a random node from this graph
	 */
	public GraphNode getRandomNode() {
		if (vlist.size() <= 0 ) {
			System.out.println("Must have nodes in the graph before randomly choosing one.");
			return null;
		}
		int randomNodeIndex = Game.RNG.nextInt(vlist.size());
		return vlist.get(randomNodeIndex);
	}
	/**
	 * A private method to set all the nodes in the spyGraph unvisited. 
	 */
	private void setNodeUnvisited(){
		for(GraphNode n: vlist)
			n.setVisited(false);
	}
}

/**
 * The tree that represents the BFS traversal through the graph
 *
 */
class Tree {

	//the root is arbitrary, since the tree stores neighbors, and the start node for the
	//BFS function can have multiple neighbors
	private TreeNode root;

	public Tree(TreeNode root) {
		this.root = root;
	}

	/**
	 * returns a list of neighbors from the root to a given node
	 *
	 * @param end the name of the node we are looking for
	 * @return a list of neighbors from the root to a given node
	 */
	public List<Neighbor> getPathTo(String end) {
		//find the node in the tree
		TreeNode node = findNode(end);
		//add the node and all the node's ancestors excluding the root into a stack so
		//that the order can be later reversed
		Deque<Neighbor> stack = new ArrayDeque<Neighbor>();
		stack.push(node.getData());
		while (!node.getParent().equals(root)) {
			node = node.getParent();
			stack.push(node.getData());
		}
		List<Neighbor> list = new LinkedList<Neighbor>();
		while (!stack.isEmpty()) 
			list.add(stack.pop());
		return list;
	}

	/**
	 * returns the treenode in the tree with a given name
	 *
	 * @param name the name of the treenode we are looking for
	 * @return the treenode that we are looking for
	 */
	public TreeNode findNode(String name) {
		//iterator for children of a given node
		Iterator<TreeNode> itr = root.getChildren().iterator();
		//queue to store nodes
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		//iterate through the tree
		while (itr.hasNext())
			queue.add(itr.next());
		while (!queue.isEmpty()) {
			TreeNode curr = queue.remove();
			//if curr is the node we are looking for, return it
			if (curr.getData().getNeighborNode().getNodeName().equals(name)) {
				return curr;
			}
			//set itr to be the children of curr
			itr = curr.getChildren().iterator();
			while(itr.hasNext())
				//add those children to the queue
				queue.add(itr.next());
		}
		//if node not found
		return null;
	}
}

	/**
	 *Represents a single node in a tree of neighbors 
	 *
	 */
class TreeNode {
	private Neighbor data;
	private TreeNode parent;
	private List<TreeNode> children;

	public TreeNode(Neighbor data) {
		this.data = data;
		children = new LinkedList<TreeNode>();
	}

	public TreeNode getParent() {
		return parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public Neighbor getData() {
		return data;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void addChild(TreeNode child) {
		children.add(child);
		//set the child's parent field to this node
		child.setParent(this);
	}
}
	/**
	 * This is a private class used to implement the Comparator interface. 
	 *
	 */

class myComparator implements Comparator<Neighbor>{

	/*
	 * Override the compare method in the Comparator interface to compare the 
	 * neighbor's cost. This is used in the priority queue to prioritize the weights
	 * of all the edges
	 */
	@Override
	public int compare(Neighbor o1, Neighbor o2) {
		if(o1.getCost() > o2.getCost()) return 1;
		if(o1.getCost() == o2.getCost()) return 0;
		else return -1;
	}
}
