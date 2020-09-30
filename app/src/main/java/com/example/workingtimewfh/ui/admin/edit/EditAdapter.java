package com.example.workingtimewfh.ui.admin.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> data;
    private OnItemClickListener mListener;

    public EditAdapter(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public EditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_comf_leave,parent,false);
        return new ViewHolder(view,mListener,this.data);
    }

    @Override
    public void onBindViewHolder(@NonNull EditAdapter.ViewHolder holder, int position) {
        if(!data.get(position).isEmpty()) {
            holder.name.setText("ประเภทการลา "+data.get(position).get(7)+"\nชื่อ-นามสกุล "+data.get(position).get(0));
            holder.date_com.setText("วันที่ส่งเรื่องขอลา "+data.get(position).get(1));
            holder.EndDay.setText(data.get(position).get(2));
            holder.StartDay.setText(data.get(position).get(3));
            holder.desc.setText("รายละเอียด "+data.get(position).get(4));
            holder.EndTime.setText(data.get(position).get(5));
            holder.StartTime.setText(data.get(position).get(6));

        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,date_com,StartDay,StartTime,EndDay,EndTime,desc;
        Button submit,canc;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener, final ArrayList<ArrayList<String>> id) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date_com = itemView.findViewById(R.id.date_com);
            StartDay = itemView.findViewById(R.id.StartDay);
            StartTime = itemView.findViewById(R.id.StartTime);
            EndDay = itemView.findViewById(R.id.EndDay);
            EndTime = itemView.findViewById(R.id.EndTime);
            desc = itemView.findViewById(R.id.desc);
            submit = itemView.findViewById(R.id.submit);
            canc = itemView.findViewById(R.id.canc);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position,(String)id.get(position).get(8),(String)id.get(position).get(1),true);
                        }
                    }
                }
            });

            canc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position,(String)id.get(position).get(8),(String)id.get(position).get(1),false);
                        }
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position,String Name,String DateCommit,boolean yes);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
