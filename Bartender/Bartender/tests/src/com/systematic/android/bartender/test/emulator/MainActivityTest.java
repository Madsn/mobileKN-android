package com.systematic.android.bartender.test.emulator;


import com.systematic.android.bartender.MainActivity;
import com.systematic.android.bartender.R;

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
		activity = getActivity();
		addBeerBtn = (Button) activity.findViewById(R.id.beer_plus_btn);
		minusBeerBtn = (Button) activity.findViewById(R.id.beer_minus_btn);
		addSodaBtn = (Button) activity.findViewById(R.id.soda_plus_btn);
		minusSodaBtn = (Button) activity.findViewById(R.id.soda_minus_btn);
		initialsText = (EditText) activity.findViewById(R.id.initials_edittext);
		saveBtn = (Button) activity.findViewById(R.id.save_btn);
		beerCount = (TextView) activity.findViewById(R.id.beer_count);
		sodaCount = (TextView) activity.findViewById(R.id.soda_count);
		activity.reset();
	}
	
	private int getSodaCount(){
		return Integer.parseInt(sodaCount.getText().toString());
	}
	
	private int getBeerCount(){
		return Integer.parseInt(beerCount.getText().toString());
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
	public void IGNOREtestCountersKeptOnOrientationChange(){
		multipleClicks(addBeerBtn, 5);
		
		assertEquals("Before rotating", 5, getBeerCount());
		assertEquals("Before rotating", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, activity.getResources().getConfiguration().orientation);
		
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		assertEquals("After rotating to landscape", 5, getBeerCount());
		assertEquals("After rotating to landscape", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, activity.getResources().getConfiguration().orientation);
		
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		assertEquals("After rotating back to portrait", 5, getBeerCount());
		assertEquals("After rotating back to portrait", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, activity.getResources().getConfiguration().orientation);
	}

	private void multipleClicks(Button button, int i) {
		for (;i>0;i--){
			button.performClick();
		}
	}

}
