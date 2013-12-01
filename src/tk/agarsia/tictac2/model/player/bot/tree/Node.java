package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;
import tk.agarsia.tictac2.model.board.BoardParser;


public class Node {

	
	private int ID;
	private int boardDim;
	private int[] boardArr;
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children = new ArrayList<Node>();
	private int playerIndexIset;
	private int playerIthinkFor;
	private int winner = 0;
	private boolean flag = false;
	
	public Node(Node parent, int[] boardArr, int boardDim, int winLength, int playerIndexIset, int playerIthinkFor){
		ID = this.hashCode();
		parents.add(parent);
		this.boardDim = boardDim;
		this.boardArr = boardArr;
		this.playerIndexIset = playerIndexIset;
		this.playerIthinkFor = playerIthinkFor;
		winner = BoardParser.testBoardForWinner(boardArr, boardDim, winLength);
	}
	
	public void flag(){
		flag = true;
	}
	
	public void addParent(Node parent){
		parents.add(parent);
	}
	
	public boolean compare(Node other){		
		if(this.ID != other.getID()){
			boolean sameBoardArr = true;
			for(int i = 0; i < boardArr.length; i++)
				if(other.getBoardArr()[i] != boardArr[i])
					sameBoardArr = false;
			if(sameBoardArr){
				flag = true;
				return true;
			}
			else
				return false;
		}	
		return false;
	}
	
	public boolean getFlag(){
		return flag;
	}
	
	public int[] getBoardArr(){
		return boardArr;
	}
	
	public int getID(){
		return ID;
	}
	
	public ArrayList<Node> getParents(){
		return parents;
	}
	
	public int getBoardDim(){
		return boardDim;
	}
	
	public boolean wonOrLost(){
		return winner != 0;
	}
	
	public boolean iWon(){
		return winner == playerIthinkFor;
	}
	
	public String getDetails(){
		return "winner: " + winner + " playerIndexIset: " + playerIndexIset;
	}
	
		
	public String showBoard(){
		String buffer = "";		
		for(int row = 0; row < boardDim; row++){
			for(int i = row * boardDim; i < row * boardDim + boardDim; i ++)				
				buffer +=  boardArr[i] + " ";
			buffer += "\n";
		}
		return buffer;
	}
	
}






//OLD NODE
/*package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.BoardParser;

public class oldNode {

	private TreeBuilder tree;
	
	protected int ID;
	protected int playerIset;
	protected int[] boardArr;
	protected int len;
		
	protected int vertical = 1;	
	protected int winner = 0;	
	
	protected Node parent = null;
	protected ArrayList<oldNode> children = new ArrayList<oldNode>();
	
	
	public oldNode(TreeBuilder tree, Node parent, int[] boardArrParent, int[] turnIndize, int myPosInTurnIndize, int len, int wLen){
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
	
	public void setParent(oldNode parent){
		this.parent = parent;
	}
	
	public oldNode getParent(){
		return parent;
	}

	public void addChild(oldNode child){
		children.add(child);
	}
	
	public ArrayList<oldNode> getChildren(){
		return children;
	}
}
*/