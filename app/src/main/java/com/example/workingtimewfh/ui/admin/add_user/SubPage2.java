package com.example.workingtimewfh.ui.admin.add_user;

import android.os.Bundle;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.workingtimewfh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubPage2 extends Fragment {

    ArrayList<String> SUB_DISTRICT_NAME = new ArrayList<>();
    ArrayList<String> DISTRICT_NAME = new ArrayList<>();
    ArrayList<String> PROVINCE_NAME = new ArrayList<>();
    ArrayList<String> ZIPCODE = new ArrayList<>();
    ArrayList<String> All = new ArrayList<>();
    CheckBox sameLocation;
    View rootView;

    public static SubPage2 newInstance() {
        SubPage2 fragment = new SubPage2();
        return fragment;
    }
    public SubPage2() { }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.subpage2, container, false);
        sameLocation = rootView.findViewById(R.id.checkBox);



        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("zipcode");


            for (int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);

                SUB_DISTRICT_NAME.add(userDetail.getString("SUB_DISTRICT_NAME"));
                DISTRICT_NAME.add(userDetail.getString("DISTRICT_NAME"));
                PROVINCE_NAME.add(userDetail.getString("PROVINCE_NAME"));
                ZIPCODE.add(userDetail.getString("ZIPCODE"));

                All.add(userDetail.getString("SUB_DISTRICT_NAME")+" ,"+userDetail.getString("DISTRICT_NAME")+" ,"+userDetail.getString("PROVINCE_NAME"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final EditText addr = rootView.findViewById(R.id.addaddress1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, All);
        final AutoCompleteTextView textView = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton1);
        textView.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DISTRICT_NAME);
        final AutoCompleteTextView textView2 = (AutoCompleteTextView) rootView.findViewById(R.id.addDistrict1);
        textView2.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PROVINCE_NAME);
        final AutoCompleteTextView textView3 = (AutoCompleteTextView) rootView.findViewById(R.id.addprovince1);
        textView3.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ZIPCODE);
        final AutoCompleteTextView textView4= (AutoCompleteTextView) rootView.findViewById(R.id.addcode1);
        textView4.setAdapter(adapter4);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = All.indexOf(textView.getText().toString());
                //Toast.makeText(getActivity(),"rtrtrtr"+ZIPCODE.get(a),Toast.LENGTH_SHORT).show();
                textView.setText(SUB_DISTRICT_NAME.get(a));
                textView2.setText(DISTRICT_NAME.get(a));
                textView3.setText(PROVINCE_NAME.get(a));
                textView4.setText(ZIPCODE.get(a));

            }
        });

        final EditText addr2 = rootView.findViewById(R.id.addaddress2);
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, All);
        final AutoCompleteTextView textView11 = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton2);
        textView11.setAdapter(adapter11);
        ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DISTRICT_NAME);
        final AutoCompleteTextView textView12 = (AutoCompleteTextView) rootView.findViewById(R.id.addDistrict2);
        textView12.setAdapter(adapter12);
        ArrayAdapter<String> adapter13 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PROVINCE_NAME);
        final AutoCompleteTextView textView13 = (AutoCompleteTextView) rootView.findViewById(R.id.addprovince2);
        textView13.setAdapter(adapter13);
        ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ZIPCODE);
        final AutoCompleteTextView textView14= (AutoCompleteTextView) rootView.findViewById(R.id.addcode2);
        textView14.setAdapter(adapter14);

        textView11.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = All.indexOf(textView11.getText().toString());
                //Toast.makeText(getActivity(),"rtrtrtr"+ZIPCODE.get(a),Toast.LENGTH_SHORT).show();
                textView11.setText(SUB_DISTRICT_NAME.get(a));
                textView12.setText(DISTRICT_NAME.get(a));
                textView13.setText(PROVINCE_NAME.get(a));
                textView14.setText(ZIPCODE.get(a));

            }
        });


        sameLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) sameLocation).isChecked();
                if(checked){
                    addr2.setText(addr.getText().toString());
                    textView11.setText(textView.getText().toString());
                    textView12.setText(textView2.getText().toString());
                    textView13.setText(textView3.getText().toString());
                    textView14.setText(textView4.getText().toString());
                }else {
                    addr2.setText(null);
                    textView11.setText(null);
                    textView12.setText(null);
                    textView13.setText(null);
                    textView14.setText(null);
                }
            }
        });






        return rootView;
    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("zipcode.json");
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


    public boolean ValidAll(ViewPager pager) {
        pager.setCurrentItem(1);
        if(!ValidPlace1() | !ValidSubPlace1() | !ValidPlace2() | !ValidSubPlace2())return false;
        return true;
    }

    boolean ValidPlace1(){

        EditText txt = (EditText) rootView.findViewById(R.id.addaddress1);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }

    boolean ValidSubPlace1(){

        AutoCompleteTextView txt = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton1);

        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }


    boolean ValidPlace2(){

        EditText txt = (EditText) rootView.findViewById(R.id.addaddress2);
        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }

    boolean ValidSubPlace2(){

        AutoCompleteTextView txt = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton2);

        if(txt.getText().toString().isEmpty()){
            txt.requestFocus();
            txt.setError("กรุณากรอกข้อมูล");
            return false;
        }
        return true;
    }



}