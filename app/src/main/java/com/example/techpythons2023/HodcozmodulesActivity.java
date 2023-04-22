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
import android.widget.Toast;

import com.example.techpythons2023.Model.HodCourseModulesRecyclerAdapter;
import com.example.techpythons2023.Model.LectureModulesRecyclerAdapter;
import com.example.techpythons2023.Model.ModuleItem;
import com.example.techpythons2023.Model.Selected;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HodcozmodulesActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<ModuleItem> moduleItemArrayList;
    HodCourseModulesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodcozmodules);



        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.hodcozrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        moduleItemArrayList = new ArrayList<>();



        readData();

    }

    private void readData() {


        databaseReference.child("Modules").orderByChild("Cosname").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moduleItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModuleItem moduleItem = dataSnapshot.getValue(ModuleItem.class);
                    if(moduleItem.getCosname().equals(Selected.value1.toString())){
                        moduleItemArrayList.add(moduleItem);
                    }
                }

                adapter = new HodCourseModulesRecyclerAdapter(HodcozmodulesActivity.this, moduleItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}