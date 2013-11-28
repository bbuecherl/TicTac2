package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.BoardParser;

public class Node {

	private TreeBuilder tree;
	
	protected int ID;
	protected int playerIset;
	protected int[] boardArr;
	protected int len;
		
	protected int vertical = 1;	
	protected int winner = 0;	
	
	protected Node parent = null;
	protected ArrayList<Node> children = new ArrayList<Node>();
	
	
	public Node(TreeBuilder tree, Node parent, int[] boardArrParent, int[] turnIndize, int myPosInTurnIndize, int len, int wLen){
		this.tree = tree;
		ID = this.hashCode();
		this.len = len;
		this.playerIset = turnIndize[myPosInTurnIndize];
			
		tree.addNode(this);
		
		if(parent != null){ // if not rootnode 
			this.parent = parent;
			vertical = parent.getVertical() + 1;			
			parent.addChild(this);
			tree.addEdge(new Edge(parent, this));	
		}
	
		//deep copying boardArr
		boardArr = new int[boardArrParent.length];
		for(int j = 0; j < boardArrParent.length; j++)
			boardArr[j] = boardArrParent[j];	
		
		winner = BoardParser.testBoardForWinner(boardArr, len, wLen);
		
		if(winner == 0 && vertical < tree.getMaxDepth()){
			int[] emptyIndize = BoardParser.getEmptyIndizeOpt(boardArr);
			//ArrayList<Integer> emptyIndize = BoardParser.getEmptyIndize(boardArr);
			
			//for(int i : emptyIndize){
			for(int i = 0; i < emptyIndize.length; i++){							
				int j = emptyIndize[i];
				boardArr[j] = playerIset;
				new Node(tree, this, boardArr, turnIndize, myPosInTurnIndize + 1, len, wLen); 	
				boardArr[j] = 0;				
			}	
		}
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public boolean wonOrLost(){
		return winner != 0;
	}
	
	public boolean iWon(){
		return winner == tree.getCurrentPlayerIndex();
	}
	
	public String showBoard(){
		String buffer = "";		
		for(int row = 0; row < len; row++){
			for(int i = row * len; i < row * len + len; i ++)				
				buffer +=  boardArr[i] + " ";
			buffer += "\n";
		}
		return buffer;
	}
	
	public int getID(){
		return ID;
	}
	
	public int getBoardDim(){
		return len;
	}
	
	public int[] getBoardArr(){
		return boardArr;
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
