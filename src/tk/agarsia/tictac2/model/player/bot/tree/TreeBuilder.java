package tk.agarsia.tictac2.model.player.bot.tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.Field;

public class TreeBuilder {

	private Board initialBoard;
	private int currentPlayerIndex;
	private int marksPerTurn;
	private int maxDepth;
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private Node rootnode;

	public TreeBuilder(int maxDepth, Board board, int currentPlayerIndex, int marksPerTurn) {
		this.maxDepth = maxDepth;
		this.initialBoard = board;
		this.currentPlayerIndex = currentPlayerIndex;
		this.marksPerTurn = marksPerTurn;
		
		//create turnIndize array
		int freeFieldCount = board.getFreeFieldCount();
		int[] turnIndize = new int[freeFieldCount];
		int whosTurn = currentPlayerIndex;
		int marksCount = 1;
		for(int i = 0; i < freeFieldCount; i++){
			turnIndize[i] = whosTurn;
			marksCount ++;
			if(marksCount > marksPerTurn){
				whosTurn = whosTurn == 1 ? 2 : 1;
				marksCount = 1;
			}
		}	
//		for(int i = 0; i < freeFieldCount; i++) //proof
//			System.out.println(turnIndize[i]);
			
		rootnode = new Node(this, null, board.getBoardAsArr(), turnIndize, 0, board.getBoardDim(), board.getWinLength());
		
		try {
			Exporter.doExport(this, "BotSmart_DecisionTree.graphml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("export of decision tree sucessfull");	
		
		System.exit(0);
	}

	
	public int getCurrentPlayerIndex(){
		return currentPlayerIndex;
	}
	
	public int getMaxDepth(){
		return maxDepth;
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}
	
	public void addEdge(Edge edge){
		edges.add(edge);
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
}
