package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result_hero;

import java.util.List;

/**
 * Created by android on 2016/4/20.
 */
public class guess_more_hm_adapter_hero extends BaseAdapter {
    private Context context;
    private List<kaijiang_result_hero> data;
    public guess_more_hm_adapter_hero(Context context,List<kaijiang_result_hero> list_kaijiang_results){
        data=list_kaijiang_results;
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

        View qs_view= LayoutInflater.from(context).inflate(R.layout.guess_qshero_xiangp, null);
        TextView qs= (TextView) qs_view.findViewById(R.id.jincai_line1_1);
        TextView op_number= (TextView) qs_view.findViewById(R.id.jincai_line1_2);
        TextView name= (TextView) qs_view.findViewById(R.id.jincai_line1_3);
        TextView jieguo1= (TextView) qs_view.findViewById(R.id.jincai_line1_4_1);
        TextView jieguo2= (TextView) qs_view.findViewById(R.id.jincai_line1_4_2);
        TextView jieguo3= (TextView) qs_view.findViewById(R.id.jincai_line1_4_3);
        qs.setText(data.get(position).getExpect());
        String str="<font color=red>"+data.get(position).getLast_code_all()+"</font>"+"+"+data.get(position).getCode_1()+"+"+data.get(position).getCode_2()+"+"+data.get(position).getCode_3()+"+"+data.get(position).getCode_4()+"=>"+data.get(position).getCode_all();
        op_number.setText(Html.fromHtml(str));
        name.setText(data.get(position).getName());
        String result=data.get(position).getResult();
        String[] aa=result.split(",");
        for (int j=0;j<aa.length;j++){
            switch (aa[j]){
                case "有蓝" :jieguo1.setTextColor(context.getResources().getColor(R.color.blue));
                    jieguo1.setText("有蓝,");
                    break;
                case "无蓝" :jieguo1.setTextColor(context.getResources().getColor(R.color.red));
                    jieguo1.setText("无蓝,");
                    break;
                case "近战" :jieguo2.setTextColor(context.getResources().getColor(R.color.blue));
                    jieguo2.setText("近战,");
                    break;
                case "远程" :jieguo2.setTextColor(context.getResources().getColor(R.color.red));
                    jieguo2.setText("远程,");
                    break;
                case "打脸" :jieguo3.setTextColor(context.getResources().getColor(R.color.blue));
                    jieguo3.setText("打脸");
                    break;
                case "打你妹" :jieguo3.setTextColor(context.getResources().getColor(R.color.red));
                    jieguo3.setText("打你妹");
                    break;
            }
        }
        return qs_view;
    }
}
