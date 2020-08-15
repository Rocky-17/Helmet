package com.example.lenovo.activity;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.Student.KebiaoFragment;
import com.example.lenovo.Student.MessageFragment;
import com.example.lenovo.Student.MyselfFragment;
import com.example.lenovo.Student.StudentNowFragment;
import com.example.lenovo.bean.DataForNetMessage;
import com.example.lenovo.bean.Seat;
import com.example.lenovo.bean.User;
import com.example.lenovo.utils.BottomBar;
import com.example.lenovo.R;
import com.example.lenovo.utils.DbMananger;
import com.example.lenovo.utils.Mysqlitehelper;
import com.get.demo.ochelper.OCHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageActivity extends AppCompatActivity {

    private Mysqlitehelper helper;

    private TextView tvAreaName;
    private ImageView start;
    private GridLayout gridLayout;
    private Timer timer;
    private TimerTask timerTask;
    private static final int PERIOD = 3000;
    private static final String TAG = "SeatActivity";
    private Intent intent;
    private int areaId;
    private String areaName;
    private static int bgColor;
    private static List<Seat> list;
    public static int licheng = 0;

//    private static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            SeatItem seatItem=null;
//            if(msg.obj.equals("w6OYeemqsWJC4Ila_P0CqNy_eZ4a")){
//                //seatItem= seatItemsMap.get(101);
//            }else if(msg.obj.equals("QO7fJXSK8Z4aLV9KjUVdk996DHka")){
//                //seatItem= seatItemsMap.get(102);
//            }
//            if(seatItem!=null) {
//                if (msg.what == 0x01) {
//                    seatItem.setAvailable(false);
//
//                } else if (msg.what == 0x00) {
//                    seatItem.setAvailable(true);
//                }
////                seatItemsMap.remove(101);
////                seatItemsMap.put(101, seatItem);
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        helper=DbMananger.getIntance(this);


        //intent = getIntent();
        //areaId = intent.getIntExtra("areaId", 0);
        //areaName = intent.getStringExtra("areaName");
        //bgColor = R.drawable.shape_bt;

        //start = findViewById(R.id.button26);
        //tvAreaName = findViewById(R.id.seat_area_name);
        //gridLayout = findViewById(R.id.seat_grid);

        //list = Seat.getSeatList(areaId);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //doGetMessage();
            }
        };

        //tvAreaName.setText(areaName);
        timer.schedule(timerTask, 0, PERIOD);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#505050", "#5bbce7")
                .addItem(StudentNowFragment.class,
                        "首页",
                        R.mipmap.home1,
                        R.mipmap.home2)
                .addItem(KebiaoFragment.class,
                        "课程表",
                        R.mipmap.sche1,
                        R.mipmap.sche2)
                .addItem(MessageFragment.class,
                        "消息",
                        R.mipmap.mess1,
                        R.mipmap.mess4)
                .addItem(MyselfFragment.class,
                        "我的",
                        R.mipmap.own1,
                        R.mipmap.own2)
                .build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void doGetMessage() {
        getMessage(new HomePageActivity.GetMessageCallBack() {
            @Override
            public void onSuccess(DataForNetMessage dataForNetMessage) {
                Log.d(TAG, "onSuccess: ");
                switch (dataForNetMessage.getType()) {
                    case DataForNetMessage.YL:
                        HomePageActivity.this.YaLi(dataForNetMessage.getYaLi());
                        int YaLi = dataForNetMessage.getYaLi();
                        Message msg = new Message();
                        msg.obj=dataForNetMessage.getAppId();
                        if (YaLi > 10000 && YaLi <20000) {
                            msg.what = 0x01;
                            //handler.sendMessage(msg);
                            //以下写入数据库存储代码
                            SQLiteDatabase db=helper.getWritableDatabase();
                            String sql="insert into shiyan values(null,1,2,2,'计网',"+YaLi+")";
                            db.execSQL(sql);
                            db.close();

                        } else {
                            msg.what = 0x00;
                           //handler.sendMessage(msg);
                        }
                        break;
                }
            }

            @Override
            public void onFail(String tip) {

            }
        });
    }

    private void getMessage(final HomePageActivity.GetMessageCallBack callBack) {
        List<User> userList=User.getUserList();
        for (User user:userList){
            OCHttpClient builder = new OCHttpClient.Builder()
                    .appId(user.getAppId())
                    .ip(user.getIp())
                    .secret(user.getSecret())
                    .port(user.getPort())
                    .activity(HomePageActivity.this)
                    .build();
            builder.getWhatIWant(new OCHttpClient.GetWhatIWantCallBack() {

                @Override
                public void onSuccess(String s) {
                    Log.i("json",s);
                    try {
                        JSONObject resJson = new JSONObject(s);
                        JSONArray histories = resJson.getJSONArray("deviceDataHistoryDTOs");
                        JSONObject latestDeviceHistory = histories.getJSONObject(0);
                        JSONObject data = latestDeviceHistory.getJSONObject("data");
                        int type = -1;
                        DataForNetMessage dataForNetMessage = new DataForNetMessage();

                        while (data.keys().hasNext()) {
                            String key = data.keys().next();
                            Log.d(TAG, "onSuccess....: " + key);
                            if (key.equalsIgnoreCase("HHHH")) {
                                type = DataForNetMessage.YL;
                                dataForNetMessage.setYaLi(data.getInt("hhhh"));
                                dataForNetMessage.setAppId(latestDeviceHistory.getString("appId"));
                                Log.d("404", "dataForNetMessage.getYaLi");
                                break;
                            } else if (key.equals("Temperature")) {
                                type = DataForNetMessage.WSD;
                                dataForNetMessage.setTem(data.getInt("Temperature"));
                                dataForNetMessage.setAppId(latestDeviceHistory.getString("appId"));
                                dataForNetMessage.setHum(data.getInt("Humidity"));
                                break;
                            }
                        }
                        dataForNetMessage.setType(type);
                        callBack.onSuccess(dataForNetMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callBack.onFail("没有历史数据");
                    }

                }

                @Override
                public void onFail(String tip) {

                }
            });

        }
    }

    private interface GetMessageCallBack {
        void onSuccess(DataForNetMessage dataForNetMessage);

        void onFail(String tip);
    }


    private void YaLi(int YaLi) {
        if (YaLi == 1) {
//            Button button = (Button) findViewById(R.id.button10);
//            getResources().getColor(R.color.red);
        }
    }



}
