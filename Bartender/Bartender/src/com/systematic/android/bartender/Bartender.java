package com.systematic.android.bartender;

import java.io.Serializable;

public class Bartender implements Serializable {

	private static final long serialVersionUID = 4631878964182806525L;

	public static final String TAG = "BartenderObj";
	
	private int beerCount;
	private int sodaCount;
	private String initials;

	public Bartender() {
		this.beerCount = 0;
		this.sodaCount = 0;
		this.initials = "";
	}
	
	private static class Holder {
		static final Bartender INSTANCE = new Bartender();
	}
	
	public static Bartender getInstance() {
		return Holder.INSTANCE;
	}

	public String getBeerCountAsString() {
		return Integer.toString(beerCount);
	}

	public void addBeer() {
		beerCount++;
	}

	public void removeBeer() {
		if (beerCount > 0) {
			beerCount--;
		}
	}

	public void setBeerCount(int newCount) {
		if (newCount < 0)
			return; // Don't allow negative numbers
		beerCount = newCount;
	}

	public String getSodaCountAsString() {
		return Integer.toString(sodaCount);
	}

	public void addSoda() {
		sodaCount++;
	}

	public void removeSoda() {
		if (sodaCount > 0) {
			sodaCount--;
		}
	}

	public void setSodaCount(int newCount) {
		if (newCount < 0)
			return; // Don't allow negative numbers
		sodaCount = newCount;
	}
	
	public void resetCounts(){
		beerCount = 0;
		sodaCount = 0;
	}

	public String getInitials() {
		return initials;
	}

}
