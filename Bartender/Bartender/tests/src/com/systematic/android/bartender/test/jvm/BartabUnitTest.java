package com.systematic.android.bartender.test.jvm;

import java.util.Locale;

import com.systematic.android.bartender.data.Bartab;


import junit.framework.TestCase;

public class BartabUnitTest extends TestCase {

	private Bartab bartab;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bartab = new Bartab(Locale.getDefault());
	}
	
	private int getBeerCount(){
		return Integer.parseInt(bartab.getBeerCountAsString());
	}
	
	private int getSodaCount(){
		return Integer.parseInt(bartab.getSodaCountAsString());
	}

	public void testAddBeers() {
		assertEquals(getBeerCount(), 0);
		bartab.addBeer();
		bartab.addBeer();
		assertEquals(getBeerCount(), 2);
	}
	
	public void testRemoveBeersNeverGoesNegative() {
		bartab.setBeerCount(0);
		assertEquals(0, getBeerCount());
		bartab.removeBeer();
		bartab.removeBeer();
		assertEquals(0, getBeerCount());
		bartab.setBeerCount(-5);
		assertEquals(0, getBeerCount());
	}
	
	public void testAddSodas() {
		assertEquals(0, getSodaCount());
		bartab.addSoda();
		bartab.addSoda();
		assertEquals(2, getSodaCount());
	}
	
	public void testRemoveSodasNeverGoesNegative() {
		bartab.setSodaCount(0);
		assertEquals(0, getSodaCount());
		bartab.removeSoda();
		bartab.removeSoda();
		assertEquals(0, getSodaCount(), 0);
		bartab.setSodaCount(-10);
		assertEquals(0, getSodaCount(), 0);
	}

}
