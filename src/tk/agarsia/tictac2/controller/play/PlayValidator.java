package tk.agarsia.tictac2.controller.play;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.activities.MenuActivity;
import android.accounts.Account;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class PlayValidator extends AsyncTask<Void, Void, String> {
	private Account acc;
	private MenuActivity act;

	public PlayValidator(Account a, MenuActivity act) {
		acc = a;
		this.act = act;

		execute();
	}

	@Override
	protected String doInBackground(Void... arg0) {
		String token = null; // init token

		try {
			token = GoogleAuthUtil.getToken(ApplicationControl.getContext(),
					acc.name, PlayController.SCOPE);
		} catch (UserRecoverableAuthException userRecoverableException) {
			//authorization necessary
			act.startActivityForResult(userRecoverableException.getIntent(),
					1001);
		} catch (Exception ex) {
			//something went wrong
			ex.printStackTrace();
			token = ex.getMessage();
		}

		return token;
	}

	@Override
	protected void onPostExecute(String token) {
		act.finished(token);
	}
}