package com.example.lenovo.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.College.College_fragment1;
import com.example.lenovo.College.College_fragment2;
import com.example.lenovo.R;
import com.example.lenovo.Teacher.Teacher_fragment1;
import com.example.lenovo.Teacher.Teacher_fragment2;

public class CollegeActivity extends AppCompatActivity implements View.OnClickListener{
    private College_fragment1 f1;
    private College_fragment2 f2;

    private Button button_college_video;
    private Button button_college_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);

        button_college_video = findViewById(R.id.college_video);
        button_college_data = findViewById(R.id.college_data);
        button_college_video.setOnClickListener(this);//对按钮1设置监听
        button_college_data.setOnClickListener(this);//对按钮2设置监听

        initFragment1();

    }//on

    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if(f1 == null){
            f1 = new College_fragment1();
            transaction.add(R.id.main_frame_layout, f1);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(f1);

        //第二种方式(replace)，初始化fragment//
        //  if(f1 == null){
        //            f1 = new MyFragment("首页");
        //  }
        //  transaction.replace(R.id.main_frame_layout, f1);

        // 提交事务
        transaction.commit();
    }

    //显示第二个fragment
    private void initFragment2(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f2 == null){
            f2 = new College_fragment2();
            transaction.add(R.id.main_frame_layout,f2);
        }
        hideFragment(transaction);
        transaction.show(f2);
        //        if(f2 == null) {
        //            f2 = new MyFragment("分类");
        //        }
        //        transaction.replace(R.id.main_frame_layout, f2);

        transaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(f1 != null){
            transaction.hide(f1);
        }
        if(f2 != null){
            transaction.hide(f2);
        }
    }

    @Override
    public void onClick(View v) {
        //点击哪个按钮就显示哪个fragment;
        if(v == button_college_video){
            initFragment1();
        }
        else if(v == button_college_data){
            initFragment2();
        }
    }

}
