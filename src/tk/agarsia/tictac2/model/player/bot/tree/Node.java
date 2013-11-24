package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.Field;

public class Node {

	private TreeBuilder tree;
	
	protected int ID;
	protected Board board;
	protected int valueIset;
	protected int vertical = 1;
	
	protected Node parent = null;
	protected ArrayList<Node> children = new ArrayList<Node>();
	
	public Node(TreeBuilder tree, Node parent, Board board, int valueIset){
		ID = this.hashCode();
		
		if(parent != null){
			this.parent = parent;
			vertical = parent.getVertical() + 1;
			parent.addChild(this);	
			tree.addEdge(new Edge(parent, this));	
		}
		
		this.board = new Board(board);;
		this.valueIset = valueIset;
		tree.addNode(this);		
				
		if(vertical < 4) //depth-stop, VERY important :D ... formula required to plan meaningful computation limits!
			for(Field freeField : board.getFreeFields()){
				//System.out.println(freeField.getRow() + ", " + freeField.getColumn());
				//System.out.println(vertical);
				freeField.setValue(valueIset);
				new Node(tree, this, board, (valueIset == 1 ? 2 : 1)); //simple toggle won't do it when marksPerTurn have to be considered... 
																		//will need to come up with a good playerIndex passforward-mechanism thingy, maybe that info has to sit in Board
				freeField.setValue(0);			
			}		
	}
		
	public int getValueISet(){
		return valueIset;
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public String getContent(){
		return board.show(false);
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
