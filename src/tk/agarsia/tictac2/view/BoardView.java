package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.BoardTouchListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class BoardView extends LinearLayout {

//	private Game game;
	private ImageButton[][] fields;
	private BoardTouchListener listener;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
//		game = ApplicationControl.getGame();

		Log.i("board", "start");

		// testing purposes
		// game.initModel(0, 3, 3, 1,0);

		listener = new BoardTouchListener();

		int len = 3;

		fields = new ImageButton[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				fields[i][j] = new ImageButton(getContext());
				fields[i][j].setOnTouchListener(listener);
				fields[i][j].setImageResource(R.drawable.ic_action_about);
				fields[i][j].setLayoutParams(new ViewGroup.LayoutParams(160, 160));
				this.addView(fields[i][j]);
			}
		}
		this.invalidate();
	}

//	@Override
//	protected void onLayout(boolean changed, int top, int left, int right,
//			int bottom) {
//		// width, height and stuff
//		int len = 3;
//		int width = getWidth() / len;
//		int height = getHeight() / len;
//
//		// make it squared
//		if (width > height)
//			width = height;
//		else
//			height = width;
//
//		Log.i("board", "onLayout(" + changed + "," + top + "," + left + ","
//				+ right + "," + bottom + ") " + width + "-" + height);
//
//		//LayoutParams layout = new LayoutParams(width, height);
//		// yeah, no iteration, we need the indexes to initialize fields
//		for (int i = 0; i < len; i++) {
//			for (int j = 0; j < len; j++) {
//				//fields[i][j].setLayoutParams(layout);
//			}
//		}
//	}
}
