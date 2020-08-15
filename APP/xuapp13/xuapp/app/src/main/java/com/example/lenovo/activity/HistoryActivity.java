package com.example.lenovo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.Helper.Student_History.MonthBean;
import com.example.lenovo.Helper.Student_History.PieFragment;
import com.example.lenovo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vpMain;

    private String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"专注\",\"value\":48}," +
            "{\"title\":\"不专注\",\"value\":32},{\"title\":\"睡觉\",\"value\":11}]}," +
            "{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"专注\",\"value\":32}," +
            "{\"title\":\"不专注\",\"value\":22},{\"title\":\"睡觉\",\"value\":42}]}," +
            "{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"专注\",\"value\":34}," +
            "{\"title\":\"不专注\",\"value\":123},{\"title\":\"睡觉\",\"value\":24}]}," +
            "{\"date\":\"2016年8月\",\"obj\":[{\"title\":\"专注\",\"value\":145}," +
            "{\"title\":\"不专注\",\"value\":123},{\"title\":\"睡觉\",\"value\":124}]}]";
    private ArrayList<MonthBean> mData;
    private Button btPre;
    private Button btNext;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        vpMain = (ViewPager) findViewById(R.id.vp_main);
        btPre = (Button) findViewById(R.id.bt_pre);
        btNext = (Button) findViewById(R.id.bt_next);
        textView = (TextView) findViewById(R.id.topcenter_tv);

        btPre.setOnClickListener(this);
        btNext.setOnClickListener(this);

        initData();
        initView();

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                textView.setText(""+mData.get(vpMain.getCurrentItem()).date);
                updateJumpText();
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }//on

    private void initData() {
        Gson gson = new Gson();
        mData = gson.fromJson(mJson, new TypeToken<ArrayList<MonthBean>>(){}.getType());
    }

    private void initView() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
        updateJumpText();
        textView.setText(""+mData.get(vpMain.getCurrentItem()).date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                if (vpMain.getCurrentItem() != vpMain.getAdapter().getCount()){
                    //Toast.makeText(MainActivity.this,""+vpMain.getCurrentItem(),Toast.LENGTH_LONG).show();
                    vpMain.setCurrentItem(vpMain.getCurrentItem()+1);//viewpager切换试图
                }
                break;
            case R.id.bt_pre:
                if (vpMain.getCurrentItem() != 0){
                    vpMain.setCurrentItem(vpMain.getCurrentItem()-1);
                }
        }
        updateJumpText();
    }

    private void updateJumpText() {
        if (vpMain.getCurrentItem() != vpMain.getAdapter().getCount()-1) {
            btNext.setText(handleText(mData.get(vpMain.getCurrentItem()+1).date));
        } else {
            btNext.setText("没有了！");
        }
        if (vpMain.getCurrentItem() != 0) {
            btPre.setText(handleText(mData.get(vpMain.getCurrentItem()-1).date));
        } else {
            btPre.setText("没有了！");
        }
        textView.setText(""+mData.get(vpMain.getCurrentItem()).date);
    }

    private CharSequence handleText(String date) {
        return date.substring(date.indexOf("年")+1);//自年后所有文字显示   ,date.indexOf("月")
    }

}//end
