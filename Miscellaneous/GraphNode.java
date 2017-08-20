import java.util.*;

//GraphNode for a directed graph
public class GraphNode {
	private String vertext;
	private boolean visited;
	private List<GraphNode> neighbors;
	
	public GraphNode(String vertex){
		this.vertext = vertex;
		this.neighbors = new ArrayList<GraphNode>();
		this.visited = false;
	}
	
	public void addNeighbor(GraphNode node){
		neighbors.add(node);
	}
	
	public String getVertex(){
		return this.vertext;
	}
	
	public boolean isVisited(){
		return this.visited;
	}
	public List<GraphNode> getNeighbors(){
		return this.neighbors;
	}
	
	public void setVisited(boolean flagVal ){
		this.visited = flagVal;
	}
	
	public void printMe(){
		System.out.print(this.vertext + " : ");
		Iterator<GraphNode> neighborsItr = neighbors.iterator();
		while (neighborsItr.hasNext())
			System.out.print(neighborsItr.next().getVertex() + " ");
		System.out.println();
		
	}

}

