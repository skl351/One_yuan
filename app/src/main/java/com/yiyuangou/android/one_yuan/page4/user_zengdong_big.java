package com.yiyuangou.android.one_yuan.page4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class user_zengdong_big extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private View user_zengdong;
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.my_zengsong_big);

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
                    zengsong_1.setBackgroundResource(R.color.home_orange);
                    zengsong_2.setBackgroundResource(R.color.white);
                    zengsong_3.setBackgroundResource(R.color.white);
                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.home_orange);
                    zengsong_3.setBackgroundResource(R.color.white);

                } else if (currentIndex == 1 && position == 1) // 2->1
                {
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.home_orange);
                    zengsong_3.setBackgroundResource(R.color.white);
                } else if (currentIndex == 2 && position == 1) // 1->2
                {
                    zengsong_1.setBackgroundResource(R.color.white);
                    zengsong_2.setBackgroundResource(R.color.white);
                    zengsong_3.setBackgroundResource(R.color.home_orange);

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
    protected void init_wangluo_init() {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_send_index;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", "" + 1);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {

                        arg1.getString("useJd");

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
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
        user_zengdong=findViewById(R.id.user_zengdong);
        back= user_zengdong.findViewById(R.id.logreg_left);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        zengsong_1=findViewById(R.id.zengsong_1);
        zengsong_2=findViewById(R.id.zengsong_2);
        zengsong_3=findViewById(R.id.zengsong_3);
        user_zengsong1 u1=new user_zengsong1();
        user_zengsong2 u2=new user_zengsong2();
        user_zengsong3 u3=new user_zengsong3();
        mFragments.add(u1);
        mFragments.add(u2);
        mFragments.add(u3);
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
