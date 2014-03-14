package com.systematic.android.bartender.activities;

import java.util.List;


import com.systematic.android.bartender.R;
import com.systematic.android.bartender.R.layout;
import com.systematic.android.bartender.R.menu;
import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class HistoryActivity extends ListActivity {
	
	public final static String EDIT_MESSAGE = "com.systematic.android.bartender.EDIT_MESSAGE";

	private BartabDataSource dbsource;

	private ArrayAdapter<Bartab> adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		dbsource = new BartabDataSource(this);
		
		initializeAdapter();
	}
	
	public void initializeAdapter(){
		dbsource.open();
		List<Bartab> values = dbsource.findAllBartabsSortedByDate("DESC");

		adapter = new ArrayAdapter<Bartab>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		dbsource.close();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Bartab tab = (Bartab) getListAdapter().getItem(position);
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra(EDIT_MESSAGE, tab.getId());
		startActivity(intent);
		dbsource.close();
		finish();
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
	protected void onDestroy() {
		dbsource.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_delete_all:
			deleteAll();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void deleteAll() {
		dbsource.open();
		dbsource.deleteAllBartabs();
		dbsource.close();
		initializeAdapter();
	}

}
