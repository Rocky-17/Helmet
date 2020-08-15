package com.example.lenovo.Teacher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lenovo.Helper.Barchart.BarChartActivityOnlyUtils;
import com.example.lenovo.Helper.Barchart.BarChartActivityUtils;
import com.example.lenovo.Helper.LineChart.LineChartBean;
import com.example.lenovo.Helper.LineChart.LineChartInterface;
import com.example.lenovo.Helper.LineChart.LineChartMarkView;
import com.example.lenovo.R;

import com.example.lenovo.utils.LocalJsonResolutionUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class Teacher_fragment2 extends Fragment {
    private LineChart lineChart;
    private BarChart chart1 ,chart2;
    private PieChart chart3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.teacher_fragment2,container,false);
        return view;
    }//on

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ////Linechart
        lineChart = getActivity().findViewById(R.id.m_lineChart);
        LineChartInterface lineChartInterface = new LineChartInterface();
        lineChartInterface.initChart(lineChart);

        setMarkerView();

        String fileName = "test.json";
        String testJson = LocalJsonResolutionUtils.getJson(getActivity(), fileName);
        LineChartBean lineChartBean = LocalJsonResolutionUtils.JsonToObject(testJson, LineChartBean.class);

        List<LineChartBean.IncomeBean> list = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();
        lineChartInterface.showLineChart(list, "迟到比例", Color.CYAN,lineChart);

        List<LineChartBean.CompositeIndexBean> indexBeanList = lineChartBean.getGRID0().getResult().getCompositeIndexShanghai();
        lineChartInterface.addLine(indexBeanList, "缺勤比例", Color.RED,lineChart);

        ////Barchart
        BarchartinitView();

        ////Piechart
        chart3 = getActivity().findViewById(R.id.chart3);
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(20f,"其他"));
        strings.add(new PieEntry(30f,"迟到人数"));
        strings.add(new PieEntry(50f,"签到人数"));

        PieDataSet dataSet = new PieDataSet(strings,"");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(182,0,122));
        colors.add(Color.rgb(231,213,142));
        colors.add(Color.rgb(83,178,193));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(14);//数字字体大小
        pieData.setValueTextColor(Color.rgb(255, 255, 255));//数字颜色
        //chart3.getData().setValueTextSize(18);//文字字体大小
        //chart3.getData().setValueTextColor(Color.rgb(0, 0, 0));//文字颜色

        Description description = new Description();
        description.setText("1606物联网班签到统计");
        //description.setTextSize(12);//右下角文字大小
        chart3.setDescription(description);

        chart3.setCenterText(getCenterText());
        chart3.setCenterTextSize(12);

        chart3.setRotationEnabled(true);//能否旋转

        chart3.setData(pieData);
        chart3.invalidate();
    }

    private CharSequence getCenterText() {
        CharSequence centerText = "总人数\n" + 100 + "人";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//中间内部文字颜色（前3个字）包左不包右
        spannableString.setSpan(new AbsoluteSizeSpan(84, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**  设置 可以显示X Y 轴自定义值的 MarkerView **/
    public void setMarkerView() {
        LineChartInterface lineChartInterface2 = new LineChartInterface();
        lineChartInterface2.initChart(lineChart);
        LineChartMarkView mv = new LineChartMarkView(getActivity(), lineChartInterface2.xAxis2.getValueFormatter());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    private void BarchartinitView() {
        chart1 = getActivity().findViewById(R.id.chart1);
        chart2 = getActivity().findViewById(R.id.chart2);

        //x 周默认输值 可用下面的x_value代替
        List<Float> x_number = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            x_number.add((float)i);
        }

        //y的值
        //int xcv = 2;
        List<Float> y_value = new ArrayList<>();
        y_value.add((float)44);
        y_value.add((float)74);
        y_value.add((float)61);
        y_value.add((float)19);
        y_value.add((float)73);
        y_value.add((float)23);
        y_value.add((float)52);
        y_value.add((float)83);
        y_value.add((float)6);
        y_value.add((float)47);

        //x的值
        String[] x_value = {"A","B","C","D","E","F","G","H","I","J"};
        //单行柱状图
        BarChartActivityOnlyUtils.init(getActivity(), chart1, x_number, y_value, "chart1", x_value);
        //对比性柱状图
        BarChartActivityUtils.init(getActivity(), chart2, x_number, y_value, "chart2", x_value);
    }

}
