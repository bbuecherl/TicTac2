package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;


public class BotRandom extends AbstractBot{

	public BotRandom(Game game) {
		super(game);
		setPlayerType(2);
		setName("RandomBot");
	}

	@Override
	public void myTurn() {
		myChoice(-1, -1); //"secret code" for game to call the placeRandomly method on board
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

}


