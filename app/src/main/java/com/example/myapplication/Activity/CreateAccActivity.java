package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class CreateAccActivity extends AppCompatActivity {
    EditText edtEmail_create,edtPassnew1_create,edtPassnew2_create;
    Button btnSignUp;
    ImageButton btnBack;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
        progressDialog=new ProgressDialog(CreateAccActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Sign Up");
        progressDialog.setCancelable(false);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSignUp() {
        String email=edtEmail_create.getText().toString().trim();
        String password1=edtPassnew1_create.getText().toString().trim();
        String password2=edtPassnew2_create.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "You haven't entered your email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password1)) {
            Toast.makeText(this, "You haven't entered your  new password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "You haven't entered your new Re-enter new password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password1.equals(password2)){
            Toast.makeText(this, "Password and re-entered password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(CreateAccActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreateAccActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });

    }

    private void updateUI(FirebaseUser user) {
        if(user==null){
            Toast.makeText(CreateAccActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Authentication successful", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        edtEmail_create=findViewById(R.id.edtEmail_create);
        edtPassnew1_create=findViewById(R.id.edtPassnew1_create);
        edtPassnew2_create=findViewById(R.id.edtPassnew2_create);
        btnSignUp=findViewById(R.id.btnSignUp);
        btnBack=findViewById(R.id.btnBack);
    }
}