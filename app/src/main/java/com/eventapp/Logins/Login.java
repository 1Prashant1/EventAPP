package com.eventapp.Logins;

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
import com.eventapp.signup.forgotPassword;
import com.eventapp.signup.signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText et_email,et_pass;
    TextView tv_signup, forgotpass;
    Button login;
    FirebaseAuth Login;
    ProgressBar progressBar;
    private static String emailPattern = "[a-zA-Z+{n}0-9._-]+@[a-z]+\\.+[a-z]+";
    private static String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}";
    private String Email;
    private String Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        setOnclickListner();
        Login = FirebaseAuth.getInstance();

        if (Login.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Dashboard.class));
            finish();
        }

    }

    private void setOnclickListner() {
        login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

    }

    private void findViewById() {
        progressBar = findViewById(R.id.progressBar);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        login = findViewById(R.id.login);
        tv_signup = findViewById(R.id.tv_signup);
        forgotpass = findViewById(R.id.forgotpass);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (et_email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (!et_email.getText().toString().matches(emailPattern)){
                    Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show();
                    return;
                }else if (et_pass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (!et_pass.getText().toString().matches(passwordPattern)){
                    Toast.makeText(this, "Password must contain atleast 8 character long with one uppercase, one lowercase, one digit.", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Email = et_email.getText().toString();
                    Password = et_pass.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    LoginFirebase();
                }break;
            case R.id.tv_signup:
                startActivity(new Intent(Login.this, signup.class));
                finish();
                break;
            case R.id.forgotpass:
                startActivity(new Intent(Login.this, forgotPassword.class));
                finish();
                break;
        }

    }

    private void LoginFirebase(){
        Login.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (Password.length() < 6) {
                                Toast.makeText(Login.this, "Password must be atlest 6 character", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }
}
