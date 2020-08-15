package com.example.lenovo.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.R;
import com.example.lenovo.Teacher.Teacher_fragment1;
import com.example.lenovo.Teacher.Teacher_fragment2;

public class TeacherMainActivity extends AppCompatActivity implements View.OnClickListener{

    private Teacher_fragment1 f1;
    private Teacher_fragment2 f2;

    private Button button_teacher_video;
    private Button button_teacher_data;
    int i = 0;

    private static final String TAG ="Plus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        button_teacher_video = findViewById(R.id.teacher_video);
        button_teacher_data = findViewById(R.id.teacher_data);
        button_teacher_video.setOnClickListener(this);//对按钮1设置监听
        button_teacher_data.setOnClickListener(this);//对按钮2设置监听

        //第一次初始化首页默认显示第一个fragment
        initFragment1();

    }//on

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teacher_plus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(TeacherMainActivity.this,"you clicked add_item",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsItemSelected: add_item");
                break;
            case R.id.remove_item:
                Toast.makeText(TeacherMainActivity.this,"you clicked remove_item",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsItemSelected: remove_item");
                break;
            default:
                ;
        }
        return true;
    }

    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if(f1 == null){
            f1 = new Teacher_fragment1();
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
            f2 = new Teacher_fragment2();
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
        if(v == button_teacher_video){
            initFragment1();
        }
        else if(v == button_teacher_data){
            initFragment2();
        }
    }

}//end
