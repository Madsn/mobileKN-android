package com.systematic.android.bartender.activities;

import java.util.Date;

import com.systematic.android.bartender.Bartab;
import com.systematic.android.bartender.R;
import com.systematic.android.bartender.R.id;
import com.systematic.android.bartender.R.layout;
import com.systematic.android.bartender.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView sodaCount, beerCount;
	EditText initials;
	Bartab bartab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sodaCount = (TextView) findViewById(R.id.soda_count);
		beerCount = (TextView) findViewById(R.id.beer_count);
		initials = (EditText) findViewById(R.id.initials_edittext);
		
		if (savedInstanceState == null){
			bartab = new Bartab();
		} else {
			bartab = (Bartab) savedInstanceState.getSerializable(Bartab.TAG);
		}
		updateGUI();
	}

	public void updateGUI() {
		beerCount.setText(bartab.getBeerCountAsString());
		sodaCount.setText(bartab.getSodaCountAsString());
		initials.setText(bartab.getInitials());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onBeerPlusBtnClick(View v) {
		bartab.addBeer();
		beerCount.setText(bartab.getBeerCountAsString());
	}

	public void onSodaPlusBtnClick(View v) {
		bartab.addSoda();
		sodaCount.setText(bartab.getSodaCountAsString());
	}

	public void onBeerMinusBtnClick(View v) {
		bartab.removeBeer();
		beerCount.setText(bartab.getBeerCountAsString());
	}

	public void onSodaMinusBtnClick(View v) {
		bartab.removeSoda();
		sodaCount.setText(bartab.getSodaCountAsString());
	}

	public void onSaveBtnClick(View v) {
		Date curDate = new Date();
		bartab.setCreatedAt(curDate);
		bartab.setLastEditedAt(curDate);
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(Bartab.TAG, bartab);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateGUI();
	}

	public void resetBartender() {
		bartab.resetAll();
	}

}
