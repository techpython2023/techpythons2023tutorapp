package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.techpythons2023.Model.FacultyItem;
import com.example.techpythons2023.Model.FacultiesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class FacultiesActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<FacultyItem> facultyItemItemArrayList;
    FacultiesRecyclerAdapter adapter;

    private Button submitbtn;
    private EditText facnameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        facultyItemItemArrayList = new ArrayList<>();

        submitbtn = (Button) findViewById(R.id.submitbtn);
        facnameInput = (EditText) findViewById(R.id.facName);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFaculties();
            }
        });

        readData();
    }



    private void readData() {

        databaseReference.child("Faculties").orderByChild("facultyName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facultyItemItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FacultyItem facultyItem = dataSnapshot.getValue(FacultyItem.class);
                    facultyItemItemArrayList.add(facultyItem);
                }
                adapter = new FacultiesRecyclerAdapter(FacultiesActivity.this, facultyItemItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }



    private void AddFaculties()
    {
        String facname = facnameInput.getText().toString().toLowerCase(Locale.ROOT);

        if (TextUtils.isEmpty(facname))
        {
            Toast.makeText(this, "Please write faculty name", Toast.LENGTH_SHORT).show();
        }

        else
        {

            Validatefacultyname(facname);
        }
    }



    private void Validatefacultyname(final String facname)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Faculties").child(facname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Facultyname",facname);


                    RootRef.child("Faculties").child(facname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(FacultiesActivity.this, "Faculty has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(FacultiesActivity.this, FacultiesActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(FacultiesActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(FacultiesActivity.this,FacultiesActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(FacultiesActivity.this, "This " + facname + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FacultiesActivity.this, FacultiesActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}