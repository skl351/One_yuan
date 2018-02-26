package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page4.xiangce.UploadFileTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by android on 2016/4/16.
 */
public class user_touxiang_cunfang extends Activity {
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView mFace;
    private Bitmap bitmap;
    private View touxiang_topbar;
    private View back;

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_touxiang_cunfang);
        mFace = (ImageView) this.findViewById(R.id.iv_image);
        touxiang_topbar=findViewById(R.id.touxiang_topbar);
        back=touxiang_topbar.findViewById(R.id.logreg_left);
        init_back();
        mFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chanshengdia();
            }
        });

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
    /*
       * 上传图片
       */
    public void upload(View view) {
        if(picPath!=null){
            UploadFileTask uploadFileTask=new UploadFileTask(this);
            uploadFileTask.execute(picPath);
            finish();
        }else{
            Toast.makeText(this,"图片为空",Toast.LENGTH_SHORT).show();
        }




    }
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }


    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//
//        // 判断存储卡是否可以用，可用进行存储
//        if (hasSdcard()) {
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(new File(Environment
//                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
//        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }
    private String picPath = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断是否使系统图片
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                try {
                    String[] pojo = {MediaStore.Images.Media.DATA};

                    Cursor cursor = managedQuery(uri, pojo, null, null,null);
                    if(cursor!=null)
                    {
                        ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(colunm_index);
                        /***
                         * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，这样的话，我们判断文件的后缀名
                         * 如果是图片格式的话，那么才可以
                         */
                        if(path.endsWith("jpg")||path.endsWith("png"))
                        {
                            picPath = path;
                            crop(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            mFace.setImageBitmap(bitmap);
                        }else{
                            alert();
                        }
                    }else{
                        alert();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else if(requestCode == PHOTO_REQUEST_CAMERA){
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
                Bundle bundle=data.getExtras();
                if(bundle!=null){
                    Bitmap bitmap= (Bitmap) bundle.get("data");
                    picPath=saveBitmapFile(bitmap);
                    mFace.setImageBitmap(bitmap);
                }

            }


        }else if(requestCode == PHOTO_REQUEST_CUT){
            if (data != null) {
                Bundle bundle=data.getExtras();
                if(bundle!=null){
                    Bitmap bitmap= (Bitmap) bundle.get("data");
                    picPath=saveBitmapFile(bitmap);
                    mFace.setImageBitmap(bitmap);
                }

            }



        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    public String saveBitmapFile(Bitmap bitmap){
        File file=new File(getPhotoPath()+PHOTO_FILE_NAME);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getPhotoPath()+PHOTO_FILE_NAME;
    }

    /**
     * 剪切图片
     *
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    private void chanshengdia() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.picture_share,null);
        View xitongxiangji= view.findViewById(R.id.pic_share_top_friend);//系统相机
        View xitongpic= view.findViewById(R.id.pic_share_top_cried);//照片
        View no_do=view.findViewById(R.id.pic_no_do);
        final AlertDialog alert = builder.create();
        alert.setView(view, 0, 0, 0, -10);
        alert.show();
        xitongpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
                gallery();
            }
        });

        no_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        //相机拍照
        xitongxiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                camera();
            }
        });





    }
    private void alert()
    {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                picPath = null;
                            }
                        })
                .create();
        dialog.show();
    }
}
