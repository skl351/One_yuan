package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.DensityUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/19.
 */
public class gift_send_v_xiangq extends FragmentActivity {

    private String name;
    private String price;
    public  String info;
    private String img;
    private Button gift_ok;
    private View gift_topter;
    private View back;
    public static String UUID;
    private ImageView lingp_imgtap;
    private FragmentPagerAdapter mAdapter;
    //viewpager当前页
    private int currentIndex;
    private View please_wait;
    tuwenxiangp_view tuwenxiangp_view;
    chanpingguige_view chanpingguige_view;
    private TextView text_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.gift_send_v_xiangq);
        please_wait=findViewById(R.id.please_wait);
        tuwenxiangp_view=new tuwenxiangp_view();
         chanpingguige_view=new chanpingguige_view();
        UUID=getIntent().getStringExtra("UUID");
        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        info=getIntent().getStringExtra("info");
        img=getIntent().getStringExtra("iMg_5");
        init();
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动中一直调用
            /*
            1参数 当前页面就是你滑动的页面
            2参数 当前页面偏移的百分比
            3参数 当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (currentIndex == 0 && position == 0)// 0->1
                {
                   LinearLayout.LayoutParams lp_uu= (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
                    lp_uu.height=getIntent().getIntExtra("js_lenght",0)+DensityUtil.dip2px(gift_send_v_xiangq.this,60);
                    mViewPager.setLayoutParams(lp_uu);
                    tuwenxiangq_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    chanpingguige_text.setTextColor(getResources().getColor(R.color.black));
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) lingp_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 2) + currentIndex * (MainActivity.screenWidth / 2));
                    lingp_imgtap.setLayoutParams(lp);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    LinearLayout.LayoutParams lp_uu= (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
                    lp_uu.height=gethight3()+DensityUtil.dip2px(gift_send_v_xiangq.this,60);
                    mViewPager.setLayoutParams(lp_uu);

                    tuwenxiangq_text.setTextColor(getResources().getColor(R.color.black));
                    chanpingguige_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) lingp_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 /2) + currentIndex
                            * (MainActivity.screenWidth / 2));
                    lingp_imgtap.setLayoutParams(lp);
                }
            }
            //页面完成后调用当前页面
            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }
            //状态改变 1正在滑动 2 滑动完毕 0什么都没做。
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        init_text();
        init_ok();
        init_back();
        init_three();
    }
    private void init_three() {
        tuwenxiangq_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuwenxiangq_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                chanpingguige_text.setTextColor(getResources().getColor(R.color.black));
                mViewPager.setCurrentItem(0);
            }
        });
        chanpingguige_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuwenxiangq_text.setTextColor(getResources().getColor(R.color.black));
                chanpingguige_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                mViewPager.setCurrentItem(1);
            }
        });
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    Log.e("Exception when doBack", e.toString());
                }
            }
        });
    }
    private void init_ok() {
        gift_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(gift_send_v_xiangq.this,queren_shouhuodizhi.class);
                intent.putExtra("UUID",getIntent().getStringExtra("UUID"));
                startActivity(intent);
            }
        });
    }
    private void init_text() {
        ImageLoader.getInstance().displayImage(img,title_img);
        gift_name.setText(name);
        price_text.setText(price);
        text_info.setText(info);
        price_2.setText(price);
    }
    private ImageView title_img;
    private TextView gift_name;
    private TextView price_text;
    private TextView price_2;
    private TextView tuwenxiangq_text;
    private ViewPager mViewPager;
    private TextView chanpingguige_text;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private void init() {
        text_info= (TextView) findViewById(R.id.info);
        chanpingguige_text= (TextView) findViewById(R.id.chanpingguige_text);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        tuwenxiangq_text= (TextView) findViewById(R.id.tuwenxiangq_text);
        lingp_imgtap= (ImageView) findViewById(R.id.lingp_imgtap);
        gift_topter=findViewById(R.id.gift_topter);
        back=gift_topter.findViewById(R.id.logreg_left);
        gift_ok= (Button) findViewById(R.id.gift_ok);
        price_2= (TextView) findViewById(R.id.price_2);
        price_text= (TextView) findViewById(R.id.price);
        gift_name= (TextView) findViewById(R.id.gift_name);
        title_img= (ImageView) findViewById(R.id.title_img);
       ViewGroup.LayoutParams layoutParams= title_img.getLayoutParams();
        layoutParams.width= MainActivity.screenWidth;
        layoutParams.height= (int) (MainActivity.screenWidth*0.6);
        title_img.setLayoutParams(layoutParams);
        lingp_imgtap= (ImageView) findViewById(R.id.lingp_imgtap);
        //初始化指示线的长度
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) lingp_imgtap.getLayoutParams();
        lp.width= MainActivity.screenWidth/2;
        lingp_imgtap.setLayoutParams(lp);
        mFragments.add(tuwenxiangp_view);
        mFragments.add(chanpingguige_view);
    }
    static  int hight2=0;
    public static void gethight2(int h){
        hight2=h;
    }
    public static int gethight3(){
        return com.yiyuangou.android.one_yuan.page1.gift_send.chanpingguige_view.height;
    }

}
