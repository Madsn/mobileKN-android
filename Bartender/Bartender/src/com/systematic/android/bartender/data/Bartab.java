package com.systematic.android.bartender.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class Bartab implements Serializable {

	private static final long serialVersionUID = 4631878964182806525L;

	public static final String TAG = "BartabObj";

	protected Long id;

	protected int beerCount;
	protected int sodaCount;
	protected String initials;

	protected Date createdAt;
	protected Date lastEditedAt;
	protected DateFormat mediumDf;
	
	public Bartab() {
		this(Locale.getDefault());
	}
	
	public Bartab(Locale locale) {
		this.beerCount = 0;
		this.sodaCount = 0;
		this.initials = "";
		mediumDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM, locale);
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

	public void resetAll() {
		beerCount = 0;
		sodaCount = 0;
		initials = "";
	}

	public String getInitials() {
		return initials;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public String getFormattedCreatedAt() {
		return mediumDf.format(createdAt);
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastEditedAt() {
		return lastEditedAt;
	}

	public void setLastEditedAt(Date lastEditedAt) {
		this.lastEditedAt = lastEditedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// for display in listview
	@Override
	public String toString() {
		// TODO: i18n/get strings from strings.xml
		return mediumDf.format(createdAt) + "\n" + initials + ": \t" + beerCount
				+ " øl \t" + sodaCount + " vand";
	}

	public int getBeerCount() {
		return beerCount;
	}

	public int getSodaCount() {
		return sodaCount;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

}
