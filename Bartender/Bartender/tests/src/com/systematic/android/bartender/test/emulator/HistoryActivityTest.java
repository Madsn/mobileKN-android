package com.systematic.android.bartender.test.emulator;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.systematic.android.bartender.activities.EditActivity;
import com.systematic.android.bartender.activities.HistoryActivity;
import com.systematic.android.bartender.data.Bartab;
import com.systematic.android.bartender.data.BartabDataSource;

public class HistoryActivityTest extends
		ActivityInstrumentationTestCase2<HistoryActivity> {

	private HistoryActivity activity;
	private ListAdapter adapter;
	private ListView list;
	private BartabDataSource db;

	public HistoryActivityTest() {
		super(HistoryActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		adapter = activity.getListAdapter();
		getInstrumentation().invokeMenuActionSync(activity,
				com.systematic.android.bartender.R.id.action_delete_all, 0);
		db = new BartabDataSource(activity);
		db.open();
		db.deleteAllBartabs();
	}
	
	@Override
	public void tearDown() throws Exception {
		db.close();
		super.tearDown();
	}

	@UiThreadTest
	public void testClickingItemOpensEditActivity() {
		saveBartabInDb(5, 10, "MIKMA_TEST");

		assertEquals(1, adapter.getCount());

		Instrumentation inst = getInstrumentation();
		ActivityMonitor monitor = inst.addMonitor(EditActivity.class.getName(),
				null, false);
		assertEquals(0, monitor.getHits());

		int itemNumber = 0;
		list = activity.getListView();
		list.performItemClick(list.getChildAt(itemNumber), itemNumber,
				adapter.getItemId(itemNumber));

		inst.waitForMonitorWithTimeout(monitor, 5000);

		assertEquals(1, monitor.getHits());
		inst.removeMonitor(monitor);
	}

	private void saveBartabInDb(int sodaCount, int beerCount, String initials) {
		Bartab tab = new Bartab();
		tab.setBeerCount(beerCount);
		tab.setSodaCount(sodaCount);
		tab.setInitials(initials);
		db.saveBartab(tab);
		activity.initializeAdapter();
	}

}
