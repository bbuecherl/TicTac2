package tk.agarsia.tictac2.view.options;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.marks.MarkController;
import tk.agarsia.tictac2.view.TicTac2;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MarkCreator extends ActionBarActivity {
	public static final int NEW_MARK = -1;
	public static int CUR_ID = -1;

	private MarkCreatorView view;
	private EditText text;

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		
		if (!ApplicationControl.isInit()) {
			startActivity(new Intent(getApplicationContext(), TicTac2.class));
			AppStackController.toStack(this);
			return;
		}

		String name = "";

		if (CUR_ID != NEW_MARK) {
			try {
				JSONObject json = MarkController.getDB().getJSONObject(CUR_ID);
				
				name = json.getString("name");
				
				view = new MarkCreatorView(getApplicationContext(),json.getJSONArray("elements"));
			} catch(JSONException e) {
				e.printStackTrace();
				view = new MarkCreatorView(getApplicationContext());
			}
		} else {
			view = new MarkCreatorView(getApplicationContext());			
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setSubtitle(getString(R.string.markcreator));

		text = new EditText(getApplicationContext());

		text.setText(name);
		text.setHint(R.string.mark_name);
		text.setTextColor(Color.DKGRAY);
		
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(text, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		setContentView(layout);
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		// "up" navigation
		return new Intent(getApplicationContext(), Options.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.markcreator_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_mark_save:
			save();
			return true;
		}
		return false;
	}

	@Override
	protected void onPause() {
		save();
		finish();
		super.onPause();
	}
	
	private void save() {
		view.save();
	}

	class MarkCreatorView extends View implements OnTouchListener {
		private Paint paint;
		private String tool;
		
		private JSONArray mark;

		public MarkCreatorView(Context context) {
			this(context,new JSONArray());
		}
		
		public MarkCreatorView(Context context, JSONArray mark) {
			super(context);
			
			this.mark = mark;

			tool = "select";

			paint = new Paint();
			paint.setColor(0x50AA0000);

			setOnTouchListener(this);
		}

		public void save() {

		}

		public void setTool(String tool) {
			this.tool = tool;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			int offset = 0;
			
			int box = canvas.getWidth() > canvas.getHeight() +offset? canvas
					.getHeight()+offset : canvas.getWidth();
			canvas.drawRect(0, offset, box, box+offset, paint);
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {

			return false;
		}
	}
}