package tk.agarsia.tictac2.model;

import tk.agarsia.tictac2.model.player.AbstractPlayer;

public interface GameInterface {

	/**
	 * Function to initialize options for the board to play.
	 *    
	 * @param interval
	 * 			in ms.
	 * @param boardDim
	 * 			set board dimension.
	 * @param winLength
	 * 			set win Length.
	 * @param marksPerTurn
	 * 			set how many marks per round are allowed.
	 * @param startPlayerIndex
	 * 			which player starts.
	 *            
	 */
	public void initModel(int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex);
	
	/**
	 * Function to initialize Player1 and Player 2.
	 *    
	 *            @param player1
	 *            			Set an AbspractPlayer 1.
	 *            @param player2
	 *            			Set an AbspractPlayer 2.
	 *            
	 */
	public void setPlayers(AbstractPlayer player1, AbstractPlayer player2);
	
	/**
	 * Function to start a game.
	 */
	public void start();
	
	/**
	 * Function to pause a game.
	 */
	public void pause();
	
	/**
	 * Function to reset a game.
	 */
	public void reset();	
	
	/* true : mark placed
	 * false : field already taken 
	 */	 
	
	/**
	 * Function to handle local players click.
	 * 
	 * 				@param row
	 * 				@param column
	 */
	public boolean handleLocalPlayerClick(int row, int column);
	
	/**
	 * Function to get winner.
	 */
	public AbstractPlayer getWinner();
	
	/**
	 * Function to get current game history.
	 */
	public String getGameRecording();	
	
	/**
	 * Function to board dimension.
	 */
	public int getBoardDim();
	
	/**
	 * Function to get win length.
	 */
	public int getWinLength();
	
	/**
	 * Function to get how many marks per round allowed.
	 */
	public int getMarksPerTurn();
	
	/**
	 * Function to get the player to start the game.
	 */
	public int getStartPlayerIndex();
	
	/**
	 * Function to get the current player index.
	 */
	public int getCurrentPlayerIndex();
	
}
