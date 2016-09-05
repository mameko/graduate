package assign4;

import java.util.ArrayList;

public class HeartbeatTopo {

	/**
	 * This program uses heartbeat algorithm to find the network topology.
	 * 
	 * 
	 * @param args
	 */
	static int NETWORKSIZE = 5;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node[] network=new Node[NETWORKSIZE];
		
		//create nodes in the network
		for (int i = 0; i < NETWORKSIZE; i++) {
			network[i] = new Node(i, NETWORKSIZE);
		}
		
		//set up the initial connection
		network[0].setNeighbour(network[2]);
		network[1].setNeighbour(network[2]);
		network[2].setNeighbour(network[0]);
		network[2].setNeighbour(network[1]);
		network[2].setNeighbour(network[3]);
		network[3].setNeighbour(network[2]);
		network[3].setNeighbour(network[4]);
		network[4].setNeighbour(network[3]);	
		
		//start the threads so that they can exchange the information the have with their neighbour
		for (int i = 0; i < network.length; i++) {
			new Thread(network[i]).start();
		}
		
	}	
}

/**
 * This class is the network node class.
 * @author Shuwen Pan
 *
 */
class Node implements Runnable{
	ArrayList<Node> nodeCanReached = new ArrayList<Node>();
	ArrayList<Node> neighbours = new ArrayList<Node>();
	ArrayList<Node> receiveInfo = new ArrayList<Node>();
	int id;
	int totalNodes;
	
	//initialise the node by passing the node's id and the total number of nodes inside the network to it.
	public Node(int nodeId, int total){
		id = nodeId;
		nodeCanReached = neighbours;
		this.totalNodes = total;
	}
	
	//Set the node's neighbour
	public void setNeighbour(Node neighbour){
		neighbours.add(neighbour);
	}
	
	//send the information to it's neighbour nodes
	public void send(){
		for (int i = 0; i < neighbours.size(); i++) {
			neighbours.get(i).receive(nodeCanReached);
		}
	}
	
	//receive the information from it's neighbour nodes
	public void receive(ArrayList<Node> infoFromNeighbour){
		receiveInfo = infoFromNeighbour;
	}
	
	//update it's own information
	public void update(){
		for (int i = 0; i < receiveInfo.size(); i++) {
			 Node oNode = receiveInfo.get(i);
			if (!nodeCanReached.contains(oNode)) {
				nodeCanReached.add(oNode);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//If the nodes it can connect is equal to the total nodes, stop exchanging.
		while(nodeCanReached.size() <totalNodes) {
			send();
			update();			
		}
		
		//print the information.
		System.out.println("node"+id+" :");
		for (int i = 0; i < nodeCanReached.size(); i++) {
			System.out.println(nodeCanReached.get(i).id);
		}
	}
}


