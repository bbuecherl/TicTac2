package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.BoardTouchListener;
import tk.agarsia.tictac2.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {

	private Game game;
	private Paint paint;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
		game = ApplicationControl.getGame();

		this.setOnTouchListener(new BoardTouchListener());
		
		paint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int len = game.getboardDim();
		
		//width & tl
		int width = (canvas.getWidth()>canvas.getHeight())?canvas.getHeight() /len : canvas.getWidth() / len;
		int tl = 5;
		width -= tl;
		
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				paint.setColor(Color.LTGRAY);
				if(j==0&&i!=0) {
					canvas.drawRect(tl+i*width,0,tl+i*width+5,width*len, paint);
				} else if(i==0&&j!=0) {
					canvas.drawRect(0,tl+j*width,width*len,tl+j*width+5, paint);
				}
				paint.setColor(Color.MAGENTA);
				paint.setAlpha(100);
				canvas.drawRect(tl+i*width+5*i, tl+j*width+5*j, tl+(i+1)*width+5*i, tl+(j+1)*width+5*j, paint);
			}
		}	
	}
}