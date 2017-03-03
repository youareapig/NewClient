package com.yunzhidong.fragmentindex;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunzhidong.newclient1.R;

/**
 * Created by Dell on 2017/3/2.
 */
public class Booking extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bookingfragment,container,false);
        return view;
    }
}
