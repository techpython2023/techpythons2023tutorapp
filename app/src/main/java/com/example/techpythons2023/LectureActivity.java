package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Prevalent.Prevalent;

public class LectureActivity extends AppCompatActivity {

    TextView lectureemail;
    Button modsbtn,tarequestsbtn,logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        lectureemail = findViewById(R.id.lectureemail);
        modsbtn = findViewById(R.id.modsbtn);
        tarequestsbtn = findViewById(R.id.tarequestsbtn);
        logoutbtn = findViewById(R.id.logoutbtn);



        lectureemail.setText(Prevalent.currentOnlineUser.getEmail().toString().replace(",","."));
        Selected.value3 = Prevalent.currentOnlineUser.getEmail().toLowerCase();
        modsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LectureActivity.this,LectureModulesActivity.class);
                startActivity(i);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LectureActivity.this,LoginActivity.class);

                startActivity(i);
            }
        });

        tarequestsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LectureActivity.this, LectureTARequestsActivity.class);
                startActivity(i);
            }
        });


    }
}