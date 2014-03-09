package com.systematic.android.bartender;

public class Bartender {

	private int beerCount;
	private int sodaCount;

	public Bartender() {
		this.beerCount = 0;
		this.sodaCount = 0;
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

}
