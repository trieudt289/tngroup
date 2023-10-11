package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.User.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddUserFragment extends Fragment {

    EditText edtName, edtGmail;
    RadioButton radiomale, radiofemale;
    View view;
    Button btnAddstaff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnAddstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String gmail = edtGmail.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "You haven't entered name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(gmail)) {
                    Toast.makeText(getActivity(), "You haven't entered a gmail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(gmail)) {
                    Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean gender = true;
                if(radiomale.isChecked()){
                    gender=true;
                }
                if(radiofemale.isChecked()){
                    gender=false;
                }
                User user = new User(name, gmail, gender);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference("User_Staff");


                reference.child(name).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frContent, new ListStaffFragment());
                            transaction.addToBackStack(null);
                            transaction.commit();
                            if (getActivity() != null) {
                                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                                if (bottomNavigationView != null) {
                                    bottomNavigationView.setSelectedItemId(R.id.bot_liststaff);
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), "Thêm thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }

    private void addControls() {
        edtGmail = view.findViewById(R.id.edtGmail);
        edtName = view.findViewById(R.id.edtName);
        btnAddstaff = view.findViewById(R.id.btnAddstaff);
        radiofemale = view.findViewById(R.id.radiofemale);
        radiomale = view.findViewById(R.id.radiomale);
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
