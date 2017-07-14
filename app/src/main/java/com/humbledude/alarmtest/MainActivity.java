package com.humbledude.alarmtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume()");

        // 보통 케이스
        Intent i1 = new Intent(getApplicationContext(), MainReceiver.class);
        PendingIntent pi1 = PendingIntent.getBroadcast(getApplicationContext(), 1, i1, 0);
        try {
            pi1.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        // extra 있는 케이스
        Intent i2 = new Intent(getApplicationContext(), MainReceiver.class);
        i2.putExtra("key_test", "test from main activity, i2");
        PendingIntent pi2 = PendingIntent.getBroadcast(getApplicationContext(), 2, i2, 0);
        try {
            pi2.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        // send 후 cancel 하는 케이스
        Intent i3 = new Intent(getApplicationContext(), MainReceiver.class);
        i3.putExtra("key_test", "test from main activity, i3, which is canceled");
        PendingIntent pi3 = PendingIntent.getBroadcast(getApplicationContext(), 3, i3, 0);
        try {
            pi3.send();
            pi3.cancel();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }


        // extra 를 바꾸는 케이스 , send 이후 바꾸는건 동작하지 않는다.
        Intent i4 = new Intent(getApplicationContext(), MainReceiver.class);
        i4.putExtra("key_test", "test from main activity, i4");
        PendingIntent pi4 = PendingIntent.getBroadcast(getApplicationContext(), 4, i4, 0);
        i4.putExtra("key_test", "test from main activity, i4, extra changed");
        PendingIntent pi4_mod = PendingIntent.getBroadcast(getApplicationContext(), 4, i4, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pi4.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

}
