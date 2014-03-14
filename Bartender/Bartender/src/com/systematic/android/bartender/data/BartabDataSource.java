package com.systematic.android.bartender.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BartabDataSource {

	public static String TAG = "BartabDataSource";

	private SQLiteDatabase database;
	DateFormat df = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	private DbHelper dbHelper;
	private String[] allColumns = { dbHelper.COLUMN_ID,
			dbHelper.COLUMN_INITIALS, dbHelper.COLUMN_BEERS,
			dbHelper.COLUMN_SODAS, dbHelper.COLUMN_CREATED_AT,
			dbHelper.COLUMN_LAST_EDITED };

	private Locale locale;

	public BartabDataSource(Context context) {
		dbHelper = new DbHelper(context);
		this.locale = context.getResources().getConfiguration().locale;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void saveBartab(Bartab tab) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_BEERS, tab.getBeerCount());
		values.put(dbHelper.COLUMN_SODAS, tab.getSodaCount());
		values.put(dbHelper.COLUMN_INITIALS, tab.getInitials().toLowerCase());
		Date currentTime = new Date();
		if (tab.getCreatedAt() == null) {
			values.put(dbHelper.COLUMN_CREATED_AT, df.format(currentTime));
		} else {
			values.put(dbHelper.COLUMN_CREATED_AT,
					df.format(tab.getCreatedAt()));
		}
		values.put(dbHelper.COLUMN_LAST_EDITED, df.format(currentTime));
		Long id = tab.getId();
		if (id == null) {
			Log.v(TAG, "Inserting new row");
			database.insert(dbHelper.TABLE_BARTABS, null, values);
		} else {
			Log.v(TAG, "Updating existing row for ID: " + id);
			String whereClause = dbHelper.COLUMN_ID + " = " + id.toString();
			database.update(dbHelper.TABLE_BARTABS, values, whereClause, null);
		}
	}

	public void deleteBartab(Bartab tab) {
		Long id = tab.getId();
		Log.d(TAG, "Deleting bartab with ID: " + id);
		if (id != null)
			database.delete(dbHelper.TABLE_BARTABS, dbHelper.COLUMN_ID + " = "
					+ id, null);
		Log.d(TAG, "Deleted bartab with ID: " + id);
	}

	public List<Bartab> findBartabs(String filter, String orderBy) {
		List<Bartab> bartabs = new ArrayList<Bartab>();
		Cursor c = database.query(dbHelper.TABLE_BARTABS, allColumns, null,
				null, dbHelper.COLUMN_ID, filter, orderBy);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Bartab tab = cursorToBartab(c);
			bartabs.add(tab);
			c.moveToNext();
		}
		c.close();
		return bartabs;
	}

	public List<Bartab> findBartabsForUser(String initials) {
		String filter = dbHelper.COLUMN_INITIALS + " = "
				+ initials.toLowerCase();
		return findBartabs(filter, dbHelper.COLUMN_CREATED_AT);
	}

	public List<Bartab> findAllBartabs() {
		return findBartabs(null, null);
	}

	public List<Bartab> findAllBartabsSortedByDate(String direction) {
		String orderBy = dbHelper.COLUMN_CREATED_AT + " " + direction;
		return findBartabs(null, orderBy);
	}

	public Bartab getBartabById(Long id) {
		String filter = dbHelper.COLUMN_ID + " = " + id.toString();
		return findBartabs(filter, dbHelper.COLUMN_ID).get(0);
	}

	private Bartab cursorToBartab(Cursor c) {
		Bartab tab = new Bartab(locale);
		tab.setId(c.getLong(c.getColumnIndex(dbHelper.COLUMN_ID)));
		tab.setBeerCount(c.getInt(c.getColumnIndex(dbHelper.COLUMN_BEERS)));
		tab.setSodaCount(c.getInt(c.getColumnIndex(dbHelper.COLUMN_SODAS)));
		tab.setInitials(c.getString(c.getColumnIndex(dbHelper.COLUMN_INITIALS)));
		String createdAt = c.getString(c
				.getColumnIndex(dbHelper.COLUMN_CREATED_AT));
		String lastEdited = c.getString(c
				.getColumnIndex(dbHelper.COLUMN_LAST_EDITED));
		try {
			tab.setCreatedAt(df.parse(createdAt));
			tab.setLastEditedAt(df.parse(lastEdited));
		} catch (ParseException e) {
			Log.e(TAG, "Failed to parse either created at timestamp: "
					+ createdAt + " or last edited timestamp: " + lastEdited);
		}
		return tab;
	}

	public void deleteAllBartabs() {
		database.delete(dbHelper.TABLE_BARTABS, null, null);
	}

}
