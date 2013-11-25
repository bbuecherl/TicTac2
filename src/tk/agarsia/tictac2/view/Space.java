package tk.agarsia.tictac2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class Space extends View {
	public Space(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 20);
		this.setLayoutParams(params);
	}
}
