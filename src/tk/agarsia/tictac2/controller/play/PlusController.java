package tk.agarsia.tictac2.controller.play;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

public class PlusController implements ConnectionCallbacks, OnConnectionFailedListener {
	
	private static PlusController instance;

	private PlusClient client;
	
	public PlusController(Context context) {
		client = new PlusClient.Builder(context, this, this).build();		
	}
	
	public static void init(Context context) {
		instance = new PlusController(context);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
	}

	@Override
	public void onDisconnected() {
		
	}
}
