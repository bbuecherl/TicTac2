package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;
import java.util.Random;

public class Board {

	protected int boardDim;
	protected int winLength;
	protected ArrayList<int[]> history;
	protected int winner = 0;

	private BoardParserV2 boardParser = new BoardParserV2();
	private int[] boardArr;
	
	/**
	 * Function to set init game board.
	 * 
	 * @param boardDim
	 * @param winLength
	 * 
	 */
	public Board(int boardDim, int winLength){
		this.boardDim = boardDim;
		this.winLength = winLength;
		
		boardArr = new int[boardDim * boardDim];
		reset();
	}
	
	/**
	 * Copy Constructor of Board.
	 */
	@SuppressWarnings("unchecked")
	public Board(Board board){ // copy-constructor
		this.boardDim = board.boardDim;
		this.winLength = board.winLength;

        this.history = (ArrayList<int[]>) board.history.clone();

        for(int i = 0; i < boardDim * boardDim; i++)
        	boardArr[i] = board.getBoardArr()[i];
	}

	/**
	 * Function to reset game board.
	 */
	public void reset(){		
        history = new ArrayList<int[]>();
		
        for(int i = 0; i < boardDim * boardDim; i++)
        	boardArr[i] = 0;
	}

	public int[] getBoardArr(){
		return boardArr;
	}
	
	public int getWinner(){
		return winner;
	}
	
	public int getBoardDim(){
		return boardDim;
	}
	
	public int getWinLength(){
		return winLength;
	}
	
	/**
	 * Gives access to a field based on its coordinates.
	 * @param row: of requested field
	 * @param column: of requested field
	 * @return the value of the requested field
	 */
	public int getField(int row, int column){	
		return boardArr[row * boardDim + column];
	}
	
	/**
	 * Sets a value one a field, based on its coordinates.
	 * 
	 * @param playerIndex: index of the player who is placing its value on the field
	 * @param row: of field where to place the mark
	 * @param column: of field where to place the mark
	 * @return true: mark was placed, false: field is already taken
	 */
	public boolean setField(int playerIndex, int row, int column){
		if(getField(row, column) == 0){
			setField(row * boardDim + column, playerIndex);	
			return true;
		}
		else
			return false;	
	}
	
	private void setField(int boardArrIndex, int playerIndex){
		boardArr[boardArrIndex] = playerIndex;
		history.add(new int[] {playerIndex, boardArrIndex / boardDim, boardArrIndex % boardDim});			

		boardParser.testBoardForWinner(boardArr, boardDim, winLength);
		winner = boardParser.getWinner();
	}
	
	public ArrayList<int[]> getHistory(){
		return history;
	}
	
	/**
	 * @return number of free fields on the board
	 */
	public int getFreeFieldCount(){		
		int count = 0;
		for(int i = 0; i < boardDim * boardDim; i++)
			if(boardArr[i] == 0)
				count ++;		
		return count;
	}
	
	/**
	 * @return true: board full, false: board not full
	 */
	public boolean full(){
		return getFreeFieldCount() == 0;
	}
	
	/**
	 * Places the mark of the currentPlayer randomly on one of the free fields.
	 * Random bot is using this method. It could also have been done there, but it seems smarter to leave the 
	 * method embedded in here instead of pulling all data out and then pushing the choice back in
	 * @param currentPlayerIndex
	 */
	public void placeRandomly(int currentPlayerIndex) {
		int choice = (int) (new Random().nextDouble() * getFreeFieldCount());	
		int count = 0;
		int i = 0;
		while(count != choice){
			if(boardArr[i] == 0)
				count ++;	
			i++;
		}
		setField(i, currentPlayerIndex);	
	}
	
	
	/**
	 * Gives access to a string-representation of the board.
	 * 
	 * @param numbering: true: numbering is on, false: numbering is off
	 * @return a string-representation of the board
	 */
	public String show(boolean numbering){
		String buffer = "";
		
		if(numbering){		
			String columnNumbers = "    ";
			String dashLine = "    ";
			for(int i = 0; i < boardDim; i++){
				columnNumbers += i + " ";
				dashLine += "__";
			} dashLine = dashLine.substring(0, dashLine.length() - 1);
			
			buffer += columnNumbers + "\n" + dashLine + "\n";	
		}
		for(int i = 0; i < boardDim; i++){
			if(numbering) 
				buffer += i + "  |";
			for(int j = 0; j < boardDim; j++)
				buffer += getField(i, j) + " ";
			buffer = buffer.substring(0, buffer.length() - 1) + "\n";
		}
		return buffer;
	}

}
