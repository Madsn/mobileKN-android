package com.noptech.android.realtimetodo.network;

import android.util.Log;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class PollSyncer {

	private static final String TAG = "WSTEST";
	private WebSocketConnection connection;

	public PollSyncer() {
		connection = new WebSocketConnection();
		start();
	}

	private void start() {
		final String wsuri = "ws://10.0.0.10:8080";

		try {
			Log.d(TAG, "Connecting");
			connection.connect(wsuri, new WebSocketHandler() {

				@Override
				public void onOpen() {
					Log.d(TAG, "Status: Connected to " + wsuri);
					connection.sendTextMessage("Hello, world!");
					System.out.println("connected");
				}

				@Override
				public void onTextMessage(String payload) {
					Log.d(TAG, "Got echo: " + payload);
					System.out.println("got message: " + payload);
				}

				@Override
				public void onClose(int code, String reason) {
					Log.d(TAG, "Connection lost.");
				}
			});
			int i = 0;
			while(!connection.isConnected() && i < 30){
				Thread.sleep(500);
				i++;
			}
		} catch (WebSocketException e) {
			Log.d(TAG, "Failed to connect");
			Log.d(TAG, e.toString());
		} catch (InterruptedException e) {
			Log.d(TAG, e.toString());
		}
	}
	
	public WebSocketConnection getConnection(){
		return this.connection;
	}

	public void sendMsg(String msg) {
		Log.d(TAG, "sending: " + msg);
		connection.sendTextMessage(msg);
	}
}
