package com.example.tito.womensecurity.Services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.tito.womensecurity.MainActivity;
import com.example.tito.womensecurity.R;

public class MyService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    int counter;
    public static final int RECORD_AUDIO = 0;
    public static final int WRITE_STORAGE = 1;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 11) {
            counter=counter+1;
                showNotification(counter);


            Toast.makeText(getApplicationContext(),""+counter,Toast.LENGTH_SHORT);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    private void showNotification(int count) {
        if (count > 0 && count % 6 == 0) {
            final NotificationManager mgr = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder note = new NotificationCompat.Builder(this);
            note.setContentTitle("Device Accelerometer Notification");
            note.setTicker("New Message Alert!");
            note.setAutoCancel(true);
            // to set default sound/light/vibrate or all
            note.setDefaults(Notification.DEFAULT_ALL);
            // Icon to be set on Notification
            note.setSmallIcon(R.drawable.ic_launcher_background);
            // This pending intent will open after notification click
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                    MainActivity.class), 0);
            // set pending intent to notification builder
            note.setContentIntent(pi);
            mgr.notify(101, note.build());

                MainActivity mainActivity=new MainActivity();
                mainActivity.startRecording();


       Log.d("tito","notified");
        }
        else
        {
            Log.d("teno","no"+count);
        }
    }
}
