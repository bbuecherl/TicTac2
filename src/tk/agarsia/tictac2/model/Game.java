package tk.agarsia.tictac2.model;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class Game extends Thread {
	
	private int interval; //TODO release 2
	
	private int boardDim;
	private int winLength;
	
	private int marksPerTurn;
	private int markCount = 0;
	
	private int startPlayerIndex;
	private int currentPlayerIndex;
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
	 * Gives access to all Players (Abstract player is extended by random bot, smart bot, local human or remote human).
	 *            
	 * 	@return the Array that holds all AbstractPlayer objects
	 */
	public AbstractPlayer[] getPlayers() {
		return players;
	}
	
	/**
	 * Gives access to the game board.
	 *     
	 * 	@return the instance of Board that is being played on
	 */
	public Board getBoard(){
		return board;
	}		

	/**
	 *  Sets the awaitingClick boolean to true; whose trueness is the requirement for handling a board-click from the user 
	 */
	public void awaitingClick(){
		System.out.println("awaiting click from " + currentPlayer.getName());
		awaitingClick = true;
	}
	
	/**
	 *  Marks the desired cell of the board with the index of the currentPlayer.
	 *  -1, -1 is the "code" for placing a mark randomly, used by random bot
	 *  Only if the field is not occupied (=board.setField returns true) yet the method moves on to markComplete()
	 * 
	 * 	@param row of the desired cell
	 * 	@param column of the desired cell
	 * 
	 * 	@return true if the mark was placed, false if not
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
	 * Completes the steps after having placed a mark successfully (otherwise this method wouldn't have been called).
	 * Three cases are possible here:
	 * Case 1: 
	 * 		The board shifted into winState through the last mark -> this method sets the currentPlayer as the winner,
	 * 		increments its win counter, sets the winning fields and terminates the game by putting gameRuning to false.
	 * Case 2:
	 * 		The board is not in winState but full -> the game terminates without a winner.
	 * Case 3:
	 * 		No winState and the board is not full yet -> next turn: the currentPlayer index is incremented (or not, depends if marCount has reached marksPerTurn),
	 * 		the next (or same) player gets the "torch" and is notified that its his turn (myTurn()), awaitingClick will be set to false, the player will have to set it to true again
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
	 * Access to the state of the game; running or terminated
	 * 
	 *  @return true: game is running, false: game is over
	 */
	public boolean getGameRunning(){
		return gameRunning;
	}
	
	
	/**
	 * Gives access to a string-representation of the board that board can create
	 * 
	 *  @return string that represents the board (as many lines as rows)
	 */
	public String showBoard() {
		return board.show(true);
	}
	
	/**
	 * Keep the thread running while gameRunning is true
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
	 * If game is awaiting a click from the user, this method passes the coordinates of a click to the current player where it then 
	 * calls placeMark on game immediately. This "circular" approach was taken so that humans and bots can be "treated" the same 
	 * way from a code-architecture point of view
	 * 
	 * @param clicked row
	 * @param clicked column
	 * 
	 * @return true if the mark was placed successfully. false: if no click is being awaited or if the field is already taken.
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
	 * Initializes the parameters for this game based on what the user set in the options.
	 * Note: The option-screen is already taking care that no pointless parameters are set. 
	 * For instance a winLength higher than the boardDimension makes winning impossible whereas
	 * marksPerTurn as high as the winLength lets the first player win right away.
	 * 
	 * @param interval: in ms
	 * @param boardDim: the dimension of the board from 3x3 to 6x6
	 * @param winLength: the length of connected fields required for winning
	 * @param marksPerTurn: how many marks a player can place per round 
	 * @param startPlayerIndex: the index of the player who has the first turn: 1 or 2
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
	 * Setting the players based on the choices in the previous option screen
	 *    
	 * @param player1 set AbspractPlayer 1.
	 * @param player2 set AbspractPlayer 2.           
	 */
	public void setPlayers(AbstractPlayer player1, AbstractPlayer player2){
		players[1] = player1;
		players[2] = player2;	
	}
	
	/**
	 * Starts the game by setting gameRunning to true, passing the "torch" to the first player an notifying him that its his turn
	 */
	public void start(){	
		gameRunning = true;
		System.out.println("game started with players: " + players[1].getName() + " and " + players[2].getName());
		
		System.out.println(board.show(true));
		
		currentPlayer = players[startPlayerIndex];
		currentPlayer.myTurn();
		super.start();
	}
	
	/**
	 * Resetting the game.
	 */
	public void reset() {
		currentPlayerIndex = startPlayerIndex;
		board.reset();
	}

	/**
	 * Gives access to the board dimension.
	 * 
	 * @return boardDim
	 */
	public int getBoardDim() {
		return boardDim;
	}

	/**
	 * Gives access to the winning length.
	 * 
	 * @return winLength
	 */
	public int getWinLength() {
		return winLength;
	}

	/**
	 * Gives access to the number of marks that are allowed per player per turn
	 * 
	 * @return marksPerTurn
	 */
	public int getMarksPerTurn() {
		return marksPerTurn;
	}

	/**
	 * Gives access to the index of the player who had the first turn: 1 or 2
	 * 
	 * @return startPlayerIndex
	 */
	public int getStartPlayerIndex() {
		return startPlayerIndex;
	}

	/**
	 * Pauses the game.
	 * Not implemented yet.
	 */
	public void pause() {
		//TODO
	}

	/**
	 * Gives access to the index of the player whos turn it currently is: 1 or 2
	 * 
	 * @return currentPlayerIndex
	 */
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	/**
	 * Gives access to the player how won
	 * 
	 * @return the winner, null if there is no winner (yet)
	 */
	public AbstractPlayer getWinner() {
		return winner;
	}

	/**
	 * Gives access to the recorded game history.
	 * 
	 * @return a string of the game history, recorded by the board whenver a mark was set
	 */
	public String getGameRecording() {
		return board.getHistory();
	}
}