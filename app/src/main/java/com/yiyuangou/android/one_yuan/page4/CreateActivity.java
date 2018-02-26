package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yiyuangou.android.one_yuan.MyApplication;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 生成二维码
 * 
 * @author 蔡有飞 E-mail: caiyoufei@looip.cn
 * @version 创建时间：2014-2-12 上午10:38:20
 * 
 */
public class CreateActivity extends Activity {
	private String url_fengxiang;
	private String url_title;
	private String url_content;
	private IWXAPI api;
	String http_url= all_url.url_erweima;
	// 整体布局
	private LinearLayout cLinearLayout;
	// 生成二维码按钮
	private Button cButton;
	// 二维码数据输入框
	private EditText cEditText;
	// 二维码生成图片
	private ImageView cImageView;
	// 二维码图片
	private Bitmap cBitmap;
	// 分享二维码按钮
	private Button cButtonShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.erweima_view);
		api= WXAPIFactory.createWXAPI(MyApplication.getAppContext(), all_url.APP_ID);
		api.registerApp(all_url.APP_ID);
		init();
		// 生成带CF的二维码
		try {

			cBitmap = createArtwork(http_url+"?mobile="+ User.getuser().getUser_phone_number());
		} catch (WriterException e) {
			e.printStackTrace();
		}
		// 生成纯红色的二维码
		// Bitmap cBitmap = Create2DCode(cEditText.getText()
		// .toString().trim());
		if (cBitmap != null) {
			cImageView.setImageBitmap(cBitmap);
			cImageView.invalidate();
			cImageView.setVisibility(View.VISIBLE);
		}

	}

	private Button bt_fenxiang;
	private void init() {

		cImageView= (ImageView) findViewById(R.id.image_1_1);
		bt_fenxiang= (Button) findViewById(R.id.bt_fenxiang);
		bt_fenxiang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
				String url = all_url.url_fenxiang_xiangguang;
				RequestParams requestParams = new RequestParams();
				asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						super.onSuccess(jsonObject);
						try {
							if ("true".equals(jsonObject.getString("status"))) {
								System.out.println(jsonObject);
								String str=jsonObject.getJSONObject("config").getString("PZZ");
								String[] st=str.split("\\|");
								url_title=st[0];
								url_content=st[1];
								url_fengxiang=st[2];
								init_fenxiang();
							} else {
								Toast.makeText(CreateActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable throwable) {
						super.onFailure(throwable);
					}
				});



			}
		});
	}
	private void init_fenxiang() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setIcon(R.mipmap.ic_launcher);//设置图标
//                builder.setTitle("标题");
		View view= LayoutInflater.from(this).inflate(R.layout.weixin_share, null);

		View friend= view.findViewById(R.id.weixin_share_top_friend);
		View cried= view.findViewById(R.id.weixin_share_top_cried);
		View no_do=view.findViewById(R.id.weixin_no_do);
		friend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WXWebpageObject webpage=new WXWebpageObject();
				webpage.webpageUrl=url_fengxiang+User.getuser().getUser_phone_number();
				//2创建一个mess对象
				WXMediaMessage msg=new WXMediaMessage(webpage);
				msg.title=url_title;
				msg.description=url_content;
				//2设置缩率图
				Bitmap thumb2= Bitmap.createScaledBitmap(cBitmap,150,150,true);
				msg.thumbData=bmpToByteArray(thumb2,true);
				//4
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction=buildTransaction("webpage");
				req.message=msg;
				req.scene = SendMessageToWX.Req.WXSceneSession;
				Toast.makeText(MyApplication.getAppContext(), String.valueOf(api.sendReq(req)), Toast.LENGTH_SHORT).show();
//				WXImageObject imageObject=new WXImageObject(cBitmap);
//				WXMediaMessage msg_msg=new WXMediaMessage();
//				msg_msg.mediaObject=imageObject;
//
//				Bitmap thumb= Bitmap.createScaledBitmap(cBitmap,150,150,true);
//				msg_msg.thumbData=bmpToByteArray(thumb,true);
//				SendMessageToWX.Req req = new SendMessageToWX.Req();
//				req.message = msg_msg;
//				req.transaction = buildTransaction("img");
//				req.scene = SendMessageToWX.Req.WXSceneSession;
//				Toast.makeText(MyApplication.getAppContext(), String.valueOf(api.sendReq(req)), Toast.LENGTH_SHORT).show();

			}
		});
		cried.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WXWebpageObject webpage=new WXWebpageObject();
				webpage.webpageUrl=url_fengxiang+User.getuser().getUser_phone_number();
				//2创建一个mess对象
				WXMediaMessage msg=new WXMediaMessage(webpage);
				msg.title=url_title;
				msg.description=url_content;
				//2设置缩率图

				Bitmap thumb2= Bitmap.createScaledBitmap(cBitmap,150,150,true);
				msg.thumbData=bmpToByteArray(thumb2,true);
				//4
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction=buildTransaction("webpage");
				req.message=msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				Toast.makeText(MyApplication.getAppContext(), String.valueOf(api.sendReq(req)), Toast.LENGTH_SHORT).show();

			}
		});
		no_do.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Runtime runtime = Runtime.getRuntime();
					runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
				} catch (IOException e) {
					Log.e("Exception when doBack", e.toString());
				}
			}
		});
		final AlertDialog alert = builder.create();
		alert.setView(view, 0, 0, 0, -10);
		alert.show();
	}

	private String buildTransaction(final String type){
		return (type==null)?String.valueOf(System.currentTimeMillis()):type+System.currentTimeMillis();
	}

	/**
	 * 字符串生成二维码
	 * 
	 * @version 更新时间：2014-2-12 上午11:08:59
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public Bitmap Create2DCode(String str) throws WriterException {
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();

		final int WHITE = 0xFFFFFFFF;
		// 整体为黑色
		// final int BLACK = 0xFF000000;
		final int RED = 0xFFFF0000;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = RED;
					// pixels[y * width + x-2] = BLACK ;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		bitmap.setPixel(0, 0, WHITE);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 生成带有其他装饰的二维码图片
	 * 
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public Bitmap createArtwork(String str) throws WriterException {
		Bitmap res = Bitmap.createBitmap(300, 300, Config.ARGB_8888);

		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();

		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;
		//去掉cf
		final int RED = 0xFFFF0000;
		final int BLUE = 0xFF0000FF;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					/*
					// 绘制一个CF样式
					if ((x > 60 && x < 120 && y > 73 && y < 77)
							|| (x > 58 && x < 62 && y >= 75 && y <= 225)
							|| (x > 60 && x < 120 && y > 223 && y < 227)) {
						pixels[y * width + x] = RED;
						// pixels[y * width + x-2] = BLACK ;
					} else if ((x > 180 && x < 240 && y > 73 && y < 77)
							|| (x > 178 && x < 182 && y >= 75 && y <= 225)
							|| (x > 180 && x < 240 && y > 148 && y < 152)) {
						pixels[y * width + x] = BLUE;
					} else {
						pixels[y * width + x] = BLACK;
					}
					*/
					pixels[y * width + x] = BLACK;
				} else {
					pixels[y * width + x] = WHITE;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(300, 300, Config.ARGB_8888);
		bitmap.setPixel(0, 0, WHITE);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		// 绘制二维码图片
		Canvas canvas = new Canvas(res);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawBitmap(bitmap, 0, 0, null);

		// 二维码上添加图片
		// Paint paint = new Paint();
		// paint.setARGB(128, 128, 0, 0);
		// Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// canvas.drawBitmap(bitmap2, 0, 0, paint);
		return res;
	}

	/**
	 * 执行分享
	 * 
	 * @version 更新时间：2014-2-12 下午1:31:44
	 * @param content
	 */
	private void myshare(String content) {
		// 获取图片所在位置的uri
		Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
				getContentResolver(), cBitmap, null, null));
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		if (uri != null) {
			shareIntent.setType("image/*");
			shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
			// 当用户选择短信时使用sms_body取得文字
			// shareIntent.putExtra("sms_body", content);
		} else {
			shareIntent.setType("text/plain");
		}
		shareIntent.putExtra(Intent.EXTRA_TEXT, content);
		// 自定义选择框的标题
		// startActivity(Intent.createChooser(shareIntent, "邀请好友"));
		// 系统默认标题
		startActivity(shareIntent);
	}
	private byte[] bmpToByteArray(final Bitmap bitmap,final boolean needRecycle){
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
		if(needRecycle){
			bitmap.recycle();
		}
		byte[] result=output.toByteArray();
		try{
			output.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
