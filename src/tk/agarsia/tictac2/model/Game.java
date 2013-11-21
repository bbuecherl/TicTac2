package tk.agarsia.tictac2.model;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.board.BoardManager;
import tk.agarsia.tictac2.model.board.Pos;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class Game extends Thread implements GameInterface {
	
	private int interval; // in ms
	private int boardDimension; // = 3;
	private int winLength; // = 3;
	private int marksPerTurn; // = 1;	
	private int markCount = 0;
	private int startPlayerIndex;
	private int currentPlayerIndex; // = 1;	
	private AbstractPlayer currentPlayer;
	
	private AbstractPlayer[] players;
	
	private BoardManager boardManager;
	
	private boolean gameRunning = false;
	private boolean awaitingClick = false;
	private AbstractPlayer winner = null;
		
	public Game(){
		players = new AbstractPlayer[3];
		players[0] = null;
	}
	
	public Board getBoard(){
		return boardManager.getBoard();
	}
	
	@Override
	public void run(){
		while(gameRunning){
			//timer
		}
	}
	
	@Override
	public void initModel(int interval, int boardDimension, int winLength, int marksPerTurn, int startPlayerIndex) {
		this.interval = interval;
		this.boardDimension = boardDimension;
		this.winLength = winLength;
		this.marksPerTurn = marksPerTurn;
		this.startPlayerIndex = startPlayerIndex;
		currentPlayerIndex = startPlayerIndex;
		boardManager = new BoardManager(boardDimension, winLength);
	}
	
	@Override
	public void setPlayers(AbstractPlayer player1, AbstractPlayer player2){
		players[1] = player1;
		players[2] = player2;	
	}
	
	@Override
	public void start(){	
		gameRunning = true;
		System.out.println("game started with players: " + players[1].getName() + " and " + players[2].getName());
		
		System.out.println(boardManager.getBoard().show());
		
		currentPlayer = players[startPlayerIndex];
		currentPlayer.myTurn();
	}
	
	
	public void awaitingClick(){
		System.out.println("awaiting click from " + currentPlayer.getName());
		awaitingClick = true;
	}
	
	@Override
	public boolean handleLocalPlayerClick(Pos pos) {
		if(awaitingClick){		
			boolean temp = currentPlayer.myChoice(pos);	
			if(temp)
				awaitingClick = true;	
			else
				System.out.println("field already taken, choose another one");
			return temp;
		}		
		return false;
	}
	
	public boolean placeMark(Pos pos){
		boolean temp = boardManager.mark(currentPlayerIndex, pos);		
		if(temp)
			markComplete();		
		return temp;
	}

	
	private void markComplete(){
		System.out.println("board after mark from " + currentPlayer.getName());
		System.out.println(boardManager.getBoard().show());
		int winCheck = boardManager.winCheck();		
		
		if(winCheck == 0){	
			if(!boardManager.getBoard().boardFull()){
				markCount ++;
				
				if(markCount == marksPerTurn){
					markCount = 0;
					if(currentPlayerIndex == 1)
						currentPlayerIndex = 2;
					else
						currentPlayerIndex = 1;
					
					currentPlayer = players[currentPlayerIndex];
				}
				currentPlayer.myTurn();
			}
			else
				gameRunning = false; //board full without winner	
		}
		else{			
			winner = currentPlayer;
			winner.incrementGameWon();
			winner.setWinningFields(boardManager.getTubeFieldpositions());
			gameRunning = false;
		}
		
	}
	
	
	public boolean getGameRunning(){
		return gameRunning;
	}
	
	@Override
	public void reset() {
		currentPlayerIndex = startPlayerIndex;
		boardManager.getBoard().reset();
	}

	public String showBoard() {
		return boardManager.getBoard().show();
	}

	@Override
	public int getBoardDimension() {
		return boardDimension;
	}

	@Override
	public int getWinLength() {
		return winLength;
	}

	@Override
	public int getMarksPerTurn() {
		return marksPerTurn;
	}

	@Override
	public int getStartPlayerIndex() {
		return startPlayerIndex;
	}

	@Override
	public void pause() {}


	@Override
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}


	@Override
	public AbstractPlayer getWinner() {
		return winner;
	}

	@Override
	public String getGameRecording() {
		return boardManager.getBoard().getHistory();
	}
}
