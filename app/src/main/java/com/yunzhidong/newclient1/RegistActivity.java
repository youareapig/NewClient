package com.yunzhidong.newclient1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yunzhidong.utils.ClassPathResource;
import com.yunzhidong.utils.CountDownTimerUtils;
import com.yunzhidong.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegistActivity extends AppCompatActivity {
    @BindView(R.id.textTel)
    TextView textTel;
    @BindView(R.id.editTel)
    EditText editTel;
    @BindView(R.id.textCode)
    TextView textCode;
    @BindView(R.id.editCode)
    EditText editCode;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.regeistNext)
    TextView regeistNext;
    private Unbinder unbinder;
    private String stringCode, stringTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        unbinder = ButterKnife.bind(this);

    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.getCode, R.id.regeistNext})
    public void onClick(View view) {
        stringTel = editTel.getText().toString().trim();
        ClassPathResource classPathResource = new ClassPathResource();
        //TODO 正则表达式判断电话号码
        boolean isPhone = classPathResource.isMobileNO(stringTel);
        switch (view.getId()) {
            case R.id.getCode:
                if (TextUtils.isEmpty(stringTel)) {
                    new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入手机号").setPositiveButton("OK", null).show().setCancelable(false);
                } else {
                    if (isPhone == false) {
                        new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入正确的电话号码").setPositiveButton("OK", null).show().setCancelable(false);

                    }
                    if (isPhone == true) {
                        getVerCode();

                    }
                }
                break;
            case R.id.regeistNext:
                stringCode = editCode.getText().toString().trim();
                if (TextUtils.isEmpty(stringTel) || TextUtils.isEmpty(stringCode)) {
                    new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入手机号或者验证码").setPositiveButton("OK", null).show().setCancelable(false);
                } else {
                    verify();
                }
                break;
        }
    }

    //TODO 获取验证码
    private void getVerCode() {
        RequestParams params = new RequestParams(Global.global().getTestUrl() + "/accounts/register/index");
        params.addBodyParameter("mobile", stringTel);
        Log.e("tag", "电话" + stringTel);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("1")) {
                        //TODO 验证码倒计时
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getCode, 10000, 1000);
                        mCountDownTimerUtils.start();
                        new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("请在3分钟内输入验证码").setPositiveButton("OK", null).show().setCancelable(false);
                    }
                    if (jsonObject.getString("status").equals("2")) {
                        String mData=jsonObject.getString("data");
                        Log.e("tag","图形地址"+mData);
                        //new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("获取图形验证码").setPositiveButton("OK", null).show().setCancelable(false);
                        LayoutInflater inflater=getLayoutInflater();
                        View view=inflater.inflate(R.layout.vercode, (ViewGroup) findViewById(R.id.dialog));
                        ImageView imageView= (ImageView) view.findViewById(R.id.imageCode);
                        final EditText editText= (EditText) view.findViewById(R.id.textCode);
                        ImageLoader.getInstance().displayImage(mData,imageView);
                        new AlertDialog.Builder(RegistActivity.this).setTitle("输入验证码").setView(view).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String imgCode=editText.getText().toString().trim();
                                if (TextUtils.isEmpty(imgCode)){
                                    Toast.makeText(RegistActivity.this,"请输入图中验证码",Toast.LENGTH_SHORT).show();
                                }else {
                                    getImageVerCode(imgCode);
                                }
                            }
                        }).show().setCancelable(false);

                    } else if (jsonObject.getString("status").equals("3")) {
                        //参数错误
                        new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("该手机已经注册").setPositiveButton("OK", null).show().setCancelable(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("服务器故障，请稍后重试").setPositiveButton("OK", null).show().setCancelable(false);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });
    }

    private void verify() {
        RequestParams params = new RequestParams(Global.global().getTestUrl() + "/accounts/captcha/smsverify");
        params.addBodyParameter("code", stringCode);
        params.addBodyParameter("mobile", stringTel);
        Log.e("tag", "参数说明" + stringCode + stringTel);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag",result);
                try {
                    JSONObject json = new JSONObject(result);
                    Log.e("tag",json.getString("status"));
                    if (json.getString("status").equals("1")) {
                        //String mIdentify=json.getString("identify");
                        Intent intent = new Intent(RegistActivity.this, SettingPasswordActivity.class);
                        //intent.putExtra("identify",mIdentify);
                        startActivity(intent);
                        finish();

                    } else if (json.getString("status").equals("3")) {
                        new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("验证码错误，请重新输入").setPositiveButton("OK", null).show().setCancelable(false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("服务器故障，请稍后重试").setPositiveButton("OK", null).show().setCancelable(false);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //TODO 带图形验证码的请求
    private void getImageVerCode(String imageCode) {
        RequestParams params = new RequestParams(Global.global().getTestUrl() + "/accounts/register/index");
        params.addBodyParameter("mobile", stringTel);
        params.addBodyParameter("captcha",imageCode);
        Log.e("tag", "电话" + stringTel+"图形验证码"+imageCode);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("1")) {
                        //TODO 验证码倒计时
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getCode, 10000, 1000);
                        mCountDownTimerUtils.start();
                        new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("请在3分钟内输入验证码").setPositiveButton("OK", null).show().setCancelable(false);
                    }
                     else if (jsonObject.getString("status").equals("3")) {
                        new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("验证码错误").setPositiveButton("OK", null).show().setCancelable(false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new AlertDialog.Builder(RegistActivity.this).setTitle("友情提示").setMessage("服务器故障，请稍后重试").setPositiveButton("OK", null).show().setCancelable(false);

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
