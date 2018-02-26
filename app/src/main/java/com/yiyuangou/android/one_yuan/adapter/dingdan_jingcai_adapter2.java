package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
import com.yiyuangou.android.one_yuan.page1.pk_ten.jingcai_dingdan_xiangq2;
import com.yiyuangou.android.one_yuan.page4.jingcai_dingdan_xiangq;

import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class dingdan_jingcai_adapter2 extends BaseAdapter {
    private String biaozhun_rq;
    private boolean flag=false;

    List<jingcai_item> lists;
    Context context;
    int zzhy;

    public dingdan_jingcai_adapter2(Context context, List<jingcai_item> lists){
        this. lists=lists;
        this.context=context;
        zzhy=0;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    private LinearLayout view;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.jingcai_dingdan_da_item, null);
        TextView jishiqi= (TextView) view.findViewById(R.id.digndan_jishiqi);
        View jingcai_kjrq_view= view.findViewById(R.id.jingcai_kjrq_view);
        TextView hmmc= (TextView) view.findViewById(R.id.hmmc);
        TextView kjrq= (TextView) view.findViewById(R.id.jingcai_kjrq);
        hmmc.setText(lists.get(position).getHMMC());
        if(!"false".equals(lists.get(position).getDate())){
            jingcai_kjrq_view.setVisibility(View.VISIBLE);
            kjrq.setText(lists.get(position).getDate());
        }
        jishiqi.setText(String.valueOf(position+1));
        TextView jingcai_xiazhu_qs= (TextView)view.findViewById(R.id.jingcai_xiazhu_qs);
        TextView jingcai_xiazhu_jd= (TextView) view.findViewById(R.id.jingcai_xiazhu_jd);
        TextView jingcai_zhongjiang_shifou= (TextView) view.findViewById(R.id.jingcai_zhongjiang_shifou);
        View quanbu=view.findViewById(R.id.quanbu);
        final  String uuid=lists.get(position).getUUID();
        final int a=position;
        quanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, jingcai_dingdan_xiangq2.class);
//                intent.putExtra("hmmc",lists.get(a).getHMMC());
                intent.putExtra("UUID", uuid);
                context.startActivity(intent);
            }
        });
        String qs=lists.get(position).getQs();
        String gmzj=lists.get(position).getGmzj();
        jingcai_xiazhu_qs.setText(qs);
        jingcai_xiazhu_jd.setText(gmzj);

        String shifou_zj=lists.get(position).getDazt();
        if("0".equals(shifou_zj)){
            jingcai_zhongjiang_shifou.setText("未开奖");
            quanbu.setEnabled(false);
        }else if("1".equals(shifou_zj)){
            jingcai_zhongjiang_shifou.setText("未中奖");
        }else{
            String syjd_s= lists.get(position).getSyjd();
            String gmzj_s= lists.get(position).getGmzj();
            jingcai_zhongjiang_shifou.setText("中奖"+String.valueOf(Integer.parseInt(syjd_s)+Integer.parseInt(gmzj_s)));
            jingcai_zhongjiang_shifou.setTextColor(context.getResources().getColor(R.color.red));
        }


        return view;

    }



}
