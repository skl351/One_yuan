package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.item_zhuanpan;
import com.yiyuangou.android.one_yuan.bean.room;
import com.yiyuangou.android.one_yuan.page4.queren_shouhuodizhi_zhuanp;

import java.util.List;

/**
 * Created by android on 2016/5/25.
 */
public class dingdan_choujiang_adapter extends BaseAdapter {

    Context mContext;
    List<item_zhuanpan> data;

    public dingdan_choujiang_adapter(Context context,List<item_zhuanpan> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View qs_view= LayoutInflater.from(mContext).inflate(R.layout.item_choujiang_dingdan, null);
        TextView room_name= (TextView) qs_view.findViewById(R.id.room_name);
        ImageView img= (ImageView) qs_view.findViewById(R.id.img);
        TextView name= (TextView) qs_view.findViewById(R.id.name);
        TextView jd= (TextView) qs_view.findViewById(R.id.jd);
        Button status= (Button) qs_view.findViewById(R.id.status);
        ImageLoader.getInstance().displayImage(data.get(position).getImg(),img);
        name.setText(data.get(position).getName());
        room_name.setText(data.get(position).getRoom_name());
        String type=data.get(position).getType();
        jd.setText(data.get(position).getJd());
        if("0".equals(type)){
            //实体
            if("0".equals(data.get(position).getStatus())){
                status.setBackgroundColor(mContext.getResources().getColor(R.color.home_orange));
                status.setText("未发货");
                status.setEnabled(true);

            }
            if("1".equals(data.get(position).getStatus())){
                status.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                status.setText("已领取");
                status.setEnabled(false);
            }
            if("2".equals(data.get(position).getStatus())){
                status.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                status.setText("已发货");
                status.setEnabled(false);
            }
            if("3".equals(data.get(position).getStatus())){
                status.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                status.setText("已完成");
                status.setEnabled(false);

            }
        }
        if("1".equals(type)){
            //金豆

            status.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            status.setText("已领取");
            status.setEnabled(false);
        }
        if("2".equals(type)){
            //任务豆
            status.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            status.setText("已领取");
            status.setEnabled(false);
        }

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button vv=(Button)v;

                Intent intent=new Intent(mContext,queren_shouhuodizhi_zhuanp.class);
                intent.putExtra("uuid",data.get(position).getUuid());

                mContext.startActivity(intent);

            }
        });





        return qs_view;
    }
}