package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techpythons2023.Prevalent.Prevalent;

public class HodActivity extends AppCompatActivity {
    Button cosbtn;
    TextView hodemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod);
        cosbtn = (Button) findViewById(R.id.cosbtn);
        hodemail = findViewById(R.id.hodemail);


        hodemail.setText(Prevalent.currentOnlineUser.getEmail().toString().replace(",","."));


        cosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodActivity.this, HodCoursesActivity.class);
                startActivity(i);
            }
        });
    }
}