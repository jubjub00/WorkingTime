package com.example.workingtimewfh.ui.user.leave;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class view_leave extends Fragment {
    View root;
    private ViewLeaveAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    FirebaseFirestore db ;
    ViewLeaveAdapter viewLeaveAdapter;
    Query queryR;
    ArrayList<ArrayList<String>> dataSet;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.tab_view_leave, container, false);
        recyclerView = root.findViewById(R.id.ViewLeave);


        dataSet = new ArrayList<ArrayList<String>>();

        SharedPreferences sp = getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String id = sp.getString("KeyDocument","null");
        if(!id.matches("null")){
            FirebaseFirestore.getInstance().collection("leave").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    Map<String, Object> item =  task.getResult().getData();
                    if(item != null) {
                        Iterator<String> a = item.keySet().iterator();
                        while(a.hasNext()){
                            String DCommit = a.next();
                            HashMap<String,String> nn = (HashMap<String, String>) item.get(DCommit);
                            ArrayList<String> tmp = new ArrayList<>();
                            tmp.add(DCommit);
                            tmp.add(nn.get("date_start"));
                            tmp.add(nn.get("time_start"));
                            tmp.add(nn.get("date_end"));
                            tmp.add(nn.get("time_end"));
                            tmp.add(nn.get("status"));
                            dataSet.add(tmp);
                        }

                        viewLeaveAdapter = new ViewLeaveAdapter(dataSet);
                        recyclerView.setAdapter(viewLeaveAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(dividerItemDecoration);
                    }
                }
            });




        }



    return root;

    }




}