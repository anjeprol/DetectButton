package com.pramont.detectbutton.Services;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.pramont.detectbutton.Events.ShakeDetector;


public class Service extends android.app.Service {

    public int count = 0;
    public static final String TAG = "Background";
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private ShakeDetector shakeDetector;
    private Vibrator vibrator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Running in background " + count, Toast.LENGTH_LONG).show();
        for (int i = 1; i < 500000; i++)
        {
            count++;
        }

        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Log.d(TAG, "Sacudido:" + count);
                long[] ms = { 0, 250, 500, 250, 500, 250, 500};

                if (count == 2)
                {
                    startService(new Intent(getBaseContext(), Service.class));
                    vibrator = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate
                    vibrator.vibrate(ms,-1);
                }

            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Stopping in background " + count, Toast.LENGTH_LONG).show();
        sensorManager.unregisterListener(shakeDetector);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //ShakeDetector initialization
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        sensorManager.registerListener(shakeDetector, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
}
