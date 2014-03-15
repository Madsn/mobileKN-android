package com.systematic.android.bartender.test.emulator;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.systematic.android.bartender.R;
import com.systematic.android.bartender.activities.EditActivity;
import com.systematic.android.bartender.activities.HistoryActivity;
import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;

public class EditActivityTest extends
ActivityInstrumentationTestCase2<EditActivity> {

	private EditActivity activity;
	private BartabDataSource db;
	private static final int BEERS = 5;
	private static final int SODAS = 10;
	
	Button addBeerBtn;
	Button minusBeerBtn;
	Button addSodaBtn;
	Button minusSodaBtn;
	Button saveBtn;
	EditText initialsText;
	TextView beerCount;
	TextView sodaCount;

	public EditActivityTest() {
		super(EditActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		activity = getActivity();
		db = new BartabDataSource(activity);
		db.open();
		saveBartabInDb(SODAS, BEERS, Util.INITIALS);
		Bartab tab = getFirstBartabFromDb();
		
		Intent intent = new Intent();
		intent.putExtra(HistoryActivity.EDIT_MESSAGE, tab.getId());
		setActivityIntent(intent);
		activity = getActivity();
		
		addBeerBtn = (Button) activity.findViewById(R.id.edit_beer_plus_btn);
		minusBeerBtn = (Button) activity.findViewById(R.id.edit_beer_minus_btn);
		addSodaBtn = (Button) activity.findViewById(R.id.edit_soda_plus_btn);
		minusSodaBtn = (Button) activity.findViewById(R.id.edit_soda_minus_btn);
		initialsText = (EditText) activity.findViewById(R.id.edit_initials_edittext);
		saveBtn = (Button) activity.findViewById(R.id.save_edit_btn);
		beerCount = (TextView) activity.findViewById(R.id.edit_beer_count);
		sodaCount = (TextView) activity.findViewById(R.id.edit_soda_count);
	}
	
	@Override
	public void tearDown() throws Exception {
		db.close();
		super.tearDown();
	}
	
	@UiThreadTest
	public void testGuiDisplaysCorrectCountsAndInitials() {
		assertEquals(BEERS, getBeerCount());
		assertEquals(SODAS, getSodaCount());
		assertEquals(Util.INITIALS, getInitials());
	}

	private void saveBartabInDb(int sodaCount, int beerCount, String initials) {
		Util.saveBartabInDb(sodaCount, beerCount, initials, db);
	}
	
	private Bartab getFirstBartabFromDb() {
		return db.findAllBartabs().get(0);
	}
	
	private int getSodaCount() {
		return Integer.parseInt(sodaCount.getText().toString());
	}

	private int getBeerCount() {
		return Integer.parseInt(beerCount.getText().toString());
	}
	
	private String getInitials() {
		return initialsText.getText().toString();
	}
	
}
