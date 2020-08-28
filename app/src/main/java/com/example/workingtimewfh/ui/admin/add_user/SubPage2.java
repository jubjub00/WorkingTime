package com.example.workingtimewfh.ui.admin.add_user;

import android.os.Bundle;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    public static SubPage2 newInstance() {
        SubPage2 fragment = new SubPage2();
        return fragment;
    }
    public SubPage2() { }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.subpage2, container, false);


        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("zipcode");


            for (int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);

                SUB_DISTRICT_NAME.add(userDetail.getString("SUB_DISTRICT_NAME"));
                DISTRICT_NAME.add(userDetail.getString("DISTRICT_NAME"));
                PROVINCE_NAME.add(userDetail.getString("PROVINCE_NAME"));
                ZIPCODE.add(userDetail.getString("ZIPCODE"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, SUB_DISTRICT_NAME);
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
                int a = SUB_DISTRICT_NAME.indexOf(textView.getText().toString());
                //Toast.makeText(getActivity(),"rtrtrtr"+ZIPCODE.get(a),Toast.LENGTH_SHORT).show();
                textView2.setText(DISTRICT_NAME.get(a));
                textView3.setText(PROVINCE_NAME.get(a));
                textView4.setText(ZIPCODE.get(a));

            }
        });


        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, SUB_DISTRICT_NAME);
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
                int a = SUB_DISTRICT_NAME.indexOf(textView11.getText().toString());
                //Toast.makeText(getActivity(),"rtrtrtr"+ZIPCODE.get(a),Toast.LENGTH_SHORT).show();
                textView12.setText(DISTRICT_NAME.get(a));
                textView13.setText(PROVINCE_NAME.get(a));
                textView14.setText(ZIPCODE.get(a));

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


}