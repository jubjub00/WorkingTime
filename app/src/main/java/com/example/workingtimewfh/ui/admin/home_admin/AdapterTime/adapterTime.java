package com.example.workingtimewfh.ui.admin.home_admin.AdapterTime;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.TimeStruct;

import java.util.ArrayList;

public class adapterTime extends RecyclerView.Adapter<adapterTime.ViewHolder> {
    ArrayList<TimeStruct> data ;
    Onclicked listener;

    public adapterTime(ArrayList<TimeStruct> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_time,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.type.setText(data.get(position).getType());
        holder.time.setText("เวลา "+data.get(position).getTime());
        holder.location.setText("ตำแหน่ง : "+data.get(position).getLatitude()+","+data.get(position).getLongtitude());

        if(data.get(position).getType().matches("เข้างาน"))
            holder.card.setCardBackgroundColor(Color.parseColor("#D5F5E3"));
        else
            holder.card.setCardBackgroundColor(Color.parseColor("#FAE5D3"));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,time;
        Button location;
        CardView card;

        public ViewHolder(@NonNull View itemView, final Onclicked listener) {
            super(itemView);

            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            card = itemView.findViewById(R.id.card);

            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && listener != null){
                        listener.Clicked(data.get(pos).getLatitude(),data.get(pos).getLongtitude());
                    }

                }
            });


        }
    }

    public interface Onclicked{
        void Clicked(double lat,double lon);
    }
    public void SetOnClickItem(Onclicked listener){
        this.listener = listener;
    }
}
