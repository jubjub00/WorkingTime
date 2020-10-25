package com.example.workingtimewfh.ui.admin.add_user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.img_slide.FullAdapter;
import com.example.workingtimewfh.img_slide.SliderAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SubPage1 extends Fragment implements DatePickerDialog.OnDateSetListener {
    private ViewPager2 viewPager2;
    List<Bitmap> BitmapImage = new ArrayList<>();
    Spinner name_title,pos,pos2 ;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    View rootView;
    int NameTitle = 0,Position = 0,reli = 0;
    ArrayList<String> All = new ArrayList<>();
    Button cam,birthday,gal;
    String Value_NameTitle = null,Value_Position = null,Value_State = null,Value_Religion = null,Gender = null,state = null;
    List<Bitmap> sliderItems;

    Uri uri;

    public static SubPage1 newInstance() {
        SubPage1 fragment = new SubPage1();

        return fragment;
    }
    public SubPage1() { }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.subpage1, container, false);

        Spinner spinner = rootView.findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Value_State = (String)adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        name_title = rootView.findViewById(R.id.addnametitle);
        pos = rootView.findViewById(R.id.addposition);
        pos2 = rootView.findViewById(R.id.spinner2);


        viewPager2 = rootView.findViewById(R.id.ViewPagerImage);
        birthday = rootView.findViewById(R.id.select_birthday);


        cam = rootView.findViewById(R.id.addphoto);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,"New picture");
                values.put(MediaStore.Images.Media.DESCRIPTION,"Camera");
                uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cam.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(cam,1);

            }
        });
        gal = rootView.findViewById(R.id.addphotophone);
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                cameraIntent.setType("image/*");
                startActivityForResult(cameraIntent, 2);
            }
        });

        CreateNameTitle();
        CreatePosition();
        CreateReligion();
        CreateSelectGender();
        CreateSelectStatus();



        if(!BitmapImage.isEmpty()){
            List<Bitmap> sliderItems = new ArrayList<>();
            for (Bitmap x: BitmapImage) {
                sliderItems.add(x);
            }
            SliderAdapter a = new SliderAdapter(sliderItems, viewPager2);

            viewPager2.setAdapter(a);
            viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        }




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

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDatePicker();
                Button txt = rootView.findViewById(R.id.select_birthday);
                txt.setError(null);
            }
        });


        final EditText Salary = rootView.findViewById(R.id.addsalary);
        Salary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Salary.removeTextChangedListener(this);

                try {
                    String givenstring = s.toString();
                    int longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Integer.parseInt(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String formattedString = formatter.format(longval);
                    Salary.setText(formattedString);
                    Salary.setSelection(Salary.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Salary.addTextChangedListener(this);
            }
        });


        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("country");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject j = userArray.getJSONObject(i);
                All.add(j.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, All);
        final AutoCompleteTextView textView = (AutoCompleteTextView) rootView.findViewById(R.id.addnationality);
        textView.setAdapter(adt);
        final AutoCompleteTextView textView2 = (AutoCompleteTextView) rootView.findViewById(R.id.addrace);
        textView2.setAdapter(adt);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(textView.getText().toString());
            }
        });

        textView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView2.setText(textView2.getText().toString());
            }
        });


        return rootView;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("country.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = null;
                try {
                    bp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Bitmap bp = (Bitmap) data.getExtras().get("data");
                BitmapImage.add(bp);

                sliderItems = new ArrayList<>();
                for (Bitmap x: BitmapImage) {
                    sliderItems.add(x);
                }
                final SliderAdapter a = new SliderAdapter(sliderItems, viewPager2);

                a.SetOnClickListener(new SliderAdapter.sOnClicked() {
                    @Override
                    public void Clicked(int pos) {

                        DialogFragment dialogFragment = new FullAdapter(sliderItems,pos,a,BitmapImage);
                        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.AppTheme_PopupOverlay);
                        dialogFragment.show(getActivity().getSupportFragmentManager(),"tag");

                        a.notifyDataSetChanged();
                        viewPager2.setAdapter(a);
                        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
                    }
                });

                viewPager2.setAdapter(a);
                viewPager2.setPageTransformer(new ZoomOutPageTransformer());

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }


        }

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {


            Uri returnUri = data.getData();
            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);

                BitmapImage.add(bitmapImage);

                sliderItems = new ArrayList<>();
                for (Bitmap x: BitmapImage) {
                    sliderItems.add(x);
                }
                final SliderAdapter a = new SliderAdapter(sliderItems, viewPager2);

                a.SetOnClickListener(new SliderAdapter.sOnClicked() {
                    @Override
                    public void Clicked(int pos) {

                        DialogFragment dialogFragment = new FullAdapter(sliderItems,pos,a,BitmapImage);
                        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.AppTheme_PopupOverlay);
                        dialogFragment.show(getActivity().getSupportFragmentManager(),"tag");

                        a.notifyDataSetChanged();
                        viewPager2.setAdapter(a);
                        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
                    }
                });

                viewPager2.setAdapter(a);
                viewPager2.setPageTransformer(new ZoomOutPageTransformer());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    private void CreateSelectGender() {
        RadioButton male = rootView.findViewById(R.id.male);
        RadioButton female = rootView.findViewById(R.id.female);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = rootView.findViewById(R.id.addsex);
                txt.setError(null);
                Gender = "ชาย";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = rootView.findViewById(R.id.addsex);
                txt.setError(null);
                Gender = "หญิง";
            }
        });
    }
    private void CreateSelectStatus() {
        RadioButton w = rootView.findViewById(R.id.addworker);
        RadioButton p = rootView.findViewById(R.id.addparttime);
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = rootView.findViewById(R.id.addstatus);
                txt.setError(null);
                state = "พนักงานประจำ";
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = rootView.findViewById(R.id.addstatus);
                txt.setError(null);
                state = "พนักงงานชั่วคราว";
            }
        });
    }
    public void CreateNameTitle(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("name_title");
                final List<String> b = a;

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                name_title.setAdapter(aa);
                name_title.setSelection(NameTitle);
                Value_NameTitle = (String)name_title.getSelectedItem();
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
                            Value_NameTitle = (String)name_title.getSelectedItem();
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

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                pos.setSelection(Position);
                Value_Position = (String)pos.getSelectedItem();
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
                            Value_Position = (String)pos.getSelectedItem();
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



                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos2.setAdapter(aa);
                pos2.setSelection(reli);
                Value_Religion = (String)pos2.getSelectedItem();
                pos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                            Value_Religion = (String)pos2.getSelectedItem();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

    public void ReadData(Map<String, Object> dataUser) {

        dataUser.put("prefix",Value_NameTitle);
        dataUser.put("name",((EditText)rootView.findViewById(R.id.addname)).getText().toString());
        dataUser.put("lastname",((EditText)rootView.findViewById(R.id.addsurname)).getText().toString());
        dataUser.put("name_eng",((EditText)rootView.findViewById(R.id.addname_eng)).getText().toString());
        dataUser.put("lastname_eng",((EditText)rootView.findViewById(R.id.addsurname_eng)).getText().toString());
        dataUser.put("personal_id",((EditText)rootView.findViewById(R.id.addid)).getText().toString());
        dataUser.put("tel",((EditText)rootView.findViewById(R.id.addtel)).getText().toString());
        dataUser.put("position",Value_Position);
        dataUser.put("birth_day",((Button)rootView.findViewById(R.id.select_birthday)).getText().toString());
        dataUser.put("salary",((EditText)rootView.findViewById(R.id.addsalary)).getText().toString());
        dataUser.put("status_family",Value_State);
        dataUser.put("nationality",((EditText)rootView.findViewById(R.id.addnationality)).getText().toString());
        dataUser.put("race",((EditText)rootView.findViewById(R.id.addrace)).getText().toString());
        dataUser.put("religion",Value_Religion);
        dataUser.put("gender",Gender);
        dataUser.put("type_employees",state);

        dataUser.put("username",((EditText)rootView.findViewById(R.id.addtel)).getText().toString());
        dataUser.put("password",((EditText)rootView.findViewById(R.id.addtel)).getText().toString());


        List<String> list_id_img = null;
        if(!BitmapImage.isEmpty()) {
            list_id_img = new ArrayList<>();;
            int i=0;
            for (Bitmap bp: BitmapImage) {
                String tmp = FirebaseDatabase.getInstance().getReference("user").push().getKey();

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
        dataUser.put("id_img", list_id_img);


    }




    public static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
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

    private void ShowDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Dialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        long now = System.currentTimeMillis() - 1000;
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR)-16, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ((Button) rootView.findViewById(R.id.select_birthday)).setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));
    }



    boolean ValidAll(ViewPager pager){

        if(!ValidName() | !ValidLastName() | !ValidTel() | !ValidSalary() | !ValidNameEng() | !ValidLastNameEng() | !ValidPersonID() | !ValidBirthDay() | !ValidNational() | !ValidRace() | !ValidGender() | !ValidStatus()){
            pager.setCurrentItem(0);
            if(!ValidName() | !ValidLastName() | !ValidTel() | !ValidSalary() | !ValidNameEng() | !ValidLastNameEng() | !ValidPersonID() | !ValidBirthDay() | !ValidNational() | !ValidRace() | !ValidGender() | !ValidStatus()){
                return false;
            }

        }
        return true;

    }

    boolean ValidName(){

        EditText txt = (EditText) rootView.findViewById(R.id.addname);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidLastName(){

        EditText txt = (EditText) rootView.findViewById(R.id.addsurname);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidNameEng(){

        EditText txt = (EditText) rootView.findViewById(R.id.addname_eng);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidLastNameEng(){

        EditText txt = (EditText) rootView.findViewById(R.id.addsurname_eng);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidPersonID(){

        EditText txt = (EditText) rootView.findViewById(R.id.addid);
        if(txt.getText().toString().isEmpty() && txt.getText().toString().length() != 13){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidTel(){
        EditText txt = rootView.findViewById(R.id.addtel);
        if(txt.getText().toString().isEmpty() && txt.getText().toString().length() != 10){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidBirthDay(){
        Button txt = rootView.findViewById(R.id.select_birthday);
        if(txt.getText().toString().compareTo("เลือกวันที่เกิด") == 0){

            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidSalary(){
        EditText txt = rootView.findViewById(R.id.addsalary);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidNational(){
        AutoCompleteTextView txt = rootView.findViewById(R.id.addnationality);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidRace(){
        AutoCompleteTextView txt = rootView.findViewById(R.id.addrace);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }
    boolean ValidGender(){
        TextView txt = rootView.findViewById(R.id.addsex);
        if(Gender == null){
            txt.requestFocus();
            txt.setError("กรุณาเลือกเพศ");
            return false;
        }
        return true;
    }
    boolean ValidStatus(){
        TextView txt = rootView.findViewById(R.id.addstatus);
        if(Gender == null){
            txt.requestFocus();
            txt.setError("กรุณาเลือกเพศ");
            return false;
        }
        return true;
    }




}