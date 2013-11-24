package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.view.BoardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BoardTouchListener implements OnTouchListener {

	private Game game;
	private int len;
	private int width;
	private BoardView view;
	
	public BoardTouchListener(BoardView view) {
		game = ApplicationControl.getGame();
		this.view = view;
		len = 0;
		width = 0;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//we only need touch downs!
		if(event.getAction()!=MotionEvent.ACTION_DOWN)
			return false;
		
		int posX = (int) event.getX()/(width+5);
		int posY = (int) event.getY()/(width+5);
		boolean bo = false;
		
		if(posX<len&&posX>=0&&posY>=0&&posY<len) {
			bo = true;
		} else { //not on board touch
			return false;
		}
		
		//debugging *_*
		Log.i("touch", "("+event.getX()+","+event.getY()+") "+bo+":("+posX+","+posY+")");
		
		Log.i("handled", ""+game.handleLocalPlayerClick(posX,posY));
		
		view.postInvalidate();
		return true;
	}

	public void updateDim(int len, int width) {
		this.len = len;
		this.width = width;
	}
}