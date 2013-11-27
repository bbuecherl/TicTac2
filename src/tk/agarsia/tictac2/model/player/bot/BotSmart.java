package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;
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
											//first param is the maxDepth, this has to be implemented to be dynamically calculated though using the formula in issue #9 ...
		TreeBuilder tree = new TreeBuilder(3, game.getBoard(), game.getCurrentPlayerIndex(), game.getMarksPerTurn());
		
		//it doesn't make choices just yet... hang in there :)
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

	
}
