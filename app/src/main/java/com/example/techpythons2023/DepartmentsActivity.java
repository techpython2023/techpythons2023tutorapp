package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.techpythons2023.Model.DepartmentItem;
import com.example.techpythons2023.Model.DepartmentsRecyclerAdapter;
import com.example.techpythons2023.Model.FacultiesRecyclerAdapter;
import com.example.techpythons2023.Model.FacultyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<DepartmentItem> departmentItemArrayList;
    DepartmentsRecyclerAdapter adapter;
    Button adddepbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        adddepbtn = (Button) findViewById(R.id.adddepbtn);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        departmentItemArrayList = new ArrayList<>();


        adddepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(DepartmentsActivity.this, AdddepActivity.class);
                startActivity(i);
            }
        });




        readData();

    }

    private void readData() {

        databaseReference.child("Departments").orderByChild("Depname").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                departmentItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DepartmentItem facultyItem = dataSnapshot.getValue(DepartmentItem.class);
                    departmentItemArrayList.add(facultyItem);
                }
                adapter = new DepartmentsRecyclerAdapter(DepartmentsActivity.this, departmentItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }




}