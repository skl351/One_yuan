package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.item_gift_send_v;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by android on 2016/5/26.
 */
public class tuwenxiangp_view extends Fragment   {
    private View view;
    private LinearLayout add_img;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.tuwenxiangp_layout, container, false);
        add_img= (LinearLayout) view.findViewById(R.id.add_img);
        init_wangluo();

        return view;
    }


    static  int height=0;
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_tuwen;
        RequestParams requestParams = new RequestParams();
        requestParams.put("giftId", gift_send_v_xiangq.UUID);
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        System.out.println(jsonObject);
                        JSONArray js=jsonObject.getJSONArray("giftImgs");

                        for(int i=0;i<js.length();i++){
                            ImageView imageView=new ImageView(getActivity());
                            ImageLoader.getInstance().displayImage(js.getJSONObject(i).getString("IMG"),imageView);
                            add_img.addView(imageView);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        }

                        int w = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        int h = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        add_img.measure(w, h);

                    }else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(getActivity(),"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
            }


        });
    }
}
