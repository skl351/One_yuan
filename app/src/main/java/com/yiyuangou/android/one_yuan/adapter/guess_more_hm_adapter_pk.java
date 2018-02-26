package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result;
import com.yiyuangou.android.one_yuan.bean.open_qs;
import com.yiyuangou.android.one_yuan.bean.pk_more_qs;
import com.yiyuangou.android.one_yuan.page1.all_guess.guess_open3;
import com.yiyuangou.android.one_yuan.zidingyi_view.small_ball;

import java.util.List;

/**
 * Created by android on 2016/4/20.
 */
public class guess_more_hm_adapter_pk extends BaseAdapter {
    private Context context;
    private List<pk_more_qs> data;
    public guess_more_hm_adapter_pk(Context context,List<pk_more_qs> list){
        data=list;
        this.context=context;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view= LayoutInflater.from(context).inflate(R.layout.saiche_qs_xiangq, null);

        TextView textView= (TextView) view.findViewById(R.id.jincai_qs);
        textView.setText(data.get(position).getExpect());
        small_ball small_ball1= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_1);
        small_ball1.setText_name(data.get(position).getHm_list().get(0));
        small_ball small_ball2= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_2);
        small_ball2.setText_name(data.get(position).getHm_list().get(1));
        small_ball small_ball3= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_3);
        small_ball3.setText_name(data.get(position).getHm_list().get(2));
        small_ball small_ball4= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_4);
        small_ball4.setText_name(data.get(position).getHm_list().get(3));
        small_ball small_ball5= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_5);
        small_ball5.setText_name(data.get(position).getHm_list().get(4));
        small_ball small_ball6= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_6);
        small_ball6.setText_name(data.get(position).getHm_list().get(5));
        small_ball small_ball7= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_7);
        small_ball7.setText_name(data.get(position).getHm_list().get(6));
        small_ball small_ball8= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_8);
        small_ball8.setText_name(data.get(position).getHm_list().get(7));
        small_ball small_ball9= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_9);
        small_ball9.setText_name(data.get(position).getHm_list().get(8));
        small_ball small_ball10= (com.yiyuangou.android.one_yuan.zidingyi_view.small_ball) view.findViewById(R.id.car_10);
        small_ball10.setText_name(data.get(position).getHm_list().get(9));

        return view;
    }
}
