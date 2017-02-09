package com.pramont.detectbutton.Services;


import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class Service extends android.app.Service{

    public int count = 0;
    public static final String TAG = "Backgorund";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Running in background "+count,Toast.LENGTH_LONG).show();
        for (int i = 1 ; i < 500000 ; i++)
        {
            count++;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Stopping in background "+count,Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
