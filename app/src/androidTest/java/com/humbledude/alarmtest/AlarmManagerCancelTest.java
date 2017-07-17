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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

@RunWith(AndroidJUnit4.class)
public class AlarmManagerCancelTest {
    private static final String TAG = AlarmManagerCancelTest.class.getSimpleName();

    private static final String TEST = "value_test";
    private Context context;
    private AlarmManager mAlarmManager;
    private Intent intent;
    private Map<String, Object> checkMap;
    private SimpleDateFormat format;


    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        intent = new Intent(context, MainReceiver.class);
        checkMap = new HashMap<>();
        format = new SimpleDateFormat("yyyyMMdd HHmmss");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void test1() throws InterruptedException {
        setAlarm(0, 30);
        setAlarm(1, 60);
        setAlarm(2, 90);

        // 알람을 취소할때는 AlarmManager.cancel() 만 하면 됨.
        cancelAlarm(0);
        cancelAlarm(1);
        cancelAlarm(2);


        boolean clear = false;
        long count = 0;
        long maxCount = 100 * DateUtils.SECOND_IN_MILLIS;
        do {
            clear = checkAlarm(0, count)
                && checkAlarm(1, count)
                && checkAlarm(2, count);

            Thread.sleep(1000L);
            count += 1000L;
            if (maxCount < count) {
                break;
            }
        } while (!clear);

        Log.e(TAG, "it took " + count/1000L + " sec");

        assertFalse(clear);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(int code, int secLater) {
        intent.putExtra("key_test", TEST + code);
        long triggerTime = System.currentTimeMillis() + secLater * DateUtils.SECOND_IN_MILLIS;
        Log.i(TAG, "test" + code + " : " + triggerTime + " /  "  + format.format(new Date(triggerTime)));
        PendingIntent pi = PendingIntent.getBroadcast(context, code, intent, 0);
        //        mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        //        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        checkMap.put(TEST + code, false);
    }

    private void cancelAlarm(int code) {
        // extra 는 PI 를 가져올때 고려 대상이 아님
//        intent.putExtra("key_test", TEST + code);
        PendingIntent pi = PendingIntent.getBroadcast(context, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(pi);
    }


    private boolean checkAlarm(int code, long count) {
        if (MainReceiver.valueSet.contains(TEST + code)) {
            if (!(Boolean) checkMap.get(TEST + code)) {
                Log.i(TAG, TEST + code + " received : " + count / 1000L);
                checkMap.put(TEST + code, true);
            }
        }

        return (Boolean) checkMap.get(TEST + code);
    }
}
