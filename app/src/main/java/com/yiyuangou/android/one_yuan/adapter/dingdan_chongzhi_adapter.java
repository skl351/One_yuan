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

import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class dingdan_chongzhi_adapter extends BaseAdapter {
    private Context Mcontext;
    List<chongzhi_info> data;

    public dingdan_chongzhi_adapter(Context context,List<chongzhi_info> list){
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
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_dingdan_chongzhi, null);

        String DDBH=data.get(position).getDDHM();
        String CZJE=data.get(position).getCZJE();
       String[] zz= CZJE.split("\\.");
        CZJE=zz[0]+"元";
        String ZFLX=data.get(position).getZFLX();
        if ("1".equals(ZFLX)){
            ZFLX="金豆充值";
        }else if("2".equals(ZFLX)){
            ZFLX="余额充值";
        }
        String CZFS=data.get(position).getCZFS();
        if ("1".equals(CZFS)){
            CZFS="京东支付";
        }else if("2".equals(CZFS)){
            CZFS="微信支付";
        }else if("3".equals(CZFS)){
            CZFS="银联支付";
        }
        String CZZT=data.get(position).getCZZT();
        if ("0".equals(CZZT)){
            CZZT="未支付";
        }else if("2".equals(CZZT)){
            CZZT="已支付";
        }
        String XDSJ=data.get(position).getXDSJ();

        LinearLayout dingdan_chongzhi_DDBH_line= (LinearLayout) view.findViewById(R.id.dingdan_chongzhi_DDBH_line);
        LinearLayout dingdan_chongzhi_CZJE_line= (LinearLayout) view.findViewById(R.id.dingdan_chongzhi_CZJE_line);
        LinearLayout dingdan_chongzhi_ZFLX_line= (LinearLayout) view.findViewById(R.id.dingdan_chongzhi_ZFLX_line);
        LinearLayout dingdan_chongzhi_CZFS_line= (LinearLayout) view.findViewById(R.id.dingdan_chongzhi_CZFS_line);
        LinearLayout dingdan_chongzhi_CZZT_line= (LinearLayout) view.findViewById(R.id.dingdan_chongzhi_CZZT_line);
        TextView dingdan_chongzhi_xiadantime_text= (TextView) view.findViewById(R.id.dingdan_chongzhi_xiadantime_text);
        dingdan_chongzhi_xiadantime_text.setText(XDSJ);

        TextView textView_DDBH=new TextView(Mcontext.getApplicationContext());
        textView_DDBH.setText(DDBH);
        textView_DDBH.setTextSize(12);
        textView_DDBH.setTextColor(Mcontext.getResources().getColor(R.color.black));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER;
        textView_DDBH.setLayoutParams(lp);
        dingdan_chongzhi_DDBH_line.addView(textView_DDBH);

        TextView textView_CZJE=new TextView(Mcontext.getApplicationContext());
        textView_CZJE.setText(CZJE);
        textView_CZJE.setTextSize(12);
        textView_CZJE.setTextColor(Mcontext.getResources().getColor(R.color.black));
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity= Gravity.CENTER;
        textView_CZJE.setLayoutParams(lp2);
        dingdan_chongzhi_CZJE_line.addView(textView_CZJE);

        TextView textView_ZFLX=new TextView(Mcontext.getApplicationContext());
        textView_ZFLX.setText(ZFLX);
        textView_ZFLX.setTextSize(12);
        textView_ZFLX.setTextColor(Mcontext.getResources().getColor(R.color.black));
        LinearLayout.LayoutParams lp3=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp3.gravity= Gravity.CENTER;
        textView_ZFLX.setLayoutParams(lp);
        dingdan_chongzhi_ZFLX_line.addView(textView_ZFLX);

        TextView textView_CZFS=new TextView(Mcontext.getApplicationContext());
        textView_CZFS.setText(CZFS);
        textView_CZFS.setTextSize(12);
        textView_CZFS.setTextColor(Mcontext.getResources().getColor(R.color.black));
        LinearLayout.LayoutParams lp4=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp4.gravity= Gravity.CENTER;
        textView_CZFS.setLayoutParams(lp4);
        dingdan_chongzhi_CZFS_line.addView(textView_CZFS);

        TextView textView_CZZT=new TextView(Mcontext.getApplicationContext());
        textView_CZZT.setText(CZZT);
        textView_CZZT.setTextSize(12);
        textView_CZZT.setTextColor(Mcontext.getResources().getColor(R.color.red));
        LinearLayout.LayoutParams lp5=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp5.gravity= Gravity.CENTER;
        textView_CZZT.setLayoutParams(lp5);
        dingdan_chongzhi_CZZT_line.addView(textView_CZZT);






        return view;
    }
}
