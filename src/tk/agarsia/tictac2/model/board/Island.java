package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;

public class Island {

	private ArrayList<Field> members = new ArrayList<>();
		
	public Island(Field init){
		members.add(init);
	}
	
	public void addMember(Field field){
		members.add(field);
	}
	
	public void addMembers(ArrayList<Field> newMembers){
		members.addAll(newMembers);
	}
	
	public int getLength(){
		return members.size();
	}
	
	public ArrayList<Field> getMembers(){
		return members;
	}
	
/*	public boolean isSameIsland(Island other){
		boolean temp = true;
		
		if(members.size() < other.getLength())
			return false;
		else		
			for(int i = 0; i < members.size(); i++){
				Field thisField = members.get(i);
				Field otherField = other.getMembers().get(i);				
				if(!thisField.isSameField(otherField))
					temp = false;				
			}
		return temp;
	}*/
	
	
	public String show(){
		String temp = "";	
		for(Field field : members)
			temp += "[" + field.getRow() + ", " + field.getColumn() + "]  ";
		return temp;
	}
	
}
