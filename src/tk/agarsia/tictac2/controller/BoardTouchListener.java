package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.view.BoardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BoardTouchListener implements OnTouchListener {

	private int len;
	private int width;
	private int offset;
	private BoardView view;
	
	public BoardTouchListener(BoardView view) {
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
		int posY = (int) (event.getY()-offset)/(width+5);
		
		if(!(posX<len&&posX>=0&&posY>=0&&posY<len))
			return false; //out of bounds
		
		ApplicationControl.getGameController().handleClick(posX,posY);
		
		view.postInvalidate();
		return true;
	}

	public void updateDim(int len, int width, int offset) {
		this.len = len;
		this.width = width;
		this.offset = offset;
	}
}