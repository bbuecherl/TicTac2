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
		
		System.out.println("it's smart bots turn...");
		TreeBuilder tree = new TreeBuilder(game.getBoard(), game.getCurrentPlayerIndex(), game.getMarksPerTurn());
		
		//tree.export();
		//tree.getDecision... and feed into myChoice

	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

	
}
