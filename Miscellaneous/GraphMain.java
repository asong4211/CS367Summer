import java.util.*;
public class GraphMain {

	public static void main(String[] args){
		
		System.out.println("---Graph Traversal---");
		Graph graph = new Graph();

	
		GraphNode a = new GraphNode("A");
		GraphNode b = new GraphNode("B");
		GraphNode c = new GraphNode("C");
		GraphNode d = new GraphNode("D");
		GraphNode e = new GraphNode("E");
		GraphNode f = new GraphNode("F");
		GraphNode g = new GraphNode("G");
		GraphNode h = new GraphNode("H");
		GraphNode i = new GraphNode("I");
		
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		graph.addVertex(i);
		
		graph.addNeighbors(a, b);
		graph.addNeighbors(a, c);
		graph.addNeighbors(a, d);
		graph.addNeighbors(b, a);
		graph.addNeighbors(b, c);
		graph.addNeighbors(b, f);
		graph.addNeighbors(e, b);
		graph.addNeighbors(e, f);
		graph.addNeighbors(e, g);
		graph.addNeighbors(e, i);
		graph.addNeighbors(f, i);
		graph.addNeighbors(f, i);
		graph.addNeighbors(g, f);
		graph.addNeighbors(g, h);
		graph.addNeighbors(h, f);
		
		graph.printGraph();
		
		
		//DFS calling
		System.out.println("---DFS---");
		DFS(a);
		System.out.println();
		
		
		//reset all the visited graph before doing another traversal
		graph.setVisitedFlags();
		
		//BFS calling
		System.out.println("---BFS traversal---");
		BFS(a);
		System.out.println();

	}
	
	public static void DFS(GraphNode node){
		node.setVisited(true);
		System.out.print( node.getVertex() + " ");
		
		Iterator<GraphNode> itr = node.getNeighbors().iterator();
		while(itr.hasNext()){
			GraphNode successor = itr.next();
			if(!successor.isVisited()){
				DFS(successor);
			}
		}
		
	}
	
	
	public static void BFS(GraphNode node){
		node.setVisited(true);
		System.out.print(node.getVertex() + " ");
		Queue<GraphNode> queue = new LinkedList<>();
		queue.add(node);
		while(!queue.isEmpty()){
			GraphNode removed = queue.remove();
			Iterator<GraphNode> neighborNode = removed.getNeighbors().iterator();
			while(neighborNode.hasNext()){
				GraphNode successor = neighborNode.next();
				if(!successor.isVisited()){
					successor.setVisited(true);
					System.out.print(successor.getVertex() + " ");
					queue.add(successor);
				}
			}
		}
	}
	
	
	
}

