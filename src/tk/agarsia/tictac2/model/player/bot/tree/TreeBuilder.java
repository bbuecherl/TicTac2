package tk.agarsia.tictac2.model.player.bot.tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.BoardParser;

public class TreeBuilder {

	private Board initialBoard;
	private int currentPlayerIndex;
	private int maxDepth;
	
	//private ArrayList<Node> nodes = new ArrayList<Node>();
	Map<Integer, Node> nodes = new HashMap<Integer, Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private Node rootnode;

	public TreeBuilder(int maxDepth, Board board, int currentPlayerIndex, int marksPerTurn) {
		this.maxDepth = maxDepth;
		this.initialBoard = board;
		this.currentPlayerIndex = currentPlayerIndex;
		
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
		for(int i = 0; i < freeFieldCount; i++) //proof
			System.out.println(turnIndize[i]);
				
		Node.setStaticParams(board.getBoardDim(), board.getWinLength(), currentPlayerIndex);
			
		int[] boardArr = board.getBoardAsArr();
		rootnode = new Node(null, 0, boardArr, 0);
		nodes.put(rootnode.getID(), rootnode);
				
		ArrayList<Node> level = new ArrayList<Node>();
		level.add(rootnode);
		
		for(int i = 0; i < maxDepth; i ++){
			
			whosTurn = turnIndize[i];
			ArrayList<Node> nextLevel = new ArrayList<Node>();	
			
			for(Node parent : level){
				
				if(!parent.wonOrLost()){				
					int[] emptyIndize = BoardParser.getEmptyIndizeOpt(parent.getBoardArr());			
					for(int j = 0; j < emptyIndize.length; j++){					
						int[] newBoardArr = BoardParser.boardArrCopy(parent.getBoardArr());
						newBoardArr[emptyIndize[j]] = whosTurn;		
						
						int boardArrAsInt = BoardParser.mergeArrIntoInteger(newBoardArr);
						
						if(nodes.keySet().contains(boardArrAsInt)){	//node alread in, just make a new link
							edges.add(new Edge(parent, nodes.get(boardArrAsInt)));
						}
						else{ //no node with that ID yet, create a new one
							Node node = new Node(parent, boardArrAsInt, newBoardArr, whosTurn);
							nodes.put(node.getID(), node);
							nextLevel.add(node);
							edges.add(new Edge(parent, node));
						}	
					}				
				}			
			}			
			level.clear();
			level = nextLevel;
		}		

		try {
			Exporter.doExport(this, "GRAPH_boardDim" + board.getBoardDim() + "_winLength" + board.getWinLength() + "_marksPerTurn" + marksPerTurn + "_depth" + maxDepth + "_nodes"+ nodes.size() + "_edges" + edges.size() + ".graphml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("export of decision-graph sucessfull");
		
		System.exit(0);
	}	
	
/*	private ArrayList<Node> getNodesAtLevel(int vertical){	
		ArrayList<Node> collect = new ArrayList<Node>();		
		for(Entry<Integer, Node> entry : nodes.entrySet()){
		    int key = entry.getKey();
		    Node node = entry.getValue();
		    if(entry.getValue().getVertical() == vertical)
				collect.add(entry.getValue());
		}		
		    return collect;
	}*/
	
	public ArrayList<Node> getNodes(){
		ArrayList<Node> allNodes = new ArrayList<Node>();		
		for(Entry<Integer, Node> entry : nodes.entrySet())
			allNodes.add(entry.getValue());		
		return allNodes;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
}
