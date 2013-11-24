package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;


public class Field {
	
	public enum dir{
		EAST, WEST, SOUTH, NORTH, SOUTHEAST, SOUTHWEST, NORTHWEST, NORTHEAST,
	}	
	
	private Board board;
	private int boardDim;
	private int winLength;
	private ArrayList<dir> snakePath;
	
	private int row;
	private int column;
	private int value = 0;
	private int pathPos;
	
	protected Field east = null;
	protected Field west = null;
	protected Field south = null;
	protected Field north = null;
	protected Field southeast = null;
	protected Field southwest = null;
	protected Field northwest = null;
	protected Field northeast = null;
	
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	protected Field getNeighbour(dir index){
        switch(index) {
	        case EAST: return east;
	        case WEST: return west;
	        case SOUTH: return south;
	        case NORTH: return north;
	        case SOUTHEAST: return southeast;
	        case SOUTHWEST: return southwest;
	        case NORTHWEST: return northwest;
	        case NORTHEAST: return northeast;     
            default: return null;
        }		
	}
	
	protected void setNeighbour(dir index, Field field){    					
		if(field != null){	
			switch(index) {
				case EAST: east = field; 
						if(field.getNeighbour(dir.WEST) == null)
								field.setNeighbour(dir.WEST, this);
						break;
				case WEST: west = field;
						if(field.getNeighbour(dir.EAST) == null)
							field.setNeighbour(dir.EAST, this);
						break;
				case SOUTH: south = field; 
						if(field.getNeighbour(dir.NORTH) == null)
							field.setNeighbour(dir.NORTH, this);
						break;
				case NORTH: north = field; 
						if(field.getNeighbour(dir.SOUTH) == null)
							field.setNeighbour(dir.SOUTH, this);
						break;					
				case SOUTHEAST: southeast = field; 
						if(field.getNeighbour(dir.NORTHWEST) == null)
								field.setNeighbour(dir.NORTHWEST, this);
						break;
				case SOUTHWEST: southwest = field;
						if(field.getNeighbour(dir.NORTHEAST) == null)
							field.setNeighbour(dir.NORTHEAST, this);
						break;
				case NORTHWEST: northwest = field; 
						if(field.getNeighbour(dir.SOUTHEAST) == null)
							field.setNeighbour(dir.SOUTHEAST, this);
						break;
				case NORTHEAST: northeast = field; 
						if(field.getNeighbour(dir.SOUTHWEST) == null)
							field.setNeighbour(dir.SOUTHWEST, this);
						break;			
			}	
		}
	}
	
	protected int getPathPos(){
		return pathPos;
	}
	
	public Field(Board board){
		this.board = board;
		boardDim = board.getBoardDim();
		winLength = board.getWinLength();
		
		pathPos = 0;		
		dir direction = dir.WEST;
				
		//"snake" iterator-pfad erstellen
		snakePath = new ArrayList<dir>();
		for(int i = 0; i < boardDim; i++){		
			if(direction == dir.WEST) direction = dir.EAST; else direction = dir.WEST; //toggle direction btwn east & west
			for(int j = 0; j < boardDim - 1; j++)
				snakePath.add(direction);
			snakePath.add(dir.SOUTH);
		} snakePath.remove(snakePath.size() - 1);			
				
//		for(dir i : snakePath)
//			System.out.println(i);
		
		board.addField(this);
		initNextField();
	}

	
	private void initNextField(){			
		Field field = new Field(this, board, snakePath);
		setNeighbour(snakePath.get(pathPos), field);
		
		int newCol = column;
		if(snakePath.get(pathPos) == dir.EAST)
			newCol = column + 1;
		else 
			if(snakePath.get(pathPos) == dir.WEST)
				newCol = column - 1;	
		field.setPos((pathPos + 1) / boardDim, newCol);			
		field.finishFieldAndInitNext();
	}
	
	protected Field(Field field, Board board, ArrayList<dir> snakePath){
		this.board = board;
		this.snakePath = snakePath;
		boardDim = board.getBoardDim();
		winLength = board.getWinLength();
		pathPos = field.getPathPos() + 1;				
	}
	
	protected void setPos(int newRow, int newCol){
		row = newRow;
		column = newCol;
	}

	protected void finishFieldAndInitNext(){		
		if(row > 0){
			if((row + 1) % 2 == 0){ //even rows				
				if(column == boardDim - 1){ //RIGHT border in EVEN row					
					setNeighbour(dir.NORTHWEST, north.west);
				}
				else 
					if(column == 0){ //LEFT border in EVEN row
						setNeighbour(dir.NORTHEAST, east.north);
						setNeighbour(dir.NORTH, east.north.west);
					}
					else{ //everything in between the borders of EVEN rows
						
						setNeighbour(dir.NORTHEAST, east.north);
						setNeighbour(dir.NORTH, northeast.west);
						setNeighbour(dir.NORTHWEST, north.west);					
					}				
			}
			else{ //odd rows
			
				if(column == 0){ //LEFT border in ODD row					
					setNeighbour(dir.NORTHEAST, north.east);
				}
				else 
					if(column == boardDim - 1){ //RIGHT border in ODD row
						setNeighbour(dir.NORTHWEST, west.north);
						setNeighbour(dir.NORTH, northwest.east);
					}
					else{ //everything in between the borders of ODD rows						
						setNeighbour(dir.NORTHWEST, west.north);
						setNeighbour(dir.NORTH, northwest.east);
						setNeighbour(dir.NORTHEAST, north.east);
					}
			}	
		}
		
		board.addField(this);

		if(pathPos < snakePath.size())
			initNextField();
	}
		
	
	public ArrayList<Field> getFreeFields(){
		ArrayList<Field> collect = new ArrayList<Field>();	
		Field field = this;
		collect.add(field);		
		for(int i = 0; i < boardDim * boardDim - 1; i++){
			field = field.getNeighbour(snakePath.get(field.pathPos)); //walk along snakePath
			if(field.getValue() == 0)
				collect.add(field);
		}	
		return collect;
	}	
	
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
		horizontal.addMembers(wander(dir.NORTH));
		horizontal.addMembers(wander(dir.SOUTH));		
		if(horizontal.getSize() >= winLength)
			board.islandWon(horizontal);
		
		Island vertical = new Island(this);
		vertical.addMembers(wander(dir.EAST));
		vertical.addMembers(wander(dir.WEST));
		if(vertical.getSize() >= winLength)
			board.islandWon(vertical);
		
		Island backslash = new Island(this);
		backslash.addMembers(wander(dir.NORTHWEST));
		backslash.addMembers(wander(dir.SOUTHEAST));
		if(backslash.getSize() >= winLength)
			board.islandWon(backslash);
		
		Island slash = new Island(this);
		slash.addMembers(wander(dir.SOUTHWEST));
		slash.addMembers(wander(dir.NORTHEAST));
		if(slash.getSize() >= winLength)
			board.islandWon(slash);						
	}
	
	private ArrayList<Field> wander(dir index){		
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