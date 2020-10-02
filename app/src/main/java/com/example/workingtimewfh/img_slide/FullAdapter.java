package com.example.workingtimewfh.img_slide;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FullAdapter extends DialogFragment {
    private List<Bitmap> sliderItems,items;
    private int pos;
    SliderAdapter a ;
    ViewPager2 viewPager2;


    public FullAdapter(List<Bitmap> sliderItems, int pos, SliderAdapter a, List<Bitmap> items){
        this.sliderItems = sliderItems;
        this.pos = pos;
        this.a = a;
        this.items = items;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list,container,false);

        viewPager2 = view.findViewById(R.id.RecycleImage);
        final DialogAdapter dialogAdapter = new DialogAdapter(sliderItems);


        ((Button)view.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((Button)view.findViewById(R.id.button6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderItems.remove(pos);
                dialogAdapter.notifyItemRemoved(pos);

                a.notifyDataSetChanged();
                items.remove(pos);


                viewPager2.setAdapter(dialogAdapter);
                dismiss();

            }
        });

        viewPager2.setAdapter(dialogAdapter);
        viewPager2.setCurrentItem(pos);

        return view;
    }
}
