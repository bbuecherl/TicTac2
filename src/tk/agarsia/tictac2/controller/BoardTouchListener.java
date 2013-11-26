package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.view.BoardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * This class handles touch events on board view objects.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class BoardTouchListener implements OnTouchListener {

	private int len; // game board length
	private int width; // field width on the board view object
	private int offset; // top offset on board view
	private BoardView view; // board view reference

	/**
	 * Custom constructor
	 * 
	 * The constructor initializes the object variables and sets the board view
	 * reference
	 * 
	 * @param view
	 *            board view object
	 */
	public BoardTouchListener(BoardView view) {
		this.view = view;
		len = 0;
		width = 0;
		offset = 0;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// we listen to touch downs only!
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;

		// calculate field (x,y) of current touch event
		int x = (int) event.getX() / (width + 5);
		int y = (int) (event.getY() - offset) / (width + 5);

		// prevent out of bound exceptions
		if (!(x < len && x >= 0 && y >= 0 && y < len))
			return false;

		// handle the click on game controller
		ApplicationControl.getGameController().handleClick(x, y);

		// invalidate the view to force repainting (without this, no changes
		// would be displayed!)
		view.postInvalidate();
		return true;
	}

	/**
	 * Method to update board view dimensions.
	 * 
	 * This method will be updated by board view after every draw event
	 * 
	 * @param len
	 *            new board dimension (TODO actually couldn't change...)
	 * @param width
	 *            new field width
	 * @param offset
	 *            new offset from top (TODO this currently doesn't change
	 *            either)
	 */
	public void updateDim(int len, int width, int offset) {
		this.len = len;
		this.width = width;
		this.offset = offset;
	}
}