package com.example.workingtimewfh.ui.admin.add_user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.util.ArrayList;

public class Sub4Adapter extends RecyclerView.Adapter<Sub4Adapter.ViewHolder>{
    private OnItemClickListener mListener;
    private ArrayList<subDatapage4> experience;
    public Sub4Adapter(ArrayList<subDatapage4> experience) {
        this.experience = experience;
    }


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subpage4_sub,parent,false);
        return new ViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.company.setText(experience.get(position).getCompany());
        holder.position.setText(experience.get(position).getPosition());
        holder.start_work.setText(experience.get(position).getStart());
        holder.end_work.setText(experience.get(position).getEnd());
    }



    @Override
    public int getItemCount() {
        return experience.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView company;
        TextView position;
        TextView start_work;
        TextView end_work;
        Button delete;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            company = itemView.findViewById(R.id.company);
            position = itemView.findViewById(R.id.position);
            start_work = itemView.findViewById(R.id.start_work);
            end_work = itemView.findViewById(R.id.end_work);

            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

}

