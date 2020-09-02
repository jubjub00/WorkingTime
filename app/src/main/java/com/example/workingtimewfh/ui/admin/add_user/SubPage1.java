package com.example.workingtimewfh.ui.admin.add_user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.img_slide.SliderAdapter;
import com.example.workingtimewfh.img_slide.SliderItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SubPage1 extends Fragment implements AdapterView.OnItemSelectedListener {
    private ViewPager2 viewPager2;
    List<Bitmap> BitmapImage;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    View rootView;
    int NameTitle = 0,Position = 0,reli = 0;
    Button cam;


    public static SubPage1 newInstance() {
        SubPage1 fragment = new SubPage1();

        return fragment;
    }
    public SubPage1() { }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.subpage1, container, false);

        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        viewPager2 = rootView.findViewById(R.id.ViewPagerImage);
        BitmapImage = new ArrayList<>();

        cam = rootView.findViewById(R.id.addphoto);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam,1);
            }
        });

        CreateNameTitle();
        CreatePosition();
        CreateReligion();

        EditText ID = rootView.findViewById(R.id.addid);
        ID.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(((EditText)rootView.findViewById(R.id.addid)).getText().toString().length() < 13) {
                    ((EditText) rootView.findViewById(R.id.addid)).setError("กรอกให้ครบ 13 หลัก");}
                return false;
            }
        });
        ID.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});

        EditText Tel = rootView.findViewById(R.id.addtel);
        Tel.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(((EditText)rootView.findViewById(R.id.addtel)).getText().toString().length() < 10) {
                    ((EditText) rootView.findViewById(R.id.addtel)).setError("กรอกให้ครบ 10 หลัก");}
                return false;
            }
        });
        Tel.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                BitmapImage.add(bp);



                List<SliderItem> sliderItems = new ArrayList<>();
                for (Bitmap x: BitmapImage) {
                    sliderItems.add(new SliderItem(x));
                }
                SliderAdapter a = new SliderAdapter(sliderItems, viewPager2);

                viewPager2.setAdapter(a);
                viewPager2.setPageTransformer(new ZoomOutPageTransformer());

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Can celled", Toast.LENGTH_LONG).show();
            }


        }
    }

    public void valid(String a, ViewPager pager){
        pager.setCurrentItem(0);
        ((EditText) rootView.findViewById(R.id.addname)).setError(a);
        ((EditText) rootView.findViewById(R.id.addname)).requestFocus();
    }

    public void CreateNameTitle(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("name_title");
                final List<String> b = a;

                Spinner name_title = rootView.findViewById(R.id.addnametitle);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                name_title.setAdapter(aa);
                name_title.setSelection(NameTitle);
                name_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มคำนำหน้าชื่อ") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);

                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มคำนำหน้าชื่อ") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("name_title",b);
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.addnametitle);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            NameTitle = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

    public void CreatePosition(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("position");
                final List<String> b = a;

                Spinner pos = rootView.findViewById(R.id.addposition);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                pos.setSelection(Position);
                pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มตำแหน่ง") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);
                            ((TextView)mView.findViewById(R.id.textView6)).setText("เพิ่มตำแหน่ง");
                            ((EditText)mView.findViewById(R.id.editText)).setHint("ใส่ค่าตำแหน่ง");
                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มตำแหน่ง") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("position",b);
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.addposition);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            Position = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }
    public void CreateReligion(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("religion");
                final List<String> b = a;

                Spinner pos = rootView.findViewById(R.id.spinner2);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                pos.setSelection(reli);
                pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มศาสนา") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);
                            ((TextView)mView.findViewById(R.id.textView6)).setText("เพิ่มศาสนา");
                            ((EditText)mView.findViewById(R.id.editText)).setHint("ใส่ค่าศาสนา");
                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มศาสนา") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("religion",b);
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.spinner2);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            reli = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


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
}