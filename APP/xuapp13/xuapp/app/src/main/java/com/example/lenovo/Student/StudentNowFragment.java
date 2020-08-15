package com.example.lenovo.Student;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lenovo.R;
import com.example.lenovo.activity.ForestActivity;
import com.example.lenovo.activity.HistoryActivity;
import com.example.lenovo.activity.TeacherMainActivity;
import com.example.lenovo.bean.DataForNetMessage;
import com.example.lenovo.bean.User;
import com.example.lenovo.utils.DbMananger;
import com.example.lenovo.utils.Mysqlitehelper;
import com.get.demo.ochelper.OCHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StudentNowFragment extends Fragment {

    //以下为定义显示参数
    private int id;
    private int sign;
    private int focus;
    private int face;
    private String course;
    private int time;

    private int image_index;

    private int xincanshu;

    private int index;
    private MyRunnable myRunnable=new MyRunnable();

    private Mysqlitehelper helper;
    private Timer timer;
    private TimerTask timerTask;
    private static final int PERIOD = 3000;
    private static final String TAG = "SeatActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //SeatItem seatItem=null;
            if(msg.obj.equals("w6OYeemqsWJC4Ila_P0CqNy_eZ4a")){
                //seatItem= seatItemsMap.get(101);
            }else if(msg.obj.equals("QO7fJXSK8Z4aLV9KjUVdk996DHka")){
                //seatItem= seatItemsMap.get(102);
            }

            /////////////////更新UI代码/////////////////
            if (msg.what == 0x01) {
                    TextView textView1 = getActivity().findViewById(R.id.textView4);
                    textView1.setText("id:"+id+" 签到："+sign+" 专注度："+focus+" 表情:"+face+" 课名："+course+" 课时："+time);
                    TextView textView2 = getActivity().findViewById(R.id.textView2);
                    textView2.setText("id:"+id+" 签到："+xincanshu+" 专注度："+focus+" 表情:"+face);
                Log.i(TAG,"进入HandlerMessage1");

                stu_classtime = getActivity().findViewById(R.id.stu_classtime);
                stu_classtime.setText(getCenterText_classtime());
                stu_focustime = getActivity().findViewById(R.id.stu_focustime);
                stu_focustime.setText(getCenterText_focustime());
                stu_score = getActivity().findViewById(R.id.stu_score);
                stu_score.setText(getCenterText_score());
                stu_rank = getActivity().findViewById(R.id.stu_rank);
                stu_rank.setText(getCenterText_rank());

                image_index = time / 1000000;
                ImageChange();

            } else if (msg.what == 0x00) {
                Log.i(TAG,"进入HandlerMessage2");
            }
            /////////////////////////////////////////////////

        }
    };

    private TextView stu_classtime;
    private TextView stu_focustime;
    private TextView stu_score;
    private TextView stu_rank;

    private TextView stu_sign;

    private Button stu_history_btn;
    private Button stu_forest_button;
    private Button stu_home_btn;

    //以下为部分图片、数据变化区域
    private ImageView stu_iv1;
    private ImageView stu_iv2;
    private void ImageChange(){
        stu_iv1 = getActivity().findViewById(R.id.stu_iv1);
        stu_iv2 = getActivity().findViewById(R.id.stu_iv2);
        stu_sign = getActivity().findViewById(R.id.stu_sign);
        if(image_index == 11) {
            stu_sign.setText("签到");
            stu_iv2.setImageDrawable(getResources().getDrawable(R.drawable.emotion_smile));
        }
        if(image_index == 12) {
            stu_sign.setText("签到");
            stu_iv2.setImageDrawable(getResources().getDrawable(R.drawable.emotion_neutral));
        }
        if(image_index == 13) {
            stu_sign.setText("签到");
            stu_iv2.setImageDrawable(getResources().getDrawable(R.drawable.emotion_sad));
        }
        if(image_index == 14) {
            stu_sign.setText("签到");
            stu_iv2.setImageDrawable(getResources().getDrawable(R.drawable.emotion_angry));
        }
    }

    private CharSequence getCenterText_focustime() {
        CharSequence centerText = "" + 90 + "分钟";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new AbsoluteSizeSpan(32, true), 0, centerText.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private CharSequence getCenterText_classtime() {
        CharSequence centerText = "" + 100 + "分钟";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new AbsoluteSizeSpan(32, true), 0, centerText.length()-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private CharSequence getCenterText_score() {
        CharSequence centerText = "" + 87 + "分";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new AbsoluteSizeSpan(32, true), 0, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public CharSequence getCenterText_rank() {
        CharSequence centerText = "" + 12 + "名";
        SpannableString spannableString = new SpannableString(centerText);
        //spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//中间内部文字颜色（前3个字）包左不包右
        spannableString.setSpan(new AbsoluteSizeSpan(32, true), 0, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_student_fragment, container, false);

    }

    //方法二：Runnable
    class MyRunnable implements Runnable{
        @Override
        public void run() {
            doGetMessage();
            index++;
            index=index%3;
            SQLiteDatabase db2=helper.getWritableDatabase();
            Cursor cursor = db2.query ("shiyan",null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                cursor.moveToLast();
                //cursor.move(0);
                id = cursor.getInt(0);
                sign = cursor.getInt(1);
                focus = cursor.getInt(2);
                face = cursor.getInt(3);
                course = cursor.getString(4);
                time = cursor.getInt(5);
            }
            db2.close();
            TextView textView1 = getActivity().findViewById(R.id.textView4);
            textView1.setText("id:"+id+" 签到："+sign+" 专注度："+focus+" 表情:"+face+" 课名："+course+" 课时："+time);
            TextView textView2 = getActivity().findViewById(R.id.textView2);
            textView2.setText(""+2*index);
            handler.postDelayed(myRunnable,3000);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        helper=DbMananger.getIntance(getActivity());
        //handler.postDelayed(myRunnable,3000);//采用方法二：Handler - Runnable更新界面


        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                doGetMessage();
            }
        };
        timer.schedule(timerTask, 0, PERIOD);

        stu_history_btn = getActivity().findViewById(R.id.stu_history_btn);
        stu_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HistoryActivity.class);
                startActivity(intent);
            }
        });

        stu_forest_button = getActivity().findViewById(R.id.stu_forest_button);
        stu_forest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ForestActivity.class);
                startActivity(intent);
            }
        });

        stu_home_btn = getActivity().findViewById(R.id.stu_home_btn);
        stu_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TeacherMainActivity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void doGetMessage() {
        getMessage(new GetMessageCallBack() {
            @Override
            public void onSuccess(DataForNetMessage dataForNetMessage) {
                Log.d(TAG, "onSuccess: ");
                switch (dataForNetMessage.getType()) {
                    case DataForNetMessage.YL:
                        YaLi(dataForNetMessage.getYaLi());
                        int YaLi = dataForNetMessage.getYaLi();
                        Message msg = new Message();
                        msg.obj=dataForNetMessage.getAppId();
                        if (YaLi > 10000 ) {
                            Log.i(TAG,"进入if");
                            msg.what = 0x01;
                            handler.sendMessage(msg);
                            //以下是存入数据库代码
                            SQLiteDatabase db=helper.getWritableDatabase();
                            String sql="insert into shiyan values(null,1,2,2,'JAVA',"+YaLi+")";
                            db.execSQL(sql);

                            //以下是3.7实验代码
                            //String sql2 = "SELECT AVG(time) from shiyan where face = 2";
                            //db.execSQL(sql2);
                           /* SQLiteDatabase db2=helper.getReadableDatabase();
                            Cursor cursor2 = db2.query ("shiyan",null,"time = 12522",null,null,null,null);;
                            cursor2.moveToFirst();
                            xincanshu = cursor2.getInt(0);
                            cursor2.close();
                            db2.close();*/

                            //以下数据库读取代码
                            //SQLiteDatabase db=helper.getReadableDatabase();
                            Cursor cursor = db.query ("shiyan",null,null,null,null,null,null);
//                            int count = cursor.getCount();
//                            TextView textView2 = getActivity().findViewById(R.id.textView5);
//                            textView2.setText(""+count);
                            if(cursor.moveToFirst()){
                                cursor.moveToLast();
                                //cursor.move(0);
                                id = cursor.getInt(0);
                                sign = cursor.getInt(1);
                                focus = cursor.getInt(2);
                                face = cursor.getInt(3);
                                course = cursor.getString(4);
                                time = cursor.getInt(5);
                                //以下改变文本
                                //TextView textView = getActivity().findViewById(R.id.textView4);
                                //textView.setText("id:"+id+" 签到："+sign+" 专注度："+focus+" 表情:"+face+" 课名："+course+" 课时："+time);
                            }
                            cursor.close();
                            db.close();

                        } else {
                            msg.what = 0x00;
                            handler.sendMessage(msg);
                            Log.i(TAG,"进入else");
                        }
                        break;
                }
            }

            @Override
            public void onFail(String tip) {

            }
        });
    }

    private void getMessage(final GetMessageCallBack callBack) {
        List<User> userList=User.getUserList();
        for (User user:userList){
            OCHttpClient builder = new OCHttpClient.Builder()
                    .appId(user.getAppId())
                    .ip(user.getIp())
                    .secret(user.getSecret())
                    .port(user.getPort())
                    .activity(getActivity())
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




}//fragment
