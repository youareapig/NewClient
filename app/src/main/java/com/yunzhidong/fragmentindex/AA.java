package com.yunzhidong.fragmentindex;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yunzhidong.newclient1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dell on 2017/3/2.
 */
public class AA extends Fragment implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.mon)
    CheckBox mon;
    @BindView(R.id.tue)
    CheckBox tue;
    @BindView(R.id.wed)
    CheckBox wed;
    @BindView(R.id.thurs)
    CheckBox thurs;
    @BindView(R.id.fri)
    CheckBox fri;
    @BindView(R.id.sat)
    CheckBox sat;
    @BindView(R.id.sun)
    CheckBox sun;
    @BindView(R.id.start)
    TextView start;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aafragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mon.setOnCheckedChangeListener(this);
        tue.setOnCheckedChangeListener(this);
        wed.setOnCheckedChangeListener(this);
        thurs.setOnCheckedChangeListener(this);
        fri.setOnCheckedChangeListener(this);
        sat.setOnCheckedChangeListener(this);
        sun.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.start)
    public void onClick() {
        Log.e("tag", "开始----------------");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/**
 * 多选按钮监听*/
        if (mon.isChecked() || tue.isChecked() || wed.isChecked() || thurs.isChecked() || fri.isChecked() || sat.isChecked() || sun.isChecked()) {
            start.setBackgroundResource(R.drawable.start1);
            start.setEnabled(true);
        } else {
            start.setBackgroundResource(R.drawable.start);
            start.setEnabled(false);
        }
    }
}
