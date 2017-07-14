package com.humbledude.alarmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

public class MainReceiver2 extends BroadcastReceiver {
    private static final String TAG = MainReceiver2.class.getSimpleName();

    public static String valTest = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive : " + intent.toString());

        if (intent.hasExtra("key_test")) {
            valTest = intent.getStringExtra("key_test");
            Log.e(TAG, "key_test : " + valTest);
        }
    }
}
