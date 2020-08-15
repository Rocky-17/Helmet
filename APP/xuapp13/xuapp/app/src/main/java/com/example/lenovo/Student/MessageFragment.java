package com.example.lenovo.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.R;
import com.example.lenovo.fragement.MessageFragment.Message_fragment1;
import com.example.lenovo.fragement.MessageFragment.Message_fragment2;

public class MessageFragment extends Fragment {
    private TextView textView1;
    private TextView textView2;
    private View view1;
    private View view2;
    private int index = 0;
    private Message_fragment1 messageFragment1;
    private Message_fragment2 messageFragment2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_message,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView1 = getActivity().findViewById(R.id.message_main1);
        textView2 = getActivity().findViewById(R.id.message_main2);
        view1 = getActivity().findViewById(R.id.message_view1);
        view2 = getActivity().findViewById(R.id.message_view2);

        initFragment1();

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment1();
                textView1.setTextColor(0xff24a0ff);
                view1.setBackgroundColor(0xff24a0ff);
                textView2.setTextColor(0xff000000);
                view2.setBackgroundColor(0xfff3f3f3);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment2();
                textView1.setTextColor(0xff000000);
                view1.setBackgroundColor(0xfff3f3f3);
                textView2.setTextColor(0xff24a0ff);
                view2.setBackgroundColor(0xff24a0ff);
            }
        });



    }//on

    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if(messageFragment1 == null){
            messageFragment1 = new Message_fragment1();
            transaction.add(R.id.message_fragment, messageFragment1);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(messageFragment1);
        transaction.commit();
    }

    private void initFragment2(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if(messageFragment2 == null){
            messageFragment2 = new Message_fragment2();
            transaction.add(R.id.message_fragment,messageFragment2);
        }
        hideFragment(transaction);
        transaction.show(messageFragment2);
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(messageFragment1 != null){
            transaction.hide(messageFragment1);
        }
        if(messageFragment2 != null){
            transaction.hide(messageFragment2);
        }
    }

}//end
