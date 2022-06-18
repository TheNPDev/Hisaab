package com.example.hisaab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class home extends AppCompatActivity {
    EditText budget,expenditure,credit;
    TextView saving;
    Button save , account,clear;
    int b , e ,s,c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        budget = findViewById(R.id.budget);
        expenditure = findViewById(R.id.expenditure);
        saving = findViewById(R.id.saving);
        credit = findViewById(R.id.Credit);

        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , account.class);
                startActivity(intent);
            }
        });






        save = findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double b = Double.parseDouble(budget.getText().toString());
                double e = Double.parseDouble(expenditure.getText().toString());
                double c = Double.parseDouble(credit.getText().toString());
                double s = b-e+c;
                saving.setText(Double.toString(s));}
        });
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                budget.setText(null);
                expenditure.setText(null);
                credit.setText(null);
                saving.setText(null);


            }
        });











    }
}