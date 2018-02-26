package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/13.
 */
public class user_zengsong_1_1 extends Activity {
    String img_url;
    String name;
    String money;
    String uuid;
    private ImageView user_zengsong_1_1_img;
    private TextView user_zengsong_1_1_name;
    private TextView user_zengsong_1_1_money;
    private Button user_zengsong_1_1_but;
    private EditText zengsong_edit_phone;
    private View zengsong1_1_topbar;

    private Button jianhao;
    private Button jiahao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_zengsong_1_1);

        init();
        init_yunsuan();
        init_ok();
        init_tongxulu();
        init_back();
//        init_tanchuang();

    }

    private void init_yunsuan() {
        jianhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num.getText().toString().equals("1")){
                    return;
                }
                num.setText(""+(Integer.valueOf(num.getText().toString())-1));
            }
        });
        jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(""+(Integer.valueOf(num.getText().toString())+1));
            }
        });

    }


    public  void init_tanchuang(){
        user_zengsong_1_1_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(user_zengsong_1_1.this);
                final AlertDialog alert = builder.create();
                View view = LayoutInflater.from(user_zengsong_1_1.this).inflate(R.layout.item_tanchuang, null);
                TextView name= (TextView) view.findViewById(R.id.name);
                TextView phone= (TextView) view.findViewById(R.id.phone);
                Button get= (Button) view.findViewById(R.id.getCode);
                EditText edit= (EditText) view.findViewById(R.id.edittext);
                Button quxiao= (Button) view.findViewById(R.id.quxiao);
                Button ok= (Button) view.findViewById(R.id.ok);

                alert.setView(view, 0, 0, 0, -10);
                alert.show();
            }
        });

    }


    private void init_tongxulu() {
        tongxunlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
            }
        });
    }

    String phone_number;
    private TextView num;
    private void init_ok() {
        user_zengsong_1_1_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(user_zengsong_1_1_but);
                phone_number = zengsong_edit_phone.getText().toString();
                String phone_zhenz = "^1\\d{10}$";
                if (!phone_number.matches(phone_zhenz)) {
                    btn_cls.bt_endisable(user_zengsong_1_1_but);
                    Toast.makeText(user_zengsong_1_1.this, "手机号码输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(num.getText().toString().equals("0")){
                    btn_cls.bt_endisable(user_zengsong_1_1_but);
                    Toast.makeText(user_zengsong_1_1.this, "请设置数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_send;
                RequestParams requestParams = new RequestParams();
                requestParams.put("phone", phone_number);//SPID	   YHID    phone  num
                requestParams.put("SPID", uuid);
                requestParams.put("YHID", User.getuser().getUser_uuid());
                requestParams.put("num", num.getText().toString());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        System.out.println(arg1.toString());
                        try {
                            if ("true".equals(arg1.getString("status"))) {
                                btn_cls.bt_endisable(user_zengsong_1_1_but);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(user_zengsong_1_1.this);
                                View view = LayoutInflater.from(user_zengsong_1_1.this).inflate(R.layout.zengsong_ok_zengsong, null);

                                TextView name1 = (TextView) view.findViewById(R.id.zengs_dialo_zhongjian_name);
                                TextView phone = (TextView) view.findViewById(R.id.zengsong_phone);
                                name1.setText(arg1.getString("name"));
                                phone.setText(phone_number);
                                View quxiao = view.findViewById(R.id.zengs_dialo_zhongjian_quxiao);
                                View ok = view.findViewById(R.id.zengs_dialo_zhongjian_ok);
                                quxiao.setOnClickListener(new View.OnClickListener() {
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
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                        String url = all_url.url_send_ok;
                                        RequestParams requestParams = new RequestParams();
                                        requestParams.put("SPID", uuid);
                                        requestParams.put("YHID", User.getuser().getUser_uuid());
                                        requestParams.put("phone", phone_number);
                                        requestParams.put("num", num.getText().toString());
                                        System.out.print(uuid + "[" + User.getuser().getUser_uuid() + phone_number);
                                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int arg0, JSONObject arg1) {
                                                super.onSuccess(arg0, arg1);
                                                System.out.println(arg1.toString());
                                                try {
                                                    if ("true".equals(arg1.getString("status"))) {
                                                        Intent intent = new Intent(user_zengsong_1_1.this, user_zengdong_big.class);
                                                        startActivity(intent);
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
                                final AlertDialog alert = builder.create();
                                alert.setView(view, 0, 0, 0, -10);
                                alert.show();
                                btn_cls.bt_endisable(user_zengsong_1_1_but);

                            }else{
                                Toast.makeText(user_zengsong_1_1.this,arg1.getString("result"),Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(user_zengsong_1_1_but);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub
                        super.onFailure(arg0);
                        btn_cls.bt_endisable(user_zengsong_1_1_but);
                    }
                });

            }
        });
    }

    String username;
    String usernumber;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                zengsong_edit_phone.setText(usernumber);
            }

        }
    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
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
    }
    private View tongxunlu;
    private View back;
    private void init() {
        jiahao= (Button) findViewById(R.id.jiahao);
        jianhao= (Button) findViewById(R.id.jianhao);
        num= (TextView) findViewById(R.id.num);
        zengsong1_1_topbar=findViewById(R.id.zengsong1_1_topbar);
        back= zengsong1_1_topbar.findViewById(R.id.logreg_left);
        tongxunlu=findViewById(R.id.tongxunlu);
        zengsong_edit_phone= (EditText) findViewById(R.id.zengsong_edit_phone);
        zengsong_edit_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        img_url=getIntent().getStringExtra("img_url");
        name=getIntent().getStringExtra("name");
        money=getIntent().getStringExtra("money");
        uuid=getIntent().getStringExtra("uuid");
        user_zengsong_1_1_name= (TextView) findViewById(R.id.user_zengsong_1_1_name);
        user_zengsong_1_1_img= (ImageView) findViewById(R.id.user_zengsong_1_1_img);
        user_zengsong_1_1_money= (TextView) findViewById(R.id.user_zengsong_1_1_money);

        user_zengsong_1_1_name.setText(name);
        user_zengsong_1_1_money.setText(money);
        ImageLoader.getInstance().displayImage(img_url, user_zengsong_1_1_img);
        user_zengsong_1_1_but= (Button) findViewById(R.id.user_zengsong_1_1_but);


    }
}
