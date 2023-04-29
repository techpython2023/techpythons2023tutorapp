package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techpythons2023.Prevalent.Prevalent;

public class UserActivity extends AppCompatActivity {

    Button myappsbtn,avjobsbtn;
    TextView useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myappsbtn = findViewById(R.id.myappsbtn);
        avjobsbtn = findViewById(R.id.avjobsbtn);
        useremail = findViewById(R.id.useremail);

        useremail.setText(Prevalent.currentOnlineUser.getEmail());

        avjobsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(UserActivity.this, UseropeningsActivity.class);
                startActivity(i);
            }
        });


        myappsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(UserActivity.this, MyappsActivity.class);
                startActivity(i);
            }
        });
    }
}