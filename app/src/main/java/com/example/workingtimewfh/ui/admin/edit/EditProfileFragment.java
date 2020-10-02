package com.example.workingtimewfh.ui.admin.edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditProfileFragment extends Fragment {
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    Query queryR;
    private RecyclerView recyclerView;
    private EditAdapter recyclerAdapter;
    ArrayList<ArrayList<String>> data ;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        data = new ArrayList<>();
        Task<QuerySnapshot> item = db.collection("leave").get();
        recyclerView = root.findViewById(R.id.recyclerView);

        item.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> i = queryDocumentSnapshots.getDocuments();
                if(!i.isEmpty()){
                    for (DocumentSnapshot x:i) {
                        final String name = x.getId();
                        Iterator<String> iter = x.getData().keySet().iterator();
                        Map<String, Object> y = x.getData();
                        while(iter.hasNext()){
                            final String doc = iter.next();
                            final Map<String, Object> a = (Map<String, Object>) y.get(doc);
                            db.collection("user").document(name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(((String)a.get("status")).matches("รออนุมัติ")) {

                                        String n = (String) documentSnapshot.get("name");
                                        String l = (String) documentSnapshot.get("lastname");
                                        ArrayList<String> tmp = new ArrayList<>();
                                        tmp.add(n + " " + l);
                                        tmp.add(doc);
                                        tmp.add((String) a.get("date_end"));
                                        tmp.add((String) a.get("date_start"));
                                        tmp.add((String) a.get("description"));
                                        tmp.add((String) a.get("time_end"));
                                        tmp.add((String) a.get("time_start"));
                                        tmp.add((String) a.get("title"));
                                        tmp.add(name);

                                        data.add(tmp);

                                        recyclerAdapter = new EditAdapter(data);
                                        recyclerAdapter.setOnClickListener(new EditAdapter.OnItemClickListener() {
                                            @Override
                                            public void OnItemClick(final int position, final String Name, final String DateCommit1, final boolean yes) {
                                                String title = "";
                                                if(yes) title = "อนุมัติ";
                                                else title = "ไม่อนุมัติ";
                                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                                alertDialog.setTitle("ยันยืนการ"+title+"การลางาน")
                                                        .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                db.collection("leave").document(Name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        Map<String, Object> a = documentSnapshot.getData();
                                                                        Iterator<String> purpose = a.keySet().iterator();
                                                                        while (purpose.hasNext()){
                                                                            String Day = purpose.next();
                                                                            if(Day.matches(DateCommit1)) {
                                                                                Map<String, String> it = (Map<String, String>) a.get(Day);
                                                                                if(yes)
                                                                                    it.replace("status","อนุมัติ");
                                                                                else
                                                                                    it.replace("status","ไม่อนุมัติ");
                                                                                HashMap<String, Object> detail = new HashMap<String, Object>();
                                                                                detail.put(Day,it);
                                                                                db.collection("leave").document(name).set(detail, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
                                                                                        Toast.makeText(getActivity(), "ดำเนินการสำเร็จ", Toast.LENGTH_SHORT).show();
                                                                                        data.remove(position);
                                                                                        recyclerAdapter.notifyItemRemoved(position);


                                                                                        recyclerAdapter = new EditAdapter(data);
                                                                                        recyclerView.setAdapter(recyclerAdapter);
                                                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                                                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
                                                                                        recyclerView.addItemDecoration(dividerItemDecoration);
                                                                                    }
                                                                                });


                                                                            }
                                                                        }

                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getActivity(),"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }
                                                        })
                                                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                alertDialog.show();
                                            }
                                        });

                                        recyclerView.setAdapter(recyclerAdapter);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
                                        recyclerView.addItemDecoration(dividerItemDecoration);

                                    }
                                }
                            });



                        }


                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"การเชื่อมต่อขัดข้อง",Toast.LENGTH_SHORT).show();
            }
        });





        return root;
    }
}
