package com.ibm.mymedicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText registerFullName, registerEmail, registerPassword, registerConfPassword;
    Button registerUserBtn, gotoLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPassword = findViewById(R.id.confirmPassword);
        registerUserBtn = findViewById(R.id.registerbtn);
        gotoLoginBtn = findViewById(R.id.gotoLoginBtn);

        fAuth = FirebaseAuth.getInstance();

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = registerFullName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPassword = registerConfPassword.getText().toString();

                if(fullName.isEmpty()){
                    registerFullName.setError("Full Name is Required");
                    return;
                }

                if(email.isEmpty()){
                    registerEmail.setError("Email is Required");
                    return;
                }

                if(password.isEmpty()){
                    registerPassword.setError("Password is Required");
                    return;
                }

                if(confPassword.isEmpty()){
                    registerConfPassword.setError("Confirmation Password is Required");
                    return;
                }

                if(!confPassword.equals(password)){
                    registerConfPassword.setError("Password does not match");
                    return;
                }

                Toast.makeText(Register.this, "Data Validated", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
