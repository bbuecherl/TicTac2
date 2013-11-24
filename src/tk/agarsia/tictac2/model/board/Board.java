package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;
import java.util.Random;

public class Board {

	protected int boardDim;
	protected int winLength;
	protected Field[][] fields2D;
	protected String history;
	protected boolean winState = false;
	//protected ArrayList<Island> islands = new ArrayList<Island>();
	protected Island winningIsland;
	
	
	public Board(int boardDim, int winLength){
		this.boardDim = boardDim;
		this.winLength = winLength;

		reset();
	}
	
	public Board(Board board){ // copy-constructor
		this.boardDim = board.boardDim;
		this.winLength = board.winLength;

		this.history = board.history;
		
		fields2D = new Field[boardDim][boardDim];
		new Field(this);
		
		for(int i = 0; i < boardDim; i++)	
			for(int j = 0; j < boardDim; j++)
				fields2D[i][j].setValue(board.getField(i, j).getValue());	
	}

	
	public void reset(){		
		history = boardDim + "x" + boardDim + " field:\n";
		fields2D = new Field[boardDim][boardDim];	
		
		new Field(this); //pure awesomeness... the whole board is building itself when just the first Field is instantiated :)
				
		for(int i = 0; i < boardDim; i++)	
			for(int j = 0; j < boardDim; j++)
				System.out.println(fields2D[i][j].show()); // proof
	}
	
	public void addField(Field field){
		fields2D[field.getRow()][field.getColumn()] = field;
		//System.out.println("added " + field.getPathPos() + " at " + field.getRow() + ", " + field.getColumn());
	}
	
/*	public void addIsland(Island island){
		//islands.add(island);	
		
		if(island.getSize() >= winLength){
			winState = true;
			winningIsland = island;
		}
	}*/
	
	public void islandWon(Island island){
		winState = true;
		winningIsland = island;
	}
	
	public String getWinnersPositions() {		
		return winningIsland.showPos();
	}
	
	public boolean getWinState(){
		return winState;
	}
	
	public Island getWinningIsland(){
		return winningIsland;
	}
	
	public int getBoardDim(){
		return boardDim;
	}
	
	public int getWinLength(){
		return winLength;
	}
	
	
	public Field getField(int row, int column){
		if(row < 0 || row >= boardDim || column < 0 || column >= boardDim)
			return null;		
		return fields2D[row][column];
	}
	
	public boolean setField(int playerIndex, int row, int column){
		if(fields2D[row][column].isFree()){
			fields2D[row][column].setValue(playerIndex);		
			history += playerIndex + " [" + row + ", " + column + "]\n";			
			return true;
		}
		else 
			return false;		
	}
	
	public String getHistory(){
		return history;
	}
	
	public ArrayList<Field> getFreeFields(){
/*		ArrayList<Field> collect = new ArrayList<Field>();		
		for(int i = 0; i < boardDim; i++)	
			for(int j = 0; j < boardDim; j++)
				if(fields2D[i][j].getValue() == 0)
					collect.add(fields2D[i][j]);
		return collect;*/
		return fields2D[0][0].getFreeFields();
	}
	
	
	public int getFreeFieldCount(){
		return getFreeFields().size() - 1;
	}
	
	public boolean full(){
		return getFreeFieldCount() == 0;
	}
	
	public void placeRandomly(int currentPlayerIndex) {
		int choice = 1 + (int) (new Random().nextDouble() * getFreeFieldCount());
		getFreeFields().get(choice).setValue(currentPlayerIndex);
	}
	
	public void depositAtSpecificFreeField(int currentPlayerIndex, int target){		
		getFreeFields().get(target - 1).setValue(currentPlayerIndex);
	}
	
	public String show(){
		String buffer = "";
		
		String columnNumbers = "    ";
		String dashLine = "    ";
		for(int i = 0; i < boardDim; i++){
			columnNumbers += i + " ";
			dashLine += "__";
		} dashLine = dashLine.substring(0, dashLine.length() - 1);
		
		buffer += columnNumbers + "\n" + dashLine + "\n";
		
		for(int i = 0; i < boardDim; i++){
			buffer += i + "  |";
			for(int j = 0; j < boardDim; j++)
				buffer += fields2D[i][j].getValue() + " ";
			buffer += "\n";
		}
		return buffer;
	}

}
