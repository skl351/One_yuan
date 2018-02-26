package com.yiyuangou.android.one_yuan.zidingyi_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/3/21.
 */
public class input_border extends RelativeLayout {

    private ImageView int_left;
    private EditText int_center;
    private ImageView int_right;

    public input_border(Context context) {
        this(context, null);
    }

    public input_border(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public input_border(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_intput_border, this);
        int_left= (ImageView) findViewById(R.id.view_intput_lef);
        int_center= (EditText) findViewById(R.id.view_intput_center);
        int_right= (ImageView) findViewById(R.id.view_intput_right);
        TypedArray ta= context.obtainStyledAttributes(attrs, R.styleable.input_border);
        int n=ta.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=ta.getIndex(i);
            switch (attr)
            {
                case R.styleable.input_border_int_lef:
                    BitmapDrawable drawable= (BitmapDrawable) ta.getDrawable(attr);
                    int_left.setImageDrawable(drawable);
                    break;
                case R.styleable.input_border_int_center:
                    String string=ta.getString(attr);
                    int_center.setHint(string);
                    break;
                case R.styleable.input_border_int_rig:
                    BitmapDrawable deltere= (BitmapDrawable) ta.getDrawable(attr);
                    int_right.setImageDrawable(deltere);
                    int_right.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int_center.setText("");
                        }
                    });
                    break;


            }
        }


    }
}
