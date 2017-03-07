package com.yunzhidong.newclient1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingPasswordActivity extends AppCompatActivity {
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editRePassword)
    EditText editRePassword;
    @BindView(R.id.textTel3)
    TextView textTel3;
    @BindView(R.id.textTel4)
    TextView textTel4;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.textAgreement)
    TextView textAgreement;
    @BindView(R.id.textRegeist)
    TextView textRegeist;
    private Unbinder unbinder;
    private String stringPwd, stringRepwd,mIdentify;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        unbinder = ButterKnife.bind(this);
        Intent intent=getIntent();
        mIdentify=intent.getStringExtra("identify");
        Log.e("tag",mIdentify);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    aMapLocation.getCountry();//国家信息
                    aMapLocation.getProvince();//省信息
                    aMapLocation.getCity();//城市信息
                    aMapLocation.getDistrict();//城区信息
                    aMapLocation.getStreet();//街道信息
                    aMapLocation.getStreetNum();//街道门牌号信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                    aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    aMapLocation.getFloor();//获取当前室内定位的楼层
                    //aMapLocation.getGpsStatus();//获取GPS的当前状态;
                    Log.e("tag","定位成功"+"经度："+aMapLocation.getLongitude()+"纬度："+aMapLocation.getLatitude());
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("tag","定位失败------------:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }else {
                Log.e("tag","aMapLocation为空");
            }
        }
    };

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    @OnClick({R.id.textAgreement, R.id.textRegeist})
    public void onClick(View view) {
        stringPwd = editPassword.getText().toString().trim();
        stringRepwd = editRePassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.textAgreement:
                break;
            case R.id.textRegeist:
                if (TextUtils.isEmpty(stringPwd) || TextUtils.isEmpty(stringRepwd)) {
                    new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入密码").setPositiveButton("OK", null).show().setCancelable(false);
                } else {
                    if (stringPwd.length() < 6 || stringRepwd.length() < 6) {
                        new AlertDialog.Builder(this).setTitle("友情提示").setMessage("密码至少由6位字符或数字组成").setPositiveButton("OK", null).show().setCancelable(false);
                    } else {
                        if (!stringPwd.equals(stringRepwd)) {
                            new AlertDialog.Builder(this).setTitle("友情提示").setMessage("两次密码输入不一致，请重新输入").setPositiveButton("OK", null).show().setCancelable(false);
                        } else {
                            Log.e("tag", "注册成功");
                        }
                    }
                }
                break;
        }
    }
}
