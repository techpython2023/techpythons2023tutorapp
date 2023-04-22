package com.example.techpythons2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button facultiesbtn,deparmentsbtn,courses,modules,staffs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        facultiesbtn = (Button) findViewById(R.id.facultiesbtn);
        deparmentsbtn =(Button) findViewById(R.id.departmentsbtn);
        courses =(Button) findViewById(R.id.coursesbtn);
        modules =(Button) findViewById(R.id.modulesbtn);
        staffs =(Button) findViewById(R.id.staffsbtn);




        facultiesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdminActivity.this, FacultiesActivity.class);
                startActivity(i);
            }
        });
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdminActivity.this, AddCourseActivity.class);
                startActivity(i);
            }
        });
        deparmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdminActivity.this, AdddepActivity.class);
                startActivity(i);
            }
        });

        modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdminActivity.this, AddModuleActivity.class);
                startActivity(i);
            }
        });

        staffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdminActivity.this, AddStaffActivity.class);
                startActivity(i);
            }
        });

    }
}