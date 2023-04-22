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

import com.example.techpythons2023.Model.LectureModulesRecyclerAdapter;
import com.example.techpythons2023.Model.ModuleItem;
import com.example.techpythons2023.Model.ModulesRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LectureModulesActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<ModuleItem> moduleItemArrayList;
    LectureModulesRecyclerAdapter adapter;
    Button addmodbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        addmodbtn = (Button) findViewById(R.id.addmodbtn);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        moduleItemArrayList = new ArrayList<>();


        addmodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LectureModulesActivity.this, AddModuleActivity.class);
                startActivity(i);
            }
        });




        readData();

    }

    private void readData() {

        databaseReference.child("Modules").orderByChild("Modname").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moduleItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModuleItem moduleItem = dataSnapshot.getValue(ModuleItem.class);
                    moduleItemArrayList.add(moduleItem);
                }
                adapter = new LectureModulesRecyclerAdapter(LectureModulesActivity.this, moduleItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }





}