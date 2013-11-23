package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.view.MainActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;


public class OptActivity extends MainActivity {
	
	public OptActivity() {
		super(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opt);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		switch(ApplicationControl.getGameType()) {
		case INIT:
			//not initialized, go back
			back();
			break;
		case SINGLEPLAYER:
			
			break;
		case MULTIPLAYER:
			
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.opt_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_next) {
			next();
			return true;
		} else if (item.getItemId() == R.id.action_back) {
			back();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {

		return false;
	}

	private void back() {
		ApplicationControl.start(this,MenuActivity.class);
	}
	
	private void next() {

		Spinner size = (Spinner) findViewById(R.id.opt_boardsize_id);
		Spinner win = (Spinner) findViewById(R.id.opt_winlength_id);
		Spinner mpr = (Spinner) findViewById(R.id.opt_mpr_id);
		
		int interval = 0;
		int boardDim = 3+size.getSelectedItemPosition();
		int winLength = 3+win.getSelectedItemPosition();
		int mpt = 1+mpr.getSelectedItemPosition();
		int spi = 0;
		
		
		ApplicationControl.getGame().initModel(interval, boardDim, winLength, mpt, spi);

		if(ApplicationControl.getGameType()==GameType.SINGLEPLAYER) {
			ApplicationControl.start(this,GameActivity.class);
		} else {
			ApplicationControl.start(this,MultiNameActivity.class);
		}
	}
}