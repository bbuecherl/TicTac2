package tk.agarsia.tictac2.model.board;

public class Pos {
	
	protected int row;
	protected int column;
	
	public Pos(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}

}
