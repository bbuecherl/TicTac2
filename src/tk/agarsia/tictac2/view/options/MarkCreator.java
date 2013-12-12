package tk.agarsia.tictac2.view.options;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.marks.MarkController;
import tk.agarsia.tictac2.view.TicTac2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
	private MenuItem save;

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

				view = new MarkCreatorView(this, json.getJSONArray("elements"));
			} catch (JSONException e) {
				e.printStackTrace();
				view = new MarkCreatorView(this);
			}
		} else {
			view = new MarkCreatorView(this);
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setSubtitle(getString(R.string.markcreator));

		text = new EditText(getApplicationContext());

		text.setText(name);
		text.setHint(R.string.mark_name);
		text.setTextColor(Color.DKGRAY);
		text.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				madeChanges();
			}
		});

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
		save = menu.findItem(R.id.action_mark_save);
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

	public void madeChanges() {
		save.setEnabled(true);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	private void save() {
		save.setEnabled(false);
		view.save();
	}

	class MarkCreatorView extends View implements OnTouchListener {
		private static final int TOOLBAR_PADDING = 8;
		
		private Paint paint;
		private String tool;

		private JSONArray mark;
		private MarkCreator parent;
		private Bitmap select,rect,circle;

		private int toolsize;
		
		public MarkCreatorView(MarkCreator parent) {
			this(parent, new JSONArray());
		}

		public MarkCreatorView(MarkCreator parent, JSONArray mark) {
			super(parent.getApplicationContext());

			this.parent = parent;
			this.mark = mark;

			tool = "select";

			paint = new Paint();

			select = BitmapFactory.decodeResource(getContext()
					.getResources(), R.drawable.ic_action_mark_select);
			rect = BitmapFactory.decodeResource(getContext()
					.getResources(), R.drawable.ic_action_mark_rect);
			circle = BitmapFactory.decodeResource(getContext()
					.getResources(), R.drawable.ic_action_mark_circle);
			
			
			setOnTouchListener(this);
		}

		public void save() {

		}

		@Override
		protected void onDraw(Canvas canvas) {
			int offset = drawToolbar(canvas);

			int box = canvas.getWidth() > canvas.getHeight() + offset ? canvas
					.getHeight() + offset : canvas.getWidth();
			
			paint.setColor(0x50AA0000);
			canvas.drawRect(0, offset, box, box + offset, paint);
		}

		private int drawToolbar(Canvas canvas) {
			paint.setColor(Color.LTGRAY);
			toolsize = canvas.getWidth()/3-8;

			canvas.drawRect(0, 0, canvas.getWidth(), 4, paint);
			canvas.drawRect(0, select.getHeight()+TOOLBAR_PADDING*2+4, canvas.getWidth(), select.getHeight()+8+TOOLBAR_PADDING*2, paint);
			canvas.drawRect(toolsize,4,toolsize+4,select.getHeight()+TOOLBAR_PADDING*2+4,paint);
			canvas.drawRect(toolsize*2+4,4,toolsize*2+8,select.getHeight()+TOOLBAR_PADDING*2+4,paint);
			
			paint.setColor(Color.BLUE);
			paint.setAlpha(20);
			if(tool.equals("select"))
				canvas.drawRect(0,4,toolsize,select.getHeight()+TOOLBAR_PADDING*2+4,paint);
			if(tool.equals("rect"))
				canvas.drawRect(toolsize+4,4,toolsize*2+4,select.getHeight()+TOOLBAR_PADDING*2+4,paint);
			if(tool.equals("circle"))
				canvas.drawRect(toolsize*2+8,4,canvas.getWidth(),select.getHeight()+TOOLBAR_PADDING*2+4,paint);
				
			paint.setAlpha(255);	
				
			canvas.drawBitmap(select,(toolsize-select.getWidth())/2,TOOLBAR_PADDING+4,
					paint);
			canvas.drawBitmap(rect,toolsize+4+(toolsize-rect.getWidth())/2,TOOLBAR_PADDING+4,
					paint);
			canvas.drawBitmap(circle,toolsize*2+8+(toolsize-circle.getWidth())/2,TOOLBAR_PADDING+4,
					paint);
			

			return select.getHeight()+8+TOOLBAR_PADDING*2;
		}

		@Override
		public boolean onTouch(View view, MotionEvent ev) {
			if(ev.getY()<select.getHeight()+8+TOOLBAR_PADDING*2) {
				if(ev.getX()<toolsize+3) {
					tool = "select";
				} else if(ev.getX()<toolsize*2+7) {
					tool = "rect";
				} else {
					tool = "circle";
				}
			}
			
			invalidate();
			return false;
		}
	}
}