package com.humbledude.alarmtest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

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
public class PendingIntentCancelTest {

    private static final String TAG = PendingIntentCancelTest.class.getSimpleName();
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
    public void cancel1() throws PendingIntent.CanceledException {
        // pendingIntent 찾아와서 cancel 하면 해당 pendingIntent 를 다시 찾아올 수 없음
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());

        // 이미 send 한담에 cancel 해도 send 한 동작은 실행됨. 다만 FLAG_NO_CREATE 로 찾을수는 없음.
        pi1.send();
        pi.cancel();

        PendingIntent pi2 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        assertNull(pi2);
    }

    @Test(expected = PendingIntent.CanceledException.class)
    public void cancel2() throws PendingIntent.CanceledException {
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());
        pi1.cancel();

        // cancel 된 pi 를 보내면 CanceledException 발생
        pi.send();
    }






}
