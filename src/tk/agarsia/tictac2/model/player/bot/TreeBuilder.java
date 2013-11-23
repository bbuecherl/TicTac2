package tk.agarsia.tictac2.model.player.bot;

import tk.agarsia.tictac2.model.board.Board;

public class TreeBuilder {

	private Board currentBoard;
	private int currentPlayerIndex;
	private int winLength;
	private int marksPerTurn;
	
	public TreeBuilder(Board board, int currentPlayerIndex, int marksPerTurn) {
		this.currentBoard = board;
		this.currentPlayerIndex = currentPlayerIndex;
		this.marksPerTurn = marksPerTurn;
	}

}
