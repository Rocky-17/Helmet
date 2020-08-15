package com.example.lenovo.Helper.Barchart;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.lenovo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivityUtils {
    protected static BarChart mChart;

    /**
     *
     * @param context
     * @param mCharts
     * @param xAxisValues     x_number
     * @param yAxisValues     y_value
     * @param label           标题
     * @param values          x_value
     */
    public static void init(Context context,
                            BarChart mCharts ,
                            List<Float> xAxisValues,
                            List<Float> yAxisValues,
                            String label,
                            String[] values){

        mChart = mCharts;
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        MyMarkerView mv = new MyMarkerView(context, R.layout.activity_chart2);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        MyXFormatterNew formatter = new MyXFormatterNew(values);
        xAxis.setValueFormatter(formatter);
        xAxis.setLabelCount(xAxisValues.size() - 1, false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        float groupSpace = 0.08f;
        float barSpace = 0.06f; // x2 DataSet     0.03f x4 DataSet
        float barWidth = 0.4f; // x2 DataSet      0.2f x4 DataSet

        int groupCount = xAxisValues.size();

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        for (int i = 0; i < yAxisValues.size(); i++) {
            yVals1.add(new BarEntry(i, yAxisValues.get(i)));
            yVals2.add(new BarEntry(i, yAxisValues.get(i)+20));
        }

        BarDataSet set1, set2;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, "16计算机"); //文本label
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "16软件");
            set2.setColor(Color.rgb(164, 228, 251));

            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());

            mChart.setData(data);
        }

        // specify the width each bar should have
        mChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.invalidate();
    }

}

class MyXFormatterNew implements IAxisValueFormatter {

    private String[] mValues;

    public MyXFormatterNew(String[] values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.e(TAG, "----->getFormattedValue: "+value);

        if ((int) value <0||(int) value >=mValues.length){
            return String.valueOf((int) value);
        }else{
            return mValues[(int) value % mValues.length];
        }
    }

}
