package com.yiyuangou.android.one_yuan.zidingyi_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yiyuangou.android.one_yuan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by android on 2016/7/9.
 */
public class sezi_view extends View {
    private Paint paint;
    private List<Bitmap> list;

    private int i = 0;
    private int num;

    public int getnumber() {
        return num;
    }

    public void setnumber(int i) {
        num= i;
    }

    public sezi_view(Context context) {
        super(context);
    }

    public sezi_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        list=new ArrayList<Bitmap>();

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_one);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_two);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_three);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_four);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_five);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.sezi_six);
        list.add(bitmap1);
        list.add(bitmap2);
        list.add(bitmap3);
        list.add(bitmap4);
        list.add(bitmap5);
        list.add(bitmap6);

    }
    /*
    控件的宽
     */
    private  int mRealWidth;//当前控件宽度，减去padding的值
    Timer timer;
    public void run(){
        timer=  new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,200);
    }
    private boolean flag=false;
    private boolean flag_2quan=false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // int widthMode=MeasureSpec.getMode(widthMeasureSpec);//拿着当前的int 得到一个模式
        int widthVal=measurew(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthVal,height);//自己重写，必须用这个来保存测量完的宽高
        mRealWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
    }
    private int measureHeight(int heightMeasureSpec) {

        int result=0;
        int mode=MeasureSpec.getMode(heightMeasureSpec);
        int size=MeasureSpec.getSize(heightMeasureSpec);
        //精确值
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            /*
            1.基准点是baseline
               2.ascent：是baseline之上至字符最高处的距离
                3.descent：是baseline之下至字符最低处的距离
                4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
                5.top：是指的是最高字符到baseline的值,即ascent的最大值
                6.bottom：是指最低字符到baseline的值,即descent的最大值
             */

            result=getPaddingTop()+getPaddingBottom()+list.get(0).getHeight();//上边距下编剧和。。

            if(mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);//必须比size小
            }
        }
        return result;
    }
    private int measurew(int heightMeasureSpec) {

        int result=0;
        int mode=MeasureSpec.getMode(heightMeasureSpec);
        int size=MeasureSpec.getSize(heightMeasureSpec);
        //精确值
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            /*
            1.基准点是baseline
               2.ascent：是baseline之上至字符最高处的距离
                3.descent：是baseline之下至字符最低处的距离
                4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
                5.top：是指的是最高字符到baseline的值,即ascent的最大值
                6.bottom：是指最低字符到baseline的值,即descent的最大值
             */

            result=getPaddingLeft()+getPaddingRight()+list.get(0).getWidth();//上边距下编剧和。。

            if(mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);//必须比size小
            }
        }
        return result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawBitmap(list.get(i),0,0,null);
        if(flag&&flag_2quan){
            timer.cancel();
        }
        flag=false;
        i++;
        if(i==6){
            i=0;
            flag_2quan=true;
        }

        if(i==num){
            flag=true;
        }

        canvas.restore();
    }


}
