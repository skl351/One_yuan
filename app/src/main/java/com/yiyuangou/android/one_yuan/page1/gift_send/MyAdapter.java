package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.covics.zxingscanner.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.item_gift_send_v;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class MyAdapter extends BaseAdapter {
	List<item_gift_send_v> items;
	Context context;

	public MyAdapter(Context context, List<item_gift_send_v> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final View view =  LayoutInflater.from(context).inflate(
				R.layout.item_gidt_send, null);
		final String name=items.get(position).getName();
		final String UUID=items.get(position).getUUID();
		final String price=items.get(position).getPrice() ;
		final String info=items.get(position).getInfo();
		final String img=items.get(position).getIMG();
		final String iMg_5=items.get(position).getIMg_5();
		final String gift_info=items.get(position).getGift_info();
		TextView gift_send_text= (TextView) view.findViewById(R.id.gift_send_text);
		gift_send_text.setText(name);
		TextView gifr_send_jd= (TextView) view.findViewById(R.id.gifr_send_jd_text);
		gifr_send_jd.setText(price);
		ImageView imageView= (ImageView) view.findViewById(R.id.item_gift_send_img_small);

		RelativeLayout relativeLayout= (RelativeLayout) view.findViewById(R.id.item_gift_send_img_big);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(MainActivity.screenWidth/2-40,MainActivity.screenWidth/2-40);
		relativeLayout.setLayoutParams(lp);
		ImageLoader.getInstance().displayImage(img, imageView);
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.arg1==1){
					view.setEnabled(true);
				}
			}
		};
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				v.setEnabled(false);
				AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
				asyncHttpClient.setTimeout(20000);
				String url = all_url.url_tuwen;
				RequestParams requestParams = new RequestParams();
				requestParams.put("giftId", UUID);
				asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						super.onSuccess(jsonObject);
						try {
							if ("true".equals(jsonObject.getString("status"))){
								System.out.println(jsonObject);
								final JSONArray js=jsonObject.getJSONArray("giftImgs");
								Thread thread=new Thread(new Runnable() {
									@Override
									public void run() {
										int h=0;
										for (int i=0;i<js.length();i++){

											try {
												h=GetLocalOrNetBitmap(js.getJSONObject(i).getString("IMG")).getHeight()+h;
											} catch (JSONException e) {
												e.printStackTrace();
											}

										}
										Intent intent = new Intent(context, gift_send_v_xiangq.class);
										intent.putExtra("UUID",UUID);
										intent.putExtra("name",name);
										intent.putExtra("price",price);
										intent.putExtra("info",info);
										intent.putExtra("iMg_5",iMg_5);
										intent.putExtra("gift_info",gift_info);
										intent.putExtra("js_lenght",h);
										context.startActivity(intent);
										Message msg=handler.obtainMessage();
										msg.arg1=1;
										handler.sendMessage(msg);
									}
								});
								thread.start();



							}else{
								Intent intent = new Intent(context, gift_send_v_xiangq.class);
								intent.putExtra("UUID",UUID);
								intent.putExtra("name",name);
								intent.putExtra("price",price);
								intent.putExtra("info",info);
								intent.putExtra("iMg_5",iMg_5);
								intent.putExtra("gift_info",gift_info);
								intent.putExtra("js_lenght",0);
								view.setEnabled(true);
								context.startActivity(intent);
										System.out.println(jsonObject.getString("result"));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable throwable) {
						super.onFailure(throwable);
						Toast.makeText(context,"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
					}


				});

			}
		});


		return view;
	}


	public static Bitmap GetLocalOrNetBitmap(String url)
	{
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try
		{
			in = new BufferedInputStream(new URL(url).openStream(), 2*1024);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 2*1024);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[2*1024];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

}
