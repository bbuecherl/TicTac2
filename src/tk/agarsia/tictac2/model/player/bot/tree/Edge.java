package tk.agarsia.tictac2.model.player.bot.tree;

public class Edge {

	private String ID;
	private Node source;
	private Node target;
	
	public Edge(Node source, Node target){
		ID = "" + this.hashCode();
		this.source = source;
		this.target = target;
	}
	
	public String getID(){
		return ID;
	}
	
	public Node getSource(){
		return source;
	}
	
	public Node getTarget(){
		return target;
	}
	
	
}
