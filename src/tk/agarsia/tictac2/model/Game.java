package tk.agarsia.tictac2.model;

import tk.agarsia.tictac2.model.board.Board;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class Game extends Thread implements GameInterface {
	
	private int interval; // in ms
	private int boardDim; // = 3;
	private int winLength; // = 3;
	private int marksPerTurn; // = 1;	
	private int markCount = 0;
	private int startPlayerIndex;
	private int currentPlayerIndex; // = 1;	
	private AbstractPlayer currentPlayer;
	
	private AbstractPlayer[] players;
	
	private Board board;
	
	private boolean gameRunning = false;
	private boolean awaitingClick = false;
	private AbstractPlayer winner = null;
		
	public Game(){
		players = new AbstractPlayer[3];
		players[0] = null;
	}
	
	public AbstractPlayer[] getPlayers() {
		return players;
	}
	
	public Board getBoard(){
		return board;
	}
	
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
	
	@Override
	public void initModel(int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex) {
		this.interval = interval;
		this.boardDim = boardDim;
		this.winLength = winLength;
		this.marksPerTurn = marksPerTurn;
		this.startPlayerIndex = startPlayerIndex;
		currentPlayerIndex = startPlayerIndex;
		board = new Board(boardDim, winLength);
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
		
		System.out.println(board.show());
		
		currentPlayer = players[startPlayerIndex];
		currentPlayer.myTurn();
		super.start();
	}
	
	
	public void awaitingClick(){
		System.out.println("awaiting click from " + currentPlayer.getName());
		awaitingClick = true;
	}
	
	@Override
	public boolean handleLocalPlayerClick(int row, int column) {
		if(awaitingClick){		
			boolean temp = currentPlayer.myChoice(row, column);	
			if(temp)
				awaitingClick = true;	
			else
				System.out.println("field already taken, choose another one");
			return temp;
		}		
		return false;
	}
	
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

	
	private void markComplete(){
		System.out.println("board after mark from " + currentPlayer.getName());
		System.out.println(board.show());
		
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
	
	
	public boolean getGameRunning(){
		return gameRunning;
	}
	
	@Override
	public void reset() {
		currentPlayerIndex = startPlayerIndex;
		board.reset();
	}

	public String showBoard() {
		return board.show();
	}

	@Override
	public int getBoardDim() {
		return boardDim;
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
		return board.getHistory();
	}
}
