package com.example.lenovo.Helper.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lenovo.R;

import java.util.List;

public class ListViewDemoAdapter extends BaseAdapter {
    private Context context;//上下文对象
    private List<String> dataList;
    //ListView显示的数据
    // /**     * 构造器     *
    // * @param context  上下文对象
    //  * @param dataList 数据     */
    public ListViewDemoAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;        //判断是否有缓存
         if (convertView == null) {
             convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_demo, null);
             viewHolder = new ViewHolder(convertView);
             convertView.setTag(viewHolder);
         } else {
             //得到缓存的布局
             viewHolder = (ViewHolder) convertView.getTag();
         }
         //设置图片
         viewHolder.pictureImg.setImageResource(R.mipmap.ic_launcher);
         //设置内容
        viewHolder.contentTv.setText(dataList.get(position));
        return convertView;
    }

    /**     * ViewHolder类     */
    private final class ViewHolder {
        ImageView pictureImg;//图片
         TextView contentTv;//内容
         /**
          *  * 构造器         *
          * @param view 视图组件（ListView的子项视图）
          * */
        ViewHolder(View view) {
            pictureImg = (ImageView) view.findViewById(R.id.picture_img);
            contentTv = (TextView) view.findViewById(R.id.content_tv);
        }
    }



}
