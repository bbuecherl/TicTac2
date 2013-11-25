package tk.agarsia.tictac2.view.activities;

import java.util.Random;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class OptActivity extends MainActivity {
	
	private Random rand;
	
	public OptActivity() {
		super(false,R.string.opt_subtitle);
		rand = new Random();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opt);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		((RadioButton) findViewById(R.id.opt_start_me)).setText(ApplicationControl.getStringPref("pref_player"));
		
		switch(ApplicationControl.getGameType()) {
		case INIT:
			//not initialized, go back
			back();
			break;
		case SINGLEPLAYER:
			((RadioButton) findViewById(R.id.opt_start_other)).setText(getString(R.string.opt_bot));
			findViewById(R.id.opt_name_desc).setVisibility(View.GONE);
			findViewById(R.id.opt_name).setVisibility(View.GONE);
			findViewById(R.id.space4).setVisibility(View.GONE);
			break;
		case MULTIPLAYER:
			findViewById(R.id.opt_name_desc).setVisibility(View.VISIBLE);
			findViewById(R.id.opt_name).setVisibility(View.VISIBLE);			
			findViewById(R.id.space4).setVisibility(View.VISIBLE);
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
		RadioGroup index = (RadioGroup) findViewById(R.id.opt_start);
		
		int interval = 0;
		int boardDim = 3+size.getSelectedItemPosition();
		int winLength = 3+win.getSelectedItemPosition();
		int mpt = 1+mpr.getSelectedItemPosition();
		int spi = 1;
		
		switch(index.getCheckedRadioButtonId()) {
		case R.id.opt_start_me:
			spi = 1;
			break;
		case R.id.opt_start_other:
			spi = 2;
			break;
		case R.id.opt_start_random:
			spi = 1+rand.nextInt(2);
		}
		
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
		
		ApplicationControl.getGame().initModel(interval, boardDim, winLength, mpt, spi);

		ApplicationControl.getGame().setPlayers(ApplicationControl.getMe(), player2);
		ApplicationControl.start(this,GameActivity.class);
	}

	@Override
	public void onClick(View v) {	
	}
}