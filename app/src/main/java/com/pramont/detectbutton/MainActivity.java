package com.pramont.detectbutton;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private int timesDown = 0;
    private int timesUp = 0;
    private TextView tv_message;
    private Vibrator vibrator;
    private static final String TAG = "DetectButton";
    
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate");
        setContentView(R.layout.activity_main);
        tv_message = (TextView) findViewById(R.id.tv_meesage);
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
                    tv_message.setText("Modo Panico: [Encendido]");
                    vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(500);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                timesUp++;
               // Toast.makeText(this, "Vol Up Pressed timesUp(" + timesUp + ")", Toast.LENGTH_SHORT).show();
                if(timesUp == 3)
                {
                    timesUp=0;
                    tv_message.setText("Modo Panico: [Apagado]");
                    vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(500);
                }
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
