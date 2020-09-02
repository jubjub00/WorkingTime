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

public class Sub3Adapter extends  RecyclerView.Adapter<Sub3Adapter.ViewHolder>{
    ArrayList<subDatapage3> item;
    private OnItemClickListener mListener;

    public Sub3Adapter(ArrayList<subDatapage3> item) {
        this.item = item;
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
        View view = layoutInflater.inflate(R.layout.subpage3_sub,parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Sub3Adapter.ViewHolder holder, int position) {
        subDatapage3 a = item.get(position);
        holder.education_level.setText(a.getLevel());
        holder.education_name.setText(a.getName());
        holder.education_year.setText(""+a.getYear());
        holder.education_grade.setText(""+a.getGrade());
    }



    @Override
    public int getItemCount() {
        return item.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView education_level;
        TextView education_name;
        TextView education_year;
        TextView education_grade;
        Button delete;

        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            education_level = itemView.findViewById(R.id.education_level);
            education_name = itemView.findViewById(R.id.education_name);
            education_year = itemView.findViewById(R.id.education_year);
            education_grade = itemView.findViewById(R.id.education_grade);
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

