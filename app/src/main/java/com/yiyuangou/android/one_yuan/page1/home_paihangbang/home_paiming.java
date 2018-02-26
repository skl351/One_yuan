package com.yiyuangou.android.one_yuan.page1.home_paihangbang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yiyuangou.android.one_yuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/14.
 */
public class home_paiming extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private View user_zengdong;
    private int currentIndex;
    private Button jigncai_dingdan_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_paiming);
        init();
        init_back();
        init_three();
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
                    jigncai_dingdan_click.setVisibility(View.GONE);
                    zengsong_1.setBackgroundResource(R.color.home_orange);
                    zengsong_2.setBackgroundResource(R.color.white);
                    zengsong_3.setBackgroundResource(R.color.white);
                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    jigncai_dingdan_click.setVisibility(View.GONE);
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.home_orange);
                    zengsong_3.setBackgroundResource(R.color.white);

                } else if (currentIndex == 1 && position == 1) // 2->1
                {
                    jigncai_dingdan_click.setVisibility(View.GONE);
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.home_orange);
                    zengsong_3.setBackgroundResource(R.color.white);
                } else if (currentIndex == 2 && position == 1) // 1->2
                {
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.white);
                    zengsong_3.setBackgroundResource(R.color.home_orange);
                    jigncai_dingdan_click.setVisibility(View.VISIBLE);

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
    }
    private void init_three() {
        zengsong_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zengsong_1.setBackgroundResource(R.color.home_orange);
                zengsong_2.setBackgroundResource(R.color.white);
                zengsong_3.setBackgroundResource(R.color.white);
                mViewPager.setCurrentItem(0);
            }
        });
        zengsong_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zengsong_1.setBackgroundResource(R.color.white);
                zengsong_2.setBackgroundResource(R.color.home_orange);
                zengsong_3.setBackgroundResource(R.color.white);

                mViewPager.setCurrentItem(1);
            }
        });
        zengsong_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zengsong_1.setBackgroundResource(R.color.white);
                zengsong_2.setBackgroundResource(R.color.white);
                zengsong_3.setBackgroundResource(R.color.home_orange);
                mViewPager.setCurrentItem(2);
            }
        });
    }
    private View back;
    private View zengsong_1;
    private View zengsong_2;
    private View zengsong_3;
    private void init() {
        jigncai_dingdan_click= (Button) findViewById(R.id.jigncai_dingdan_click);
        jigncai_dingdan_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home_paiming.this,guizhe.class);
                startActivity(intent);
            }
        });
        user_zengdong=findViewById(R.id.paihangbang_topbar);
        back= user_zengdong.findViewById(R.id.logreg_left);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        zengsong_1=findViewById(R.id.zengsong_1);
        zengsong_2=findViewById(R.id.zengsong_2);
        zengsong_3=findViewById(R.id.zengsong_3);

        paiming1 p1=new paiming1();
        paiming2 p2=new paiming2();
        paiming3 p3=new paiming3();
        mFragments.add(p2);
//        mFragments.add(p3);
//        mFragments.add(p1);
    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
