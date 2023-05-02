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

import com.example.techpythons2023.Model.HodRequestsAdapter;
import com.example.techpythons2023.Model.LecturetaRequestsAdapter;
import com.example.techpythons2023.Model.TarequestItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HodTArequestsActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<TarequestItem> moduleItemArrayList;
    HodRequestsAdapter adapter;

    Button hodpotbtn,hodcozsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_tarequests);

        hodcozsbtn = findViewById(R.id.hodcozsbtn);
        hodpotbtn = findViewById(R.id.hodpotbtn);



        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        moduleItemArrayList = new ArrayList<>();

        hodpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodTArequestsActivity.this, HodActivity.class);
                startActivity(i);
            }
        });


        hodcozsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodTArequestsActivity.this, HodCoursesActivity.class);
                startActivity(i);
            }
        });



        readData();

    }

    private void readData() {

        databaseReference.child("Tarequests").orderByChild("Status").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moduleItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TarequestItem moduleItem = dataSnapshot.getValue(TarequestItem.class);
                    moduleItemArrayList.add(moduleItem);

                }
                adapter = new HodRequestsAdapter(HodTArequestsActivity.this, moduleItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}






