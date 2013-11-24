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
        
        public ArrayList<Field> getMembers(){
                return members;
        }      
        
        public String showPos(){
                String temp = "";        
                for(Field field : members)
                        temp += "[" + field.getRow() + ", " + field.getColumn() + "]  ";
                return temp;
        }      
}