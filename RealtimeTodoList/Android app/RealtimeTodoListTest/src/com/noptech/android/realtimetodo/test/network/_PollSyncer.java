package com.noptech.android.realtimetodo.test.network;

import com.noptech.android.realtimetodo.network.PollSyncer;

import de.tavendo.autobahn.WebSocketConnection;

import android.test.AndroidTestCase;

public class _PollSyncer extends AndroidTestCase {

	@Override
	public void setUp() throws Exception{
		super.setUp();
	}
	
	public void testConnecting() throws InterruptedException{
		PollSyncer syncer = new PollSyncer();
		WebSocketConnection con = syncer.getConnection();
		syncer.sendMsg("hello");
		syncer.sendMsg("hello2");
		assertTrue(con.isConnected());
	}
}
