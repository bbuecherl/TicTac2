package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.BoardTouchListener;
import tk.agarsia.tictac2.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class for the board view.
 * 
 * This view displays the current game board, the players and indicates the
 * active player.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class BoardView extends View {

	private Game game; // reference to the current game
	private Paint paint; // paint object for onDraw
	private BoardTouchListener listener; // touch listener
	private int me; // color of the local player
	private int you; // color of the other player

	/**
	 * Default Constructor for android views
	 * 
	 * This constructor implements the super constructor, to register the view,
	 * initializes the variables and registers the BoardTouchListener as touch
	 * listener.
	 * 
	 * @param context
	 *            context object of the application
	 * @param attrs
	 *            attribute set of the view (defined in xml)
	 */
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
		game = ApplicationControl.getGame();

		// touch listener
		listener = new BoardTouchListener(this);
		this.setOnTouchListener(listener);

		// create a paint object
		paint = new Paint();
		paint.setTextSize(25);

		// fetch colors from preferences
		me = Color.parseColor(ApplicationControl.getStringPref("pref_color_me",
				"#007200"));
		you = Color.parseColor(ApplicationControl.getStringPref(
				"pref_color_other", "#720000"));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// redraw if layout has changed
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw player informations
		int offset = drawInfo(canvas);

		// draw grid
		drawGrid(canvas, offset);

		// update listener dimensions too!
		listener.updateDim(
				game.getBoardDim(),
				(canvas.getWidth() > canvas.getHeight() - offset) ? (canvas
						.getHeight() - offset) / game.getBoardDim() : canvas
						.getWidth() / game.getBoardDim(), offset);
	}

	/**
	 * Method to draw player information on the canvas.
	 * 
	 * This will print the player names in their color at the top of the view
	 * and draw a line underneath to indicate the active one.
	 * 
	 * @param canvas
	 *            canvas object
	 * @return offsetTop for the grid (TODO currently static at 80, may change
	 *         for version 2.0)
	 */
	private int drawInfo(Canvas canvas) {
		// paint local player on the left
		paint.setColor(me);
		paint.setAlpha(255);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText(game.getPlayers()[1].getName(), 10, 50, paint);
		if (game.getCurrentPlayerIndex() == 1) // if active player, draw
												// indicator
			canvas.drawRect(0, 70, canvas.getWidth() / 2, 75, paint);

		// paint other player on the right
		paint.setColor(you);
		paint.setAlpha(255);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(game.getPlayers()[2].getName(), canvas.getWidth() - 10,
				50, paint);
		if (game.getCurrentPlayerIndex() == 2) // if active player, draw
												// indicator
			canvas.drawRect(canvas.getWidth() / 2, 70, canvas.getWidth(), 75,
					paint);

		return 80;
	}

	/**
	 * Method to draw the game grid.
	 * 
	 * Paints the basic tic tac toe grid and indicates the taken fields with the
	 * player colors.
	 * 
	 * @param canvas
	 *            canvas object
	 * @param offset
	 *            offset from top
	 */
	private void drawGrid(Canvas canvas, int offset) {
		// fetch game dimensions
		int len = game.getBoardDim();

		// width of an field and size of a gridline (tl).
		int width = (canvas.getWidth() > canvas.getHeight() - offset) ? (canvas
				.getHeight() - offset) / len : canvas.getWidth() / len;
		int tl = 5;
		width -= tl;
		boolean draw;

		// iterate over all fields
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				// first paint the grid
				paint.setColor(Color.LTGRAY);
				paint.setAlpha(255);
				if (j == 0 && i != 0) {
					// vertical grid lines
					canvas.drawRect(tl / 2 + i * width + tl * (i - 1), offset
							+ tl / 2, tl / 2 + i * width + tl * i, offset
							+ width * len + tl * (len - 0.5f), paint);
				} else if (i == 0 && j != 0) {
					// horizontal grid lines
					canvas.drawRect(tl / 2, offset + tl / 2 + j * width + tl
							* (j - 1), width * len + tl * (len - 0.5f), offset
							+ tl / 2 + j * width + tl * j, paint);
				}

				draw = true;

				// select paint color depending on field value
				switch (game.getBoard().getField(i, j).getValue()) {
				case 1: // local player has marked this field
					paint.setColor(me);
					paint.setAlpha(255);
					break;
				case 2: // other player has marked this field
					paint.setColor(you);
					paint.setAlpha(255);
					break;
				default: // the field is still empty
					draw = false; // do not draw
					break;
				}

				// draw the field
				if (draw)
					canvas.drawRect(tl / 2 + i * width + tl * i, offset + tl
							/ 2 + j * width + tl * j, tl / 2 + (i + 1) * width
							+ tl * i, offset + tl / 2 + (j + 1) * width + tl
							* j, paint);
			}
		}
	}
}