package tk.agarsia.tictac2.model.player.bot.tree;


public class WeightController {
	
	private static int boardDim;
	private static int winLength;
	private static int marksPerTurn;
	private static int playerIthinkFor;
	private static int maxDepth;
	
	private static int weightFactorWinEXPONENT = 3;
	private static int weightFactorLossEXPONENT = 4;
	private static int bonusFactorWinRun = 2;
	private static int bonusFactorLossRun = 2;
	
	//private static ArrayList<Node> collect = new ArrayList<Node>();
	
	
	/**
	 * setting the parameters. a few are not used at this stage, but left in there for possible later use
	 * @param boardDim
	 * @param winLength
	 * @param marksPerTurn
	 * @param playerIthinkFor
	 * @param maxDepth
	 */
	public static void setParams(int boardDim, int winLength, int marksPerTurn, int playerIthinkFor, int maxDepth){
		WeightController.boardDim = boardDim;
		WeightController.winLength = winLength;
		WeightController.marksPerTurn = marksPerTurn;
		WeightController.playerIthinkFor = playerIthinkFor;
		WeightController.maxDepth = maxDepth;		
	}
	
	/**
	 * setting the weight according to rules
	 * @param node the node that is "asking" to know it's weight
	 */
	public static void setWEIGHT(Node node){	
	
		 //WEIGHT FACTOR
        int linearFactor = maxDepth - node.getVertical() + 1;
        int weightFactorWin = (int) Math.pow(linearFactor, weightFactorWinEXPONENT); // could also be as high as linearFactor^linearFactor
		int weightFactorLoss = (int) Math.pow(linearFactor, weightFactorLossEXPONENT);
		node.setWeightFactors(weightFactorWin, weightFactorLoss);
        
	    if(node.iWon()){
	    	node.addToWinnersWEIGHTED(weightFactorWin);	 
	    	if(node.getVertical() == 1)
	    		node.addToWinnersWEIGHTED(Integer.MAX_VALUE - node.getWinnersWEIGHTED() - 1); //immediate win hyperbonus :)
	    }
	    if(node.iLost())
	    	node.addToLosersWEIGHTED(weightFactorLoss);
		
		//collect from children
		for(Node child : node.getChildren()){		
			node.addToWinnersWEIGHTED(child.getWinnersWEIGHTED());
			node.addToLosersWEIGHTED(child.getLosersWEIGHTED());			
			
			// win/loss runs, only looking to parent though
			if(child.getIPlaceSamePlayerIndexAsMyParents()){	
				if(child.iWon())
					node.addToWinnersWEIGHTED(child.getWeightFactorWin() * bonusFactorWinRun);
				if(child.iLost())
					node.addToLosersWEIGHTED(child.getWeightFactorLoss() * bonusFactorLossRun);	
			}
		}			
		

	}	
	
	
}