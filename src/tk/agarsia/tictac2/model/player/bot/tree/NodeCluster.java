package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

public class NodeCluster {

	private ArrayList<Node> cluster = new ArrayList<Node>();
	
	public NodeCluster(Node node){
		cluster.add(node);
	}
	
	public void addNode(Node node){
		cluster.add(node);
	}
	
	public ArrayList<Node> getCluster(){
		return cluster;
	}
	
	public Node produceSingleNode(){
		Node firstInCluster = cluster.get(0);
		
		//System.out.println(cluster.size());
		
		if(cluster.size() < 2)
			return firstInCluster;
		else{
			
			ArrayList<Node> collectParents = new ArrayList<Node>();
			
			for(Node clusterMember : cluster)
				collectParents.add(clusterMember.getNaturalParent());
					
			Node merged = new Node(collectParents, firstInCluster.getPlayerIndexIset(), firstInCluster.getBoardArr());
			
			return merged;
		}
		
	}
}
