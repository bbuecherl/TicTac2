package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.BoardTouchListener;
import tk.agarsia.tictac2.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BoardView extends View {

	private Game game;
	private Paint paint;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
		game = ApplicationControl.getGame();

		Log.i("board", "start");

		// testing purposes
		game.initModel(0, 5, 3, 1,0);

		this.setOnTouchListener(new BoardTouchListener());
		
		paint = new Paint();
		paint.setColor(Color.LTGRAY);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int len = 5;
		
		//width & tl
		int width = (canvas.getWidth()>canvas.getHeight())?canvas.getHeight() /len : canvas.getWidth() / len;
		int tl = 10;
		width -= tl;
		
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				if(j!=0) {
					paint.setAlpha(255);
					//canvas.drawRect(i*width+5*i-5,0,5,len*width+5*(len-1), paint);
				} else {
					paint.setAlpha(255);
					//canvas.drawRect(0,,5,len*width+5*(len-1), paint);
				}
				paint.setAlpha(30);
				canvas.drawRect(tl+i*width+5*i, tl+j*width+5*j, -tl+(i+1)*width+5*i, -tl+(j+1)*width+5*j, paint);
			}
		}	
	}
}