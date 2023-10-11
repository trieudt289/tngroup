package com.example.myapplication.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassFragment extends Fragment {
    EditText edtpassnew1,edtpassnew2;
    Button btnChange;
    View view;
    ImageButton btnBack2;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_change_pass, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickUpdateprofile();
            }
        });
    }

    private void onclickUpdateprofile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword1 = edtpassnew1.getText().toString().trim();
        String newPassword2 = edtpassnew2.getText().toString().trim();



        if (TextUtils.isEmpty(newPassword1)) {
            Toast.makeText(getActivity(), "You haven't entered your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword2)) {
            Toast.makeText(getActivity(), "You haven't entered a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPassword1.equals(newPassword2)){
            Toast.makeText(getActivity(), "Password and re-entered password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        user.updatePassword(newPassword1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mainActivity, "Password update successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            openDiaalog();
                        }
                    }
                });

    }

    private void openDiaalog() {
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_changepass);
        EditText edtEmail=dialog.findViewById(R.id.edtEmail_dia);
        EditText edtpass=dialog.findViewById(R.id.edtPass_dia);
        Button btnChange_dia=dialog.findViewById(R.id.btnchange_dia);
        String email_dia = edtEmail.getText().toString().trim();
        String pass_dia = edtpass.getText().toString().trim();
        dialog.show();

        btnChange_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email_dia, pass_dia);

            // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   onclickUpdateprofile();
                               }
                               else {

                               }
                            }
                        });
            }
        });

    }

    private void addControls() {
        edtpassnew1=view.findViewById(R.id.edtpassnew1);
        edtpassnew2=view.findViewById(R.id.edtpassnew2);

        btnChange=view.findViewById(R.id.btnChangpass);
        btnBack2=view.findViewById(R.id.btnBack2);
        mainActivity= (MainActivity) getActivity();
    }
}