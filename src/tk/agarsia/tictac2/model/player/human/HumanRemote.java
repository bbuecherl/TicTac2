package tk.agarsia.tictac2.model.player.human;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class HumanRemote extends AbstractPlayer {

	public HumanRemote(String name, Game game) {
		super(game);
		setPlayerType(1);
		setName(name);		
	}

	@Override
	public void myTurn() {
		// TODO wait for remote client to tell choice... then call myChoice(pos)
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}
	

}
