package com.yiyuangou.android.one_yuan.zidingyi_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/7/1.
 */
public class small_ball extends RelativeLayout {
private   TextView text_name;

    public void setText_name(String name) {
        text_name.setText(name);
    }

    public small_ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.saiche_qiu, this);
        text_name= (TextView) findViewById(R.id.text_name);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.small_ball);

        int attr = ta.getIndex(0);
        if (attr==R.styleable.small_ball_text_name){
            String string=ta.getString(attr);
            text_name.setText(string);
        }
    }

    public small_ball(Context context) {
        this(context, null);
    }

    public small_ball(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
