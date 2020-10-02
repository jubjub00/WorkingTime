package com.example.workingtimewfh.img_slide;

import android.app.AlertDialog;
import android.app.slice.SliceItem;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    private List<Bitmap> sliderItems;
    private ViewPager2 viewPager2;
    private sOnClicked mListener;

    public SliderAdapter(List<Bitmap> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.slide_item_container,parent,false);
        return new SliderViewHolder(view,mListener);


    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.imageView.setImageBitmap(sliderItems.get(position));

    }


    @Override
    public int getItemCount() {
        return sliderItems.size();
    }


     public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        SliderViewHolder(@NonNull View itemView, final sOnClicked listen) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageSlide);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listen != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listen.Clicked(position);
                        }
                    }
                }
            });

        }


    }
    public interface sOnClicked{
        void Clicked(int pos);
    }
    public void SetOnClickListener(sOnClicked listener){
        this.mListener = listener;
    }
}
