package tk.agarsia.tictac2.model.player.human;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Pos;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class HumanLocal extends AbstractPlayer{

	public HumanLocal(String name, Game game) {
		super(game);
		setPlayerType(0);
		setName(name);		
	}

	@Override
	public void myTurn() {
		game.awaitingClick();	
	}

	@Override
	public boolean myChoice(Pos pos) {
		return game.placeMark(pos);
	}
	


	
}
