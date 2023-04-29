package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.Lecturemoduleitem;
import com.example.techpythons2023.Model.LecturetaRequestsAdapter;
import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Model.TarequestItem;
import com.example.techpythons2023.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HodrequestdetailsActivity extends AppCompatActivity {
    TextView modnamev,cosnamev,minmark, minqual, reson,status,txtapprove,txtdecline;
    Button lecpotbtn, tareqbtn,viewappsbtn;
    ImageButton approvebtn,declinebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodrequestdetails);
        modnamev = findViewById(R.id.modnamev);
        cosnamev = findViewById(R.id.cosnamev);
        minmark = findViewById(R.id.minmark);
        minqual = findViewById(R.id.minqual);
        reson = findViewById(R.id.reson);
        status = findViewById(R.id.status);

        approvebtn = findViewById(R.id.approverebtn);
        declinebtn = findViewById(R.id.declinereqbtn);

        txtapprove = findViewById(R.id.txtapprove);
        txtdecline = findViewById(R.id.txtdecline);

        lecpotbtn = findViewById(R.id.lecpotbtn);
        tareqbtn = findViewById(R.id.tareqbtn);
        viewappsbtn = findViewById(R.id.viewappsbtn);


        viewappsbtn.setVisibility(View.GONE);



        lecpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodrequestdetailsActivity.this, HodActivity.class);
                startActivity(i);
            }
        });

        tareqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodrequestdetailsActivity.this, HodTArequestsActivity.class);
                startActivity(i);
            }
        });


        viewappsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodrequestdetailsActivity.this, HodappsActivity.class);
                startActivity(i);
            }
        });


        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Approverequest();
            }
        });

        txtapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Approverequest();
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

                    if(lecmod.getStatus().equals("Approved and job posted" )){
                        approvebtn.setVisibility(View.GONE);
                        txtapprove.setVisibility(View.GONE);
                        declinebtn.setVisibility(View.GONE);
                        txtdecline.setVisibility(View.GONE);
                        tareqbtn.setVisibility(View.GONE);
                        viewappsbtn.setVisibility(View.VISIBLE);

                    }
                }
                else
                {
                    Toast.makeText(HodrequestdetailsActivity.this, "Request doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HodrequestdetailsActivity.this, HodTArequestsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    private void Approverequest() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                TarequestItem lecmod = dataSnapshot.child("Tarequests").child(Selected.value1).getValue(TarequestItem.class);


                RootRef.child("Tarequests").child(lecmod.getRequestid()).setValue(new TarequestItem(lecmod.getLecemail(), lecmod.getLecmodcos(),
                        lecmod.getMinmark(), lecmod.getMinqual(), lecmod.getModname(), lecmod.getReason(),lecmod.getRequestid(),  "Approved and job posted"));


                if (lecmod != null)
                {
                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            //Lecturemoduleitem lecmod = dataSnapshot.child("Taopenings").child(Selected.value1).getValue(Lecturemoduleitem.class);

                            if (!(dataSnapshot.child("Taopenings").child(Selected.value1).exists()))
                            {
                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("Jobid",lecmod.getRequestid());
                                userdataMap.put("Requestid",lecmod.getRequestid());
                                userdataMap.put("Modname",lecmod.getModname());
                                userdataMap.put("Lecmodcos",lecmod.getLecmodcos());
                                userdataMap.put("Minmark",lecmod.getMinmark());
                                userdataMap.put("Minqual",lecmod.getMinqual());
                                userdataMap.put("Status","job position open");
                                userdataMap.put("Lecemail",lecmod.getLecemail());
                                userdataMap.put("Hodemail", Prevalent.currentOnlineUser.getEmail());




                                RootRef.child("Taoppenings").child(lecmod.getRequestid()).updateChildren(userdataMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(HodrequestdetailsActivity.this, "Request approved and job posted", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(HodrequestdetailsActivity.this, HodrequestdetailsActivity.class);
                                                    startActivity(i);
                                                }
                                                else
                                                {

                                                    Toast.makeText(HodrequestdetailsActivity.this, "Error couldnt post job ", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(HodrequestdetailsActivity.this, HodrequestdetailsActivity.class);
                                                    startActivity(i);

                                                }
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(HodrequestdetailsActivity.this, "Error job exists ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(HodrequestdetailsActivity.this, HodrequestdetailsActivity.class);
                                startActivity(i);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(HodrequestdetailsActivity.this, "Request doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HodrequestdetailsActivity.this, HodTArequestsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}