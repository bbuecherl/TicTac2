package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.BoardParserV2;


public class Node {

	
	private static int boardDim;
	private static int winLength;
	private static int playerIndexIthinkFor;
	private static int maxDepth;

	private String ID;

	private int[] boardArr;
	private int indexWhereIplacedMyMark = -1;
	private int forHowManyTurnsLeftIsItMyTurn = 0;
	private int playerIndexIplace = 0;
	private boolean iPlaceSamePlayerIndexAsMyParents = false;
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children = new ArrayList<Node>();
	private int vertical = 0;
	private int weightFactorWin = 1;
	private int weightFactorLoss = 1;
	
	/**
	 * 0: no winner
	 * 1: i won
	 * -1: i lost
	 */
	private int nodeState = 0;
	
	private int winners = 0;
	private int winnersWEIGHTED = 0;
	private int losers = 0;
	private int losersWEIGHTED = 0;
	
/*	private int x = 0;
	private int y = 0;
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}*/
	
	public int getForHowManyTurnsLeftIsItMyTurn(){
		return forHowManyTurnsLeftIsItMyTurn;
	}
	
	public int getPlayerIndexIplace(){
		return playerIndexIplace;
	}
	
	public int getNodeState(){
		return nodeState;
	}
	
	public void countWinLoss(){		
		for(Node child : children){		
			winners += child.getWinners();
			losers += child.getLosers();
		}	
		if(iWon())
			winners ++;
		if(iLost())
			losers ++;
	}
	
	public int getWeightedDifference(){
		return winnersWEIGHTED - losersWEIGHTED;
	}
	
	public int getWinners(){
		return winners;
	}
	

	public int getWeightFactorWin(){
		return weightFactorWin;
	}
	public int getWeightFactorLoss(){
		return weightFactorLoss;
	}
	
	public void addToWinnersWEIGHTED(int add){
		winnersWEIGHTED += add;
	}
	
	public void addToLosersWEIGHTED(int add){
		losersWEIGHTED += add;
	}
	
	public int getWinnersWEIGHTED(){
		return winnersWEIGHTED;
	}
		
	public int getLosersWEIGHTED(){
		return losersWEIGHTED;
	}
	
	public int getLosers(){
		return losers;
	}
	
	public static void setStaticParams(int boardDim, int maxDepth, int winLength, int playerIndexIthinkFor){
		Node.boardDim = boardDim;
		Node.maxDepth = maxDepth;
		Node.winLength = winLength;
		Node.playerIndexIthinkFor = playerIndexIthinkFor;
	}
	
	public Node(Node parent, String ID, int[] boardArr, int indexWhereIplacedMyMark, int forHowManyTurnsLeftIsItMyTurn){
		this.ID = ID;
		
		this.boardArr = boardArr;
		this.indexWhereIplacedMyMark = indexWhereIplacedMyMark;
		this.forHowManyTurnsLeftIsItMyTurn = forHowManyTurnsLeftIsItMyTurn;
		
		if(parent != null){
			vertical = parent.getVertical() + 1;
			addParent(parent);
			playerIndexIplace = boardArr[indexWhereIplacedMyMark]; // :)
		
			BoardParserV2 bp = new BoardParserV2(); //TODO temporarily using V2
			bp.testBoardForWinner(boardArr, boardDim, winLength);		
			int winCheck = bp.getWinner();	
			
			//int winCheck = BoardParser.testBoardForWinner(boardArr, boardDim, winLength);
			if(winCheck != 0)
				nodeState = winCheck == playerIndexIthinkFor ? 1 : -1;
		}
	}
	
	public void setWeightFactors(int weightFactorWin, int weightFactorLoss){
		this.weightFactorWin = weightFactorWin;
		this.weightFactorLoss = weightFactorLoss;
	}
	
	public int getIndexWhereIplacedMyMark(){
		return indexWhereIplacedMyMark;
	}
	
	public boolean getIPlaceSamePlayerIndexAsMyParents(){
		return iPlaceSamePlayerIndexAsMyParents;
	}
	
	public String getExtraInfo(){			
		
		//return "";//winners + "|" + losers;// + "\nx: " + x + " y: " + y;
		
		return "placed " + playerIndexIplace + " at (" + (indexWhereIplacedMyMark / boardDim) + ", " + (indexWhereIplacedMyMark % boardDim) + ")"
				+ "\nhow many more turns do i have: " + forHowManyTurnsLeftIsItMyTurn
				+ "\niPlaceSamePlayerIndexAsMyParents: " + iPlaceSamePlayerIndexAsMyParents
				+ "\nwin: " + winners + "  loose: " + losers
				+ "\nwinF+: " + winnersWEIGHTED + "  looseF: " + losersWEIGHTED
				+ "\nweightFactors(win/loss): " + weightFactorWin + "/" + weightFactorLoss + "  diff(winW-lossW): " + getWeightedDifference();
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public void addParent(Node parent){ // parent-child handshake
		parents.add(parent);		
		iPlaceSamePlayerIndexAsMyParents = playerIndexIplace == parent.getPlayerIndexIplace();		
		parent.addChild(this);
	}
	
	public void addChild(Node child){
		children.add(child);
	}

	public ArrayList<Node> getChildren(){
		return children;
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