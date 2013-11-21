package tk.agarsia.tictac2.model.board;

public abstract class AbstractPos {
	
	protected int row;
	protected int column;
	
	public AbstractPos(int row, int column){
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
