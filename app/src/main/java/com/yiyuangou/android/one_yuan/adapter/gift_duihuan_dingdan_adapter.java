package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.gift_dingdan_item;

import java.util.List;

/**
 * Created by android on 2016/4/21.
 */
public class gift_duihuan_dingdan_adapter extends BaseAdapter {
    private Context context;
    private List<gift_dingdan_item> data;

    public gift_duihuan_dingdan_adapter(Context context,List<gift_dingdan_item> list){
        this.data=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_gift_dingdan_jiaohuan_item, null);
        ImageView imageView= (ImageView) view.findViewById(R.id.img);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView status= (TextView) view.findViewById(R.id.status);
        TextView jd= (TextView) view.findViewById(R.id.jd);
        name.setText(data.get(position).getName());
        jd.setText(data.get(position).getJd());
        if("0".equals(data.get(position).getStatus())){
            status.setText("未发货");
        }else if("1".equals(data.get(position).getStatus())){
            status.setText("运输中");
        }else{
            status.setText("已收货");
        }
        ImageLoader.getInstance().displayImage(data.get(position).getImg(),imageView);



        return view;
    }
}
