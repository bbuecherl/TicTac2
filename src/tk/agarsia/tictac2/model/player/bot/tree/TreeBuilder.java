package tk.agarsia.tictac2.model.player.bot.tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.BoardParser;

public class TreeBuilder {

	private int myPlayerIndex;
	private int maxDepth;
	private int boardDim;
	private int winLength;
	private int marksPerTurn;
	
	Map<String, Node> nodes = new HashMap<String, Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private Node rootnode;
	
	private int indexWhereNodeWithMaxDiffPlacedMark = -1; //this is why we do the whole thing... we want the index where to place next!
	

	public int getChoiceIndex(){
		return indexWhereNodeWithMaxDiffPlacedMark;
	}
	
	public TreeBuilder(int maxDepth, int marksCount, Board board, int whosTurn, int marksPerTurn) {
		this.maxDepth = maxDepth;
		this.boardDim = board.getBoardDim();
		this.winLength = board.getWinLength();
		this.marksPerTurn = marksPerTurn;
		this.myPlayerIndex = whosTurn;
		
		WeightController.setParams(boardDim, winLength, marksPerTurn, myPlayerIndex, maxDepth);				
		Node.setStaticParams(board.getBoardDim(), maxDepth, board.getWinLength(), myPlayerIndex);
			
		int[] boardArr = board.getBoardAsArr(); //System.out.println("boardArr: " + BoardParser.mergeArrIntoString(boardArr) + " " + boardArr.length); //debug
		rootnode = new Node(null, BoardParser.mergeArrIntoString(boardArr), boardArr, -1, -1); //-1 because this node doesn't place a mark, so it's just a dummy
		nodes.put(rootnode.getID(), rootnode);
				
		ArrayList<Node> level = new ArrayList<Node>();
		level.add(rootnode);

		for(int i = 0; i < maxDepth; i ++){
			
			//this is replacing the previous awkward turnIndize-array
			if(marksCount > marksPerTurn){
				marksCount = 1;
				whosTurn = (whosTurn == 1 ? 2 : 1);
			}
			int howManyTurnsLeft = marksPerTurn - marksCount;
			marksCount ++;			
						
			ArrayList<Node> nextLevel = new ArrayList<Node>();	
			
			for(Node parent : level){
				
				if(!parent.wonOrLost()){
					
					int[] emptyIndize = BoardParser.getEmptyIndizeOpt(parent.getBoardArr()); //System.out.println("emptyIndize: " + emptyIndize.length + " " + BoardParser.mergeArrIntoString(boardArr)); //debug
									
					for(int j = 0; j < emptyIndize.length; j++){ //System.out.println("emptyIndize " + j + " : " + emptyIndize[j] + "  length: " + emptyIndize.length); //debug
						int[] newBoardArr = BoardParser.boardArrCopy(parent.getBoardArr());
						newBoardArr[emptyIndize[j]] = whosTurn;										
						String boardArrAsString = BoardParser.mergeArrIntoString(newBoardArr);
						
						if(nodes.keySet().contains(boardArrAsString)){	//node already in, just make a new edge and do parent-child handshake
							Node alreadyThere = nodes.get(boardArrAsString);
							edges.add(new Edge(parent, alreadyThere));
							alreadyThere.addParent(parent);
						}
						else{ //no node with that ID yet, create a new one
							Node node = new Node(parent, boardArrAsString, newBoardArr, emptyIndize[j], howManyTurnsLeft);
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

		
		//count win/loss on each node AND standard weight BOTTOM UP
		for(int i = maxDepth; i >= 0; i--){			
			level.clear();
			level = getNodesAtLevel(i);	
			for(Node node : level){
				node.countWinLoss();
				WeightController.setWEIGHT(node);
			}
		}

			
		//find best child of rootnode (highest difference-value), which is the choice of placing the mark indeed			
		int maxDifference = -Integer.MAX_VALUE;			
		Node rootnodeChildWithMaxDiff = null;
	
		for(Node rootnodeChild : rootnode.getChildren()){
			System.out.println("weightedDiff: " + rootnodeChild.getWeightedDifference() + "  [" + rootnodeChild.getID() + "]"); // debugging			
				
			int myDiff = rootnodeChild.getWeightedDifference();
			//System.out.println(rootnodeChild.getID() + " diff: " + myDiff); //debug
			if(myDiff > maxDifference){
				maxDifference = myDiff;
				rootnodeChildWithMaxDiff = rootnodeChild;
			}		
		}

		
		//go through once more to collect rootnode children with the same weightedDifference to the choose randomly amgonst them. TODO more performant?
		ArrayList<Node> collectMaxDiffs = new ArrayList<Node>();
		for(Node rootnodeChild : rootnode.getChildren())
			if(rootnodeChild.getWeightedDifference() == maxDifference)
				collectMaxDiffs.add(rootnodeChild);

		if(collectMaxDiffs.size() > 1){
			Random randomGenerator = new Random();
			int randomChoice = randomGenerator.nextInt(collectMaxDiffs.size());
			rootnodeChildWithMaxDiff = collectMaxDiffs.get(randomChoice);
		}
				
		indexWhereNodeWithMaxDiffPlacedMark = rootnodeChildWithMaxDiff.getIndexWhereIplacedMyMark();	
		System.out.println("maxDiff is owned by " + rootnodeChildWithMaxDiff.getID() + " (possibly chosen randomly amongst siblings with same maxDiff) with weighted difference of: " + rootnodeChildWithMaxDiff.getWeightedDifference());
	}	
	
	
	public void export(){
		try {
			Exporter.setBoardDim(boardDim);
			Exporter.doExport(this, "GRAPH_boardDim" + boardDim + "_winLength" + winLength + "_marksPerTurn" + marksPerTurn + "_depth" + maxDepth + "_nodes"+ nodes.size() + "_edges" + edges.size() + ".graphml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("export of decision-graph sucessfull");
		//System.exit(0);
	}
	
	
	private ArrayList<Node> getNodesAtLevel(int vertical){	
		return getNodesAtLevel(nodes, vertical);
	}
	
	public static ArrayList<Node> getNodesAtLevel(Map<String, Node> nodesParam, int vertical){	
		ArrayList<Node> collect = new ArrayList<Node>();		
		for(Entry<String, Node> entry : nodesParam.entrySet()){
/*		    int key = entry.getKey();
		    Node node = entry.getValue();*/
		    if(entry.getValue().getVertical() == vertical)
				collect.add(entry.getValue());
		}		
		    return collect;
	}
	
	public ArrayList<Node> getNodes(){
		ArrayList<Node> allNodes = new ArrayList<Node>();		
		for(Entry<String, Node> entry : nodes.entrySet())
			allNodes.add(entry.getValue());		
		return allNodes;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
}
