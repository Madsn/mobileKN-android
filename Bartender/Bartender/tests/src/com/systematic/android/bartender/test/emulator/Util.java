package com.systematic.android.bartender.test.emulator;

import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;

public class Util {
	
	public static final String INITIALS = "MIKMA_TEST";
	
	public static void saveBartabInDb(int sodaCount, int beerCount, String initials, BartabDataSource db) {
		Bartab tab = new Bartab();
		tab.setBeerCount(beerCount);
		tab.setSodaCount(sodaCount);
		tab.setInitials(initials);
		db.saveBartab(tab);
	}

}
