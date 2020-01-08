package com.eventapp.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eventapp.Dashboard.Dashboard;
import com.eventapp.Profile.FragmentMyProfile;
import com.eventapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity implements View.OnClickListener  {

    EditText et_email, et_pass, et_confirm_pass;
    Button Register;
    TextView forgotpass;
    FirebaseAuth Signup;
    ProgressBar progressBar;
    private static String emailPattern = "[a-zA-Z+{n}0-9._-]+@[a-z]+\\.+[a-z]+";
    private static String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}";
    private String Email;
    private String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById();
        setOnclickListner();
        Signup = FirebaseAuth.getInstance();
    }


    private void setOnclickListner() {
        Register.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

    }

    private void findViewById() {
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_confirm_pass = findViewById(R.id.et_confirm_pass);
        Register = findViewById(R.id.Register);
        progressBar = findViewById(R.id.progressBar);
        forgotpass = findViewById(R.id.forgotpass);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register:
                if (et_email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show();
                    return;
                }else if (!et_email.getText().toString().matches(emailPattern)){
                    Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show();
                    return;
                }else if (et_pass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                    return;
                }else if (et_pass.getText().toString().trim().length()<6){
                    Toast.makeText(this, "Password must be atleast 6 character", Toast.LENGTH_LONG).show();
                    return;
                } else if (!et_pass.getText().toString().matches(passwordPattern)){
                    Toast.makeText(this, "Password must contain atleast 6 character long with one uppercase, one lowercase, one digit.", Toast.LENGTH_LONG).show();
                    return;
                }else if (et_confirm_pass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                    return;
                }else if (!et_pass.getText().toString().equals(et_confirm_pass.getText().toString())){
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Email = et_email.getText().toString();
                    Password = et_pass.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    SignUpFirebase();
                } break;
                case R.id.forgotpass:
                        startActivity(new Intent(signup.this, forgotPassword.class));
                        finish();


        }
    }

    private void SignUpFirebase() {
        Signup.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(signup.this, "Sign Up successful" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(signup.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(signup.this, Dashboard.class));
                            finish();
                        }
                    }
                });
    }
        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }
    }
