package com.example.signuplogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;

    // checkbox codes
    CheckBox showCheckBox_btn;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.lgn_email);
        loginPassword = findViewById(R.id.lgn_password);
        loginButton = findViewById(R.id.lgn_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if (!pass.isEmpty())
                    {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(login.this, "Login Successful" ,Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(login.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(login.this, "Login Failed" ,Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    else
                    {
                        loginPassword.setError("Password cannot be Empty");
                    }

                     if(email.isEmpty())
                    {
                        loginEmail.setError("Email cannot be Empty");
                    }

                    else
                    {
                        loginEmail.setError("Please enter valid email");
                    }
                }
            }
        });


        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, MainActivity.class));
            }
        });

        // checkbox codes for show and hide password

        showCheckBox_btn = findViewById(R.id.lgn_check_btn);
        pass = findViewById(R.id.lgn_password);

        showCheckBox_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    // for show password
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    // for hide password
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

    }
}