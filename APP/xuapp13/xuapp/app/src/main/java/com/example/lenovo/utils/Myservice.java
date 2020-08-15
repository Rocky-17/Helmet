package com.example.lenovo.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class Myservice extends Service {
    public  String TAG = "MyService";
    Handler mHandler = new Handler();
    public static int focus_timer = 2;

    Runnable r = new Runnable() {
        @Override
        public void run() {
            //每隔1s循环执行run方法
            Log.e(TAG, "run"+focus_timer);
            focus_timer++;
            mHandler.postDelayed(this, 1000);
        }
    };

    public int getnumber(int xyz){
        xyz=focus_timer;
        return xyz;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //定义log方法在后台打印
        System.out.println("创建了");
        Log.v("BACKGROUND", "进入了service");
        Log.e(TAG, "onCreate() ++++++++++++++++++++++++++++++"+Thread.currentThread().getName());
        mHandler.postDelayed(r, 100);//延时100毫秒
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(r);//取消后台的计数子线程
        focus_timer = 0;
        Log.e(TAG, "onDestroy() +++++++++++++++++++++++++++++");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() ++++++++++++++++++++++++");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
