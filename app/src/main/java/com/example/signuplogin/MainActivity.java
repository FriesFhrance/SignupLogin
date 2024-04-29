package com.example.signuplogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    // signup codes
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupBtn;
    private TextView loginRedirectText;


    // checkbox codes
    CheckBox showCheckBox_btn;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // SignUp Codes
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.email);
        signupPassword = findViewById(R.id.password);
        signupBtn = findViewById(R.id.sgn_btn);
        loginRedirectText = findViewById(R.id.loginRedirecText);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                if (user.isEmpty())
                {
                    signupEmail.setError("Email cannot be empty");
                }
                if (password.isEmpty())
                {
                    signupPassword.setError("Password cannot be empty");
                }

                else
                {
                    auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "SignUp Successful" ,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, login.class));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "SignUp Failed" + task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }

        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });


        // checkbox codes for show and hide password
        showCheckBox_btn = findViewById(R.id.check_btn);
        pass = findViewById(R.id.password);

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