package com.example.workingtimewfh.ui.admin.home_admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.util.ArrayList;

public class DetailEmployeesAdapter extends RecyclerView.Adapter<DetailEmployeesAdapter.ViewHolder>{

    private ArrayList<String> dataSet;
    private OnClicked listener;

    public DetailEmployeesAdapter(ArrayList<String> dataSet) {
        this.dataSet = dataSet;
    }
    @NonNull
    @Override
    public DetailEmployeesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_detail_employees,parent,false);
        return new ViewHolder(view,listener);
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
        Button viewTime,viewWork;
        public ViewHolder(@NonNull View itemView, final OnClicked listener) {
            super(itemView);
            show = itemView.findViewById(R.id.txt);
            viewTime = itemView.findViewById(R.id.time);
            viewWork = itemView.findViewById(R.id.work);


            viewTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && listener != null){
                        listener.onclick(dataSet.get(pos),"time");
                    }

                }
            });

            viewWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && listener != null){
                        listener.onclick(dataSet.get(pos),"work");
                    }

                }
            });



        }
    }

    public interface OnClicked{
        void onclick(String date,String type);
    }
    public void SetOnClickList(OnClicked listener){
        this.listener = listener;
    }


}
