package com.yiyuangou.android.one_yuan.page4;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_zengdong_adapter;
import com.yiyuangou.android.one_yuan.bean.dingdan_zengsong_item;

import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class dingdan_zengsong extends Fragment {
    private View view;
    dingdan_zengdong_adapter adapter;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<dingdan_zengsong_item> list ;
    private View xiangyaotoum;
    private View zhanwushuju;
    int flag=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dingdan_zengsong, container, false);

        /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());
        return view;
    }

    /**
     * 在其中实现上拉和下拉的功能-----4----最主要的地方
     * @return
     */
    private PullToRefreshLayout.OnRefreshListener getlin() {
        return re=new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
//                        init_wangluo_init();
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);


            }


            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//                init_wangluo_init2();
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

            }
        };

    }

}
