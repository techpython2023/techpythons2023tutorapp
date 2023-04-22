package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddStaffActivity extends AppCompatActivity {
    private static final String[] roles = {"Admin", "Tutor", "Lecture","Hod"};
    DatabaseReference databaseReference;
    Button staffs,submitbtn;
    EditText Email,Password,Staffnum;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        staffs = (Button) findViewById(R.id.backtostaffsbtn);
        Email =(EditText) findViewById((R.id.email));
        Password =(EditText) findViewById((R.id.password));
        Staffnum =(EditText) findViewById((R.id.staffNumber));

        spinner = findViewById((R.id.rolesspiner));
        submitbtn = findViewById((R.id.submitbtn));

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AddStaffActivity.this,
                android.R.layout.simple_spinner_item,roles);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        staffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddStaffActivity.this, StaffsActivity.class);
                startActivity(i);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                adddep();

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();


    }



    public void adddep(){
        String roles = spinner.getSelectedItem().toString();
        String email = Email.getText().toString().toLowerCase(Locale.ROOT).replace(".", ",");
        String staffnum = Staffnum.getText().toString();
        String password = Password.getText().toString();



        if (TextUtils.isEmpty(roles))
        {
            Toast.makeText(this, "Please select role", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(staffnum))
        {
            Toast.makeText(this, "Please write staff number", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write password", Toast.LENGTH_SHORT).show();
        }

        else
        {

            Validatedepname(roles,email,staffnum,password);
        }
    }

    public  void Validatedepname(final String roles,final String email,final String staffnum,final String password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("role",roles);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);
                    userdataMap.put("studentnumber",staffnum);




                    RootRef.child("Users").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AddStaffActivity.this, "Staff member has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddStaffActivity.this, AddStaffActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(AddStaffActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddStaffActivity.this,AddStaffActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AddStaffActivity.this, "This staff member already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddStaffActivity.this, AddStaffActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}