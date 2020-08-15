package com.example.lenovo.Helper.Student_History;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.R;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class PieFragment extends Fragment implements OnChartValueSelectedListener {

    private static final String DATA_KEY = "pie_fragment_data_key";
    private MonthBean mData;
    private PieChart mChart;
    private TextView tvDes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_pie, null);
        mChart = (PieChart) inflate.findViewById(R.id.pc_chart);
        tvDes = (TextView) inflate.findViewById(R.id.tv_des);

        initView();
        return inflate;
    }

    private void initView() {
        setData();
        mChart.setCenterText(getCenterText());
        mChart.setDrawSliceText(true);
        mChart.getData().getDataSet().setDrawValues(true);
        mChart.getLegend().setEnabled(true);
        mChart.setHoleColor(Color.rgb(255, 255, 255));//正中间环形的背景色

        Description description = new Description();
        description.setText("hello");
        mChart.setDescription(description);

        mChart.setRotationEnabled(true);
        //mChart.setOnChartValueSelectedListener(this);
    }

    private CharSequence getCenterText() {
        CharSequence centerText = "总评分\n" + mData.getSum() + "分";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//中间内部文字颜色（前3个字）包左不包右
        spannableString.setSpan(new AbsoluteSizeSpan(64, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void setData() {
        List<String> titles = new ArrayList<>();
        List<PieEntry> entrys  = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            PieEntry pieEntry = new PieEntry(pieBean.value,pieBean.title);
            //titles.add(pieBean.title);
            //entrys.add(new Entry(pieBean.value, i));
            entrys.add(pieEntry);
        }
        PieDataSet dataSet = new PieDataSet(entrys,"");
        //dataSet.setColors(new int[]{Color.rgb(216, 77, 71), Color.rgb(183, 56, 63), Color.rgb(247, 85, 47)});//饼状图配色
        dataSet.setColors(new int[]{Color.rgb(236, 213, 142), Color.rgb(83, 178, 193), Color.rgb(182, 0, 122)});
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.rgb(255, 255, 255));
        pieData.setValueTextSize(18);
        mChart.setData(pieData);
    }

    public static PieFragment newInstance(MonthBean data) {
        
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);

        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }



/*    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float proportion = 360f/mData.getSum();
        float angle = 90-mData.obj.get((int)e.getX()).value*proportion/2-mData.getSum((int)e.getX())*proportion;
        mChart.setRotationAngle(angle);
        updateDesText((int)e.getX());
    }*/

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //float proportion = 360f/mData.getSum();
        //float angle = 90-mData.obj.get(e.getXIndex()).value*proportion/2-mData.getSum(e.getXIndex())*proportion;
        //mChart.setRotationAngle(angle);
        updateDesText((int)e.getX());
    }

    private void updateDesText(int index) {
        tvDes.setText(mData.obj.get(1).title + ": " + mData.obj.get(1).value);
    }

    @Override
    public void onNothingSelected() {
        tvDes.setText("");
    }

}
