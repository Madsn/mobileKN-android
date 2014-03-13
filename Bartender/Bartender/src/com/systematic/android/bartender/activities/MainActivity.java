package com.systematic.android.bartender.activities;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.systematic.android.bartender.Bartab;
import com.systematic.android.bartender.BartabDataSource;
import com.systematic.android.bartender.GlobalSettings;
import com.systematic.android.bartender.dbHelper;
import com.systematic.android.bartender.R;

public class MainActivity extends Activity {

	TextView sodaCount, beerCount;
	EditText initials;
	Bartab bartab;
	GlobalSettings globals;
	
	private BartabDataSource dbsource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sodaCount = (TextView) findViewById(R.id.soda_count);
		beerCount = (TextView) findViewById(R.id.beer_count);
		initials = (EditText) findViewById(R.id.initials_edittext);
		
		globals = GlobalSettings.getInstance();
		globals.setLocale(getResources().getConfiguration().locale);
		
		if (savedInstanceState == null){
			bartab = new Bartab();
		} else {
			bartab = (Bartab) savedInstanceState.getSerializable(Bartab.TAG);
		}
		dbsource = new BartabDataSource(this);
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
		bartab.setInitials(initials.getText().toString());
		
		dbsource.open();
		dbsource.saveBartab(bartab);
		dbsource.close();
		
		bartab.resetAll();
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
	}
	
	public void onHistoryBtnClick(View v) {
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
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
