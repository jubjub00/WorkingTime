package com.example.workingtimewfh.ui.admin.add_user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    View root;

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
                getCurrent_position++;
                if(getCurrent_position>=3)getCurrent_position=3;
                pager.setCurrentItem(getCurrent_position);
                setButton();
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





/*
        progressBar2 = root.findViewById(R.id.progressBar2);

        viewPager2 = root.findViewById(R.id.ViewPagerImage);
        BitmapImage = new ArrayList<>();

        RadioButton male = root.findViewById(R.id.male);
        RadioButton female = root.findViewById(R.id.female);
        male.setOnClickListener(this);
        female.setOnClickListener(this);

        RadioButton worker = root.findViewById(R.id.addworker);
        RadioButton partime = root.findViewById(R.id.addparttime);
        worker.setOnClickListener(this);
        partime.setOnClickListener(this);

        Button AddPhoto = root.findViewById(R.id.addphoto);
        AddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam,1);
            }
        });

        Button SelectPhoto = root.findViewById(R.id.addphotophone);
        SelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                if(!ValidPrefix(root) | !ValidName(root) | !ValidLastname(root) | !ValidTel(root) | !ValidAddress(root) | !ValidPos(root) | !ValidSalary(root) | !ValidGender(root) | !ValidStatus(root)) {
                    progressBar2.setVisibility(View.GONE);
                    return;
                }
                String name = ((EditText)root.findViewById(R.id.addname)).getText().toString();
                String surname = ((EditText)root.findViewById(R.id.addsurname)).getText().toString();
                String tel = ((EditText)root.findViewById(R.id.addtel)).getText().toString();
                String addr = ((EditText)root.findViewById(R.id.addaddress)).getText().toString();
                String position = ((EditText)root.findViewById(R.id.addposition)).getText().toString();
                String salary = ((EditText)root.findViewById(R.id.addsalary)).getText().toString();
                user = new HashMap<>();
                user.put("gender",Vgender);
                user.put("lastname",surname);
                user.put("name",name);
                user.put("prefix","คุณ");
                user.put("status","user");
                user.put("tel",tel);
                user.put("address",addr);
                user.put("position",position);
                user.put("salary",salary);
                List<Integer> list_id_img = null;
                if(!BitmapImage.isEmpty()) {
                    list_id_img = new ArrayList<>();;
                    int i=0;
                    for (Bitmap bp: BitmapImage) {
                        int tmp = bp.getGenerationId();
                        list_id_img.add(tmp) ;
                        StorageReference mountainsRef = storageRef.child("user/"+tmp);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] img = baos.toByteArray();
                        UploadTask uploadTask = mountainsRef.putBytes(img);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        });
                        i++;
                    }
                }
                user.put("image",list_id_img);
                db.collection("user").document("next_id").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        next_id = task.getResult().get("id").toString();
                        user.put("username",next_id);
                        user.put("password",next_id);
                        db.collection("user").document(next_id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(),"บันทึกข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"บันทึกข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                            }
                        });
                        int PastNumber = Integer.parseInt(next_id)+1;
                        String PastNumberString = String.valueOf(PastNumber);
                        String nxt_id="";
                        for(int i = PastNumberString.length();i<=5;i++){
                            nxt_id += "0";
                        }
                        nxt_id += PastNumberString;
                        db.collection("user").document("next_id").update("id",nxt_id);
                        progressBar2.setVisibility(View.GONE);
                    }
                });
            }
        });*/

        return root;
    }
    public void setButton(){
        if(getCurrent_position == 0){
            submit.setText("ถัดไป");
            cancel.setVisibility(View.INVISIBLE);
        }else if(getCurrent_position == 1 || getCurrent_position == 2){
            submit.setText("ถัดไป");
            cancel.setText("ย้อนกลับ");
            cancel.setVisibility(View.VISIBLE);
        }else if(getCurrent_position == 3){
            submit.setText("บันทึก");
            cancel.setText("ย้อนกลับ");
            cancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = "android:switcher:" + container.getId() + ":" + position;
        return getFragmentManager().findFragmentByTag(name);

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



    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<SliderItem> sliderItems = new ArrayList<>();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                BitmapImage.add(bp);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }


        }else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            InputStream input = null;
            try {
                input = getActivity().getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            BitmapImage.add(bitmap);
        }

        for (Bitmap x: BitmapImage) {
            sliderItems.add(new SliderItem(x));
        }
        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());

    }

    public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                view.setAlpha(0f);

            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }


                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);


                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else {
                view.setAlpha(0f);
            }
        }
    }

    boolean ValidPrefix(View root){
        EditText txt = root.findViewById(R.id.prefix);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }

    boolean ValidName(View root){
        EditText txt = root.findViewById(R.id.addname);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidLastname(View root){
        EditText txt = root.findViewById(R.id.addsurname);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidTel(View root){
        EditText txt = root.findViewById(R.id.addtel);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }else if(txt.getText().toString().length() != 10){
            txt.requestFocus();
            txt.setError("กรุณากรอกเบอร์โทรศัพท์ 10 หลัก");
            return false;
        }else if(txt.getText().toString().charAt(0) != '0'){
            txt.requestFocus();
            txt.setError("กรุณากรอกเบอร์โทรศัพท์เท่านั้น");
            return false;
        }


        return true;
    }
    boolean ValidAddress(View root){
        EditText txt = root.findViewById(R.id.addaddress);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidPos(View root){
        EditText txt = root.findViewById(R.id.addposition);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidSalary(View root){
        EditText txt = root.findViewById(R.id.addsalary);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }

    boolean ValidGender(View root){
        RadioButton a = root.findViewById(R.id.male);
        RadioButton b = root.findViewById(R.id.female);
        if(Vgender == null){

            a.setError("โปรดกรอกข้อมูล");
            b.setError("โปรดกรอกข้อมูล");
            return false;
        }
        a.setError(null);
        b.setError(null);
        return true;
    }
    boolean ValidStatus(View root){
        RadioButton a = root.findViewById(R.id.addworker);
        RadioButton b = root.findViewById(R.id.addparttime);
        if(Vstatus == null){

            a.setError("โปรดกรอกข้อมูล");
            b.setError("โปรดกรอกข้อมูล");
            return false;
        }
        a.setError(null);
        b.setError(null);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.male:
                Vgender = "ชาย";
                ((RadioButton)root.findViewById(R.id.male)).setError(null);
                ((RadioButton)root.findViewById(R.id.female)).setError(null);
                break;
            case R.id.female:
                Vgender = "หญิง";
                ((RadioButton)root.findViewById(R.id.male)).setError(null);
                ((RadioButton)root.findViewById(R.id.female)).setError(null);
                break;
            case R.id.addworker:
                Vstatus = "พนักงานประจำ";
                ((RadioButton)root.findViewById(R.id.addworker)).setError(null);
                ((RadioButton)root.findViewById(R.id.addparttime)).setError(null);
                break;
            case R.id.addparttime:
                Vstatus = "พนักงานชั่วคราว";
                ((RadioButton)root.findViewById(R.id.addworker)).setError(null);
                ((RadioButton)root.findViewById(R.id.addparttime)).setError(null);
                break;

        }

    }


*/



}
