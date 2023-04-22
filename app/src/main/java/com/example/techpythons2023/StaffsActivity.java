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

import com.example.techpythons2023.Model.ModuleItem;
import com.example.techpythons2023.Model.ModulesRecyclerAdapter;
import com.example.techpythons2023.Model.StaffItem;
import com.example.techpythons2023.Model.StaffsRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffsActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<StaffItem> staffItemArrayList;
    StaffsRecyclerAdapter adapter;
    Button addstaffbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffs);

        addstaffbtn = (Button) findViewById(R.id.addstaffbtn);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        staffItemArrayList = new ArrayList<>();


        addstaffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(StaffsActivity.this, AddStaffActivity.class);
                startActivity(i);
            }
        });




        readData();

    }

    private void readData() {

        databaseReference.child("Users").orderByChild("email").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                staffItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StaffItem staffItem = dataSnapshot.getValue(StaffItem.class);
                    staffItemArrayList.add(staffItem);
                }
                adapter = new StaffsRecyclerAdapter(StaffsActivity.this, staffItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }



}