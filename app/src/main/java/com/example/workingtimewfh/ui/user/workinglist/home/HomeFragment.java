package com.example.workingtimewfh.ui.user.workinglist.home;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.ExtFunction;
import com.example.workingtimewfh.R;
import com.example.workingtimewfh.img_slide.FullAdapter;
import com.example.workingtimewfh.img_slide.SliderAdapter;
import com.example.workingtimewfh.ui.admin.add_user.SubPage1;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btnCamera, saveDetail;
    String head, location, detailwork;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    SharedPreferences sp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FusedLocationProviderClient fusedLocationProviderClient;
    double latitude = 0.0, longtitude = 0.0;
    String addr = null;
    ProgressBar s;
    ExtFunction ext;

    Uri uri;
    private ViewPager2 viewPager2;
    List<Bitmap> sliderItems;
    List<Bitmap> BitmapImage;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home_workinglist, container, false);
        btnCamera = root.findViewById(R.id.camera);
        ext = new ExtFunction();
        viewPager2 = root.findViewById(R.id.ViewPagerImage);
        BitmapImage = new ArrayList<>();


        saveDetail = root.findViewById(R.id.savedetail);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE,"New picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION,"Camera");
                    uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cam.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    startActivityForResult(cam,1);
                }else{
                    ActivityCompat.requestPermissions(getActivity() ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},45);

                }
            }
        });

        saveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getActivity(), "กรุณาเปิด GPS", Toast.LENGTH_SHORT).show();
                    } else {
                        getlocation(root);
                    }
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }

            }
        });


        return root;
    }


    public void getlocation(final View root) {
        s = root.findViewById(R.id.WorkingDetail);
        s.setVisibility(View.VISIBLE);
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        latitude = 0.0;
        longtitude = 0.0;


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                fusedLocationProviderClient.removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {

                    try {
                        int lastesLocationIndex = locationResult.getLocations().size() - 1;
                        latitude = locationResult.getLocations().get(lastesLocationIndex).getLatitude();
                        longtitude = locationResult.getLocations().get(lastesLocationIndex).getLongitude();
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = null;
                        addresses = geocoder.getFromLocation(latitude, longtitude, 1);
                        addr = addresses.get(0).getAddressLine(0);
                        if (latitude != 0.0 && longtitude != 0.0 && addr != null) {
                            save(root);
                            s.setVisibility(View.GONE);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, Looper.getMainLooper());

    }

    public void save(View root){
        head = ((EditText)root.findViewById(R.id.namedetail)).getText().toString();
        location = ((EditText)root.findViewById(R.id.namelocation)).getText().toString();
        detailwork = ((EditText)root.findViewById(R.id.detailwork)).getText().toString();

        String formattedTime = ext.GetTime();
        String formattedDate = ext.GetDate();


        Map<String, Object> DetailTask = new HashMap<>();
        Map<String, Object> TimeTask = new HashMap<>();
        Map<String, Object> DateTask = new HashMap<>();
        DetailTask.put("head",head);
        DetailTask.put("location",location);
        DetailTask.put("detail",detailwork);
        DetailTask.put("latitude",latitude);
        DetailTask.put("longtitude",longtitude);


        List<String> list_id_img = null;
        if(!BitmapImage.isEmpty()) {
            list_id_img = new ArrayList<>();;
            int i=0;
            for (Bitmap bp: BitmapImage) {
                String tmp = FirebaseDatabase.getInstance().getReference("task").push().getKey();

                list_id_img.add(tmp) ;
                StorageReference mountainsRef = storageRef.child("task/"+tmp);
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

        DetailTask.put("id_img", list_id_img);
        TimeTask.put(formattedTime,DetailTask);
        DateTask.put(formattedDate,TimeTask);


        sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        db.collection("task").document(sp.getString("KeyDocument","???")).set(DateTask, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                BitmapImage.clear();

                getActivity().finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"บันทึกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 44 && grantResults.length > 0){
            //save(root);
        }else {
            Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
        }
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
