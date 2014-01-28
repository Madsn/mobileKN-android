package com.noptech.android.phonekiller;

import java.text.MessageFormat;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	SensorManager mSensorManager;
	Sensor mSensor;
	TextView label, label2;
	private float[] gravity = new float[3];
	private float maxX, x, maxY, y, maxZ, z; // linear acceleration
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		label = (TextView) findViewById(R.id.textView1);
		label2 = (TextView) findViewById(R.id.textView2);
		setActivityBackgroundColor(Color.BLUE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// In this example, alpha is calculated as t / (t + dT),
		// where t is the low-pass filter's time-constant and
		// dT is the event delivery rate.
		final float alpha = (float) 0.8;
		// Isolate the force of gravity with the low-pass filter.
		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

		// // Remove the gravity contribution with the high-pass filter.
		x = event.values[0] - gravity[0];
		y = event.values[1] - gravity[1];
		z = event.values[2] - gravity[2];
		if (i < 10) {
			label2.setText("Calibrating...");
			i++;
		} else {
			if (Math.abs(x) > maxX)
				maxX = Math.abs(x);
			if (Math.abs(y) > maxY)
				maxY = Math.abs(y);
			if (Math.abs(z) > maxZ)
				maxZ = Math.abs(z);
			label2.setText(MessageFormat.format(
					"Max X: {0} Max Y: {1} Max Z: {2}", maxX, maxY, maxZ));
		}
		label.setText(MessageFormat.format("x:{0} y:{1} z:{2}", x, y, z));
		if (playerLost()) {
			setActivityBackgroundColor(Color.RED);
		}
	}
	
	public void setActivityBackgroundColor(int color) {
	    View view = this.getWindow().getDecorView();
	    view.setBackgroundColor(color);
	}

	private boolean playerLost() {
		float limit = 1.7f;
		return maxX > limit || maxY > limit || maxZ > limit;
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
