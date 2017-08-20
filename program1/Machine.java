 /*
 * This class creates and stores information about a specific machine.
 */
public class Machine {
    
    private String machineName;     //stores the machine name
    private int numCPUs;            //stores the number of CPU's
    private int memorySize;         //stores RAM size in GB's
    private int diskSize;           //stores disk size in GB's
    private double price;           //price of the machine
    
    /**
     * Constructs a machine with a name, specifications and price. 
     * 
     * @param machineName name of machine
     * @param numCPUs number of CPU's
     * @param memorySize number of GB's of RAM
     * @param diskSize number of GB's of disk
     * @param price price of product in $ 
     */
    public Machine(String machineName, int numCPUs, int memorySize, 
                    int diskSize, double price) {
    	this.machineName = machineName;
    	this.numCPUs = numCPUs;
    	this.memorySize = memorySize;
    	this.diskSize = diskSize;
    	this.price = price;
    }
    
    /** 
     * Returns the name of the machine
     * @return the machineName
     */
    public String getName() {
    	return this.machineName;
    }

    /**                
     * Returns the number of CPU's of the machine
     * @return the numCPUs
     */                
    public int getCPUs() {
    	return this.numCPUs;
    }   
    
    /**                
     * Returns the memory size of the machine
     * @return the memorySize
     */                
    public int getMemorySize() {
    	return this.memorySize;
    }     

   /**                       
     * Returns the disk size of the machine
     * @return the diskSize 
     */                       
    public int getDiskSize() {
    	return this.diskSize;
    }      
    
    /** 
     * Returns the price of the product
     * @return the price
     */
    public double getPrice() {
    	return this.price;
    }
    
    /** 
     * Returns the machine's information in the following format: 
     *
     * Note: The below line exceeds the 80 character limit just to make sure
     * that you have a clear understanding of the output format
     * <NAME> [Number of CPU's:<CPUs>, RAM size:<RAM>, Disk size: <DISK>] [Price:$<PRICE>]
     * @return the string
     */
    @Override
    public String toString() {
    	return ""+ machineName + " [Number of CPU's: " + numCPUs + ", RAM size: "+
    			memorySize +", Disk size: " + diskSize + "] [Price: $" + price + "]";
    }

}
