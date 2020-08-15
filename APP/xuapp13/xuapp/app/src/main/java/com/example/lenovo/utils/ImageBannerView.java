package com.example.lenovo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

public class ImageBannerView extends ViewGroup {

    private int children;
    private int childheight;
    private int childwidth;
    private int x;
    private int index=0;

    private Scroller scroller;
    //
    //自动轮播
    private boolean isAuto=true;//默认启动轮播
    private Timer timer=new Timer();
    private TimerTask task;
    private Handler autoHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what ){
                case 0://此时需要图片自动轮播
                    if(++index >children-1){//从最后到第一张
                        index=0;
                    }
                    scrollTo(childheight*index,0);
                    break;
            }
        }
    };
    private void statAuto(){
        isAuto=true;
    }
    private void stopAuto(){
        isAuto=false;
    }

    public ImageBannerView(Context context) {
        super(context);
        initobj();
    }
    public ImageBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initobj();
    }

    public ImageBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initobj();
    }
    private void initobj(){
        scroller=new Scroller(getContext());

        task=new TimerTask() {
            @Override
            public void run() {
                if(isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task,100,2000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        children=getChildCount();
        if(children==0){
            setMeasuredDimension(0,0);
        }
        else{
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            View view=getChildAt(0);
            childwidth=view.getMeasuredWidth();
            childheight=view.getMeasuredHeight();
            int width=view.getMeasuredWidth()*children;
            setMeasuredDimension(width,childheight);
        }
    }

    /**
     * 继承ViewGroup 必须要实现布局OnLayout方法
     */

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        if(change){
            int leftMargin=0;
            for(int i=0;i<children;i++){
                View view=getChildAt(i);
                view.layout(leftMargin,0,leftMargin+childwidth,childheight);
                leftMargin+=childwidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                x= (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE://安下后的过程
                int moveX= (int) event.getX();
                int distance=moveX-x;
                scrollBy(-distance,0);
                x=moveX;
                break;
            case MotionEvent.ACTION_UP:
                int scrollx=getScrollX();
                index=(scrollx+childwidth/2)/childwidth;
                if(index<0){//已经滑到最左边首张图片
                    index=0;
                }else if(index>children-1){//已经滑动到最右端最哈偶一张图
                    index=children-1;
                }
                //  scrollTo(index*childwidth,0);scrollto方法实现轮播
                int dx =index*childwidth-scrollx;
                scroller.startScroll(scrollx,0,dx,0);//scroller方法实现
                postInvalidate();
                statAuto();
                break;
            default:
                break;
        }
        return true ;//父容器已经处理好
    }
}
