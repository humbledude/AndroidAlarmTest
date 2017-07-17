package com.humbledude.alarmtest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

@RunWith(AndroidJUnit4.class)
public class PendingIntentUpdateTest {

    private static final String TAG = PendingIntentUpdateTest.class.getSimpleName();
    private Context context;
    private Intent intent;
    private PendingIntent pi;


    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        intent = new Intent(context, MainReceiver.class);
        intent.putExtra("key_test", "value_test");

        pi = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Test
    public void update_extra1() {
        intent.putExtra("key_test", "extra value is changed");

        // 기존 intent 에 extra 만 바꿔서 UPDATE 했는데, 잘됨.
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());
    }

    @Test
    public void update_extra2() {
        Intent intent2 = new Intent(context, MainReceiver.class);
        intent2.putExtra("key_test", "extra value is changed");

        // intent instance 를 새로 만들어서 extra 만 바꿔서 UPDATE 했는데, 잘됨.
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());
    }

    @Test
    public void update_extra3() {
        Intent intent3 = new Intent(context, MainReceiver.class);
        intent3.putExtra("key_test", "extra value is changed");

        // CANCEL 은 extra가 업데이트된 PI 자체를 새로 만드는 거임.
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent3, PendingIntent.FLAG_CANCEL_CURRENT);

        assertNotNull(pi1);
        assertNotSame(pi, pi1);
        assertNotSame(pi.hashCode(), pi1.hashCode());
    }

}
