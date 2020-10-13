package com.example.workingtimewfh.img_slide;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;



public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.SliderViewHolder>{
    private List<Bitmap> sliderItems;

    private sOnClicked mListener;

    public DialogAdapter(List<Bitmap> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.image_page,parent,false);
        return new SliderViewHolder(view,mListener);


    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.img.setImageBitmap(sliderItems.get(position));
    }


    @Override
    public int getItemCount() {
        return sliderItems.size();
    }


    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView img;
        SliderViewHolder(@NonNull View itemView, final sOnClicked listen) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView2);

        }


    }
    public interface sOnClicked{
        void Clicked(int pos);
        void ClickedBack();
    }
    public void SetOnClickListener(sOnClicked listener){
        this.mListener = listener;
    }
}
