package com.yiyuangou.android.one_yuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.dingdan_zengsong_item;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by android on 2016/4/13.
 */
public class user_preent_3_adapter extends BaseAdapter {
    private Context mContext;
    private List<dingdan_zengsong_item> data;

    public  user_preent_3_adapter(Context context,List<dingdan_zengsong_item> list){
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
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_user_present3,null);
        TextView item_present_name= (TextView) view.findViewById(R.id.item_present_name);
        TextView item_present_phone= (TextView) view.findViewById(R.id.item_present_phone);
        TextView name= (TextView) view.findViewById(R.id.item_present_spmc);
        TextView money= (TextView) view.findViewById(R.id.item_present_jd);
        final Button button= (Button) view.findViewById(R.id.item_user_present_send_but);
        ImageView img= (ImageView) view.findViewById(R.id.item_user_private_img);
        TextView te_shidao= (TextView) view.findViewById(R.id.item_present_jd_shidao);
        item_present_name.setText(data.get(position).getName());
        item_present_phone.setText(data.get(position).getOther_phone());
        te_shidao.setText(String.valueOf(Integer.parseInt(data.get(position).getAll_jd())-Integer.parseInt(data.get(position).getSdje())));
        name.setText(data.get(position).getSPMC());
        money.setText(data.get(position).getAll_jd());
        ImageLoader.getInstance().displayImage(data.get(position).getUrl_img(), img);
        if (!"0".equals(data.get(position).getZT())){
            btn_cls.bt_disable(button);
        }
        final String uuid=data.get(position).getUuid();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(button);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_jd_dh;
                RequestParams requestParams=new RequestParams();
                requestParams.put("donatelog_id",uuid );
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        System.out.println(arg1.toString());
                        try {
                            if ("true".equals(arg1.getString("status"))) {

                                btn_cls.bt_disable(button);
                            }else{
                                btn_cls.bt_endisable(button);
                            }
                            Toast.makeText(mContext.getApplicationContext(),arg1.getString("result"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub
                        super.onFailure(arg0);
                    }
                });
            }
        });
        return view;
    }
}
