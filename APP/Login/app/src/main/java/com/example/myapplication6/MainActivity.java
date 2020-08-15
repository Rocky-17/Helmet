package com.example.myapplication6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private TextView mTvdg,mTvzg,mTvkg;
    private Button mBtn,mBtn1,mBtn2,mBtn3,mBtn4;
    private List<String> list1=new ArrayList<String>();
    private List<String> list2=new ArrayList<String>();
    private List<String> list3=new ArrayList<String>();
    private Spinner mSpinner1,mSpinner2,mSpinner3;
    private ArrayAdapter<String>  adapter1,adapter2,adapter3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvdg=findViewById(R.id.tv_dengji);
        mTvzg=findViewById(R.id.tv_zhengxing);
        mTvkg=findViewById(R.id.tv_kaigong);
        mSpinner1=findViewById(R.id.spinner1);
        mSpinner2=findViewById(R.id.spinner2);
        mSpinner3=findViewById(R.id.spinner3);

        list1.add("一等");
        list1.add("二等");
        list1.add("三等");
        list1.add("四等");
        list1.add("五等");

        list2.add("安全帽佩戴问题");
        list2.add("工作服穿着问题");
        list2.add("工地人数问题");

        list3.add("2019.12.30");
        list3.add("2020.03.12");
        list3.add("2020.04.15");
        list3.add("2020.02.26");
        list3.add("2020.05.05");

        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter1);

        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(adapter2);

        adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner3.setAdapter(adapter3);

        mSpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTvdg.setText("隐患等级：");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTvdg.setText("NONE");

            }
        });
        mSpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTvzg.setText("整改类型：");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mTvzg.setText("NONE");

            }
        });
        mSpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTvkg.setText("开工日期：");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTvkg.setText("NONE");
            }
        });

        mBtn=findViewById(R.id.list_right_btn);
        mBtn1=findViewById(R.id.list_right_btn1);
        mBtn2=findViewById(R.id.list_right_btn2);
        mBtn3=findViewById(R.id.list_right_btn3);
        mBtn4=findViewById(R.id.list_right_btn4);
        OnClick onClick=new OnClick();
        mBtn.setOnClickListener(onClick);
        mBtn1.setOnClickListener(onClick);
        mBtn2.setOnClickListener(onClick);
        mBtn3.setOnClickListener(onClick);
        mBtn4.setOnClickListener(onClick);



    }
    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent intent = null;
            switch (v.getId()){
                case R.id.list_right_btn:
                    case R.id.list_right_btn3:
                case R.id.list_right_btn4:
                    intent=new Intent(MainActivity.this,MainActivity3.class);
                    break;
                case R.id.list_right_btn1:
                    case R.id.list_right_btn2:
                    intent=new Intent(MainActivity.this,MainActivity2.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
