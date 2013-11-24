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
	private BoardTouchListener listener;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// for easier reference
		game = ApplicationControl.getGame();
		
		listener = new BoardTouchListener(this);
		
		this.setOnTouchListener(listener);
		
		paint = new Paint();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int len = game.getBoardDim();
		
		//width & tl
		int width = (canvas.getWidth()>canvas.getHeight())?canvas.getHeight() /len : canvas.getWidth() / len;
		int tl = 5;
		width -= tl;
		int val;
		
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				paint.setColor(Color.LTGRAY);
				paint.setAlpha(255);
				if(j==0&&i!=0) {
					canvas.drawRect(tl+i*width,tl/2,tl+i*width+5,width*len+tl*(len-1), paint);
				} else if(i==0&&j!=0) {
					canvas.drawRect(tl/2,tl+j*width,width*len+tl*(len-1),tl+j*width+5, paint);
				}
				
				val = game.getBoard().getField(i, j).getValue();
				if(val==0) {
					paint.setColor(Color.LTGRAY);
				} else if(val==1) {
					paint.setColor(Color.GREEN);
				} else {
					paint.setColor(Color.RED);
				}
				
				
				paint.setAlpha(100);
				canvas.drawRect(tl/2+i*width+5*i, tl/2+j*width+5*j, tl/2+(i+1)*width+5*i,tl/2+(j+1)*width+5*j, paint);
			}
		}	
		
		//update listener dimensions too!
		listener.updateDim(len,width);
	}
}