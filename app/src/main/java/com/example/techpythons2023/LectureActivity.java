package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techpythons2023.Prevalent.Prevalent;

public class LectureActivity extends AppCompatActivity {

    TextView lectureemail;
    Button modsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        lectureemail = findViewById(R.id.lectureemail);
        modsbtn = findViewById(R.id.modsbtn);


        lectureemail.setText(Prevalent.currentOnlineUser.getEmail().toString().replace(",","."));

        modsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LectureActivity.this,LectureModulesActivity.class);
                startActivity(i);
            }
        });


    }
}