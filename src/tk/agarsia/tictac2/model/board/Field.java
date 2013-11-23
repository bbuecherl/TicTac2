package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;


public class Field extends Pos{

	private static Board board;
	private static int boardDim;
	private static int winLength;
	
	private int value = 0;
		
	private Field north = null;
	private Field northeast = null;
	private Field east = null;
	private Field southeast = null;
	private Field south = null;
	private Field southwest = null;
	private Field west = null;
	private Field northwest = null;
	
	
	
	public Field getDirection(int index){
        switch (index) {
            case 1: return north;
            case 2: return northeast;
            case 3: return east;
            case 4: return southeast;
            case 5: return south;
            case 6: return southwest;
            case 7: return west;
            case 8: return northwest;
            default: return null;
        }		
	}
	
	public Field(Board board){
		super(0, 0);
		Field.board = board;
		Field.boardDim = board.getBoardDim();
		Field.winLength = board.getWinLength();
		board.addField(this);
	}

	public Field(Field field){		
		super(
			field.getColumn() == boardDim - 1 ? field.getRow() + 1 : field.getRow(), //row
			field.getColumn() == boardDim - 1 ? 0 : field.getColumn() + 1			 //column
		);
		board.addField(this);
	}
	
	public void handshakes() {
		north = board.getField(row - 1, column);
		northeast = board.getField(row - 1, column + 1);
		east = board.getField(row, column + 1);
		southeast = board.getField(row + 1, column + 1);
		south = board.getField(row + 1, column);
		southwest = board.getField(row + 1, column - 1);
		west = board.getField(row, column - 1);
		northwest = board.getField(row - 1, column - 1);		
	}
	
	
	public Field(int row, int column){
		super(row, column);
	}
	
/*	public boolean isSameField(Field other){
		return row == other.getRow() && column == other.getColumn() && value == other.getValue();
	}*/
	
	public boolean isFree(){
		return value == 0;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){		
		this.value = value;
		ping360();		
	}
	
	
	private void ping360(){			
		Island horizontal = new Island(this);
		horizontal.addMembers(wander(1));
		horizontal.addMembers(wander(5));
		board.addIsland(horizontal);
		
		Island vertical = new Island(this);
		vertical.addMembers(wander(3));
		vertical.addMembers(wander(7));
		board.addIsland(vertical);
		
		Island backslash = new Island(this);
		backslash.addMembers(wander(8));
		backslash.addMembers(wander(4));
		board.addIsland(backslash);
		
		Island slash = new Island(this);
		slash.addMembers(wander(6));
		slash.addMembers(wander(2));
		board.addIsland(slash);						
		
/*		int horizontal = 1 + wander(1) + wander(5);
		int vertical = 1 + wander(3) + wander(7);
		int backslash = 1 + wander(8) + wander(4); // from northwest to southeast: \
		int slash = 1 + wander(6) + wander(2); // from southwest to northeast: /
*/		
		//System.out.println("h: " + horizontal + " v: " + vertical + " b: " + backslash + " s: " + slash);		
		//if(horizontal.getLength() >= winLength || vertical.getLength() >= winLength || backslash.getLength() >= winLength || slash.getLength() >= winLength)
		//	board.winStateReached();		
	}
	
	private ArrayList<Field> wander(int index){		
		ArrayList<Field> sum = new ArrayList<>();
		
		boolean endReached = false;
		Field next = getDirection(index);
		
		while(!endReached){							
			if(next != null)
				if(next.getValue() == value){
					sum.add(next);
					next = next.getDirection(index);
				}
				else
					endReached = true;
			else 
				endReached = true;
		}	
		return sum;
	}

	
	
	
/*	if (boardDim % 2 == 0) { //even
	} 
	else { //odd
	}	*/
	
	
/*	private void expand(){
		boolean northB = true;
		boolean northeastB = true;
		boolean eastB = true;
		boolean southeastB = true;
		boolean southB = true;
		boolean southwestB = true;
		boolean westB = true;
		boolean northwestB = true;
		
		if(row == 0){
			northwestB = false;
			northB = false;
			northeastB = false;
		}
		if(row == boardDim - 1){
			southwestB = false;
			southB = false;
			southeastB = false;
		}
		if(column == 0){
			northwestB = false;
			westB = false;
			southwestB = false;
		}
		if(column == boardDim - 1){
			northeastB = false;
			eastB = false;
			southeastB = false;
		}	
		doExpand(northB, northeastB, eastB, southeastB, southB, southwestB, westB, northwestB);
	}
	
	
	private void doExpand(boolean northB, boolean northeastB, boolean eastB, boolean southeastB, boolean southB, boolean southwestB, boolean westB, boolean northwestB){		
		if(northB && north == null) 
			north = new Field(this, -1, 0);
		if(northeastB && northeast == null)
			northeast = new Field(this);
		if(eastB && east == null) 
			east = new Field(this);
		if(southeastB && southeast == null) 
			southeast = new Field(this);
		if(southB && south == null) 
			south = new Field(this);
		if(southwestB && southwest == null) 
			southwest = new Field(this);
		if(westB && west == null) 
			west = new Field(this);
		if(northwestB && northwest == null) 
			northwest = new Field(this);
	}
	
	public Field(Field field, int dRow, int dColumn){		
		super(field.getRow() + dRow, field.getColumn() + dColumn);
	}*/
	
	
/*	private int north = 0;
	private int northeast = 0;
	private int east = 0;
	private int southeast = 0;
	private int south = 0;
	private int southwest = 0;
	private int west = 0;
	private int northwest = 0;
	
	private void setWalls(){
		if(row == 0){
			northwest = -1;
			north = -1;
			northeast = -1;
		}
		if(row == boardDim - 1){
			southwest = -1;
			south = -1;
			southeast = -1;
		}
		if(column == 0){
			northwest = -1;
			west = -1;
			southwest = -1;
		}
		if(column == boardDim - 1){
			northeast = -1;
			east = -1;
			southeast = -1;
		}
	}*/
	

}
