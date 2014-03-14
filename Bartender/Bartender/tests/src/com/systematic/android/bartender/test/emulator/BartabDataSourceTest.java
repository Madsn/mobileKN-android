package com.systematic.android.bartender.test.emulator;

import java.util.List;
import java.util.Locale;

import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class BartabDataSourceTest extends AndroidTestCase {
	
	private BartabDataSource db;
	
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		db = new BartabDataSource(context);
		db.open();
//		db.deleteAllBartabs();
	}
	
	public void testAddEntry() {
		Bartab tab = new Bartab(Locale.getDefault());
		tab.setBeerCount(5);
		tab.setSodaCount(10);
		tab.setInitials("MIKMA_TEST");
		db.saveBartab(tab);
		
		List<Bartab> allTabs = db.findAllBartabs();
		assertEquals(1, allTabs.size());
	}
	
	public void tearDown() throws Exception {
		db.close();
		super.tearDown();
	}
	
}
