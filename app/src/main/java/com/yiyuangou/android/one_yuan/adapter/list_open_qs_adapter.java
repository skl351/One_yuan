package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.open_qs;

import java.util.List;

/**
 * Created by android on 2016/4/19.
 */
public class list_open_qs_adapter extends BaseAdapter {
    Context mContext;
    List<open_qs> data;

    public list_open_qs_adapter(Context context,List<open_qs> list){
        mContext=context;
        data=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View qs_view= LayoutInflater.from(mContext).inflate(R.layout.guess_qs_xiangp, null);
        TextView jincai_line1_1=(TextView)qs_view.findViewById(R.id.jincai_line1_1);
        TextView jincai_line1_2=(TextView)qs_view.findViewById(R.id.jincai_line1_2);
        TextView jincai_line1_3=(TextView)qs_view.findViewById(R.id.jincai_line1_3);
        TextView jincai_line1_4=(TextView)qs_view.findViewById(R.id.jincai_line1_4);
        TextView jincai_line1_5=(TextView)qs_view.findViewById(R.id.jincai_line1_5);
        jincai_line1_1.setText(data.get(position).getKJQS());
        jincai_line1_2.setText(data.get(position).getGroup_1()+data.get(position).getGroup_2()+data.get(position).getGroup_3());
        jincai_line1_3.setText(data.get(position).getGroup_num());
        jincai_line1_4.setText(data.get(position).getResult_1());
        jincai_line1_5.setText(data.get(position).getResult_2());
        return qs_view;
    }
}
