package com.humbledude.alarmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

public class MainReceiver extends BroadcastReceiver {
    private static final String TAG = MainReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");

    }
}
