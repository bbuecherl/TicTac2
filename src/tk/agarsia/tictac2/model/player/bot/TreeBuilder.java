package tk.agarsia.tictac2.model.player.bot;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.Board;

public class TreeBuilder {

	private Board initialBoard;
	private int currentPlayerIndex;
	private int winLength;
	private int marksPerTurn;
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private Node rootnode;
	
	public Board getInitialBoard(){
		return initialBoard;
	}
	
	public int getCurrentPlayerIndex(){
		return currentPlayerIndex;
	}
	
	public TreeBuilder(Board board, int currentPlayerIndex, int marksPerTurn) {
		this.initialBoard = board;
		this.currentPlayerIndex = currentPlayerIndex;
		this.marksPerTurn = marksPerTurn;
		
		rootnode = new Node(this);
			
		try {
			Exporter.doExport(this, "BotSmart_DecisionTree.graphml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
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
