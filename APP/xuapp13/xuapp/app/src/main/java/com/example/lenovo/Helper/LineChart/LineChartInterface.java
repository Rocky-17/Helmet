package com.example.lenovo.Helper.LineChart;

import android.graphics.Color;

import com.example.lenovo.utils.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class LineChartInterface {
    //private LineChart lineChart;
    public XAxis xAxis2;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    /**
     * 展示曲线
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(final List<LineChartBean.IncomeBean> dataList, String name, int color,LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            LineChartBean.IncomeBean data = dataList.get(i);
            /**         * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             *  * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示         */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);    }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        // CUBIC_BEZIER 设置圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER,lineChart);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        //X轴值的定义
        xAxis2.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getTradeDate();
                return DateUtil.formatDateToMD(tradeDate);
            }});
        xAxis2.setLabelCount(6,false);//设置X轴分割数量

        //Y轴值的自定义
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) (value * 100)) + "%";
            }});
        leftYAxis.setLabelCount(8);////设置Y轴分割数量


    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    public void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode,LineChart lineChart) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);

        ///////教程优化：图表背景、边框、网格线修改
        lineChart.setBackgroundColor(Color.rgb(250,250,250));//修改背景
        lineChart.setDrawBorders(false);//是否显示边界

        xAxis2.setDrawGridLines(false);//关闭X网格线
        rightYaxis.setDrawGridLines(false);//关闭Y轴右侧网格线
        leftYAxis.setDrawGridLines(true);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYaxis.setEnabled(false);//去掉右侧Y轴

        ///////教程优化：不显示点、不显示值
        //lineDataSet.setDrawCircles(false);
        //lineDataSet.setDrawValues(false);

        ///////教程优化：调用MarkView
        //setMarkerView();

        ///////教程优化：右下角DESC内容设置
        Description description = new Description();
        //description.setText("需要展示的内容");
        description.setEnabled(false);
        lineChart.setDescription(description);



        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        } else {
            lineDataSet.setMode(mode);
        }

    }


    public void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(true);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);
        /***XY轴的设置***/
        xAxis2 = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setAxisMinimum(0f);
        xAxis2.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);
        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);

    }

/*    *//** * 设置 可以显示X Y 轴自定义值的 MarkerView *//*
    public void setMarkerView() {
        LineChartMarkView mv = new LineChartMarkView(this, xAxis.getValueFormatter());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }*/

    /** * 添加曲线 */
    public void addLine(List<LineChartBean.CompositeIndexBean> dataList, String name, int color,LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            LineChartBean.CompositeIndexBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR,lineChart);
        //不显示值
        lineDataSet.setDrawValues(false);
        lineChart.getLineData().addDataSet(lineDataSet);
        lineChart.invalidate();
    }




}
