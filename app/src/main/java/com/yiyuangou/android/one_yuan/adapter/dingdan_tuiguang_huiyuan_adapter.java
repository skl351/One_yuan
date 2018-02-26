package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.item_jiangli;
import com.yiyuangou.android.one_yuan.bean.tuiguang_huiyuan;
import com.yiyuangou.android.one_yuan.util.Util_string;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class dingdan_tuiguang_huiyuan_adapter extends BaseAdapter {
    private Context Mcontext;
    List<tuiguang_huiyuan> data;

    public dingdan_tuiguang_huiyuan_adapter(Context context, List<tuiguang_huiyuan> list){
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
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_huiyuan, null);
        TextView money= (TextView) view.findViewById(R.id.money);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView phone= (TextView) view.findViewById(R.id.phone);

        money.setText(data.get(position).getMoney());
        name.setText(data.get(position).getName());

        phone.setText(Util_string.jiequ(data.get(position).getPhone(),0,3)+"****"+Util_string.jiequ2(data.get(position).getPhone(),7));


        return view;
    }
}
