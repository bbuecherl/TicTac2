package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class WeightController {

	
	private static int boardDim;
	private static int winLength;
	private static int marksPerTurn;
	private static int playerIthinkFor;
	private static int maxDepth;
	
	private static int runFactor = 2;
	
	//private static ArrayList<Node> collect = new ArrayList<Node>();
	
	
	public static void setParams(int boardDim, int winLength, int marksPerTurn, int playerIthinkFor, int maxDepth){
		WeightController.boardDim = boardDim;
		WeightController.winLength = winLength;
		WeightController.marksPerTurn = marksPerTurn;
		WeightController.playerIthinkFor = playerIthinkFor;
		WeightController.maxDepth = maxDepth;		
	}
	
	public static void setWEIGHT(Node node){	
		for(Node child : node.getChildren()){		
			int factor = 1;
			if(child.getPlayerIndexIplace() == node.getPlayerIndexIplace())
				factor = runFactor;			//current attempt for wins/losses in a "run" meaning within the turns i can expect... 
			node.addToWinnersWEIGHTED(child.getWinnersWEIGHTED() * factor); //old: winnersBelowWeighted += child.getWinnersWeightedBelow();
			node.addToLosersWEIGHTED(child.getLosersWEIGHTED() * factor);
		}	
		
		 //WEIGHT FACTOR
        int linearFactor = maxDepth - node.getVertical() + 1;
        int weightFactorWin = (int) Math.pow(linearFactor, 2);
		int weightFactorLoss = weightFactorWin;
		node.setWeightFactors(weightFactorWin, weightFactorLoss);
        
	    if(node.iWon()){
	    	node.addToWinnersWEIGHTED(weightFactorWin);	    	
	    }
	    if(node.iLost())
	    	node.addToLosersWEIGHTED(weightFactorLoss);
	}
	
}


/*	public static void extraWeights(Node node){		
		for(Node child : node.getChildren()){					
			if(node.getPlayerIndexIplace() == child.getPlayerIndexIplace()){
				node.addToWinnersWEIGHTED(child.getWinnersWEIGHTED());
				node.addToLosersWEIGHTED(child.getLosersWEIGHTED());
			}
		}		
	}
	*/
	
	
		/*
			collectChildren(node);
			ArrayList<Node> allChildrenUpTo = filterChildren(node.getVertical() + 1 + forHowManyTurnsLeftIsItMyTurn);
		
			int winsInThisRun = 0;
			int lossesInThisRun = 0;		
			for(Node below : allChildrenUpTo){
				if(below.iWon())
					winsInThisRun ++;
				if(below.iLost())
					lossesInThisRun ++;
			}
			collect.clear();
			
			node.addToWinnersWEIGHTED(winsInThisRun * node.getWeightFactorWin());
			node.addToLosersWEIGHTED(lossesInThisRun * node.getWeightFactorLoss());
		
	}
	
	
	private static void collectChildren(Node parent){
		collect.add(parent);

		for(Node child : parent.getChildren())
			collectChildren(child);		
	}
	
	private static ArrayList<Node> filterChildren(int upToDepth){
		ArrayList<Node> finalCollect = new ArrayList<Node>();
				
		for(Node node : collect)
			if(node.getVertical() <= upToDepth)
				finalCollect.add(node);
		
		return finalCollect;
	}*/
	
	
/*	private static ArrayList<Node> findChildrenSoFarDeeper(Map<String, Node> nodes, Node parent, int soManyLevelsDeeper){
		ArrayList<Node> collect = new ArrayList<Node>();		
		
		int parentVertical = parent.getVertical();
		
		for(Entry<String, Node> entry : nodes.entrySet()){
		    if(entry.getValue().getVertical() > parentVertical && entry.getValue().getVertical() <= (parentVertical + soManyLevelsDeeper))
				collect.add(entry.getValue());
		}		
		    return collect;
	}*/
