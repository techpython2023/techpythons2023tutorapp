package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.ApplicationItem;
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
import com.squareup.picasso.Picasso;


import java.util.HashMap;

public class HodappdetailsActivity extends AppCompatActivity {
    TextView fname,modname,appmark, appstatus, lecemail,txtapprove,txtdecline;
    Button lecpotbtn, tareqbtn;
    ImageButton approvebtn,declinebtn,closebtn;

    ImageView imageView, imageView2,imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodappdetails);

        fname = findViewById(R.id.fname);
        modname = findViewById(R.id.modname);
        appmark = findViewById(R.id.appmark);
        appstatus = findViewById(R.id.appstatus);
        lecemail = findViewById(R.id.lecemail);
        closebtn = findViewById(R.id.closebtn);
        imageView3 = findViewById(R.id.imageView3);

        approvebtn = findViewById(R.id.approverebtn);
        declinebtn = findViewById(R.id.declinereqbtn);

        txtapprove = findViewById(R.id.txtapprove);
        txtdecline = findViewById(R.id.txtdecline);

        lecpotbtn = findViewById(R.id.lecpotbtn);
        tareqbtn = findViewById(R.id.tareqbtn);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);





        lecpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodappdetailsActivity.this, HodActivity.class);
                startActivity(i);
            }
        });

        tareqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HodappdetailsActivity.this, ScheduleinterviewActivity.class);
                startActivity(i);
            }
        });



        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                imageView3.setVisibility(View.GONE);
                closebtn.setVisibility(View.GONE);

            }
        });

        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Approveapp();
            }
        });

        txtapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Approveapp();
            }
        });



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ApplicationItem lecmod = dataSnapshot.child("Applications").child(Selected.value1).getValue(ApplicationItem.class);
                if (lecmod != null)
                {
                    fname.setText(lecmod.getApplifullname());
                    modname.setText(lecmod.getModname());
                    appmark.setText(lecmod.getApplimark() +"%");
                    appstatus.setText(lecmod.getStatus());
                    lecemail.setText(lecmod.getLecemail());
                    Picasso.get().load(lecmod.getIdcopy()).into(imageView);
                    Picasso.get().load(lecmod.getQualcopy()).into(imageView2);



                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            Picasso.get().load(lecmod.getIdcopy()).into(imageView3);
                            imageView3.setVisibility(View.VISIBLE);
                            closebtn.setVisibility(View.VISIBLE);



                        }
                    });

                    imageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            Picasso.get().load(lecmod.getQualcopy()).into(imageView3);
                            imageView3.setVisibility(View.VISIBLE);
                            closebtn.setVisibility(View.VISIBLE);



                        }
                    });


                    if(lecmod.getStatus().equals("Approved and interview scheduled" )){
                        approvebtn.setVisibility(View.GONE);
                        txtapprove.setVisibility(View.GONE);
                        declinebtn.setVisibility(View.GONE);
                        txtdecline.setVisibility(View.GONE);
                    }


                }
                else
                {
                    Toast.makeText(HodappdetailsActivity.this, "Application doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HodappdetailsActivity.this, HodTArequestsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    private void Approveapp() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                ApplicationItem lecmod = dataSnapshot.child("Applications").child(Selected.value1).getValue(ApplicationItem.class);


                RootRef.child("Applications").child(lecmod.getAppid()).setValue(new ApplicationItem(lecmod.getAppid(),lecmod.getAppliemail(), lecmod.getApplifullname(),
                        lecmod.getApplimark(), lecmod.getDate(), lecmod.getHodemail(), lecmod.getIdcopy(),lecmod.getJobid(),lecmod.getLecemail(),lecmod.getQualcopy(),"Approved and interview scheduled",lecmod.getTime(),lecmod.getStudentnumber(),lecmod.getModname(),lecmod.getCosname()));


                if (lecmod != null)
                {
                    Toast.makeText(HodappdetailsActivity.this, "Approved and interview scheduled", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HodappdetailsActivity.this, HodappdetailsActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(HodappdetailsActivity.this, "Request doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HodappdetailsActivity.this, HodTArequestsActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}