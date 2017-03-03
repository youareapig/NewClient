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
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunzhidong.newclient1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dell on 2017/3/2.
 */
public class Vectory extends Fragment implements CompoundButton.OnCheckedChangeListener{
    @BindView(R.id.mon1)
    CheckBox mon1;
    @BindView(R.id.tue1)
    CheckBox tue1;
    @BindView(R.id.wed1)
    CheckBox wed1;
    @BindView(R.id.thurs1)
    CheckBox thurs1;
    @BindView(R.id.fri1)
    CheckBox fri1;
    @BindView(R.id.sat1)
    CheckBox sat1;
    @BindView(R.id.sun1)
    CheckBox sun1;
    @BindView(R.id.noon1)
    RadioButton noon1;
    @BindView(R.id.night1)
    RadioButton night1;
    @BindView(R.id.start1)
    TextView start1;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vectoryfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mon1.setOnCheckedChangeListener(this);
        tue1.setOnCheckedChangeListener(this);
        wed1.setOnCheckedChangeListener(this);
        thurs1.setOnCheckedChangeListener(this);
        fri1.setOnCheckedChangeListener(this);
        sat1.setOnCheckedChangeListener(this);
        sun1.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.start1)
    public void onClick() {
        Log.e("tag", "开始----------------");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /**
         * 多选按钮监听*/
        if (mon1.isChecked() || tue1.isChecked() || wed1.isChecked() || thurs1.isChecked() || fri1.isChecked() || sat1.isChecked() || sun1.isChecked()) {
            start1.setBackgroundResource(R.drawable.start1);
            start1.setEnabled(true);
        } else {
            start1.setBackgroundResource(R.drawable.start);
            start1.setEnabled(false);
        }
    }
}
