package tk.agarsia.tictac2.view.options;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MarkCreator extends ActionBarActivity {

	private MarkCreatorView view;

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);

		view = new MarkCreatorView(getApplicationContext());

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		setContentView(layout);
	}

	@Override
	protected void onPause() {
		view.save();
		finish();
		super.onPause();
	}

	class MarkCreatorView extends View implements OnTouchListener {
		public MarkCreatorView(Context context) {
			super(context);
			setOnTouchListener(this);
		}

		public void save() {

		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {

			return false;
		}

	}
}