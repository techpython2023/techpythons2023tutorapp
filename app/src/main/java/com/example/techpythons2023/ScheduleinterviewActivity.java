package com.example.techpythons2023;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techpythons2023.Model.ApplicationItem;
import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Model.TaopeningItem;
import com.example.techpythons2023.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;

import us.zoom.sdk.MeetingService;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.StartMeetingParamsWithoutLogin;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;


public class ScheduleinterviewActivity extends AppCompatActivity {

    TextView modname,cosname;
    TextView Time;
    TextView Date;
    Button submitbtn;

    private String saveCurrentDate, saveCurrentTime;
    private String productRandomKey;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private DatabaseReference intersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleinterview);

        cosname = findViewById(R.id.cosnamev);
        modname = findViewById(R.id.modnamev);
        Date = findViewById(R.id.minmark);
        Time = findViewById(R.id.minqual);

        submitbtn = findViewById(R.id.submitbtn);


        intersRef = FirebaseDatabase.getInstance().getReference().child("Interviews");




        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ScheduleinterviewActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                Date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleinterviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule();
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
                    cosname.setText(lecmod.getCosname());
                    modname.setText(lecmod.getModname());

                }
                else
                {
                    Toast.makeText(ScheduleinterviewActivity.this, "Application doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ScheduleinterviewActivity.this, HodTArequestsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void Schedule(){
        String date = Date.getText().toString();
        String time = Time.getText().toString();





            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {


                    ApplicationItem lecmod = dataSnapshot.child("Applications").child(Selected.value1.toString().replace(" ","")).getValue(ApplicationItem.class);

                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calendar.getTime());

                    productRandomKey = saveCurrentDate + saveCurrentTime;


                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("Interid", productRandomKey.toString().replace(" ",""));
                    productMap.put("Date", date);
                    productMap.put("Time", time);
                    productMap.put("Applid", lecmod.getAppid());
                    productMap.put("Jobid", lecmod.getJobid());
                    productMap.put("Applimark", lecmod.getApplimark());
                    productMap.put("Applifullname", lecmod.getApplifullname());
                    productMap.put("Lecemail", lecmod.getLecemail());
                    productMap.put("Appliemail",lecmod.getAppliemail());
                    productMap.put("Status", "Interview scheduled");

                    productMap.put("Hodemail", lecmod.getHodemail());
                    productMap.put("Meetinglink","https://us05web.zoom.us/j/5983695065?pwd=N0JETXJDMkMrZUtWcGRkQ3RLdGNBdz09");
                    productMap.put("Startid","");
                    productMap.put("Startpas","");
                    productMap.put("Modname",lecmod.getModname());
                    productMap.put("Cosname",lecmod.getCosname());




                    intersRef.child(productRandomKey.toString().replace(" ","")).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ScheduleinterviewActivity.this, "Application submited successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ScheduleinterviewActivity.this, ApplyActivity.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        String message = task.getException().toString();
                                        Toast.makeText(ScheduleinterviewActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




    }

    public  void Check(){

    }


}
