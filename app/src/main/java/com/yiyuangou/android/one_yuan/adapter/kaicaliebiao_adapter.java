package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.kaicai_item;

import java.util.List;

/**
 * Created by android on 2016/5/25.
 */
public class kaicaliebiao_adapter extends BaseAdapter{
    Context mContext;
    List<kaicai_item> data;

    public kaicaliebiao_adapter(Context context, List<kaicai_item> list){
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
        View qs_view= LayoutInflater.from(mContext).inflate(R.layout.kaicailiebiao_item, null);
       TextView bh= (TextView) qs_view.findViewById(R.id.kaicai_bh);
       TextView name= (TextView) qs_view.findViewById(R.id.kaicai_name);
        TextView total= (TextView) qs_view.findViewById(R.id.kaicai_total);
        bh.setText(data.get(position).getBh());
        name.setText(data.get(position).getName());
        total.setText(data.get(position).getTotal());

        return qs_view;
    }
}
