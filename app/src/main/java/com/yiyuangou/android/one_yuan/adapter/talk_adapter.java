package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.talk_bean;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class talk_adapter extends BaseAdapter {
    private Context Mcontext;
    List<talk_bean> data;

    public talk_adapter(Context context, List<talk_bean> list){
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
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.talk_item_view, null);
       TextView name= (TextView) view.findViewById(R.id.name);
       TextView content= (TextView) view.findViewById(R.id.content);
       TextView name2= (TextView) view.findViewById(R.id.name2);
       TextView content2= (TextView) view.findViewById(R.id.content2);
        TextView time1= (TextView) view.findViewById(R.id.time);
        TextView time2= (TextView) view.findViewById(R.id.time2);
        View view1=view.findViewById(R.id.suggest_back_info);
        time1.setText(data.get(position).getTime());
        content.setText(data.get(position).getText());
        if(!data.get(position).getRevetext().equals("null")){
            view1.setVisibility(View.VISIBLE);
            content2.setText(data.get(position).getRevetext());
            time2.setText(data.get(position).getRevetime());
        }

        return view;
    }
}
