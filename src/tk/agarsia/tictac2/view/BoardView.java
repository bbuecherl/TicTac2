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

public class BoardView extends View {

	private Game game;
	private Paint paint;
	private BoardTouchListener listener;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
		game = ApplicationControl.getGame();

		listener = new BoardTouchListener(this);

		this.setOnTouchListener(listener);

		paint = new Paint();
		paint.setTextSize(20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int offset = 100;

		//draw player informations
		drawInfo(canvas);
		
		//draw grid
		drawGrid(canvas, offset);

		// update listener dimensions too!
		listener.updateDim(
				game.getBoardDim(),
				(canvas.getWidth() > canvas.getHeight() - offset) ? (canvas
						.getHeight() - offset) / game.getBoardDim() : canvas
						.getWidth() / game.getBoardDim(),offset);
	}
	
	private void drawInfo(Canvas canvas) {
		paint.setColor(Color.GREEN);
		paint.setAlpha(255);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText(game.getPlayers()[1].getName(), 0, 30, paint);
		paint.setColor(Color.RED);
		paint.setAlpha(255);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(game.getPlayers()[2].getName(), canvas.getWidth(), 30, paint);
	}

	private void drawGrid(Canvas canvas, int offset) {
		int len = game.getBoardDim();

		// width & tl
		int width = (canvas.getWidth() > canvas.getHeight() - offset) ? (canvas
				.getHeight() - offset) / len : canvas.getWidth() / len;
		int tl = 5;
		width -= tl;
		int val;

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				paint.setColor(Color.LTGRAY);
				paint.setAlpha(255);
				if (j == 0 && i != 0) {
					canvas.drawRect(tl / 2 + i * width + tl * (i - 1),
							offset+ tl / 2, tl / 2 + i * width + tl * i, offset + width
									* len + tl * (len - 0.5f), paint);
				} else if (i == 0 && j != 0) {
					canvas.drawRect(tl / 2, offset + tl / 2 + j * width + tl
							* (j - 1), width * len + tl * (len - 0.5f), offset +
							tl / 2 + j * width + tl * j, paint);
				}

				val = game.getBoard().getField(i, j).getValue();
				if (val == 0) {
					paint.setColor(Color.LTGRAY);
					paint.setAlpha(0);
				} else if (val == 1) {
					paint.setColor(Color.GREEN);
					paint.setAlpha(255);
				} else {
					paint.setColor(Color.RED);
					paint.setAlpha(255);
				}

				canvas.drawRect(tl / 2 + i * width + tl * i, offset + tl / 2 + j
						* width + tl * j, tl / 2 + (i + 1) * width + tl
						* i, offset + tl / 2 + (j + 1) * width + tl * j, paint);
			}
		}
	}
}