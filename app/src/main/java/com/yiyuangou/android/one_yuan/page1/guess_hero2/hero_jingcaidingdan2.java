package com.yiyuangou.android.one_yuan.page1.guess_hero2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.dingdan_hero_click;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.dingdan_kaicai_click;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.guess_hero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class hero_jingcaidingdan2 extends FragmentActivity {


    private dingdan_hero_click jin;
    private View jigncai_click_topbar;
    private View back;
    private ImageView dingdan_imgtap;
    //viewpager当前页
    private int currentIndex;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView hero_text;
    private TextView kaicai_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hero_jingcaidingdan);
        init();
        init_back();
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
                    hero_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    kaicai_text.setTextColor(getResources().getColor(R.color.black));
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 2) + currentIndex * (MainActivity.screenWidth / 2));
                    dingdan_imgtap.setLayoutParams(lp);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    hero_text.setTextColor(getResources().getColor(R.color.black));
                    kaicai_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 / 2) + currentIndex
                            * (MainActivity.screenWidth / 2));
                    dingdan_imgtap.setLayoutParams(lp);

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
        init_three();
    }

    private void init() {
        jigncai_click_topbar=findViewById(R.id.jigncai_click_topbar);
        back=jigncai_click_topbar.findViewById(R.id.logreg_left);
        hero_text= (TextView) findViewById(R.id.hero_text);
        kaicai_text= (TextView) findViewById(R.id.kaicai_text);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        dingdan_hero_click2 dingdan_hero_click=new dingdan_hero_click2();
        dingdan_kaicai_click2 dingdan_kaicai_click=new dingdan_kaicai_click2();
        mFragments.add(dingdan_hero_click);
        mFragments.add(dingdan_kaicai_click);
        dingdan_imgtap= (ImageView) findViewById(R.id.dingdan_imgtap);
        //初始化指示线的长度
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) dingdan_imgtap.getLayoutParams();
        lp.width= MainActivity.screenWidth/2;
        dingdan_imgtap.setLayoutParams(lp);
    }

    private void init_three() {
        hero_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hero_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                kaicai_text.setTextColor(getResources().getColor(R.color.black));
                mViewPager.setCurrentItem(0);
            }
        });
        kaicai_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hero_text.setTextColor(getResources().getColor(R.color.black));
                kaicai_text.setTextColor(getResources().getColor(R.color.yigehuadong));
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(hero_jingcaidingdan2.this,guess_hero2.class);
        startActivity(intent);
        finish();
    }
}
