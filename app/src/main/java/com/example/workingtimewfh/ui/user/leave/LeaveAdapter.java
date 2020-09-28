package com.example.workingtimewfh.ui.user.leave;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LeaveAdapter extends FragmentPagerAdapter {

    public LeaveAdapter(FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new add_leave();
            case 1:
                return new view_leave();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
