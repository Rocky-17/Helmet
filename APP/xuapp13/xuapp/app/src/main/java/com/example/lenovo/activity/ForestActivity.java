package com.example.lenovo.activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.lenovo.R;
import com.example.lenovo.utils.Myservice;

import pl.droidsonroids.gif.GifImageView;

public class ForestActivity extends AppCompatActivity {
    private Chronometer timer;
    private long recordingTime = 0;// 记录下来的总时间
    private Button button1,button2,button3;
    private static String LOG_TAG = "HELLO";
    private int flag = 0;

    private int service_time = 0;

    private GifImageView forest_iv;

    public void onRecordStart() {
        timer.setBase(SystemClock.elapsedRealtime() - recordingTime);
        //跳过已经记录了的时间，起到继续计时的作用
        timer.start();
    }
    public void onRecordPause() {
        timer.stop();
        recordingTime = SystemClock.elapsedRealtime()- timer.getBase();
        // 保存这次记录了的时间
    }
    public void onRecordStop() {
        recordingTime = 0;
        timer.setBase(SystemClock.elapsedRealtime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest);

        timer = this.findViewById(R.id.chronometer);

        //Intent startServiceIntent = new Intent(this,Myservice.class);
        //startService(startServiceIntent);

        button1 = findViewById(R.id.forest_btn1);
        button2 = findViewById(R.id.forest_btn2);
        button3 = findViewById(R.id.forest_btn3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将计时器清零
                //timer.setBase(SystemClock.elapsedRealtime());
                //开始计时
                //timer.start();
                Myservice ser = new Myservice();
                service_time = ser.focus_timer;
                Toast.makeText(ForestActivity.this,""+service_time,Toast.LENGTH_LONG).show();
                //onRecordStart();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止计时显示
                //timer.stop();
                onRecordPause();
                onRecordStart();
                Toast.makeText(ForestActivity.this,""+(recordingTime/1000),Toast.LENGTH_LONG).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForestActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        //变化代码
        if((recordingTime/1000)>10){
            forest_iv = findViewById(R.id.forest_iv);
            forest_iv.setImageDrawable(getResources().getDrawable(R.drawable.tree0));
        }

    }//on

    @Override
    protected void onResume() {
        super.onResume();

        Intent stopServiceIntent = new Intent(ForestActivity.this, Myservice.class);
        stopService(stopServiceIntent);

        onRecordStart();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        flag = 0;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        Intent startServiceIntent = new Intent(this, Myservice.class);
        startService(startServiceIntent);


        onRecordPause();
        Log.v("BACKGROUND", "程序进入后台");
        if(flag!=1) {
            Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.tree1);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent startIntent = new Intent(this, ForestActivity.class);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, startIntent, 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("1 new messages")
                    .setLargeIcon(btm)
                    .setContentText("快切回来界面~认真听课哦")
                    .setNumber(12)
                    .setSmallIcon(R.drawable.tree1)
                    .setContentIntent(pIntent);

            Notification notification = notificationBuilder.build();
            notificationManager.notify(0, notification);
            //震动
            Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0, 1000}, -1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(LOG_TAG, "onKeyDown: keyCode -- " + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.i(LOG_TAG, "KeyEvent.KEYCODE_BACK");
                flag = 1;
                break;
            //return false;//拦截事件
            case KeyEvent.KEYCODE_MENU:
                Log.i(LOG_TAG, "KeyEvent.KEYCODE_MENU");
                break;
            case KeyEvent.KEYCODE_HOME:
                Log.i(LOG_TAG, "KeyEvent.KEYCODE_HOME");
                // 收不到
                break;
            case KeyEvent.KEYCODE_APP_SWITCH:
                Log.i(LOG_TAG, "KeyEvent.KEYCODE_APP_SWITCH");
                // 收不到
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}

