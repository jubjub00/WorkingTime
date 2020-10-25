package com.example.workingtimewfh.ui.admin.home_admin.AdapterTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.img_slide.DialogAdapter;
import com.example.workingtimewfh.img_slide.FullAdapter;
import com.example.workingtimewfh.img_slide.SliderAdapter;
import com.example.workingtimewfh.ui.admin.add_user.SubPage1;

import java.util.ArrayList;
import java.util.List;

public class adapterTask extends RecyclerView.Adapter<adapterTask.ViewHolder> {
    private ArrayList<TaskStruct> data;
    private OnClicked listener;
    private Context context;

    public adapterTask(ArrayList<TaskStruct> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public adapterTask.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task,parent,false);
        context = parent.getContext();
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final adapterTask.ViewHolder holder, final int position) {
        holder.time.setText(data.get(position).getTime());
        holder.head.setText("หัวข้อ : "+data.get(position).getHead());
        holder.locat.setText("สถานที่ : "+data.get(position).getLocation());
        holder.detail.setText("รายละเอียด : "+data.get(position).getDetail());
        holder.Button_location.setText("ตำแหน่ง : "+data.get(position).getLatitude()+","+data.get(position).getLongtitude());


    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time,head,locat,detail;
        CardView card_v;
        Button Button_location;
        public ViewHolder(@NonNull View itemView, final OnClicked listener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            head = itemView.findViewById(R.id.head);
            locat = itemView.findViewById(R.id.locat);
            detail = itemView.findViewById(R.id.detail);
            card_v = itemView.findViewById(R.id.card_v);
            Button_location = itemView.findViewById(R.id.Button_location);

            Button_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && listener != null){
                        listener.Clicked(data.get(pos).getLatitude(),data.get(pos).getLongtitude());
                    }
                }
            });

            card_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && listener != null){
                        listener.Click_card(data.get(pos).getImg());
                    }
                }
            });



        }
    }

    public interface OnClicked{
        void Clicked(double lat,double lon);
        void Click_card(List<String> img);
    }
    public void SetOnClicked(OnClicked listener){
        this.listener = listener;
    }
}
