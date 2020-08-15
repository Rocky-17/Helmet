package com.example.lenovo.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lenovo.One.Datapoints;
import com.example.lenovo.One.Datastreams;
import com.example.lenovo.One.JsonRootBean;
import com.example.lenovo.R;
import com.example.lenovo.activity.Teacher_ListActivity;
import com.example.lenovo.utils.myWbviewClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Teacher_fragment1 extends Fragment {

    private Button teacher_bottom_btn;
    private TextView teacher_sign,teacher_queqin,teacher_focus;
    private WebView teacher_video2;

    private static final String DeviceID = "526229356";
    private static final String ApiKey = "AK1CqjNXV0hNSzbilKJKc2epSIQ=";
    private static final String shumditity="number";//onenet平台上对应设备的其中一个数据流的名字

    private Timer timer;
    private TimerTask timerTask;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 1:
                    teacher_sign = getActivity().findViewById(R.id.teacher_sign);
                    teacher_sign.setText("签到人数："+msg.getData().getString("value")+"人");
                    teacher_queqin = getActivity().findViewById(R.id.teacher_queqin);
                    int queqin = 47 -  Integer.parseInt(msg.getData().getString("value"));
                    teacher_queqin.setText("缺勤人数："+queqin+"人");
                case 2:
                    teacher_focus = getActivity().findViewById(R.id.teacher_focus);
                    teacher_focus.setText("专注人数："+msg.getData().getString("value")+"人");
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.teacher_fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teacher_bottom_btn = getActivity().findViewById(R.id.teacher_bottom_btn);
        teacher_bottom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Teacher_ListActivity.class);
                startActivity(intent);
            }
        });

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getvalue();
                getvalue2();
            }
        };
        timer.schedule(timerTask, 0, 3000);

        teacher_video2 = getActivity().findViewById(R.id.teacher_video2);
        teacher_video2.getSettings().setJavaScriptEnabled(true);
        teacher_video2.setWebViewClient(new myWbviewClient());
        teacher_video2.loadUrl("http://192.168.43.242:8080/?action=stream");

    }//ona

    public void getvalue(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=" + shumditity).header("api-key", ApiKey).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            parseJSONWithGSON(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getvalue2(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=" + "number2").header("api-key", ApiKey).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            parseJSONWithGSON2(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData) {
        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        List<Datapoints> points = streams.get(0).getDatapoints();
        int count = app.getData().getCount();//获取数据的数量
        for (int i = 0; i < points.size(); i++) {
            String time = points.get(i).getAt();
            String value = points.get(i).getValue();
            Log.w("www","time="+time);
            Log.w("www","value="+value);
            Message message=new Message();
            message.what=1;
            Bundle bundle=new Bundle();
            bundle.putString("value",value);
            message.setData(bundle);
            handler.sendMessage(message);

        }
    }

    private void parseJSONWithGSON2(String jsonData) {
        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        List<Datapoints> points = streams.get(0).getDatapoints();
        int count = app.getData().getCount();//获取数据的数量
        for (int i = 0; i < points.size(); i++) {
            String time = points.get(i).getAt();
            String value = points.get(i).getValue();
            Log.w("www","time="+time);
            Log.w("www","value="+value);
            Message message=new Message();
            message.what=2;
            Bundle bundle=new Bundle();
            bundle.putString("value",value);
            message.setData(bundle);
            handler.sendMessage(message);

        }
    }


}
