package com.example.lenovo.College;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lenovo.R;
import com.example.lenovo.activity.HomePageActivity;
import com.example.lenovo.activity.Teacher_ListActivity;

public class College_fragment1 extends Fragment {
    private Button college_bottom_btn;
    private ImageView college_1605;
    private ImageView college_1606;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.college_fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        college_1605 = getActivity().findViewById(R.id.college_1605);
        college_1605.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),College_view_Activity.class);
                startActivity(intent);
            }
        });

        college_1606 = getActivity().findViewById(R.id.college_1606);
        college_1606.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),College_view_Activity.class);
                startActivity(intent);
            }
        });


        college_bottom_btn = getActivity().findViewById(R.id.college_bottom_btn);
        college_bottom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomePageActivity.class);
                startActivity(intent);
            }
        });

    }//ona
}
