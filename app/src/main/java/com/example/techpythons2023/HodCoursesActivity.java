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
import com.example.techpythons2023.Model.HodCourseItem;
import com.example.techpythons2023.Model.HodCoursesRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HodCoursesActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<HodCourseItem> hodcourseItemArrayList;
    HodCoursesRecyclerAdapter adapter;
    Button hodpotbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_courses);

        hodpotbtn = findViewById(R.id.hodpotbtn);



        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        hodcourseItemArrayList = new ArrayList<>();


        hodpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodCoursesActivity.this, HodActivity.class);
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
                hodcourseItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HodCourseItem hodcourseItem = dataSnapshot.getValue(HodCourseItem.class);
                    hodcourseItemArrayList.add(hodcourseItem);
                }
                adapter = new HodCoursesRecyclerAdapter(HodCoursesActivity.this,hodcourseItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}






