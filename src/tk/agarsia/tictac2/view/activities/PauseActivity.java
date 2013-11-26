package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.view.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO this still needs to be implemented and included into the GameActivity...
public class PauseActivity extends MainActivity {

	public PauseActivity() {
		super(true,R.string.pause_subtitle);
	}
	
	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		return new Intent(getApplicationContext(),GameActivity.class);
	}

	@Override
	public void onClick(View v) {
	}
}