package com.example.workingtimewfh.ui.admin.home_admin.AdapterTask;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryEmployeeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterImage extends RecyclerView.Adapter<adapterImage.ViewHolder> {
    private ArrayList<Uri> uris;
    public adapterImage(ArrayList<Uri> lst) {
        this.uris = lst;
    }

    @NonNull
    @Override
    public adapterImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_show_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterImage.ViewHolder holder, int position) {
        Picasso.get().load(uris.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
        }
    }
}
