package tk.agarsia.tictac2.controller.play;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.activities.MenuActivity;
import android.accounts.Account;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

@Deprecated
public class PlayValidator extends AsyncTask<Void, Void, String> {
	private Account acc;
	private MenuActivity act;
	private boolean auth;
	
	public PlayValidator(Account a, MenuActivity act) {
		this(a,act,true);
	}
	
	public PlayValidator(Account a, MenuActivity act, boolean auth) {
		this.auth = auth;
		acc = a;
		this.act = act;

		execute();
	}
	
	public PlayValidator(Account a) {
		this(a,null,false);
	}
	
	

	@Override
	protected String doInBackground(Void... arg0) {
		String token = null; // init token

		try {
			token = GoogleAuthUtil.getToken(ApplicationControl.getContext(),
					acc.name, PlayController.SCOPE);
			PlayController.setToken(token);
			PlayController.setValidated(true);
		} catch (UserRecoverableAuthException userRecoverableException) {
			PlayController.setValidated(false);
			//authorization necessary
			if(auth)
				act.startActivityForResult(userRecoverableException.getIntent(),
						1001);
		} catch (Exception ex) {
			PlayController.setValidated(false);
			//something went wrong
			ex.printStackTrace();
			token = ex.getMessage();
		}

		return token;
	}

	@Override
	protected void onPostExecute(String token) {
		if(act!=null)
			act.finished(token);
	}
}