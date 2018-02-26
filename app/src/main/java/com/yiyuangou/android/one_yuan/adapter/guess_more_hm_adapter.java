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
import com.yiyuangou.android.one_yuan.page1.all_guess.guess_open;
import com.yiyuangou.android.one_yuan.page1.all_guess.guess_open2;
import com.yiyuangou.android.one_yuan.page1.all_guess.guess_open3;

import java.util.List;

/**
 * Created by android on 2016/4/20.
 */
public class guess_more_hm_adapter extends BaseAdapter {
    private Context context;
    private List<open_qs> data;
    List<kaijiang_result> list_kaijiang_results;
    public guess_more_hm_adapter(Context context, List<open_qs> list,List<kaijiang_result> list_kaijiang_results){
        data=list;
        this.context=context;
        this.list_kaijiang_results=list_kaijiang_results;
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

        View view= LayoutInflater.from(context).inflate(R.layout.guess_qs_xiangp, null);
        TextView jincai_line1_1=(TextView)view.findViewById(R.id.jincai_line1_1);
        TextView jincai_line1_2=(TextView)view.findViewById(R.id.jincai_line1_2);
        TextView jincai_line1_3=(TextView)view.findViewById(R.id.jincai_line1_3);
        TextView jincai_line1_4=(TextView)view.findViewById(R.id.jincai_line1_4);
        TextView jincai_line1_5=(TextView)view.findViewById(R.id.jincai_line1_5);
        jincai_line1_1.setText(data.get(position).getKJQS());
        jincai_line1_2.setText(data.get(position).getGroup_1()+data.get(position).getGroup_2()+data.get(position).getGroup_3());
        jincai_line1_3.setText(data.get(position).getGroup_num());
        jincai_line1_4.setText(data.get(position).getResult_1());
        jincai_line1_5.setText(data.get(position).getResult_2());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,guess_open3.class);
                intent.putExtra("kaijiang_results", list_kaijiang_results.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
