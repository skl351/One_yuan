package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.chongzhi_info;
import com.yiyuangou.android.one_yuan.bean.item_jiangli;
import com.yiyuangou.android.one_yuan.util.Util_string;

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class dingdan_jiangli_adapter extends BaseAdapter {
    private Context Mcontext;
    List<item_jiangli> data;

    public dingdan_jiangli_adapter(Context context, List<item_jiangli> list){
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
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_jiangli, null);

        TextView money= (TextView) view.findViewById(R.id.money);
        TextView type= (TextView) view.findViewById(R.id.type);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView phone= (TextView) view.findViewById(R.id.phone);

        money.setText(data.get(position).getMoney());
        String type_text="";
        switch (data.get(position).getType()){
            case "1":type_text="充值送";
                break;
            case "2":type_text="豆商购买";
                break;
            case "3":type_text="玩家玩20局送";
                break;
        }
        type.setText(type_text);
        name.setText(data.get(position).getName());
        phone.setText(Util_string.jiequ(data.get(position).getPhone(),0,3)+"****"+Util_string.jiequ2(data.get(position).getPhone(),7));
        return view;
    }
}
