package com.humbledude.alarmtest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;

/**
 * Created by keunhui.park on 2017. 7. 14..
 */

@RunWith(AndroidJUnit4.class)
public class PendingIntentFindTest {

    private static final String TAG = PendingIntentFindTest.class.getSimpleName();
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
    public void pendingIntentTest_FLAG_NO_CREATE_find1() {
        // code 와 intent 가 같으면 pending intent 가져온다.
        // pendingIntent 가 send 가 안되어도 가져온다...
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());
    }

    @Test
    public void pendingIntentTest_FLAG_NO_CREATE_find2() {
        // code 가 다르면 pending intent 못찾는다
        PendingIntent pi2 = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_NO_CREATE);

        assertNull(pi2);
    }

    @Test
    public void pendingIntentTest_FLAG_NO_CREATE_find3() {
        Intent i = new Intent(context, MainReceiver.class);
        assertNotSame(intent, i);
        assertTrue(intent.hashCode() != i.hashCode());

        // intent 오브젝트가 달라도 대상 클래스가 같으면 같은 PendingIntent 찾아온다.
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());
    }

    @Test
    public void pendingIntentTest_FLAG_NO_CREATE_find4() {
        Intent i = new Intent(context, MainReceiver.class);
        i.putExtra("key_test", "value_test234234234");

        // intent 에 다른 extra 가 들어있어도 대상 클래스만 같으면 같은 PendingIntent 찾아온다
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        assertNotNull(pi1);
        assertEquals(pi, pi1);
        assertEquals(pi.hashCode(), pi1.hashCode());

    }


}
