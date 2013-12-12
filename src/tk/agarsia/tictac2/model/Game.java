package tk.agarsia.tictac2.model;

import java.util.ArrayList;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class Game extends Thread {
	
	private int interval; // in ms
	private int boardDim; // = 3;
	private int winLength; // = 3;
	private int marksPerTurn; // = 1;	
	private int markCount = 0;
	
	private int startPlayerIndex;
	private int currentPlayerIndex; // = 1;	
	private AbstractPlayer currentPlayer;
	
	private AbstractPlayer[] players;
	private AbstractPlayer winner = null;
	
	private Board board;
	
	private boolean gameRunning = false;
	private boolean awaitingClick = false;
	
	public Game(){
		players = new AbstractPlayer[3];
		players[0] = null;
	}
	
	
	/**
	 * Function to get AbstractPlayer.
	 *            
	 * 		@return returns a player object
	 * 
	 */
	public AbstractPlayer[] getPlayers() {
		return players;
	}
	
	/**
	 * Function to get the game board.
	 *     
	 * 		@return returns an board object
	 *          
	 */
	public Board getBoard(){
		return board;
	}
	

	/**
	 * Function to awaiting a click from current player.
	 */
	public void awaitingClick(){
		System.out.println("awaiting click from " + currentPlayer.getName());
		awaitingClick = true;
	}
	
	
	/**
	 * Function to mark the current placement.
	 * 
	 * 		@param row
	 * 		@param column
	 * 
	 * 		@return returns a true value if mark was correct, else value is false.
	 * 
	 */
	public boolean placeMark(int row, int column){
		
		if(row == -1 && column == -1){
			board.placeRandomly(currentPlayerIndex);
			markComplete();		
			return true;
		}
		else{		
			boolean temp = board.setField(currentPlayerIndex, row, column);		
			if(temp)
				markComplete();		
			return temp;
		}
	}

	/**
	 * Function to check if mark placement is complete and check if there is already a winner.
	 * 
	 */
	private void markComplete(){
		System.out.println("board after mark from " + currentPlayer.getName());
		System.out.println(board.show(true));
		
		if(!board.getWinState()){	
			if(!board.full()){
				markCount ++;
				
				if(markCount == marksPerTurn){
					markCount = 0;
					if(currentPlayerIndex == 1)
						currentPlayerIndex = 2;
					else
						currentPlayerIndex = 1;
					
					currentPlayer = players[currentPlayerIndex];
				}
				awaitingClick = false;
				currentPlayer.myTurn();
			}
			else
				gameRunning = false; //board full without winner	
		}
		else{			
			winner = currentPlayer;
			winner.incrementGameWon();
			winner.setWinningFields(board.getWinnersPositions());
			gameRunning = false;
		}
		
	}
	
	/**
	 * Function to check if game board is finished.
	 * 
	 *     @return returns the status if the game is still running.
	 * 
	 */
	public boolean getGameRunning(){
		return gameRunning;
	}
	
	
	/**
	 * Function to show current game board.
	 * 
	 *     @return returns an string that shows the current game board.
	 * 
	 */
	public String showBoard() {
		return board.show(true);
	}
	
	/**
	 * Function to run the application.
	 * 
	 */	
	@Override
	public void run(){
		while(gameRunning){
			try {
				Thread.sleep(1);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Funktion to handle local player click
	 * 
	 * @param row
	 * @param column
	 * 
	 * @return returns a true oder false value for the awaiting click.
	 */
	public boolean handleLocalPlayerClick(int row, int column) {
		if(awaitingClick){		
			boolean temp = currentPlayer.myChoice(row, column);	
			if(!temp)
				System.out.println("field already taken, choose another one");
			return temp;
		}		
		return false;
	}
	
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
	public void initModel(int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex) {
		this.interval = interval;
		this.boardDim = boardDim;
		this.winLength = winLength;
		this.marksPerTurn = marksPerTurn;
		this.startPlayerIndex = startPlayerIndex;
		currentPlayerIndex = startPlayerIndex;
		board = new Board(boardDim, winLength);
	}
	
	/**
	 * Function to initialize Player1 and Player 2.
	 *    
	 *            @param player1
	 *            			Set an AbspractPlayer 1.
	 *            @param player2
	 *            			Set an AbspractPlayer 2.
	 *            
	 */
	public void setPlayers(AbstractPlayer player1, AbstractPlayer player2){
		players[1] = player1;
		players[2] = player2;	
	}
	
	/**
	 * Function to start a game.
	 */
	@Override
	public void start(){	
		gameRunning = true;
		System.out.println("game started with players: " + players[1].getName() + " and " + players[2].getName());
		
		System.out.println(board.show(true));
		
		currentPlayer = players[startPlayerIndex];
		currentPlayer.myTurn();
		super.start();
	}
	
	/**
	 * Function to reset the Player who starts and the game board.
	 */
	public void reset() {
		currentPlayerIndex = startPlayerIndex;
		board.reset();
	}

	/**
	 * Function to get the board dimensions.
	 * 
	 * @return returns the current game board dimension.
	 */
	public int getBoardDim() {
		return boardDim;
	}

	/**
	 * Function to get win length.
	 * 
	 * @return returns the set win Length.
	 */
	public int getWinLength() {
		return winLength;
	}

	/**
	 * Function to get how many marks per round are allowed.
	 * 
	 * @return returns the set marks allowed per turn.
	 */
	public int getMarksPerTurn() {
		return marksPerTurn;
	}

	/**
	 * Function to get the player to start the game.
	 * 
	 * @return returns the player index who start the game.
	 */
	public int getStartPlayerIndex() {
		return startPlayerIndex;
	}

	/**
	 * Function to hold the game.
	 */
	public void pause() {}


	/**
	 * Function to get the current player index.
	 * 
	 * @return returns the current player index.
	 */
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}


	/**
	 * Function to get the winner.
	 * 
	 * @return returns the winner.
	 */
	public AbstractPlayer getWinner() {
		return winner;
	}

	/**
	 * Function to get current game history.
	 * 
	 * @return returns a 2d array of the board history.
	 */
	public ArrayList<int[]> getGameRecording() {
		return board.getHistory();
	}
}