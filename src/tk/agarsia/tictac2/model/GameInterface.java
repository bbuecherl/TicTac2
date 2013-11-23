package tk.agarsia.tictac2.model;

import tk.agarsia.tictac2.model.board.Pos;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public interface GameInterface {

	public void initModel(int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex);
	public void setPlayers(AbstractPlayer player1, AbstractPlayer player2);
	public void start();
	public void pause();
	public void reset();	
	
	/* true : mark placed
	 * false : field already taken 
	 */	 
	public boolean handleLocalPlayerClick(Pos pos);
	
	public AbstractPlayer getWinner();
	public String getGameRecording();	
	
	public int getboardDim();
	public int getWinLength();
	public int getMarksPerTurn();
	public int getStartPlayerIndex();
	public int getCurrentPlayerIndex();
}
