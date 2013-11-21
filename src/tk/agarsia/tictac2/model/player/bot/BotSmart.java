package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Pos;

public class BotSmart extends AbstractBot{

	public BotSmart(Game game) {
		super(game);
		setPlayerType(2);
		setName("SmartBot" + this.hashCode());
	}

	@Override
	public void myTurn() {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean myChoice(Pos pos) {
		return game.placeMark(pos);
	}

	
}
