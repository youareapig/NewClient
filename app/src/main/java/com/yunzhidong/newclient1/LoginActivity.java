package com.yunzhidong.newclient1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.textTel)
    TextView textTel;
    @BindView(R.id.editTel1)
    EditText editTel1;
    @BindView(R.id.textCode1)
    TextView textCode1;
    @BindView(R.id.editCode1)
    EditText editCode1;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.forgetpassword)
    TextView forgetpassword;
    @BindView(R.id.newuserRegist)
    TextView newuserRegist;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.forgetpassword, R.id.newuserRegist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetpassword:
                Intent intent=new Intent(LoginActivity.this,ForgetPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.newuserRegist:
                break;
        }
    }
}
