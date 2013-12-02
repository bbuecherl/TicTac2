package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;
import tk.agarsia.tictac2.model.board.BoardParser;


public class Node {

	
	private static int boardDim;
	private static int winLength;
	private static int playerIthinkFor; //currentPlayerIndex
	
	private int ID;

	private int[] boardArr;
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children = new ArrayList<Node>();
	private int playerIndexIset;
	private int winner = 0;
	private int vertical = 0;
	private boolean flag = false;
	
	public static void setStaticParams(int boardDim, int winLength, int plazerIthinkFor){
		Node.boardDim = boardDim;
		Node.winLength = winLength;
		Node.playerIthinkFor = playerIthinkFor;
	}
	
	public Node(Node parent, int[] boardArr, int playerIndexIset){
		ID = this.hashCode();
		if(parent != null)
			vertical = parent.getVertical() + 1;
		parents.add(parent);
		this.boardArr = boardArr;
		this.playerIndexIset = playerIndexIset;
		winner = BoardParser.testBoardForWinner(boardArr, boardDim, winLength);
	}
	
	public Node(ArrayList<Node> parents, int playerIndexIset, int[] boardArr){
		ID = this.hashCode();
		
		this.parents = parents;
		
		this.boardArr = boardArr;
		this.playerIndexIset = playerIndexIset;
		winner = BoardParser.testBoardForWinner(boardArr, boardDim, winLength);
	}
	
	public void flag(){
		flag = true;
	}
	
	public boolean getHasParent(){
		return parents.size() > 0;
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public void addParent(Node parent){ // parent-child handshake
		parents.add(parent);
		parent.addChild(this);
	}
	
	public void addChild(Node child){
		children.add(child);
	}
	
	public void addChildren(ArrayList<Node> children){
		children.addAll(children);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public void setNaturalParent(Node parent){
		parents.clear();
		parents.add(parent);
	}
	
//now happening in BoardParser
/*	public boolean compare(Node other){		
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
	}*/
	
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
	
	public Node getNaturalParent(){
		return parents.get(0);
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
	
	public int getPlayerIndexIset(){
		return playerIndexIset;
	}
	
	public String getDetails(){
		return "winner: " + winner + " playerIndexIset: " + playerIndexIset;
	}
	
	public String showNode(){
		String buffer = "node_" + ID + ": [ ";
		for(int i = 0; i < boardArr.length; i++)
			buffer +=  boardArr[i] + " ";
		return buffer + "]";
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