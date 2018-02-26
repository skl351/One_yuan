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
import com.yiyuangou.android.one_yuan.bean.dingdan_zengsong_item;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class dingdan_zengdong_adapter extends BaseAdapter {
    private Context mContext;
    private List<dingdan_zengsong_item> data;

    public  dingdan_zengdong_adapter(Context context,List<dingdan_zengsong_item> list){
        data=list;
        mContext=context;
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
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_dingdan_zengsong,null);
       TextView money= (TextView) view.findViewById(R.id.item_dingdan_zengsong_money);
       TextView name= (TextView) view.findViewById(R.id.item_dingdan_zengsong_name);
       TextView username= (TextView) view.findViewById(R.id.item_dingdan_zengsong_username);
       TextView otherphone= (TextView) view.findViewById(R.id.item_dingdan_zengsong_otherphone);
       TextView zt= (TextView) view.findViewById(R.id.item_dingdan_zengsong_zt);
        ImageView img= (ImageView) view.findViewById(R.id.item_dingdan_zengdong_img);
        money.setText(data.get(position).getAll_jd());
        name.setText(data.get(position).getSPMC());
        username.setText(data.get(position).getName());
        otherphone.setText(data.get(position).getOther_phone());

        ImageLoader.getInstance().displayImage(data.get(position).getUrl_img(), img);



        return view;
    }
}
