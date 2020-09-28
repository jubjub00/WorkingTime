package com.example.workingtimewfh.ui.user.leave;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.util.ArrayList;

public class ViewLeaveAdapter extends RecyclerView.Adapter<ViewLeaveAdapter.ViewHolder> {


    ArrayList<ArrayList<String>> dataSet;

    public ViewLeaveAdapter(ArrayList<ArrayList<String>> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subpage_tabviewleave,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> item = dataSet.get(position);
        if(item != null){
            holder.DateCommit.setText("วันที่ส่งเรื่องขอลา : "+item.get(0));
            holder.StartDay.setText(item.get(1));
            holder.StartTime.setText(item.get(2));
            holder.EndDay.setText(item.get(3));
            holder.EndTime.setText(item.get(4));
            holder.button5.setText(item.get(5));
            if(item.get(5).matches("รออนุมัติ"))holder.button5.setBackgroundColor(Color.parseColor("#F4D03F"));
            else if(item.get(5).matches("อนุมัติ"))holder.button5.setBackgroundColor(Color.parseColor("#58D68D"));
            else holder.button5.setBackgroundColor(Color.parseColor("#EC7063"));


        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView DateCommit,StartDay,StartTime,EndDay,EndTime;
        Button button5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DateCommit = itemView.findViewById(R.id.DateCommit);
            StartDay = itemView.findViewById(R.id.StartDay);
            StartTime = itemView.findViewById(R.id.StartTime);
            EndDay = itemView.findViewById(R.id.EndDay);
            EndTime = itemView.findViewById(R.id.EndTime);
            button5 = itemView.findViewById(R.id.button5);


        }
    }
}
