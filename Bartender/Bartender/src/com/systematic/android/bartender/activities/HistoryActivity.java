package com.systematic.android.bartender.activities;

import java.util.List;

import com.systematic.android.bartender.Bartab;
import com.systematic.android.bartender.BartabDataSource;
import com.systematic.android.bartender.R;
import com.systematic.android.bartender.R.layout;
import com.systematic.android.bartender.R.menu;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class HistoryActivity extends ListActivity {

	private BartabDataSource dbsource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		dbsource = new BartabDataSource(this);
		dbsource.open();

		List<Bartab> values = dbsource.findAllBartabs();

		ArrayAdapter<Bartab> adapter = new ArrayAdapter<Bartab>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onResume() {
		dbsource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		dbsource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
