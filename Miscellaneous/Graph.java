iimport java.util.*;

public class Graph {

	private List<GraphNode> vertices;
	
	
	public Graph (){
		vertices = new ArrayList<GraphNode>();
	}
	public void addVertex(GraphNode vertex){
		this.vertices.add(vertex);
	}
	
	public void addNeighbors(GraphNode src, GraphNode dest){
		src.addNeighbor(dest);
		//In the case of undirected graph, you can call addNeighbor  here twice
	}

	public void setVisitedFlags(){
		Iterator<GraphNode> itr = vertices.iterator();
		while(itr.hasNext()){
			GraphNode currNode = itr.next();
			currNode.setVisited(false);
		}
	}
	
	public void printGraph(){
		Iterator<GraphNode> itr = vertices.iterator();
		while(itr.hasNext()){
			itr.next().printMe();
		}
	}
	
}

