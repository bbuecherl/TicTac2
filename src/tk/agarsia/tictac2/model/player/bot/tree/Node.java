package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.BoardParser;
import tk.agarsia.tictac2.model.board.BoardParserV2;


public class Node {

	
	private static int boardDim;
	private static int winLength;
	private static int playerIndexIthinkFor;
	private static int maxDepth;
			
	private String ID;

	private int[] boardArr;
	private int indexWhereIplacedMyMark;
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children = new ArrayList<Node>();
	private int vertical = 0;
	private int weightFactor = 1;
	
	/**
	 * 0: no winner
	 * 1: i won
	 * -1: i lost
	 */
	private int nodeState = 0;
	
	private int winnersBelow = 0;
	private int winnersBelowWeighted = 0;
	private int loosersBelow = 0;
	private int loosersBelowWeighted = 0;
	

	public int getNodeState(){
		return nodeState;
	}
	
	public void determineStatsDownwards(){		
		for(Node child : children){		
			winnersBelow += child.getWinnersBelow();
			winnersBelowWeighted += child.getWinnersWeightedBelow();
			loosersBelow += child.getLoosersBelow();
			loosersBelowWeighted += child.getLoosersBelowWeighted();
			
			if(child.iWon()){
				winnersBelow ++;
				winnersBelowWeighted += weightFactor;
			}
			if(child.iLost()){
				loosersBelow ++;
				loosersBelowWeighted += weightFactor * weightFactor; //drastic penalty increase for potential wins of the opponent in the turn after mine
			}
		}
		if(iWon()){
			winnersBelow ++;
			winnersBelowWeighted += weightFactor * weightFactor; // drastic bonus if i could win right now, why no give MAX_INTEGER here?
		}
		if(iLost()){
			loosersBelow ++;
			loosersBelowWeighted += weightFactor;
		}
	}
	
/*	public void incrementWinnersLineageUpwards(){		
		for(Node parent : parents)
			parent.incrementWinnersLineageUpwards();	
		if(!isLeaf())
			incrementWinnersBelow();	
	}
	
	public void incrementWinnersBelow(){
			winnersBelow ++;	
			winnersBelowWeighted += weightFactor;
	}
	
	public void incrementLoosersLineageUpwards(){
		for(Node parent : parents)
			parent.incrementLoosersLineageUpwards();
		if(!isLeaf())
			incrementLoosersBelow();		
	}
	
	public void incrementLoosersBelow(){
		loosersBelow ++;
		loosersBelowWeighted += weightFactor;
	}
	*/
	
	public int getWeightedDifference(){
		return winnersBelowWeighted - loosersBelowWeighted;
	}
	
	public int getWinnersBelow(){
		return winnersBelow;
	}
	
	public int getWinnersWeightedBelow(){
		return winnersBelowWeighted;
	}
	
	public int getLoosersBelowWeighted(){
		return loosersBelowWeighted;
	}
	
	public int getLoosersBelow(){
		return loosersBelow;
	}
	
	public boolean isLeaf(){
		return children.size() == 0;
	}
	
	public static void setStaticParams(int boardDim, int maxDepth, int winLength, int playerIndexIthinkFor){
		Node.boardDim = boardDim;
		Node.maxDepth = maxDepth;
		Node.winLength = winLength;
		Node.playerIndexIthinkFor = playerIndexIthinkFor;
	}
	
	public Node(Node parent, String ID, int[] boardArr, int indexWhereIplacedMyMark){
		this.ID = ID;
		if(parent != null){
			vertical = parent.getVertical() + 1;
			addParent(parent); //parents.add(parent);
		}
		this.boardArr = boardArr;
		this.indexWhereIplacedMyMark = indexWhereIplacedMyMark;
		
		BoardParserV2 bp = new BoardParserV2(); //TODO temporarily using V2
		bp.testBoardForWinner(boardArr, boardDim, winLength);		
		int winCheck = bp.getWinner();	
		
		//int winCheck = BoardParser.testBoardForWinner(boardArr, boardDim, winLength);
		if(winCheck != 0)
			nodeState = winCheck == playerIndexIthinkFor ? 1 : -1;
		
		//WEIGHT FACTOR
		int linearFactor = maxDepth - vertical + 1;
		weightFactor = (int) Math.pow(linearFactor, 2); //the 2 can be higher...
	}
	
	
	public int getIndexWhereIplacedMyMark(){
		return indexWhereIplacedMyMark;
	}
	
	public String getExtraInfo(){		
		return "winnersBelow: " + winnersBelow + "  loosersBelow: " + loosersBelow
			+ "\nwinnersBelowWeighted: " + winnersBelowWeighted + "  loosersBelowWeighted: " + loosersBelowWeighted
			+ "\nweight factor: " + weightFactor + "  weightedDifference: " + getWeightedDifference();
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
	
	public int[] getBoardArr(){
		return boardArr;
	}
	
	public String getID(){
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
		return nodeState != 0;
	}
	
	public boolean iWon(){
		return nodeState == 1;
	}
	
	public boolean iLost(){
		return nodeState == -1;
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
			buffer = buffer.substring(0, buffer.length() - 1) + "\n";
		}
		return buffer;
	}
	
}