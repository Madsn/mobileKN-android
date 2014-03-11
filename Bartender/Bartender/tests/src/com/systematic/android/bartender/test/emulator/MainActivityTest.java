package com.systematic.android.bartender.test.emulator;


import com.systematic.android.bartender.MainActivity;
import com.systematic.android.bartender.R;

import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
	
	public MainActivityTest(){
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		activity = this.getActivity();
		
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
	
	@UiThreadTest
	public void testAddingDrinks(){
		assertEquals(0, getSodaCount());
		assertEquals(0, getBeerCount());
		multipleClicks(addBeerBtn, 3);
		multipleClicks(addSodaBtn, 4);
		assertEquals(4, getSodaCount());
		assertEquals(3, getBeerCount());
	}
	
	@UiThreadTest
	public void testRemovingDrinks(){
		addBeerBtn.performClick();
		addSodaBtn.performClick();
		assertEquals(1, getBeerCount());
		assertEquals(1, getSodaCount());
		multipleClicks(minusBeerBtn, 4);
		multipleClicks(minusSodaBtn, 4);
		assertEquals(0, getBeerCount());
		assertEquals(0, getSodaCount());
	}
	
	@UiThreadTest
	public void testStateDestroy() {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before destroying activity", 5, getBeerCount());
		
		activity.finish();
		activity = this.getActivity();
		
		addBeerBtn.performClick();
		assertEquals("Beercount after recreating activity", 6, getBeerCount());
	}
	
	@UiThreadTest
	public void testStateStop() {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before stopping activity", 5, getBeerCount());
		
		this.getInstrumentation().callActivityOnStop(activity);
		
		this.getInstrumentation().callActivityOnRestart(activity);
		
		assertEquals("Beercount after restarting activity", 5, getBeerCount());
	}
	
	@UiThreadTest
	public void testStatePause() {
		multipleClicks(addBeerBtn, 5);
		assertEquals("Beercount before pausing activity", 5, getBeerCount());
		
		// Pause activity and alter state
		this.getInstrumentation().callActivityOnPause(activity);
		minusBeerBtn.performClick();
		
		// Verify that UI is in sync with data on resume
		this.getInstrumentation().callActivityOnResume(activity);
		assertEquals("Beercount after resuming activity", 4, getBeerCount());
	}

	private void multipleClicks(Button button, int i) {
		for (;i>0;i--){
			button.performClick();
		}
	}
	
	private int getSodaCount(){
		return Integer.parseInt(sodaCount.getText().toString());
	}
	
	private int getBeerCount(){
		return Integer.parseInt(beerCount.getText().toString());
	}

}
