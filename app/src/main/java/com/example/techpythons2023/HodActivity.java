package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techpythons2023.Prevalent.Prevalent;

public class HodActivity extends AppCompatActivity {
    Button cosbtn,tarequestsbtn,intersbtn,logoutbtn;
    TextView hodemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod);
        cosbtn = (Button) findViewById(R.id.cosbtn);
        hodemail = findViewById(R.id.useremail);
        tarequestsbtn = findViewById(R.id.tarequestsbtn);
        intersbtn = findViewById(R.id.intersbtn);
        logoutbtn = findViewById(R.id.logoutbtn);





        hodemail.setText(Prevalent.currentOnlineUser.getEmail().toString().replace(",","."));


        cosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodActivity.this, HodCoursesActivity.class);
                startActivity(i);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        intersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodActivity.this, HodintersActivity.class);
                startActivity(i);
            }
        });

        tarequestsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodActivity.this, HodTArequestsActivity.class);
                startActivity(i);
            }
        });
    }
}