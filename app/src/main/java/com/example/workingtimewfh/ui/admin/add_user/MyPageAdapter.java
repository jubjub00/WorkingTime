package com.example.workingtimewfh.ui.admin.add_user;


import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.concurrent.BlockingQueue;

public class MyPageAdapter extends FragmentPagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>();


    public MyPageAdapter(FragmentManager fm) {
        super(fm);
        registeredFragments.put(0,SubPage1.newInstance());
        registeredFragments.put(1,SubPage2.newInstance());
        registeredFragments.put(2,SubPage3.newInstance());
        registeredFragments.put(3,SubPage4.newInstance());

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)return  registeredFragments.get(0);
        else if(position == 1) return  registeredFragments.get(1);
        else if(position == 2) return  registeredFragments.get(2);
        else if(position == 3) return  registeredFragments.get(3);
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }



}