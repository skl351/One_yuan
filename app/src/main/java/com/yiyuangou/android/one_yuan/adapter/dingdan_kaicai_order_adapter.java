package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
import com.yiyuangou.android.one_yuan.bean.kaicai_order_item;
import com.yiyuangou.android.one_yuan.page1.guess_hero2.hero_kaicai_xiangq2;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.hero_dingdan_xiangq;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.hero_kaicai_xiangq;

import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class dingdan_kaicai_order_adapter extends BaseAdapter {
    private String biaozhun_rq;
    private boolean flag=false;

    List<kaicai_order_item> lists;
    Context context;
    int zzhy;

    public dingdan_kaicai_order_adapter(Context context, List<kaicai_order_item> lists){
        this. lists=lists;
        this.context=context;
        zzhy=0;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    private LinearLayout view;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.hero_dingdan_kaicai_item, null);
        View jingcai_kjrq_view= view.findViewById(R.id.jingcai_kjrq_view);
        TextView kjrq= (TextView) view.findViewById(R.id.jingcai_kjrq);
        TextView qishu= (TextView) view.findViewById(R.id.qishu);
        TextView zjds= (TextView) view.findViewById(R.id.zjds);
        TextView syjd= (TextView) view.findViewById(R.id.syjd);

        if(!"false".equals(lists.get(position).getXz_time())){
            jingcai_kjrq_view.setVisibility(View.VISIBLE);
            kjrq.setText(lists.get(position).getXz_time().split(" ")[0]);
        }

        syjd.setText(""+Math.round(Double.parseDouble(lists.get(position).getSyjd())));
        qishu.setText(lists.get(position).getExpect());
        zjds.setText(lists.get(position).getTotal_jd());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,hero_kaicai_xiangq2.class);
                intent.putExtra("syjd",lists.get(position).getSyjd());
                intent.putExtra("total",lists.get(position).getTotal_jd());
                intent.putExtra("sy1",lists.get(position).getSy1());
                intent.putExtra("sy2",lists.get(position).getSy2());
                intent.putExtra("sy3",lists.get(position).getSy3());
                intent.putExtra("sy4",lists.get(position).getSy4());
                intent.putExtra("sy5",lists.get(position).getSy5());
                intent.putExtra("sy6",lists.get(position).getSy6());
                intent.putExtra("addtime",lists.get(position).getXz_time());
                intent.putExtra("expect",lists.get(position).getExpect());
                intent.putExtra("show_time",lists.get(position).getShow_time());
                intent.putExtra("kcjd",lists.get(position).getKcjd());
                context.startActivity(intent);
            }
        });

        return view;

    }



}
