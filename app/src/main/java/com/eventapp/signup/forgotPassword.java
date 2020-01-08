package com.eventapp.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eventapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText email;
    Button send;
    ProgressBar progressBar;
    FirebaseAuth Forgot;
    private static String emailPattern = "[a-zA-Z+{n}0-9._-]+@[a-z]+\\.+[a-z]+";
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViewById();
        setOnclickListner();
        Forgot = FirebaseAuth.getInstance();
    }

    private void setOnclickListner() {
        send.setOnClickListener(this);

    }


    private void findViewById() {
        email = findViewById(R.id.email);
        send = findViewById(R.id.send);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (email.getText().toString().trim().isEmpty()){
                    Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show();
                    return;
                }else if (!email.getText().toString().matches(emailPattern)){
                    Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    Email = email.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    forgotPassFirebase();
                }
        }
    }

    private void forgotPassFirebase() {
        Forgot.sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(forgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    }