package com.example.workingtimewfh.ui.user.workinglist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.workingtimewfh.ExtFunction;
import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    ExtFunction ext ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView sat;
    String UserID;
    ProgressBar w;

    ArrayList<HashMap<String, String>> ArrTask;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        ext = new ExtFunction();
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Intent a = getActivity().getIntent();
        UserID = a.getStringExtra("UserID");
        w = root.findViewById(R.id.WaitData);
        w.setVisibility(View.VISIBLE);
        ext = new ExtFunction();


        ShowListTask(root);
        showDate(root);
        return root;
    }

    public void ShowListTask(View root){
        sat = root.findViewById(R.id.ShowATask);



        ArrTask = new ArrayList<HashMap<String, String>>();
        db.collection("task").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.getResult().exists()) {

                    if(task.getResult().get(ext.GetDate()) != null){
                        HashMap<String,Object> mm = (HashMap<String,Object>) task.getResult().get(ext.GetDate());
                        Map<String, Object> sortedTask = new TreeMap<>();
                        sortedTask.putAll(mm);
                        Iterator iterator = sortedTask.keySet().iterator();
                        while( iterator.hasNext() )
                        {
                            String time = iterator.next().toString();

                            HashMap<String,String> nn = (HashMap<String, String>) mm.get(time);
                            HashMap<String, String> PerTask;
                            PerTask = new HashMap<String, String>();
                            PerTask.put("Time", time);
                            PerTask.put("Head",nn.get("head"));
                            PerTask.put("Loc", nn.get("location"));
                            ArrTask.add(PerTask);

                        }
                        SimpleAdapter sAdap = new SimpleAdapter(getActivity(), ArrTask, R.layout.listtask,new String[] {"Time", "Head", "Loc"}, new int[] {R.id.Time, R.id.Head, R.id.Loc});
                        sat.setAdapter(sAdap);
                        w.setVisibility(View.GONE);

                    }else w.setVisibility(View.GONE);

                }else w.setVisibility(View.GONE);

            }
        });



    }

    public void showDate(View root){
        TextView ShowDate;
        ShowDate = root.findViewById(R.id.showtime);
        ShowDate.setText(ext.GetDate());
    }
}
