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

public class AddCourseActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button deps,submitbtn;
    EditText Cosname;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        deps = (Button) findViewById(R.id.backtocosbtn);
        Cosname =(EditText) findViewById((R.id.cosName));
        spinner = findViewById((R.id.departmentsspiner));
        submitbtn = findViewById((R.id.submitbtn));


        deps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddCourseActivity.this, CoursesActivity.class);
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

        listdepartments();

    }

    public void listdepartments(){
        databaseReference.child("Departments").addValueEventListener(new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final List<String> Departments = new ArrayList<String>();

                for ( DataSnapshot suggestionSnap : dataSnapshot.getChildren() )
                {
                    String suggestion = suggestionSnap.child("Depname").getValue(String.class);
                    Departments.add(suggestion);

                }//End For()

                //XML TextView variable
                spinner = (Spinner) findViewById(R.id.departmentsspiner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_list_item_1, Departments);
                spinner.setAdapter(adapter);

            }//End onDataChange()

            public void onCancelled(DatabaseError databaseError)
            {

            }//End onCancelled()

        });

    }


    public void adddep(){
        String depname = spinner.getSelectedItem().toString();
        String cosname = Cosname.getText().toString();


        if (TextUtils.isEmpty(depname))
        {
            Toast.makeText(this, "Please select department", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(cosname))
        {
            Toast.makeText(this, "Please write course name", Toast.LENGTH_SHORT).show();
        }

        else
        {

            Validatedepname(cosname,depname);
        }
    }

    public  void Validatedepname(final String cosname,final String depname){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Courses").child(cosname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Cosname",cosname);
                    userdataMap.put("Depname",depname);



                    RootRef.child("Courses").child(cosname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AddCourseActivity.this, "Course has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddCourseActivity.this, AdddepActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(AddCourseActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddCourseActivity.this,AddCourseActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AddCourseActivity.this, "This " + cosname + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddCourseActivity.this, AddCourseActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}