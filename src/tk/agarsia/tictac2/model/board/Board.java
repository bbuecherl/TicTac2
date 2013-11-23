package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;

public class Board {

	private final int boardDim;
	private final int winLength;
	private Field[][] fields2D;
	private String history;
	private boolean winState = false;
	private ArrayList<Island> islands = new ArrayList<Island>();
	private Island winningIsland;
	
	public Board(int boardDim, int winLength){
		this.boardDim = boardDim;
		this.winLength = winLength;

		reset();
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
	
	public void addIsland(Island island){
		//System.out.println(island.show());
		islands.add(island);	
		
		if(island.getLength() >= winLength){
			winState = true;
			winningIsland = island;
		}
	}
	
	public String getWinnersPositions() {		
		return winningIsland.show();
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
	
	public int getEmptyFieldCount(){
		int temp = 0;
		for(int i = 0; i < boardDim; i++)	
			for(int j = 0; j < boardDim; j++)
				if(fields2D[i][j].getValue() == 0)
					temp ++;
		return temp;	
	}
	
	public boolean full(){
		return getEmptyFieldCount() == 0;
	}
	
	public Field getChosenFreeField(int choice) {
		int count = 0;
		for(int i = 0; i < boardDim; i++)	
			for(int j = 0; j < boardDim; j++){
				if(fields2D[i][j].getValue() == 0){
					count ++;
					if(count == choice)
						return fields2D[i][j];
				}
			}	
		return null;	
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
