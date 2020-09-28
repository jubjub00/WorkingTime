package com.example.workingtimewfh.ui.user.leave;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.workingtimewfh.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LeaveFragment extends Fragment {

    private LeaveViewModel LeaveViewModel;
    LocalActivityManager mLocalActivityManager;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LeaveViewModel = ViewModelProviders.of(this).get(LeaveViewModel.class);
        View root = inflater.inflate(R.layout.activity_vacation, container, false);
        mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

        TabLayout tabHost = (TabLayout) root.findViewById(R.id.tabLayout);
        TabItem add = root.findViewById(R.id.add);
        TabItem view = root.findViewById(R.id.view);
        final ViewPager viewPager = root.findViewById(R.id.showPager);

        LeaveAdapter leaveAdapter = new LeaveAdapter(getChildFragmentManager());
        viewPager.setAdapter(leaveAdapter);
        tabHost.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }
}
