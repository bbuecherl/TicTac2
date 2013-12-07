package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.bot.tree.TreeBuilder;

public class BotSmart extends AbstractBot{

	//int turnCount = 0;
	private TreeBuilder decisionGraph;
	private int marksPerTurn;
	private int marksCount = 0;
	
	public BotSmart(Game game) {
		super(game);
		setPlayerType(2);
		setName("SmartBot" + this.hashCode());
		marksPerTurn = game.getMarksPerTurn();
	}

	public void exportLastGraph(){
		decisionGraph.export();
	}
	
	@Override
	public void myTurn() {	
		
		marksCount ++;
		if(marksCount > marksPerTurn)
			marksCount = 1;
		
		//if(turnCount > 2){
		
		System.out.println(">> it's smart bots turn...(currentPlayer: " + game.getCurrentPlayerIndex() + ")");

		int dynamicDepth = game.getBoard().getFreeFieldCount();
		
		int cap = 5;
		if(game.getBoard().getBoardDim() > 4)
			cap --;
		if(game.getBoard().getBoardDim() > 5)
			cap --;
			
		if(dynamicDepth > cap)
			dynamicDepth = cap;
		
		
		decisionGraph = new TreeBuilder(dynamicDepth, marksCount, game.getBoard(), game.getCurrentPlayerIndex(), game.getMarksPerTurn());
		System.out.println("dynDepth: " + dynamicDepth);
		
		int chosenIndex = decisionGraph.getChoiceIndex();
		
		int row = chosenIndex / game.getBoardDim();
		int column = chosenIndex % game.getBoardDim();
		
		System.out.println("chosenIndex: " + chosenIndex + " > row: " + row + " column: " + column);
		
		System.out.println("MARKS_COUNT: " + marksCount);
		
		myChoice(row, column);
		
		
/*		}
		else{
			
			if(turnCount == 0)
				myChoice(1, 2);
			if(turnCount == 1)
				myChoice(3, 3);
			if(turnCount == 2)
				myChoice(3, 2);		
			
			turnCount ++;
		}*/
		
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

	
}
