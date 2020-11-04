package com.example.workingtimewfh.ui.admin.home_admin;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.EditReal.EditReal;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeAdminFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    FirebaseFirestore db ;
    Query queryR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        queryR = db.collection("user").whereEqualTo("status","user");
        final FirestoreRecyclerOptions<UserStruct> options = new FirestoreRecyclerOptions.Builder<UserStruct>().setQuery(queryR,UserStruct.class).build();

        recyclerAdapter = new RecyclerAdapter(options);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);
        recyclerView = root.findViewById(R.id.RecycleUser);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);




        RecyclerTouchListener touchListener = new RecyclerTouchListener(getActivity(),recyclerView);
        touchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                FirebaseFirestore detail = FirebaseFirestore.getInstance();
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);
                Query a = detail.collection("user").whereEqualTo("tel",recyclerAdapter.getItem(position).getTel());
                a.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        dialog.dismiss();
                        //Toast.makeText(getActivity(),queryDocumentSnapshots.getDocuments().get(0).getId(),Toast.LENGTH_SHORT).show();
                        Intent ToDetail =new Intent(getActivity(),DetailEmployees.class);
                        ToDetail.putExtra("id",queryDocumentSnapshots.getDocuments().get(0).getId());
                        startActivity(ToDetail);
                    }
                });
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        })
                .setSwipeOptionViews(R.id.delete_task,R.id.tel_task,R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        switch (viewID){
                            case R.id.delete_task:

                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                dialog.setTitle("ยืนยันการลบพนักงาน");
                                dialog.setIcon(R.drawable.ic_delete_forever_black_24dp);
                                dialog.setCancelable(true);
                                dialog.setMessage("ต้องการการลบพนักงานชื่อ"+recyclerAdapter.getItem(position).getName()+" "+recyclerAdapter.getItem(position).getLastname()+"หรือไม่?");
                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseFirestore delete = FirebaseFirestore.getInstance();

                                        Query a = delete.collection("user").whereEqualTo("tel",recyclerAdapter.getItem(position).getTel());
                                        a.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                //Toast.makeText(getActivity(),""+queryDocumentSnapshots.getDocuments().get(0).getId(),Toast.LENGTH_SHORT).show();

                                                FirebaseFirestore delete = FirebaseFirestore.getInstance();
                                                delete.collection("user").document(queryDocumentSnapshots.getDocuments().get(0).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getContext(),"ลบสำเร็จ",Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(),"เกิดความผิดพลาด",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });

                                    }
                                });

                                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                dialog.show();

                                break;
                            case R.id.edit_task:

                                //Toast.makeText(getActivity(),"edit "+viewID +"  : "+recyclerAdapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
                                db.collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        List<DocumentSnapshot> a = queryDocumentSnapshots.getDocuments();
                                        String id = null;
                                        for (DocumentSnapshot x : a) {
                                            Map<String, Object> b = x.getData();

                                            if(b.get("tel") != null)
                                                if(((String)x.get("tel")).matches(recyclerAdapter.getItem(position).getTel()))
                                                    id = x.getId();
                                        }
                                        Intent intent_edit = new Intent(getActivity(), EditReal.class);
                                        intent_edit.putExtra("id",id);
                                        startActivity(intent_edit);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(),"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show();
                                    }
                                });


                                break;
                            case R.id.tel_task:

                                //Toast.makeText(getActivity(),"tel "+viewID +"  : "+recyclerAdapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", recyclerAdapter.getItem(position).getTel(), null));
                                startActivity(intent);

                                break;


                        }
                    }
                });
        recyclerView.addOnItemTouchListener(touchListener);


        SearchView searchView = (SearchView) root.findViewById(R.id.search_user);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String QueryString = query.toLowerCase();
                Query squery;
                try{
                    Integer.parseInt(QueryString);
                    squery = queryR.orderBy("tel").startAt(QueryString).endAt(QueryString + "\uf8ff");
                }catch (NumberFormatException e){
                    squery = queryR.orderBy("name").startAt(QueryString).endAt(QueryString + "\uf8ff");
                }
                FirestoreRecyclerOptions<UserStruct> options = new FirestoreRecyclerOptions.Builder<UserStruct>().setQuery(squery,UserStruct.class).build();
                recyclerAdapter.updateOptions(options);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String QueryString = newText.toLowerCase();

                Query squery;
                try{
                    Integer.parseInt(QueryString);
                    squery = queryR.orderBy("tel").startAt(QueryString).endAt(QueryString + "\uf8ff");
                }catch (NumberFormatException e){
                    squery = queryR.orderBy("name").startAt(QueryString).endAt(QueryString + "\uf8ff");
                }
                FirestoreRecyclerOptions<UserStruct> options = new FirestoreRecyclerOptions.Builder<UserStruct>().setQuery(squery,UserStruct.class).build();
                recyclerAdapter.updateOptions(options);

                return false;
            }
        });
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

}
