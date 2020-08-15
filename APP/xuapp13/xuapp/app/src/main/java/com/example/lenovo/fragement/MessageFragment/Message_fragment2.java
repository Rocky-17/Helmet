package com.example.lenovo.fragement.MessageFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.R;

public class Message_fragment2 extends Fragment {
    private Button message2_button;
    private EditText message2_edittext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_message_fragment2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        message2_button = getActivity().findViewById(R.id.message2_button);
        message2_edittext = getActivity().findViewById(R.id.message2_edittext);
        message2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"已提交",Toast.LENGTH_LONG).show();
                message2_edittext.setText("");
            }
        });
    }

}
