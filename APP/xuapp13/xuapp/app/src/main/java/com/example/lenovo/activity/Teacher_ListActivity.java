package com.example.lenovo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.Helper.List.ListViewDemoAdapter;
import com.example.lenovo.R;

import java.util.ArrayList;
import java.util.List;

public class Teacher_ListActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView testLv;//ListView组件
    private Button updateDataBtn;//动态加载数据组件
    private List<String> dataList = new ArrayList<>();//存储数据
    private ListViewDemoAdapter listViewDemoAdapter;//ListView的数据适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__list);

        initView();//初始化组件
        initData();//初始化数据
    }

    private void initView() {
        testLv =  findViewById(R.id.test_lv);
        updateDataBtn = findViewById(R.id.update_data_btn);
        updateDataBtn.setOnClickListener(this);
    }

    private void initData() {
        //初始化10项数据
        for (int i = 1; i <= 20; i++) {
            dataList.add("显示同学" + i);
        }

        //设置ListView的适配器
        listViewDemoAdapter = new ListViewDemoAdapter(this, dataList);
        testLv.setAdapter(listViewDemoAdapter);
        testLv.setSelection(4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_data_btn:    //动态加载列表数据
                dataList.add("动态加载的数据项");

                //通知ListView更改数据源
                if (listViewDemoAdapter != null) {
                    listViewDemoAdapter.notifyDataSetChanged();
                    testLv.setSelection(dataList.size() - 1);//设置显示列表的最后一项
                } else {
                    listViewDemoAdapter = new ListViewDemoAdapter(this, dataList);
                    testLv.setAdapter(listViewDemoAdapter);
                    testLv.setSelection(dataList.size() - 1);
                }
                break;
        }
    }

}
