package com.example.shake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    private float accelLast;
    private float accelCurrent;
    private float accel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(mSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        accel = 10f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));

            float delta = accelCurrent - accelLast;
            accel = accel * 0.9f + delta;

            TextView msg;

            if (accel > 12) {
                setContentView(R.layout.fragment_blank);
                // Log.d("sensor", "shake detected with speed: " + accel);
                msg = (TextView) findViewById(R.id.msg);
                msg.setText("shake deected with speed: " + accel);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}