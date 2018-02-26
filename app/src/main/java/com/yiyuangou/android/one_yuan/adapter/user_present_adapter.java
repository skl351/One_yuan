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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.User_present;
import com.yiyuangou.android.one_yuan.page4.user_zengsong_1_1;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class user_present_adapter extends BaseAdapter {
    private Context Mcontext;
    private List<User_present> mlist;

    public user_present_adapter(Context context,List<User_present> list) {
        Mcontext=context;
        mlist=list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(Mcontext).inflate(R.layout.item_user_present,null);
        ImageView item_user_private_img= (ImageView) view.findViewById(R.id.item_user_private_img);
        TextView spmc= (TextView) view.findViewById(R.id.item_present_spmc);
        TextView spje= (TextView) view.findViewById(R.id.item_present_jd);
        TextView text_shouxuy= (TextView) view.findViewById(R.id.item_present_shouxu);
        final String name=mlist.get(position).getSPMC();
        final String money=mlist.get(position).getSPJG();
        text_shouxuy.setText(mlist.get(position).getShouxufei());
        spmc.setText(name);
        spje.setText(money);
        final String img_url=mlist.get(position).getUrl_img();
        final String uuid= mlist.get(position).getUuid();
        ImageLoader.getInstance().displayImage(img_url, item_user_private_img);
       Button button= (Button) view.findViewById(R.id.item_user_present_send_but);
        if(!"true".equals(mlist.get(position).getOk_xia())){
            button.setEnabled(false);
            button.setBackgroundColor(Mcontext.getResources().getColor(R.color.gray));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_zengsong_ok_ma;
                RequestParams requestParams=new RequestParams();
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        System.out.println("没有啦");
                        System.out.println(arg1.toString());
                        try {
                                if("true".equals(arg1.getString("status"))){
                                    Intent intent=new Intent(Mcontext.getApplicationContext(),user_zengsong_1_1.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("money",money);
                                    intent.putExtra("uuid",uuid);
                                    intent.putExtra("img_url",img_url);
                                    Mcontext.startActivity(intent);
                                }else{
                                    try{
                                        Toast.makeText(Mcontext.getApplicationContext(),arg1.getString("result"),Toast.LENGTH_SHORT).show();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }

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
