package com.systematic.android.bartender;

import java.io.Serializable;
import java.util.Date;

public class Bartab implements Serializable {

	private static final long serialVersionUID = 4631878964182806525L;

	public static final String TAG = "BartabObj";
	
	private long id;
	
	private int beerCount;
	private int sodaCount;
	private String initials;

	private Date createdAt;
	private Date lastEditedAt;
	
	public Bartab() {
		this.beerCount = 0;
		this.sodaCount = 0;
		this.initials = "";
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
	
	public void resetAll(){
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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastEditedAt() {
		return lastEditedAt;
	}

	public void setLastEditedAt(Date lastEditedAt) {
		this.lastEditedAt = lastEditedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	// for display in listview
	@Override
	public String toString() {
		// TODO: i18n
		return this.createdAt.toString() + "\n" + beerCount + " øl, " + sodaCount + " vand";  
	}

}
