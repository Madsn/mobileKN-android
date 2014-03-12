package com.systematic.android.bartender.activities;

import com.systematic.android.bartender.R;
import com.systematic.android.bartender.R.layout;
import com.systematic.android.bartender.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}