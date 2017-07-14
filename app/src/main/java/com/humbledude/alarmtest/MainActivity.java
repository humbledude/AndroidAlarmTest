package com.humbledude.alarmtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    List<Intent> mIntentList;
    List<PendingIntent> mPendingIntentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntentList = new ArrayList<>();
        mPendingIntentList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume()");

        test_case0();
        test_case1();
        test_case2();
        test_case3();
        test_case4();

    }


    public void test_case0() {
        int index = 0;
        mIntentList.add(index, new Intent(getApplicationContext(), MainReceiver.class));
        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 0");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), 0));

        try {
            mPendingIntentList.get(index).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

    // pendingIntent 에 intent 연결후에 extra 바꾸면 안됨 (update 해야됨)
    public void test_case1() {
        int index = 1;
        mIntentList.add(index, new Intent(getApplicationContext(), MainReceiver.class));
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), 0));
        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 1");

        try {
            mPendingIntentList.get(index).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    // send 후 cancel 하는 케이스
    public void test_case2() {
        int index = 2;
        mIntentList.add(index, new Intent(getApplicationContext(), MainReceiver.class));
        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 2, which is canceled");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), 0));

        try {
            mPendingIntentList.get(index).send();
            mPendingIntentList.get(index).cancel();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    // extra 를 바꾸는 케이스, send 이후 바꾸는건 동작하지 않는다.
    public void test_case3() {
        int index = 3;
        mIntentList.add(index, new Intent(getApplicationContext(), MainReceiver.class));
        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 3");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), 0));

        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 3, extra changed");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), PendingIntent.FLAG_UPDATE_CURRENT));

        try {
            mPendingIntentList.get(index).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    // oneshot의 extra 를 바꾸는 케이스 : 잘동작함
    public void test_case4() {
        int index = 4;
        mIntentList.add(index, new Intent(getApplicationContext(), MainReceiver.class));
        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 4");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), PendingIntent.FLAG_ONE_SHOT));

        mIntentList.get(index).putExtra("key_test", "test from main activity, index = 4, oneshot extra changed");
        mPendingIntentList.add(index, PendingIntent.getBroadcast(getApplicationContext(), index, mIntentList.get(index), PendingIntent.FLAG_UPDATE_CURRENT));

        try {
            mPendingIntentList.get(index).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

}
