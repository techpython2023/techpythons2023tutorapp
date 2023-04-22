package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.techpythons2023.Model.CourseItem;
import com.example.techpythons2023.Model.CoursesRecyclerAdapter;
import com.example.techpythons2023.Model.DepartmentItem;
import com.example.techpythons2023.Model.DepartmentsRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<CourseItem> courseItemArrayList;
    CoursesRecyclerAdapter adapter;
    Button adddepbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        adddepbtn = (Button) findViewById(R.id.adddepbtn);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        courseItemArrayList = new ArrayList<>();


        adddepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(CoursesActivity.this, AddCourseActivity.class);
                startActivity(i);
            }
        });




        readData();

    }

    private void readData() {

        databaseReference.child("Courses").orderByChild("Cosname").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CourseItem courseItem = dataSnapshot.getValue(CourseItem.class);
                    courseItemArrayList.add(courseItem);
                }
                adapter = new CoursesRecyclerAdapter(CoursesActivity.this, courseItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }





}