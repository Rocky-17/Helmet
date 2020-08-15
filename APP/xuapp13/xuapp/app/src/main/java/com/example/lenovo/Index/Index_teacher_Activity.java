package com.example.lenovo.Index;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.R;
import com.example.lenovo.activity.TeacherMainActivity;
import com.example.lenovo.utils.ImageBannerView;

public class Index_teacher_Activity extends Activity {

    private static String name,passwd;
    private EditText editText1,editText2;
    private Button button;
    String na=null;
    String pasw=null;
    private TextView textView1,textView2;

    private ImageBannerView myGroup;
    private int[] ids=new int []{
            R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_teacher_);

        editText1 = findViewById(R.id.tea_edit1);
        editText2 = findViewById(R.id.tea_edit2);
        button = findViewById(R.id.btn_tea_signup);
        textView1 = findViewById(R.id.txt_tea_register);
        textView2 = findViewById(R.id.txt_tea_coll);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = new String(editText1.getText().toString());
                passwd = new String(editText2.getText().toString());
                if (name.isEmpty() || passwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "登录信息不可为空", Toast.LENGTH_SHORT).show();
                } else if ((na.equals(name)) && (pasw.equals(passwd))) {
                    Intent intent = new Intent(Index_teacher_Activity.this, TeacherMainActivity.class);//跳转至学院端
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                na = new String(editText1.getText().toString());
                pasw = new String(editText2.getText().toString());
                if (na.isEmpty() || pasw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "注册信息不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index_teacher_Activity.this, Index_college_Activity.class);
                startActivity(intent);
            }
        });


        //计算当前手机宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        myGroup = findViewById(R.id.image_group);

        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(this);

            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setImageResource(ids[i]);
            myGroup.addView(iv);
        }

    }//on
}
