package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.ApplicationItem;
import com.example.techpythons2023.Model.InterviewItem;
import com.example.techpythons2023.Model.Selected;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.StartMeetingParamsWithoutLogin;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class UserinterdetailsActivity extends AppCompatActivity {
    TextView fname,modname,date, time, appliemail,status;
    Button userpotbtn, startbtn;
    private DatabaseReference tutorsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinterdetails);

        fname = findViewById(R.id.fname);
        modname = findViewById(R.id.modname);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        appliemail = findViewById(R.id.appliemail);
        startbtn = findViewById(R.id.startbtn);
        userpotbtn = findViewById(R.id.userpotbtn);
        status = findViewById(R.id.status);

        tutorsRef = FirebaseDatabase.getInstance().getReference().child("Tutors");


        initializeSdk(this);


        userpotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(UserinterdetailsActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                joinMeeting(UserinterdetailsActivity.this,"5983695065","5s2EVZ");
            }
        });







        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                InterviewItem lecmod = dataSnapshot.child("Interviews").child(Selected.value1).getValue(InterviewItem.class);

                ApplicationItem app = dataSnapshot.child("Applications").child(lecmod.getApplid()).getValue(ApplicationItem.class);



                if (lecmod != null)
                {
                    fname.setText(lecmod.getApplifullname());
                    modname.setText(lecmod.getModname());
                    date.setText(lecmod.getDate());
                    time.setText(lecmod.getTime());
                    appliemail.setText(lecmod.getAppliemail());
                    status.setText(lecmod.getStatus());

                    if(app.getStatus().equals("Interview started")){

                        startbtn.setVisibility(View.GONE);

                    }

                    if(app.getStatus().equals("Interview success applicant appointed")){
                        startbtn.setVisibility(View.GONE);
                    }





                }
                else
                {
                    Toast.makeText(UserinterdetailsActivity.this, "Interview doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserinterdetailsActivity.this, UserintersActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }


    public void initializeSdk(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        // TODO: Do not use hard-coded values for your key/secret in your app in production!
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        params.appKey = "3dimUYAkQwmMlDrbWw4oYA"; // TODO: Retrieve your SDK key and enter it here
        params.appSecret = "L2oKuPhTazflJBUeMWxHHCP4Rz2BfDRr"; // TODO: Retrieve your SDK secret and enter it here
        params.domain = "zoom.us";
        params.enableLog = true;
        // TODO: Add functionality to this listener (e.g. logs for debugging)
        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            /**
             * @param errorCode {@link us.zoom.sdk.ZoomError#ZOOM_ERROR_SUCCESS} if the SDK has been initialized successfully.
             */
            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
            }

            @Override
            public void onZoomAuthIdentityExpired() {
            }
        };





        sdk.initialize(context, listener, params);
    }



    public void joinMeeting(Context context, String meetingNumber, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {



                InterviewItem inter = dataSnapshot.child("Interviews").child(Selected.value1).getValue(InterviewItem.class);

                ApplicationItem lecmod = dataSnapshot.child("Applications").child(inter.getApplid()).getValue(ApplicationItem.class);


                RootRef.child("Applications").child(lecmod.getAppid()).setValue(new ApplicationItem(lecmod.getAppid(),lecmod.getAppliemail(), lecmod.getApplifullname(),
                        lecmod.getApplimark(), lecmod.getDate(), lecmod.getHodemail(), lecmod.getIdcopy(),lecmod.getJobid(),lecmod.getLecemail(),lecmod.getQualcopy(),"Interview started",lecmod.getTime(),lecmod.getStudentnumber(),lecmod.getModname(),lecmod.getCosname()));


                RootRef.child("Interviews").child(Selected.value1).setValue(new InterviewItem(inter.getApplid(),inter.getAppliemail(), inter.getApplifullname(),
                        inter.getApplimark(), inter.getDate(), inter.getHodemail(), inter.getInterid(),inter.getJobid(),inter.getLecemail(), inter.getMeetinglink(), inter.getStartid(), inter.getStartpas(),"Interview started",inter.getTime(), inter.getModname(),inter.getCosname()));


                if (lecmod != null)
                {
                    MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
                    JoinMeetingOptions options = new JoinMeetingOptions();
                    JoinMeetingParams params = new JoinMeetingParams();
                    params.displayName = lecmod.getApplifullname(); // TODO: Enter your name
                    params.meetingNo = meetingNumber;
                    params.password = password;
                    meetingService.joinMeetingWithParams(context, params, options);
                }
                else
                {
                    Toast.makeText(UserinterdetailsActivity.this, "Meeting doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserinterdetailsActivity.this, UserintersActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }




}