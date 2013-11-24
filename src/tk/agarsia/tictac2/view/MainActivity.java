package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class MainActivity extends ActionBarActivity  implements View.OnClickListener {

	private AlertDialog.Builder about;
	private boolean actions;
	
	public MainActivity(boolean actions) {
		this.actions = actions;
	}
	
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		
		about = new AlertDialog.Builder(this)
		.setTitle(R.string.about)
		.setMessage(R.string.about_text)
		.setNeutralButton(
				getResources().getString(R.string.close),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(actions) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main_activity_actions, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			ApplicationControl.start(this,Options.class);
			return true;
		} else if (item.getItemId() == R.id.action_about) {
			about.show();
			return true;
		}
		return false;
	}
}