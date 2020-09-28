package com.example.workingtimewfh.ui.admin.home_admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.util.ArrayList;

public class DetailEmployeesAdapter extends RecyclerView.Adapter<DetailEmployeesAdapter.ViewHolder>{

    private ArrayList<String> dataSet;

    public DetailEmployeesAdapter(ArrayList<String> dataSet) {
        this.dataSet = dataSet;
    }
    @NonNull
    @Override
    public DetailEmployeesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_detail_employees,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailEmployeesAdapter.ViewHolder holder, int position) {
        holder.show.setText(dataSet.get(position));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show = itemView.findViewById(R.id.txt);



        }
    }
}
