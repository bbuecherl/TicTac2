package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;

public class Board {

	private final int boardDim;
	private final int winLength;
	private ArrayList<Field> fields = new ArrayList<Field>();
	private Field[][] fields2D;
	private String history;
	private boolean winState = false;
	private ArrayList<Island> islands = new ArrayList<Island>();
	private Island winningIsland;
	
	public Board(int boardDim, int winLength){
		this.boardDim = boardDim;
		this.winLength = winLength;
		history = boardDim + "x" + boardDim + " field:\n";

		fields2D = new Field[boardDim][boardDim];			
		reset();
	}
	
	public void reset(){		
		fields.clear();	
		
		Field nextField = new Field(this);	
		for(int i = 1; i < boardDim * boardDim; i++) // have them fill it up themselves using a "smart" constructor
			nextField = new Field(fields.get(fields.size() - 1));		
		
		for(Field field : fields)
			field.handshakes();			
	}
	
	public void addField(Field field){
		fields.add(field);	
		fields2D[field.getRow()][field.getColumn()] = field;
	}
	
	public void addIsland(Island island){
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
	
	public int getFieldValue(int row, int column){
		return fields2D[row][column].getValue();
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




//OLD BOARDMANAGER

/*package tk.agarsia.tictac2.model.board;

public class BoardManager {

	private int boardDim;
	private Board board;
	
	//private Tube tube;
	
	public BoardManager(int boardDim, int winLength){		
		this.boardDim = boardDim;
		
		board = new Board(boardDim, winLength);	
		board.reset();
				
		//tube = new Tube(winLength);
	}

	public Board getBoard(){
		return board;
	}
	
	public boolean mark(int playerIndex, Pos pos) {
		if(board.setField(playerIndex, pos.getRow(), pos.getColumn()))
			return true;
		else
			return false;
	}

	
	
	public String getTubeFieldpositions(){
		return tube.getFieldpositions();
	}
	
	public int winCheck(){	
		
		//CHECK ALL ROWS
		for(int i = 0; i < boardDim; i++){
			tube.flush();
			for(int j = 0; j < boardDim; j++){
				int temp = tube.feed(board.getField(i, j));
				if(temp != 0)
					return temp;
			}
		}
		
		//CHECK ALL COLUMNS
		for(int i = 0; i < boardDim; i++){
			tube.flush();
			for(int j = 0; j < boardDim; j++){
				int temp = tube.feed(board.getField(j, i));
				if(temp != 0)
					return temp;
			}
		}
		
		//CHECK ALL DIAGONALS	
		
		//north-west to east-south direction
		//part 1
		for(int i = boardDim - 1; i >= 0; i--){
			int temp = wanderDiagonal(new WanderField(i, 0).setParams(boardDim, 1, 1));	
			if(temp != 0)
				return temp;
		}
		//part 2
		for(int j = 1; j < boardDim; j++){
			int temp = wanderDiagonal(new WanderField(0, j).setParams(boardDim, 1, 1));	
			if(temp != 0)
				return temp;
		}
		
		//south-west to north-east direction
		//part 1
		for(int i = 0; i < boardDim; i++){
			int temp = wanderDiagonal(new WanderField(i, 0).setParams(boardDim, -1, 1));	
			if(temp != 0)
				return temp;
		}		
		//part 2
		for(int j = 1; j < boardDim; j++){
			int temp = wanderDiagonal(new WanderField(boardDim - 1, j).setParams(boardDim, -1, 1));	
			if(temp != 0)
				return temp;
		}
		
		return 0;
	}
	
	
	private int wanderDiagonal(WanderField startField){			
		tube.flush();
		tube.feed(board.getField(startField.getRow(), startField.getColumn()));
	
		while(startField.nextIsWithin()){
			int temp = tube.feed(board.getField(startField.getRow(), startField.getColumn()));
			if(temp != 0)
				return temp;
		}
		return 0;
	}
	
	
	class WanderField extends Pos{	
		public WanderField(int row, int column) {
			super(row, column);
		}	
		int boardDim;
		int dRow;
		int dColumn;		
		public WanderField setParams(int boardDim, int dRow, int dColumn){
			this.boardDim = boardDim;
			this.dRow = dRow;
			this.dColumn = dColumn;
			return this;
		}		
		public boolean nextIsWithin(){
			if(row + dRow >= boardDim || column + dColumn >= boardDim || row + dRow < 0 || column + dColumn < 0)
				return false;
			else{
				row += dRow;
				column += dColumn;					
				return true;	
			}
		}	
		public int getRow(){
			return row;
		}
		public int getColumn(){
			return column;
		}		
	}

	
}*/

// OLD TUBE
/*package tk.agarsia.tictac2.model.board;



 * used to feed in elements from wandering along horizontals, verticals or diagonals
 * as soon as all are equal the index of the winning player is returned
 
public class Tube {

	
	private Field[] tube;
	
	public Tube(int winLength){
		tube = new Field[winLength];	
		flush();
	}
	
	public int feed(Field field){
		for(int i = tube.length - 1; i > 0; i--)
			tube[i] = tube[i - 1];
		tube[0] = field;
		
		if(allEqual())
			return tube[0].getValue();
		else
			return 0;
	}
	
	public boolean allEqual(){
		boolean temp = true;
		for(int i = 0; i < tube.length - 1; i++){
			if(tube[i].getValue() != tube[i + 1].getValue())
				temp = false;
		}
		return temp;
	}
	
	public void flush(){
		for(int i = 0; i < tube.length; i++){
			Field nullField = new Field(-1, -1);
			nullField.setValue(-1);
			tube[i] = nullField;
		}
	}	

	public String getFieldpositions(){
		String temp = "";	
		for(int i = 0; i < tube.length; i++)
			temp += "[" + tube[i].getRow() + "," + tube[i].getColumn() + "] ";	
		return temp;
	}
	
		
	public int getLength(){
		return tube.length;
	}
	
	public String show(){
		String buffer = "";
		for(int i = 0; i < tube.length; i++)
			buffer += tube[i] + " ";
		return buffer;
	}
	
	
	public static void main(String[] args) {	
		TestTube t = new TestTube(4);
		System.out.println(" " + t.getLength());
		System.out.println(t.show());
		
		System.out.println(t.feed(2));
		System.out.println(t.show());
		
		System.out.println(t.feed(3));
		System.out.println(t.show());
		
		System.out.println(t.feed(3));
		System.out.println(t.show());
		
		System.out.println(t.feed(3));
		System.out.println(t.show());
		
		System.out.println(t.feed(3));
		System.out.println(t.show());
	}	
}
*/
