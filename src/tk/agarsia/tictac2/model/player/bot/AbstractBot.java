package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public abstract class AbstractBot extends AbstractPlayer {

	protected int boardDimension;
	protected Board board;
	
	public AbstractBot(Game game){
		super(game);
		this.boardDimension = game.getBoardDimension();
		this.board = game.getBoard();
	}
	

	
}
