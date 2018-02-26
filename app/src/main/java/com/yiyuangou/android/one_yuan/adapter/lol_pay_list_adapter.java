package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.order_item;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class lol_pay_list_adapter extends BaseAdapter {
    private Context Mcontext;
    List<order_item> data;

    public lol_pay_list_adapter(Context context,List<order_item> list){
        data=list;
        Mcontext=context;
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
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.lol_pay_list_item, null);
        TextView name= (TextView) view.findViewById(R.id.hero_lol_number);
       TextView jd= (TextView) view.findViewById(R.id.hero_lol_jd);
       TextView pl= (TextView) view.findViewById(R.id.hero_lol_pl);
        name.setText(data.get(position).getName());
        jd.setText(data.get(position).getAll_bean());
        pl.setText(data.get(position).getPl());
        return view;
    }
}
