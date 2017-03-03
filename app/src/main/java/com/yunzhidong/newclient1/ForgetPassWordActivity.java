package com.yunzhidong.newclient1;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunzhidong.utils.ClassPathResource;
import com.yunzhidong.utils.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgetPassWordActivity extends AppCompatActivity {
    @BindView(R.id.textTel)
    TextView textTel;
    @BindView(R.id.editTel2)
    EditText editTel2;
    @BindView(R.id.textCode)
    TextView textCode;
    @BindView(R.id.editCode2)
    EditText editCode2;
    @BindView(R.id.getCode2)
    TextView getCode2;
    @BindView(R.id.regeistNext2)
    TextView regeistNext2;
    private Unbinder unbinder;
    private String stringCode, stringTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.getCode2, R.id.regeistNext2})
    public void onClick(View view) {
        stringTel = editTel2.getText().toString().trim();
        ClassPathResource classPathResource = new ClassPathResource();
        //TODO 正则表达式判断电话号码
        boolean isPhone = classPathResource.isMobileNO(stringTel);
        switch (view.getId()) {
            case R.id.getCode2:
                if (TextUtils.isEmpty(stringTel)) {
                    new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入手机号").setPositiveButton("OK",null).show().setCancelable(false);
                } else {
                    if (isPhone == false) {
                        new AlertDialog.Builder(this).setTitle("友情提示").setMessage("请输入正确的电话号码").setPositiveButton("OK",null).show().setCancelable(false);

                    }
                    if (isPhone == true) {
                        //TODO 验证码倒计时
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getCode2, 10000, 1000);
                        mCountDownTimerUtils.start();
                    }
                }
                break;
            case R.id.regeistNext2:
                break;
        }
    }
}
