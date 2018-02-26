package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.room;

import java.util.List;

/**
 * Created by android on 2016/5/25.
 */
public class jiugong_guess_adapter extends BaseAdapter{
    Context mContext;
    List<room> data;

    public jiugong_guess_adapter(Context context,List<room> list){
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
        View qs_view= LayoutInflater.from(mContext).inflate(R.layout.item_jiugon_guess, null);
       TextView name= (TextView) qs_view.findViewById(R.id.name);
        TextView xiaohao= (TextView) qs_view.findViewById(R.id.xiaohaojd);
        name.setText(data.get(position).getName());
        xiaohao.setText(data.get(position).getVal());

        return qs_view;
    }
}
