package com.example.workingtimewfh.ui.admin.add_user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.workingtimewfh.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.util.List;
import java.util.Map;


public class AddUserFragment extends Fragment  implements ViewPager.OnPageChangeListener{

    private AddUserViewModel AddUserViewModel;
    Button submit,cancel;
    String Vstatus,Vgender;
    View root,s1;

    List<Bitmap> BitmapImage;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String next_id;
    Map<String, Object> user;
    ProgressBar progressBar2;
    int current_position=0;
    String[] descriptionData = {"ข้อมูลส่วนตัว", "ข้อมูลที่อยู่", "ข้อมูล\nการศึกษา", "ข้อมูล\nการทำงาน"};
    MyPageAdapter adapter;
    StateProgressBar stateProgressBar;
    ViewPager pager=null;
    SubPage1  sb1;
    Fragment sub11;
    int getCurrent_position = 0;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AddUserViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
        root = inflater.inflate(R.layout.fragment_add_user, container, false);



        submit = root.findViewById(R.id.save);
        cancel = root.findViewById(R.id.cancel);
        Vstatus = null;
        Vgender = null;

        adapter = new MyPageAdapter(getChildFragmentManager());



        pager =  root.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrent_position == 3)
                    getDataFromFragment();
                else{
                    getCurrent_position++;
                    if(getCurrent_position>=3)getCurrent_position=3;
                    pager.setCurrentItem(getCurrent_position);
                    setButton();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrent_position--;
                if(getCurrent_position<=0)getCurrent_position=0;
                pager.setCurrentItem(getCurrent_position);
                setButton();
            }
        });



        stateProgressBar = (StateProgressBar) root.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                pager.setCurrentItem(stateNumber-1);
                switch (stateNumber){
                    case 1:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        getCurrent_position=0;
                        break;
                    case 2:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        getCurrent_position=1;
                        break;
                    case 3:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        getCurrent_position=2;
                        break;
                    case 4:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        getCurrent_position=3;
                        break;

                }
                setButton();

            }
        });




        return root;
    }
    public void setButton(){
        if(getCurrent_position == 0){
            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_arrow_forward_black_24dp));
            cancel.setVisibility(View.INVISIBLE);
        }else if(getCurrent_position == 1 || getCurrent_position == 2){
            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_arrow_forward_black_24dp));
            cancel.setVisibility(View.VISIBLE);
        }else if(getCurrent_position == 3){

            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_save_black_24dp));
            cancel.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position+1){
            case 1:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                getCurrent_position=0;
                break;
            case 2:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                getCurrent_position=1;
                break;
            case 3:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                getCurrent_position=2;
                break;
            case 4:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                getCurrent_position=3;
                break;

        }
        setButton();


    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }



    void getDataFromFragment(){
        SubPage1 fg_subpage1 = (SubPage1) adapter.getItem(0);
        SubPage2 fg_subpage2 = (SubPage2) adapter.getItem(1);
        SubPage3 fg_subpage3 = (SubPage3) adapter.getItem(2);
        SubPage4 fg_subpage4 = (SubPage4) adapter.getItem(3);

        if(fg_subpage1 != null && fg_subpage2 != null && fg_subpage3 != null && fg_subpage4 != null){
            if(!fg_subpage1.ValidAll(pager)){
                getCurrent_position = 0;
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            if(!fg_subpage2.ValidAll(pager)){
                getCurrent_position = 1;
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
        }






    }




}
