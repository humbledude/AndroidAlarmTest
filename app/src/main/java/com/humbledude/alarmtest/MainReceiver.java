package com.humbledude.alarmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

public class MainReceiver extends BroadcastReceiver {
    private static final String TAG = MainReceiver.class.getSimpleName();

    public static Set<String> valueSet = new HashSet<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive : " + intent.toString());

        if (intent.hasExtra("key_test")) {
            String valTest = intent.getStringExtra("key_test");
            Log.e(TAG, "key_test : " + valTest);
            valueSet.add(valTest);
        }
    }
}
