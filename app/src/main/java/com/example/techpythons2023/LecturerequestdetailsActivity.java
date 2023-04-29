package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.Lecturemoduleitem;
import com.example.techpythons2023.Model.LecturetaRequestsAdapter;
import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Model.TarequestItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LecturerequestdetailsActivity extends AppCompatActivity {

    TextView modnamev,cosnamev,minmark, minqual, reson,status;
    Button lecpotbtn, tareqbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturerequestdetails);
        modnamev = findViewById(R.id.modnamev);
        cosnamev = findViewById(R.id.cosnamev);
        minmark = findViewById(R.id.minmark);
        minqual = findViewById(R.id.minqual);
        reson = findViewById(R.id.reson);
        status = findViewById(R.id.status);

        lecpotbtn = findViewById(R.id.lecpotbtn);
        tareqbtn = findViewById(R.id.tareqbtn);


        lecpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LecturerequestdetailsActivity.this, LectureActivity.class);
                startActivity(i);
            }
        });

        tareqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LecturerequestdetailsActivity.this, LectureTARequestsActivity.class);
                startActivity(i);
            }
        });





        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                TarequestItem lecmod = dataSnapshot.child("Tarequests").child(Selected.value1).getValue(TarequestItem.class);
                if (lecmod != null)
                {
                   modnamev.setText(lecmod.getModname());
                   cosnamev.setText(lecmod.getLecmodcos());
                   minmark.setText(lecmod.getMinmark() +"%");
                   minqual.setText(lecmod.getMinqual());
                   reson.setText(lecmod.getReason());
                   status.setText(lecmod.getStatus());
                }
                else
                {
                    Toast.makeText(LecturerequestdetailsActivity.this, "Request doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LecturerequestdetailsActivity.this, LecturetaRequestsAdapter.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}