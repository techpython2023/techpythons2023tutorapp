package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.techpythons2023.Model.HodintersAdapter;
import com.example.techpythons2023.Model.InterviewItem;
import com.example.techpythons2023.Model.UserintersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserintersActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<InterviewItem> moduleItemArrayList;
    UserintersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinters);



        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        moduleItemArrayList = new ArrayList<>();





        readData();

    }

    private void readData() {

        databaseReference.child("Interviews").orderByChild("Date").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moduleItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InterviewItem moduleItem = dataSnapshot.getValue(InterviewItem.class);
                    moduleItemArrayList.add(moduleItem);
                }

                adapter = new UserintersAdapter(UserintersActivity.this, moduleItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


}