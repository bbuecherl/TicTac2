package tk.agarsia.tictac2.model.player.bot;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.Field;

public class Node {

	private TreeBuilder tree;
	
	private int ID;
	private Board board;
	private int valueIset;
	
	private Node parent = null;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(TreeBuilder tree){
		ID = this.hashCode();
		this.tree = tree;		
		this.board = tree.getInitialBoard();	
		tree.addNode(this);
		
		valueIset = tree.getCurrentPlayerIndex();
		
		for(Field freeField : board.getFreeFields()){
			freeField.setValue(valueIset);
			new Node(tree, this, new Board(board));
		    freeField.setValue(0);					
		}
		
	}
	
	public Node(TreeBuilder tree, Node parent, Board board){
		ID = this.hashCode();
		this.parent = parent;
		parent.addChild(this);	
		tree.addEdge(new Edge(parent, this));				
		this.board = board;	
		tree.addNode(this);
	}
	
	public String getContent(){
		return board.show();
	}
	
	public int getID(){
		return ID;
	}
	
	
	public Board getBoard(){
		return board;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public Node getParent(){
		return parent;
	}

	public void addChild(Node child){
		children.add(child);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	
}
