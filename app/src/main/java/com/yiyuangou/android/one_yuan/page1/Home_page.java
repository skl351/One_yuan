package com.yiyuangou.android.one_yuan.page1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.model.item_new;
import com.yiyuangou.android.one_yuan.bean.zhongjiangmingdan;
import com.yiyuangou.android.one_yuan.page1.all_guess.guess;
import com.yiyuangou.android.one_yuan.page1.all_task_back.all_task;
import com.yiyuangou.android.one_yuan.page1.flipper_util.AutoTextView;
import com.yiyuangou.android.one_yuan.page1.flipper_util.Utils;
import com.yiyuangou.android.one_yuan.page1.gift_send.gift_send_activity;
import com.yiyuangou.android.one_yuan.page1.gonggao.Today_gonggao;
import com.yiyuangou.android.one_yuan.page1.gonggao.more_gonggao;
import com.yiyuangou.android.one_yuan.page1.guess_hero2.guess_hero2;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.guess_hero;
import com.yiyuangou.android.one_yuan.page1.home_paihangbang.home_paiming;
import com.yiyuangou.android.one_yuan.page1.huanlegou.fanjiang_jiugong;
import com.yiyuangou.android.one_yuan.page1.pk_ten.pk_ten;
import com.yiyuangou.android.one_yuan.page1.yiyuangou.yiyuan_gou_web;
import com.yiyuangou.android.one_yuan.page4.user_login;
import com.yiyuangou.android.one_yuan.util.Util_string;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by android on 2016/3/17.
 */
public class Home_page extends Fragment {
    private ViewPager mViewPager;
    private BannerAdapter bannerAdapter;
    private ImageView[] mIndicator;
    private Timer mTimer;
    private int mBannerPosition = 0;//当前位置
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 4;//可以设动态
    private boolean mIsUserTouched = false;
    private TimerTask mTimerTask ;
    private View yiyuangou_more;
    private View view;
    private TextView main_title;
//    private AutoTextView home_page_flitext;
    private View all_guess;
//    private View gift_send_but;
    private View all_task1;
    private View home_jrpm;
    private View huanlegou;
//    private View yiyuangou;
    private View pk_ten_jie;
    private View guess_hero_view;
    private TextView gonggao_title;
    private View home_more;
    private View guess_hero_copy;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.home_page, container,false);
        init();
        init_gonggao();
        init_paiming();
        init_all_guess_click();
//        init_gist_send_click();
        init_task();
        init_huanlegou();//九宫格
//        init_yiyuangou();//一元购
        init_pk_ten();//pk_shi
        init_guess_hero();//猜英雄
        init_guess_hero2();//猜英雄新的
        return view;
    }

    private void init_gonggao() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_gonggao;
        RequestParams requestParams=new RequestParams();
        requestParams.put("NUM","1");
        requestParams.put("PAGE","1");
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        gonggao_title.setText(jsonObject.getJSONArray("list").getJSONObject(0).getString("title"));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                    Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
            }
        });
        gonggao_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Today_gonggao.class);
                startActivity(intent);
            }
        });
        home_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),more_gonggao.class);
                startActivity(intent);
            }
        });
    }

    private void init_guess_hero() {
        guess_hero_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.IS_login) {

                    Intent intent = new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                } else {
                    MainActivity.hero_flag="1";
                    Intent intent=new Intent(getActivity(),guess_hero.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void init_guess_hero2() {
        guess_hero_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.IS_login) {

                    Intent intent = new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                } else {
//                    MainActivity.hero_flag="2";
                    Intent intent=new Intent(getActivity(),guess_hero2.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void init_pk_ten() {
        pk_ten_jie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.IS_login) {
                    Intent intent = new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                } else {
                    Intent intent=new Intent(getActivity(),pk_ten.class);
                    startActivity(intent);
                }
            }
        });

    }

//    private void init_yiyuangou() {
//        yiyuangou.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!MainActivity.IS_login) {
//                    Intent intent = new Intent(getActivity(), user_login.class);
//                    startActivity(intent);
//                } else {
//                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//                    asyncHttpClient.setTimeout(20000);
//                    String url = all_url.url_yiyuan;
//                    asyncHttpClient.post(url, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(JSONObject jsonObject) {
//                            super.onSuccess(jsonObject);
//                            try {
//                                if ("true".equals(jsonObject.getString("status"))) {
//                                    System.out.println(jsonObject+"----------------");
//                                    JSONObject jo=jsonObject.getJSONObject("config");
//                                    String html=jo.getString("PZZ");
//
//                                    Intent intent=new Intent(getActivity(),yiyuan_gou_web.class);
//                                    intent.putExtra("html",html);
//                                    startActivity(intent);
//
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable throwable) {
//                            super.onFailure(throwable);
////                            if (getActivity() != null) {
//
////                                Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
////                            }
//                        }
//                    });
//
//                }
//                /**
//                 * else {
//                 AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//                 asyncHttpClient.setTimeout(20000);
//                 String url = all_url.url_yiyuan;
//                 asyncHttpClient.post(url, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(JSONObject jsonObject) {
//                super.onSuccess(jsonObject);
//                try {
//                if ("true".equals(jsonObject.getString("status"))) {
//                JSONObject jo=jsonObject.getJSONObject("config");
//                String html=jo.getString("PZZ");
//
//                Intent intent=new Intent(getActivity(),yiyuan_gou_web.class);
//                intent.putExtra("html",html);
//                startActivity(intent);
//
//                }
//                } catch (Exception e) {
//                e.printStackTrace();
//                }
//                }
//
//                @Override
//                public void onFailure(Throwable throwable) {
//                super.onFailure(throwable);
//                if (getActivity() != null) {
//
//                Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                }
//                }
//                });
//
//                 }
//                 */
//
//
//            }
//        });
//    }

    private void init_huanlegou() {
        huanlegou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.IS_login) {
                    Intent intent = new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                } else {
                    Intent intent=new Intent(getActivity(),fanjiang_jiugong.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void init_paiming() {
        home_jrpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),home_paiming.class);
                    startActivity(intent);

            }
        });
    }

    private void init_task() {
        all_task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.IS_login) {
                    Intent intent = new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), all_task.class);
                    startActivity(intent);
                }

            }
        });
    }



//    private void init_gist_send_click() {
//        gift_send_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), gift_send_activity.class);
//                startActivity(intent);
//            }
//        });
//    }

    private void init_all_guess_click() {
        all_guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.IS_login){
                    Intent intent=new Intent(getActivity(), guess.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(), user_login.class);
                    startActivity(intent);
                }


            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
        }
    }
    private List<String> list;
    private void init() {
        guess_hero_copy=view.findViewById(R.id.guess_hero_copy);
        home_more=view.findViewById(R.id.home_more);
        gonggao_title= (TextView) view.findViewById(R.id.gonggao_title);
        guess_hero_view=view.findViewById(R.id.guess_hero);
        pk_ten_jie=view.findViewById(R.id.pk_ten);
//        yiyuangou=view.findViewById(R.id.huanlegou);
        huanlegou=view.findViewById(R.id.jiugonge_zhuanpan);
        home_jrpm=view.findViewById(R.id.home_jrpm);
        all_task1=view.findViewById(R.id.all_task);
        yiyuangou_more=view.findViewById(R.id.yiyuangou_more);
//        gift_send_but=view.findViewById(R.id.gift_send);
        all_guess=view.findViewById(R.id.all_guess);
//        home_page_flitext= (AutoTextView) view.findViewById(R.id.home_page_flitext);
        main_title= (TextView) view.findViewById(R.id.main_title);
//        main_title.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);//斜体，中文有效
        initView();
        initEvent();
    }
    private void initView() {
        //第一个5000表示从调用schedule()方法到第一次执行mTimerTask的run()方法的时间间隔
        //第二个5000表示以后每隔5000毫秒执行一次mTimerTask的run()方法
        mTimerTask=  new TimerTask() {
            @Override
            public void run() {
                if (!mIsUserTouched){
                    mBannerPosition = (mBannerPosition + 1) % FAKE_BANNER_SIZE;
                    /**
                     * Android在子线程更新UI的几种方法
                     * Handler，AsyncTask,view.post,runOnUiThread
                     */
                    if(getActivity()!=null){
                        getActivity(). runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mBannerPosition == FAKE_BANNER_SIZE - 1) {
                                    mViewPager.setCurrentItem(DEFAULT_BANNER_SIZE - 1, false);
                                } else {
                                    mViewPager.setCurrentItem(mBannerPosition);
                                }
                            }
                        });
                    }

                }
            }
        };
        mTimer= new Timer();
        mTimer.schedule(mTimerTask,5000,5000);
        //dot-----动态
        mIndicator = new ImageView[]{
                (ImageView)view.findViewById(R.id.dot_indicator1),
                (ImageView) view.findViewById(R.id.dot_indicator2),
                (ImageView) view.findViewById(R.id.dot_indicator3),
                (ImageView) view.findViewById(R.id.dot_indicator4),
        };
        //view
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
//        mTitle = (TextView) findViewById(R.id.title);
        //loadData
        loadData();
        //Touch
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
    }
    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBannerPosition = position;
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    List<item_new> list_new=new ArrayList<item_new>();;
    private void loadData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_index;
        asyncHttpClient.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject.toString()+"banner图片");
                        JSONArray js = jsonObject.getJSONArray("imgList");
                        for (int i = 0; i < js.length(); i++) {
                            item_new it1 = new item_new();
                            it1.setTpdz(js.getJSONObject(i).getString("TPDZ"));
                            list_new.add(it1);
                        }
                        bannerAdapter = new BannerAdapter(getActivity(), list_new);
                        mViewPager.setAdapter(bannerAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                if (getActivity() != null) {

//                    Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                }
            }


        });

    }

    private class BannerAdapter extends PagerAdapter {

        private Context context;
        private List<item_new> newsList;

        public BannerAdapter(Context context, List<item_new> list) {
            this.context = context;
            this.newsList = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= DEFAULT_BANNER_SIZE;

            View view = LayoutInflater.from(context).inflate(R.layout.item, container, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);
//            image.setBackgroundResource(R.mipmap.ic_launcher);
            Utils.loadImage(newsList.get(position).getTpdz(), image);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,"-->可以点击"+pos,Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return FAKE_BANNER_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void finishUpdate(ViewGroup container) {

            //这个有点懵逼..
            int position = mViewPager.getCurrentItem();
            if (position == 0){
                position = DEFAULT_BANNER_SIZE;
                mViewPager.setCurrentItem(position,false);
            }else if (position == FAKE_BANNER_SIZE - 1){
                position = DEFAULT_BANNER_SIZE - 1;
                mViewPager.setCurrentItem(position,false);
            }
        }
    }

    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        //遍历mIndicator重置src为normal
        for (ImageView indicator : mIndicator){
            indicator.setImageResource(R.drawable.dot_normal);
        }
        mIndicator[position].setImageResource(R.drawable.dot_focused);
//        mTitle.setText(news.getTop_stories().get(position).getTitle());
    }
    Thread thread;
    @Override
    public void onResume() {
        super.onResume();
//        init_getwangluo_zhongjianmingdan();



    }

//    List<zhongjiangmingdan> list_zhongjiangmingdan=new ArrayList<>();
//    private void init_getwangluo_zhongjianmingdan() {
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.setTimeout(20000);
//        String url = all_url.url_zhongjian_mingdan;
//        RequestParams requestParams=new RequestParams();
//        requestParams.put("PAGE","1");
//        requestParams.put("NUM", "100");
//        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                super.onSuccess(jsonObject);
//                try {
//                    System.out.println(jsonObject);
//                    if ("true".equals(jsonObject.getString("status"))) {
//                        JSONArray js = jsonObject.getJSONArray("winList");
//                        list_zhongjiangmingdan.clear();
//                        for (int i = 0; i < js.length(); i++) {
//                            zhongjiangmingdan it1 = new zhongjiangmingdan();
//                            it1.setJd(js.getJSONObject(i).getString("LOTTERY"));
//                            it1.setName(js.getJSONObject(i).getString("NAME"));
//                            it1.setDate(js.getJSONObject(i).getString("NODATE"));
//                            it1.setPhone(js.getJSONObject(i).getString("MOBILE"));
//                            list_zhongjiangmingdan.add(it1);
//                        }
//                        if (thread == null) {
//                            thread = new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    handler.postDelayed(runnable, 1000);
//                                }
//                            });
//                            thread.start();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                super.onFailure(throwable);
//
//            }
//
//
//        });
//    }

//
//    static int flag=-1;
//    Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            home_page_flitext.next();
//            flag=flag+1;
//            if (flag>=list_zhongjiangmingdan.size()){
//                flag=0;
//            }
//            zhongjiangmingdan z1=list_zhongjiangmingdan.get(flag);
//            home_page_flitext.setText(Html.fromHtml("<font color=#888888>" + "恭喜"+Util_string.jiequ(z1.getPhone(),0,3)+"****"+ Util_string.jiequ2(z1.getPhone(), 7)+ " 获得</font><font color=red>" +z1.getJd() + "金豆"+"</font>"));
//            handler.postDelayed(this, 4000);
//        }
//    };
//    Handler handler=new Handler();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    @Override
    public void onPause() {
        super.onPause();
        if(mTimer!=null){
            mTimer.cancel();
        }

    }

}
