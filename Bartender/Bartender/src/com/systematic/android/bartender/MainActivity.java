package com.systematic.android.bartender;

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
	Bartender bartender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bartender = new Bartender();
		
		sodaCount = (TextView) findViewById(R.id.soda_count);
		beerCount = (TextView) findViewById(R.id.beer_count);
		initials = (EditText) findViewById(R.id.initials_edittext);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onBeerPlusBtnClick(View v){
		bartender.addBeer();
		beerCount.setText(bartender.getBeerCountAsString());
	}
	
	public void onSodaPlusBtnClick(View v){
		bartender.addSoda();
		sodaCount.setText(bartender.getSodaCountAsString());
	}
	
	public void onBeerMinusBtnClick(View v){
		bartender.removeBeer();
		beerCount.setText(bartender.getBeerCountAsString());
	}
	
	public void onSodaMinusBtnClick(View v){
		bartender.removeSoda();
		sodaCount.setText(bartender.getSodaCountAsString());
	}
	
	public void onSaveBtnClick(View v){
		
	}
}
