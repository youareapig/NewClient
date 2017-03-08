package com.yunzhidong.newclient1;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunzhidong.utils.Global;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingNameActivity extends AppCompatActivity {
    @BindView(R.id.uploadhead)
    ImageView uploadhead;
    @BindView(R.id.userInfoOK)
    TextView userInfoOK;
    @BindView(R.id.inputname)
    EditText inputname;
    private Unbinder unbinder;
    private Bitmap bitmap,bitmap1;
    private String base64,base64_1,fileName;
    private Dialog dialog;
    private  Uri uri;
    private ContentResolver contentResolver;
    private FileOutputStream[] fileOutputStream = {null};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_name);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.uploadhead, R.id.userInfoOK})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uploadhead:
                dialog = new Dialog(SettingNameActivity.this, R.style.mydialog);
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control, null);
                linearLayout.findViewById(R.id.photograph).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //拍摄
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 2);
                    }
                });
                linearLayout.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //相册
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });
                linearLayout.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setContentView(linearLayout);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.dialogstyle);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.x = 0;// 新位置X坐标  
                layoutParams.y = 20;// 新位置Y坐标  
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;// 宽度 
                linearLayout.measure(0, 0);
                layoutParams.height = linearLayout.getMeasuredHeight();
                layoutParams.alpha = 9f;// 透明度  
                window.setAttributes(layoutParams);
                dialog.show();
                break;
            case R.id.userInfoOK:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            uri = data.getData();
            contentResolver = this.getContentResolver();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, filePathColumns, null, null, null);
            cursor.moveToFirst();
            //int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            //final String imagePath = cursor.getString(columnIndex);
            try {
                  bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                  base64=Bitmap2StrByBase64(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            RequestParams params=new RequestParams(Global.global().getTestUrl()+"/accounts/settings/avatar");
            params.addBodyParameter("token","4b873e236a97e08deead72747df62425");
            params.addBodyParameter("file",base64);
            Log.e("tag","base"+base64);
            params.setMultipart(true);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("tag","请求成功"+result);
                        uploadhead.setImageBitmap(bitmap);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("tag","请求失败");

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        } else if (requestCode == 2) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            bitmap1 = (Bitmap) bundle.get("data");

            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            fileName = "/sdcard/myImage/" + name;
            base64_1=Bitmap2StrByBase64(bitmap1);
            RequestParams params=new RequestParams(Global.global().getTestUrl()+"/accounts/settings/avatar");
            params.addBodyParameter("token","4b873e236a97e08deead72747df62425");
            params.addBodyParameter("file",base64_1);
            Log.e("tag","参数说明:"+base64_1);
            params.setMultipart(true);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("tag","请求成功"+result);
                    try {
                        fileOutputStream[0] = new FileOutputStream(fileName);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream[0]);
                        uploadhead.setImageBitmap(bitmap1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fileOutputStream[0].flush();
                            fileOutputStream[0].close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("tag","请求失败");

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });


        }
    }
    //TODO 将位图转换成base64编码
    private String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩  
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
