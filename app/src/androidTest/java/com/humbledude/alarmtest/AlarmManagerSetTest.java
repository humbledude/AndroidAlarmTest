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

import dalvik.annotation.TestTarget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void test() throws InterruptedException {
        Intent intent = new Intent(context, MainReceiver.class);

        intent.putExtra("key_test", "value_test0");
        long triggerTime = System.currentTimeMillis() + 30 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pi);

        intent.putExtra("key_test", "value_test1");
        long triggerTime1 = System.currentTimeMillis() + 60 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 1, intent, 0);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime1, pi1);

        intent.putExtra("key_test", "value_test2");
        long triggerTime2 = System.currentTimeMillis() + 90 * DateUtils.SECOND_IN_MILLIS;
        PendingIntent pi2 = PendingIntent.getBroadcast(context, 2, intent, 0);
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime2, pi2);

        Thread.sleep(3 * DateUtils.MINUTE_IN_MILLIS);

    }


}
