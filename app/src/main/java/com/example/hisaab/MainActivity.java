package com.example.hisaab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {


    Button go_register;
    Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        go_register = (Button) findViewById(R.id.button2);
        go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this , register.class);
                startActivity(intent1);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user  =mAuth.getCurrentUser();
                if (user!=null){
                    Intent intent2 = new Intent(getApplicationContext(), home.class);
                    startActivity(intent2);
                    finish();
                }
            }
        };
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_pass);


        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (TextUtils.isEmpty(emailString)){
                    email.setError("Email is required");
                }
                if (TextUtils.isEmpty(passwordString)){
                    password.setError("Password is required");
                }

                else {

                    progressDialog.setMessage("login in progress");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent2 = new Intent(MainActivity.this, home.class);
                                startActivity(intent2);
                                finish();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}