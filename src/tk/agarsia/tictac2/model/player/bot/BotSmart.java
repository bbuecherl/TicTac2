package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Field;
import tk.agarsia.tictac2.model.player.bot.tree.TreeBuilder;

public class BotSmart extends AbstractBot{

	public BotSmart(Game game) {
		super(game);
		setPlayerType(2);
		setName("SmartBot" + this.hashCode());
	}

	@Override
	public void myTurn() {	
		
		System.out.println("it's smart bots turn...(currentPlayer: " + game.getCurrentPlayerIndex() + ")");

		int dynamicDepth = game.getBoard().getFreeFieldCount();
		TreeBuilder decisionGraph = new TreeBuilder(dynamicDepth, game.getBoard(), game.getCurrentPlayerIndex(), game.getMarksPerTurn());
		System.out.println("dynDepth: " + dynamicDepth);
		
		int chosenIndex = decisionGraph.getChoiceIndex();
		System.out.println("chosenIndex: " + chosenIndex);
		
		myChoice(chosenIndex / game.getBoardDim(), chosenIndex % game.getBoardDim());
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

	
}
