package com.example.hisaab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Currency;
import java.util.List;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button go_login;
    Button register;
    private ProgressBar progressBar;
    private EditText name, phone, password, email;
    private Spinner Currency;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String[] currency = {"Indian Rupee", "Pakistani Rupee", "Kuwait Dinar", "Bahraini Dinar", "Oman Rial", "Pound Sterling", "Euro", "US Dollar", "Yen", "Canadian Dollar", "Afghani", "Peso", "Rupiah", "Dirham", "Taka"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_register);
        Currency = (Spinner) findViewById(R.id.Currency);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Currency.setAdapter(adapter);
        Currency.setOnItemSelectedListener(this);


        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_pass);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.register_phone);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        go_login = findViewById(R.id.button3);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String phoneString = phone.getText().toString();
                String nameString = name.getText().toString();
                String currencyString = Currency.getSelectedItem().toString();

                if (TextUtils.isEmpty(emailString)){
                    email.setError("Email is required");
                }
                if (TextUtils.isEmpty(passwordString)){
                    password.setError("Password is required");
                }
                if (TextUtils.isEmpty(phoneString)){
                    phone.setError("Phone is required");
                }
                if (TextUtils.isEmpty(nameString)){
                    name.setError("Name is required");
                }
                else {

                    progressDialog.setMessage("registration in progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }




   @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
