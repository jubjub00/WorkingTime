package com.example.workingtimewfh.ui.user.change_password;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener{

    private ChangePasswordViewModel ChangePasswordViewModel;
    private TextInputLayout OldLayout,NewLayout,ConfLayout;
    String OldPassword,NewPassword,ConfPassword;
    View root;
    SharedPreferences sp;
    Button save,cancel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ChangePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        root = inflater.inflate(R.layout.fragment_change_password, container, false);
        OldLayout = root.findViewById(R.id.PO);
        NewLayout = root.findViewById(R.id.NP);
        ConfLayout = root.findViewById(R.id.CP);

        save = root.findViewById(R.id.btnchangepass);
        save.setOnClickListener(this);
        cancel = root.findViewById(R.id.btnchangepass2);
        cancel.setOnClickListener(this);


        return root;
    }
    public boolean ValidateOldPassword(){
        OldPassword = OldLayout.getEditText().getText().toString().trim();
        if(OldPassword.isEmpty()){
            OldLayout.setError("กรุณากรอกข้อมูล");
            return false;
        }else {
            OldLayout.setError(null);
            return true;
        }
    }
    public boolean ValidateNewPassword(){
        NewPassword = NewLayout.getEditText().getText().toString().trim();

        if(NewPassword.isEmpty()){
            NewLayout.setError("กรุณากรอกข้อมูล");
            return false;
        }else {
            NewLayout.setError(null);
            return true;
        }
    }
    public boolean ValidateConfPassword(){
        ConfPassword = ConfLayout.getEditText().getText().toString().trim();
        if(ConfPassword.isEmpty()){
            ConfLayout.setError("กรุณากรอกข้อมูล");
            return false;
        }else if(NewPassword.compareTo(ConfPassword) != 0){
            NewLayout.setError("รหัสผ่านไม่ตรงกัน");
            return false;
        }else {
            ConfLayout.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btnchangepass:
                if(!ValidateOldPassword() | !ValidateNewPassword() | !ValidateConfPassword()) return;
                if(ValidateNewPassword() && ValidateConfPassword()){
                    sp = getActivity().getSharedPreferences( "LoginPreferences", Context.MODE_PRIVATE);
                    if(OldPassword.compareTo(sp.getString("password","???")) == 0 && ValidateOldPassword()){
                        //Toast.makeText(getActivity(),""+sp.getString("username","???"),Toast.LENGTH_SHORT).show();
                        db.collection("user").document(sp.getString("KeyDocument","???")).update("password",NewPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(),"เปลี่ยนรหัสผ่านสำเร็จ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"เปลี่ยนรหัสผ่านไม่สำเร็จ"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else  OldLayout.setError("รหัสผ่านไม่ถูกต้อง");

                }

                break;
            case R.id.btnchangepass2:
                ((EditText)root.findViewById(R.id.passold)).getText().clear();
                ((EditText)root.findViewById(R.id.passnew)).getText().clear();
                ((EditText)root.findViewById(R.id.passcon)).getText().clear();
                break;
        }
    }
}
