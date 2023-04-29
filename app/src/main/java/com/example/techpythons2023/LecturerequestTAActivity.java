package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.CourseItem;
import com.example.techpythons2023.Model.Lecturemoduleitem;
import com.example.techpythons2023.Model.Selected;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LecturerequestTAActivity extends AppCompatActivity {

    TextView cnamev, mnamev;
    EditText Minmark, Minqual;
    Button submitbtn;
    EditText txtReson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturerequest_taactivity);

        Minmark = findViewById(R.id.minmark);
        Minqual = findViewById(R.id.minqual);
        submitbtn = findViewById(R.id.submitbtn);

        txtReson = findViewById(R.id.txtReson);

        cnamev = findViewById(R.id.cnamev);
        mnamev = findViewById(R.id.mnamev);

        cnamev.setText(Selected.value3);
        mnamev.setText(Selected.value2);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String lecmodid = Selected.value1;
                String lecmodname = Selected.value2;
                String lecmodcosname = Selected.value3;
                String minmark = Minmark.getText().toString();
                String minqual = Minqual.getText().toString();
                String txtreson = txtReson.getText().toString();

                if(minmark.isEmpty() || minqual.isEmpty() || txtreson.isEmpty()){
                    Toast.makeText(LecturerequestTAActivity.this, "one or nore of your fields are empty", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LecturerequestTAActivity.this, LecturerequestTAActivity.class);
                    startActivity(i);
                }

                sendrequest(lecmodid,lecmodname,lecmodcosname,minmark,minqual,txtreson);
            }
        });





    }



    public  void sendrequest(final String lecmodid,final String lecmodname, final String lecmodcosname, final String minmark, final String minqual,final String txtreson){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Lecturemoduleitem lecmod = dataSnapshot.child("Lecturemodules").child(lecmodid).getValue(Lecturemoduleitem.class);
                if (!(dataSnapshot.child("Tarequests").child(lecmodid).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Requestid",lecmodid);
                    userdataMap.put("Modname",lecmodname);
                    userdataMap.put("Lecmodcos",lecmodcosname);
                    userdataMap.put("Minmark",minmark);
                    userdataMap.put("Minqual",minqual);
                    userdataMap.put("Reason",txtreson);
                    userdataMap.put("Status","request sent");
                    userdataMap.put("Lecemail",lecmod.getLecemail());



                    RootRef.child("Tarequests").child(lecmodid).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(LecturerequestTAActivity.this, "Request sent ", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LecturerequestTAActivity.this, LecturerequestTAActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {

                                        Toast.makeText(LecturerequestTAActivity.this, "Error couldnt send request ", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LecturerequestTAActivity.this, LecturerequestTAActivity.class);
                                        startActivity(i);

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(LecturerequestTAActivity.this, "Request already exists", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LecturerequestTAActivity.this, LecturerequestTAActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}