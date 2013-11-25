package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.model.player.bot.BotRandom;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
import tk.agarsia.tictac2.view.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class OptActivity extends MainActivity {
	
	public OptActivity() {
		super(false,R.string.opt_subtitle);
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
			findViewById(R.id.opt_name_desc).setVisibility(View.GONE);
			findViewById(R.id.opt_name_desc).setVisibility(View.GONE);
			break;
		case MULTIPLAYER:
			findViewById(R.id.opt_name_desc).setVisibility(View.VISIBLE);
			findViewById(R.id.opt_name_desc).setVisibility(View.VISIBLE);			
			break;
		}
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		return new Intent(getApplicationContext(),MenuActivity.class);
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

	private void back() {
		ApplicationControl.start(this,MenuActivity.class);
	}
	
	private void next() {
		ApplicationControl.reinit();
		
		Spinner size = (Spinner) findViewById(R.id.opt_boardsize_id);
		Spinner win = (Spinner) findViewById(R.id.opt_winlength_id);
		Spinner mpr = (Spinner) findViewById(R.id.opt_mpr_id);
		
		int interval = 0;
		int boardDim = 3+size.getSelectedItemPosition();
		int winLength = 3+win.getSelectedItemPosition();
		int mpt = 1+mpr.getSelectedItemPosition();
		int spi = 1;
		
		AbstractPlayer player2;
		//init other player
		if(ApplicationControl.getGameType()==GameType.SINGLEPLAYER) {
			//start singleplayer
			 player2 = new BotRandom(ApplicationControl.getGame());
		} else {
			//start guest player
			String name = ((EditText) findViewById(R.id.opt_name)).getText().toString();
			if(name=="")
				name = getString(R.string.opt_default);
			
			player2 = new HumanLocal(name,ApplicationControl.getGame());
		}
		
		//TODO add start index

		ApplicationControl.getGame().initModel(interval, boardDim, winLength, mpt, spi);

		ApplicationControl.getGame().setPlayers(ApplicationControl.getMe(), player2);
		ApplicationControl.start(this,GameActivity.class);
	}

	@Override
	public void onClick(View v) {	
	}
}