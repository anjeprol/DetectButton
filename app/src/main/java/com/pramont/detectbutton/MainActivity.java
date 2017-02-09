package com.pramont.detectbutton;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private int timesDown = 0;
    private int timesUp = 0;
    private TextView tv_message;
    private Vibrator vibrator;
    private final static String TAG = "DetectButton";
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG,"OnCreate");
        setContentView(R.layout.activity_main);

        tv_message = (TextView) findViewById(R.id.tv_meesage);
        //ShakeDetector initialization
        sensorManager       = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Log.d(TAG,"Sacudido:"+count);
                if(count == 2) {
                    sendUIMessage("Encendido");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector,sensorAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                timesDown++;
               // Toast.makeText(this, "Vol Down Pressed timesDown(" + timesDown + ")", Toast.LENGTH_SHORT).show();
                if(timesDown==3)
                {
                    timesDown=0;
                    sendUIMessage("Encendido");
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                timesUp++;
               // Toast.makeText(this, "Vol Up Pressed timesUp(" + timesUp + ")", Toast.LENGTH_SHORT).show();
                if(timesUp == 3)
                {
                    timesUp=0;
                    sendUIMessage("Apagado");
                }
                break;
        }
        return true;
    }

    private void sendUIMessage(String status){
        tv_message.setText("Modo Panico: ["+status+"]");
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vibrator.vibrate(500);
    }
}
