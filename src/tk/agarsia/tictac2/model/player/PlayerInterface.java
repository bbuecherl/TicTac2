package tk.agarsia.tictac2.model.player;

import tk.agarsia.tictac2.model.board.Pos;

public interface PlayerInterface {

	public void myTurn();
	public boolean myChoice(Pos pos);
	
}
