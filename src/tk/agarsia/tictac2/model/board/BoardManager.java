package tk.agarsia.tictac2.model.board;


public class BoardManager {

	private int boardDimension;
	private Board board;
	
	private Tube tube;
	
	public BoardManager(int boardDimension, int winLength){		
		this.boardDimension = boardDimension;
		
		board = new Board(boardDimension);	
		board.reset();
				
		tube = new Tube(winLength);
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
		for(int i = 0; i < boardDimension; i++){
			tube.flush();
			for(int j = 0; j < boardDimension; j++){
				int temp = tube.feed(board.getField(i, j));
				if(temp != 0)
					return temp;
			}
		}
		
		//CHECK ALL COLUMNS
		for(int i = 0; i < boardDimension; i++){
			tube.flush();
			for(int j = 0; j < boardDimension; j++){
				int temp = tube.feed(board.getField(j, i));
				if(temp != 0)
					return temp;
			}
		}
		
		//CHECK ALL DIAGONALS	
		
		//north-west to east-south direction
		//part 1
		for(int i = boardDimension - 1; i >= 0; i--){
			int temp = wanderDiagonal(new WanderField(i, 0).setParams(boardDimension, 1, 1));	
			if(temp != 0)
				return temp;
		}
		//part 2
		for(int j = 1; j < boardDimension; j++){
			int temp = wanderDiagonal(new WanderField(0, j).setParams(boardDimension, 1, 1));	
			if(temp != 0)
				return temp;
		}
		
		//south-west to north-east direction
		//part 1
		for(int i = 0; i < boardDimension; i++){
			int temp = wanderDiagonal(new WanderField(i, 0).setParams(boardDimension, -1, 1));	
			if(temp != 0)
				return temp;
		}		
		//part 2
		for(int j = 1; j < boardDimension; j++){
			int temp = wanderDiagonal(new WanderField(boardDimension - 1, j).setParams(boardDimension, -1, 1));	
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
	
	
	class WanderField extends AbstractPos{	
		public WanderField(int row, int column) {
			super(row, column);
		}	
		int boardDimension;
		int dRow;
		int dColumn;		
		public WanderField setParams(int boardDimension, int dRow, int dColumn){
			this.boardDimension = boardDimension;
			this.dRow = dRow;
			this.dColumn = dColumn;
			return this;
		}		
		public boolean nextIsWithin(){
			if(row + dRow >= boardDimension || column + dColumn >= boardDimension || row + dRow < 0 || column + dColumn < 0)
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

	
	
}
