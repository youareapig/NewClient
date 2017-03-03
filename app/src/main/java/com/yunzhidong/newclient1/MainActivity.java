package com.yunzhidong.newclient1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhidong.fragmentindex.AA;
import com.yunzhidong.fragmentindex.Booking;
import com.yunzhidong.fragmentindex.Mine;
import com.yunzhidong.fragmentindex.Vectory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fragmentAA)
    RelativeLayout fragmentAA;
    @BindView(R.id.fragmentVictory)
    RelativeLayout fragmentVictory;
    @BindView(R.id.fragmentBooking)
    RelativeLayout fragmentBooking;
    @BindView(R.id.fragmentMine)
    RelativeLayout fragmentMine;
    @BindView(R.id.indexFragment)
    LinearLayout indexFragment;
    @BindView(R.id.imgAA)
    ImageView imgAA;
    @BindView(R.id.textAA)
    TextView textAA;
    @BindView(R.id.imgVictory)
    ImageView imgVictory;
    @BindView(R.id.textVictory)
    TextView textVictory;
    @BindView(R.id.imgBooking)
    ImageView imgBooking;
    @BindView(R.id.textBooking)
    TextView textBooking;
    @BindView(R.id.imgMine)
    ImageView imgMine;
    @BindView(R.id.textMine)
    TextView textMine;
    private FragmentManager fragmentManager;
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private Fragment fragment = new Fragment();
    private List<Fragment> list = new ArrayList<>();
    private int currentIndex = 0;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            list.removeAll(list);
            list.add(fragmentManager.findFragmentByTag(0 + ""));
            list.add(fragmentManager.findFragmentByTag(1 + ""));
            list.add(fragmentManager.findFragmentByTag(2 + ""));
            list.add(fragmentManager.findFragmentByTag(3 + ""));
            restoreFragment();
        } else {
            list.add(new AA());
            list.add(new Vectory());
            list.add(new Booking());
            list.add(new Mine());
            showFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }

    @OnClick({R.id.fragmentAA, R.id.fragmentVictory, R.id.fragmentBooking, R.id.fragmentMine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragmentAA:
                currentIndex = 0;
                showFragment();
                imgAA.setImageResource(R.mipmap.aa1);
                textAA.setTextColor(this.getResources().getColor(R.color.myblue));
                imgVictory.setImageResource(R.mipmap.shengfu);
                textVictory.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgBooking.setImageResource(R.mipmap.dingchang);
                textBooking.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgMine.setImageResource(R.mipmap.wode);
                textMine.setTextColor(this.getResources().getColor(R.color.mywhite));
                break;
            case R.id.fragmentVictory:
                currentIndex = 1;
                showFragment();
                imgAA.setImageResource(R.mipmap.aa);
                textAA.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgVictory.setImageResource(R.mipmap.shengfu1);
                textVictory.setTextColor(this.getResources().getColor(R.color.myblue));
                imgBooking.setImageResource(R.mipmap.dingchang);
                textBooking.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgMine.setImageResource(R.mipmap.wode);
                textMine.setTextColor(this.getResources().getColor(R.color.mywhite));
                break;
            case R.id.fragmentBooking:
                currentIndex = 2;
                showFragment();
                imgAA.setImageResource(R.mipmap.aa);
                textAA.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgVictory.setImageResource(R.mipmap.shengfu);
                textVictory.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgBooking.setImageResource(R.mipmap.dingchang1);
                textBooking.setTextColor(this.getResources().getColor(R.color.myblue));
                imgMine.setImageResource(R.mipmap.wode);
                textMine.setTextColor(this.getResources().getColor(R.color.mywhite));
                break;
            case R.id.fragmentMine:
                currentIndex = 3;
                showFragment();
                imgAA.setImageResource(R.mipmap.aa);
                textAA.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgVictory.setImageResource(R.mipmap.shengfu);
                textVictory.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgBooking.setImageResource(R.mipmap.dingchang);
                textBooking.setTextColor(this.getResources().getColor(R.color.mywhite));
                imgMine.setImageResource(R.mipmap.wode1);
                textMine.setTextColor(this.getResources().getColor(R.color.myblue));
                break;
        }
    }

    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!list.get(currentIndex).isAdded()) {
            transaction
                    .hide(fragment)
                    .add(R.id.indexFragment, list.get(currentIndex), "" + currentIndex);
        } else {
            transaction
                    .hide(fragment)
                    .show(list.get(currentIndex));
        }
        fragment = list.get(currentIndex);
        transaction.commit();

    }

    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            if (i == currentIndex) {
                mBeginTreansaction.show(list.get(i));
            } else {
                mBeginTreansaction.hide(list.get(i));
            }

        }
        mBeginTreansaction.commit();
        fragment = list.get(currentIndex);
    }
}
