package com.systematic.android.bartender.test.emulator;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.test.UiThreadTest;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.systematic.android.bartender.activities.EditActivity;
import com.systematic.android.bartender.activities.HistoryActivity;
import com.systematic.android.bartender.data.BartabDataSource;

public class HistoryActivityTest extends
		ActivityInstrumentationTestCase2<HistoryActivity> {

	private HistoryActivity activity;
	private ListAdapter adapter;
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
		RenamingDelegatingContext context = new RenamingDelegatingContext(activity, "test_");
		db = new BartabDataSource(context);
		db.open();
		db.deleteAllBartabs();
	}
	
	@Override
	public void tearDown() throws Exception {
		db.close();
		super.tearDown();
	}
	
	@UiThreadTest
	public void testItemsShownInList() {
		assertEquals(0, activity.getListView().getCount());
		saveBartabInDb(5, 5, Util.INITIALS);
		saveBartabInDb(6, 4, Util.INITIALS);
		assertEquals(2, activity.getListView().getCount());
	}

	@UiThreadTest
	public void testClickingItemOpensEditActivity() {
		saveBartabInDb(5, 10, Util.INITIALS);

		Instrumentation inst = getInstrumentation();
		ActivityMonitor monitor = inst.addMonitor(EditActivity.class.getName(),
				null, false);
		assertEquals(0, monitor.getHits());

		int itemNumber = 0;
		ListView list = activity.getListView();
		list.performItemClick(list.getChildAt(itemNumber), itemNumber,
				adapter.getItemId(itemNumber));

		inst.waitForMonitorWithTimeout(monitor, 5000);

		assertEquals(1, monitor.getHits());
		inst.removeMonitor(monitor);
	}

	private void saveBartabInDb(int sodaCount, int beerCount, String initials) {
		Util.saveBartabInDb(sodaCount, beerCount, initials, db);
		activity.initializeAdapter();
	}

}
