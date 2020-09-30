package com.example.workingtimewfh.ui.user.leave;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class view_leave extends Fragment {
    View root;
    private ViewLeaveAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ViewLeaveAdapter viewLeaveAdapter;
    Query queryR;
    ArrayList<ArrayList<String>> dataSet;
    String id;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.tab_view_leave, container, false);
        recyclerView = root.findViewById(R.id.ViewLeave);


        dataSet = new ArrayList<ArrayList<String>>();

        SharedPreferences sp = getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        id = sp.getString("KeyDocument","null");
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

                        viewLeaveAdapter.SetOnClickListener(new ViewLeaveAdapter.onItemClicked() {
                            @Override
                            public void clicked(final int position) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setTitle("ยืนยันการยกเลิกการลางาน")
                                        .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.collection("leave").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        Map<String, Object> list = documentSnapshot.getData();
                                                        Iterator<String> key = list.keySet().iterator();
                                                        HashMap<String, Object> i = new HashMap<>();
                                                        while(key.hasNext()){
                                                            String day = key.next();

                                                            if(!day.matches((String)dataSet.get(position).get(0)))
                                                                i.put(day,list.get(day));

                                                        }
                                                        db.collection("leave").document(id).delete();
                                                        db.collection("leave").document(id).set(i, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(getActivity(),"ดำเนินการสำเร็จ",Toast.LENGTH_SHORT).show();
                                                                dataSet.remove(position);
                                                                viewLeaveAdapter.notifyItemRemoved(position);

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getActivity(),"ดำเนินการผิดพลาด ลองอีกครั้ง",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });



                                                    }
                                                });
                                            }
                                        }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();


                            }
                        });


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