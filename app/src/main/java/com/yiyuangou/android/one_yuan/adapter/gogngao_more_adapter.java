package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.gonggao_item;
import com.yiyuangou.android.one_yuan.page1.gonggao.all_gonggao;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class gogngao_more_adapter extends BaseAdapter {
    private Context Mcontext;
    List<gonggao_item> data;

    public gogngao_more_adapter(Context context, List<gonggao_item> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.gogngao_item_view, null);
       TextView time= (TextView) view.findViewById(R.id.time);
       TextView title= (TextView) view.findViewById(R.id.title);
        time.setText(data.get(position).getTime());
        title.setText(data.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Mcontext, all_gonggao.class);
                intent.putExtra("title",data.get(position).getTitle());
                intent.putExtra("content",data.get(position).getContent());

                Mcontext.startActivity(intent);
            }
        });
        return view;
    }
}
