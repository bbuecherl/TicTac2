package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;

public class BotSmart extends AbstractBot{

	public BotSmart(Game game) {
		super(game);
		setPlayerType(2);
		setName("SmartBot" + this.hashCode());
	}

	@Override
	public void myTurn() {	
		//TODO release 2
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

	
}
