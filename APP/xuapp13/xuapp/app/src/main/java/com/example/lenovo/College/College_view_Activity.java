package com.example.lenovo.College;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.lenovo.R;
import com.example.lenovo.utils.myWbviewClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class College_view_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static final String SMS_ACTION = "com.example.lenovo.message.RESULT";

    private WebView college_new_video;

    //	private TextView message;
    private Button snd;
    private EditText tel;
    private EditText txt;
    private SentReceiver receiver = new SentReceiver();
    private class SentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_ACTION)) {
                int code = getResultCode();
                //短消息发送成功
                if(code == Activity.RESULT_OK);
                // Toast.makeText(TinySMS.this, R.string.msg_sent,Toast.LENGTH_SHORT).show();
            }
        }
    };
    /** Called when the activity is first created. */

    private GridView gridView;
    private TextView txtView1;
    private  int[] imageId = new int[]{R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img5, R.drawable.img6};//定义并初始化保存图片id的数组
    private String[] title1 = new String[]{"听课","睡觉", "阅读", "听课", "睡觉","听课","睡觉","听课"};//定义并初始化保存说明文字的数组

    private String[] title2 = new String[]{"1730200101\n柯鹏", "1730200101\n潘梦远", "1730200101\n刘宇彤",
            "1730200101\n赖泉英", "1730200101\n丁耀祥", "1730200101\n邓虎","1730200101\n霍炎强", "1730200101\n韩宇臻"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_view_);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS} , 1);

        gridView=findViewById(R.id.view);
        txtView1=findViewById(R.id.title_1);
        ArrayList<Map<String, Object>> listitems;//创建一个list集合
        listitems = new ArrayList<Map<String, Object>>();
        //通过for循环将图片和列表项文字放到Map中，并添加到List集合中
        for (int i = 0; i <8; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mygv_1", imageId[i]);
            map.put("title_1", title1[i]);
            map.put("title_2", title2[i]);
            listitems.add(map);
            SimpleAdapter adapter= new SimpleAdapter(this,
                    listitems, R.layout.layout_grid_item, new String[]{"mygv_1","title_1","title_2"},
                    new int[]{ R.id.mygv_1,R.id.title_1,R.id.title_2});
            //adapter.getView()
            gridView.setAdapter(adapter);//将适配器与gridView关联
            gridView.setOnItemClickListener(this);

        }

        college_new_video = findViewById(R.id.college_new_video);
        college_new_video.getSettings().setJavaScriptEnabled(true);
        college_new_video.setWebViewClient(new myWbviewClient());
        college_new_video = findViewById(R.id.college_new_video);
        college_new_video.loadUrl("http://192.168.43.242:8080/?action=stream");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(College_view_Activity.this.imageId[position]>-1){
            //Toast.makeText(this,"点击",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(College_view_Activity.this);
            //setIcon()属性可以添加图标
            builder.setTitle("提醒").setMessage("是否发送信息提醒").setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tel=(EditText) findViewById(R.id.EditText01);
                    tel.setText("18236393504");
                    //模拟器之间互发短信
                    txt=(EditText) findViewById(R.id.EditText02);
                    txt.setText("同学，请你注意听讲");
                    String phoneNo = tel.getText().toString();
                    String message = txt.getText().toString();
                    if (phoneNo.length()>0 && message.length()>0){
                        sendSMS(phoneNo, message);
                        Toast.makeText(College_view_Activity.this,"已发出短信提醒",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(College_view_Activity.this,"请重新输入电话号码和短信内容",Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(College_view_Activity.this,"已取消发送",Toast.LENGTH_LONG).show();

                }
            }).show();
        }
    }
    public void sendSMS(String address, String content)

    {
        SmsManager manager = SmsManager.getDefault();
        Intent i = new Intent(SMS_ACTION);
        //生成PendingIntent，当消息发送完成，接收到广播
        PendingIntent sentIntent = PendingIntent.getBroadcast(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        manager.sendTextMessage(address,null,content,sentIntent,null);
    }


}
