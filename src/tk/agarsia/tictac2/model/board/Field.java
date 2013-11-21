package tk.agarsia.tictac2.model.board;


public class Field extends AbstractPos{

	private int value = 0;
	
	public Field(int row, int column){
		super(row, column);
	}
	
	public boolean isFree(){
		return value == 0;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}
