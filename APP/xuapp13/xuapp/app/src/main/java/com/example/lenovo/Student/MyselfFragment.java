package com.example.lenovo.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.lenovo.Index.Index_student_Activity;
import com.example.lenovo.R;
import com.example.lenovo.R;
import com.example.lenovo.activity.CollegeActivity;
import com.example.lenovo.activity.TestActivity;

public class MyselfFragment extends Fragment {
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_myself_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayout = getActivity().findViewById(R.id.myself_main);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Index_student_Activity.class);//空白页TestActivity
                startActivity(intent);
            }
        });
    }
}//end
