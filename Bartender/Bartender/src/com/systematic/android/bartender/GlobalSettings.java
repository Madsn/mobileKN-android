package com.systematic.android.bartender;

import java.util.Locale;

public class GlobalSettings {

	private final static GlobalSettings instance = new GlobalSettings();
	private Locale locale;

	private GlobalSettings() {

	}

	public static GlobalSettings getInstance() {
		return instance;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}

}
