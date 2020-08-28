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

import com.example.workingtimewfh.R;

public class LeaveFragment extends Fragment {

    private LeaveViewModel LeaveViewModel;
    LocalActivityManager mLocalActivityManager;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LeaveViewModel = ViewModelProviders.of(this).get(LeaveViewModel.class);
        View root = inflater.inflate(R.layout.activity_vacation, container, false);
        mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);

        TabHost tabHost = (TabHost) root.findViewById(R.id.tabhost);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
                .setIndicator("เอกสารขอลา")
                .setContent(new Intent(this.getActivity(), add_leave.class));

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                .setIndicator("สถานะการลา")
                .setContent(new Intent(this.getActivity(), view_leave.class));



        tabHost.addTab(tabSpec);
        tabHost.addTab(tabSpec2);
        //tabHost.setBackgroundColor(Color.parseColor("#6A98DC"));



        return root;
    }
}
