package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;

public class Island {

	private ArrayList<Field> members = new ArrayList<Field>();
                
    public Island(Field init){
    	members.add(init);
    }
        
    public void addMembers(ArrayList<Field> newMembers){
    		members.addAll(newMembers);
    }
        
    public int getSize(){
    		return members.size();
    }
        
    //not needed yet
/*    public ArrayList<Field> getMembers(){
    		return members;
    }    */  
    
    /**
     * @return a string representation of the fields of the winning island
     */
    public String showPos(){
    		String temp = "";        
    		for(Field field : members)
    			temp += "[" + field.getRow() + ", " + field.getColumn() + "]  ";
    		return temp;
    }      
}