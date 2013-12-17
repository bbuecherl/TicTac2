/*package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.ArrayList;

public class Layout {

	private TreeBuilder tree;
	
	public Layout(TreeBuilder treeBuilder){
		this.tree = treeBuilder;
		
		ArrayList<Node> level = new ArrayList<Node>();
		int maxLevelsize = 0;
		int maxLevel = 0;
		
		int y = tree.getMaxDepth() * 500;
		
		for(int i = tree.getMaxDepth(); i >= 0; i--){			
			level.clear();
			level = tree.getNodesAtLevel(i);	
			if(level.size() > maxLevelsize){
				maxLevelsize = level.size();
				maxLevel = i;
			}
			for(Node node : level)
				node.setY(y);
			y -= 500;
		}
		System.out.println("maxLevel: " + maxLevel + " with size: " + maxLevelsize);
				
		//biggest level
		int nodeSize = 50;
		int nodeDist = 60;
		
		int spanning = maxLevelsize * nodeSize + (maxLevelsize - 1) * nodeDist;
		int leftBorder = - spanning / 2 + nodeSize / 2;
		

		for(int i = 1; i <= tree.getMaxDepth(); i++){			
			level.clear();
			level = tree.getNodesAtLevel(i);	
		
			if(i < 3){
				int dist = spanning / level.size();
				int x = leftBorder;		
				for(Node node : level){
					node.setX(x);
					x += dist;
				}
			}
			else{
				for(Node node : level){
					int x = 0;
					for(Node parent : node.getParents())
						x += parent.getX();
					x /= node.getParents().size();					
					node.setX(x);
				}			
			}
		}
	}
}
*/