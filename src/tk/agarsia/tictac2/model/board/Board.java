package tk.agarsia.tictac2.model.board;

public class Board {

	private int boardDimension;
	private Field[][] fields;
	private String history;
	
	public Board(int boardDimension){
		this.boardDimension = boardDimension;
		fields = new Field[boardDimension][boardDimension];	
		history = boardDimension + "x" + boardDimension + " field:\n";
	}
	
	public Field getField(int row, int column){
		return fields[row][column];
	}
	
	public int getFieldValue(int row, int column){
		return fields[row][column].getValue();
	}
	
	public boolean setField(int playerIndex, int row, int column){
		if(fields[row][column].isFree()){
			fields[row][column].setValue(playerIndex);		
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
		for(int i = 0; i < boardDimension; i++)	
			for(int j = 0; j < boardDimension; j++)
				if(fields[i][j].getValue() == 0)
					temp ++;
		return temp;		
	}
	
	public boolean boardFull(){
		return getEmptyFieldCount() == 0;
	}
	
	public Field getChosenFreeField(int choice) {
		int count = 0;
		for(int i = 0; i < boardDimension; i++)	
			for(int j = 0; j < boardDimension; j++){
				if(fields[i][j].getValue() == 0){
					count ++;
					if(count == choice)
						return fields[i][j];
				}
			}	
		return null;	
	}
	
	public String show(){
		String buffer = "";
		
		String columnNumbers = "    ";
		String dashLine = "    ";
		for(int i = 0; i < boardDimension; i++){
			columnNumbers += i + " ";
			dashLine += "__";
		} dashLine = dashLine.substring(0, dashLine.length() - 1);
		
		buffer += columnNumbers + "\n" + dashLine + "\n";
		
		for(int i = 0; i < boardDimension; i++){
			buffer += i + "  |";
			for(int j = 0; j < boardDimension; j++)
				buffer += fields[i][j].getValue() + " ";
			buffer += "\n";
		}
		return buffer;
	}
	
	public void reset(){
		for(int i = 0; i < boardDimension; i++)
			for(int j = 0; j < boardDimension; j++)
				fields[i][j] = new Field(i, j);
	}


	
	
}
