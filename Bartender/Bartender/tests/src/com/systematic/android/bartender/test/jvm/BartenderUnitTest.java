package com.systematic.android.bartender.test.jvm;

import junit.framework.TestCase;

import com.systematic.android.bartender.Bartender;
import com.systematic.android.bartender.MainActivity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;

public class BartenderUnitTest extends TestCase {

	private Bartender bartender;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bartender = new Bartender();
	}
	
	private int getBeerCount(){
		return Integer.parseInt(bartender.getBeerCountAsString());
	}
	
	private int getSodaCount(){
		return Integer.parseInt(bartender.getSodaCountAsString());
	}

	public void testAddBeers() {
		assertEquals(getBeerCount(), 0);
		bartender.addBeer();
		bartender.addBeer();
		assertEquals(getBeerCount(), 2);
	}
	
	public void testRemoveBeersNeverGoesNegative() {
		bartender.setBeerCount(0);
		assertEquals(0, getBeerCount());
		bartender.removeBeer();
		bartender.removeBeer();
		assertEquals(0, getBeerCount());
		bartender.setBeerCount(-5);
		assertEquals(0, getBeerCount());
	}
	
	public void testAddSodas() {
		assertEquals(0, getSodaCount());
		bartender.addSoda();
		bartender.addSoda();
		assertEquals(2, getSodaCount());
	}
	
	public void testRemoveSodasNeverGoesNegative() {
		bartender.setSodaCount(0);
		assertEquals(0, getSodaCount());
		bartender.removeSoda();
		bartender.removeSoda();
		assertEquals(0, getSodaCount(), 0);
		bartender.setSodaCount(-10);
		assertEquals(0, getSodaCount(), 0);
	}

}
