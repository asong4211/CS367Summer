import java.util.*;

import javax.xml.soap.Node;

/**
 * Represents a player in the Game.
 */
public class Player {
    String playerName;
    int budget;
    int numSpycams;
    GraphNode currNode;
    List<GraphNode> spycams;

    /*
     * Constructs an instance of Player to track location and other information
     * for the player.
     */
    public Player(String name, int budget, int spycams, GraphNode startnode) {
    	this.playerName = name;
    	this.budget = budget;
    	this.numSpycams = spycams;
    	this.currNode = startnode;
    	this.spycams = new LinkedList<>();
    
    }

    /*
     * Subtract the decrease amount from the budget.
     * 
     * @param dec amount to decrease the budget by
     */
    public void decreaseBudget(int dec) {
    	//if(dec < 0) throw new IllegalArgumentException();
    	this.budget = this.budget - dec;
    }

    /*
     * If there are no remaining spy cams to drop, display "Not enough spycams" 
     * and return false. Otherwise:
     *
     * If there is not a spy cam at the player's current location:
     *  display "Dropped a Spy cam at D" where D is the node name
     *  drop a spy cam (here) D
     *  decrement the remaining spy cam count if there was not already a spycam
     *
     * Note: Make sure to set the spycam on the GraphNode as well.
     */
    public boolean dropSpycam() {
    	if(numSpycams <= 0 ){
    		System.out.println("Not enough spycams");
    	}
    	else{
    		if(currNode.getSpycam() == false){
    			System.out.println("Dropped a Spy cam at " + currNode.getNodeName());
    			currNode.setSpycam(true);
    			numSpycams--;
    			spycams.add(currNode);   //add the node to the spycam list
    			return true;
    		}
    		else System.out.println("Already a Spy cam there");
    	}
    	return false;
    }

    /*
     * Return the amount remaining in this player's budget.
     *
     * @return budget remaining
     */
    public int getBudget() {
    	return budget;
    }

    /*
     * Returns the node where the player is currently located.
     * @return currNode player's current node 
     */
    public GraphNode getLocation() {
    	return currNode;
    }

    /*
     * Returns the name of the node where the player is currently located.
     *
     * @return node label for the current location of the player
     */
    public String getLocationName() {
    	return currNode.getNodeName();
    }

    /*
     * Return the name of the player.
     *
     * @return name of player
     */
    public String getName() {
    	return playerName;
    }

    /*
     * If pickupSpyCam is true, increment the number of spy cams remaining.
     * Note: Make sure to unset the spycam on the GraphNode as well.
     *
     * @param: pickupSpyCam true if a spy cam was picked up. 
     *         False means there was no spy cam
     */
    public void getSpycamBack(boolean pickupSpyCam) {
    	if(pickupSpyCam){
    		numSpycams++;  
    		currNode.setSpycam(false);
    	}
    }

    /*
     * Returns the number of spy cams available to drop.
     *
     * @return number of spycams remaining
     */
    public int getSpycams() {
    	return numSpycams ;
    }

    /*         
     * Change the location of the player to the specified node. Moves to nodes 
     * with a cost of one are not counted. (ie. it is "free" to "walk" to a 
     * node). 
     * If attempt to move to a node that is not a neighbor (nn) is made,
     * displays the message: "<nn> is not a neighbor of your current location"; 
     * and returns false.
     * If there is no sufficient budget, then display 
     * "Not enough money cost is <COST> budget is <BUDGET>" 
     * Note: Quotes are given here for clarification, do not print the quotes.
     * If the cost to neighbor is greater than 1, decrement budget by that 
     * amount.         
     *                 
     * Note: Write a try-catch block for NotNeighborException, but you should
     * do necessary checks such that this exception will never be thrown from
     * the GraphNode functions that you are invoking.
     * Hint: Carefully consider which function to call first to ensure that 
     * an exception will never be thrown.
     *                 
     * @param name of the neighboring to move to
     * @return true if the player successfully moves to this node
     */                

    public boolean move(String name) {
    	try{
    		//if the cost is more than the budget
    		if(currNode.getCostTo(name) > budget){
    			System.out.println("Not enough money cost is " + currNode.getCostTo(name)
    			+ " budget is " + budget);
    			return false;
    		}
    		if(currNode.getCostTo(name) > 1) //free to walk to a node that cost 1 pound
    			budget = budget - currNode.getCostTo(name); //decrement from the budget
    		currNode = currNode.getNeighbor(name);
    		return true;
    	}catch(NotNeighborException e){
    		System.out.println(name + " is not a neighbor of your current location");
    	}
    	return false;
    }
    
    /*
     * Check the node to see if there is a spy cam. If there is a spy cam at 
     * that node, remove the spy cam from that node. Also, remove the spy cam 
     * name from the Player's list of spy cam names. Otherwise, return false.
     * Note: Make sure to unset the spycam on the GraphNode as well.
     *
     * @param node the player asked to remove a spy cam from.
     * @return true if a spycam is retrieved
     */
    public boolean pickupSpycam(GraphNode node) {
    	if(node.getSpycam()){ //if there is a spycam at this location
    		node.setSpycam(false); //remove the spy came from that node
    		//remove the spy cam name from the Player's list of spy cam names
    		spycams.remove(node); 
    		numSpycams++;
    		return true;
    	}
    	return false;
    }

    /*
     * Display the names of the locations where Spy Cams were dropped (and are 
     * still there). If there are spy cams at nodes named a, f, and g, the 
     * display would show:
     * "Spy cam at a"
     * "Spy cam at f"
     * "Spy cam at g"
     * Note: Quotes are given here for clarification, do not print the quotes.
     */
    public void printSpyCamLocations() {
    	Iterator<GraphNode> listOfSpyCams = spycams.iterator();
    	while(listOfSpyCams.hasNext()){
    		System.out.println("Spy cam at " + listOfSpyCams.next());
    	}
    	
    	
    }
}
