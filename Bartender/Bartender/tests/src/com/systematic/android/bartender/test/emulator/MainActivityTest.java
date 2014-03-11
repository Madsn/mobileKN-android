package com.systematic.android.bartender.test.emulator;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.systematic.android.bartender.R;
import com.systematic.android.bartender.activities.MainActivity;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity activity;

	Button addBeerBtn;
	Button minusBeerBtn;
	Button addSodaBtn;
	Button minusSodaBtn;
	Button saveBtn;
	EditText initialsText;
	TextView beerCount;
	TextView sodaCount;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();

		addBeerBtn = (Button) activity.findViewById(R.id.beer_plus_btn);
		minusBeerBtn = (Button) activity.findViewById(R.id.beer_minus_btn);
		addSodaBtn = (Button) activity.findViewById(R.id.soda_plus_btn);
		minusSodaBtn = (Button) activity.findViewById(R.id.soda_minus_btn);
		initialsText = (EditText) activity.findViewById(R.id.initials_edittext);
		saveBtn = (Button) activity.findViewById(R.id.save_btn);
		beerCount = (TextView) activity.findViewById(R.id.beer_count);
		sodaCount = (TextView) activity.findViewById(R.id.soda_count);
	}

	@Override
	public void tearDown() throws Exception {
		activity.resetBartender();
		super.tearDown();
	}

	public void testAddingDrinks() {
		assertEquals(0, getSodaCount());
		assertEquals(0, getBeerCount());
		multipleClicks(addBeerBtn, 3);
		multipleClicks(addSodaBtn, 4);
		assertEquals(4, getSodaCount());
		assertEquals(3, getBeerCount());
	}

	public void testRemovingDrinks() {
		clickOn(addBeerBtn);
		clickOn(addSodaBtn);
		assertEquals(1, getBeerCount());
		assertEquals(1, getSodaCount());
		multipleClicks(minusBeerBtn, 4);
		multipleClicks(minusSodaBtn, 4);
		assertEquals(0, getBeerCount());
		assertEquals(0, getSodaCount());
	}

	// TODO: Test below doesn't work properly. If mainactivity is broken
	// in a way where changing orientation resets all state, this test will
	// still pass
	public void testStateDestroy() throws InterruptedException {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before destroying activity", 5, getBeerCount());

		// destroyActivity();
		activity.finish();
		setActivity(null);
		activity = getActivity();
		getInstrumentation().waitForIdleSync();

		assertEquals("Beercount after recreating activity", 5, getBeerCount());
	}

	public void testStateStop() {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before stopping activity", 5, getBeerCount());

		getInstrumentation().callActivityOnStop(activity);
		getInstrumentation().callActivityOnRestart(activity);
		getInstrumentation().waitForIdleSync();

		assertEquals("Beercount after restarting activity", 5, getBeerCount());
	}

	public void testStatePause() {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before pausing activity", 5, getBeerCount());

		// Pause activity and alter state
		getInstrumentation().callActivityOnPause(activity);
		getInstrumentation().waitForIdleSync();
		clickOn(minusBeerBtn);

		// Verify that UI is in sync with data on resume
		resumeActivity();
		assertEquals("Beercount after resuming activity", 4, getBeerCount());
	}

	private void destroyActivity() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				getInstrumentation().callActivityOnDestroy(activity);
			}
		});
		// getInstrumentation().waitForIdleSync();
	}

	private void resumeActivity() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				getInstrumentation().callActivityOnResume(activity);
			}
		});
		getInstrumentation().waitForIdleSync();
	}

	private void multipleClicks(final Button button, final int count) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				for (int j = count; j > 0; j--) {
					button.performClick();
				}
			}
		});
		getInstrumentation().waitForIdleSync();
	}

	private void clickOn(final Button button) {
		multipleClicks(button, 1);
	}

	private void updateGUI() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				activity.updateGUI();
			}
		});
		getInstrumentation().waitForIdleSync();
	}

	private int getSodaCount() {
		// updateGUI();
		return Integer.parseInt(sodaCount.getText().toString());
	}

	private int getBeerCount() {
		// updateGUI();
		return Integer.parseInt(beerCount.getText().toString());
	}

}
