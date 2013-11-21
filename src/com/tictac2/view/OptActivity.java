package com.tictac2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.tictac2.controller.ApplicationControl;

public class OptActivity extends MainActivity {
	
	public OptActivity() {
		super(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		switch(ApplicationControl.getGameType()) {
		case INIT:
			//not initialized, go back
			startActivity(new Intent(getApplicationContext(),MenuActivity.class));
			break;
		case SINGLEPLAYER:
			
			break;
		case MULTIPLAYER:
			
			break;
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
