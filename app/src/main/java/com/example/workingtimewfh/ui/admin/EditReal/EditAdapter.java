package com.example.workingtimewfh.ui.admin.EditReal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryStruct;

import java.util.ArrayList;


public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder> {

    private ArrayList<HistoryStruct> dataSet;

    public EditAdapter(ArrayList<HistoryStruct> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_edit,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.HistoryHeader.setText(dataSet.get(position).getName());
        holder.HistoryContent.setText(dataSet.get(position).getLastname());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView HistoryHeader;
        TextView HistoryContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.HistoryHeader = (TextView) itemView.findViewById(R.id.header);
            this.HistoryContent = (TextView) itemView.findViewById(R.id.detail);
        }
    }
}

