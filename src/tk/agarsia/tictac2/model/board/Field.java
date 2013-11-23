package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;


public class Field extends Pos{

	private static Board board;
	private static int boardDim;
	private static int winLength;
	private static ArrayList<Integer> snakePath = new ArrayList<Integer>();
	
	private int value = 0;
	private int pathPos;
		
	private Field north = null;
	private Field northeast = null;
	private Field east = null;
	private Field southeast = null;
	private Field south = null;
	private Field southwest = null;
	private Field west = null;
	private Field northwest = null;		
	
	public Field getNeighbour(int index){
        switch(index) {
	        case 1: return east;
	        case 2: return west;
	        case 3: return south;
	        case 4: return north;
	        case 5: return southeast;
	        case 6: return southwest;
	        case 7: return northwest;
	        case 8: return northeast;     
            default: return null;
        }		
	}
	
	
	public void setNeighbour(int index, Field field){    			
		
		switch(index) {
			case 1: east = field; 
					if(field.getNeighbour(2) == null)
							field.setNeighbour(2, this);
					break;
			case 2: west = field;
					if(field.getNeighbour(1) == null)
						field.setNeighbour(1, this);
					break;
			case 3: south = field; 
					if(field.getNeighbour(4) == null)
						field.setNeighbour(4, this);
					break;
			case 4: north = field; 
					if(field.getNeighbour(3) == null)
						field.setNeighbour(3, this);
					break;					
			case 5: southeast = field; 
					if(field.getNeighbour(7) == null)
							field.setNeighbour(7, this);
					break;
			case 6: southwest = field;
					if(field.getNeighbour(8) == null)
						field.setNeighbour(8, this);
					break;
			case 7: northwest = field; 
					if(field.getNeighbour(5) == null)
						field.setNeighbour(5, this);
					break;
			case 8: northeast = field; 
					if(field.getNeighbour(6) == null)
						field.setNeighbour(6, this);
					break;			
		}	
	}
	
	public int getPathPos(){
		return pathPos;
	}
	
	public Field(Board board){
		super(0, 0);
		Field.board = board;
		Field.boardDim = board.getBoardDim();
		Field.winLength = board.getWinLength();
		
		pathPos = 0;		
		int direction = 2;
				
		for(int i = 0; i < boardDim; i++){		
			if(direction == 2) direction = 1; else direction = 2; //toggle direction btwn east & west
			for(int j = 0; j < boardDim - 1; j++)
				snakePath.add(direction);
			snakePath.add(3);
		} snakePath.remove(snakePath.size() - 1);			
			
		
		for(int i : snakePath)
			System.out.println(i);

		
		board.addField(this);
		next();
	}

	
	private void next(){			
		Field field = new Field(this);
		setNeighbour(snakePath.get(pathPos), field);			
		field.moveOn();
	}
	
	public Field(Field field){
		super(0, 0);
		pathPos = field.getPathPos() + 1;
		board.addField(this);
		
		if(row > 0){
			
			
			
/*			if(column == boardDim - 1)
				setNeighbour(7, north.getNeighbour(2));
			else if (column == 0)
				setNeighbour()*/
		}
		
	}
	
	public void moveOn(){
		if(pathPos < snakePath.size())
			next();
		else{
			
		}
	}
	
/*	private static int calcRow(Field prevField){		
		return (prevField.getPathPos() + 1) % boardDim;	
	}
	
	private static int calcColumn(Field prevField){
		int prevRow = prevField.getRow(); 
		int prevCol = prevField.getColumn();
		
		int nowRow = calcRow(prevField);
		if(nowRow > prevRow && (prevCol == 0 || prevCol == boardDim - 1)){
			return prevCol;
		}

		boolean evenRow = (nowRow + 1) % 2 == 0;
		if(evenRow)
			return prevCol - 1;
		else
			return prevCol + 1;
	}*/
	
	
	
/*	private void handshakes() {
		north 		= board.getField(row - 1, 	column		);
		northeast 	= board.getField(row - 1, 	column + 1	);
		east 		= board.getField(row	, 	column + 1	);
		southeast 	= board.getField(row + 1, 	column + 1	);
		south 		= board.getField(row + 1, 	column		);
		southwest 	= board.getField(row + 1, 	column - 1	);
		west 		= board.getField(row	,	column - 1	);
		northwest 	= board.getField(row - 1, 	column - 1	);		
	}*/
	
	
	public boolean isFree(){
		return value == 0;
	}
	
	public int countFree(){
		int sum = 0;
		
		Field field = this;
		for(int i = 0; i < boardDim * boardDim; i++){			
		}
		
		
		return sum;
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
		horizontal.addMembers(wander(4));
		horizontal.addMembers(wander(3));
		board.addIsland(horizontal);
		
		Island vertical = new Island(this);
		vertical.addMembers(wander(1));
		vertical.addMembers(wander(2));
		board.addIsland(vertical);
		
		Island backslash = new Island(this);
		backslash.addMembers(wander(7));
		backslash.addMembers(wander(5));
		board.addIsland(backslash);
		
		Island slash = new Island(this);
		slash.addMembers(wander(6));
		slash.addMembers(wander(8));
		board.addIsland(slash);							
	}
	
	private ArrayList<Field> wander(int index){		
		ArrayList<Field> sum = new ArrayList<Field>();
		
		boolean endReached = false;
		Field next = getNeighbour(index);
		
		while(!endReached){							
			if(next != null)
				if(next.getValue() == value){
					sum.add(next);
					next = next.getNeighbour(index);
				}
				else
					endReached = true;
			else 
				endReached = true;
		}	
		return sum;
	}

	public String show() {
		return "pathPos: " + pathPos + " val: " + value + " row: " + row + " column: " + column 
				+ " E: " + (east == null ? "-" : east.getPathPos())
				+ " W: " + (west == null ? "-" : west.getPathPos())
				+ " S: " + (south == null ? "-" : south.getPathPos())
				+ " N: " + (north == null ? "-" : north.getPathPos())
				+ " NE: " + (northeast == null ? "-" : northeast.getPathPos())
				+ " SE: " + (southeast == null ? "-" : southeast.getPathPos())
				+ " SW: " + (southwest == null ? "-" : southwest.getPathPos())
				+ " NW: " + (northwest == null ? "-" : northwest.getPathPos())
				;
	}
}


//field.getColumn() == boardDim - 1 ? field.getRow() + 1 : field.getRow(), //row
//field.getColumn() == boardDim - 1 ? 0 : field.getColumn() + 1			 //column 
