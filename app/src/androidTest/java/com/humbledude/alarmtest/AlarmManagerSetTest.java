package com.humbledude.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;
import android.util.Log;

import dalvik.annotation.TestTarget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

@RunWith(AndroidJUnit4.class)
public class AlarmManagerSetTest {
    private static final String TAG = AlarmManagerSetTest.class.getSimpleName();
    private Context context;
    private AlarmManager mAlarmManager;


    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void test1() throws InterruptedException {
        Map<String, Object> checkMap = new HashMap<>();

        Intent intent = new Intent(context, MainReceiver.class);

        intent.putExtra("key_test", "value_test0");
        long triggerTime = System.currentTimeMillis() + 30 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        checkMap.put("value_test0", false);

        intent.putExtra("key_test", "value_test1");
        long triggerTime1 = System.currentTimeMillis() + 60 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 1, intent, 0);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime1, pi1);
        checkMap.put("value_test1", false);

        intent.putExtra("key_test", "value_test2");
        long triggerTime2 = System.currentTimeMillis() + 90 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi2 = PendingIntent.getBroadcast(context, 2, intent, 0);
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime2, pi2);
        checkMap.put("value_test2", false);

        boolean clear = false;
        long count = 0;
        do {
            if (MainReceiver.getInstance() != null) {
                if (MainReceiver.getInstance().valueSet.contains("value_test0")) {
                    if (!(Boolean)checkMap.get("value_test0")) {
                        Log.i(TAG, "value_test0 received : " + count/1000L);
                        checkMap.put("value_test0", true);
                    }
                }
                if (MainReceiver.getInstance().valueSet.contains("value_test1")) {
                    if (!(Boolean)checkMap.get("value_test1")) {
                        Log.i(TAG, "value_test1 received : " + count/1000L);
                        checkMap.put("value_test1", true);
                    }
                }
                if (MainReceiver.getInstance().valueSet.contains("value_test2")) {
                    if (!(Boolean)checkMap.get("value_test2")) {
                        Log.i(TAG, "value_test2 received : " + count/1000L);
                        checkMap.put("value_test2", true);
                    }
                }

                clear = (Boolean)checkMap.get("value_test0")
                    && (Boolean)checkMap.get("value_test1")
                    && (Boolean)checkMap.get("value_test2");
            }

            Thread.sleep(1000L);
            count += 1000L;
        } while (!clear);

        Log.e(TAG, "it took " + count/1000L + " sec");

    }


}
