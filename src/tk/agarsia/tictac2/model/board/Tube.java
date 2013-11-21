package tk.agarsia.tictac2.model.board;


/*
 * used to feed in elements from wandering along horizontals, verticals or diagonals
 * as soon as all are equal the index of the winning player is returned
 */
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
	
		
/*	public int getLength(){
		return tube.length;
	}
	
	public String show(){
		String buffer = "";
		for(int i = 0; i < tube.length; i++)
			buffer += tube[i] + " ";
		return buffer;
	}*/
	
	
/*	public static void main(String[] args) {	
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
	}*/	
}
