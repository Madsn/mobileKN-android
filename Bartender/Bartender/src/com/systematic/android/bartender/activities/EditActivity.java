package com.systematic.android.bartender.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.systematic.android.bartender.R;
import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;

public class EditActivity extends Activity {
	
	private BartabDataSource dbsource;
	private TextView sodaCount;
	private TextView beerCount;
	private EditText initials;
	
	private EditText ownerInitials;
	private TextView creationDate;
	
	private Bartab tab;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		Intent intent = getIntent();
		// TODO: Error handling	
		Long tabId = intent.getLongExtra(HistoryActivity.EDIT_MESSAGE, -1);
		
		dbsource = new BartabDataSource(this);
//		tab = getBartabFromDB(tabId);
//		Log.v("TEST", tab.toString());
		if (savedInstanceState == null){
			tab = getBartabFromDB(tabId);
		} else {
			tab = (Bartab) savedInstanceState.getSerializable(Bartab.TAG);
		}
		sodaCount = (TextView) findViewById(R.id.edit_soda_count);
		beerCount = (TextView) findViewById(R.id.edit_beer_count);
		initials = (EditText) findViewById(R.id.edit_initials_edittext);
		creationDate = (TextView) findViewById(R.id.edit_header_date);
		ownerInitials = (EditText) findViewById(R.id.edit_initials_edittext);
		
		updateGUI();
	}

	private void updateGUI() {
		beerCount.setText(tab.getBeerCountAsString());
		sodaCount.setText(tab.getSodaCountAsString());
		initials.setText(tab.getInitials());
		creationDate.setText(tab.getFormattedCreatedAt());
		ownerInitials.setText(tab.getInitials());
	}

	private Bartab getBartabFromDB(Long tabId) {
		dbsource.open();
		Bartab bartab = dbsource.getBartabById(tabId);
		dbsource.close();
		return bartab;
	}
	
	public void onBeerPlusBtnClick(View v) {
		tab.addBeer();
		beerCount.setText(tab.getBeerCountAsString());
	}

	public void onSodaPlusBtnClick(View v) {
		tab.addSoda();
		sodaCount.setText(tab.getSodaCountAsString());
	}

	public void onBeerMinusBtnClick(View v) {
		tab.removeBeer();
		beerCount.setText(tab.getBeerCountAsString());
	}

	public void onSodaMinusBtnClick(View v) {
		tab.removeSoda();
		sodaCount.setText(tab.getSodaCountAsString());
	}

	public void onSaveBtnClick(View v) {
		tab.setInitials(initials.getText().toString());
		
		dbsource.open();
		dbsource.saveBartab(tab);
		dbsource.close();
		
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onDeleteBtnClick(View v) {
		dbsource.open();
		dbsource.deleteBartab(tab);
		dbsource.close();
		
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(Bartab.TAG, tab);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateGUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
