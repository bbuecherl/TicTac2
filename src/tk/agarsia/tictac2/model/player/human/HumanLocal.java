package tk.agarsia.tictac2.model.player.human;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class HumanLocal extends AbstractPlayer{

	public HumanLocal(String name, Game game) {
		super(game);
		setPlayerType(0);
		setName(name);		
	}

	/**
	 * Tells game that we are waiting for a click. Once that click comes in, game is calling the myChoice method below directly.
	 * So this object doesn't influence the choice anymore - but given the fact that the local physical player has clicked, there is no need to modify that choice :)
	 */
	@Override
	public void myTurn() {
		game.awaitingClick();	
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}
	


	
}
