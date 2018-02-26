package com.yiyuangou.android.one_yuan.page4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class my_dingdan  extends FragmentActivity{
    private TextView gift_jiaohuan_text;
    private TextView jingcai_text;
    private TextView chongzhi_text;
    private TextView choujiang_text;
    private TextView jiangli_text;

    private ImageView dingdan_imgtap;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private View back;
    //viewpager当前页
    private int currentIndex;
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_dingdan);
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
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                    jingcai_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                    choujiang_text.setTextColor(getResources().getColor(R.color.black));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 4) + currentIndex * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                    choujiang_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 / 4) + currentIndex
                            * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);

                } else if (currentIndex == 1 && position == 1) // 2->1
                {
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                    choujiang_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 4) + currentIndex * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);
                } else if (currentIndex == 2 && position == 1) // 1->2
                {
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    choujiang_text.setTextColor(getResources().getColor(R.color.black));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 / 4) + currentIndex
                            * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);

                } else if (currentIndex == 2 && position == 2) // 1->2
                {
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    choujiang_text.setTextColor(getResources().getColor(R.color.black));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 4) + currentIndex
                            * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);

                }
                else if (currentIndex == 3 && position == 2) // 1->2
                {
                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                    choujiang_text.setTextColor(getResources().getColor(R.color.black));
                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 / 4) + currentIndex
                            * (MainActivity.screenWidth / 4));
                    dingdan_imgtap.setLayoutParams(lp);
                }

//                else if (currentIndex == 3 && position == 3) // 1->2
//                {
//                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
//                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
//                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
//                    choujiang_text.setTextColor(getResources().getColor(R.color.yigehuadong));
//                    jiangli_text.setTextColor(getResources().getColor(R.color.black));
//                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
//                            .getLayoutParams();
//                    lp.leftMargin = (int) (positionOffset * (MainActivity.screenWidth * 1.0 / 5) + currentIndex
//                            * (MainActivity.screenWidth / 5));
//                    dingdan_imgtap.setLayoutParams(lp);
//
//                }
//                else if (currentIndex == 4 && position == 3) // 1->2
//                {
//                    gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
//                    jingcai_text.setTextColor(getResources().getColor(R.color.black));
//                    chongzhi_text.setTextColor(getResources().getColor(R.color.black));
//                    choujiang_text.setTextColor(getResources().getColor(R.color.black));
//                    jiangli_text.setTextColor(getResources().getColor(R.color.yigehuadong));
//                    RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) dingdan_imgtap
//                            .getLayoutParams();
//                    lp.leftMargin = (int) (-(1 - positionOffset) * (MainActivity.screenWidth * 1.0 / 5) + currentIndex
//                            * (MainActivity.screenWidth / 5));
//                    dingdan_imgtap.setLayoutParams(lp);
//
//                }



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

    private void init_three() {
        gift_jiaohuan_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                jingcai_text.setTextColor(getResources().getColor(R.color.black));
                chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                choujiang_text.setTextColor(getResources().getColor(R.color.black));

                mViewPager.setCurrentItem(3);
            }
        });
        jingcai_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                jingcai_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                choujiang_text.setTextColor(getResources().getColor(R.color.black));

                mViewPager.setCurrentItem(0);
            }
        });
        chongzhi_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                jingcai_text.setTextColor(getResources().getColor(R.color.black));
                chongzhi_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                choujiang_text.setTextColor(getResources().getColor(R.color.black));
                mViewPager.setCurrentItem(2);
            }
        });
        choujiang_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
                jingcai_text.setTextColor(getResources().getColor(R.color.black));
                chongzhi_text.setTextColor(getResources().getColor(R.color.black));
                choujiang_text.setTextColor(getResources().getColor(R.color.yigehuadong));
                mViewPager.setCurrentItem(1);
            }
        });
//        jiangli_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gift_jiaohuan_text.setTextColor(getResources().getColor(R.color.black));
//                jingcai_text.setTextColor(getResources().getColor(R.color.black));
//                chongzhi_text.setTextColor(getResources().getColor(R.color.black));
//                choujiang_text.setTextColor(getResources().getColor(R.color.black));
////                jiangli_text.setTextColor(getResources().getColor(R.color.yigehuadong));
//                mViewPager.setCurrentItem(4);
//            }
//        });
    }

    private View user_dingdan;

    private void init() {
//        jiangli_text= (TextView) findViewById(R.id.jiangli_text);
        user_dingdan=findViewById(R.id.user_dingdan);
        back= user_dingdan.findViewById(R.id.logreg_left);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        dingdan_gift_jiaohuan dingdan_gift_jiaohuan1=new dingdan_gift_jiaohuan();
//        dingdan_jiangli dingdan_jiangli=new dingdan_jiangli();
        dingdan_chongzhi dingdan_chongzhi=new dingdan_chongzhi();
        dingdan_jincai dingdan_jincai=new dingdan_jincai();
        dingdan_choujiang dingdan_choujiang=new dingdan_choujiang();
        mFragments.add(dingdan_jincai);//竞猜
        mFragments.add(dingdan_choujiang);//抽奖
        mFragments.add(dingdan_chongzhi);//充值
        mFragments.add(dingdan_gift_jiaohuan1);//礼品交换
//        mFragments.add(dingdan_jiangli);//奖励
        dingdan_imgtap= (ImageView) findViewById(R.id.dingdan_imgtap);
        //初始化指示线的长度
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) dingdan_imgtap.getLayoutParams();
        lp.width= MainActivity.screenWidth/4;
        dingdan_imgtap.setLayoutParams(lp);

        gift_jiaohuan_text= (TextView) findViewById(R.id.gift_jiaohuan_text);
        jingcai_text= (TextView) findViewById(R.id.jingcai_text);
        chongzhi_text= (TextView) findViewById(R.id.chongzhi_text);
        choujiang_text= (TextView) findViewById(R.id.choujiang_text);

    }


}
